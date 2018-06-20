# BoomArea Module.
# @author: Jari Lubberding/Andreas Theys
# @version: 1.0

from math import *
import Inertia as inert
import Geometry as geo
import IdealizedMOI as idmoi

# Calculate stringer locations
# Inputs: Radius [mm], number of stringers
# Origin coincides with center of circle
# Returns lists of x and y [mm] with length = n_st
def calculateStringerLocations():
    St_x = []
    St_y = []
    for i in range(0,int(geo.n_st)):
        angle = 2.*pi/geo.n_st*i
        St_y.append(cos(angle)*geo.R)
        St_x.append(sin(angle)*geo.R)
    return St_x, St_y

# Calculate the locations of the floor booms
# Inputs: Number of floor booms, floor length [mm], radius [mm], floor height [mm]
# Origin coincides with center of circle    
# Returns lists of x and y [mm] with length = n_fl_booms
def calculateFloorBoomLocations(n_fl_booms):
    B_fl_loc_x = []
    B_fl_loc_y = []
    L_f_segment = inert.floorWidth()/(n_fl_booms-1)
    y_loc = -geo.R+geo.h_f
    for i in range(n_fl_booms):
        x_loc = inert.floorWidth()/2. - L_f_segment*i
        B_fl_loc_x.append(x_loc)
        B_fl_loc_y.append(y_loc)
    return B_fl_loc_x, B_fl_loc_y
    
# Calculate angle of neutral axis measured CW from positive x-axis
# Inputs: Moments [Nmm], MOI's [mm^4]
# Origin coincides with center of circle
# Returns value for alpha [rad]
def calculateRotatedNeutralAxis(M_x,M_y):
    alpha = atan2((M_y*inert.I_xx()),(M_x*inert.I_yy()))
    return alpha

# Calculate perpendicular distance to NA for all booms 
# Inputs: x- and y-locations of stringers w.r.t center circle [mm], angle of NA [rad], x- and y- locations of centroid w.r.t center circle [mm]
# Origin now coincides with centroid
# Returns list y_transated [mm] with length = n_st
def calculateDistanceToRotatedAxis(x_loc,y_loc,alpha):
    y_translated = []
    for i in range(len(x_loc)):
        y_trans = (x_loc[i]-centroidX())*sin(alpha)+(y_loc[i]-centroidY())*cos(alpha)
        y_translated.append(y_trans)
    return y_translated
    
# Calculate the length of a skin segment between two booms
# Inputs: x- and y-locations of both booms w.r.t. center circle [mm] 
# Returns value for segment length [mm]
def calculateSegmentLength(x_1,y_1,x_2,y_2):
    length = sqrt((x_1-x_2)**2+(y_1-y_2)**2)
    return length

# Calculate the area contribution of the fuselage skin
# Inputs: segment length [mm], normalized distance of adjacent and self boom to NA [mm]
# Returns value for area contribution to boom of skin segment [mm^2]
def areaSkinContribution(t_segm,length, normalized_loc_adj, normalized_loc_self):
    area = t_segm*length/6.*(2+normalized_loc_adj/normalized_loc_self)   
    return area

# Divide the stringers into three segments based on floor location
# Inputs: x- and y-locations of the booms and the stringers [mm]
# Returns x- and y-lists for the three segments [mm]
def calculateStringerSegments(B_fl_loc_x,B_fl_loc_y,St_loc_x=calculateStringerLocations()[0],St_loc_y=calculateStringerLocations()[1]):
    first_segment_x = []
    first_segment_y = []
    second_segment_x = []
    second_segment_y = []
    third_segment_x = []
    third_segment_y = []
    #split it up into different sections
    for i in range(len(St_loc_x)):
        if St_loc_y[i] > B_fl_loc_y[0] and St_loc_x[i] >= 0:
            first_segment_x.append(St_loc_x[i])
            first_segment_y.append(St_loc_y[i])
        elif St_loc_y[i] < B_fl_loc_y[0]:
            second_segment_x.append(St_loc_x[i])
            second_segment_y.append(St_loc_y[i])
        elif St_loc_y[i] > B_fl_loc_y[-1] and St_loc_x[i] <0:
            third_segment_x.append(St_loc_x[i])
            third_segment_y.append(St_loc_y[i])
        else:
            print "waar ben je mee bezig"
    return first_segment_x, first_segment_y, second_segment_x, second_segment_y, third_segment_x, third_segment_y

# Calculate the boom areas for the stringer section
# Inputs: x- and y- locations of the booms, stringer area, segment thickness, angle of NA, x- and y-location of centroid
# Returns     
def calculateBoomAreaStringerSection(previous_segment_x,previous_segment_y,this_segment_x,this_segment_y,next_segment_x,next_segment_y,A_st,ts,alpha):
    previous_segment_y_trans =  calculateDistanceToRotatedAxis(previous_segment_x, previous_segment_y,  alpha)  
    this_segment_y_trans =      calculateDistanceToRotatedAxis(this_segment_x, this_segment_y,          alpha)  
    next_segment_y_trans =      calculateDistanceToRotatedAxis(next_segment_x, next_segment_y,          alpha)
    B_area_seg = []
    
    #calculate boom area of the first element in this_segment
    l_previous_first =    calculateSegmentLength(previous_segment_x[-1],previous_segment_y[-1],this_segment_x[0],this_segment_y[0])
    l_next_first     =    calculateSegmentLength(this_segment_x[0],this_segment_y[0],this_segment_x[1],this_segment_y[1])
    A_previous_first =    areaSkinContribution(ts,l_previous_first,previous_segment_y_trans[-1],this_segment_y_trans[0])
    A_next_first =        areaSkinContribution(ts,l_next_first,this_segment_y_trans[1],this_segment_y_trans[0])
    B_area_seg.append(A_previous_first+A_next_first+A_st)    
    
    #caclculate boom area for all the elements in between
    for i in range(1,len(this_segment_y_trans)-1):
        l_previous  =   calculateSegmentLength(this_segment_x[i-1],this_segment_y[i-1],this_segment_x[i],this_segment_y[i])
        l_next      =   calculateSegmentLength(this_segment_x[i],this_segment_y[i],this_segment_x[i+1],this_segment_y[i+1])   
        A_previous  =   areaSkinContribution(ts,l_previous,this_segment_y_trans[i-1],this_segment_y_trans[i])
        A_next      =   areaSkinContribution(ts,l_next,this_segment_y_trans[i+1],this_segment_y_trans[i])
        A_tot       =   A_previous + A_next + A_st
        B_area_seg.append(A_tot)
    
    #calculate boom area for the last element in this_segment
    l_next_last      =    calculateSegmentLength(this_segment_x[-1],this_segment_y[-1],next_segment_x[0],next_segment_y[0])
    l_previous_last  =    calculateSegmentLength(this_segment_x[-2],this_segment_y[-2],this_segment_x[-1],this_segment_y[-1])
    A_previous_last  =    areaSkinContribution(ts,l_previous_last,this_segment_y_trans[-2],this_segment_y_trans[-1])
    A_next_last  =        areaSkinContribution(ts,l_next_last,next_segment_y_trans[0],this_segment_y_trans[-1])
    B_area_seg.append(A_previous_last+A_next_last+A_st)        
    return B_area_seg

def calculateBoomAreaFloorSection(first_segment_x,first_segment_y,second_segment_x,second_segment_y,third_segment_x,third_segment_y,B_fl_loc_x,B_fl_loc_y,ts,tf,alpha) :
    first_segment_y_trans   =   calculateDistanceToRotatedAxis(first_segment_x,first_segment_y,     alpha)  
    second_segment_y_trans  =   calculateDistanceToRotatedAxis(second_segment_x,second_segment_y,   alpha)  
    third_segment_y_trans   =   calculateDistanceToRotatedAxis(third_segment_x, third_segment_y,    alpha)    
    fl_seg_y_trans          =   calculateDistanceToRotatedAxis(B_fl_loc_x,      B_fl_loc_y,         alpha)

    B_area_fl = []
    
    #calculate boom area of the first element in this_segment
    l_prev_seg_first    =   calculateSegmentLength(first_segment_x[-1],first_segment_y[-1],B_fl_loc_x[0],B_fl_loc_x[0])
    l_next_seg_first    =   calculateSegmentLength(B_fl_loc_x[0],B_fl_loc_x[0],second_segment_x[0],second_segment_y[0])
    l_fl_seg_first      =   calculateSegmentLength(B_fl_loc_x[0],B_fl_loc_x[0],B_fl_loc_x[1],B_fl_loc_x[1])
    A_prev_seg_first    =   areaSkinContribution(ts,l_prev_seg_first,first_segment_y_trans[-1],fl_seg_y_trans[0])
    A_next_seg_first    =   areaSkinContribution(ts,l_next_seg_first,second_segment_y_trans[0],fl_seg_y_trans[0])
    A_fl_seg_first      =   areaSkinContribution(tf,l_fl_seg_first,fl_seg_y_trans[1],fl_seg_y_trans[0])
    B_area_fl.append(A_prev_seg_first+A_next_seg_first+A_fl_seg_first)
    
    #caclculate boom area for all the elements in between (this is the same exact code as in BoomAreaStringerSection except for one line, but too lazy to refactor)
    for i in range(1,len(B_fl_loc_x)-1):
        l_previous  =   calculateSegmentLength(B_fl_loc_x[i-1],B_fl_loc_y[i-1],B_fl_loc_x[i],B_fl_loc_y[i])
        l_next      =   calculateSegmentLength(B_fl_loc_x[i],B_fl_loc_y[i],B_fl_loc_x[i+1],B_fl_loc_y[i+1])   
        A_previous  =   areaSkinContribution(tf,l_previous,fl_seg_y_trans[i-1],fl_seg_y_trans[i])
        A_next      =   areaSkinContribution(tf,l_next,fl_seg_y_trans[i+1],fl_seg_y_trans[i])
        A_tot       =   A_previous + A_next
        B_area_fl.append(A_tot)
        
    l_prev_seg_last    =   calculateSegmentLength(second_segment_x[-1],second_segment_y[-1],B_fl_loc_x[-1],B_fl_loc_x[-1])
    l_next_seg_last    =   calculateSegmentLength(B_fl_loc_x[-1],B_fl_loc_x[-1],third_segment_x[0],third_segment_y[0])
    l_fl_seg_last      =   calculateSegmentLength(B_fl_loc_x[-1],B_fl_loc_x[-1],B_fl_loc_x[-2],B_fl_loc_x[-2])
    A_prev_seg_last    =   areaSkinContribution(ts,l_prev_seg_last,second_segment_y_trans[-1],fl_seg_y_trans[-1])
    A_next_seg_last    =   areaSkinContribution(ts,l_next_seg_last,third_segment_y_trans[0],fl_seg_y_trans[-1])
    A_fl_seg_last      =   areaSkinContribution(tf,l_fl_seg_last,fl_seg_y_trans[-2],fl_seg_y_trans[-1])
    B_area_fl.append(A_prev_seg_last+A_next_seg_last+A_fl_seg_last)
    return B_area_fl    
    


def calculateBoomAreasWithFloor(first_segment_x,first_segment_y,second_segment_x,second_segment_y,third_segment_x,third_segment_y,B_fl_loc_x,B_fl_loc_y,A_st,ts,tf,alpha):
    
    B_area_first_segment    = calculateBoomAreaStringerSection(third_segment_x,third_segment_y,first_segment_x,first_segment_y,B_fl_loc_x,B_fl_loc_y,A_st,ts,alpha)
    B_area_second_segment   = calculateBoomAreaStringerSection(B_fl_loc_x,B_fl_loc_y,second_segment_x,second_segment_y,B_fl_loc_x,B_fl_loc_y,A_st,ts,alpha)
    B_area_third_segment    = calculateBoomAreaStringerSection(B_fl_loc_x,B_fl_loc_y,third_segment_x,third_segment_y,first_segment_x,first_segment_y,A_st,ts,alpha)
    B_area_fl_segment       = calculateBoomAreaFloorSection(first_segment_x,first_segment_y,second_segment_x,second_segment_y,third_segment_x,third_segment_y,B_fl_loc_x,B_fl_loc_y,ts,tf,alpha)
    
    l_first = len(B_area_first_segment)
    l_sec   = len(B_area_second_segment)
    l_third = len(B_area_third_segment)
    l_fl    = len(B_area_fl_segment)
    cell_1_b_area = np.zeros(l_first+l_fl+l_third)    
    cell_2_b_area = np.zeros(l_sec+l_third)
    
    cell_1_b_area[:l_first] = B_area_first_segment
    cell_1_b_area[l_first:l_first+l_fl] = B_area_fl_segment
    cell_1_b_area[l_first+l_fl:] = B_area_third_segment
    
    cell_2_b_area[:l_sec] = B_area_second_segment
    cell_2_b_area[l_sec:] = B_area_fl_segment

    b_all = np.zeros([l_first+l_sec+l_third+l_fl,3])
    b_all[:l_first,0] = first_segment_x
    b_all[:l_first,1] = first_segment_y
    b_all[:l_first,2] = B_area_first_segment
    b_all[l_first:l_first+l_sec,0] = second_segment_x
    b_all[l_first:l_first+l_sec,1] = second_segment_y
    b_all[l_first:l_first+l_sec,2] = B_area_second_segment
    b_all[l_first+l_sec:l_first+l_sec+l_third,0] = third_segment_x
    b_all[l_first+l_sec:l_first+l_sec+l_third,1] = third_segment_y
    b_all[l_first+l_sec:l_first+l_sec+l_third,2] = B_area_third_segment
    b_all[l_first+l_sec+l_third:,0] = B_fl_loc_x
    b_all[l_first+l_sec+l_third:,1] = B_fl_loc_y
    b_all[l_first+l_sec+l_third:,2] = B_area_fl_segment


    return cell_1_b_area, cell_2_b_area, b_all