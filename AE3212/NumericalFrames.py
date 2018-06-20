# -*- coding: utf-8 -*-
"""
Created on Tue Feb 28 12:36:13 2017

@author: kmeijerman
print """

import Booms as booms
import Geometry as geo
from math import *
import numpy as np
import Inertia as inert
import IdealizedMOI as idmoi
import NumericalShear as numshear
import Reactions as reac

#Calculate boom areas for the frame [mm^2]
def boom_area_frames(t_s,l_seg,y_booms,A_st):
    B_frame = []
    #Boom area for first 35 booms
    for i in range(len(y_booms)-1):
        if abs(y_booms[i])<=0.1:
            A_previous,A_next = 0,0
        else:
            A_previous = booms.areaSkinContribution(t_s,l_seg,y_booms[i-1],y_booms[i])
            A_next     = booms.areaSkinContribution(t_s,l_seg,y_booms[i+1],y_booms[i])
        A_tot = A_previous + A_next + A_st
        B_frame.append(A_tot)
    #Boom area for last boom
    if abs(y_booms[i])<=0.5:
        A_previous,A_next = 0,0
    else:
        A_previous = booms.areaSkinContribution(t_s,l_seg,y_booms[-2],y_booms[-1])
        A_next     = booms.areaSkinContribution(t_s,l_seg,y_booms[0],y_booms[-1])
    A_tot = A_previous + A_next + A_st
    B_frame.append(A_tot)
    return B_frame

#Calculate x-and y-dimensions [mm] of each segment AFTER a boom (going clockwise)    
def Segm_len(St_x_frame,St_y_frame):
    Segm_length_x,Segm_length_y = [],[]
    #Segment lengths for first 35 segments
    for i in range(len(St_x_frame)-1):
        Segm_len_x = abs(St_x_frame[i+1]-St_x_frame[i])
        Segm_len_y = abs(St_y_frame[i+1]-St_y_frame[i])
        Segm_length_x.append(Segm_len_x)
        Segm_length_y.append(Segm_len_y)
    #Segment length for last segment
    Segm_len_x = abs(St_x_frame[0]-St_x_frame[-1])
    Segm_len_y = abs(St_y_frame[0]-St_y_frame[-1])
    Segm_length_x.append(Segm_len_x)
    Segm_length_y.append(Segm_len_y)
    return Segm_length_x,Segm_length_y

#Calculate moment arm of each segment to the point at which S_x intersects the y-axis    
def Mom_arm(St_x_frame,St_y_frame):
    x_dist,y_dist = [],[]
    force_loc_x,force_loc_y = 0,(geo.d_ty*1000-geo.h_f)
    for i in range(len(St_x_frame)):
        x_dist_segm = St_x_frame[i]-force_loc_x
        y_dist_segm = St_y_frame[i]-force_loc_y
        x_dist.append(x_dist_segm)
        y_dist.append(y_dist_segm)
    return x_dist, y_dist

def shearcalc_z(z):    
    S_x = reac.V_x(z)       #N
    S_y = reac.V_y(z)       #N
    T = reac.T(z)*10**3     #N*mm    
    #Calculate stringer x- and y-locations [mm] w.r.t. center of circle
    St_x_frame, St_y_frame = booms.calculateStringerLocations()
    #Calculate segment length [mm], constant along circumference
    l_seg_frame = 2*pi*geo.R/geo.n_st
    #Calculate boom areas [mm^2]
    B_frame = boom_area_frames(geo.t_s,l_seg_frame,St_y_frame,inert.stringerArea())
    #Put values into a matrix of x [mm], y [mm] and boom area [mm^2]
    B_geo = np.zeros([len(B_frame),3])
    B_geo[:,0],B_geo[:,1],B_geo[:,2] = St_x_frame,St_y_frame,B_frame
    #Calculate centroid x- and y-location [mm] w.r.t. center of circle and MOI's
    B_geo_MOI = np.copy(B_geo)
    x_bar_frame,y_bar_frame,I_xx_frame,I_yy_frame, I_xy_frame = idmoi.MOI_idealized(B_geo_MOI)
    #Calculate shear segment contribution
    q_b_frame = numshear.shearSegmentContribution(S_x,S_y,I_xx_frame,I_yy_frame,B_frame,B_geo,x_bar_frame,y_bar_frame)
    #Intersection point of S_x and y-axis
    force_loc_x,force_loc_y = 0,(geo.d_ty*1000-geo.h_f)
    #Calculate segment lengths and their moment arms
    Segm_length_x,Segm_length_y = Segm_len(St_x_frame,St_y_frame)
    x_dist,y_dist = Mom_arm(St_x_frame,St_y_frame)
    #Create matrix of q_b, x-length, y-length, moment arms in x and y and moments around x- and y-axis
    q_b_matrix = np.zeros([len(q_b_frame),7])
    q_b_matrix[:,0] = q_b_frame
    q_b_matrix[:,1],q_b_matrix[:,2] = Segm_length_x,Segm_length_y
    q_b_matrix[:,3],q_b_matrix[:,4] = x_dist,y_dist
    q_b_matrix[:,5] = q_b_matrix[:,0]*q_b_matrix[:,1]*q_b_matrix[:,4]
    q_b_matrix[:,6] = q_b_matrix[:,0]*q_b_matrix[:,2]*q_b_matrix[:,3]
    #Calculation of constant shear flow for closed section using 2q_s0*A = -sum(p*q_b*ds)
    q_s0_frame = -(sum(q_b_matrix[:,5])+sum(q_b_matrix[:,6]))/2
    #Shear flow due to torque
    A_encl = pi*geo.R**2
    q_T = T/(2.*A_encl)
    #Final shear calculation: matrix with q_b, q_tot
    q_final_frame = np.zeros([len(q_b_matrix[:,0]),2])
    q_final_frame[:,0] = q_b_matrix[:,0]
    q_final_frame[:,1] = q_final_frame[:,0]+q_s0_frame+q_T
    return q_final_frame[:,1]

#z-locations of frames
z_front = geo.L-geo.L_f1
z_rear = geo.L-geo.L_f1 - geo.L_f2

#Front frame calculation
q_final_front_frame_backside = shearcalc_z(z_front-0.001)
q_final_front_frame_frontside = shearcalc_z(z_front+0.001)
q_final_front_frame = q_final_front_frame_backside - q_final_front_frame_frontside

#Rear frame calculation
q_final_rear_frame_backside = shearcalc_z(z_rear-0.001)
q_final_rear_frame_frontside = shearcalc_z(z_rear+0.001)
q_final_rear_frame = q_final_rear_frame_backside - q_final_rear_frame_frontside

