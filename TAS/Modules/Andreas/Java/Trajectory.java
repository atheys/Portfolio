import java.util.ArrayList;

/**************************
 * Class Trajectory.
 * @author Andreas Theys
 * @version 2.0
 **************************/
public class Trajectory {

	/*********************
	 * Class-attributes.
	 *********************/
	private ArrayList<Datapoint> trajectory;
	
	/***********************
	 * Default constructor.
	 ***********************/
	public Trajectory(){this.trajectory = new ArrayList<Datapoint>();}
	
	/**********************
	 * Basic constructor.
	 * @param trajectory
	 * @return
	 **********************/
	public Trajectory(ArrayList<Datapoint> trajectory){
		this.trajectory = trajectory;
	}

	/*******************************************************
	 * trajectory-getter.
	 * @param
	 * @return trajectory 	ArrayList of Datapoint-objects 
	 *******************************************************/
	public ArrayList<Datapoint> getTrajectory(){
		return trajectory;
	}

	/*******************************************************
	 * trajectory-setter.
	 * @param trajectory 	ArrayList of Datapoint-objects 
	 * @return
	 *******************************************************/
	public void setTrajectory(ArrayList<Datapoint> trajectory){
		this.trajectory = trajectory;
	}
	
	/***********************************************************
	 * Computes the horizontal distance between two Datapoints.
	 * @param d1	first datapoint.
	 * @param d2	second datapoint.
	 * @return d	horizontal distance between points. 
	 ***********************************************************/
	public double distance(Datapoint d1, Datapoint d2){
		double d = Math.sqrt(Math.pow(d1.getY()-d2.getY(),2)+Math.pow(d1.getZ()-d2.getZ(),2));
		return d;
	}
	
	/******************************************************************
	 * Computes the vertical height difference between two Datapoints.
	 * @param d1	first datapoint.
	 * @param d2	second datapoint.
	 * @return h	height differences between points. 
	 ******************************************************************/
	public double height(Datapoint d1, Datapoint d2){
		double h = Math.abs(d1.getX()-d2.getX());
		return h;
	}
	
	/**************************************************
	 * Computes flight path efficiency of trajectory. 
	 * @param
	 * @return result	flight path efficiency. 
	 **************************************************/
	public double efficiency(){
		if(this.trajectory.size()==1)
			return 0;
		if(this.trajectory.size()==2)
			return 1;
		double result = 0;
		for(int i=0; i<this.trajectory.size()-1; i++){
			result+=distance(trajectory.get(i),trajectory.get(i+1));
			}
		result = distance(trajectory.get(0),trajectory.get(trajectory.size()-1))/result;
		return result;
	}
	
	/**********************************************************
	 * Computes flight path vertical work done of trajectory. 
	 * @param
	 * @return result	vertical work on flight path. 
	 **********************************************************/
	public double work(){
		double result = 0;
		for(int i=0; i<this.trajectory.size()-1; i++){
			result+=height(trajectory.get(i),trajectory.get(i+1));
		}
		return result;
	}
	
}
