# Inertia Test Module.
# @author: Andreas Theys.
# @version: 2.0 

# Inertia Module Import
from Inertia import *

# Load Case Test.
def test_case():
    test1 = centroidX()==0.
    test2 = abs(centroidY()+397.21)/(397.21)<0.01
    ctests = test1 and test2
    test3 = abs(fuselageInertiaX(centroidY())-9.353*10.**10)/(9.353*10.**10)<0.01
    test4 = abs(floorInertiaX(centroidY())-8.005*10.**10)/(8.005*10.**10)<0.01
    test5 = abs(stringersInertiaX(centroidY())-3.573*10.**9)/(3.573*10.**9)<0.01
    test6 = abs(I_xx()-1.772*10.**11)/(1.772*10.**11)<0.01
    xtests = test3 and test4 and test5 and test6
    test7 = abs(fuselageInertiaY(centroidX())-8.728*10.**10)/(8728*10.**10)<0.01
    test8 = abs(floorInertiaY(centroidX())-1.087*10.**11)/(1.087*10.**11)<0.01
    test9 = abs(stringersInertiaY(centroidX())-3.334*10.**9)/(3.334*10.**9)<0.01
    test10 = abs(I_yy()-1.993*10.**11)/(1.993*10.**11)<0.01
    ytests = test7 and test8 and test9 and test10
    test = ctests and xtests and ytests
    return test

# Centroid Subtest.
# Fuselage-Only Test Case.
def subtest_FuselageOnly_centroid():
    return centroidX() == 0. and centroidY()==0.

# Inertia Moment Subtest.
# Fuselage-Only Test Case.
def subtest_FuselageOnly_intertia():
    test1 = floorInertiaX(centroidY()) == 0. 
    test2 = stringersInertiaX(centroidY()) == 0.
    test3 = abs(I_xx()-42092432819.6)/42092432819.6<0.001
    test4 = abs(I_yy()-42092432819.6)/42092432819.6<0.01 
    test = test1 and test2 and test3 and test4
    return test 

# Fuselage-Only Test Case.
# Must import TestGeometry1 Module  in Inertia Module.   
def test_FuselageOnly():
    test1 = subtest_FuselageOnly_centroid()
    test2 = subtest_FuselageOnly_intertia()
    test = test1 and test2
    return test

# Centroid Subtest.
# Fuselage and Middle Floor Test Case.
def subtest_FuselageAndMiddleFloor_centroid():
    return centroidX() == 0. and centroidY()==0.
 
# Inertia Moment Subtest.
# Fuselage and Middle Floor Test Case.   
def subtest_FuselageAndMiddleFloor_inertia():
    test1 = floorInertiaX(centroidY()) == 0. 
    test2 = stringersInertiaX(centroidY()) == 0.
    test3 = abs(I_xx()-4.209*10.**10)/(4.209*10.**10)<0.01
    test4 = abs(floorInertiaY(centroidX())-5.359*10.**10)/(5.359*10.**10)<0.01 
    test5 = stringersInertiaY(centroidX()) == 0.
    test6 = abs(I_yy()-9.569*10.**10)/(9.569*10.**10)<0.01 
    test = test1 and test2 and test3 and test4 and test5 and test6
    return test

# Fuselage and Middle Floor Test Case.
# Must import TestGeometry2 Module in Inertia Module.
def test_FuselageAndMiddleFloor():
    test1 = subtest_FuselageAndMiddleFloor_centroid()
    test2 = subtest_FuselageAndMiddleFloor_inertia()
    test = test1 and test2
    return test

# Centroid Subtest.
# Fuselage and Floor Test Case.    
def subtest_FuselageAndFloor_centroid():
    test1 = centroidX() == 0
    test2 = abs(centroidY()+323.34)/323.34<0.01
    test = test1 and test2
    return test
  
# Inertia Moment Subtest.
# Fuselage and Floor Test Case.    
def subtest_FuselageAndFloor_inertia():
    test1 = abs(fuselageInertiaX(centroidY())-4.497*10.**10)/(4.497*10.**10) < 0.01
    test2 = abs(floorInertiaX(centroidY())-3.411*10.**10)/(3.411*10.**10) < 0.01
    test3 = stringersInertiaX(centroidY()) == 0.
    test4 = abs(I_xx()-7.907*10.**10)/(7.907*10.**10)<0.01
    xtests = test1 and test2 and test3 and test4
    test5 = abs(fuselageInertiaY(centroidX())-4.209*10.**10)/(4.209*10.**10)<0.01
    test6 = abs(floorInertiaY(centroidX())-4.717*10.**10)/(4.717*10.**10)<0.01
    test7 = stringersInertiaY(centroidX()) == 0.
    test8 = abs(I_yy()-8.926*10.**10)/(8.926*10.**10)<0.01
    ytests = test5 and test6 and test7 and test8
    test = xtests and ytests
    return test

# Fuselage and Floor Test Case.  
# Must import TestGeometry3 Module in Inertia Module.
def test_FuselageAndFloor():
    test1 = subtest_FuselageAndFloor_centroid()
    test2 = subtest_FuselageAndFloor_inertia()
    test = test1 and test2
    return test  

# Centroid Subtest.
# Full Cross-Section Test Case.  
def subtest_fullCrossSection_centroid():
    test1 = centroidX() == 0.
    test2 = abs(centroidY()+315.39)/315.39 < 0.01
    test = test1 and test2
    return test   

# Inertia Moment Subtest.
# Full Cross-Section Test Case.  
def subtest_fullCrossSection_inertia():
    test1 = abs(fuselageInertiaX(centroidY())-4.483*10.**10)/(4.483*10.**10) < 0.01
    test2 = abs(floorInertiaX(centroidY())-3.345*10.**10)/(3.345*10.**10) < 0.01
    test3 = abs(stringersInertiaX(centroidY())-3.196*10.**9)/(3.196*10.**9)<0.01
    test4 = abs(I_xx()-8.147*10.**10)/(8.147*10.**10)<0.01
    xtests = test1 and test2 and test3 and test4
    test5 = abs(fuselageInertiaY(centroidX())-4.209*10.**10)/(4.209*10.**10)<0.01
    test6 = abs(floorInertiaY(centroidX())-4.717*10.**10)/(4.717*10.**10)<0.01
    test7 = abs(stringersInertiaY(centroidX())-3.001*10.**9)/(3.001*10.**9)<0.01
    test8 = abs(I_yy()-9.226*10.**10)/(9.226*10.**10)<0.01
    ytests = test5 and test6 and test7 and test8
    test = xtests and ytests
    return test

# Full Cross-Section Test Case.    
# Must Import TestGeometry4 Module in Inertia Module.
def test_fullCrossSection():
    test1 = subtest_fullCrossSection_centroid()
    test2 = subtest_fullCrossSection_inertia()
    test = test1 and test2
    return test

# Overall Testing Outcomes
# Main Method
if __name__ == "__main__":
    # Test Study Case (no TestGeometry imports)
    print "Study Case Test: ",str(test_case())
    # Import TestGeometry1 Module in Inertia.py
    print "Fuselage-Only Test: ",str(test_FuselageOnly())
    # Import TestGeometry2 Module in Inertia.py
    print "Fuselage & Middle Floor Test: ",str(test_FuselageAndMiddleFloor())
    # Import TestGeometry3 Module in Inertia.py
    print "Fuselage & Floor Test: ",str(test_FuselageAndFloor())
    # Import TestGeometry4 Module in Inertia.py
    print "Full Cross-Section Test: ",str(test_fullCrossSection())