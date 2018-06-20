from math import sqrt
import numpy as np
from Booms import *

def shearSegmentContribution(S_x,S_y,I_xx_id,I_yy_id,boom_areas_cell,cell_geo,x_bar_id,y_bar_id):
    q_b = np.zeros(np.size(boom_areas_cell))    
    q_b_tot = 0
    
    for i in range(len(boom_areas_cell)):
        boom_product_x = boom_areas_cell[i]*(cell_geo[i,0]-x_bar_id)
        boom_product_y = boom_areas_cell[i]*(cell_geo[i,1]-y_bar_id)
        q_b[i] = q_b_tot -S_x/I_yy_id*boom_product_x-S_y/I_xx_id*boom_product_y
        q_b_tot = q_b[i]
    return q_b

def findShearCenter(S_x,S_y,cell_1,cell_2,b_a_cell_1,b_a_cell_2,I_xx_id,I_yy_id,x_bar_id,y_bar_id):
    q_b_1_x = shearSegmentContribution(S_x,0,I_xx_id,I_yy_id,b_a_cell_1,cell_1,x_bar_id,y_bar_id)
    q_b_2_x = shearSegmentContribution(S_x,0,I_xx_id,I_yy_id,b_a_cell_1,cell_1,x_bar_id,y_bar_id)

    q_s_1_x, q_s_2_x = shearFlowSolverShearSenter(q_b_1_x,q_b_2_x,cell_1,cell_2)
    
    moment_1_x = np.multiply(np.multiply(q_s_1_x,cell_1[:,4]),cell_1[:,2]) #mutliply with arm then with segment length
    moment_2_x = np.multiply(np.multiply(q_s_2_x,cell_2[:,4]),cell_2[:,2]) #mutliply with arm then with segment length
    
    moment_1_tot_x = np.sum(moment_1_x,axis=0)
    moment_2_tot_x = np.sum(moment_2_x,axis=0)
        
    sc_y = 1/S_x*(moment_1_tot_x+moment_2_tot_x)
    
    q_b_1_y = shearSegmentContribution(0,S_y,I_xx_id,I_yy_id,b_a_cell_1,cell_1,x_bar_id,y_bar_id)
    q_b_2_y = shearSegmentContribution(0,S_y,I_xx_id,I_yy_id,b_a_cell_1,cell_1,x_bar_id,y_bar_id)

    q_s_1_y, q_s_2_y = shearFlowSolverShearSenter(q_b_1_y,q_b_2_y,cell_1,cell_2)
    
    moment_1_y = np.multiply(np.multiply(q_s_1_y,cell_1[:,4]),cell_1[:,2])
    moment_2_y = np.multiply(np.multiply(q_s_2_y,cell_2[:,4]),cell_2[:,2])
    
    moment_1_tot_y = np.sum(moment_1_y,axis=0)
    moment_2_tot_y = np.sum(moment_2_y,axis=0)    
    
    sc_x = 1/S_y*(moment_1_tot_y+moment_2_tot_y)

    return sc_x, sc_y    

def shearFlowSolverShearSenter(q_b_cell_1,q_b_cell_2,cell_1,cell_2):
    q_matrix_1 = np.zeros([np.size(q_b_cell_1),3])
    q_matrix_1[:,0] = q_b_cell_1                    #base shearflow
    q_matrix_1[:,1:3] = cell_1[:,5:7]               #constant shearflow (still unknown)
    
    q_matrix_2 = np.zeros([np.size(q_b_cell_2),3])
    q_matrix_2[:,0] = q_b_cell_2                    #same as above but for cell 2
    q_matrix_2[:,1:3] = cell_2[:,5:7]       
    
    
    rotation_1 = np.multiply(q_matrix_1,cell_1[:,2])#mutiply each element with its respective length
    rotation_1 = np.divide(rotation_1,cell_1[:,3])  #divide each element with its respective thickness
    rotation_1_sum = np.sum(rotation_1,axis=0)      #get the sum of all these so a equation with two unknowns
 
    rotation_2 = np.multiply(q_matrix_2,cell_2[:,2]) #same as above
    rotation_2 = np.divide(rotation_2,cell_2[:,3])   
    rotation_2_sum = np.sum(rotation_2,axis=0)      #get the sum of all the elements in the 2nd cell yielding an eq with two unknowns
    q_0 = np.linalg.solve([rotation_1_sum[1:3],[rotation_2_sum[1:3]]],[[rotation_1_sum[0]],[rotation_2_sum[0]]]) #solve for q01 and q02
    
    q_matrix_1[:,1] = q_matrix_1[:,1] * q_0[0,0]    #pluggin found q01 into original matrix
    q_matrix_1[:,2] = q_matrix_1[:,2] * q_0[1,0]    #pluggin found q02 into original matrix
    
    q_matrix_2[:,1] = q_matrix_2[:,1] * q_0[0,0]    #same but for second cell
    q_matrix_2[:,2] = q_matrix_2[:,2] * q_0[1,0]
    
    cell_1_q = np.sum(q_matrix_1,axis=1)            #sum the shearflows to get shearflow after each boom
    cell_2_q = np.sum(q_matrix_2,axis=1)            #sum the shearflows to get shearflow after each boom
    
    return cell_1_q, cell_2_q

   
    
def createMatrixWithGeometryForEachCell(first_segment_x,first_segment_y,second_segment_x,second_segment_y,third_segment_x,third_segment_y,B_fl_x,B_fl_y,t_sk,R,t_fl,h_fl):
    #returns a matrix for each cell with x coordinate y coordinate and length to next section
    n_first_seg = len(first_segment_x)
    n_sec_seg   = len(second_segment_x)
    n_third_seg = len(third_segment_x)
    n_fl_seg    = len(B_fl_x)
    
    n_cell_1    = n_first_seg + n_fl_seg + n_third_seg
    n_cell_2    = n_sec_seg + n_fl_seg
    
    cell_1 = np.zeros([n_cell_1,7])
    cell_2 = np.zeros([n_cell_2,7])

        
    
    cell_1[:n_first_seg,0] = first_segment_x
    cell_1[:n_first_seg,1] = first_segment_y
    cell_1[:n_first_seg,3] = t_sk #thickness of segment
    cell_1[:n_first_seg,4] = R    #arm of segment wrt center of tube
    cell_1[:n_first_seg,5] = 1    #amount of q01 in segment
    cell_1[:n_first_seg,6] = 0    #amount of q02 in segment
    cell_1[n_first_seg:n_first_seg+n_fl_seg,0] = B_fl_x
    cell_1[n_first_seg:n_first_seg+n_fl_seg,1] = B_fl_y
    cell_1[n_first_seg:n_first_seg+n_fl_seg-1,3] = t_fl     #thickness of segment
    cell_1[n_first_seg:n_first_seg+n_fl_seg-1,4] = R-h_fl   #arm of segment wrt center of tube
    cell_1[n_first_seg:n_first_seg+n_fl_seg-1,5] = 1        #amount of q01 in segment
    cell_1[n_first_seg:n_first_seg+n_fl_seg-1,6] = -1       #amount of q02 in segment
    cell_1[n_first_seg+n_fl_seg:,0] = third_segment_x
    cell_1[n_first_seg+n_fl_seg:,1] = third_segment_y
    cell_1[n_first_seg+n_fl_seg-1:,3] = t_sk #thickness of segment
    cell_1[n_first_seg+n_fl_seg-1:,4] = R #thickness of segment
    cell_1[n_first_seg+n_fl_seg-1:,5] = 1 #amount of q01 in segment
    cell_1[n_first_seg+n_fl_seg-1:,6] = 0 #amount of q02 in segment
    
    cell_2[:n_sec_seg,0] = second_segment_x
    cell_2[:n_sec_seg,1] = second_segment_y
    cell_2[:n_sec_seg,3] = t_sk 
    cell_2[:n_sec_seg,4] = R
    cell_2[:n_sec_seg,5] = 0
    cell_2[:n_sec_seg,6] = 1
    
    cell_2[n_sec_seg:n_sec_seg+n_fl_seg,0] = B_fl_x[::-1]
    cell_2[n_sec_seg:n_sec_seg+n_fl_seg,1] = B_fl_y[::-1]
    cell_2[n_sec_seg:n_sec_seg+n_fl_seg,3] = t_fl
    cell_2[n_sec_seg:n_sec_seg+n_fl_seg,4] = h_fl-R #negative wrt to cell 1 since it is ccw around origin
    cell_2[n_sec_seg:n_sec_seg+n_fl_seg,5] = -1
    cell_2[n_sec_seg:n_sec_seg+n_fl_seg,6] = 1
    
    cell_1[n_cell_1-1,2] = calculateSegmentLength(cell_1[n_cell_1-1,0],cell_1[n_cell_1-1,1],cell_1[0,0],cell_1[0,1])
    for i in range(n_cell_1-1):
        cell_1[i,2] = calculateSegmentLength(cell_1[i+1,0],cell_1[i+1,1],cell_1[i,0],cell_1[i,1])
     
    cell_2[n_cell_2-1,2] = calculateSegmentLength(cell_2[n_cell_2-1,0],cell_2[n_cell_2-1,1],cell_2[0,0],cell_2[0,1]) 
    for i in range(n_cell_2-1):
        cell_2[i,2] = calculateSegmentLength(cell_2[i+1,0],cell_2[i+1,1],cell_2[i,0],cell_2[i,1]) 
        
    
        
    return cell_1, cell_2
  
