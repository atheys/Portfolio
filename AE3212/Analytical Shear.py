import pickle
import numpy as np
import copy
from math import *
from Inertia import I_xx,I_yy
from Reactions import V_x, V_y
import Geometry as geo
import matplotlib.pyplot as plt

#Shear

fileObject = open("Coordinates",'r')
coordinates = pickle.load(fileObject)

#Inputs
lf = 2*sqrt(geo.R**2-(geo.R-geo.h_f)**2) #length floor
Af = geo.t_f*lf #Floor area
centroid = -Af*(geo.R-geo.h_f)/(2*pi*geo.R*geo.t_s+Af)
yc = centroid+ geo.R #y-coordinate centroid from bottom [mm]



Ixx = I_xx() #Moment of inertia [m^4]
Iyy = I_yy()
y=list((np.array(coordinates[1]))*1000)
x=list(np.array(coordinates[0])*1000)
yf=list((np.array(coordinates[3]))*1000)
xf=list(np.array(coordinates[2])*1000)
z=list(np.array(coordinates[6]))
qsv=[]
qsh=[]
qfv=[]
qfh=[]

#Vertical Shear Force
for i in range(len(z)/15):
    qsv_temp = []
    qfv_temp = []
    for d in xrange(-len(y)/4, len(y)/4):
        #print d
        if y[d] <= (2*geo.R) and y[d] >= (geo.h_f): #Calculation from top to floor
            alpha = np.arccos((y[d]-geo.R)/geo.R)
            if alpha == 0:
                Ap=0
                ycp=2*geo.R
            else:
                theta = 2*alpha #included angle of crosssection
                Ap = theta*geo.R*geo.t_s #A'
                #print alpha
                ycp = geo.R*sin(alpha)/alpha - centroid #centroid of A' from yc
        elif (y[d] < (geo.h_f) and y[d] >= (0)): #Calculation below floor
            alpha = np.arccos((y[d]-geo.R)/geo.R)
            #print alpha
            theta = 2*alpha #included angle of crosssection   
            Aarc = theta*geo.R*geo.t_s #Arc Area
            Ap = Aarc + Af #A' 
            ycp = ((geo.R*sin(alpha)/alpha)*Aarc + (geo.h_f-geo.R)*Af)/Ap - centroid #centroid of A'
        else:
            print "Out of y boundary!"
            
        Q = Ap *ycp #Moment of Area
        #print y[d]
        
        qsv_temp.append(V_y(z[i])*Q/Ixx*1000)
    qsv_temp2 = copy.copy(qsv_temp)
    qsv_temp2 = qsv_temp2[0:-1]
    qsv_temp2.reverse()
    qsv_temp3 = qsv_temp + qsv_temp2
    qsv.append(qsv_temp3) #Shear flow by vertical force
        
    #Floor
    for d in xrange(len(yf)):
        alpha = np.arccos((yf[d]-geo.R)/geo.R)
        #print alpha
        theta = 2*alpha #included angle of crosssection   
        Aarc = theta*geo.R*geo.t_s #Arc Area
        Ap = Aarc + Af #A' 
        ycp = ((geo.R*sin(alpha)/alpha)*Aarc + (geo.h_f-geo.R)*Af)/Ap - centroid #centroid of A'
        Q = Ap *ycp #Moment of Area
        qfv_temp.append(V_y(z[i])*Q/Ixx*1000)
    qfv.append(qfv_temp) #Shear flow by vertical force

    
#Horizontal Shear Force
for i in range(len(z)):
    qsh_temp = []
    qfh_temp = []
    for d in xrange(0, len(x)/2+1):
        #print d
        if x[d]>=-geo.R and x[d]<=geo.R:
            alpha = np.arccos((x[d])/geo.R)
            if alpha == 0:
                Ap=0
            else:
                theta = 2*alpha #included angle of crosssection
                Aarc = theta*geo.R*geo.t_s #Arc Area
                Afp = geo.t_f*(geo.R-x[d]) #Area of Floor piece
                Ap = Aarc + Afp #A' 
                ycp = ((geo.R*sin(alpha)/alpha)*Aarc + (x[d]+(geo.R-x[d])/2)*Afp)/Ap           
        else:
            print "Out of x boundary!"
            
        Q = Ap *ycp #Moment of Area
        
        qsh_temp.append(V_x(z[i])*Q/Ixx*1000)
    qsh_temp2 = copy.copy(qsh_temp)
    qsh_temp2 = qsh_temp2[0:-1]
    qsh_temp2.reverse()
    qsh_temp3 = qsh_temp + qsh_temp2
    qsh.append(qsh_temp3) #Shear flow by vertical force
        
    #Floor
    for d in xrange(len(xf)):
        alpha = np.arccos((xf[d])/geo.R)
        theta = 2*alpha #included angle of crosssection
        Aarc = theta*geo.R*geo.t_s #Arc Area
        Afp = geo.t_f*(geo.R-xf[d]) #Area of Floor piece
        Ap = Aarc + Af #A' 
        ycp = ((geo.R*sin(alpha)/alpha)*Aarc + (xf[d]+(geo.R-xf[d])/2)*Afp)/Ap
        Q = Ap *ycp #Moment of Area
        qfh_temp.append(V_x(z[i])*Q/Iyy*1000) #Shear flow by horizontal force
    qfh.append(qfh_temp)
#qsv=qsv[len(qsv)/4:]+qsv[:len(qsv)/4]      
#qsv=qsv[int(ceil(len(qsv)/4.0)):]+qsv[:int(ceil(len(qsv)/4.0))]  
shear = [qsh, qsv, qfh, qfv]

#Saving to file 
file_Name = "Shear_analytical"
fileObject = open(file_Name,'wb')
pickle.dump(shear,fileObject)
fileObject.close()

#plt.plot(y,qsv[6])
print 'done'