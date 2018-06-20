# Inertia Calculation Module.
# @author: Andreas Theys / Simon Beets.
# @version: 3.0
# @note: geometric sizes primarily put in millimeters.

# Imported Modules.
# Math Library Import.
from math import pi,sqrt,sin,cos
# Structural Geometry Module Import.
from Geometry import *

# TestGeometry Module Imports (Unit tests)
# from TestGeometry1 import * # Fuselage-Only Test 
# from TestGeometry2 import * # Fuselage & Middle Floor Test
# from TestGeometry3 import * # Fuselage & Floor Test
# from TestGeometry4 import * # Full Cross-Section Test

# Determines angular stringer increment.
# @return: angular stringer increment [rad].
def stringerAngleIncrement():
    if n_st > 0.:
        return (2.*pi)/float(n_st)
    else:
        return 0.

# Determines stringer area.
# @return: stringer area [mm^2].
def stringerArea():
    return (h_st+w_st)*t_st
    
# Determines fuselage area.
# @return: fuselage area [mm^2]. 
def fuselageArea():
    return 2.*R*pi*t_s

# Determines floor width.
# @return: floor width [mm].
def floorWidth():
    return 2.*sqrt(h_f*(2.*R-h_f))
   
# Determines floor area.
# @return: floor area [mm^2].
def floorArea():
   return floorWidth()*t_f

# Determines x-centroid.
# @return: x-centroid [mm].
def centroidX():
    return 0.

# Determines y-centroid.
# @return: y-centroid [mm].
def centroidY():
    fuselage,floor = fuselageArea(),floorArea()
    stringers,stcentroid = n_st*stringerArea(),0.
    total = fuselage + stringers + floor 
    if int(n_st)%2 != 0:
        increment = stringerAngleIncrement()
        for i in range(int(n_st)):
            angle = float(i)*increment
            stcentroid += R*cos(angle)
    y = ((stringers*stcentroid)-(floor*(R-h_f)))/total
    return y

# Determines fuselage unit moment of inertia.
# @return: fuselage unit moment of inertia [mm^4].
def fuselageUnitInertia():
    return pi*t_s*R**3

# Determines fuselage moment of inertia I_xx.
# @return: fuselage moment of inertia [mm^4].
def fuselageInertiaX(centroidY):
    area,centroidY = fuselageArea(),float(centroidY)
    I_xx = fuselageUnitInertia()+area*centroidY**2
    return I_xx
 
# Determines fuselage moment of inertia I_yy.
# @return: fuselage moment of inertia [mm^4].  
def fuselageInertiaY(centroidX=0.):
    area,centroidX = fuselageArea(),float(centroidX)
    I_yy = fuselageUnitInertia()+area*centroidX**2
    return I_yy

# Determines floor unit moment of inertia I_xx.
# @return: floor unit moment of inertia [mm^4].
def floorUnitInertiaX():
    return 0.

# Determines floor unit moment of inertia I_yy.
# @return: floor unit moment of inertia [mm^4].
def floorUnitInertiaY():
    return (1./12.)*t_f*floorWidth()**3

# Determines floor moment of inertia I_xx.
# @return: floor moment of inertia [mm^4].
def floorInertiaX(centroidY):
     area,centroidY = floorArea(),float(centroidY)
     I_xx =  floorUnitInertiaX()+area*(centroidY-(R-h_f))**2
     return I_xx

# Determines floor moment of inertia I_yy.
# @return: floor moment of inertia [mm^4].
def floorInertiaY(centroidX=0.):
     area,centroidX = floorArea(),float(centroidX)
     I_yy = floorUnitInertiaY()+area*centroidX**2
     return I_yy

# Determines stringer unit moment of inertia.
# @return: stringer unit moment of inertia [mm^4].
def stringerUnitInertia():
    return 0.

# Determines stringer moment of inertia I_xx.
# @return: stringer moment of inertia [mm^4].
def stringersInertiaX(centroidY):
    increment,I_xx = stringerAngleIncrement(),0.
    for i in range(int(n_st)):
        angle = float(i)*increment
        I_xx += stringerArea()*(cos(angle)*R-centroidY)**2
    return I_xx

# Determines stringer moment of inertia I_yy.
# @return: stringer moment of inertia [mm^4].
def stringersInertiaY(centroidX=0.):
    increment,I_yy = stringerAngleIncrement(),0.
    for i in range(int(n_st)):
        angle = float(i)*increment
        I_yy += stringerArea()*(sin(angle)*R-centroidX)**2
    return I_yy 
 
# Determines overall moment of inertia I_xx.
# @return: overall moment of inertia [mm^4].  
def I_xx():
       I_xx,cY = 0.,centroidY()
       I_xx += fuselageInertiaX(cY)
       I_xx += floorInertiaX(cY)
       I_xx += stringersInertiaX(cY)
       return I_xx
 
# Determines overall moment of inertia I_yy.
# @return: overall moment of inertia [mm^4].           
def I_yy():
       I_yy,cX = 0.,centroidX()
       I_yy += fuselageInertiaY(cX)
       I_yy += floorInertiaY(cX)
       I_yy += stringersInertiaY(cX)
       return I_yy

# Determines overall moment of inertia I_xy.
# @return: overall moment of inertia [mm^4].         
def I_xy():
    return 0.