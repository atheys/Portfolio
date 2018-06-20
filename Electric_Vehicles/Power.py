
import numpy as np

def ind(t,a):
    if t>=a:
        return 1.
    return 0.

def singleTimes(point):
    t_O = point[1]
    t_E = t_O + 5.*(point[2]/130.)
    return t_O,t_E

def doubleTimes(point):
    t_O_1 = point[1]
    t_E_1 = min(t_O_1 + 5.*(point[2]/130.),point[3])
    t_O_2 = point[4]
    t_E_2 = t_O_2+ 5.*((point[2]+point[5])/130.)+(t_E_1-t_O_1)
    return t_O_1,t_E_1,t_O_2,t_E_2

def deltaSingle(t,point):
    t_O,t_E = singleTimes(point)
    return ind(t,t_O)-ind(t,t_E)

def deltaDouble(t,point):
    t_O_1,t_E_1,t_O_2,t_E_2 = doubleTimes(point)
    return ind(t,t_O_1)-ind(t,t_E_1)+ind(t,t_O_2)-ind(t,t_E_2)

def find(t,T):
    for i in range(len(T)-1):
        if T[i]<=t and t<T[i+1]:
            return i

def power(single,double):
    T,Tbis,P,Pbis = np.arange(0.,35.2,0.2),[],[],[] 
    for t in T:
        print t
        n = 0.
        for item in single:
            n += 7.5*deltaSingle(t,item)
        for item in double:
            n += 10.*deltaDouble(t,item)
        n *= 6.103
        P.append(n)
        if t<24.:
            Tbis.append(t)
            Pbis.append(n)
        else:
            Pbis[find(float(t)%24,Tbis)]+=n
    return T,Tbis,P,Pbis