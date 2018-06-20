import java.util.ArrayList;

/****************************************
 * Class Density (only static mathods).
 * @author Andreas Theys
 * @version 1.0
 ****************************************/
public abstract class Density {

	/***************************************
	 * Determines minimum x-position.
	 * @param points	list of Datapoints.
	 * @return result	minimum x-position.
	 ***************************************/
	static double minX(ArrayList<Datapoint> points){
		double result = Double.MAX_VALUE;
		for(Datapoint point: points){
			result = (point.getX()<result)?point.getX():result;
		}
		return result;
	}
	
	/***************************************
	 * Determines maximum x-position.
	 * @param points	list of Datapoints.
	 * @return result	maximum x-position.
	 ***************************************/
	static double maxX(ArrayList<Datapoint> points){
		double result = Double.MIN_VALUE;
		for(Datapoint point: points){
			result = (point.getX()>result)?point.getX():result;
		}
		return result;
	}
	
	/***************************************
	 * Determines minimum y-position.
	 * @param points	list of Datapoints.
	 * @return result	minimum y-position.
	 ***************************************/
	static double minY(ArrayList<Datapoint> points){
		double result = Double.MAX_VALUE;
		for(Datapoint point: points){
			result = (point.getY()<result)?point.getY():result;
		}
		return result;
	}
	
	/***************************************
	 * Determines maximum y-position.
	 * @param points	list of Datapoints.
	 * @return result	maximum y-position.
	 ***************************************/
	static double maxY(ArrayList<Datapoint> points){
		double result = Double.MIN_VALUE;
		for(Datapoint point: points){
			result = (point.getY()>result)?point.getY():result;
		}
		return result;
	}
	
	/***************************************
	 * Determines minimum z-position.
	 * @param points	list of Datapoints.
	 * @return result	minimum z-position.
	 ***************************************/
	static double minZ(ArrayList<Datapoint> points){
		double result = Double.MAX_VALUE;
		for(Datapoint point: points){
			result = (point.getZ()<result)?point.getZ():result;
		}
		return result;
	}
	
	/***************************************
	 * Determines maximum z-position.
	 * @param points	list of Datapoints.
	 * @return result	maximum z-position.
	 ***************************************/
	static double maxZ(ArrayList<Datapoint> points){
		double result = Double.MIN_VALUE;
		for(Datapoint point: points){
			result = (point.getZ()>result)?point.getZ():result;
		}
		return result;
	}
	
	/********************************************
	 * Computes traffic density.
	 * @param points	list of Datapoints.
	 * @return result	density value.
	 ********************************************/
	public static double density(ArrayList<Datapoint> points){
		int result = points.size();
		double dx = Math.abs(maxX(points)-minX(points));
		double dy = Math.abs(maxY(points)-minY(points));
		double dz = Math.abs(maxZ(points)-minZ(points));
		return result/(dx*dy*dz);
	}
	
	/*****************************************************
	 * Computes traffic densities for all time intervals.
	 * @param dpoints	time-sorted Datapoint lists.
	 * @return dens		densities array.
	 *****************************************************/
	public static double[] densities(ArrayList<ArrayList<Datapoint>> dpoints){
		double[] dens = new double[dpoints.size()];
		for(int i=0; i<dpoints.size(); i++){
			dens[i]=density(dpoints.get(i));
		}
		return dens;
	}
	
}
