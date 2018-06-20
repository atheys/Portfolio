import InputReader
import FlightPath
import Intrusion
import Intrusionnumpy as Intrusionnumpy
import numpy as np
import time
Ids, times, sortedDPoints = InputReader.read('1.csv')
print 'donereading'
completeintrusionlist=[]
print len(times)
for timestep in range(len(times)):
    #raw_input()
    #setup data
    t0 = time.time()
    data = np.asarray(FlightPath.dPointsAtTimeT(times[timestep],sortedDPoints))
    t = np.mat(data[:,0].astype('float'))
    IDs = np.mat(data[:,1])
    x = np.mat(data[:,2].astype('float'))
    #print len(x[:,0])
    x = x-x[0,0]
    y = np.mat(data[:,3].astype('float'))
    y = y-y[0,0]
    z = np.mat(data[:,4].astype('float'))
    z = z-z[0,0]
    vx = np.mat(data[:,5].astype('float'))
    vx = vx-vx[0,0]
    vy = np.mat(data[:,6].astype('float'))
    vy = vy-vy[0,0]
    vz = np.mat(data[:,7].astype('float'))
    vz = vz-vz[0,0]
    #raw_input()
    #do vertical intrusion
    #print len(np.asarray(x[0])[0,:])
    intrusions1,intrusions2 = Intrusionnumpy.intrusion(x,y,z,vx,vy,vz,IDs)
    #print 'donevert'
    #find the real intrusions with severities
    ts = 30.
    print len(intrusions1)
    intrusionlist = []
    #print len(intrusions1)
    for i in range(len(intrusions1)):
        dp1 = intrusions1[i]
        dp2 = intrusions2[i]
        intrusion = Intrusion.intrusion(dp1,dp2,ts)
        if intrusion[0] == True:
                print intrusion
                intrusionlist.append(intrusion)
    print len(intrusionlist)
    #add these intrusions to the complete list
    completeintrusionlist.append(intrusionlist)
    #time it took
    t1 = time.time()
    print t1-t0
    t0 = t1
    #how much percent we are done
    print float(timestep)/float(len(times))*100.
