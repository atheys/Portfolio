# IntrusionGraphReader Module
# @author Andreas Theys
# @version 1.0
# Graphic module for intrusion analysis


# Imported modules
# Graph functionality
import pylab as plt
# Graph patches
import matplotlib.patches as mpatches
# Basic math functions
from math import *


# Datafile variables
concepts = ['FullMix','Layers']
densities = ['Low','Medium','High','UltraHigh']
daytimes = ['Morning','Lunch','Evening']
numbers = ['1','2']


# Read-method for datafile
# NOTE[1]: one specific datafile considered.
def read(concept,density,daytime,number):
    name = '/Users/andreastheys/Documents/TAS/Stats/'\
    +concept+'/'+density+'/'+daytime+'/Intrusions'+number+'.txt'
    File = open(name,'r')
    lines = File.readlines()
    nFlights = int(lines[len(lines)-1])
    lines = lines[129:len(lines)-1]
    severities = []
    for line in lines:
        temp = line.split(',')
        if(float(temp[3])>0.):
            severities.append(float(temp[3]))
    return severities,nFlights


# Data gathering method per concept
# NOTE[1]: read-method implementend
def data_con(concept):
    severities,nFlights = [],[]
    for density in densities:
        for daytime in daytimes:
            for number in numbers:
                sever_temp,nFlights_temp = read(concept,density,daytime,number)
                severities.append(sever_temp)
                nFlights.append(nFlights_temp)
    return severities,nFlights


# Data gathering method per density
# NOTE[1]: read-method implementend
def data_dens(density):
    severities,nFlights = [],[]
    for concept in concepts:
        for daytime in daytimes:
            for number in numbers:
                sever_temp,nFlights_temp = read(concept,density,daytime,number)
                severities.append(sever_temp)
                nFlights.append(nFlights_temp)
    return severities,nFlights
  

# Data gathering method per daytime
# NOTE[1]: read-method implementend
def data_day(daytime):
    severities,nFlights = [],[]
    for concept in concepts:
        for density in densities:
            for number in numbers:
                sever_temp,nFlights_temp = read(concept,density,daytime,number)
                severities.append(sever_temp)
                nFlights.append(nFlights_temp)
    return severities,nFlights


# Data gathering method per concept/density configuration
# NOTE[1]: read-method implementend
def data_condens(concept,density):
    severities,nFlights = [],[]
    for daytime in daytimes:
        for number in numbers:
            sever_temp,nFlights_temp = read(concept,density,daytime,number)
            severities.append(sever_temp)
            nFlights.append(nFlights_temp)
    return severities,nFlights
    
    
# Process data for intrusion number
# Note[1]: nested list of severities as input
# Note[2]: list of flight numbers as input  
def process(severities,nFlights):
    result = []
    for i in range(len(severities)):
        result.append(float(len(severities[i]))/float(nFlights[i]))
    return result

  
# Process data for intrusion number
# Note[1]: nested list of severities as input
# Note[2]: list of flight numbers as input  
def stats_process(result):
    mu = sum(result)/float(len(result))
    sigma = 0.
    for res in result:
        sigma += (res-mu)*(res-mu)
    sigma = sqrt(sigma/float(len(result)))
    return mu,sigma
    
  
# Process severity numbers (statistics)
# Note[1]: nested list of severities as input
# Output: statistic features (mean / standard deviation)
def stats_sever(severities):
    n,s = 0,0.
    for sever in severities:
        s+=sum(sever)
        n+=len(sever)
    mu = s/float(n)
    sigma = 0.
    n = 0
    for sever in severities: 
        n += len(sever)
        for item in sever:
            sigma += (item-mu)*(item-mu)
    sigma = sqrt(sigma/float(n))
    return mu,sigma,n


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
        df1,df2 = n1[i]-1,n2[i]-2
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
    valuesVar1 = []
    valuesSigma1 = []
    N1 = []
    valuesMu2 = []
    valuesVar2 = []
    valuesSigma2 = []
    N2 = []
    for concept in concepts:
        for density in densities:
            severities,nFlights = data_condens(concept,density)
            m = 0
            for nf in nFlights:
                m+=len(nFlights)
            N1.append(m)
            result = process(severities,nFlights)
            mu1, sigma1 = stats_process(result)
            mu2, sigma2,n = stats_sever(severities)
            N2.append(n)
            valuesMu1.append(mu1)
            valuesVar1.append(sqrt(sigma1))
            valuesSigma1.append(1.96*sigma1/sqrt(6))
            valuesMu2.append(mu2)
            valuesVar2.append(sqrt(sigma2))
            valuesSigma2.append(1.96*sigma2/sqrt(float(n)))
    #print valuesMu1
    #print valuesSigma1
    #print valuesMu2
    #print valuesSigma2
    ftest(valuesVar1,N1)
    ftest(valuesVar2,N2)
    plt.figure(4)
    plt.errorbar(uneven(number),valuesMu1[:4],yerr=valuesSigma1[:4],fmt='o',color='b',label="Full Mix")
    plt.errorbar(even(number),valuesMu1[4:],yerr=valuesSigma1[4:],fmt='o',color='r',label="Layers")
    plt.xticks([2,4,6,8], densities)
    plt.xlabel("Air Traffic Densities",fontweight='bold')
    plt.xlim(1,9)
    plt.ylim(0,1)
    plt.ylabel("Number of Instrusions per Flight []",fontweight='bold')
    plt.title("Intrusion Analysis",fontsize=12, fontweight='bold')
    plt.legend(loc=4,numpoints=1)
    plt.show()
    
    plt.figure(5)
    plt.errorbar(uneven(number),valuesMu2[:4],yerr=valuesSigma2[:4],fmt='o',color='b',label='Full Mix')
    plt.errorbar(even(number),valuesMu2[4:],yerr=valuesSigma2[4:],fmt='o',color='r',label='Layers')
    plt.xlim(1,9)
    plt.ylim(0,1)
    plt.xticks([2,4,6,8], densities)
    plt.xlabel("Air Traffic Densities",fontweight='bold')
    plt.ylabel("Intrusion Severity []",fontweight='bold')
    plt.title("Intrusion Severity Analysis",fontsize=12, fontweight='bold')
    plt.legend(loc=1,numpoints=1)
    plt.show()
    return

graph_condens(concepts,densities)
