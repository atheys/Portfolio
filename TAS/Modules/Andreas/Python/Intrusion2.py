
#from Intrusion import *
from math import *
from numpy import *

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


def intersect(c1,c2):
    if c1==[] or c2==[]:
        return []
    minimum = max(min(c1),min(c2))
    maximum = min(max(c1),max(c2))
    if maximum<minimum:
        return []
    else:
        return [minimum,maximum]


def detectIntrusion(d1,d2,ts):
    drel,vrel = rDistance(d1,d2),rVelocity(d1,d2)
    x = drel[0]
    vx = vrel[0]
    tv1,tv2 = min((-50.-x)/vx,(50.-x)/vx),max((-50.-x)/vx,(50.-x)/vx)
    inter1 = intersect([0.,ts],[tv1,tv2])
    if inter1 !=[]:
        y,z = drel[1],drel[2]
        vy,vz = vrel[1],vrel[2]
        a,b,c = vy*vy+vz*vz, 2*(y*vy+z*vz), y*y+z*z-62500.
        D = b*b-4*a*c
        if D<=0.:
            return False
        else:
            roots = [(-b+sqrt(D))/(2.*a),(-b-sqrt(D))/(2.*a)]
            hor = [min(roots),max(roots)]
            result = intersect(inter1,hor)
            if result==[]:
                return False
            else:
                print result
                return True
    else:
        return False


