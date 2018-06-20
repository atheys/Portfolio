# TrafficManagement Module
# @author: Andreas Theys
# @version: 2.0
# This module primarily computes air traffic densities.
# NOTE[1]: no modules imported


# Computes instantaneous air traffic density
# NOTE[1]: input is list of all datapoints at time 
# Edge case: none or only 1 datapoint present
# Edge case: very small volume
def density(instDPoints):
    # Edge case 
    if len(instDPoints)==0 or len(instDPoints)==1:
        return 0.
    else:
        dx = max(instDPoints,key=lambda x: x[2])-min(instDPoints,key=lambda x: x[2])
        dy = max(instDPoints,key=lambda x: x[3])-min(instDPoints,key=lambda x: x[3])
        dz = max(instDPoints,key=lambda x: x[4])-min(instDPoints,key=lambda x: x[4])
        V = abs(dx*dy*dz)
        # Edge case
        if((V-0.)<0.001):
            return 0.
        else:
            # Datapoint/m^3
            return len(instDPoints)/V