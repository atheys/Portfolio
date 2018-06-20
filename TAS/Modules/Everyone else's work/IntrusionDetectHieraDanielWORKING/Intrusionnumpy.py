# Intrusion module
# @author: Hiera Sanghera, Daniel Willemsen and Andreas Theys
# @version 2.0
# Module for algebraic intrusion-detection and -processing .

from math import *
import numpy as np
import scipy
# Time and space specs for intrusions

global vZone
vZone = 50.
global hZone
hZone = 250.
#creates matrix of relative things
def relative(p,q):
    pr = p - q.T
    return pr


# Method for the detection of vertical intrusion
def detect_vert_self(xrel,vxrel,ts):
    
    #Convert arrays and avoid intrusion detected with itself
    xrelar = np.asarray(xrel+np.identity(len(xrel))*9999999.)
    vxrelar =np.asarray(vxrel+np.identity(len(xrel))*0.00000001)
    
    # Calculate the times at which the aircraft enters the zone (t1) or exits (t2)
    ta = (-xrelar-vZone)/vxrelar
    tb = (-xrelar+vZone)/vxrelar
    b = ta<tb
    t1 = b*ta+np.invert(b)*tb
    t2 = b*tb+np.invert(b)*ta

    
    #create matrix with True if an plane enters, exits or remains in vertical intrusion zone
    intrusion = np.logical_or(np.logical_or(np.logical_and(t1>=0.,t1<=ts),np.logical_and(t2>=0.,t2<=ts)),abs(xrelar)<vZone)

    return intrusion

# Method for the detection of vertical intrusion
def detect_vert_other(xrel,vxrel,ts):
    #Convert to arrays
    xrelar = np.asarray(xrel)
    vxrelar= np.asarray(vxrel)
    
    # Calculate the times at which the aircraft enters the zone (t1) or exits (t2)
    ta = (-xrelar-vZone)/vxrelar
    tb = (-xrelar+vZone)/vxrelar
    b = ta<tb
    t1 = b*ta+np.invert(b)*tb
    t2 = b*tb+np.invert(b)*ta

    #create matrix with True if an plane enters, exits or remains in vertical intrusion zone
    intrusion = np.logical_or(np.logical_or(np.logical_and(t1>=0.,t1<=ts),np.logical_and(t2>=0.,t2<=ts)),abs(xrelar)<vZone)

    return intrusion


# VerticalIntrusion determination in 1 timestep, returns list of possible intrusions
def intrusion(x,y,z,vx,vy,vz,ids):
    #initiation
    ts = 30.
    intrusions1 = []
    intrusions2 = []
   
    #when necassery (matrix too big), split up for memory
    if max(x.shape)>2000:
        x1 = x[0,:1500]
        x2 = x[0,1500:]
        vx1 = vx[0,:1500]
        vx2 = vx[0,1500:]
        
        #1 with itself
        
        #calculate matrix of relative velocities
        xrel = relative(x1,x1)
        vxrel = relative(vx1,vx1)
        
        #detect possible intrusions
        intrusion_vert = detect_vert_self(xrel,vxrel,ts)

        #append them to the list
        for i in range(len(intrusion_vert[0])):
            for j in range(i):
                if intrusion_vert[i,j]:
                    intrusions1.append([ts,ids[0,i],x[0,i],y[0,i],z[0,i],vx[0,i],vy[0,i],vz[0,i]])
                    intrusions2.append([ts,ids[0,j],x[0,j],y[0,j],z[0,j],vx[0,j],vy[0,j],vz[0,j]])

        #2 with itself

        #calculate matrix of relative velocities            
        xrel = relative(x2,x2)
        vxrel = relative(vx2,vx2)
        
        #detect possible intrusions
        intrusion_vert = detect_vert_self(xrel,vxrel,ts)

        #append them to the list
        for i in range(len(intrusion_vert[0])):
            for j in range(i):
                if intrusion_vert[i,j]:
                    intrusions1.append([ts,ids[0,i+1500],x[0,i+1500],y[0,i+1500],z[0,i+1500],vx[0,i+1500],vy[0,i+1500],vz[0,i+1500]])
                    intrusions2.append([ts,ids[0,j+1500],x[0,j+1500],y[0,j+1500],z[0,j+1500],vx[0,j+1500],vy[0,j+1500],vz[0,j+1500]])

        # 2 with 1

        #calculate matrix of relative velocities
        xrel = relative(x1,x2)
        vxrel = relative(vx1,vx2)

        #detect possible intrusions
        intrusion_vert = detect_vert_other(xrel,vxrel,ts)

        #append them to the list
        for i in range(max(x1.shape)):
            for j in range(max(x2.shape)):
                if intrusion_vert[j,i]:
                    intrusions1.append([ts,ids[0,i],x[0,i],y[0,i],z[0,i],vx[0,i],vy[0,i],vz[0,i]])
                    intrusions2.append([ts,ids[0,j+1500],x[0,j+1500],y[0,j+1500],z[0,j+1500],vx[0,j+1500],vy[0,j+1500],vz[0,j+1500]])
    #if the dataset is small enough, do it in 1 matrix:
    else:
        #calculate matrix of relative velocities
        xrel = relative(x,x)
        vxrel = relative(vx,vx)
        
        #detect possible intrusions
        intrusion_vert = detect_vert_self(xrel,vxrel,ts)
        #append them to the list
        for i in range(len(intrusion_vert[0])):
            for j in range(i):
                if intrusion_vert[i,j]:
                    intrusions1.append([ts,ids[0,i],x[0,i],y[0,i],z[0,i],vx[0,i],vy[0,i],vz[0,i]])
                    intrusions2.append([ts,ids[0,j],x[0,j],y[0,j],z[0,j],vx[0,j],vy[0,j],vz[0,j]])
    #return the intrusionlists
    return intrusions1,intrusions2
