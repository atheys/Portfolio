import pickle
import numpy as np
from math import *

#Inputs
R=2.1
hf = 1.5

#Shear Flow Superposition
#Shear Superposition

fileObject = open("Shear_analytical",'r')
shear = pickle.load(fileObject)

qsh=list(np.array(shear[0]))
qsv=list(np.array(shear[1]))
qfh=list(np.array(shear[2]))
qfv=list(np.array(shear[3]))

fileObject = open("Coordinates",'r')
coordinates = pickle.load(fileObject)

y=list(np.array(coordinates[1]))
x=list(np.array(coordinates[0]))
yf=list(np.array(coordinates[3]))
xf=list(np.array(coordinates[2]))
z=list(np.array(coordinates[6]))

fileObject = open("Torsion_Shear_analytical",'r')
torsion = pickle.load(fileObject)

qtt=[seq[0] for seq in torsion]
qtb=[seq[1] for seq in torsion]
qtf=[seq[2] for seq in torsion]

qs = []
qsf = []
q = []
qf = []

for j in xrange(len(z)):
    qs_temp = np.zeros((1,len(x)))
    qs_temp = np.ndarray.tolist(qs_temp[0])
    qs.append(qs_temp)
    
    qsf_temp = np.zeros((1,len(xf)))
    qsf_temp = np.ndarray.tolist(qsf_temp[0])
    qsf.append(qsf_temp)
    
    q_temp = np.zeros((1,len(x)))
    q_temp = np.ndarray.tolist(q_temp[0])
    q.append(q_temp)
    
    qf_temp = np.zeros((1,len(xf)))
    qf_temp = np.ndarray.tolist(qf_temp[0])
    qf.append(qf_temp)
    
    for i in xrange(len(x)):
        #Quarter I
        if x[i]>= 0.0 and y[i]>=R:
            if i<(len(x)-1):
                qs[j][i]=qsh[j][i]-qsv[j][i+len(x)/4]
            else:
                qs[j][i]=qsh[j][i]-qsv[j][len(x)/4]
        
        #Quarter II
        elif x[i]< 0.0 and y[i]>=R:
            qs[j][i]=qsh[j][i]+qsv[j][i+len(x)/4]
        
        #Quarter III
        elif x[i]< 0.0 and y[i]<R:
            qs[j][i]=qsv[j][i+len(x)/4]-qsh[j][i]
        
        #Quarter IV
        elif x[i]>= 0.0 and y[i]<R:
            qs[j][i]=-qsv[j][i-3/4*len(x)]-qsh[j][i]
            
        else:
            qs[j][i]=42
            
    for i in xrange(len(xf)):
        #Floor x<0
        if xf[i] <= 0:
            qsf[j][i]=-qfh[j][i]-qfv[j][i]
        
        #Floor x>0
        elif xf[i] > 0:
            qsf[j][i]=qfv[j][i]-qfh[j][i]
        else:
            qsf[j][i]=42
        
    #Torsion + Shear
    #Skin
    for i in xrange(len(x)):
        #Top
        if y[i]>hf:
            q[j][i] = (qs[j][i] + qtt[j])
    
        #Bottom
        elif y[i]<hf:
            q[j][i] = qs[j][i] + qtb[j]
    
        #Floor
    for i in xrange(len(xf)):
            qf[j][i] = qsf[j][i] + qtf[j]

shear = [q, qf]

"""shear = []
for i in xrange(len(q)):
    qf[i].reverse()
    q[i][-1] = 0
    shear.append(q[i]+qf[i]) """

#Saving to file 
file_Name = "Superposition_shear_analytical"
fileObject = open(file_Name,'wb')
pickle.dump(shear,fileObject)
fileObject.close()