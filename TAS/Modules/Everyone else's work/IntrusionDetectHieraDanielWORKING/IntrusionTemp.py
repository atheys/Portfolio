# IntrusionExtension module (implement Coordinate module)
# @author: Andreas Theys
# @version 4.0 by Andreas Theys
# Module for algebraic intrusion-detection and -processing .

from math import *
from Math import *
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
    drel = array(datapoint2[2:5])-array(datapoint1[2:5])
    return drel

# Computes the relative velocity between two datapoints.
# Simulation datapoint convetions are at place
def rVelocity(datapoint1,datapoint2):
    vrel = array(datapoint2[5:8])-array(datapoint1[5:8])
    return vrel

def norm(vector):
    return sqrt(sum(vector*vector))

def angle(vector1,vector2):
    return acos(sum(vector1*vector2)/(norm(vector1)*norm(vector2)))

def evaluator00(drel,vrel,ts):
    d = array([hZone,vZone])
    return norm(drel)-ts*norm(vrel)<norm(d)
    
def evaluator0(drel,vrel):
    dx,dy,dz = drel[0],drel[1],drel[2]
    return sqrt(dy*dy+dz*dz)<hZone and abs(dx)<vZone

def evaluator1(drel,vrel):
    res = drel*vrel
    return (res[0]<=0. or drel[0]<50.) and \
    ((res[1]<=0. and res[2]<=0.) or sqrt(drel[1]*drel[1]+drel[2]*drel[2])<250.)


def normalize(drel,vrel):
    theta = atan2(drel[1],drel[2])
    # Rotation x-axis
    drel = array([abs(drel[0]),0.,abs(sin(theta)*drel[1]+cos(theta)*drel[2])])
    vrel = array([-1.*abs(vrel[0]),\
    cos(theta)*vrel[1]-sin(theta)*vrel[2],\
    -1.*abs(sin(theta)*vrel[1]+cos(theta)*vrel[2])])
    return drel,vrel

def evaluator2(drel,vrel):
    #if drel[2]<=50.:
     #   return True
    #elif (drel[2]-vZone)/hZone<abs(vrel[2]/vrel[1]):
    if drel[2]/hZone<abs(vrel[2]/vrel[1]) and (drel[0]-vZone)/hZone<abs(vrel[0]/vrel[1]):
        return True
    else:
        return False
 
 
def reduce_vector(vector):
    return array([vector[2],vector[0]])
 
def situation(drel):
    if drel[0]<=hZone and drel[1]<=vZone:
        return 0
    elif drel[0]<=hZone:
        return 3
    elif drel[1]<=vZone:
        return 1
    else:
        return 2

def scenario1(drel,vrel):
    point1,point2 = array([-hZone,vZone])-drel,array([hZone,vZone])-drel
    down = array([0.,-1.])
    control,rotate = angle(point1,point2),angle(point2,down)
    T1 = array([cos(rotate),sin(rotate)])
    T2 = array([-sin(rotate),cos(rotate)])
    vrel = array([sum(vrel*T1),sum(vrel*T2)])
    if(vrel[0]<0.) and (vrel[1]<0.) and angle(vrel,down)<control:
        return True
    else:
        return False
    
def scenario2(drel,vrel):
    point1 = array([-hZone,vZone])-drel
    point3 = array([hZone,-vZone])-drel
    down = array([0.,-1.])
    control = angle(point1,point3)
    rotate = angle(point3,down)
    T1 = array([cos(rotate),-sin(rotate)])
    T2 = array([sin(rotate),cos(rotate)])
    vrel = array([sum(vrel*T1),sum(vrel*T2)])
    vrel_angle = angle(vrel,down)
    if(vrel[0]<0.) and (vrel[1]<0.) and vrel_angle<control:
         return True     
    else:
        return False
        
def scenario3(drel,vrel):
    point2,point3 = array([hZone,vZone])-drel,array([hZone,-vZone])-drel
    down = array([0.,-1.])
    control = angle(point2,point3)
    rotate = angle(point3,down)
    T1 = array([cos(rotate),-sin(rotate)])
    T2 = array([sin(rotate),cos(rotate)])
    vrel = array([sum(vrel*T1),sum(vrel*T2)])
    if(vrel[0]<0.) and (vrel[1]<0.) and angle(vrel,down)<control:
        return True
    else:
        return False 


def evaluator3(drel,vrel):
    sit = situation(drel)
    if sit==0:
        return True
    if sit==1:
        evaluate = scenario1(drel,vrel)
        return evaluate
    if sit==2: 
        evaluate = scenario2(drel,vrel)
        return evaluate
    if sit==3:
        evaluate = scenario3(drel,vrel)
        return evaluate
    else:
        return False


def evaluate(d1,d2):
    drel = rDistance(d1,d2)
    vrel = rVelocity(d1,d2)
    # Edge case --> very slow
    # if evaluator00(drel,vrel):
    # return False,0.
    
    # Edge case
    if evaluator0(drel,vrel):
        return True
    else:
        if evaluator1(drel,vrel):
            drel,vrel = normalize(drel,vrel)
            eval2 = evaluator2(drel,vrel)
            if(eval2):
                drel,vrel = reduce_vector(drel),reduce_vector(vrel)
                eval3 = evaluator3(drel,vrel)
                return eval3
            else:
                return False
        else:
            return False

def severity(drel):
    dv,dh = abs(drel[0]),sqrt(drel[1]*drel[1]+drel[2]*drel[2])
    if dv<vZone and dh<hZone:
         return min(1.-dh/hZone,1.-dv/vZone)
    else:
        return 0.
        
def compute(drel,vrel,ts):
    drel = drel+ts*vrel
    return severity(drel)