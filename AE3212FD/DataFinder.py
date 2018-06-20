# DataPlotter Module.
# @author: Group 21/03/2017_F3.
# @version: 1.1

# Plots a given parameters list in time.
# @param: [t0] Begin time [sec].
# @param: [t1] End time [sec].
# @param: [list1] time list [numpy.array].
# @param: [list2] parameter list [numpy.array].
# @note: time point precision set at 0.1.
# @return: plot figure.
def find(t0,t1,list1,list2):
    t0,t1 = float(t0),float(t1)
    list1,list2 = list1.tolist(),list2.tolist()
    i0,i1 = list1.index(t0),list1.index(t1)
    t,values = [],[]
    for i in range(i0,i1+1):
        t.append(list1[i])
        values.append(list2[i])
    return t,values