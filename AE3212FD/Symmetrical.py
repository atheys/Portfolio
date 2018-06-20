# Symmetrical Flight Simulation Module.
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Imported Module.
# Control Package Import.
import control as c
# Numpy Library Module Import.
import numpy as np
# Pyplot Library Module Import.
import matplotlib.pyplot as plt
# StateSpace Module Import.
from StateSpace import S_ss as ss
# Paramter Module Import.
import Cit_params as cp
# Math Library Module Import.
from math import pi

# Sets up time array.
# @param: [t_0] begin time (sec) [float].
# @param: [t_end] end time (sec) [float].
# @param: [dt] time increment (sec) [float].
# @return: time array [np.array].
def setupTimeArray(t_0,t_end,dt):
    t_0,t_end,dt = float(t_0),float(t_end),float(dt)
    return np.arange(t_0,t_end+dt,dt)

# Determines the nitial attitude condition for the attitude.
# @note: the following order is upheld: u,alpha,theta,qc/V.
# @param: [u_0] initial propagation velocity (m/s) [float].
# @param: [a_0] initial angle of attack (deg) [float].
# @param: [th_0] initial pitch angle (deg) [float].
# @param: [q_0] initial q*c/V  [float].
# @return: initial attitude [np.array].
def initialAttitude(u_0,a_0,th_0,q_0):
     u_0,a_0,th_0,q_0 = float(u_0),float(a_0),float(th_0),float(q_0)
     return np.array([u_0,a_0,th_0,q_0])

# Simulates step response.
# @param: [t] time array [np.array].
# @param: [x] initial attitude [np.array].
# @return: [steps] step-wise response [np.array].
# @return: [T] output time array [np.array].
# @return: [X] output attitude array [np.array].
def stepResponse(t,x):
    inp = np.zeros((len(t)))
    for index in range(len(t)):
        if t[index] < 10.0:
            inp[index] = 1.
    steps,T,X = c.lsim(ss,U=inp,T=t,X0=x)
    return steps,T,X

# Modify the outputs to get V & q.
# @param: [steps] modified step-wise response [np.array].
# @return: [steps] modified step-wise response [np.array].
def modifyOutput(steps):
    for i in steps:
        i[0] = i[0] + cp.V0
        i[1] = i[1]
        i[2] = i[2] 
        i[3] = i[3]*cp.V0/cp.c
    return steps

# Plots Responses (with subplots command).
def plotResponse1(T,steps):
    plt.figure(0)
    ax,x0,y0 = [],0.12,-0.025
    plt.rc("figure",facecolor="white")
    # Plots the velocity of the a/c
    ax.append(plt.subplot(211))
    plt.title(r"Velocity and $\alpha$ under a step input")
    plt.plot(T,steps[:,0],c='r',label="Velocity")
    ax[0].set_ylabel('V [m/s]', color='r')
    ax[0].set_xlabel('t [s]')
    ax[0].xaxis.set_label_coords(x0, y0)
    ax[0].tick_params('y', colors='r')
    ax[0].grid(b=True)
    # Plots Angle of Attack
    ax.append(ax[0].twinx())
    plt.plot(T,steps[:,0],c='b', label=r"$\alpha$")
    ax[1].set_ylabel(r'$\alpha$ [$\degree$]', color='b')
    ax[1].tick_params('y', colors='b')
    ax[1].set_xlabel('t [s]')
    ax[1].xaxis.set_label_coords(x0, y0)
    ax[1].grid(b=True)
    # Plots Pitch Angle
    ax.append(plt.subplot(212))
    plt.title(r"$\theta$ and pitch rate under a step input")
    plt.plot(T,steps[:,2],c='r', label=r'$\theta$')
    ax[2].set_ylabel(r'$\theta$ [$\degree$]', color='r')
    ax[2].tick_params('y', colors='r')
    ax[2].set_xlabel('t [s]')
    ax[2].xaxis.set_label_coords(x0, y0)
    ax[2].grid(b=True,which='both')
    # Plots Pitch Rate
    ax.append(ax[2].twinx())
    plt.plot(T,steps[:,3],c='b', label="Pitch rate")
    ax[3].set_ylabel(r'q [$\degree$ /s]', color='b')
    ax[3].set_xlabel('t [s]')
    ax[3].xaxis.set_label_coords(x0, y0)
    ax[3].tick_params('y', colors='b')
    ax[3].grid(b=True,which='both')
    plt.axes(ax[3])
    plt.show()
    return

# Plots Responses (separate plots).
def plotResponse2(T,steps):
    plt.figure(0)
    plt.rc("figure",facecolor="white")
    # Plots the velocity of the a/c
    ax = [plt.subplot(211)]
    plt.plot(T,steps[:,0],c='b',label="Velocity")
    ax[0].set_ylabel('V [m/s]', color='b')
    ax[0].set_xlabel('Time [s]')
    ax[0].tick_params('y', colors='b')
    ax[0].grid(b=True)
    # Plots Angle of Attack
    ax.append(ax[0].twinx())
    plt.plot(T,steps[:,1],c='g', label=r"$\alpha$")
    ax[1].set_ylabel(r'Angle of attack [deg]', color='g')
    ax[1].tick_params('y', colors='g')
    #ax[1].set_xlabel('t [s]')
    #ax[1].xaxis.set_label_coords(x0, y0)
    plt.xlabel('Time [s]')
    #ax[1].grid(b=True)
    # Plots Pitch Angle
    plt.figure(1)
    ax = [plt.subplot(211)]
    plt.plot(T,steps[:,2],c='b', label=r'$\theta$')
    ax[0].set_ylabel('Pitch angle [deg]', color='b')
    ax[0].tick_params('y', colors='b')
    ax[0].set_xlabel('Time [s]')
    ax[0].grid(b=True)
    # Plots Pitch Rate
    ax.append(ax[0].twinx())
    plt.plot(T,steps[:,3],c='g', label="Pitch rate")
    ax[1].set_ylabel(r'Roll rate  [deg/s]', color='g')
    ax[1].tick_params('y', colors='g')
    plt.axes(ax[1])
    plt.xlabel('Time [s]')
    plt.show()
    return

# Determines left-over response function properties.
# @param: [T] output time array [np.array].
# @param: [steps] step-wise response [np.array].
# @return: [peaks] function peak value [np.array].
# @return: [Period] function period measure [float].
# @return: [eta] eta-factor [float].
def functionProperties(T,steps):
    peaks = [[],[],[]]
    TNew = list(T)
    bigger_than = True
    LastValue = steps[:,0][0]
    for i in range(0,steps[:,2].size -1):
        ThisValue = steps[:,2][i]
        if (bigger_than):
            if (ThisValue > LastValue):
                peaks[0].append(i)
                peaks[1].append(ThisValue)
                peaks[2].append(TNew[i])
                bigger_than = False
        else:
            if (ThisValue < LastValue):
                peaks[0].append(i)
                peaks[1].append(ThisValue)
                peaks[2].append(TNew[i])
                bigger_than = True
        LastValue = ThisValue
    peaks = np.array(peaks)
    Period = peaks[2][peaks[2].size-1]-peaks[2][peaks[2].size-2]
    eta = 2 * pi / Period * cp.c / cp.V0
    return peaks,Period,eta

# Main Method
if __name__ == "__main__":
    t = setupTimeArray(0.0,200.0,0.1) 
    x = initialAttitude(0.,cp.alpha0,cp.th0,0.)   
    steps,T,X = stepResponse(t,x)
    steps = modifyOutput(steps)
    peaks,Period,eta = functionProperties(T,steps)
    plotResponse1(T,steps)
    plotResponse2(T,steps)