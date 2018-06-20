from math import log,e
import numpy as np
import matplotlib.pyplot as plt

def R(t,theta):
    t,th,p = float(t),float(theta),abs(log(t))
    c1 = -(1.-th)*p*t
    c2 = th*(1.-th)*p*t+((1.-th)**2)*(p**2)*t
    c3 = th*(1.-th)*p*t-3.*th*((1.-th)**2)*(p**2)*t-((1.-th)**3)*(p**3)*t
    c4 = -th*(th+2.)*(th**2-1.)*p*t+(7.*th**2+4.*th)*((1.-th)**2)*(p**2)*t + \
    6.*th*((1.-th)**3)*(p**3)*t+((1.-th)**4)*(p**4)*t
    c5 = th*(th+2.)*(th+3.)*(th**2-1.)*p*t \
    -(th+1.)*(15.*th**2+10.*th)*((1.-th)**2)*(p**2)*t \
    -(25.*th**2+10.*th)*(1.-th**3)*(p**3)*t \
    -10.*th*((1.-th)**4)*(p**4)*t \
    -((1.-th)**5)*(p**5)*t
    return t-1.*c1+0.5*c2-(1./6.)*c3+(1./24.)*c4-(1./120.)*c5