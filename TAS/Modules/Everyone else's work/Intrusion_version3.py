# IntrusionExtension module (implement Coordinate module)
# @original @author: Hiera Sanghera, Daniel Willemsen and Andreas Theys
# @version 3.0 by Andreas Theys
# Module for algebraic intrusion-detection and -processing .

from math import *
from numpy import *

# Time and space margin specifications for intrusion analysis
# Vertical
global vZone
vZone = 50.
# Horizontal
global hZone
hZone = 250.

# Calculates the relative distance between two datapoints.
# Simulation datapoint convetions are at place
def rDistance(datapoint1,datapoint2):
    drel = (array(datapoint2[2:5])-array(datapoint1[2:5])).tolist()
    return drel

# Computes the relative velocity between two datapoints.
# Simulation datapoint convetions are at place
def rVelocity(datapoint1,datapoint2):
    vrel = (array(datapoint2[5:8])-array(datapoint1[5:8])).tolist()
    return vrel

# Severity check at a certain time t
# drel, vrel relative position/velocity list
def severity(drel,vrel,t):
    d = array(drel)+t*array(vrel)
    dx, dy, dz = d[0], d[1], d[2]
    # Distance measures
    d_hor, d_vert = sqrt(dy*dy+dz*dz),abs(dx)
    # Severities
    sever_hor, sever_vert = 1.-float(d_hor/hZone), 1.-float(d_vert/vZone)
    # Output severity
    sever = min(sever_hor,sever_vert)
    return sever

# Method for the detection of vertical intrusion
# NOTE[1]: Coordinate tranformation: x-direction is vertical direction
# NOTE[2]: dx and vx, repsectively, relative position and velocity 
# NOTE[3]: ts is timestep
def detect_vert(dx,vx,ts):
    # Evaluation parameters
    trouble,intrusion = False,False
    t1,t2 = 0.,0.
    # Condition to avoid division by zero float error
    if (not(abs(vx-0.)<1e-9)): 
        times = [float((-vZone-dx)/vx),float((vZone-dx)/vx)]
        t1, t2 = min(times), max(times)
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

# Method for the detection of horizontal intrusion
# NOTE[1]: Coordinate tranformation: yz-plane is vertical plane
# NOTE[2]: dy,dz and vy,vz repsectively, relative position(s) and velocit(y)(ies) 
# NOTE[3]: ts is timestep
def detect_hor(dy,dz,vy,vz,ts):
    # Evaluation parameters
    trouble,intrusion = False,False
    t1,t2 = 0.,0.
    # Solve the quadratic equation to find entrance 
    # and exit points in horizontal plane
    # Equation coefficient
    a = vy*vy+vz*vz
    b = 2*(vy*dy+vz*dz)
    c = dy*dy+dz*dz
    coeff = array([a,b,c-hZone*hZone])
    # Horizontal distance
    h = sqrt(dy*dy+dz*dz)
    # Solutions quadratic equation    
    if (b*b-4*a*c)>0.:
        r = roots(coeff)
        t1,t2 = min(r),max(r)
        if (t1>=0. and t1<ts) or (t2>=0. and t2<ts) or (h<hZone):
            intrusion = True
    # Direct evaluation
    elif (h<hZone):
        intrusion = True
        trouble = True
    # Output parameters
    return intrusion,trouble,t1,t2

# Makes sure maximum severity is in the appropriate time interval
# NOTE: helper method for intrusion method underneath 
def timeInterval(t,ts):
    # Output evaluation branches
    if (t<0.):
        return 0.
    elif (t>ts):
        return ts
    else:
        return t

# Intrusion determination between two datapoints
# Simulation datapoint convetions are at place
#def intrusion(datapoint1,datapoint2,ts):
def Intrusion(temp):    
    datapoint1,datapoint2,ts = temp[0],temp[1],temp[2]
    #if datapoint1[1]==datapoint2[1]:
    #    return [False]
    # Initial parameter setup
    #intrusion_real = False
    intrusion,intrusion_hor,trouble_hor = False,False,False
    sever, t_maxsever = 0.,-1.    
    
    # PROCEDURE
    # [1] Relative positions and velocities
    drel = rDistance(datapoint1,datapoint2) 
    vrel = rVelocity(datapoint1,datapoint2)
    # [2] Vertical intrusion detection   
    dx = drel[0] 
    vx = vrel[0]    
    intrusion_vert,trouble_vert,t1_vert,t2_vert = detect_vert(dx,vx,ts)

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
            a = (vy*vy+vz*vz)*vZone*vZone-vx*vx*hZone*hZone
            b = 2*((vy*dy+vz*dz)*vZone*vZone-vx*dx*hZone*hZone)
            c = (dy*dy+dz*dz)*vZone*vZone-dx*dx*hZone*hZone
            coeff = array([a,b,c])
            # Time specs maximal severities
            t_maxh = -(dy*vy+dz*vz)/(vy*vy+vz*vz)
            t_maxv = -dx/vx
            # Solve quadratic equation
            if (b*b-4*a*c)>0.:
                r = roots(coeff)
                t1,t2 = min(r),max(r)
                # t_maxsever assignment cases
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
        t_maxh = -(dy*vy+dz*vz)/(vy*vy+vz*vz)
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
    
    # TERMINATION
    # Output branches
    if(intrusion):
        ID = [intrusion,t_maxsever,datapoint1[1],datapoint2[1],sever]
        return ID
    else:
        return [False]