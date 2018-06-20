import numpy as np
import matplotlib.pyplot as plt
from math import *
import Cit_params as p




#trim curve ---> dde/da
#cg shift with CN = W/qS----> Cmde
#dde/da and Cmde -----> Cma


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
ac_arm   = 292.18   #[inch]  aircraft moment arm


### Measured data
hp = np.array([12280,12150])#[ft]    Altitude
IAS = np.array([157,159])#[kts]         Indicated airspeed
F_used = np.array([876,902])  #[lbs] fuel used during gravity shift
de = np.array([-0.5,-1.0])#[deg]     Deflection angle
TAT = np.array([-12.8,-12.8])   #[deg C] total air temperature
Wpl = np.array([82, 90,	75,	71,	62,	75,	76,	88,	85,	0,	0,	99.79]) #[kg] payload      FIRST 9 ARE PASSENGERS, LAST 3 ARE BAGGAGE
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
Wfuel = Wfuel*lbs_N  #[N]
Wempt = Wempt*lbs_N  #[N]
Wpl = Wpl*g           #[N]
F_used = F_used*lbs_N #[N]

#Weight
Wramp = Wfuel+sum(Wpl)+Wempt #[N]
W = Wramp - F_used           #[N]Weight during gravity shift


# center of gravity
x_station = np.array([131.,131.,214.,214.,251.,251.,288.,288.,170.,74.,321.,338.])*inch #[m]
x_station2 = np.array([131.,131.,214.,214.,251.,251.,288.,131.,170.,74.,321.,338.])*inch#[m]
Dx_cg = Wpl[7]*(np.absolute(x_station2[7]/W[1]-x_station[7]/W[0]))   # [m] shift in center of gravity

x_cgdatum_ac = ac_arm*inch   # [m] cg moment arm of aircraft

M_pl = sum(x_station*Wpl)  # [Nm]   Payload Moment
M_f = 11705.5*inlbf  # [Nm] fuel moment             
M_ac = Wempt*x_cgdatum_ac       # [Nm] aircraft moment
Mtot = M_pl+M_f+M_ac    #[Nm]

x_cgdatum_BEM = M_ac/Wempt
x_cgdatum_ZFM = (M_ac+M_pl)/(Wempt+sum(Wpl))
x_cgdatum_RM = Mtot/(Wramp)

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
Ve = Vt*np.sqrt(rho/rho0)  #equivalent airspeed [m/s]
Ve_r = Ve*np.sqrt(Ws/W)     #reduced equivalent airspeed [m/s]


# Cmde calculation
Dde = np.absolute(de[1]-de[0])*(pi/180.)  # change in elevator deflection
CN = W[1]/(0.5*rho[1]*Vt[1]**2*S) 
Cmde = (-1/Dde)*CN*(Dx_cg/c)

print Cmde