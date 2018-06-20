# Sort Module
# @author: Andreas Theys
# @version 3.0 (numpy-optimized)
# This module functions as an extension for the InputReader module.
# In the data filtering process, the important data value are sorted 
# and extrapolated using methods from this module. 


# Imported modules
# numpy for optimization purposes --> questionable
from numpy import *


# Determines all IDs appearing in the datafile
# Returns list of IDs.
# NOTE[1]: method indirectly used in read-method of InputReader
def listOfAllIDs(datapoints):
    IDs = []
    for i in arange(len(datapoints)):
        IDs.append(datapoints[i][1])
    IDs = unique(IDs).tolist()    
    return IDs
      
      
# Normalizes list of time specifications
# NOTE[1]: convenient for data analysis purposes 
def normalizeTimes(timeList):
    minimum = timeList[0]
    timeList = (array(timeList)-minimum).tolist()
    return timeList,minimum  

    
# Determines all time specs appearing in the datafile
# Returns list of times.
# NOTE[1]: method indirectly used in read-method of InputReader   
def listOfAllTimes(datapoints):
    times = []
    for i in arange(len(datapoints)):
        times.append(datapoints[i][0])
    times = unique(times).tolist()
    times.sort()
    times,minimum = normalizeTimes(times)
    return times,minimum
    

# Normalizes time specifications for all datapoints
# NOTE[1]: datapoints must be in sorted/conventional configuration
# NOTE[2]: void method --> no return value
def normTimesOverall(minimum,datapoints):
    for i in arange(len(datapoints)):
            datapoints[i][0] -= minimum
    return
  
  
# Sorts list of raw datapoints in conventional configuration
# NOTE[1]: method directly used in read-method of InputReader
def sortByID(datapoints):
    IDs = listOfAllIDs(datapoints)
    times,minimum = listOfAllTimes(datapoints)
    normTimesOverall(minimum,datapoints)
    result = []
    for i in arange(len(IDs)):
        result.append([])
    for i in arange(len(datapoints)):
        index = IDs.index(datapoints[i][1])
        result[index].append(datapoints[i])
    return IDs,times,result