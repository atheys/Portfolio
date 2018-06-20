
import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/*******************************************
 * Class InputReader (static methods only)
 * @author Andreas Theys
 * @version 2.0
 *******************************************/
public abstract class InputReader{

	/*****************************************************
	 * Read-in method for datafile.
	 * @param name			name of file to read in.
	 * @return result		list of filtered datapoints.
	 * @throws IOException
	 *****************************************************/
	public static ArrayList<Datapoint> read(String name) throws IOException{
		ArrayList<Datapoint> result = new ArrayList<Datapoint>();
		BufferedReader br = new BufferedReader(new FileReader(name));
		br.readLine();
		String tempLine;
		while((tempLine = br.readLine()) != null){
			String[] tempSet = tempLine.split(",");
			for(int i=0; i<tempSet.length; i++){
				tempSet[i] = tempSet[i].replace(" ", "");
			}
			result.add(filter(tempSet));
		}
		br.close();
		return result;
	}
		
	/*****************************************************************
	 * filter-method.
	 * @param datapoint 	list of read in& separated Strign values
	 * @return 				filtered Datapoint-object.
	 *****************************************************************/
	public static Datapoint filter(String[] datapoint){
		double t = Double.parseDouble(datapoint[0]);
		String ID = datapoint[1];
		double x = Double.parseDouble(datapoint[2]);
		double y = Double.parseDouble(datapoint[3]);
		double z = Double.parseDouble(datapoint[4]);
		double vx = Double.parseDouble(datapoint[5]);
		double vy = Double.parseDouble(datapoint[6]);
		double vz = Double.parseDouble(datapoint[7]);
		return new Datapoint(t,ID,x,y,z,vx,vy,vz);
	}
	
	/***********************************************
	 * ID collection method.
	 * Collects all existing IDs in datafile. 
	 * @param points	list of Datapoint objects.
	 * @return result	list of Datapoint IDs.
	 ***********************************************/
	public static ArrayList<String> ids(ArrayList<Datapoint> points){
		ArrayList<String> result = new ArrayList<String>();
		for(Datapoint point: points){
			result.add(point.getID());
		}
		Set<String> hs = new HashSet<>();
		hs.addAll(result);
		result.clear();
		result.addAll(hs);
		return result;
	}
	
	
	/*************************************************
	 * Time collection method.
	 * Collects all existing time specs in datafile.
	 * @param points	list of Datapoint objects.
	 * @return result	list of times.
	 *************************************************/
	public static ArrayList<Double> times(ArrayList<Datapoint> points){
		ArrayList<Double> result = new ArrayList<Double>();
		for(Datapoint point: points){
			result.add(point.getT());
		}
		Set<Double> hs = new HashSet<>();
		hs.addAll(result);
		result.clear();
		result.addAll(hs);
		Collections.sort(result);
		return result;
	}
	
	
	/*************************************************
	 * Trajectory collection method.
	 * Collects all existing trajectories in datafile.
	 * @param points	list of Datapoint objects.
	 * @param IDs		list of all existing IDs.
	 * @return result	list of Trajectory-objects.
	 *************************************************/
	public static ArrayList<Trajectory> sortedDPoints(ArrayList<Datapoint> points, ArrayList<String> IDs){
		ArrayList<Trajectory> result = new ArrayList<Trajectory>();
		ArrayList<Datapoint> trajec = new ArrayList<Datapoint>();
		String currentID = points.get(0).getID();
		for(Datapoint point: points){
			if(point.getID().equals(currentID)){
				trajec.add(point);
			}
			else{
				Trajectory temp = new Trajectory(trajec);
				result.add(temp);
				trajec = new ArrayList<Datapoint>();
				trajec.add(point);
				currentID = point.getID();
			}
		}
		Trajectory temp = new Trajectory(trajec);
		result.add(temp);
		return result;
	}
	
	
	/*****************************************************************************
	 * Linear interpolation of datapoint speeds.
	 * @param trajec 			ArrayList of of trajectories.
	 * @return trajectories		Linearly interpolated ArrayList of trajectories. 
	 *****************************************************************************/
	public static ArrayList<Trajectory> linearInterpolate(ArrayList<Trajectory> trajec){
		ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
		for(Trajectory traj: trajec){
			ArrayList<Datapoint> newPoints = new ArrayList<Datapoint>();
			ArrayList<Datapoint> points = traj.getTrajectory();
			for(int i=0; i<points.size()-1; i++){
				Datapoint d1 = new Datapoint(points.get(i));
				Datapoint d2 = new Datapoint(points.get(i+1));
				double dt = d2.getT()-d1.getT();
				double dx = d2.getX()-d1.getX();
				double dy = d2.getY()-d1.getY();
				double dz = d2.getZ()-d1.getZ();
				d1.setVx(dx/dt);
				d1.setVy(dy/dt);
				d1.setVz(dz/dt);
				newPoints.add(d1);
			}
			trajectories.add(new Trajectory(newPoints));
		}
		return trajectories;
	}
	

	/*********************************************************
	 * Time sorting method for all Datapoints.
	 * @param trajectories	list of trajectories.
	 * @param times			list of time specs.
	 * @return result		time sorted lists of Datapoints.
	 *********************************************************/
	public static ArrayList<ArrayList<Datapoint>> timeSort(ArrayList<Trajectory> trajectories, ArrayList<Double> times){
		ArrayList<ArrayList<Datapoint>> result = new ArrayList<ArrayList<Datapoint>>();
		for(int i=0; i<times.size(); i++){
			result.add(new ArrayList<Datapoint>());
		}
		for(Trajectory trajec: trajectories){
			for(Datapoint point: trajec.getTrajectory()){
				result.get(times.indexOf(point.getT())).add(point);
			}
		}
		return result;
	}
	
}
