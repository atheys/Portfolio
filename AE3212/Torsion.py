import pickle
import numpy as np
import sympy as sp
from math import *

#Reading torque distribution from file

fileObject = open("T",'r')
T = pickle.load(fileObject)

#Inputs imported from other programs   
T = np.transpose(T)                         #applied torque [Nm]
r = geo.R / 1000.                           #radius of fuselage [m]
hf = geo.h_f / 1000.                        #floor height [m]
ts = geo.t_s / 1000.                        #skin thickness [m]
tf = geo.t_f / 1000.                        #floor thickness [m]     

#Dimensions
alpha = 2.0*acos((r-hf)/r)                      #angle between floor and fuselage contact points [rad]
beta = 2*pi - alpha                             #complementary angle [rad]

lf = 2*r*sin(alpha/2.0)                         #floor length [m]

#Calculations
A1 = (r**(2))*beta/(2.0)+0.5*lf*(r-hf)          #top cell area [m^2]
A2 = pi*(r**(2))-A1                             #bottom cell area [m^2]

#Definitoin of symbols for equation solver
q1 = sp.Symbol('q1')
q2 = sp.Symbol('q2')

q = []                                          #shear flows list

#Solving system of linear equations
for i in xrange(len(T)):
    if i % 5 == 0:
        print int(i/float(len(T))*100.0),"%"                                            #shows percentage of completion
    eq1 = 2*A1*q1+2*A2*q2-T[i]                                                          #first equation
    eq2 = (q1*r*beta/ts+(q1-q2)*lf/tf)/(A1**2) - (q2*r*alpha/ts+(q2-q1)*lf/tf)/(A2**2)  #second equation
    sol = sp.solve([eq1,eq2],[q1,q2])                                                   #solver
    q.append([sol[q1],sol[q2],sol[q1]-sol[q2]])                                         #shear flows in top cell, bottom cell, and floor respectively [N/m]
    
#Saving to file 
file_Name = "Torsion_Shear_analytical"
fileObject = open(file_Name,'wb')
pickle.dump(q,fileObject)
fileObject.close()