import copy
import numpy as np
#Calculation of idealized centroid
def centroid_idealized(b_all):
    boom_contr_x, boom_contr_y = 0.,0.
    b_all[:,0] = np.multiply(b_all[:,0],b_all[:,2])
    b_all[:,1] = np.multiply(b_all[:,1],b_all[:,2])
    c_loc = np.sum(b_all,axis=0)
    x_bar = c_loc[0]/c_loc[2]
    y_bar = c_loc[1]/c_loc[2]
    return x_bar, y_bar

#Calculation of idealized MOI around x- and y-axis
def MOI_idealized(b_all):
    i_xy_matrix = np.zeros(b_all[:,0].size)
    x_bar, y_bar = centroid_idealized(copy.copy(b_all))
    b_all[:,0] = b_all[:,0] - x_bar
    b_all[:,1] = b_all[:,1] - y_bar
    i_xy_matrix = np.multiply(b_all[:,2],np.multiply(b_all[:,0],b_all[:,1]))
    I_xy_id = np.sum(i_xy_matrix)
    b_all[:,0] = np.square(b_all[:,0])  
    b_all[:,1] = np.square(b_all[:,1])  
    I_yy_id = np.sum(np.multiply(b_all[:,0],b_all[:,2]))
    I_xx_id = np.sum(np.multiply(b_all[:,1],b_all[:,2]))
    return x_bar, y_bar, I_xx_id, I_yy_id , I_xy_id
