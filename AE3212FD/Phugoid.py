# Spiral Motion Simulation Module.
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

# Hard code.
kts = 0.514444444

# Plots the velocity and elevator deflection during the period of interest
def plotVelocityAndElevatorDeflection():
    # Minute at which the maneuver takes place.
    minute = 54.
    # Lower/Upper limit  time array.
    t_0,t_1 = (minute-0.5)*60.,(minute+2.5)*60. 
    t1,velocity = df.find(t_0,t_1,dr.x49,dr.x43*kts)
    t2,deflection = df.find(t_0,t_1,dr.x49,dr.x18)
    #plt.subplot(121)
    plt.figure(0)
    plt.plot(np.array(t1)-min(t1),velocity)
    plt.ylabel('Velocity [m/s]')
    plt.xlabel('Time [s]')
    plt.grid(True)
    plt.figure(1)
    plt.plot(np.array(t2)-min(t2),deflection)
    plt.ylabel('Elevator deflection angle [deg]')
    plt.xlabel('Time [s]')
    plt.grid(True)
    plt.show()
    return

# Determines step input.
# @param: [array] step input array [np.array].
# @param: [t] time array [np.array].
# @param: [defl_peak1] first peak deflection angle (rad) [float].
# @param: [defl_peak2] second peak deflection angle (rad) [float].
# @param: [p_0] start time peak (s) [float].
# @param: [p_1] mid time peak (s) [float].
# @param: [p_2] end time peak (s) [float].
# @param: [array] modified step input array [np.array].
def stepInput(array,t,p_0,p_1,p_2,da_peak1,da_peak2):
    for i in range(len(t)):
        if t[i]<p_0:
            array[i] = -0.519
        elif p_0<t[i] and t[i]<=p_1:
            array[i] = da_peak1
        elif p_1<t[i] and t[i]<p_2:
            array[i] = da_peak2
    return array

# Plots Phugoid Motion.
def plotPhugoid():
    v_0,t_0 = 95.8,3220.
    da_equi,da_peak1,da_peak2 = -0.56,-1.45,-0.9
    p_0,p_1,p_2 = 4.0,8.0,16.0
    t_end,dt = 150.,0.1
    t = sta(0.,t_end,dt)
    ip = stepInput(da_equi*np.ones((len(t))),t,p_0,p_1,p_2,da_peak1,da_peak2)
    timelist = list(dr.x49)
    # Index for starting condition.
    index  = timelist.index(t_0)
    u_0,a_0 = 0.,dr.alpha[index],
    th_0,q_0 = dr.x23[index],dr.x28[index]*float(cp.c)/v_0
    x_0 = np.array([u_0,a_0,th_0,q_0])
    # Simulate response.
    Y,T,X = c.lsim(ss,U=ip,T=t,X0=x_0)
    for i in Y:
        i[0] += v_0
        i[3] *= v_0/float(cp.c)
    t1, V = df.find(t_0,t_0+t_end,dr.x49,dr.x43)
    t2, a = df.find(t_0,t_0+t_end,dr.x49,dr.alpha)
    t2, th = df.find(t_0,t_0+t_end,dr.x49,dr.x23)
    t3, q = df.find(t_0,t_0+t_end,dr.x49,dr.x28)
    t4, dangle = df.find(t_0,t_0+t_end,dr.x49,dr.x18)
    # Plots.
    plt.rc("figure", facecolor="white")
    # First Plot.
    plt.figure(0)
    plt.subplot(211)
    plt.plot(np.array(t1)-t_0,np.array(V)*kts, label="Flight data", linestyle='--',c='b')
    plt.plot(T,Y[:,0], label="Numerical data",linestyle='-',c='g')
    plt.ylabel("Velocity [m/s]")
    plt.xlabel("Time [s]")
    plt.ylim(0.,275.)
    plt.xlim(0.,150.)
    plt.legend(loc=1)
    # plt.title('Velocity during the phugoid motion, for the actual flight and numerical simulation')
    plt.grid(True)
    # Second plot.
    plt.figure(1)
    plt.subplot(211)
    plt.plot(np.array(t4)-t_0,dangle, label="Flight data", linestyle='--',c='b')
    plt.plot(T,ip,label="Numerical data",c='g')
    plt.legend(loc=4)
    plt.ylabel("Elevator deflection [deg]")
    plt.xlabel("Time [s]")
    plt.grid(True)
    #plt.title('Elevator deflection during the phugoid motion, for the actual flight and numerical simulation')
    plt.show()
    return

# Main Method.
if __name__ == "__main__":
    plotVelocityAndElevatorDeflection()
    plotPhugoid()