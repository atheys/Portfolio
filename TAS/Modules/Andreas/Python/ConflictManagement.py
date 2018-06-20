# Module ConflicManagement
# @author: Andreas Theys
# @version 4.0
# This module mainly processes flight intrusions and conflicts
# NOTE[1]: version modified for new detection methodology


# Imported modules
# Basic math functions
from math import *
# Geographic methods
from Coordinate import *
# Intrusion detection method
from IntrusionTemp import *


# Evaluates intrusion ID to a list of already known intrusions
# NOTE[1]: a new intrusion ID is added
# NOTE[2]: an already existing ID is updated
# NOTE[3]: void method --> no return value
# Edge case: ID list is empty
def evalIntrusionID(ID, existingIDs):
    # Edge Case 
    if(len(existingIDs)==0):
        existingIDs.append(ID)
        return existingIDs
    # Evaluating variables
    index = -1
    indicator = False
    # Loop through all existing intrusions known
    for i in range(len(existingIDs)):
        if((ID[1]==existingIDs[i][1] and ID[2]==existingIDs[i][2]) or \
        (ID[1]==existingIDs[i][2] and ID[2]==existingIDs[i][1])):
            # Adjust evaluating variables
            indicator = True
            index = i
    if(indicator):
        # Define new ID in existing list
        if(ID[3]>existingIDs[index][3]):
                existingIDs[index][0]= ID[0]
                existingIDs[index][3]= ID[3]
    else:
        existingIDs.append(ID)
    return


# Evaluates conflict ID to a list of already known intrusions
# NOTE[1]: a new conflict ID is added
# NOTE[2]: an already existing ID is left unharmed
# NOTE[3]: void method --> no return value
# Edge case: ID list is empty
def evalConflictID(ID, existingIDs):
    # Edge Case
    if(len(existingIDs)==0):
        existingIDs.append(ID)
        return existingIDs
    # Evaluator
    indicator = False
    # Loop through all existing intrusions known
    for i in range(len(existingIDs)):
        if((ID[0]==existingIDs[i][0] and ID[1]==existingIDs[i][1]) or \
        (ID[0]==existingIDs[i][1] and ID[1]==existingIDs[i][0])):
            indicator = True
    if not(indicator):
        existingIDs.append(ID)
    return


# Computes number of intrusions in a certain time-interval
# NOTE[1]: input is list of datapoints existing at time t0
# NOTE[2]: number of combinations = n!/(n-2!)/2
# NOTE[3]: precision figure added for hybrid intrusion detection method 
# NOTE[4]: void method --> no return value
def nIntrusions(dPointsAtT,existingIDs,ts,precision):
    n = 0
    # Loop through all combinations
    for i in arange(len(dPointsAtT)):
        for j in arange(i+1,len(dPointsAtT)):
            # Intrusiont detection method
            intrusion = evaluate(dPointsAtT[i],dPointsAtT[j])
            if intrusion:
                n+=1
                drel = rDistance(dPointsAtT[i],dPointsAtT[j]) # numpy
                vrel = rVelocity(dPointsAtT[i],dPointsAtT[j]) # numpy
                s_vert = abs(vrel[0])
                s_hor = sqrt(vrel[1]*vrel[1]+vrel[2]*vrel[2])
                step = precision*min(250./s_hor,50./s_vert)
                sever = 0.
                for k in arange(0.,ts,step):
                    sever = compute(drel,vrel,k)
                    if sever>0.:
                        ID = [dPointsAtT[i][0]+k,dPointsAtT[i][1],dPointsAtT[j][1],sever]
                        evalIntrusionID(ID, existingIDs)   
    print n
    print len(existingIDs)
    print existingIDs
    return
 
  
# Computes number of conflicts in a certain time-interval
# NOTE[1]: linear interpolation is used
# NOTE[2]: number of combinations = n!/(n-2!)/2
# NOTE[3]: void method --> no return value
def nConflicts(dPointsAtT,existing,tAhead):
    # Copy to prevent data conflicts
    dpoints = list(dPointsAtT)
    # Revectorize datapoints --> linear interpolation
    for point in dpoints:
        point[2], point[3], point[4] = lookAhead(point,tAhead)
    # Loop through all combinations
    for i in range(len(dpoints)):
        for j in range(i+1,len(dpoints)):
            drel = rDistance(dPointsAtT[i],dPointsAtT[j])
            sever = severity(drel)
            if sever>0.:
                ID = [dPointsAtT[i][1],dPointsAtT[j][1]]
                evalConflictID(ID,existing)
    return 