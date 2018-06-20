# Reaction Forces Module.
# @author: Andreas Theys / Simon Beets.
# @version: 4.0
# @note: no imported modules.

# Imported Modules.
# Pyplot Library Import.
import matplotlib.pyplot as plt
# Numpy Library Import.
import numpy as np 
# Inertia Moment Module Import.
from Inertia import centroidY
# Structural Geometry Module Import. 
from Geometry import *

# TestGeometry Module Imports (Unit tests)
# from TestGeometry5 import * # Seperat Load Case Test 

# Computes the uniformly distributed load q.
# @return: uniformly distributed load [N/m].
def q():
    return (3.*g*W)/L

# Computes horizontal reaction force in leftmost landing gear.
# @return : reaction force in leftmost landing gear [N].
def F_x2():
    return -Sx*((L-L_f1+d_tz)/(2.*L_f2))

# Computes horizontal reaction force in rightmost landing gear.
# @return : reaction force in righmost landing gear [N].
def F_x3():
    return F_x2()

# Computes horizontal reaction force in front landing gear.
# @return : reaction force in front landing gear [N].
def F_x1():
    return -Sx-2.*F_x2()

# Computes veritcal reaction force in front landing gear.
# @return : reaction force in front landing gear [N].
def F_y1():
    return q()*L*((L_f1+L_f2-0.5*L)/L_f2)

# Computes vertical reaction force in leftmost landing gear.
# @return : reaction force in leftmost landing gear [N].
def F_y2():
    return -Sx*((d_ty+d_lgy)/L_f3)+0.5*(q()*L-F_y1())

# Computes vertical reaction force in rightmost landing gear.
# @return : reaction force in righmost landing gear [N].
def F_y3():
    return q()*L-F_y1()-F_y2()

# Computes horizontal shear force in function of z.
# @param: [z] position along the fuselage [m].
# @return : horizontal shear force [N].
def V_x(z):
    if 0.<=z and z<(L-L_f1-L_f2):
        return Sx
    elif (L-L_f1-L_f2)<=z and z<(L-L_f1):
        return Sx+F_x2()+F_x3()
    elif (L-L_f1)<=z and z<=L:
        return Sx+F_x1()+F_x2()+F_x3()
    else:
        return 0.

# Computes veritcal shear force in function of z.
# @param: [z] position along the fuselage [m].
# @return : vertical shear force [N].
def V_y(z):
    if 0.<=z and z<(L-L_f1-L_f2):
        return q()*z
    elif (L-L_f1-L_f2)<=z and z<(L-L_f1):
        return q()*z - F_y2() - F_y3()
    elif (L-L_f1)<=z and z<=L:
        return q()*z - F_y1() - F_y2() - F_y3()
    else:
        return 0.

# Computes moment around x in function of z.
# @param: [z] position along the fuselage [m].
# @return : moment around x [Nm].
def M_x(z):
    if 0.<=z and z<=(L-L_f1-L_f2):
        return 0.5*q()*z**2
    elif (L-L_f1-L_f2)<z and z<=(L-L_f1):
        return 0.5*q()*z**2-(F_y2()+F_y3())*(z-L+L_f1+L_f2)
    elif (L-L_f1)<z and z<=L:
        return 0.5*q()*z**2-F_y1()*(z-L+L_f1)-(F_y2()+F_y3())*(z-L+L_f1+L_f2)
    else:
        return 0.

# Computes moment around y in function of z.
# @param: [z] position along the fuselage [m].
# @return : moment around y [Nm].        
def M_y(z):
    if 0.<=z and z<=(L-L_f1-L_f2):
        return Sx*(z+d_tz)
    elif (L-L_f1-L_f2)<z and z<=(L-L_f1):
        return Sx*(z+d_tz)+(F_x2()+F_x3())*(z-L+L_f1+L_f2)
    elif (L-L_f1)<z and z<=L:
        return Sx*(z+d_tz)+F_x1()*(z-L+L_f1)+(F_x2()+F_x3())*(z-L+L_f1+L_f2)
    else:
        return 0.

# Computes moment around z in function of z.
# @param: [z] position along the fuselage [m].
# @return : moment around z [Nm].
def T(z):
    if 0.<=z and z<(L-L_f1-L_f2):
        return Sx*(d_ty-R/1000.-centroidY()/1000.)
    elif (L-L_f1-L_f2)<=z and z<(L-L_f1):
        return Sx*(d_ty-R/1000.-centroidY()/1000.)-(F_x2()+F_x3())*(R/1000.+d_lgy+centroidY()/1000.) \
                +0.5*(F_y2()-F_y3())*L_f3
    elif (L-L_f1)<=z and z<=L:
        return Sx*(d_ty-R/1000.-centroidY()/1000.)-(F_x1()+F_x2()+F_x3())*(R/1000.+d_lgy+centroidY()/1000.) \
                +0.5*(F_y2()-F_y3())*L_f3
    else:
        return 0.

# Makes horizontal shear force diagram.      
def V_x_diagram():
    points = []
    for i in np.arange(0.,L+0.1,0.1):
        points.append(V_x(float(i)))
    plt.figure(0)
    # plt.title("Shear Force Diagram in x")
    plt.xlabel("z [m]",fontsize=12.)
    plt.ylabel("V_x [N]",fontsize=12.) 
    plt.axhline(0.,ls="--",lw=0.5)
    plt.ylim(1.05*min(points),1.05*max(points))
    plt.plot(np.arange(0.,L+0.1,0.1),points)
    plt.show()

# Makes vertical shear force diagram.    
def V_y_diagram():
    points = []
    for i in np.arange(0.,L+0.1,0.1):
        points.append(V_y(float(i)))
    plt.figure(1)
    # plt.title("Shear Force Diagram in y)")
    plt.xlabel("z [m]",fontsize=12.)
    plt.ylabel("V_y [N]",fontsize=12.) 
    plt.axhline(0.,ls="--",lw=0.5)
    plt.ylim(1.05*min(points),1.05*max(points))
    plt.plot(np.arange(0.,L+0.1,0.1),points)
    plt.show()    
 
# Makes moment diagram around x.       
def M_x_diagram():
    points = []
    for i in np.arange(0.,L+0.1,0.1):
        points.append(M_x(float(i)))
    plt.figure(2)
    #plt.title("Moment Diagram around x")
    plt.xlabel("z [m]",fontsize=12.)
    plt.ylabel("M_x [Nm]",fontsize=12.) 
    plt.axhline(0.,ls="--",lw=0.5)
    plt.ylim(1.05*min(points),1.05*max(points))
    plt.plot(np.arange(0.,L+0.1,0.1),points)
    plt.show()
 
# Makes moment diagram around y.   
def M_y_diagram():
    points = []
    for i in np.arange(0.,L+0.1,0.1):
        points.append(M_y(float(i)))
    plt.figure(3)
    #plt.title("Moment Diagram around y")
    plt.xlabel("z [m]",fontsize=12.)
    plt.ylabel("M_y [Nm]",fontsize=12.) 
    plt.axhline(0.,ls="--",lw=0.5)
    plt.ylim(1.05*min(points),1.05*max(points))
    plt.plot(np.arange(0.,L+0.1,0.1),points)
    plt.show()

# Makes moment diagram around z.  
def T_diagram():
    points = []
    for i in np.arange(0.,L+0.1,0.1):
        points.append(T(float(i)))
    plt.figure(4)
    #plt.title("Torsion")
    plt.xlabel("z [m]",fontsize=12.)
    plt.ylabel("T [Nm]",fontsize=12.) 
    plt.axhline(0.,ls="--",lw=0.5)
    plt.ylim(1.05*min(points),1.05*max(points))
    plt.plot(np.arange(0.,L+0.1,0.1),points)
    plt.show()

# Main Method.
if __name__ == "__main__":    
    V_x_diagram()
    V_y_diagram()
    M_x_diagram()
    M_y_diagram()
    T_diagram()