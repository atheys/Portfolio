
import java.io.*;
import java.util.ArrayList;

/************************************************
 * Class Numeric (only static methods).
 * Experimental class for numerical computation.
 * @author Andreas Theys
 ************************************************/
public class Numeric{

	/**********************************************
	 * Intrusion detection & severity computation.
	 * @param ids
	 * @param points
	 * @return
	 **********************************************/
	public static void intrusions(ArrayList<ID> ids, ArrayList<Datapoint> points, double ts){
		ArrayList<Combination> combinations = Combination.generate(points);
		for(Combination comb: combinations){
			ID temp1 = new ID(0,comb.getD1().getID(),comb.getD2().getID(),0);
			for(int i=0; i<=ts; i+=1){
				ID temp2 = new ID(i,comb.getD1().getID(),comb.getD2().getID(),comb.severity(i));
				temp1.update(temp2);
			}
			if(temp1.sever>0)
				Intrusion.update(ids, temp1);
		}
	}
	
	/**************
	 * Main method.
	 ***************/
	public static void main(String[]args) throws IOException{
		ArrayList<ID> ids = new ArrayList<ID>();
		String concept = "FullMix"; String density = "Low"; String daytime = "Evening"; String number = "1";
		String fileName = "/Users/andreastheys/Documents/TAS_data/"
				+concept+"/"+density+"/"+daytime+"/"+number+number+".txt";
		
		ArrayList<Datapoint> datapoints = InputReader.read(fileName);
		ArrayList<String> IDs = InputReader.ids(datapoints);
		ArrayList<Double> times = InputReader.times(datapoints);
		ArrayList<Trajectory> trajectories = InputReader.sortedDPoints(datapoints,IDs);
		ArrayList<ArrayList<Datapoint>> timeSortedPoints = InputReader.timeSort(trajectories, times);
		
		for(int i=0; i<timeSortedPoints.size()-1; i++){
			int temp = ids.size();
			System.out.println(i);
			intrusions(ids, timeSortedPoints.get(i), times.get(i+1)-times.get(i));
			System.out.print("Interval "+i+": "+(ids.size()-temp));
		}
	}
}
