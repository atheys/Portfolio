
import numpy as np

def read(fileName):
    return np.genfromtxt(str(fileName),delimiter=',')

def simplify(array,index,denom):
    simple,index,denom = [],int(index),int(denom)
    for i in range(len(array)):
        if i % denom == index :
            simple.append(array[i])
    return simple    
    
def adhoc(array,size):
    denom = len(array)/int(size)
    samples = []
    for i in range(denom):
        samples.append(simplify(array,i,denom))
    return samples
    
def isolate(array,index):
    X = []
    for item in array:
        X.append(item[index])
    return X