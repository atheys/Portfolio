# Coordinate Module
# @author: Andreas Theys
# @version 5.0 (numpy-optimized)
# This module functions as the main processing entity for geographic data.


# Imported modules
# Basic mathematical functions
from math import * 
# 3rd degree polynomial spline interpolation
from Math import *
# numpy for optimization purposes 
# NOTE: be careful with small size arrays!
from numpy import *


# Geographic constant(s): Earth's radius(equatorial/polar)
# NOTE: necessary for coordinate transformations (LLA --> XYZ in EFEC)
# Variables made global for use in multiple functions/modules
global eRadiusEquator
# Equatorial
eRadiusEquator = 6378100. 
global eRadiusPolar
# Polar
eRadiusPolar = 6356800.


# Conversion latitude/longitude/altitude values to x,y,z coordinates
# LLA to XYZ in EFEC conventions (WSG84 ellipsoid)
# Source: http://www.colorado.edu/geography/gcraft/notes/datum/gif/llhxyz.gif
# NOTE[1]: provided maximal latitude/longitude shifts of about 0.4 deg
# NOTE[2]: at 0.4 deg offset, the max error is |E|<=0.1m
# NOTE[3]: this transformation fullfills flat earth assumption criteria.
def convertToXY(lat,lon,alt):
    f = (eRadiusEquator-eRadiusPolar)/eRadiusEquator
    e2 = 2*f-f**2
    N = eRadiusEquator/sqrt(1-e2*sin(lat)**2)
    x = (N+alt)*cos(lat)*cos(lon)
    y = (N+alt)*cos(lat)*sin(lon)
    z = (N*(1-e2)+alt)*sin(lat)
    return x,y,z


# Determines speeds specification in all directions
# NOTE[1]: linear interpolation of speed vector
# NOTE[2]: used in InputReader module for filtering purposes
# NOTE[3]: void method --> no return value
def speeds(sortedDPoints):
    for j in arange(len(sortedDPoints)):
        for i in arange(len(sortedDPoints[j])-1):
            vec = array(sortedDPoints[j][i+1][2:5])-array(sortedDPoints[j][i][2:5])
            dt = sortedDPoints[j][i+1][0]-sortedDPoints[j][i][0]
            vec = (vec/dt).tolist()
            sortedDPoints[j][i][5] = vec[0]
            sortedDPoints[j][i][6] = vec[1]
            sortedDPoints[j][i][7] = vec[2]
    return


# Determines speeds specification in all directions
# NOTE[1]: 3rd degree polynomial splie interpolation of speed vector
# NOTE[2]: optional in InputReader module --> 9th (pseudo-)data value
# NOTE[3]: method only interesting for path efficiency calculations
def Splines(sortedDPoints):
    for j in arange(len(sortedDPoints)):
        for i in arange(len(sortedDPoints[j])-1):
            sortedDPoints[j][i].append(spline(sortedDPoints[j][i],sortedDPoints[j][i+1]))
    return


# Determines specific position for a given datapoint and look-ahead time
# NOTE[1]: Euler-based method for simple vectorial equation 
# NOTE[2]: x(t+dt) = x(t)+dt*vx(t), y(t+dt) = y(t)+dt*vy(t), z(t+dt) = z(t)+dt*vz(t)
def lookAhead(datapoint,t):
    vec = (array(datapoint[2:5])+t*array(datapoint[5:8])).tolist()
    return vec[0],vec[1],vec[2]