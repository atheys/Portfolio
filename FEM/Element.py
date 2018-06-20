
def distance(x,y):
    return abs(x-y)


class Element(object):
    
    def __init__(self,begin,end,index=0):
        self.begin = min(begin,end)
        self.end = max(begin,end)
        self.length = distance(end,begin)
        self.index = index
        
    # Can be optimized with while-loop
    def containsIFPoint(self,interface):
        for point in interface:
            if self.begin<point and point <self.end:
                return True
        return False
    
    def sign(self,point,interface):
        point = float(point)
        for i in range(len(interface)):
            if point<= interface[i]:
                return (-1.)**(i)
        return (-1.)**len(interface)
    
    def iPoints(self,interface):
        points,signs = [self.begin],[self.sign(self.begin,interface)]
        if self.containsIFPoint(interface):
            for point in interface:
                if self.begin<point and point <self.end:
                    points.append(point)
                    signs.append(-1.*signs[len(signs)-1])
        points.append(self.end)
        return points,signs