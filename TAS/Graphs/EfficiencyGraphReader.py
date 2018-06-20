# EfficiencyGraphReader Module
# @author Andreas Theys
# @version 1.0
# Graphic module for efficiency analysis


# Imported modules
# Graph functionality
import pylab as plt
# Graph patches
import matplotlib.patches as mpatches
# Basic math functions
from math import *
import scipy.stats as stat


# Datafile variables
concepts = ['FullMix','Layers']
densities = ['Low','Medium','High','UltraHigh']
daytimes = ['Morning','Lunch','Evening']
numbers = ['1','2']


# Read-method for datafile
# NOTE[1]: one specific datafile considered.
def read(concept,density,daytime,number):
    name = '/Users/andreastheys/Documents/TAS/Stats/'\
    +concept+'/'+density+'/'+daytime+'/FlightEfficiencies'+number+'.txt'
    File = open(name,'r')
    lines = File.readlines()
    lines = lines[8:]
    eff, work = [],[]
    for line in lines:
        temp = line.split(',')
        if(eff>0.):
            eff.append(float(temp[1]))
            work.append(float(temp[2]))
    return eff,work


# Data gathering method per concept
# NOTE[1]: read-method implementend
def data_con(concept):
    eff,work = [],[]
    for density in densities:
        for daytime in daytimes:
            for number in numbers:
                eff_temp,work_temp = read(concept,density,daytime,number)
                eff.extend(eff_temp)
                work.extend(work_temp)
    return eff,work


# Data gathering method per density
# NOTE[1]: read-method implementend
def data_dens(density):
    eff,work = [],[]
    for concept in concepts:
        for daytime in daytimes:
            for number in numbers:
                eff_temp,work_temp = read(concept,density,daytime,number)
                eff.extend(eff_temp)
                work.extend(work_temp)
    return eff,work
  

# Data gathering method per daytime
# NOTE[1]: read-method implementend
def data_day(daytime):
    eff,work = [],[]
    for concept in concepts:
        for density in densities:
            for number in numbers:
                eff_temp,work_temp = read(concept,density,daytime,number)
                eff.extend(eff_temp)
                work.extend(work_temp)
    return eff,work


# Data gathering method per concept/density configuration
# NOTE[1]: read-method implementend
def data_condens(concept,density):
    eff,work = [],[]
    for daytime in daytimes:
        for number in numbers:
            eff_temp,work_temp = read(concept,density,daytime,number)
            eff.extend(eff_temp)
            work.extend(work_temp)
    return eff,work


# Computes mean and standard deviation
# Efficiency and work stats both required
def stats(eff,work):
    mu1, sigma1, mu2, sigma2 = 0.,0.,0.,0.
    mu1 = sum(eff)/len(eff)
    mu2 = sum(work)/len(work)
    for item in eff:
        sigma1 += (item-mu1)*(item-mu1)
    for item in work:
        sigma2 += (item-mu2)*(item-mu2)
    sigma1 = sqrt(sigma1/len(eff))
    sigma2 = sqrt(sigma2/len(work))
    return mu1, sigma1,len(eff), mu2, sigma2, len(work)


def even(values):
    result = []
    for i in range(len(values)):
        if(i%2==1):
            result.append(values[i])
    return result

def uneven(values):
    result = []
    for i in range(len(values)):
        if(i%2==0):
            result.append(values[i])
    return result

def ftest(var,n):
    var1,var2 = var[:4],var[4:]
    n1,n2 = n[:4],n[4:]
    for i in range(4):
        F = float(var1[i]/var2[i])
        df1,df2 = n1[i]-1,n2[i]-1
        alpha = 0.05 #Or whatever you want your alpha to be.
        p_value = stat.f.cdf(F, df1, df2)
        if p_value > alpha:
            print "Reject"
        else:
            print "Accept"
    return


# Produces graphs for all configurations
# FullMix vs. Layers
def graph_condens(concepts,densities):
    number = [1.8,2.2,3.8,4.2,5.8,6.2,7.8,8.2]
    valuesMu1 = []
    N1 = []
    valuesSigma1 = []
    valuesVar1 = []
    valuesMu2 = []
    N2 = []
    valuesSigma2 = []
    valuesVar2 = []
    for concept in concepts:
        for density in densities:
            eff,work = data_condens(concept,density)
            mu1, sigma1, n1, mu2, sigma2,n2 = stats(eff,work)
            N1.append(n1)
            N2.append(n2)
            valuesMu1.append(mu1)
            valuesVar1.append(sqrt(sigma1))
            valuesSigma1.append(1.96*sigma1/sqrt(len(eff)))
            valuesMu2.append(mu2)
            valuesVar2.append(sqrt(sigma2))
            valuesSigma2.append(1.96*sigma2/sqrt(len(work)))
    ftest(valuesVar1,N1)    
    
    plt.figure(4)
    plt.errorbar(uneven(number),valuesMu1[:4],yerr=valuesSigma1[:4],fmt='o',color='b',label="Full Mix")
    plt.errorbar(even(number),valuesMu1[4:],yerr=valuesSigma1[4:],fmt='o',color='r',label="Layers")
    plt.xlim(1,9)
    plt.ylim(0,1)
    #plt.ylim(0.99*min(valuesMu1),1.01*max(valuesMu1))
    plt.xticks([2,4,6,8], densities)
    plt.xlabel("Air Traffic Densities",fontweight='bold')
    plt.ylabel("Horizontal Flight Efficiency []",fontweight='bold')
    plt.title("Horizontal Flight Efficiency Analysis",fontsize=12, fontweight='bold')
    plt.legend(loc=4,numpoints=1)
    plt.show()
    """
    plt.figure(5)
    plt.errorbar(uneven(number),valuesMu2[:4],yerr=valuesSigma2[:4],fmt='o',color='b',label='Full Mix')
    plt.errorbar(even(number),valuesMu2[4:],yerr=valuesSigma2[4:],fmt='o',color='r',label='Layers')
    plt.xlim(1,9)
    plt.ylim(0.99*min(valuesMu2),1.01*max(valuesMu2))
    plt.xticks([2,4,6,8], densities)
    plt.xlabel("Air Traffic Densities",fontweight='bold')
    plt.ylabel("Displacement [m]",fontweight='bold')
    plt.title("Vertical Displacement Analysis",fontsize=12, fontweight='bold')
    plt.legend(loc=4,numpoints=1)
    plt.show()"""
    return
    
graph_condens(concepts,densities)
