from Booms import *
from NumericalShear import *
from Inertia import *
import Geometry as geo
from IdealizedMOI import *
from Reactions import *

n_floor_booms = 6

#universial calculations which will stay constant throughout the fusalage

A_st = stringerArea()
x_bar = centroidX()
y_bar = centroidY()
I_xx = I_xx()
I_yy = I_yy()

St_x, St_y     = calculateStringerLocations()
B_fl_x, B_fl_y = calculateFloorBoomLocations(n_floor_booms)
first_segment_x, first_segment_y, second_segment_x, second_segment_y, third_segment_x, third_segment_y = calculateStringerSegments(B_fl_x,B_fl_y,St_x,St_y)
cell_1_geo, cell_2_geo = createMatrixWithGeometryForEachCell(first_segment_x,first_segment_y,second_segment_x,second_segment_y,third_segment_x,third_segment_y,B_fl_x,B_fl_y,geo.t_s,geo.R,geo.t_f,geo.h_f)


#z specific calculations
z = 10

M_x = M_x(z)
M_y = M_y(z)
S_x = V_x(z)
S_y = V_y(z)


alpha = calculateRotatedNeutralAxis(M_x,M_y)
cell_1_b_area, cell_2_b_area, b_all = calculateBoomAreasWithFloor(first_segment_x,first_segment_y,second_segment_x,second_segment_y,third_segment_x,third_segment_y,B_fl_x,B_fl_y,A_st,geo.t_s,geo.t_f,alpha)
x_bar_id, y_bar_id, I_xx_id, I_yy_id = MOI_idealized(b_all)
sc_x, sc_y = findShearCenter(S_x,S_y,cell_1,cell_2,cell_1_b_area,cell_2_b_area,I_xx_id,I_yy_id,x_bar_id,y_bar_id)
