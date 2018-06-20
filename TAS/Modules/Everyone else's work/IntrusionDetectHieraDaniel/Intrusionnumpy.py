# Intrusion module
# @author: Hiera Sanghera, Daniel Willemsen and Andreas Theys
# @version 2.0
# Module for algebraic intrusion-detection and -processing .

from math import *
import numpy as np
# Time and space specs for intrusions

global vZone
vZone = 50.
global hZone
hZone = 250.
#relative
def relative(p):
    pr = p - p.T
    return pr
# Calculates the relative distance between two datapoints.
def rDistance(datapoint1,datapoint2):
    datapoint1 = list(datapoint1)
    datapoint2 = list(datapoint2)
    dx = datapoint2[2]-datapoint1[2]
    
    dy = datapoint2[3]-datapoint1[3]
    dz = datapoint2[4]-datapoint1[4]
    drel = [dx,dy,dz]
    return drel


# Computes the relative velocity between two datapoints.
def rVelocity(datapoint1,datapoint2):
    datapoint1 = list(datapoint1)
    datapoint2 = list(datapoint2)
    vx = datapoint2[5]-datapoint1[5]
    vy = datapoint2[6]-datapoint1[6]
    vz = datapoint2[7]-datapoint1[7]
    vrel = [vx,vy,vz]
    return vrel


# Severity check at a certain time t
def severity(drel,vrel,t):
    # Correct type casting input variables 
    drel = list(drel)
    vrel = list(vrel)
    t = float(t) 
    # Vectorized relative positions
    dx = drel[0] + vrel[0]*t
    dy = drel[1] + vrel[1]*t
    dz = drel[2] + vrel[2]*t
    # Distance measures
    d_hor = sqrt(dy**2+dz**2)
    d_vert = abs(dx)
    # Severities
    sever_hor = 1.-float(d_hor/hZone)
    sever_vert = 1.-float(d_vert/vZone)
    # Output severity
    sever = min(sever_hor,sever_vert)
    return sever


# Method for the detection of vertical intrusion
# NOTE[1]: Coordinate tranformation: x-direction is vertical direction
# NOTE[2]: dx and vx, repsectively, relative position and velocity 
# NOTE[3]: ts is timestep
def detect_vert(xrel,vxrel,ts):
    
    # Correct type casting
    #conf_vert = dAlt - dVS * ts < vert_margin
    # Evaluation parameters
    #trouble = False
    #intrusion = False
    #t1 = 0.
    #t2 = 0.
    xrelar = np.asarray(np.copy(xrel)+np.identity(len(xrel))*9999999.)
    vxrelar = np.asarray(np.copy(vxrel)+np.identity(len(xrel))*0.00000001)
    
    # Condition to avoid division by zero float error
    ta = (-xrelar-vZone)/vxrelar
    tb = (-xrelar+vZone)/vxrelar
    b = ta<tb
    t1 = b*ta+np.invert(b)*tb
    t2 = b*tb+np.invert(b)*ta
    #print t1
    #print t2
    #print abs(xrelar)
    intrusion = np.logical_or(np.logical_or(np.logical_and(t1>=0.,t1<=ts),np.logical_and(t2>=0.,t2<=ts)),abs(xrelar)<vZone)
    return intrusion
    '''
    if (not(abs(vx)<1e-9)): 
        times1 = [float((-dx-vZone)/vx),float((vZone-dx)/vx)]
        t1 = min(times)
        t2 = max(times)
        # If the vertical intrusion space is entered
        # exited or started in zone and remained within
        # it provides grounds for a possible intrusion
        if (t1>=0 and t1<=ts) or (t2>=0 and t2<=ts) or (abs(dx)<vZone):
            intrusion = True
    # Direct evaluation
    elif (abs(dx)<vZone):
        intrusion = True
        trouble = True      
    # Output parameters for evaluation
    return intrusion,trouble,t1,t2
    '''

# Method for the detection of horizontal intrusion
# NOTE[1]: Coordinate tranformation: yz-plane is vertical plane
# NOTE[2]: dy,dz and vy,vz repsectively, relative position(s) and velocit(y)(ies) 
# NOTE[3]: ts is timestep
def detect_hor(dy,dz,vy,vz,ts):
    # Correct type casting
    dy,dz,vy,vz = float(dy),float(dz),float(vy),float(vz)
    ts = float(ts)
    # Evaluation parameters
    trouble,intrusion = False,False
    t1,t2 = 0.,0.
    # Solve the quadratic equation to find entrance 
    # and exit points in horizontal plane
    # Equation coefficient
    a = vy**2+vz**2
    b = 2*(vy*dy+vz*dz)
    c = dy**2+dz**2
    coeff = [a,b,c-hZone**2]
    # Horizontal distance
    h = sqrt(dy**2+dz**2)
    # Solutions quadratic equation    
    D = coeff[1]**2-4*coeff[0]*coeff[2]
    if (D>0.):
        root1 = (-coeff[1]+sqrt(D))/(2*coeff[0])
        root2 = (-coeff[1]-sqrt(D))/(2*coeff[0])
        roots = [root1,root2]
        t1 = min(roots)
        t2 = max(roots)
        if (t1>=0. and t1<ts) or (t2>=0. and t2<ts) or (h<hZone):
            intrusion = True
    # Direct evaluation
    elif (h<hZone):
        intrusion = True
        trouble = True
    # Output parameters
    return intrusion,trouble,t1,t2

 
# Makes sure maximum severity is in the appropriate time interval
def timeInterval(t,ts):
    t,ts = float(t),float(ts)
    if (t<0.):
        return 0.
    elif (t>ts):
        return ts
    else:
        return t


# Intrusion determination between two datapoints
# def intrusion(x1,x2,v1,v2):
def intrusion(x,y,z,vx,vy,vz,ids):
    ts = 30.
    # INITIATION
    # Correct type casting
    #datapoint1,datapoint2 = list(datapoint1),list(datapoint2)
    # Initial parameter setup
    #intrusion_real = False    
    # PROCEDURE
    # [1] Relative positions and velocities
    xrel = relative(x)
    yrel = relative(y)
    zrel = relative(z)

    vxrel = relative(vx)
    vyrel = relative(vy)
    vzrel = relative(vz)

    intrusions1 = []
    intrusions2 = []
    # [2] Vertical intrusion detection      
    intrusion_vert = detect_vert(xrel,vxrel,ts)
    #print intrusion_vert
    # [3] Create list of possible intrusions
    for i in range(len(intrusion_vert[0])):
        for j in range(i):
            if intrusion_vert[i,j]:
                intrusions1.append([ts,ids[0,i],x[0,i],y[0,i],z[0,i],vx[0,i],vy[0,i],vz[0,i],])
                intrusions2.append([ts,ids[0,j],x[0,j],y[0,j],z[0,j],vx[0,j],vy[0,j],vz[0,j],ids[0,j]])
    return intrusions1,intrusions2
'''
    # If there is vertical intrusion, a check
    # for horizontal intrusion is performed
    if (intrusion_vert):
        dy,dz,vy,vz = drel[1],drel[2],vrel[1],vrel[2]
        intrusion_hor,trouble_hor,t1_hor,t2_hor = detect_hor(dy,dz,vy,vz,ts)
             
    # Check if there occurs a real intrusion
    # No things are dividing by 0 ==> find time of max severity
    if (intrusion_hor) and (not(trouble_hor)) and (not(trouble_vert)):
        if not(t2_vert-t1_hor<0.) and not(t2_hor-t1_vert<0.):
            intrusion = True
        if (intrusion):
            # Coeffecient quadratic equation
            a = (vy**2+vz**2)*vZone**2-vx**2*hZone**2
            b = 2*((vy*dy+vz*dz)*vZone**2-vx*dx*hZone**2)
            c = (dy**2+dz**2)*vZone**2-dx**2*hZone**2
            coeff = [a,b,c]
            # Time specs maximal severities
            t_maxh = -(dy*vy+dz*vz)/(vy**2+vz**2)
            t_maxv = -dx/vx
            # Solve quadratic equation
            D = coeff[1]**2-4*coeff[0]*coeff[2]
            if (D>=0.):
                root1 = (-coeff[1]+sqrt(D))/(2*coeff[0])
                root2 = (-coeff[1]-sqrt(D))/(2*coeff[0])
                roots = [root1,root2]
                t1,t2 = min(roots),max(roots)
                # Cases
                if t1<t_maxv and t_maxv<t2:
                    if t_maxh>t2:
                        t_maxsever = t2
                    elif t_maxh<t1:
                        t_maxsever = t1
                    else: 
                        t_maxsever = t_maxv
                elif t2<=t_maxv:
                    if t_maxh>t2:
                        t_maxsever = t_maxh
                    else:
                        t_maxsever = t2
                else:
                    if t_maxh<t1:
                        t_maxsever = t_maxh
                    else:
                        t_maxsever = t1
            else:
                t_maxsever = t_maxh
            # Maximum severity in the appropriate time interval
            t_maxsever = timeInterval(t_maxsever,ts)
            # Severity determination
            sever = severity(drel,vrel,t_maxsever)

    # Division by 0 float error in horizontal plane
    elif (intrusion_hor) and (trouble_hor) and (not(trouble_vert)):
        intrusion = True
        t_maxv = -dx/vx
        t_maxsever = t_maxv
        # Maximum severity in the appropriate time interval
        t_maxsever = timeInterval(t_maxsever,ts)
        # Severity determination
        sever = severity(drel,vrel,t_maxsever)

    # Division by 0 float error in vertical direction
    elif (intrusion_hor) and (trouble_vert) and (not(trouble_hor)):
        intrusion = True
        t_maxh = -(dy*vy+dz*vz)/(vy**2+vz**2)
        t_maxsever = t_maxh
        # Maximum severity in the appropriate time interval
        t_maxsever = timeInterval(t_maxsever,ts)
        # Severity determination
        sever = severity(drel,vrel,t_maxsever)

    # Division by 0 float error in vertical direction and horizontal plane
    elif (intrusion_hor) and (trouble_vert) and (trouble_hor):
        intrusion = True
        t_maxsever = 0.
        # Severity determination
        sever = severity(drel,vrel,t_maxsever)             
    return intrusion,sever,t_maxsever
'''    