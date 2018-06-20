import pickle
import numpy as np
import matplotlib.pyplot as plt
import Geometry as geo
from math import *

#inputs
f_step = 0.2                                #step along floor [m]
points = 40.0                               #number of points around the fuselage - ENTER ONLY EVEN INTEGER VALUES
z_step = 0.5                                  #z-step [m]

#inputs imported from other programs
r = geo.R / 1000.                           #radius of fuselage [m]
hf = geo.h_f / 1000.                        #floor height [m]
z_len = geo.L                               #length of structure [m]
no_str = geo.n_st                           #number of stringers [-]

dtheta = 2*pi/points                        #radial angular step [rad] - change only the integer that divides the 2*pi - THE INTEGER CAN ONLY BE EVEN
c_step = dtheta*r
print c_step, 'm'                           #step in radial direciton [m]




#z coordinates
z = list(np.arange(0,z_len,z_step))

#Circumferential coordinates
theta = np.arange(0,2.0*pi,dtheta)          #array of angles for coordinates around circumference

xc = list(r*np.cos(theta))                  #array of x-coordinates around circumference
yc = list(r*np.sin(theta)+r)                #array of y-coordinates around circumference

#Floor coordinates
alpha = 2.0*acos((r-hf)/r)                  #angle between floor and fuselage contact points
beta = 2*pi - alpha                         #complementary angle

lf = 2*r*sin(alpha/2.0)                     #floor length

xf = list(np.arange(-lf/2.0,lf/2.0,f_step)) #floor x-coordinates
yf = list((np.ones((1,len(xf)))*hf)[0])     #floor y-coordinates

#Stringer/Boom coordinates
dtheta_str = 2.0*pi/no_str                  #radial angular step of stringers/booms [rad]
theta_str = np.arange(0,2.0*pi,dtheta_str)  #array of angles for coordinates around circumference for stringers/booms
xs = list(r*np.cos(theta_str))              #array of x-coordinates around circumference for stringers/boooms
ys = list(r*np.sin(theta_str)+r)            #array of y-coordinates around circumference for stringers/boooms

xc.append(xc[0])
yc.append(yc[0])
xs.append(yc[0])
ys.append(yc[0])

coordinates = [xc,yc,xf,yf,xs,ys,z]           #list of x an y coordinates of fuselage tube, floor, and stringers respectively

#Saving to file 
file_Name = "Coordinates"
fileObject = open(file_Name,'wb')
pickle.dump(coordinates,fileObject)
fileObject.close()

#Plotting against you
fig = plt.figure()                                          #defines figure
ax = fig.add_subplot(1,1,1)                                 #defines axis
plt.scatter(xc,yc,s=10, edgecolors='g', marker='o')         #plots location of fuselage tube
plt.scatter(xf,yf,s=10, edgecolors='b', marker='s')         #plots location of floor
plt.scatter(xs,ys,s=100, facecolors='none', edgecolors='r') #plots locations of stringers/booms
for i in range(len(xc)):
    ax.annotate(i, (xc[i],yc[i]))
for i in range(len(xf)):
    ax.annotate(i, (xf[i],yf[i]))
ax.set_aspect('equal')                                      #sets axis to be equal in x and y so that imgae is not stretched
plt.show()                                                  #show plot

