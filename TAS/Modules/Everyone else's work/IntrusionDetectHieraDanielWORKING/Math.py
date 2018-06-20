# Math Module
# @author: Andreas Theys
# Modules that includes far-going and commonly used math procedures.
# NOTE[1]: module is barely used for main simulation purposes

# Imported modules
# Basic mathematical functions
from math import *
# numpy for optimization purposes
from numpy import *


############## QUADRATIC EQUATIONS #################


# Compute determinant of qudratic equation  
# NOTE[1]: input is coefficient list [a,b,c]
def determinant(coeff):
    D = coeff[1]**2-4*coeff[0]*coeff[2]
    return D


# Solve real numbered quadratic equation 
# NOTE[1]: input is coefficient list [a,b,c]
# NOTE[2]: output is list with solutions (unsorted)
def quadSolve(coeff):
    D = determinant(coeff)
    if (D>0.):
        sol1 = (-coeff[1]+sqrt(D))/(2*coeff[0])
        sol2 = (-coeff[1]-sqrt(D))/(2*coeff[0])
        sol = [sol1,sol2]
        return sol
    else:
        return []


############## QUADRATIC EQUATIONS #################

# NOTE: spline interpolation not explicitly used in simulation.

############### SPLINE INTERPOLATION ###############


# Transposes matrix
# NOTE[1]: matrix-datastructure is nested list
def T(M):
    r = len(M)
    c = len(M[0])
    result = []
    for j in range(c):
        temp = []
        for i in range(r):
           temp.append(float(M[i][j]))
        result.append(temp)
    return result


# Multiplies two matrices
# NOTE[1]: matrix-datastructure is nested list
def mult(A,B):
    if(len(A[0])!=len(B)):
        return
    r = len(A)
    c = len(B[0])
    intermed = len(A[0])
    result = []
    for i in range(r):
        temp = []
        for j in range(c):
            tempVar = 0.
            for k in range(intermed):
                tempVar+=float(float(A[i][k])*float(B[k][j]))
            temp.append(tempVar)
        result.append(temp)
    return result


# Reduce matrix to reduced row echelon form
# NOTE[1]: matrix-datastructure is nested list
# Source: https://rosettacode.org/wiki/Reduced_row_echelon_form#Python
def rref(M):
    if not M: return
    lead = 0
    rowCount = len(M)
    columnCount = len(M[0])
    for r in range(rowCount):
        if lead >= columnCount:
            return
        i = r
        while M[i][lead] == 0:
            i += 1
            if i == rowCount:
                i = r
                lead += 1
                if columnCount == lead:
                    return
        M[i],M[r] = M[r],M[i]
        lv = M[r][lead]
        M[r] = [ mrx / float(lv) for mrx in M[r]]
        for i in range(rowCount):
            if i != r:
                lv = M[i][lead]
                M[i] = [ iv - lv*rv for rv,iv in zip(M[r],M[i])]
        lead += 1
    return M
 
 
# Augments matrix A with vector b
# NOTE[1]: matrix-datastructure A is nested list 
# NOTE[2]: matrix-datastructure b is list  
def augment(A,b):
    M = []
    for i in range(len(A)):
        temp = []
        for j in range(len(A[0])):
            temp.append(A[i][j])
        M.append(temp)
    if(len(M)!=len(b)): 
        return
    for i in range(len(M)):
        M[i].append(b[i])
    return M


# Determines least-squares solution for dataset A|b
# NOTE[1]: matrix-datastructure A is nested list 
# NOTE[2]: matrix-datastructure b is list  
def LS_solution(A,b):
    sol = augment(A,b)
    sol = mult(T(A),sol)
    result = rref(sol)
    return result


# Interpolated spline polynomial
# NOTE[1]: simulation datapoint conventions are used
def spline(datapoint1,datapoint2):
    t1,t2 = float(datapoint1[0]),float(datapoint2[0])
    if(t1!=t2):
        # Coefficients 3rd-degree polynomial in function of time t
        coeff = [[t1**3,t1**2,t1,1.],[3*t1**2,2*t1,1.,0.],\
        [t2**3,t2**2,t2,1.],[3*t2**2,2*t2,1.,0.]]
        # Dataset vector to augment coefficient matrix with
        x = [datapoint1[2],datapoint1[5],datapoint2[2],datapoint2[5]]
        y = [datapoint1[3],datapoint1[6],datapoint2[3],datapoint2[6]]
        z = [datapoint1[4],datapoint1[7],datapoint2[4],datapoint2[7]]
        # Least-squares solution
        xSol = rref(augment(coeff,x))
        ySol = rref(augment(coeff,y))
        zSol = rref(augment(coeff,z))
        # Output results
        xRes = [xSol[0][4],xSol[1][4],xSol[2][4],xSol[3][4]]
        yRes = [ySol[0][4],ySol[1][4],ySol[2][4],ySol[3][4]]
        zRes = [zSol[0][4],zSol[1][4],zSol[2][4],zSol[3][4]]
        result = [xRes,yRes,zRes]
        # Output
        return result
 
   
############### SPLINE INTERPOLATION ###############