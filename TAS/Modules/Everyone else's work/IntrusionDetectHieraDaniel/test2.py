import Intrusion
import Intrusionnumpy
import numpy as np
import random
import time
ts = 30.
random.seed(2)
n = 4000
x = np.zeros((n,3),dtype='float32')
v = np.zeros((n,3),dtype = 'float32')
t = np.asarray(np.zeros((n,1))+30.,dtype = 'float32')
ids = np.zeros((n,1),dtype = 'float32')

for i in range(n):
    for j in range(3):
        if j != 0:
            x[i,j]=random.uniform(-50000.,50000.)
            v[i,j]=random.uniform(-200.,200.)
        else:
            x[i,j]=random.uniform(1000.,5000.)
            v[i,j]=random.uniform(-20.,20.)
            
datapoints = np.append(t,ids,1)
datapoints = np.append(datapoints,x,1)
datapoints = np.append(datapoints,v,1)

ids = np.mat(datapoints[:,1])
x = np.mat(datapoints[:,2])
y = np.mat(datapoints[:,3])
z = np.mat(datapoints[:,4])

vx = np.mat(datapoints[:,5])
vy = np.mat(datapoints[:,6])
vz = np.mat(datapoints[:,7])

print 'setup done'

t0 = time.time()
intrusions1,intrusions2 = intrusions1,intrusions2 = Intrusionnumpy.intrusion(x,y,z,vx,vy,vz,ids)
print 'donevert'

#check the possible intrusions using the old non-numpy method
ts = 30.
intrusionlist = []
print len(intrusions1)
for i in range(len(intrusions1)):
    dp1 = intrusions1[i]
    dp2 = intrusions2[i]
    intrusion = Intrusion.intrusion(dp1,dp2,ts)
    if intrusion[0] == True:
                print intrusion
t1 = time.time()
print t1-t0
