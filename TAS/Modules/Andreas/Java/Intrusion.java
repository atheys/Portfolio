import java.util.ArrayList;

/*****************************************
 * Class Intrusion (only static methods)
 * @author Andreas Theys
 * @versio 1.0
 *****************************************/
public abstract class Intrusion{
	
	/*****************************************************
	 * Computes intersection of two continuous interval.
	 * @param interval1	first interval.
	 * @param interval2	second interval.
	 * @return result	intersection interval.
	 *****************************************************/
	public static double[] intersect(double[] interval1, double[] interval2){
		double[] result = {};
		if(interval1.length==0||interval2.length==0)
			return result;
		double minimum = Math.max(interval1[0], interval2[0]);
		double maximum = Math.min(interval1[1], interval2[1]);
		if(maximum<minimum)
			return result;
		double[] newResult ={minimum,maximum};
		return newResult;
	}
	
	/******************************************************
	 * Intrusion detection method.
	 * @param com		Combination-object of Datapoints.
	 * @param timestep	time increment.
	 * @return	result	intrusion time interval.		
	 ******************************************************/
	public static double[] intrusion(Combination com, double timestep){
		double[] drel = com.rDistance(); double[] vrel = com.rVelocity();
		double vMargin = com.vMargin; double hMargin = com.hMargin;
		double x = drel[0]; double vx = vrel[0];
		double[] vert = {Math.min((-vMargin-x)/vx, (vMargin-x)/vx),Math.max((-vMargin-x)/vx, (vMargin-x)/vx)};
		double[] sim = {0.0,timestep};
		double y = drel[1]; double z = drel[2];
		double vy = vrel[1]; double vz = vrel[2];
		double a = vy*vy+vz*vz; double b = 2*(y*vy+z*vz); double c = y*y+z*z-hMargin*hMargin;
		double D = b*b-4*a*c;
		if (D<=0)
			return new double[0];
		double root1 = (-b+Math.sqrt(D))/(2*a); double root2 = (-b-Math.sqrt(D))/(2*a);
		double[] hor = {Math.min(root1,root2),Math.max(root1,root2)};
		double []result = intersect(intersect(vert,sim),hor); 
		if (result.length==0)
			return new double[0];
		return result;
	}
	
	/******************************************************
	 * Existing intrusion ID updates.
	 * @param ids	list of existing intrusion IDs.
	 * @param id	ID to update with
	 * @return			
	 ******************************************************/
	public static void update(ArrayList<ID> ids, ID id){
		boolean add = false;
		for(ID iter: ids){
			if(iter.equals(id)){
				iter.update(id);
				add = true;
				break;
			}
		}
		if(!add) ids.add(id);
	}
	
	/**********************************
	 * Computes intrusion statistics.
	 * @param intrusion
	 * @param points
	 * @return
	 **********************************/
	public static void compute(ArrayList<ID> intrusions, ArrayList<Datapoint> points, double timestep, double precision){
		ArrayList<Combination> combinations = Combination.generate(points);
		for(Combination comb: combinations){
			double[] interval = intrusion(comb,timestep);
			if (interval.length>0){
				ID id = new ID(interval[0], comb.getD1().getID(), comb.getD2().getID(), 0.0);
				double incr = comb.increment(precision);
				for(double i=interval[0]; i<=interval[1]; i+=incr){
					ID temp = new ID(comb.getD1().getT()+i, comb.getD1().getID(), comb.getD2().getID(), comb.severity(i));
					id.update(temp);
				}
				update(intrusions,id);
			}
		}
	}
	
}
