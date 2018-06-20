# StateSpace Module.
# @note: module provide state-space ready entities.
# @author: Group 21/03/2017_F3.
# @version: 2.0

# Module Imports.
# Control Package Import.
import control as c
# Numpy Library Module Import.
import numpy as np
# Scipy Linear Algebra Module Import.
import scipy.linalg as la
# Coefficient Matrix Module Import.
import CoefficientMatrix as cm

"""
Subscript S: Symmetric Motion.
Subscript A: Asymmetric Motion.
"""

# Creates State Space matrices.
# @param: [C1] first coefficient matrix [np.matrix].
# @param: [C2] second coefficient matrix [np.matrix].
# @param: [C3] third coefficient matrix [np.matrix].
# @param: [A] first state space matrix [np.matrix].
# @param: [B] second stte space matrix [np.matrix].
def ssMatrix(C1,C2,C3):
    A = -1.*la.inv(C1)*C2
    B = -1.*la.inv(C1)*C3
    return A,B
    
# Builds State Space system.
# @param: [C1] first coefficient matrix [np.matrix].
# @param: [C2] second coefficient matrix [np.matrix].
# @param: [C3] third coefficient matrix [np.matrix].  
# @return: state space system.    
def buildStateSpace(C1,C2,C3,subscript='S'):
    A,B = ssMatrix(C1,C2,C3)
    C = np.matrix([[1.,0.,0.,0.],[0.,1.,0.,0.],\
                   [0.,0.,1.,0.],[0.,0.,0.,1.]])
    if subscript == 'S':
        D = np.matrix([[0.],[0.],[0.],[0.]])
        return c.ss(A,B,C,D)
    elif subscript == 'A':
        D = np.matrix([[0.,0.],[0.,0.],[0.,0.],[0.,0.]])
        return c.ss(A,B,C,D)
    
# Symmetric State Space system.  
S_ss = buildStateSpace(cm.S_C1,cm.S_C2,cm.S_C3,subscript='S')
# Asymmetric State Space system. 
A_ss = buildStateSpace(cm.A_C1,cm.A_C2,cm.A_C3,subscript='A')