import Empirical as Emp
import Clayton3D as C3D
import Clayton6D as C6D
from math import e,sqrt

def criterium(array):
   dim,y_n = len(array[0]),Emp.K_n(array)
   y,x_range = [],Emp.makeRange(array)
   if dim==3:
       y = C3D.CDF(x_range,Emp.theta_n(array))
   if dim==6:
       y = C6D.CDF(x_range,Emp.theta_n(array))
   if len(y)>0:
       M = []
       for i in range(len(x_range)):
           M.append(abs(y[i]-y_n[i]))
       return sqrt(float(len(array)))*max(M)
   else:
       return -1.

def Kolmogorov(x):
    x,s = float(x),0.
    for i in range(1,100):
        k = float(i)
        s += ((-1.)**(k-1))*e**(-2.*(k**2)*(x**2))
    return 1-(2.*s)

def critical_alpha(array):
    crit = criterium(array)
    return 1.-Kolmogorov(crit)