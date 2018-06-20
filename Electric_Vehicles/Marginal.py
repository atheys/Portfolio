
import scipy.stats as scp

# Normal fitting
def fitNormal(data):
    return scp.norm.fit(data)

def normal_PDF(data,x):
    loc,scale = fitNormal(data)
    print loc,scale
    return scp.norm.pdf(x=x,loc=loc, scale=scale)

def normal_CDF(data,x):
    loc,scale = fitNormal(data)
    return scp.norm.cdf(x=x,loc=loc, scale=scale)
    
def binormal_PDF(x,loc1,scale1,loc2,scale2,w):
     w1,w2 = float(w),1.-float(w)
     return w1*scp.norm.pdf(x=x,loc=loc1, scale=scale1) + \
            w2*scp.norm.pdf(x=x,loc=loc2, scale=scale2)    
 
def binormal_CDF(x,loc1,scale1,loc2,scale2,w):
    PDF = dinormal_PDF(x,loc1,scale1,loc2,scale2,w)
    temp = 0.
    CDF = [temp]
    for i in range(len(x)-1): 
        temp += 0.5*(x[i+1]-x[i])*(PDF[i+1]+PDF[i])
        CDF.append(temp)
    return CDF
            
   
# Gamma fitting
def fitGamma(data):
    return scp.gamma.fit(data)

def gamma_PDF(data,x):
    a,loc,scale = fitGamma(data)
    print a,loc,scale
    return scp.gamma.pdf(x=x, a=a, loc=loc, scale=scale)

def gamma_CDF(data,x):
    a,loc,scale = fitGamma(data)
    return scp.gamma.cdf(x=x, a=a, loc=loc, scale=scale) 
  
def bigamma_PDF(x,a1,loc1,scale1,a2,loc2,scale2,w):
     w1,w2 = float(w),1.-float(w)
     return w1*scp.gamma.pdf(x=x, a=a1, loc=loc1, scale=scale1) + \
            w2*scp.gamma.pdf(x=x, a=a2, loc=loc2, scale=scale2)
  
def bigamma_CDF(x,a1,loc1,scale1,a2,loc2,scale2,w):
    PDF = digamma_PDF(x,a1,loc1,scale1,a2,loc2,scale2,w)
    temp = 0.
    CDF = [temp]
    for i in range(len(x)-1): 
        temp += 0.5*(x[i+1]-x[i])*(PDF[i+1]+PDF[i])
        CDF.append(temp)
    return CDF  
  
# Beta fitting
def fitBeta(data):
    return scp.beta.fit(data)

def beta_PDF(data,x):
    a,b,loc,scale = fitBeta(data)
    return scp.beta.pdf(x=x, a=a, b=b, loc=loc, scale=scale)

def beta_CDF(data,x):
    a,b,loc,scale = fitBeta(data)
    return scp.beta.cdf(x=x, a=a, b=b, loc=loc, scale=scale) 

# Exponential fitting
def fitExp(data):
    return scp.expon.fit(data)

def exp_PDF(data,x):
    loc,scale = fitExp(data)
    print loc,scale
    return scp.expon.pdf(x=x,loc=loc, scale=scale)

def exp_CDF(data,x):
    loc,scale = fitExp(data)
    return scp.expon.cdf(x=x,loc=loc, scale=scale)
    
# Pareto fitting
def fitPareto(data):
    return scp.pareto.fit(data)

def pareto_PDF(data,x):
    b,loc,scale = fitPareto(data)
    return scp.pareto.pdf(x=x, b=b, loc=loc, scale=scale)

def pareto_CDF(data,x):
    b,loc,scale = fitPareto(data)
    return scp.pareto.cdf(x=x, b=b, loc=loc, scale=scale)

 
def inverse(k,x,y):
    t = 0.    
    for i in range(len(y)-1):
        if y[i]<=k and k<y[i+1]:
            step = y[i+1]-y[i]
            perc = (k-y[i])/step
            t = x[i]+perc*(x[i+1]-x[i])
    return t 