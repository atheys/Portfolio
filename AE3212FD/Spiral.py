# Spiral Motion Simulation Module.
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Imported Module.
# Control Package Import.
import control as c
# StateSpace Module Import.
from StateSpace import A_ss as ss
# DataReader Module Import.
import DataReader as dr
# DataFinder Module Import.
import DataFinder as df
# Pyplot Module Import.
import matplotlib.pyplot as plt
# Numpy Library Module Import.
import numpy as np
# Parameters Module Import.
import Cit_params as cp
# Symmetrical Simulation Module Import.
from Symmetrical import setupTimeArray as sta
# Math Library Module Import.
from math import pi

# Plots velocity and elevator deflection during period of interest.
def plotVelocityAndElevatorDeflection():
    # Minute at which the maneuver takes place.
    minute = 61.
    # Lower/Upper limit for the time array.
    t_0,t_1 = (minute-1.)*60.,(minute+4.)*60.
    t1,aileron = df.find(t_0,t_1,dr.x49,dr.x17)
    t2,rollangle = df.find(t_0,t_1,dr.x49,dr.x22)
    plt.figure(0)
    plt.plot(np.array(t1)-min(t1),aileron)
    plt.ylabel("Elevator deflection [deg]")
    plt.xlabel("Time [s]")
    plt.grid(b=True)
    plt.figure(1)
    plt.plot(np.array(t2)-min(t2),rollangle)
    plt.ylabel("Roll angle [deg]")
    plt.xlabel("Time [s]")
    plt.grid(b=True)
    plt.show()
    return

# Determines step input.
# @param: [array] step input array [np.array].
# @param: [t] time array [np.array].
# @param: [defl_start] start deflection angle (rad) [float].
# @param: [defl_equi] equilibrium deflection angle (rad) [float].
# @param: [defl_peak] peak deflection angle (rad) [float].
# @param: [p_0] start time peak (s) [float].
# @param: [p_1] end time peak (s) [float].
# @param: [array] modified step input array [np.array].
def stepInput(array,t,defl_start,defl_equi,defl_peak,p_0,p_1):
    for i in range(len(t)):
        if t[i]<=p_0:
            array[i,0] = defl_start
        elif p_0<t[i] and t[i]<=p_1:
            array[i,0] = defl_peak
        else:
            array[i,0] = defl_equi
    return array
  
# Plots Spiral Flight.
def plotSpiralFlight():
    t_0 = 3640.
    # Start, equillibrium of elevator defl during maneuver.
    defl_start, defl_equi, defl_peak = -0.7,-0.66,-0.35 
    # Begin/End peak.
    p_0,p_1 = 9.,14. 
    # Set the time and input array for the simulation.
    t_end,dt = 70.,0.1
    t = sta(0.,t_end+dt,dt)
    ip = stepInput(np.ones((len(t),2)),t,defl_start,defl_equi,defl_peak,p_0,p_1)
    timelist = list(dr.x49)
    # Index of the starting condition.
    index  = timelist.index(t_0)
    # Starting conditions
    beta_0,phi_0,p_0,r_0 = 0.,dr.x22[index],dr.x27[index],dr.x29[index]
    p_0,r_0 = p_0*float(cp.b)/(2.*float(cp.V0)),r_0*float(cp.b)/(2.*float(cp.V0))
    x_0 = np.array([beta_0,phi_0,p_0,r_0])
    # Simulate response.
    Y, T, X = c.lsim(ss,U=ip,T=t,X0=x_0)
    for i in Y:
        i[2] *= (2.*float(cp.V0))/float(cp.b)
        i[3] *= (2.*float(cp.V0))/float(cp.b)
    t1,ph = df.find(t_0,t_0+t_end,dr.x49,dr.x22)
    t2,p = df.find(t_0,t_0+t_end,dr.x49,dr.x27)
    t3,r = df.find(t_0,t_0+t_end,dr.x49,dr.x29)
    t4,dangle = df.find(t_0,t_0+t_end,dr.x49,dr.x17)
    # Plotting.
    plt.rc("figure", facecolor="white")
    plt.figure(0)
    plt.subplot(211)
    plt.plot(np.array(t1)-t_0,ph, label="Flight data", linestyle='--',c='b')
    plt.plot(T,-1.*Y[:,1], label="Numerical data",linestyle='-',c='g')
    plt.ylabel(r"Roll angle [deg]")
    plt.xlabel("Time [s]")
    plt.xlim(0,50)
    plt.legend(loc=3)
    #plt.title('Roll angle, for the actual flight and numerical simulation')
    plt.grid(True)
    plt.figure(1)
    plt.subplot(212)
    plt.plot(np.array(t4)-t_0,dangle, label="Flight data", linestyle='--',c='b')
    plt.plot(T,ip[:,0], label="Numerical data",c='g')
    plt.legend(loc=1)
    plt.ylabel(r"Aileron deflection angle [deg]")
    plt.xlabel("Time [s]")
    plt.xlim(0,50)
    plt.grid(True)
    #plt.title('Aileron deflection during the spiral motion, for the actual flight and numerical simulation')
    plt.show()
    return

# Main Method.
if __name__ == "__main__":
    plotVelocityAndElevatorDeflection()
    plotSpiralFlight()