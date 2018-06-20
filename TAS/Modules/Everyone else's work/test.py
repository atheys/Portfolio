import Intrusion
import numpy as np
import random

n = 100
x = np.zeros((n,3))
v = np.zeros((n,3))
for i in range(n):
    for j in range(3):
        if j != 3:
            x[i,j]=random.uniform(-500.,5000.)
            v[i,j]=random.uniform(-200.,200.)
        else:
            x[i,j]=random.uniform(1000.,50000.)
            v[i,j]=random.uniform(-20.,20.)
            

for i in range(n):
    for k in range(n):
        if  k%100==0:
            print i
        if i!=k:
            x1 = x[i]
            x2 = x[k]
            v1 = v[i]
            v2 = v[k]
            intrusion,severity = Intrusion.intrusion(x1,x2,v1,v2)
            if intrusion == True:
                print severity
