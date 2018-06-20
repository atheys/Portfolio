# Intrusion module (implement Coordinate module)
# @author: Andreas Theys
# @version 4.0 by Andreas Theys
# Module for algebraic intrusion-detection and -processing .


# Basic mathematical functionality
from math import *
# Advanced mathematical functionality
from Math import *
# numpy for optimization purposes
from numpy import *


# Time and space margin specifications for intrusion analysis
# Vertical
global vMargin
vMargin = 50.
# Horizontal
global hMargin
hMargin = 250.


# Calculates the relative distance between two datapoints.
# Simulation datapoint conventions are at place
def rDistance(datapoint1,datapoint2):
    drel = array(datapoint2[2:5])-array(datapoint1[2:5])
    return drel


# Computes the relative velocity between two datapoints.
# Simulation datapoint conventions are at place
def rVelocity(datapoint1,datapoint2):
    vrel = array(datapoint2[5:8])-array(datapoint1[5:8])
    return vrel


# Computes time of vertical minimal distance / maximal horizontal severity
# NOTE[1]: mathematically speaking, always point-upward absolute value function 
def tMinusV(drel,vrel):
    x0 = drel[0]
    vx = vrel[0]
    return -1.*x0/vx


# Computes time of horizontal minimal distance / maximal horizontal severity
# NOTE[1]: mathematically speaking, always parabola with a>=0. 
def tMinusH(drel,vrel):
    y0,z0 = drel[1],drel[2]
    vy,vz = vrel[1],vrel[2]
    return -1.*(y0*vy+z0*vz)/(vy*vy+vz*vz)


# Evaluation function for time of minimal distance
# Note[1]: function is used to determine time of maximal severity
def evaluateTMin(interval,tmin):
    if tmin<=interval[0]:
        return interval[0]
    elif interval[0]<tmin and tmin<interval[1]:
        return tmin
    else:
        return interval[1]
  
  
# Determines time of maximal severity for a certain configuration  
def tMinus(drel,vrel,label,interval):
    if label == 'Iv': 
        return evaluateTMin(interval,tMinusV(drel,vrel))
    else:
        return evaluateTMin(interval,tMinusH(drel,vrel))
        
        
# Defines intervals for minimal severity checks
# NOTE[1]: intervals is a dictionary datatype
# NOTE[2]: t1<t2           
def intervalAssignment(ts,t1,t2,signs,intervals):
    if 0.<=t1:
        if ts<=t1:
            intervals[signs[0]].append([0.,ts])
        elif t1<ts and ts<t2:
            intervals[signs[0]].append([0.,t1])
            intervals[signs[1]].append([t1,ts])
        else:
            intervals[signs[0]].append([0.,t1])
            intervals[signs[0]].append([t2,ts])
            intervals[signs[1]].append([t1,t2])
    elif t1<0. and 0.<t2:
        if ts<=t1:
            # Edge case
            return intervals
        elif t1<ts and ts<t2:
            intervals[signs[1]].append([0.,ts])
        else:
            intervals[signs[1]].append([0.,t2])
            intervals[signs[2]].append([t2,ts])
    else:
        if ts<=t1:
            # Edge case
            return intervals
        elif t1<ts and ts<t2:
            # Edge case
            return intervals
        else:
            intervals[signs[2]].append([0.,ts])
    return intervals
        

# Computes severity of intrusion at a given time t
# Relative position and velocity required
def severity(drel,vrel,t):
    drel = drel+t*vrel
    x,y,z = drel[0],drel[1],drel[2]
    sever = min(1.-abs(x)/vMargin,1.-(y*y+z*z)/hMargin)
    """if sever <0.:
        return 0.
    else: 
        return sever"""
    return sever


# Main intrusion detection method.
# Simulation datapoint conventions are at place
def intrusion(datapoint1,datapoint2,ts):
    # Relative position & velocity
    drel,vrel = rDistance(datapoint1,datapoint2),rVelocity(datapoint1,datapoint2)
    # Relative coordinates & speed vector coordinates
    x0,y0,z0 = drel[0],drel[1],drel[2]
    vx,vy,vz = vrel[0],vrel[1],vrel[2]
    # Result variables
    t = []
    simInterval = [0.,float(ts)]
    labels = ['Iv','Ih']
    intervals = {'Iv':[],'Ih':[]} # dictionary
    # Quadratic evaluator coefficients
    a = 25*vx*vx-vy*vy-vz*vz
    b = 2*(25*x0*vx-y0*vy-z0*vz)
    c = 25*x0*x0-y0*y0-z0*z0
    # Discriminant     
    D = b*b-4*a*c
    # Discriminant evaluation
    if D<=0.:
        if c>=0. and a+b+c>=0:
            tmin = evaluateTMin(simInterval,tMinusV(drel,vrel))
            t.append(tmin)
        if c<=0. and a+b+c<=0.:
            tmin = evaluateTMin(simInterval,tMinusH(drel,vrel))
            t.append(tmin)
    else:
        roots = [(-b+sqrt(D))/(2.*a),(-b-sqrt(D))/(2.*a)]
        t1,t2 = min(roots),max(roots)
        signs = []
        if a>0.:
            signs = ['Iv','Ih','Iv']
        if a <0.:
            signs = ['Ih','Iv','Ih']
        intervals = intervalAssignment(ts,t1,t2,signs,intervals)
        labels = intervals.keys()
        for label in labels:
            for interval in intervals[label]:
                t.append(tMinus(drel,vrel,label,interval))
    severities = []
    for time in t:
        severities.append(severity(drel,vrel,time))
    sever = max(severities)
    tmax = t[severities.index(sever)]
    if sever>0.:    
        return [tmax,datapoint1[1],datapoint2[1],sever] 
    else:
        return [False]


# Direct evaluator 
def evaluator0(drel,vrel):
    dx,dy,dz = drel[0],drel[1],drel[2]
    return sqrt(dy*dy+dz*dz)<hMargin and abs(dx)<vMargin


# Directional evaluator
def evaluator1(drel,vrel):
    res = drel*vrel
    return (res[0]<=0. or drel[0]<50.) and \
    ((res[1]<=0. and res[2]<=0.) or sqrt(drel[1]*drel[1]+drel[2]*drel[2])<250.)


# Normalizator (XZ-plane)
def normalize(drel,vrel):
    theta = atan2(drel[1],drel[2])
    # Rotation x-axis
    drel = array([abs(drel[0]),0.,abs(sin(theta)*drel[1]+cos(theta)*drel[2])])
    vrel = array([-1.*abs(vrel[0]),\
    cos(theta)*vrel[1]-sin(theta)*vrel[2],\
    -1.*abs(sin(theta)*vrel[1]+cos(theta)*vrel[2])])
    return drel,vrel

# Angular evaluator
def evaluator2(drel,vrel):
    if drel[2]/hMargin<abs(vrel[2]/vrel[1]) and (drel[0]-vMargin)/hMargin<abs(vrel[0]/vrel[1]):
        return True
    else:
        return False

    
"""# Overall evaluation   
def evaluate(d1,d2):
    drel,vrel = rDistance(d1,d2),rVelocity(d1,d2)
    drelN,vrelN = normalize(drel,vrel)
    return evaluator0(drel,vrel) or (evaluator1(drel,vrel) and evaluator2(drelN,vrelN))
"""

def evaluate(d1,d2):
    drel = rDistance(d1,d2)
    vrel = rVelocity(d1,d2)
    
    # Edge case
    if evaluator0(drel,vrel):
        return True
    else:
        return evaluator1(drel,vrel)


# Computes number of intrusion
def nIntrusions(datapoints,ts):
    n = 0
    existing = []
    for i in arange(len(datapoints)):
        if i%100==0: print i
        for j in arange(i+1,len(datapoints)):
            answer = intrusion(datapoints[i],datapoints[j],ts)
            if answer != [False]:
                n+=1
                existing.append(answer)
    return n,len(existing)
    
    