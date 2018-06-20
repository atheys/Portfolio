# Symmetrical Flight Simulation Module.
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
    
# Plot velocity and elevator deflection during period of interest.
def plotVelocityAndElevatorDeflection():
    # Minute at which the maneuver takes place.
    minute = 59.
    t_0,t_1 = (minute-0.5)*60.,(minute+1.)*60.
    t1,rudder = df.find(t_0,t_1,dr.x49,dr.x19)
    t2,yaw = df.find(t_0,t_1,dr.x49,dr.x29)
    plt.figure(0)
    plt.plot(np.array(t1)-min(t1),rudder)
    plt.ylabel('Rudder angle [deg]')
    plt.xlabel('Time [s]')
    plt.grid(True)
    plt.figure(1)
    plt.plot(np.array(t2)-min(t2),yaw)
    plt.ylabel('Yaw rate [deg/s]')
    plt.xlabel('Time [s]')
    plt.grid(True)
    plt.show()
    return

# Determines step input.
# @param: [array] step input array [np.array].
# @param: [t] time array [np.array].
# @param: [p_0] begin time peak (s) [float].
# @param: [p_1] second time peak (s) [float].
# @param: [p_2] third time peak (s) [float].
# @param: [p_3] end time peak (s) [float].
# @param: [defl_equi] equilibrium peak deflection angle (rad) [float].
# @param: [defl_start] start peak deflection angle (rad) [float].
# @param: [defl_peak1] first peak deflection angle (rad) [float].
# @param: [defl_peak2] second peak deflection angle (rad) [float].
# @param: [defl_peak2] second peak deflection angle (rad) [float].
# @param: [array] modified step input array [np.array].
def stepInput(array,t,p_0,p_1,p_2,p_3,da_equi,\
              da_start,da_peak1,da_peak2,da_peak3):
    for i in range(len(t)):
        if t[i]<=p_0:
            array[i,1] = da_start
        elif p_0<t[i] and t[i]<=p_1:
            array[i,1] = da_peak1
        elif p_1<t[i] and t[i]<=p_2:
            array[i,1] = da_peak2
        elif p_2<t[i] and t[i]<=p_3:
            array[i,1] = da_peak3
        else:
            array[i,1] = da_equi
    return array

# Plots Dutch Roll Motion.
def plotDutchRoll():
    t_0,t_1 = 3478.,3525.
    da_equi,da_start = -2.,-1.9
    da_peak1,da_peak2,da_peak3= -10.0,4.6,-6.2
    p_0,p_1,p_2,p_3 = 2.5,4,5.5,6.3
    t_end,dt = 50.,0.1
    t = sta(0.,t_end,dt)
    ip = stepInput(np.ones((len(t),2)),t,p_0,p_1,p_2,p_3,\
         da_equi,da_start,da_peak1,da_peak2,da_peak3)
    timelist = list(dr.x49)
    index  = timelist.index(t_0)
    beta_0,phi_0,p_0,r_0 = 0.,dr.x22[index],dr.x27[index],dr.x29[index]
    p_0 *= float(cp.b)/(2.*float(cp.V0))
    r_0 *= float(cp.b)/(2.*float(cp.V0))
    x_0 = np.array([beta_0,phi_0,p_0,r_0])
    # Simulate response.
    Y, T, X= c.lsim(ss, U=ip, T=t, X0=x_0)
    for i in Y: 
        i[2] *= (2.*float(cp.V0))/float(cp.b)
        i[3] *= (2.*float(cp.V0))/float(cp.b)
    t1,ph = df.find(t_0,t_0+t_end,dr.x49,dr.x22)
    t2,p = df.find(t_0,t_0+t_end,dr.x49,dr.x27)
    t3,r = df.find(t_0,t_0+t_end,dr.x49,dr.x29)
    t4,rudder = df.find(t_0,t_0+t_end,dr.x49,dr.x19)
    t5,r2 = df.find(t_1,t_1+t_end,dr.x49,dr.x29)
    t6,rudder2 = df.find(t_1,t_1+t_end,dr.x49,dr.x19)
    # Plots.
    plt.rc("figure", facecolor="white")
    plt.figure(0)
    plt.subplot(211)
    plt.plot(np.array(t3)-t_0,r, label="Flight data", linestyle='-',c='b')
    plt.plot(np.array(t5)-t_1,r2, label="Flight data (YD)", linestyle='-',c='r')
    plt.plot(T,-Y[:,3], label="Numerical data",linestyle='-',c='g')
    plt.ylabel(r"Roll rate [deg/s]")
    plt.xlabel("Time [s]")
    plt.ylim(-60.,25.)
    plt.xlim(0,30)
    plt.legend(loc=4)
    #plt.title('yaw rate during the dutch roll, for the actual flight and numerical simulation')
    plt.grid(True)
    plt.figure(1)
    plt.subplot(212)
    plt.plot(np.array(t4)-t_0,rudder, label="Flight data", linestyle='-',c='b')
    plt.plot(np.array(t6)-t_1,rudder2, label="Flight data (YD)", linestyle='-',c='r')
    plt.plot(T,ip[:,1], label="Numerical data",c='g')
    plt.legend(loc=4)
    plt.ylim(-25,6)
    plt.xlim(0,30)
    plt.ylabel(r"Rudder deflectiong angle [deg]")
    plt.xlabel("Time [s]")
    plt.grid(b=True)
    #plt.title('Rudder deflection during the dutch roll, for the actual flight and numerical simulation')
    plt.show()
    return
    
# Main Method.
if __name__ == "__main__":
    plotVelocityAndElevatorDeflection()
    plotDutchRoll()