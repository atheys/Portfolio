
def K(t,theta):
    t = float(t)
    return t + (1./theta)*t*(1.-t**theta) \
             + ((1.+theta)/(2.*theta**2))*t*(t**(2.*theta)-2.*t**theta+1.) \
             + ((2.*theta**2+3.*theta+1.)/(6.*theta**3))*t*(-t**(3.*theta)+3.*t**(2.*theta)-3.*t**(theta)+1.) \
             + ((6.*theta**3+11.*theta**2+6.*theta+1.)/(24.*theta**4))*t*(t**(4.*theta)-4.*t**(3.*theta)+6.*t**(2.*theta)-4.*t**(theta)+1.) \
             + ((24.*theta**4+50.*theta**3+35.*theta**2+10.*theta+1.)/(120.*theta**5))*t*(-t**(5.*theta)+5.*t**(4.*theta)-10.*t**(3.*theta)+10.*t**(2.*theta)-5*t**(theta)+1.)

def K_inv(y,theta,precision=0.001):
    theta,prec,t = float(theta),float(precision),1.
    temp = K(t,theta)
    while temp>=y:
        t -= prec
        temp = K(t,theta)
    return t

def CDF(x_range,theta):
    y = []
    for i in x_range:
        y.append(K(i,theta))
    return y