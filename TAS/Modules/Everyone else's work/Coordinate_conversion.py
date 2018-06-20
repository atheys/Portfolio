#Authors: Jamie Tjoe Ny & Willem Volker

#Based on WGS84 

from math import *

lat = input("Latitude [deg]:")
lon = input("Longitude[deg]:")
alt = input("Altitude[m]:")

cosLat = cos(lat*pi/180)
sinLat = sin(lat*pi/180)
cosLon = cos(lon*pi/180)
sinLon = sin(lon*pi/180)

radius = 6378137.0

f = (1/298.257224)

C = 1/(sqrt(cosLat*cosLat+(1-f)*(1-f)*sinLat*sinLat))

S = (1-f)*(1-f)*C

h = alt

x = (radius*C+h)*cosLat*cosLon

y = (radius*C+h)*cosLat*sinLon

z = (radius*S+h)*sinLat

print x, y, z 
