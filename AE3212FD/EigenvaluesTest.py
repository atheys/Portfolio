
import Eigenvalues as ev

# First solve method test.
# @note: edge case.
def solveTest1():
    try:
        x1,x2 = ev.solve(0.,0.,0.)
        return False
    except Exception:
        return True

# Second solve method test.
# @note: multiplicity 2 solution.
def solveTest2():
    x1,x2 = ev.solve(1.,4.,4.)
    return x1==x2

# Third solve method test.
# @note: real solutions.
def solveTest3():
    x1,x2 = ev.solve(1.,5.,4.)
    return x1==complex(-1.,0.) and x2==complex(-4.,0.)
 
# Fourth solve method test.
# @note: complex solutions.   
def solveTest4():
    x1,x2 = ev.solve(1.,2.,4.)
    check1 = str(float(x1.real)) == "-1.0"
    check2 = str(float(x2.real)) == "-1.0"
    check3 = str(x1.imag)=='1.73205080757'
    check4 = str(float(x2.imag))=='-1.73205080757'
    return check1 and check2 and check3 and check4

# First damping method test.
# @note: all real values.     
def dampingTest1():
    a,b,c = 1.,1.,1.
    return ev.damping(a,b,c)==-0.5

# Second damping method test.
# @note: all real values, except for c. 
def dampingTest2():
    a,b,c = 1.,1.,complex(1.,1.)
    res = ev.damping(a,b,c)
    check1 = str(res.real)=='-0.388443493508'
    check2 = str(res.imag)=='0.160898563226'
    return check1 and check2

# Third damping method test.
# @note: all complex values, except for a. 
def dampingTest3():
    a,b,c = 1.,complex(1.,1.),complex(1.,1.)
    res = ev.damping(a,b,c)
    check1 = str(res.real)=='-0.549342056734'
    check2 = str(res.imag)=='-0.227544930281'
    return check1 and check2

# Fourth damping method test.
# @note: all real values, except for a. 
def dampingTest4():
    a,b,c = complex(2.,2.),1.,1.
    res = ev.damping(a,b,c)
    check1 = str(res.real)=='-0.274671028367'
    check2 = str(res.imag)=='0.113772465141'
    return check1 and check2

# Fifth damping method test.
# @note: all complex values, except for c. 
def dampingTest5():
    a,b,c = complex(2.,2.),complex(2.,3.),1.
    res = ev.damping(a,b,c)
    check1 = str(res.real)=='-0.890659452156'
    check2 = str(res.imag)=='-0.59646815482'
    return check1 and check2

# Sixth damping method test.
# @note: all complex values. 
def dampingTest6():
    a,b,c = complex(2.,2.),complex(2.,3.),complex(2.,4.)
    res = ev.damping(a,b,c)
    check1 = str(res.real)=='-0.506549632262'
    check2 = str(res.imag)=='-0.0185075645167'
    return check1 and check2

# Main Test Method.
if __name__ == "__main__":    
    test1 = solveTest1()
    test2 = solveTest2()
    test3 = solveTest3()
    test4 = solveTest4()
    solve = test1 and test2 and test3 and test4
    test5 = dampingTest1()
    test6 = dampingTest2()
    test7 = dampingTest3()
    test8 = dampingTest4()
    test9 = dampingTest5()
    test10 = dampingTest6()
    damping = test5 and test6 and test7 \
                and test8 and test9 and test10
    result = solve and damping
    if result:
        print "All tests successful: full branch coverage achieved!"