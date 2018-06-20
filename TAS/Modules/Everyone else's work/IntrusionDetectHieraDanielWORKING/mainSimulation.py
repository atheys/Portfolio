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


# Sublabels for datafiles
# Air traffic density levels
ATdensities = ["Low","Medium","High","UltraHigh"]
# Air traffic concepts
Concept = ["Layers","FullMix"]
# Daytime
Daytime = ['Morning','Lunch','Evening']
# Daytime
Number = ['1','2']


# Look-ahead time
tAhead1 = 30.
tAhead2 = 45.
tAhead3 = 60.
tAhead4 = 90.
tAhead5 = 300.
timeLookAheadList = [tAhead1,tAhead2,tAhead3,tAhead4,tAhead5]


# Conflict analysis unit
def analyze(t,tA,sDP,exInt,exCon,dat):
    # Datapoints at time t
    datapoints = dPointsAtTimeT(t,sDP)
    # Air traffic density
    rho = density(t,sDP)
    # Intrusions
    intrusions = nIntrusions(datapoints,exInt,ts)
    # Conflicts
    conflicts = nConflicts(datapoints,exCon,tA)
    dat.append([t,rho,intrusions,conflicts])
    return

# FLight efficiency analysis unit
def efficiency(trajectory,efficiencies):
    FE1 = linearFE(trajectory)
    FE2 = splineFE(trajectory)
    efficiencies.append([FE1,FE2])
    return

# Simulation run for one particular dataset
def run(pList):
