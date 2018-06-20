import numpy as np
import matplotlib.pyplot as plt
from math import *
import Cit_params as p



# Constants
rho0   = 1.2250          # air density at sea level [kg/m^3] 
parlambda = -0.0065         # temperature gradient in ISA [K/m]
Temp0  = 288.15          # temperature at sea level in ISA [K]
R      = 287.05          # specific gas constant [m^2/sec^2K]
g      = 9.81            # [m/sec^2] (gravity constant)
P0     = 101325.         # atmospheric pressure [Pa]
Ws     = 60500.           # [N] standard aircraft mass
m_fs   = 0.048           # [kg/s] standard engine fuel flow per engine
c      = 2.0569	          # mean aerodynamic cord [m]
S      = 30.00	          # wing area [m^2]
Wempt     = 9165.   #[lbs] empty mass



### Measured data

# Stationary measurements CL-CD
hp = np.array([12000,	11090,	12000,	12000,	12050,	12050])#[ft]    Altitude
IAS = np.array([251,	217,	192,	161,	129,	112])#[kts]         Indicated airspeed
alpha = np.array([0.9,	1.9,	2.8,	4.8,	8.4,	11.4])#[deg]  Angle of attack
FFl = np.array([705,	555,	502,	414,	388,	296])#[lbs/hr]    Left fuel flow during trim curve measurement
FFr = np.array([764,	607,	573,	454,	440,	423])#[lbs/hr]    Right fuel flow during trim curve measurement
F_used = np.array([441,	531,	568,	595,	629,	652])#[lbs]       Fuel used during trim curve measurement
TAT = np.array([-5.2,	-7.5,	-10,	-12.2,	-14,	-14.8])   #[deg C] total air temperature
Wpl = np.array([82, 90,	75,	71,	62,	75,	76,	88,	85,	0,	0,	99.79]) #[kg] payload      
Wfuel = 4150. #[lbs]

# conversion factors
knot = 0.514444444  # m/s
lbs_N = 4.44822162  # N
lbs_kg = 0.45359237 # kg
inch = 0.0254       # m
feet = 0.3048       # m
inlbf = 0.112984829 # N/m

# data converted to metric
hp = hp*feet     #[m]
TAT = TAT+273.15  #[K]
Vc = IAS*knot  #calibrated airspeed [m/s]
FFl = (FFl*lbs_kg)/3600.    #[kg/s]
FFr = (FFr*lbs_kg)/3600.   #[kg/s]
Wfuel = Wfuel*lbs_N  #[N]
Wempt = Wempt*lbs_N  #[N]
Wpl = Wpl*g           #[N]
F_used = F_used*lbs_N #[N]

#Weight
Wramp = Wfuel+sum(Wpl)+Wempt #[N]
W = Wramp - F_used           #[N]

# reduction of parameters
gamma = 1.4
P = P0*(1+parlambda*hp/Temp0)**-(g/(parlambda*R))
M = np.sqrt((2./(gamma-1))*((1+(P0/P)*((1+((gamma-1)*rho0*Vc**2)/(2*gamma*P0))**(gamma/(gamma-1))-1))**((gamma-1)/gamma)-1))
T = TAT/(1+(gamma-1)*M**2/2.) #corrected total air temperature
T_isa = 273.15 + parlambda*hp
DT_isa = np.abs(T_isa-T)      #difference between ISA temperature and corrected total air temperature (needed for thrust calculation)
a = np.sqrt(gamma*R*T)        #speed of sound
Vt = M*a               #true airpeed [m/s]
rho = P/(R*T)
mu = (1.512041288*(T**(3/2))/(T+120.))*10**-6 # [(Ns)/m2] dynamic viscosity
Re = (rho*Vt*c)/mu  #Reynolds number


#Thrust

for i in range(len(hp)):
    thrustvars =  [str(hp[i]),str(M[i]),str(DT_isa[i]),str(FFl[i]),str(FFr[i])]  #needed for thrust calculation
    #print(" ".join(str(x) for x in thrustvars))

Tl = np.array([3289.47,
2435.6,
2244.44,
1794.26,
1771.47,
1158.1,])
Tr = np.array([3644.54,
2793.1,
2745.31,
2081,
2164.39,
2139.43])

Tthrust = Tl + Tr

#CL and CD
CL = W/(0.5*rho*Vt**2*S)
CD = Tthrust/(0.5*rho*Vt**2*S)
CL2 = CL**2

slope = (CD[4]-CD[1])/(CL2[4]-CL2[1])  # 1/pi*A*e
Cd0 = CD[2]-slope*CL2[2]
A = (S/c**2)         # Aspect ratio
e = 1/(pi*A*slope)
print slope
print e
print Cd0



plt.subplot(221)
plt.plot(alpha,CL,marker='o')
plt.ylabel("C_L")
plt.xlabel("alpha [deg]")
plt.title("1")

plt.subplot(222)
plt.plot(alpha,CD,marker='o')
plt.ylabel("C_D")
plt.xlabel("alpha [deg]")
plt.title("1")

plt.subplot(223)
plt.plot(CD,CL,marker='o')
plt.ylabel("C_L")
plt.xlabel("C_D")
plt.title("C_D - C_L")
plt.title("1")

plt.subplot(224)
plt.plot(CD,CL2,marker='o')
plt.ylabel("C_L^2")
plt.xlabel("C_D")
plt.title("1")

plt.show()


