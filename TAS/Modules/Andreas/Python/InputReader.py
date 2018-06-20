# InputReader Module
# @author: Andreas Theys
# @ version 6.0
# This module is the main processing unit for the data input stream.


# Imported modules 
# Basic math functionality
from math import *
# Sorting procedures of the datapoints
from Sort import *
# Processing of geographical data
from Coordinate import *
from FlightPath import *


# Filters dataset from 49 to 8 entities
# Provides correct data value conversions
# NOTE[1]: all type casting happens in this method
# NOTE[2]: massively important for further data handling
def filterData(datapoint):
    datapoint = list(datapoint)
    if len(datapoint) == 49:
        datapoint = datapoint[0:13]
        del datapoint[10]
        del datapoint[8]
        del datapoint[7]
        del datapoint[3]
        del datapoint[2]
        datapoint[0] = float(datapoint[0])                      # Time [s]
        datapoint[1] = str(datapoint[1]).replace(" ","")        # ID [] 
        datapoint[2] = (pi/180.)*float(datapoint[2])            # Latitude [rad]
        datapoint[3] = (pi/180.)*float(datapoint[3])            # Longitude [rad]
        datapoint[4] = 0.3048*float(datapoint[4])               # Altitude [m]
        temp1 = 0.51444444*float(datapoint[5])
        temp2 = (pi/180.)*float(datapoint[7])
        datapoint[5] = (0.3048/60.)*float(datapoint[6])         # Vx 
        datapoint[6] = temp1*sin(temp2)                         # Vy
        datapoint[7] = temp1*cos(temp2)                         # Vz
        # WSG84 coordinate transformation
        datapoint[2],datapoint[3],datapoint[4] = convertToXY2(datapoint[2],datapoint[3],datapoint[4])
        return datapoint


# Filters dataset from 49 to 8 entities
# Provides correct data value conversions
# NOTE[1]: all type casting happens in this method
# NOTE[2]: massively important for further data handling
def filterData2(datapoint):
    datapoint = list(datapoint)
    datapoint[0] = float(datapoint[0])      # Time [s]
    datapoint[1] = str(datapoint[1])        # ID [] 
    datapoint[2] = float(datapoint[2])      # Latitude [rad]
    datapoint[3] = float(datapoint[3])      # Longitude [rad]
    datapoint[4] = float(datapoint[4])      # Altitude [m]
    datapoint[5] = float(datapoint[5])      # Vx 
    datapoint[6] = float(datapoint[6])      # Vy
    datapoint[7] = float(datapoint[7])      # Vz
    return datapoint


# Processes line directly read in from datafile 
# Helper function
# NOTE[1]: function established for optimization purposes
def process(point,datapointsFixed):
        point = str(point)
        point = point.split(',')
        for value in point:
            value = str(value).replace(" ","")
        point = filterData(point) 
        datapointsFixed.append(point)
        return
        

# Processes line directly read in from datafile 
# Helper function
# NOTE[1]: function established for optimization purposes
def process2(point,datapointsFixed):
        point = str(point)
        point = point.split(',')
        for value in point:
            value = str(value).replace(" ","")
        point = filterData2(point) 
        datapointsFixed.append(point)
        return


# Main read-in method for datafiles
# Reads in data file and converts lines into filtered datapoints
def read(name):
    name = str(name)
    try:
        File = open(name,'r') 
    except ValueError:
        print "Wrong file!"
    datapoints = File.readlines()
    File.close()
    datapointsFixed = []
    map(lambda x: process(x,datapointsFixed),datapoints)
    IDs, times, sortedDPoints = sortByID(datapointsFixed)
    speeds(sortedDPoints)
    return IDs, times, sortedDPoints
    
    
# Main read-in method for datafiles
# Reads in data file and converts lines into filtered datapoints
def read2(name):
    name = str(name)
    try:
        File = open(name,'r') 
    except ValueError:
        print "Wrong file!"
    datapoints = File.readlines()
    File.close()
    datapointsFixed = []
    map(lambda x: process2(x,datapointsFixed),datapoints)
    IDs, times, sortedDPoints = sortByID2(datapointsFixed)
    return IDs, times, sortedDPoints

