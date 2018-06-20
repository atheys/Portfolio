# Asymmetrical Flight Simulation Module.
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
from StateSpace import A_ss as ss
# Paramter Module Import.
import Cit_params as cp
# Symmetric Motion Module Import.
from Symmetrical import setupTimeArray as sta

# Time array.
t_0,t_end,dt = 0.,30.,0.01
t = sta(t_0,t_end,dt)
#Initial conditions.
b_0,vp_0,p_0,r_0 = 0.,0.,0.,0
x_0 = np.array([b_0,vp_0,p_0,r_0])
ip,flag = np.zeros((len(t),2)),0.
flag = 0. # 1 for aileron, else rudder
for i in range(len(t)):
    if t[i]<5.0:
        if flag==1:
            ip[i,0] = 1.
            ip[i,1] = 0.
        else:
            ip[i,0] = 0.
            ip[i,1] = 1.
# Simulate step response. 
Y,T,X = c.lsim(ss,U=ip,T=t,X0=x_0)
for i in Y:
    i[2]*= 2.*float(cp.V0)/float(cp.b)
    i[3]*= 2.*float(cp.V0)/float(cp.b)

# Plots.
plt.figure(0)
ax = []
plt.rc("figure",facecolor="white")
x_0,y_0 = 0.09,-0.025
# Plots the yaw angle
plt.figure(0)
ax.append(plt.subplot(211))
# plt.title(r"$\beta$ and $\varphi$ under a aileron step input")
# plt.title(r"$\beta$ and $\varphi$ under a rudder step input")
plt.plot(T,Y[:,0],c='b',label="Velocity")
ax[0].set_ylabel(r'Sideslip angle [deg]', color='b')
ax[0].tick_params('y', colors='b')
ax[0].set_xlabel('Time [s]')
ax[0].grid(b=True,which='both')
#Plots roll angle
ax.append(ax[0].twinx())
plt.plot(T,Y[:,1],c='g', label=r"$\alpha$")
ax[1].set_ylabel('Heading [deg]', color='g')
ax[1].tick_params('y', colors='g')
# Plots the pitch angle.
plt.figure(1)
ax.append(plt.subplot(212))
# plt.title(r"roll and yaw rate under a aileron step input")
# plt.title(r"roll and yaw rate under a rudder step input")
plt.plot(T,Y[:,2],c='b', label=r'$\theta$')
ax[2].set_ylabel(r'Roll rate [deg/s]', color='b')
ax[2].tick_params('y', colors='b')
ax[2].set_xlabel('Time [s]')
ax[2].grid(True,which='both')
#Plots the pitch rate
ax.append(ax[2].twinx())
plt.plot(T,Y[:,3],c='g', label="Pitch rate")
ax[3].set_ylabel(r'Yaw Rate [deg/s]', color='g')
ax[3].xaxis.set_label_coords(x_0, y_0)
ax[3].tick_params('y', colors='g')
ax[3].grid(b=True,which='both')
plt.axes(ax[3])
plt.xlabel('Time[s]')
plt.show()