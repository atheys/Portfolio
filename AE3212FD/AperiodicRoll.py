# Asymmetrical Roll Simulation Module.
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Imported Module.
# Control Package Import.
import control as c
# StateSpace Module Import.
from StateSpace import A_ss as ss
#import unpacked data
import DataReader as dr
# DataFinder Module Import.
import DataFinder as df
# Pyplot Module Import.
import matplotlib.pyplot as plt
# Numpy Library Module Import.
import numpy as np
# Parameter Module Import.
import Cit_params as cp
# Symmetrical Simulation Module Import.
from Symmetrical import setupTimeArray as sta

# Plots velocity and elevator deflection during period of interest.   
def plotVelocityAndElevatorDeflection():
    minute = 51.
    t_0,t_1 = (minute-0.5)*60.,(minute+1.)*60.
    t1,aileron = df.find(t_0,t_1,dr.x49,dr.x17)
    t2,roll = df.find(t_0,t_1,dr.x49,dr.x27)
    plt.figure(0)
    plt.plot(np.array(t1)-min(t1),aileron)
    plt.xlabel('Time [s]')
    plt.ylabel('Aileron deflection angle [deg]')
    plt.grid(True)
    plt.figure(1)
    plt.plot(np.array(t2)-min(t2),roll)
    plt.xlabel('Time [s]')
    plt.ylabel('Roll rate [deg/s]')
    plt.grid(True)
    plt.show()
    return

# Plots Assymetric roll.
def plotAssymetricRoll():
    t_0 = 3080.
    da_equi,da_peak,da_start = -0.1,-2.0,-1.3
    p_0,p_1 = 1.4,27.
    t_end,dt = 30.,0.1
    t = sta(0.,t_end,dt)
    ip = np.ones(((len(t),2)))
    for i in range(len(t)):
        if t[i]<=p_0:
            ip[i,0] = da_start
        elif p_0<t[i] and t[i]<=p_1:
            ip[i,0] = da_peak
        else:
            ip[i,0] = da_equi
    timelist = list(dr.x49)
    index  = timelist.index(t_0)
    beta_0,phi_0,p_0,r_0 = 0.,dr.x22[index],dr.x27[index],dr.x29[index]
    p_0 *= float(cp.b)/(2.*float(cp.V0))
    r_0 *= float(cp.b)/(2.*float(cp.V0))
    x_0 = np.array([beta_0,phi_0,p_0,r_0])
    # Simulate response.
    Y,T,X = c.lsim(ss,U=ip,T=t,X0=x_0)
    for i in Y:
        i[2] *= float(cp.V0)/float(cp.b)
        i[3] *= (2.*float(cp.V0))/float(cp.b)
    t1,ph = df.find(t_0,t_0+t_end,dr.x49,dr.x22)
    t2,p = df.find(t_0,t_0+t_end,dr.x49,dr.x27)
    t3,r = df.find(t_0,t_0+t_end,dr.x49,dr.x29)
    t4,dangle = df.find(t_0,t_0+t_end,dr.x49,dr.x17)
    plt.rc("figure", facecolor="white")
    plt.figure(0)
    plt.subplot(211)
    plt.plot(np.array(t2)-t_0,p, label="Flight data", linestyle='--',c='b')
    plt.plot(T,Y[:,2], label="Numerical data",linestyle='-',c='g')
    plt.ylabel("Roll rate [deg/s]")
    plt.xlabel("Time [s]")
    plt.ylim(-8,10)
    plt.legend(loc=3)
    #plt.title('Roll rate during the a-periodic roll, for the actual flight and numerical simulation')
    plt.grid(True)
    plt.figure(1)
    plt.subplot(212)
    plt.plot(np.array(t4)-t_0,dangle, label="Flight data", linestyle='--',c='b')
    plt.plot(T,ip[:,0], label="Numerical data",c='g')
    plt.legend(loc=2)
    plt.ylabel("Aileron deflection angle [deg]")
    plt.ylim(-3,1)
    plt.xlabel("Time [s]")
    plt.grid(True)
    #plt.title('Aileron deflection during the a-periodic roll, for the actual flight and numerical simulation')
    plt.show()
    return
    
# Main method.    
if __name__ == "__main__":    
    plotVelocityAndElevatorDeflection()
    plotAssymetricRoll()