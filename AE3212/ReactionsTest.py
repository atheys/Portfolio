# Reactions Test Module.
# @author: Andreas Theys.
# @version: 2.0 

# Module Imports.
# Reactions Module Import.
from Reactions import *
# from TestGeometry5 import * #New Load Case Test

# Reaction Force Subtest.
# Used in Load Case Test only.
def subtest_load_case_reactions():
    test1 = abs(F_x1()-1.9180*10.**5)/(1.9180*10.**5)<0.01
    test2 = abs(F_x2()+1.759*10.**5)/(1.759*10.**5)<0.01
    test3 = abs(F_x3()+1.759*10.**5)/(1.759*10.**5)<0.01
    test4 = F_x2() == F_x3()
    test5 = abs(Sx+F_x1()+F_x2()+F_x3())<0.01
    xreactions = test1 and test2 and test3 and test4 and test5
    test6 = abs(F_y1()-3.354*10.**5)/(3.354*10.**5)<0.01
    test7 = abs(F_y2()-6.393*10.**5)/(6.393*10.**5)<0.01
    test8 = abs(F_y3()-9.088*10.**5)/(9.088*10.**5)<0.01
    yreactions = test6 and test7 and test8
    test = xreactions and yreactions
    return test
 
# Reaction Moments Subtest. 
# Used in all tests of this module. 
def subtest_load_case_moments():
    test1 = abs(M_x(0))<0.001
    test2 = abs(M_x(L))<0.001
    Mx = test1 and test2
    test3 = abs(M_y(L))<0.001
    test4 = abs(M_y(float(L-L_f1+0.01)))<0.001
    My = test3 and test4
    test5 = abs(T(L))<0.001
    test6 = abs(T(float(L-L_f1+0.01)))<0.001
    Torsion = test5 and test6
    test = Mx and My and Torsion
    return test

# Load Case Test.   
def test_load_case(): 
    test1 = subtest_load_case_reactions()
    test2 = subtest_load_case_moments()
    test = test1 and test2
    return test

# Reaction Force Subtest.
# Used in Alternative Load Case Test only.
def test_new_load_case_reactions():
    test1 = abs(F_x1()-285000.0)/(285000.0)<0.01
    test2 = abs(F_x2()+242500.0)/(242500.0)<0.01
    test3 = abs(F_x3()+242500.0)/(242500.0)<0.01
    test4 = F_x2() == F_x3()
    test5 = abs(Sx+F_x1()+F_x2()+F_x3())<0.01
    xreactions = test1 and test2 and test3 and test4 and test5
    test6 = abs(F_y1()-1.839*10.**5)/(1.839*10.**5)<0.01
    test7 = abs(F_y2()-7.983*10.**5)/(7.983*10.**5)<0.01
    test8 = abs(F_y3()-1.225*10.**6)/(1.225*10.**6)<0.01
    yreactions = test6 and test7 and test8
    test = xreactions and yreactions
    return test 

# Load Case Test. 
# Must import TestGeometry5 Module in both ReactionTest and Reaction Module.
def test_new_load_case():
    test1 = test_new_load_case_reactions()
    test2 = subtest_load_case_moments()
    test = test1 and test2
    return test

# Overall Testing Outcomes
# Main Method
if __name__ == "__main__":
    # Test Load Case
    print "Load Case Test: ",str(test_load_case())
    # Import TestGeometry5 Module in Inertia.py
    print "Alternative Load Case Test: ",str(test_new_load_case())