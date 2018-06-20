import numpy as np
from scipy import sparse as s
from Mesh import *

class LinearFunction(object):
    
    def __init__(self,p_dot,c_dot,n_dot,index=0,begin=False,end=False):
        self.previous = p_dot
        self.current = c_dot
        self.next = n_dot
        self.begin = begin
        self.end = end
        self.index = index

    def evaluate(self,x):
        x = float(x)
        if self.begin:
            if x<self.current or self.next<=x:
                return 0.
            else:
                return 1.-(x-self.current)/(self.next-self.current)
        elif self.end:
            if x<=self.previous or self.current<x:
                return 0.
            else:
                return (x-self.previous)/(self.current-self.previous)
        else:
            if self.previous<x and x<=self.current:
                return (x-self.previous)/(self.current-self.previous)
            elif self.current<x and x<=self.next:
                return 1.-(x-self.current)/(self.next-self.current)
            else:
                return 0.  
    
    def derivatives(self):
        if self.begin:
            return 0.,-1./(self.next-self.current)
        elif self.end:
            return 1./(self.current-self.previous),0.
        return 1./(self.current-self.previous),-1./(self.next-self.current)
        
    
    def derive(self,x):
        x,d = float(x),self.derivatives
        if self.begin:
            if self.current<=x and x<=self.next:
                return d[1]
            else: 
                return 0.
        elif self.end:
            if self.previous<=x and x<=self.current:
                return d[0]
            else: 
                return 0.
        else:
            x,d = float(x),self.derivatives
            if self.previous<=x and x<self.current:
                return d[0]
            elif self.current<=x and x<=self.next:
                return d[1]
            else:
                return 0.
    
    def integral(self,x0,x1):
        x0,x1 = min(x0,x1),max(x0,x1)
        f0,f1,fc = self.evaluate(x0),self.evaluate(x1),1.
        if self.begin:
            if x1<=self.current:
                return 0.
            else:
                if x0<self.current:
                    return 0.5*(f1+fc)*(x1-self.current)
                else:
                    return 0.5*(f1+f0)*(x1-x0)
        elif self.end:
            if self.current<=x0:
                return 0.
            else:
                if self.current<x1:
                    return 0.5*(f1+fc)*(x1-self.current)
                else:
                    return 0.5*(f1+f0)*(x1-x0)
        else:
            if self.next<=x0 or x1<=self.previous:
                return 0.
            else:
                x0,x1 = max(self.previous,x0),min(x1,self.next)
                f0,f1,fc = self.evaluate(x0),self.evaluate(x1),1.
                if x1<=self.current:
                    return 0.5*(x1-x0)*(f0+f1)
                else:
                    return 0.5*((self.current-x0)*(f0+fc)+\
                           (x1-self.current)*(fc+f1))

def S(f1,f2,element):
    i1,i2,ie = f1.index,f2.index,element.index
    check = ie>i1 or ie>i2 or abs(i1-i2)>1 or abs(i1-ie)>1 or abs(ie-i2)>1
    if check:
        return 0.
    else:
        constant = 1.
        if i1 == ie:
            constant *= f1.derivatives()[1]
        else:
            constant *= f1.derivatives()[0]
        if i2 == ie:
            constant *= f2.derivatives()[1]
        else:
            constant *= f2.derivatives()[0]
        return constant*element.length
    
def S_unit(element,mesh):
    e,i = element,element.index
    f1,f2 = mesh.functions[i-1],mesh.functions[i]
    e11,e12,e21,e22 = S(f1,f1,e),S(f1,f2,e),S(f2,f1,e),S(f2,f2,e)
    return np.matrix([[e11,e12],[e21,e22]])

# Neumann condition in place.
def S_matrix(mesh):
    elements = mesh.elements
    n = len(elements)
    matrix = np.zeros((n+1,n+1))
    for i in range(n):
        matrix[i:i+2,i:i+2] += S_unit(elements[i],mesh)
    return matrix

def F_matrix(mesh):
    F = []
    for point in mesh.interface:
        F_i = len(mesh.functions)*[0.]
        loc = mesh.location(point)
        if loc > -1:
            l = mesh.functions[loc].evaluate(point)
            k = 1.-l
        F_i[loc] += l
        F_i[loc+1] += k
        F.append(F_i)
    return np.matrix(F)

def f_matrix(mesh,n):
    f = []
    for i in range(len(mesh.functions)):
        k = 0.
        """if i == 0:
            k = mesh.signs[0]
        if i == len(mesh.functions)-1:
            k = mesh.signs[len(mesh.signs)-1]"""
        function = mesh.functions[i]
        f.append(mesh.domain_integral(function)+k)
    for i in range(int(n)):
        f.append(0.)
    return np.matrix(f)

def f2_matrix(mesh):
    f = []
    for function in mesh.functions:
        f.append(mesh.domain_integral(function))
    return np.matrix(f)