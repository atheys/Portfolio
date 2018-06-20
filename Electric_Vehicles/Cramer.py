
import Empirical as Emp
import Clayton3D as C3D
import Clayton6D as C6D
import numpy as np

x = np.arange(0.1,0.41,0.01)
y = [ 0.267507,0.231871,0.201716,0.176044,0.154071,\
      0.135172,0.118848,0.104695,0.0923834,0.0816425,\
      0.0722473,0.0640103,0.0567739,0.0504049,0.0447902,\
      0.0398332,0.0354511,0.0315725,0.028136,0.025088,\
      0.0223823,0.0199784,0.017841,0.0159393,0.0142461,\
      0.0127376,0.011393,0.0101939,0.00912389,0.00816872,\
      0.00731566]

def criterium(array):
   dim,y_n = len(array[0]),Emp.K_n(array)
   y,x_range,step = [],Emp.makeRange(array),Emp.makeStep(array)
   if dim==3:
       y = C3D.CDF(x_range,Emp.theta_n(array))
   if dim==6:
       y = C6D.CDF(x_range,Emp.theta_n(array))
   if len(y)>0:
       S = 0.
       for i in range(len(x_range)-1):
           S += 0.5*step*((y[i+1]-y_n[i+1])**2+(y[i]-y_n[i])**2)
       return float(len(array))*S
   else:
       return -1.

def critical_alpha(array):
    crit = 0.5*criterium(array)
    if crit<=0.1:
        return 0.267507
    if crit>=0.4:
        return 0.00731566
    index = -1
    for i in range(len(x)-1):
        if x[i]<=crit and crit<x[i+1]:
            index = i
    step = float(x[index+1])-float(x[index])
    perc = (crit-x[index])/step
    diff = float(y[index+1])-float(y[index])
    alpha = y[index]+perc*diff
    return alpha        