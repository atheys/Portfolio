# mainSimulation module
# @author: Andreas Theys
# @version 3.0 (attempt at optimization)
# Assembly module of all previous work done
# Main processing unit for all data involved in project


# Import modules
# Read in data
from InputReader import *
# Traffic density analysis
from TrafficManagement import *
# Intrusion detection methodology
from ConflictManagement import *
# Flight efficiency analysis
from FlightPath import *
# Optimization --> questionable
from numpy import *
import time

# Sublabels for datafiles
# Air traffic concepts
Concept = ["Layers","FullMix"]
# Air traffic density levels
ATdensities = ["Low","Medium","High","UltraHigh"]
# Daytime
Daytime = ['Morning','Lunch','Evening']
# Daytime
Number = ['1','2']


# Simulation run for one particular dataset
# Loggers in console (temporary solution)
def run(concept, density, daytime, number):
    t0 = time.time()
    name = '/Users/andreastheys/Documents/TAS_data/'\
    +concept+'/'+density+'/'+daytime+'/'+number+'.csv' 
    print 'Filename created.\n',name    
    t1 = time.time()
    print 'Time: ',t1-t0
    
    IDs, times, sortedDPoints = read(name)
    print 'Datafile read in.'
    t2 = time.time()
    print 'Time: ',t2-t1
    
    efficiencies = []
    for point in sortedDPoints:
        efficiencies.append(linearFE(point))
    print 'Datapoints sorted in time.'
    t3 = time.time()
    print 'Time: ',t3-t2
    
    nameCON = '/Users/andreastheys/Documents/TAS_data/'+concept+'/'+density+'/'\
    +daytime+'/FE'+number+'.txt'  
    storageFile = open(nameCON,'w')
    
    for eff in efficiencies:
        if eff !=0.:
            storageFile.write(str(eff)+'\n')
    storageFile.close()
    print 'Data stored.'
    t4 = time.time()
    print 'Time: ',t4-t3
    print " " 


# Simulation run for one particular dataset
# Loggers in console (temporary solution)
def run2(concept, density, daytime, number):
    t0 = time.time()
    name = '/Users/andreastheys/Documents/TAS_data/'\
    +concept+'/'+density+'/'+daytime+'/'+number+'.csv' 
    print 'Filename created.\n',name    
    t1 = time.time()
    print 'Time: ',t1-t0
    
    IDs, times, sortedDPoints = read(name)
    print 'Datafile read in.'
    t2 = time.time()
    print 'Time: ',t2-t1
   
    name2 = '/Users/andreastheys/Documents/TAS_data/'\
    +concept+'/'+density+'/'+daytime+'/'+number+number+number+'.txt'
    print 'Filename created.\n',name2
    t3 = time.time()
    print 'Time: ',t3-t2
    
    output = open(name2,'w')
    for dlist in sortedDPoints:
        for item in dlist:
            string = str(item[0])+','+str(item[1])+','+str(item[2])+','+str(item[3])+\
            ','+str(item[4])+','+str(item[5])+','+str(item[6])+','+str(item[7])+'\n'
            output.write(string)
    output.close()
    print 'Output file stored.'
    t4 = time.time()
    print 'Time: ',t4-t3
    print " "

tijd1 = time.time()
for item1 in Concept:
    for item2 in ATdensities:
        for item3 in Daytime:
            for item4 in Number:
                run2(item1,item2,item3,item4)
tijd2 = time.time()
print "Simulation done in: ",tijd2-tijd1," seconds"