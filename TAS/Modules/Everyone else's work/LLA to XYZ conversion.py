#Authors: Jamie Tjoe Ny & Willem Volker


from math import *
import numpy as np

def convert(lat,lon,alt):

    cosLat = np.cos(lat*pi/180)
    sinLat = np.sin(lat*pi/180)
    cosLon = np.cos(lon*pi/180)
    sinLon = np.sin(lon*pi/180)

    radius = 6378137.0

    f = (1/298.257224)

    C = 1/(np.sqrt(cosLat*cosLat+(1-f)*(1-f)*sinLat*sinLat))

    S = (1-f)*(1-f)*C

    h = alt

    x = (radius*C+h)*cosLat*cosLon

    y = (radius*C+h)*cosLat*sinLon

    z = (radius*S+h)*sinLat

    return x, y, z 


