# @author: Hiera Sanghera & Daniel Willemsen
# This module defines functions for the interaction between aircraft.

from math import *
import numpy as np

#values for time and intrusion zones
sst = 30.
vzone = 50.
hzone = 250.

#get as begin 4 numpy vectors: x1,x2,v1,v2
#Calculates the relative velocity between two aircraft.
def relvelocity(v1,v2):
    relativevelocity = v2-v1
    return relativevelocity

#Calculates the relative distance between two aircraft.
def reldistance(s1, s2):
    relativedistance = s2-s1
    return relativedistance

#check the severity at a certain moment
def severity(s0r,vr,t):
    t_mindistance = t #-(s0rx*vrx+s0ry*vry+s0rz*vrz)/(vrx**2+vry**2+vrz**2)
    sr_mindistance = s0r+vr*t_mindistance
    srx_mindistance = sr_mindistance[0]
    sry_mindistance = sr_mindistance[1]
    srz_mindistance = sr_mindistance[2]

        #print t_mindistance
        #print min(t2_horizontal,t2_vertical)
    srh_mindistance = sqrt(sry_mindistance**2+srx_mindistance**2)

    severity_vertical = (vzone-abs(srz_mindistance))/vzone\
        
    severity_horizontal = (hzone - abs(srh_mindistance))/hzone

    severity_real = min(severity_vertical,severity_horizontal)
    return severity_real

#detect the vertical intrusion
def intrusiondetect_vertical(s0rz,vrz):
    vertical_trouble = False
    intrusion_vertical = False
    t1_vertical = False
    t2_vertical = False
    if abs(vrz)>0.: #to avoid dividing by 0.
        t1_vertical = min((-vzone - s0rz)/vrz,(vzone - s0rz)/vrz)
    #print t1_vertical
        t2_vertical = max((-vzone - s0rz)/vrz,(vzone - s0rz)/vrz)
    #print t2_vertical
    #print s0rz
    #if plane enters, exits or starts in zone and remains there, possible intrusion
        if (t1_vertical >=0 and t1_vertical < sst)\
        or (t2_vertical >=0 and t2_vertical < sst)\
        or abs(s0rz) < vzone:
            intrusion_vertical = True
    elif abs(s0rz)<vzone:
        intrusion_vertical = True
        vertical_trouble = True
    return intrusion_vertical,vertical_trouble,t1_vertical,t2_vertical

#detect the horizontal intrusion
def intrusiondetect_horizontal(vrx,vry,s0rx,s0ry):
    #solve the quadratic equation to find entrance and exit points in horizontal
    intrusion_horizontal = False
    horizontal_trouble = False
    t1_horizontal = False
    t2_horizontal = False
    
    a = vrx**2+vry**2
    b = 2*(vrx*s0rx+vry*s0ry)
    c = s0rx**2+s0ry**2

    p = np.array([a,b,c-hzone**2])
    s0rh = sqrt(s0rx**2+s0ry**2)
        
    if b**2-4*a*(c-hzone**2)>0.:
        roots = np.roots(p)
        t1_horizontal = min(roots)
        t2_horizontal = max(roots)
        if (t1_horizontal >=0. and t1_horizontal < sst)\
        or (t2_horizontal >=0. and t2_horizontal < sst)\
        or s0rh<hzone:
            intrusion_horizontal = True
    elif s0rh<hzone:
        intrusion_horizontal = True
        horizontal_trouble = True
    return intrusion_horizontal,horizontal_trouble,t1_horizontal,t2_horizontal
    

#Determines if there is an intrusion between two aircraft.
def intrusion(x1,x2,v1,v2):
    
    #setup initial things
    intrusion_real = False
    intrusion_horizontal = False
    horizontal_trouble = False
    severity_real = 0.    
    t_maxseverity = -1.

    #calculate relative position and velocity
    s0r = reldistance(x1,x2) #[0]: x,[ 1]:y,   {2]:z
    vr = relvelocity(v1,v2)
    
    #vertical intrusion detection   
    s0rz = s0r[2] #vertical relative distance
    vrz = vr[2]  #vertical relative velocity    
    intrusion_vertical,vertical_trouble,t1_vertical,t2_vertical = intrusiondetect_vertical(s0rz,vrz)

    #check horizontal if there is vertical intrusion
    if intrusion_vertical == True:
        vrx = vr[0]
        vry = vr[1]
        s0rx = s0r[0]
        s0ry = s0r[1]
        intrusion_horizontal,horizontal_trouble,t1_horizontal,t2_horizontal = intrusiondetect_horizontal(vrx,vry,s0rx,s0ry)
             
    #check if there occurs a real intrusion (horizontal and vertical occur at the same time)
    #if no things are dividing by 0: find the time of max severity
    if intrusion_horizontal and not horizontal_trouble and not vertical_trouble:
        if not (t2_vertical-t1_horizontal<0. or t2_horizontal-t1_vertical<0.):
            intrusion_real = True
        if intrusion_real == True:
        #print t1_horizontal
        #print t2_horizontal
        #print t1_vertical
        #print t2_vertical
            a = (vrx**2+vry**2)*vzone**2-vrz**2*hzone**2
            b = 2*((vrx*s0rx+vry*s0ry)*vzone**2-vrz*s0rz*hzone**2)
            c = (s0rx**2+s0ry**2)*vzone**2-s0rz**2*hzone**2
            p = np.array([a,b,c])
            tmaxh = -(s0rx*vrx+s0ry*vry)/(vrx**2+vry**2)
            tmaxv = -s0rz/vrz
            if b**2-4*a*c>0.:
                roots = np.roots(p)
                t1 = min(roots)
                t2 = max(roots)
            
            
                if t1<tmaxv<t2:#case 2
                    if tmaxh>t2:
                        t_maxseverity = t2
                    elif tmaxh<t1:
                        t_maxseverity = t1
                    else: t_maxseverity = tmaxv
                if t2<tmaxv: #case 3
                    if tmaxh>t2:
                        t_maxseverity = tmaxh
                    else:
                        t_maxseverity = t2
                if t1>tmaxv:
                    if tmaxh<t1:
                        t_maxseverity = tmaxh
                    else:
                        t_maxseverity = t1
            else:
                t_maxseverity = tmaxh #case1
        #make sure the max severity is in the interval between the 2 datapoints
            if t_maxseverity >sst:
                t_maxseverity = sst
            if t_maxseverity <0.:
                t_maxseverity = 0.
    
            #algoritm for severitycheck
            severity_real = severity(s0r,vr,t_maxseverity)

    #division by 0 in horizontal
    elif intrusion_horizontal and horizontal_trouble and not vertical_trouble:
        intrusion_real = True
        tmaxv = -s0rz/vrz
        t_maxseverity = tmaxv
        if t_maxseverity >sst:
            t_maxseverity = sst
        if t_maxseverity <0.:
            t_maxseverity = 0.
        #algoritm for severitycheck
        severity_real = severity(s0r,vr,t_maxseverity)

    #division by 0 in vertical
    elif intrusion_horizontal and vertical_trouble and not horizontal_trouble:
        intrusion_real = True
        tmaxh = -(s0rx*vrx+s0ry*vry)/(vrx**2+vry**2)
        t_maxseverity = tmaxh
        if t_maxseverity >sst:
            t_maxseverity = sst
        if t_maxseverity <0.:
            t_maxseverity = 0.
        #algoritm for severitycheck    
        severity_real = severity(s0r,vr,t_maxseverity)

    #division by 0 in both horizontal and vertical
    elif intrusion_horizontal and vertical_trouble and horizontal_trouble:
        intrusion_real = True
        t_maxseverity = 0.
        #algoritm for severitycheck
        severity_real = severity(s0r,vr,t_maxseverity)   


            
    return intrusion_real,severity_real,t_maxseverity
        
    ###DENK OM DE VERANTWOORDING VOOR LINEARE INTERPOLATIE
    ###CHECK VOOR ZELFDE INTRUSION 2x NA ELKAAR.
    
