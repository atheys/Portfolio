
import numpy as np

def compare(X_i,X_j):
    # Edge Case
    if len(X_i) != len(X_j):
        return False
    evaluate = True
    for k in range(len(X_i)):
        evaluate = evaluate and X_j[k]<X_i[k]
    return evaluate

def V(i,array):
    X = array[i]
    number,n = 0.,float(len(array)-1)
    for item in array:
        if compare(X,item):
            number += 1.
    return number/n   

        
def V_avg(array):
    total,n = 0.,float(len(array))
    for i in range(int(n)):
        total += V(i,array)
    return total/n



def tau_n(array):
    dim = len(array[0])
    factor1,factor2 = float(2.**dim),float(2.**(dim-1)-1.)
    if dim == 3 and len(array)==8579:
        return (0.127348820425*factor1-1.)/factor2
        #return (0.140600039717*factor2-1.)/factor2
    elif dim == 6 and len(array)==2434:
        return (0.0456267407777*factor1-1.)/factor2
        #return (0.0547965677359*factor1-1.)/factor2
    else:
        return (V_avg(array)*factor1-1.)/factor2
        
def f(x,tau):
    x,tau = float(x),float(tau)
    x1,x2,x3 = x,x**2,x**3
    return 465.*(1-tau)*x3 +(330.-1426*tau)*x2+(60.-1116.*tau)*x1-248.*tau
    

def bisection(tau,n=1000):
    interval = [0.,100.]
    midpoint = 0.
    while n>0:
        midpoint = 0.5*(sum(interval))
        if f(midpoint,tau)*f(interval[1],tau)>0.:
            interval[1] = midpoint
        else:
            interval[0] = midpoint
        n -= 1
    return midpoint

import InputReader as IR

def theta_n(array):
    dim,tau,theta = len(array[0]),tau_n(array),-1.
    if dim == 3:
        theta = (2.*tau)/(1.-tau)
    elif dim == 6:
        theta = bisection(tau)
    if theta<0.:
        theta = 0.01
    return theta

def makeStep(array):
    return 1./float(len(array))

def makeRange(array):
    step = makeStep(array)
    return np.arange(0.,1.+step,step)
    
def K_n(array):
    n,K,y = float(len(array)),[],[]
    for i in range(len(array)):
        k = V(i,array)*((n-1.)/(n))
        K.append(k)
    for j in makeRange(array):
        total = 0.
        for item in K:
            if item <= j:
                total += 1.
        total /= n
        y.append(total)
    return y
"""
import Clayton3D as C3D
import Clayton6D as C6D
import matplotlib.pyplot as plt

single = IR.read('single.csv')
double = IR.read('double.csv')
plt.figure(0)
x11,y11 = makeRange(single),K_n(single)
x12= np.arange(0.,1.001,0.01)
y12 =C3D.CDF(x12,theta_n(single))
plt.plot(x11,y11,c='g',label='Empirisch',lw=1.6)
plt.plot(x12,y12,c='b',label='Theoretisch',lw=1.2)
plt.xlabel('u []', size=12.)
plt.ylabel('Cumulatieve kansdichtheid []', size=12.)
plt.legend(loc=4)
plt.xlim(0.,1.)
plt.ylim(0.,1.01)
plt.grid(True)
plt.figure(1)
x11,y11 = makeRange(double),K_n(double)
x12= np.arange(0.,1.001,0.01)
y12 =C6D.CDF(x12,theta_n(double))
plt.plot(x11,y11,c='g',label='Empirisch',lw=1.6)
plt.plot(x12,y12,c='b',label='Theoretisch',lw=1.2)
plt.xlabel('u []', size=12.)
plt.ylabel('Cumulatieve kansdichtheid []', size=12.)
plt.legend(loc=4)
plt.xlim(0.,1.)
plt.ylim(0.,1.01)
plt.grid(True)
plt.show()"""