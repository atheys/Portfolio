
import InputReader as IR
import Empirical as Emp
import Clayton3D as C3D
import numpy as np
import Marginal as Mar
import Clayton6D as C6D

def upsilon(t,theta):
    t,theta = float(t),float(theta)
    return (1./theta)*(t**(-theta)-1.)

def upsilon_inv(t,theta):
    t,theta = float(t),float(theta)
    return (1.+theta*t)**(-1./theta)

def validate3D(X1,X2,X3):
    return X1>0.01 and X1<X2 and X3/(X2-X1)<130

def Simulation3D(data,n):
    r1,r2,r3 = np.arange(5.,24.01,0.01),np.arange(0.,31.01,0.01),np.arange(0.,130.01,0.01)
    X1,X2,X3 = IR.isolate(data,0),IR.isolate(data,1),IR.isolate(data,2)
    y1,y2,y3 = Mar.gamma_CDF(X1,r1),Mar.normal_CDF(X2,r2),Mar.exp_CDF(X3,r3)
    theta,sample,fails1,fails2 = Emp.theta_n(data),[],0,0
    while len(sample)<n:
        try:
            if len(sample)%1000==0:
                print len(sample)
            k1,k2,k3 = np.random.uniform(0.,1.),np.random.uniform(0.,1.),np.random.uniform(0.,1.)
            t = C3D.K_inv(k1,theta)
            w1,w2,w3,ups = k2*k3,k2*(1.-k3),1.-k2,upsilon(t,theta)
            u1 = upsilon_inv(w1*ups,theta)
            u2 = upsilon_inv(w2*ups,theta)
            u3 = upsilon_inv(w3*ups,theta)
            X1,X2,X3 = Mar.inverse(u1,r1,y1),Mar.inverse(u2,r2,y2),Mar.inverse(u3,r3,y3)
            if validate3D(X1,X2,X3):
                sample.append([X1,X2,X3])
            else: 
                fails2 += 1
        except Exception:
            fails1+=1
    return sample

def validate6D(X1,X2,X3,X4,X5,X6):
    check1 = X1<X2
    check2 = X3/(X2-X1)<130
    check3 = X1<X4 and X2<X4
    check4 = X4<X5
    check5 = X6/(X5-X4)<130
    check = check1 and check2 and check3 \
            and check4 and check5
    return check

def Simulation6D(data,n):
    r1,r2,r3 = np.arange(0.,24.01,0.01),np.arange(0.,24.01,0.01),np.arange(0.,130.01,0.01)
    r4,r5,r6 = np.arange(0.,24.01,0.01),np.arange(0.,35.01,0.01),np.arange(0.,130.01,0.01)
    X1,X2,X3 = IR.isolate(data,0),IR.isolate(data,1),IR.isolate(data,2)
    X4,X5,X6 = IR.isolate(data,3),IR.isolate(data,4),IR.isolate(data,5)
    y1,y2,y3 = Mar.gamma_CDF(X1,r1),Mar.binormal_CDF(r2,12.2,1.6,17.25,1.3,0.62),Mar.exp_CDF(X3,r3)
    y4,y5,y6 = Mar.bigamma_CDF(r4,85.,2.0,0.137,110.,2.3,0.152,0.385),Mar.binormal_CDF(r5,17.3,1.7,21.8,1.3,0.65),Mar.exp_CDF(X6,r6)
    theta,sample,fails1,fails2 = Emp.theta_n(data),[],0,0
    while len(sample)<n:
        try:
            if len(sample)%1000==0:
                print len(sample)
            k1,k2,k3 = np.random.uniform(0.,1.),np.random.uniform(0.,1.),np.random.uniform(0.,1.)
            k4,k5 = np.random.uniform(0.,1.),np.random.uniform(0.,1.)
            t = C6D.K_inv(k1,theta)
            ups = upsilon(t,theta)
            w1,w2,w3 = k2*k3,k2*(1.-k3),1.-k2
            w4,w5,w6 = k4*k5,k4*(1.-k5),1.-k4
            u1,u2 = upsilon_inv(w1*ups,theta),upsilon_inv(w2*ups,theta)
            u3,u4 = upsilon_inv(w3*ups,theta),upsilon_inv(w4*ups,theta)
            u5,u6 = upsilon_inv(w5*ups,theta),upsilon_inv(w6*ups,theta)
            X1,X2,X3 = Mar.inverse(u1,r1,y1),Mar.inverse(u2,r2,y2),Mar.inverse(u3,r3,y3)
            X4,X5,X6 = Mar.inverse(u4,r4,y4),Mar.inverse(u5,r5,y5),Mar.inverse(u6,r6,y6)
            if validate6D(X1,X2,X3,X4,X5,X6):
                sample.append([X1,X2,X3,X4,X5,X6])
            else: 
                fails2 += 1
        except Exception:
            fails1+=1
    return sample