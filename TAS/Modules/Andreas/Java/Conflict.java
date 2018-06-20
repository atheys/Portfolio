import java.util.ArrayList;

/****************************************
 * Class Conflict (only static mathods).
 * @author Andreas Theys
 * @version 1.0
 ****************************************/
public abstract class Conflict {

	/*************************************************
	 * Conflict analysis method.
	 * @param dpoints	time-sorted Datapoint lists.
	 * @param t			look-ahead time.
	 * @return confl	number of conflict 
	 *************************************************/
	public static int[] conflicts(ArrayList<ArrayList<Datapoint>> dpoints, double t){
		int[] confl = new int[dpoints.size()];
		for(int i=0; i<dpoints.size(); i++){
			ArrayList<Datapoint> temp = new ArrayList<Datapoint>();
			for(Datapoint point: dpoints.get(i)){
				temp.add(point.lookAhead(t));
			}
			confl[i]=conflict(temp);
		}
		return confl;
	}
	
	/********************************************
	 * Computes the number of flight conflicts.
	 * @param points	list of Datapoints.
	 * @return result	number of conflicts.
	 ********************************************/
	public static int conflict(ArrayList<Datapoint> points){
		int result = 0;
		ArrayList<Combination> combinations = Combination.generate(points);
		for(Combination comb: combinations){
			if(comb.severity(0.0)>0.0){
				result++;
			}
		}
		return result;
	}
	
}
