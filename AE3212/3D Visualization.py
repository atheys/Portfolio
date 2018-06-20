import pickle
import numpy as np
import numpy.random as rnd
import matplotlib
import matplotlib.cm as cm
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from matplotlib.ticker import LinearLocator, FormatStrFormatter
from math import *

def convert_to_hex(rgba_color) :                                            #definition to convert RGBA color to hexadecimal
    red = int(rgba_color[0]*255)                                            #source: http://stackoverflow.com/questions/21954792/gtk3-and-python-rgba-convert-to-hex
    green = int(rgba_color[1]*255)                                          #post by karim, Feb 22nd, 2014
    blue = int(rgba_color[2]*255)
    return '#{r:02x}{g:02x}{b:02x}'.format(r=red,g=green,b=blue)

#Get crossectional coordinates of structure
fileObject = open("Coordinates",'r')
coordinates = pickle.load(fileObject)

#Creating the coordinates for the fuselage tube 3D surface
zees = 15                                                                  #length of structure [m]
z_step = 0.8                                                              #z-step [m]

xc = coordinates[0]
xc.append(xc[0])
yc = coordinates[1]
yc.append(yc[0])
z = np.arange(0,zees,z_step)[:, np.newaxis]                          #list of z coordinates in 1D
                                                                            #[:, np.newaxis] transposes array z

XC = np.tile(xc,(len(z),1))                                                 #creating 2D array of the x coordinates for 3D plotting
YC = np.tile(yc,(len(z),1))                                                 #creating 2D array of the y coordinates for 3D plotting
Z = np.tile(z,(1,len(xc)))                                                  #creating 2D array of the z coordinates for 3D plotting

#Get stresses on fuselage tube
"""
fileObject = open("Fuselage_Stresses",'r')
stress_c = pickle.load(fileObject)
"""
stress_c = list((rnd.rand(1,XC.shape[0]*XC.shape[1]))[0])                   #list of shear or normal stresses at each coordinate
                                                                            #for now random values are generated

#Coloring the surface with stresses                                         #source up to facecolors (not including facecolors):
minima = min(stress_c)                                                      #http://stackoverflow.com/questions/28752727/map-values-to-colors-in-matplotlib
maxima = max(stress_c)                                                      #post by mfitzp, Feb 26th, 2015

norm = matplotlib.colors.Normalize(vmin=minima, vmax=maxima, clip=True)     #normalizing the stresses between 0 and 1
mapper = cm.ScalarMappable(norm=norm, cmap=cm.jet)                          #defining the mapping function incl. the color map

colors = []                                                                 #1D list of hexadecimal colors of all elements in structure

for v in stress_c:
    colors.append(convert_to_hex(mapper.to_rgba(v)))                        #finding the RGBA values using stress and mapper, and converting to hexadecimal
    
facecolors = np.array(colors)                                               #1D array with the hexadecimal colors
facecolors = np.reshape(facecolors,(XC.shape[0],XC.shape[1]))               #converted to 2D array to be able to plot in 3D

# Plot the surface
fig = plt.figure()
ax = fig.gca(projection='3d')

surf = ax.plot_surface(XC, Z, YC, facecolors=facecolors, rstride=1, cstride=1,  #rstride and cstride determine which nth element to plot
                       linewidth=1, antialiased=True)                           #1=every element plotted, 3=every third element plotted etc.

# Customize the axis
ax.set_zlim(-max(z)/2.0, max(z)/2.0)
ax.set_ylim(0, max(z))
ax.set_xlim(-max(z)/2.0, max(z)/2.0)
#ax.zaxis.set_major_locator(LinearLocator(10))                                  # formats the axis really weirdly- I don't like it
ax.zaxis.set_major_formatter(FormatStrFormatter('%.02f'))                       #does something with the minus signs on the axis

# Add a color bar which maps values to colors.                                  #NEED TO FIX THE COLORBAR STILL
#fig.colorbar(surf, shrink=0.5, aspect=5)

plt.show()