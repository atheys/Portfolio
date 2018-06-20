
def correct(t):
    if t<0.:
        return 0.
    else:
        return float(t)

def K(t,theta):
    t,theta = float(t),float(theta)
    return t + (1./theta)*t*(1.-(t)**(theta)) \
             + ((1.+theta)/(2.*(theta)**(2.)))*t*(t**(2.*theta)-2.*(t)**(theta)+1.)            

def K_inv(y,theta,precision=0.001):
    theta,prec,t = float(theta),float(precision),1.
    temp = K(t,theta)
    while temp>=y and temp>0.:
        t -= prec
        temp = K(t,theta)
    return t

def CDF(x_range,theta):
    y = []
    for i in x_range:
        y.append(K(i,theta))
    return y