# Module FlightPath
# @author: Andreas Theys
# @version 3.0 (numpy-optimized)
# This module mainly processes flight efficieny data
# Trajectory interpolation implemented here


# Imported modules
# Geographical methods
from Coordinate import *
# main mathematical functions
from math import *
# numpy for optimization purposes
from numpy import *


# Determines distance between 2 datapoints
# NOTE[1]: numpy array operators used for convenience/optimization
# NOTE[2]: time specs of datapoints must be indentical
def distance(datapoint1,datapoint2):
    vec = array(datapoint1[2:5])-array(datapoint1[5:8])
    vec = vec*vec
    d = sqrt(sum(vec))
    return d


# Determines distance traveled (DT) along flight path
# NOTE[1]: linear interpolation is used
# NOTE[2]: method distance(datapoint1,datapoint2) implemented
# Edge case: only 1 datapoint --> distance traveled 0.
def linearDT(listOfDPoints):
    result = 0.
    if(len(listOfDPoints)==1):
        return result
    else:
        for i in arange(len(listOfDPoints)-1):
            result += distance(listOfDPoints[i],listOfDPoints[i+1])
        return result


# Determines position and speed using spline interpolation
# NOTE[1]: spline coefficient list implemented
# NOTE[2]: spline conventions are used for 3rd degree polynomial
def determinePosVel(coeff,t):
    coeff,temp1,temp2 = array(coeff),array([t*t*t,t*t,t,1.]),array([3*t*t,2*t,1,0.])
    return sum(coeff*temp1),sum(coeff*temp2) 


# Interpolates position along established spline
# Edge case: t is starting time of datapoint
def interSpline(datapoint,t):
    # Edge case
    if(datapoint[0]==t):
        return datapoint
    elif(datapoint[0]<t):
        # Copy datapoint --> avoid data conflicts
        pseudo = list(datapoint)
        pseudo[0] = t
        x,vx = determinePosVel(datapoint[8][0],t)
        y,vy = determinePosVel(datapoint[8][1],t)
        z,vz = determinePosVel(datapoint[8][2],t)
        pseudo[2],pseudo[3],pseudo[4] = x,y,z
        pseudo[5],pseudo[6],pseudo[7] = vx,vy,vz
        return pseudo
    else:
        return []


# Computes total distance traveled (DT) along flight path
# NOTE[1]: flight path is linearly interpolated in smaal increments
# NOTE[2]: method distance(datapoint1,datapoint2) implemented
# Edge case: only 1 datapoint --> distance traveled 0.
def splineDT(listOfDPoints,precision):
    # Edge case
    if(len(listOfDPoints)==1):
        return 0.
    else:
        result = 0.
        for i in range(len(listOfDPoints)-1):
            dt = listOfDPoints[i+1][0]-listOfDPoints[i][0]
            incr,t = dt/precision,listOfDPoints[i][0]
            temp = listOfDPoints[i]
            for j in range(int(precision)):
                t += incr
                pseudo = interSpline(temp,t)
                result += distance(temp,pseudo)
                temp =pseudo
        return result


# Calculates minimum possible distance in flight path 
# NOTE[1]: distance between first and last datapoint
# NOTE[2]: method distance(datapoint1,datapoint2) implemented
# Edge case: only 1 datapoint --> distance traveled 0.
def minDistance(listOfDPoints):
    # Edge case
    if(len(listOfDPoints)==1):
        return 0.
    else:
        # First and last datapoint
        return distance(listOfDPoints[0],listOfDPoints[len(listOfDPoints)-1])


# Determines flight efficiency factor
# NOTE[1]: linear interpolation used
# NOTE[2]: method provides upper boundary
# NOTE[3]: offset in curvatious paths (consider spline interpolation!)
# Edge case: only 1 datapoint --> efficiency 0.
def linearFE(listOfDPoints):
    # Edge case
    if(minDistance(listOfDPoints)==0.):
        return 0.
    else:
        return minDistance(listOfDPoints)/linearDT(listOfDPoints)


# Determines flight efficiency factor
# NOTE[1]: spline interpolation used (3rd degree polynomial)
# NOTE[2]: used spline polynomial is questionable 
# Edge case: only 1 datapoint --> efficiency 0.
def splineFE(listOfDPoints):
    # Edge case
    if(minDistance(listOfDPoints)==0.):
        return 1.
    else:
        return minDistance(listOfDPoints)/splineDT(listOfDPoints,5.)


# Extracts datapoint existing at a certain time specification
# NOTE[1]: about 120 different time specs in 1 simulation file
# NOTE[2]: no offset margin inplemented --> might need revision
def dPoint(t,listOfDPoints):
    for i in arange(len(listOfDPoints)):
        if listOfDPoints[i][0]==t:
            return listOfDPoints[i]
    # No datapoint exists --> special list constructed
    return [False]


# Extracts all datapoints existing at a certain time specification
# NOTE[1]: method dPoint(t,listOfDPoints) implemented
def dPointsAtTimeT(t,sortedDPoints):
    result = []
    for i in arange(len(sortedDPoints)):
        pseudo = dPoint(t,sortedDPoints[i])
        if(pseudo!=[False]):
            result.append(pseudo)
    return result