import pickle
import numpy as np
from math import *
from Inertia import I_xx,I_yy
from Reactions import M_x, M_y

fileObject = open("Coordinates",'r')
coordinates = pickle.load(fileObject)

#Inputs
y=list(np.array(coordinates[1]))
x=list(np.array(coordinates[0]))
yf=list(np.array(coordinates[3]))
xf=list(np.array(coordinates[2]))
z=list(np.array(coordinates[6]))

#Outputs
sigma = []
sigma_f = []

#Calculation
for j in xrange(len(z)):
    sigma_temp = np.zeros((1,len(x)))
    sigma_temp = np.ndarray.tolist(sigma_temp[0])
    sigma.append(sigma_temp)
    
    sigma_f_temp = np.zeros((1,len(xf)))
    sigma_f_temp = np.ndarray.tolist(sigma_f_temp[0])
    sigma_f.append(sigma_f_temp)
    
    for i in xrange(len(x)):
        sigma[j][i] = (M_y(z[j])/I_yy() * x[i] + M_x(z[j])/I_xx() * y[i])*(1000**4)
        
    for i in xrange(len(xf)):
        sigma_f[j][i] = (M_y(z[j])/I_yy() * xf[i] + M_x(z[j])/I_xx() * yf[i])*(1000**4)
        
normal = [sigma, sigma_f]

#Saving to file 
file_Name = "normal_stress_analytical"
fileObject = open(file_Name,'wb')
pickle.dump(normal,fileObject)
fileObject.close()

print 'done'