# Imported Modules.
# Numpy Library
import numpy as np
# Pyplot Library
import matplotlib.pyplot as plt
import matplotlib.lines as l
# Random number generator
# from random import random
from math import *
import InputReader as IR

single = IR.read('doublesim60.csv')
fig = plt.figure(figsize=(10,6))
ax = fig.gca(projection='3d')
i = 0
for item in single[:30000]:
    if i%1000 == 0: 
        print i
    i+=1
    ax.scatter(item[0], item[1], item[2], c='b')
    ax.scatter(item[3], item[4], item[5], c='g')
    
#ax.legend()
view = (20, -35)
init_view = view
ax.view_init(*init_view)
ax.set_xlim3d(0, 25)
ax.set_ylim3d(0, 35)
ax.set_zlim3d(0, 120)
ax.set_xlabel('Vertrektijd [h]',size=12)
ax.set_ylabel('Aankomsttijd [h]',size=12)
ax.set_zlabel('Afstand [km]',size=12)
ax.xaxis._axinfo['label']['space_factor'] = 2.
ax.yaxis._axinfo['label']['space_factor'] = 2.
ax.zaxis._axinfo['label']['space_factor'] = -8.
scatter1_proxy = l.Line2D([0],[10], linestyle="none", c='b', marker = 'o')
scatter2_proxy = l.Line2D([0],[10], linestyle="none", c='g', marker = 'o')
ax.legend([scatter1_proxy, scatter2_proxy], ['Eerste reis', 'Tweede reis'], numpoints = 1)
plt.show()



# Produces a plot with the number of cars charging.
# @note: no input variables, but data is collected
# from loaddemand.csv .
def carsChargingPlot(title=True):
    # Read the loaddemand.csv file
    single,charging = np.genfromtxt('single.csv', delimiter=','),[]
    times = np.linspace(0, 30, 100)
    for time in times:
        total = 0
        for td,ta,d in single:
            # Count a car if it has arrived at home and is not yet done charging
            if time > ta and time < ta + (d / 130 * 5):
                total += 1
        charging.append(total)
    plt.plot(times,charging)
    plt.title("Charging as soon as one comes home")
    plt.xlabel("Time of the day [h]")
    plt.ylabel("Cars charging")
    plt.show()

# Produces a load demand plot in function of time.
# @note: no input variables, but data is collected
# from loaddemand.csv .
def loadDemandPlot(title=True):
    # Read the loaddemand.csv file
    demand = np.genfromtxt('loaddemand.csv', delimiter=',')
    # Initiate plot figure
    plt.figure(0)
    plt.plot(demand)
    # Plot title
    if title:
        plt.title("Demand over Time")
    # Plot labeling
    plt.xlabel("Time [h]")
    plt.ylabel("Load [GW]")
    # Show plot
    plt.show()

# Produces single trip data plot.
# @note: no input variables, but data is collected
# from single.csv .
def singleTripPlot(title=True):
    # Read the single.csv file
    single = np.genfromtxt('single.csv', delimiter=',')
    single = single.transpose()
    # Initiate plot figure
    plt.figure(1)
    plt.plot(single[0], single[1],'o')
    # Plot title
    if title:
        plt.title("Departure vs. Arrival Time")
    # Plot labeling
    plt.xlabel("Departure [h]")
    plt.ylabel("Arrival [h]")
    # Show plot
    plt.show()