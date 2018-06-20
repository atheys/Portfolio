# Short Period Motion Simulation Module.
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Imported Module.
# Control Package Import.
import control as c
# StateSpace Module Import.
from StateSpace import S_ss as ss
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
# Math Library Module Import.
from math import pi
        
kts = 0.514444444

# Plots velocity and elevator deflection during period of interest.
def plotVelocityAndElevatorDeflection():
    # Minute at which the maneuver takes place.
    minute = 52.
    t_0,t_1 = (minute-0.5)*60.,(minute+2.)*60.
    t1,pitchrate = df.find(t_0,t_1,dr.x49,dr.x28)
    t2,deflection = df.find(t_0,t_1,dr.x49,dr.x18)
    plt.figure(0)
    plt.subplot(211)
    plt.plot(np.array(t1)-min(t1),pitchrate)
    plt.ylabel('Pitch rate [deg/s]')
    plt.xlabel('Time [s]')
    plt.xlim(0.,150.)
    plt.grid(True)
    plt.figure(1)
    plt.subplot(211)
    plt.ylabel('Elevator deflection angle [deg]')
    plt.xlabel('Time [s]')
    plt.xlim(0.,150.)
    plt.plot(np.array(t2)-min(t2),deflection)
    plt.grid(True)
    plt.show()
    return

# Plots Short Period Motion.
def plotShortPeriod():
    t_0,shift = 3148.,0.
    t_0 += shift
    t_end,dt = 20.,0.1
    t = sta(0.,t_end,dt)
    ip = np.zeros((len(t)))
    for i in range(len(t)):
        if t[i]<=0.9:
            ip[i] = -0.47
        else:
            ip[i] = -1.25
    # Index starting condition.
    timelist = list(dr.x49)
    index  = timelist.index(t_0)
    u_0,a_0,th_0 = 0.,dr.alpha[index],dr.x23[index]
    q_0 = dr.x28[index]*float(cp.c)/float(cp.V0)
    x_0 = np.array([u_0,a_0,th_0,q_0])
    # Simulate response.
    Y,T,X = c.lsim(ss,U=ip,T=t,X0=x_0)
    for i in Y:
        i[0] += float(cp.V0)
        i[3] *= float(cp.V0)/float(cp.c)
    t1,V = df.find(t_0,t_0+t_end,dr.x49,dr.x43*kts)
    t2,a = df.find(t_0,t_0+t_end,dr.x49,dr.alpha)
    t3,th = df.find(t_0,t_0+t_end,dr.x49,dr.x23)
    t4,q = df.find(t_0,t_0+t_end,dr.x49,dr.x28)
    t5,dangle = df.find(t_0,t_0+t_end,dr.x49,dr.x18)
    # Plot 1.
    plt.rc("figure", facecolor="white")
    plt.figure(0)
    plt.subplot(211)
    plt.plot(np.array(t4)-t_0,np.array(q), label="Flight data", linestyle='--',c='b')
    plt.plot(T-1.5,Y[:,3]/(2.*pi), label="Numerical data",linestyle='-',c='g')
    plt.ylabel("Pitch rate [deg/s]")
    plt.xlabel("Time [s]")
    plt.xlim(0.,15.)
    plt.legend(loc=3)
    #plt.title('Pitch rate during the short-period motion, for the actual flight and numerical simulation')
    plt.grid(True)
    # Plot 2.
    plt.figure(1)
    plt.subplot(212)
    plt.plot(np.array(t5)-t_0,dangle, label="Flight data", linestyle='--',c='b')
    plt.plot(T,ip, label="Numerical data",c='g')
    plt.legend(loc=1)
    plt.ylabel("Elevator deflection angle [deg]")
    plt.xlabel("Time [s]")
    plt.xlim(0.,15.)
    plt.grid(b=True)
    #plt.title('Elevator deflection during the short-period motion, for the actual flight and numerical simulation')
    plt.show()
    return

# Main Method.
if __name__ == "__main__":
    plotVelocityAndElevatorDeflection()
    plotShortPeriod()