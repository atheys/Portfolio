
import Kolmogorov as KS
import Cramer as CVM

def Kolomogorov_AdHoc(data,alpha):
    alphas = []
    for item in data:
        alphas.append(KS.critical_alpha(item))
    fails = 0
    for item in alphas:
        if alpha>item:
            fails += 1
    fail_rate = float(fails)/float(len(alphas))
    return fail_rate

def Cramer_AdHoc(data,alpha): 
    alphas = []
    for item in data:
        alphas.append(CVM.critical_alpha(item))
    fails = 0
    for item in alphas:
        if alpha>item:
            fails += 1
    fail_rate = float(fails)/float(len(alphas))
    return fail_rate