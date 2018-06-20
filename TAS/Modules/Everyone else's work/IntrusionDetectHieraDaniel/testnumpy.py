import numpy as np
import Intrusionnumpy
import Intrusion
x = np.mat([0.,0.,1.])
vx = np.mat([0.,0.,0.])

y = np.mat([0.,0.,0.])
vy = np.mat([0.,0.,0.])
z = np.mat([0.,0.,0.])
vz = np.mat([0.,0.,0.])
ids =np.mat([0.,0.,0.])

#find possible intrusions using only vertical intrusion detection
intrusions1,intrusions2 = Intrusionnumpy.intrusion(x,y,z,vx,vy,vz,ids)
print 'donevert'

#check the possible intrusions per datapoint.
ts = 30.
intrusionlist = []
for i in range(len(intrusions1)):
    dp1 = intrusions1[i]
    dp2 = intrusions2[i]
    intrusion = Intrusion.intrusion(dp1,dp2,ts)
    intrusionlist.append(intrusion)
print intrusionlist
