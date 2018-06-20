# CoefficientMatrix Module.
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Imported Modules.
# Numpy Library Module Import.
import numpy as np
# Parameter Module Import.
import Cit_params as p

"""
Subscript S: Symmetric Motion.
Subscript A: Asymmetric Motion.
Equation:
    C1 * xdot + C2 * x + C3 * u = 0.
"""

# Creates C1-matrix for Symmetric Motion.
# @return: C1-matrix [np.matrix].
def symmetric_C1():
    r1 = [-2.*float(p.muc)*float(p.c)/float(p.V0),0.,0.,0.]
    r2 = [0.,(float(p.CZadot)-2.*float(p.muc))*float(p.c)/float(p.V0),0.,0.]
    r3 = [0.,0.,-float(p.c)/float(p.V0),0.]
    r4 = [0.,float(p.Cmadot)*float(p.c)/float(p.V0),0.,-2.*float(p.muc)*float(p.KY2)*float(p.c)/float(p.V0)]
    return np.matrix([r1,r2,r3,r4])
    
# Creates C2-matrix for Symmetric Motion.
# @return: C2-matrix [np.matrix].
def symmetric_C2():
    r1 = [float(p.CXu),float(p.CXa),float(p.CZ0),float(p.CXq)]
    r2 = [float(p.CZu),float(p.CZa),-float(p.CX0),float(p.CZq)+2.*float(p.muc)]
    r3 = [0.,0.,0.,1.]
    r4 = [float(p.Cmu),float(p.Cma),0.,float(p.Cmq)]
    return np.matrix([r1,r2,r3,r4])

# Creates C3-matrix for Symmetric Motion.
# @return: C3-matrix [np.matrix].
def symmetric_C3():
    r1 = [float(p.CXde)]
    r2 = [float(p.CZde)]
    r3 = [0.]
    r4 = [float(p.Cmde)]
    return np.matrix([r1,r2,r3,r4])

# Creates C1-matrix for Asymmetric Motion.
# @return: C1-matrix [np.matrix].
def asymmetric_C1():
    r1 = [(float(p.CYbdot)-2.*float(p.mub))*float(p.b)/float(p.V0),0.,0.,0.]
    r2 = [0.,-float(p.b)/(2.*float(p.V0)),0.,0.]
    r3 = [0.,0.,-4.*float(p.mub)*float(p.KX2)*float(p.b)/float(p.V0),\
          4.*float(p.mub)*float(p.KXZ)*float(p.b)/float(p.V0)]
    r4 = [float(p.Cnbdot)*float(p.b)/float(p.V0),0.,\
          4.*float(p.mub)*float(p.KXZ)*float(p.b)/float(p.V0),\
          -4.*float(p.mub)*float(p.KZ2)*float(p.b)/float(p.V0)]
    return np.matrix([r1,r2,r3,r4])

# Creates C2-matrix for Asymmetric Motion.
# @return: C2-matrix [np.matrix].
def asymmetric_C2():
    r1 = [float(p.CYb),float(p.CL),float(p.CYp),float(p.CYr)-4.*float(p.mub)]
    r2 = [0.,0.,1.,0.]
    r3 = [float(p.Clb),0.,float(p.Clp),float(p.Clr)]
    r4 = [float(p.Cnb),0.,float(p.Cnp),float(p.Cnr)]
    return np.matrix([r1,r2,r3,r4])

# Creates C3-matrix for Asymmetric Motion.
# @return: C3-matrix [np.matrix].
def asymmetric_C3():
    r1 = [float(p.CYda),float(p.CYdr)]
    r2 = [0.,0.]
    r3 = [float(p.Clda),float(p.Cldr)]
    r4 = [float(p.Cnda),float(p.Cndr)]
    return np.matrix([r1,r2,r3,r4])
             
S_C1 = symmetric_C1()
S_C2 = symmetric_C2()
S_C3 = symmetric_C3()
A_C1 = asymmetric_C1()
A_C2 = asymmetric_C2()
A_C3 = asymmetric_C3()