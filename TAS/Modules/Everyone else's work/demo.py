import Intrusion
import numpy as np

ts = 30.

#CASE 1. 
datapoint1 = [30.,'A',0., 0., 0.,0., 0., 0.,]
datapoint2 = [30.,'B',400., 0., 0.,-100., 0., 0.]

intrusion = Intrusion.intrusion(datapoint1,datapoint2,ts)
print intrusion
raw_input()

#CASE 2. #CASE 1 met beetje afwijking
pos1 = np.array([0., 0., 0.])
pos2 = np.array([1000., 0., 0.])

v1 = np.array([100., 25., 0.1])
v2 = np.array([0., 0., 0.])

intrusion = Intrusion.intrusion(pos1,pos2,v1,v2)
print intrusion
raw_input()

#CASE 2.
pos1 = np.array([0., 0., 0.])
pos2 = np.array([0., 10., 1000.])

v1 = np.array([0., 0., 100.])
v2 = np.array([0., 0., 0.])

intrusion = Intrusion.intrusion(pos1,pos2,v1,v2)
print intrusion
raw_input()
