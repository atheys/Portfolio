import numpy as np
import matplotlib.pyplot as plt
from math import *
import Cit_params as p
from cg_shift import Cmde




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
C_m_Tc = -0.0064               # dimensionless thrust moment arm
Wempt     = 9165.   #[lbs] empty mass

### Measured data

# Stationary measurements elevator trim curve
hp = np.array([12150,	12450,	12720,	13070,	12350,	12000,	11540])#[ft]    Altitude
IAS = np.array([160,	150,	140,	130,	170,	180,	188])#[kts]         Indicated airspeed
alpha = np.array([4.8,	5.8,	6.8,	8.1,	4,	3.4,	3])#[deg]  Angle of attack
de = np.array([-0.5,	-0.9,	-1.3,	-1.9,	-0.1,	0.2,	0.4])#[deg]     Deflection angle
detr = [1]  #[deg]  Trim tab angle
Fe = np.array([-1,	-14,	-27,	-36,	21,   47,		80])    #[N]    Control force
FFl = np.array([415,	411,	406,	402,	420,	427,	434])#[lbs/hr]    Left fuel flow during trim curve measurement
FFr = np.array([471,	466,	461,	453,	471,	479,	486])#[lbs/hr]    Right fuel flow during trim curve measurement
F_used = np.array([723,	744,	762,	795,	813,	830,	849])#[lbs]       Fuel used during trim curve measurement
TAT = np.array([-12.8,	-13.8,	-13.8,	-14.5,	-12,	-11,	-10])   #[deg C] total air temperature
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
T_isa = Temp0 + parlambda*hp
DT_isa = np.abs(T_isa-T)      #difference between ISA temperature and corrected total air temperature (needed for thrust calculation)
a = np.sqrt(gamma*R*T)        #speed of sound
Vt = M*a               #true airpeed [m/s]
rho = P/(R*T)
Ve = Vt*np.sqrt(rho/rho0)  #equivalent airspeed [m/s]
Ve_r = Ve*np.sqrt(Ws/W)     #reduced equivalent airspeed [m/s]
Fe_r = Fe*(Ws/W)     #reduced control force


#Thrust
for i in range(len(hp)):
    thrustvars =  [str(hp[i]),str(M[i]),str(DT_isa[i]),str(FFl[i]),str(FFr[i])]  #needed for thrust calculation
    #print(" ".join(str(x) for x in thrustvars))


Tthrust = np.array([1803.97+	2205.47,
1839.88	+2239.82,
1873.1	+2282.33,
1914.48	+2300.49,
1799.29	+2159.55,
1782.6	+2144.98,
1771.04	+2128.85
])

Tc = Tthrust/(0.5*rho*Vt**2*c) #thrust coefficient

Tstandard = np.array([1559.84+	1559.84,
1620.49+	1620.49,
1687.72	+1687.72,
1757.46	+1757.46,
1523.75	+1523.75,
1463.44	+1463.44,
1408.3	+1408.3
]) # Thrust calculated using standard fuel flow

Tc_s = Tstandard/(0.5*rho*Vt**2*c) # dimensionless standard thrust coefficient



de_r = de*(np.pi/180.) - (1/Cmde)*C_m_Tc*(Tc_s-Tc) #reduced elevator deflection

# Reduced elevator control force curve and trim curve

plt.plot(sorted(Ve_r),-1.*np.array(sorted(Fe_r)),marker='o') #MENTION CG and TRIM TAB
plt.ylabel("Fe_r (-)     [N]")
plt.xlabel("Ve_r [m/s]")
plt.title("Reduced control force Fe_r - Reduced equivalent airspeed Ve_r (d_te = 2.7 deg)")
plt.show()

plt.plot(sorted(Ve_r),-1.*np.array(sorted(de_r)),marker='o')
plt.ylabel("de_r (-)     [rad]")
plt.xlabel("Ve_r [m/s]")
plt.title("Reduced deflection angle de_r - Reduced equivalent airspeed Ve_r (d_te = 2.7 deg)")
plt.show()

# Cma calculation

plt.plot(sorted(alpha),-1.*np.array(sorted(de,reverse=True)),marker='o')
plt.ylabel("de (-)   [deg]")
plt.xlabel("alpha [deg]")
plt.title("Deflection de  - Angle of attack alpha (d_te = 2.7 deg)")
plt.show()

dde_da = (de[2]-de[0])/(alpha[2]-alpha[0])   # slope of elevator trim curve
Cma = -dde_da*Cmde


