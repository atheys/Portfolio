# DataReader Module.
# @author: Group 21/03/2017.
# @version: 2.0

# Imported Modules.
# Scipy Library Module Import.
import scipy.io as sio

# Data File Reading.
fileName = './FTISxprt-20170321_125024.mat'
var = sio.loadmat(fileName)
# Data variable.
data = var['flightdata'][0][0]

# Obtain a list with the order of vars
names = []
for i in range(len(data)):
    names.append(data[i][0][0][-1])

# Assign right arrays to the vars.
alpha = data[0][0][0][0]   # Angle of attack.
x2 = data[1][0][0][0]      # Deflection of elevator trim.
x3 = data[2][0][0][0]      # Force on elevator control wheel.
x4 = data[3][0][0][0]      # Fuel mass flow engine 1.
x5 = data[4][0][0][0]      # Fuel mass flow engine 2.
x6 = data[5][0][0][0]      # Inter turbine Temperature engine 1.
x7 = data[6][0][0][0]      # Inter turbine Temperature engine 2.
x8 = data[7][0][0][0]      # Oil pressure engine 1.
x9 = data[8][0][0][0]      # Oil pressure engine 2.
x10 = data[9][0][0][0]     # Deflection of control column.
x11 = data[10][0][0][0]    # Fan speed (N1) engine 1.
x12 = data[11][0][0][0]    # Turbine speed (N2) engine 1.
x13 = data[12][0][0][0]    # Fan speed(N2) engine 2.
x14 = data[13][0][0][0]    # Turbine speed (N2) engine 2.
x15 = data[14][0][0][0]    # Calculated fuel used by fuel mass flow, engine 1 assumed.
x16 = data[15][0][0][0]    # Calculated fuel used by fuel mass flow, engine 2 assumed.
x17 = data[16][0][0][0]    # Deflection of aileron.
x18 = data[17][0][0][0]    # Deflection of elevator.
x19 = data[18][0][0][0]    # Deflection of rudder.
x20 = data[19][0][0][0]    # UTC Date DD:MM:YY.
x21 = data[20][0][0][0]    # UTC Seconds.
x22 = data[21][0][0][0]    # Roll angle.
x23 = data[22][0][0][0]    # Pitch angle.
x24 = data[23][0][0][0] 
x25 = data[24][0][0][0]    # GNSS latitude.
x26 = data[25][0][0][0]    # GNSS longitude.
x27 = data[26][0][0][0]    # Body roll rate.
x28 = data[27][0][0][0]    # Body pitch rate.
x29 = data[28][0][0][0]    # Body yaw rate.
x30 = data[29][0][0][0]    # Body longitudinal acceleration.
x31 = data[30][0][0][0]    # Body lateral acceleration.
x32 = data[31][0][0][0]    # Body normal acceleration.
x33 = data[32][0][0][0]    # Along heading acceleration.
x34 = data[33][0][0][0]    # Cross heading acceleration.
x35 = data[34][0][0][0]    # Vertical acceleration.
x36 = data[35][0][0][0]    # Static air temperature.
x37 = data[36][0][0][0]    # Total air temperature.
x38 = data[37][0][0][0]    # Pressure altitude.
x39 = data[38][0][0][0]    # Baro corrected altitude.
x40 = data[39][0][0][0] 
x41 = data[40][0][0][0]    # Mach.
x42 = data[41][0][0][0]    # Computed airspeed.
x43 = data[42][0][0][0]    # TAS in kts.
x44 = data[43][0][0][0]    # Altitude rate.
x45 = data[44][0][0][0]    # Measurement running.
x46 = data[45][0][0][0]    # Number of measurements ready.
x47 = data[46][0][0][0]    # Status of graph.
x48 = data[47][0][0][0]    # Active screen.
x49 = data[48][0][0][0][0] # Time.