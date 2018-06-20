# Analytical Eigenvalues Module
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Module Imports.
# Parameter Module Import.
import Cit_params as cp

# Computes the square roots, provided a set of equation coefficients.
# @param: [a] a-coefficient (degree: 2) [].
# @param: [b] b-coefficient (degree: 1) [].
# @param: [c] c-coefficient (degree: 0) [].
# @return: [x1] first solution [].
# @return: [x2] second solution [].
def solve(a,b,c):
    c1,c2,c3 = complex(float(a),0.),complex(float(b),0.),complex(float(c),0.)
    d1,d2 = -c2/(2.*c1),(c2**2-4*c1*c3)**0.5/(2.*c1)
    x1,x2 = d1+d2,d1-d2
    return x1,x2
    
# Computes the analytical damping coefficient, provided a set of equation coefficients.   
# @param: [a] a-coefficient (degree: 2) [].
# @param: [b] b-coefficient (degree: 1) [].
# @param: [c] c-coefficient (degree: 0) [].
# @return: [dzeta] damping coefficient [].
def damping(a,b,c):
    c1,c2,c3 = complex(a),complex(b),complex(c)
    dzeta = -(0.5*c2)/(c1*c3)**0.5
    return dzeta

# Provides equation coefficients for Short Period Motion.   
# @return: [a] a-coefficient (degree: 2) [].
# @return: [b] b-coefficient (degree: 1) [].
# @return: [c] c-coefficient (degree: 0) [].
def shortPeriod():
    a = -2.*float(cp.muc)*float(cp.KY2)
    b = float(cp.Cmadot) + float(cp.Cmq)
    c = float(cp.Cma)
    return a,b,c

# Provides equation coefficients for Phugoid Motion.   
# @return: [a] a-coefficient (degree: 2) [].
# @return: [b] b-coefficient (degree: 1) [].
# @return: [c] c-coefficient (degree: 0) [].
def phugoid():
    a = -4.*float(cp.muc)**2
    b = 2.*float(cp.muc)*float(cp.CXu)
    c = -float(cp.CZu)*float(cp.CZ0)
    return a,b,c
  
# Provides equation coefficients for Dutch Roll Motion.   
# @return: [a] a-coefficient (degree: 2) [].
# @return: [b] b-coefficient (degree: 1) [].
# @return: [c] c-coefficient (degree: 0) [].    
def dutchRoll():
    a = -2.*float(cp.mub)*float(cp.KZ2)
    b = 0.5*float(cp.Cnr)
    c = -float(cp.Cnb)
    return a,b,c  
  
# Computes Eigenvalues for Short Period Motion.
# @return: eigenvalues [].  
def eigenShortPeriod():
    a,b,c = shortPeriod()
    return solve(a,b,c)

# Computes damping factor for Short Period Motion.
# @return: damping factor [].
def dampingShortPeriod():
    a,b,c = shortPeriod()
    return damping(a,b,c)

# Computes Eigenvalues for Phugoid Motion.
# @return: eigenvalues [].  
def eigenPhugoid():
    a,b,c = phugoid()
    return solve(a,b,c)
    
# Computes damping factor for Phugoid Motion.
# @return: damping factor [].
def dampingPhugoid():
    a,b,c = phugoid()
    return damping(a,b,c)    
    
# Computes Eigenvalues for Dutch Roll Motion.
# @return: eigenvalues [].  
def eigenDutchRoll():
    a,b,c = dutchRoll()
    return solve(a,b,c)
    
# Computes damping factor for Phugoid Motion.
# @return: damping factor [].
def dampingDutchRoll():
    a,b,c = dutchRoll()
    return damping(a,b,c)

# Computes Eigenvalues for Aperiodic Roll Motion.
# @return: eigenvalue [].
def eigenAperiodicRoll():
    return float(cp.Clp)/(4. * float(cp.mub) * float(cp.KX2))

# Computes Eigenvalues for Aperiodic Roll Motion.
# @return: eigenvalue [].
def eigenSpiral():
    arg = 2.*float(cp.CL)*(float(cp.Clb)*float(cp.Cnr)-float(cp.Cnb)*float(cp.Clr))
    denom = float(cp.Clp)*(float(cp.CYb)*float(cp.Cnr)+4.*float(cp.mub)*float(cp.Cnb)) \
            - float(cp.Cnp)*(float(cp.CYb)*float(cp.Clr)+4.*float(cp.mub)*float(cp.Clb))
    return arg/denom

# Main Method.
if __name__ == "__main__":
    print "Eigenvalues Short Period: ",eigenShortPeriod()
    print "Damping Factor Short Period: ",dampingShortPeriod()
    print " "
    print "Eigenvalues Phugoid: ",eigenPhugoid()
    print "Damping Factor Phugoid: ",dampingPhugoid()
    print " "
    print "Eigenvalues Dutch Roll: ",eigenDutchRoll()
    print "Damping Factor Dutch Roll: ",dampingDutchRoll()
    print " "
    print "Eigenvalues Aperiodic Roll: ",eigenAperiodicRoll()
    print " "
    print "Eigenvalues Spiral: ",eigenSpiral()  