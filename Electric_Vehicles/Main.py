
import InputReader as IR
import Empirical as Emp
import Clayton3D as C3D
import Clayton6D as C6D
import Kolmogorov as KS
import Cramer as CVM
import matplotlib.pyplot as plt 
import AdHoc as AH

single = 'single.csv'
double = 'double.csv'


data = IR.read(double)
data2 = IR.adhoc(data,500)

dim = len(data[2])

x = Emp.makeRange(data2[2])
y_n = Emp.K_n(data2[2])
y = []
if dim==3:
    y = C3D.CDF(x,Emp.theta_n(data))
if dim==6:
    y = C6D.CDF(x,Emp.theta_n(data))

plt.figure(1)
plt.plot(x,y_n,c='g',label='Empirisch',lw=1.6)
plt.plot(x,y,c='b',label='Theoretisch',lw=1.2)
plt.xlabel('u []', size=12.)
plt.ylabel('Cumulatieve kansdichtheid []', size=12.)
plt.legend(loc=4)
plt.xlim(0.,1.)
plt.ylim(0.,1.01)
plt.grid(True)
plt.show()

print "Kolmogorov: ",str(AH.Kolomogorov_AdHoc(data2,0.05))
print "Cramer: ",str(AH.Cramer_AdHoc(data2,0.05))