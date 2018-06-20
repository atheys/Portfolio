import pickle
import numpy as np
import numpy.random as rnd
import matplotlib
import matplotlib.cm as cm
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from matplotlib.ticker import LinearLocator, FormatStrFormatter
from math import *
import itertools

def convert_to_hex(rgba_color) :                                            #definition to convert RGBA color to hexadecimal
    red = int(rgba_color[0]*255)                                            #source: http://stackoverflow.com/questions/21954792/gtk3-and-python-rgba-convert-to-hex
    green = int(rgba_color[1]*255)                                          #post by karim, Feb 22nd, 2014
    blue = int(rgba_color[2]*255)
    return '#{r:02x}{g:02x}{b:02x}'.format(r=red,g=green,b=blue)

#Get crossectional coordinates of structure

fileObject = open("Coordinates",'r')
coordinates = pickle.load(fileObject)

coordinates[2].reverse()
coordinates[3].reverse()

#Fuselage tube
xc = coordinates[0] + coordinates[2]# + [coordinates[2][-1]]
yc = coordinates[1] + coordinates[3]# + [coordinates[3][-1]]

XC = np.tile(xc,(len(z),1))                                                 #creating 2D array of the x coordinates for 3D plotting
YC = np.tile(yc,(len(z),1))                                                 #creating 2D array of the y coordinates for 3D plotting
"""
#Floor
xf = coordinates[2]
yf = coordinates[3]

XF = np.tile(xf,(len(z),1))                                                 #creating 2D array of the x coordinates for 3D plotting
YF = np.tile(yf,(len(z),1))                                                 #creating 2D array of the y coordinates for 3D plotting
"""
#Z-coordinates
z = np.asarray(coordinates[6])[:, np.newaxis]                               #list of z coordinates in 1D
                                                                            #[:, np.newaxis] transposes array z
#Fuselage tube
ZC = np.tile(z,(1,len(xc)))                                                  #creating 2D array of the z coordinates for 3D plotting
"""
#Floor
ZF = np.tile(z,(1,len(xf)))                                                  #creating 2D array of the z coordinates for 3D plotting
"""
#Get stresses on structure

fileObject = open("Superposition_shear_analytical",'r')
stress = pickle.load(fileObject)

#Fuselage tube
stress_c = list(itertools.chain.from_iterable(stress))
#stress_c = list((rnd.rand(1,XC.shape[0]*XC.shape[1]))[0])                  #list of shear or normal stresses at each coordinate
"""                                                                            #for now random values are generated
#Floor
fileObject = open("Superposition_shear_analytical",'r')
stress = pickle.load(fileObject)

stress_f = list(itertools.chain.from_iterable(stress[1]))
"""
#Coloring the surface with stresses                                             #source up to facecolors (not including facecolors):
                                                                                #http://stackoverflow.com/questions/28752727/map-values-to-colors-in-matplotlib
                                                                                #post by mfitzp, Feb 26th, 2015
#Fuselage tube
minima_c = min(stress_c)                                                      
maxima_c = max(stress_c)                                                      

norm_c = matplotlib.colors.Normalize(vmin=minima_c, vmax=maxima_c, clip=True)    #normalizing the stresses between 0 and 1
mapper_c = cm.ScalarMappable(norm=norm_c, cmap=cm.jet)                           #defining the mapping function incl. the color map

colors_c = []                                                                    #1D list of hexadecimal colors of all elements in structure

for v in stress_c:
    colors_c.append(convert_to_hex(mapper_c.to_rgba(v)))                        #finding the RGBA values using stress and mapper, and converting to hexadecimal
    
facecolors_c = np.array(colors_c)                                               #1D array with the hexadecimal colors
facecolors_c = np.reshape(facecolors_c,(XC.shape[0],XC.shape[1]))               #converted to 2D array to be able to plot in 3D
"""
#Floor
minima_f = min(stress_f)                                                      
maxima_f = max(stress_f)                                                      

norm_f = matplotlib.colors.Normalize(vmin=minima_f, vmax=maxima_f, clip=True)    #normalizing the stresses between 0 and 1
mapper_f = cm.ScalarMappable(norm=norm_f, cmap=cm.jet)                           #defining the mapping function incl. the color map

colors_f = []                                                                    #1D list of hexadecimal colors of all elements in structure

for v in stress_f:
    colors_f.append(convert_to_hex(mapper_f.to_rgba(v)))                        #finding the RGBA values using stress and mapper, and converting to hexadecimal
    
facecolors_f = np.array(colors_f)                                               #1D array with the hexadecimal colors
facecolors_f = np.reshape(facecolors_f,(XF.shape[0],XF.shape[1]))               #converted to 2D array to be able to plot in 3D
"""
#Plot the surfaces

fig = plt.figure(figsize=plt.figaspect(0.5))
"""
#Floor
ax = fig.add_subplot(1, 2, 1, projection='3d')
                      
surf_f = ax.plot_surface(XF, ZF, YF, facecolors=facecolors_c, rstride=1, cstride=1,    #rstride and cstride determine which nth element to plot
                       linewidth=1, antialiased=False)                               #1=every element plotted, 3=every third element plotted etc.

# Customize the axis
ax.set_zlim(-max(z)/2.0, max(z)/2.0)
ax.set_ylim(0, max(z))
ax.set_xlim(-max(z)/2.0, max(z)/2.0)
ax.zaxis.set_major_locator(LinearLocator(10))                                  # formats the axis really weirdly- I don't like it
ax.zaxis.set_major_formatter(FormatStrFormatter('%.02f'))                       #does something with the minus signs on the axis
"""
#Fuselage tube
ax = fig.add_subplot(1, 1, 1, projection='3d')

surf_c = ax.plot_surface(XC, ZC, YC, facecolors=facecolors_c, rstride=1, cstride=1,    #rstride and cstride determine which nth element to plot
                       linewidth=1, antialiased=True, shade = False)                               #1=every element plotted, 3=every third element plotted etc.
                       
# Customize the axis
ax.set_zlim(-max(z)/2.0, max(z)/2.0)
ax.set_ylim(0, max(z))
ax.set_xlim(-max(z)/2.0, max(z)/2.0)
#ax.zaxis.set_major_locator(LinearLocator(10))                                  # formats the axis really weirdly- I don't like it
ax.zaxis.set_major_formatter(FormatStrFormatter('%.02f'))                       #does something with the minus signs on the axis

# Add a color bar which maps values to colors.                                  #NEED TO FIX THE COLORBAR STILL
#fig.colorbar(surf, shrink=0.5, aspect=5)

plt.show()