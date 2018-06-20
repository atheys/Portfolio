from Mesh import *
from BasicFunctions import *
from Element import *
import numpy as np
import numpy.linalg as la
import matplotlib.pyplot as plt
import random as rdn

precision = 0.1
x = np.arange(0.,1.+precision,precision)
m = Mesh(x)
iface = [0.11,0.27,0.59,0.87]
m.setInterface(iface)

print S_matrix(m)

def createLevelSet(mesh):
    S,F = S_matrix(m),F_matrix(m)
    n = len(F)
    f = f_matrix(m,n)
    zero = np.zeros((n,n))
    block = np.bmat([[S,F.transpose()],[F,zero]])
    y = (la.inv(block)*f.transpose()).transpose()
    y = y.tolist()[0][:-n]
    return y
    
def evalIntegral(mesh):
    x,y = mesh.array,createLevelSet(mesh)
    integral = 0.
    for i in range(1,len(x)):
        diff = (y[i]-y[i-1])/(x[i]-x[i-1])
        integr = 0.5*(1.-diff**2)**2
        integr *= (x[i]-x[i-1])
        integral += integr
    return integral

def partials(mesh,delta=0.01):
    delta = float(delta)
    initial,alphas = list(mesh.alphas),list(mesh.alphas)
    partials = []
    for i in range(len(alphas)):
        alphas[i] += delta
        mesh.setAlphas(alphas)
        eval1 = evalIntegral(mesh)
        alphas[i] -= 2.*delta
        mesh.setAlphas(alphas)
        eval2 = evalIntegral(mesh)
        partials.append((eval1-eval2)/(2.*delta))
        alphas[i] = initial[i]
    mesh.setAlphas(initial)
    return partials
      
def descent(mesh,n=500):
    i = 0
    # temp1,temp2 = list(mesh.alphas),partials(mesh)
    gamma = 1000.
    while i<n:
        alphas,part = list(mesh.alphas),partials(mesh)
        alphas = list((np.array(alphas)-gamma*np.array(part)).tolist())
        mesh.setAlphas(alphas)
        if i%10 == 0:
            print i,evalIntegral(mesh)
        i += 1
    return mesh
#(1.-float(i/n))
a = m.alphas       
m = descent(m)
x,y = m.array,createLevelSet(m)
plt.figure(0)
for i in m.interface:
    plt.axvline(i,color='r')
plt.axhline(0.,color='r')
print a
print m.alphas
plt.plot(x,y)
