import pickle
import numpy as np
from math import *
import Geometry as geo

fileObject = open("Superposition_shear_analytical",'r')
shear = pickle.load(fileObject)

fileObject = open("normal_stress_analytical",'r')
normal = pickle.load(fileObject)

qs=np.array(shear[0])
qf=np.array(shear[1])

sigma_z=np.array(normal[0])
sigmaf_z=np.array(normal[1])

tau_s = qs / geo.t_s *1000 #Shear stresses
tau_f = qf / geo.t_f *1000

Ys = np.sqrt((sigma_z)**2 + 3*tau_s**2) /10**6 #von Mises Stress
Yf = np.sqrt((sigmaf_z)**2 + 3*tau_f**2) /10**6

Ys_temp = np.ndarray.tolist(Ys)
Yf_temp = np.ndarray.tolist(Yf)

mises = []
for i in xrange(len(Ys)):
    Yf_temp[i].reverse()
    Ys_temp[i][-1] = 0
    mises.append(Ys_temp[i]+Yf_temp[i])

#Saving to file 
file_Name = "von_mises_analytical"
fileObject = open(file_Name,'wb')
pickle.dump(mises,fileObject)
fileObject.close()