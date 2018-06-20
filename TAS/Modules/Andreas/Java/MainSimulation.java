import java.io.*;
import java.util.ArrayList;

/*********************************************
 * MainSimulation class (incl. main method).
 * @author Andreas Theys
 * @version 1.0
 *********************************************/
public class MainSimulation {
	
	/*************************
	 * Class attributes.
	 * Datafile combinations.
	 *************************/
	public static final String[] concepts = {"FullMix","Layers"};
	public static final String[] densities = {"Low","Medium","High","UltraHigh"};
	public static final String[] daytimes = {"Morning","Lunch","Evening"};
	public static final String[] numbers = {"1","2"};
	
	/***********************
	 * Run unit method.
	 * @param concept
	 * @param density
	 * @param daytime
	 * @param number
	 * @throws IOException
	 * @return
	 ***********************/
	public static void run(String concept, String density, String daytime, String number,
			double[] tAhead, double precision) throws IOException{
		
		long t0 = System.currentTimeMillis();
		// [0] FILE NAMES --> See conventions
		// Adjusted datafile
		String fileName = "/Users/andreastheys/Documents/TAS_data/"
				+concept+"/"+density+"/"+daytime+"/"+number+number+".txt";
		// Datafile specific logbook
		String logName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/Logbook"+number+".txt";
		// Intrusion analysis
		String intrusionName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/Intrusions"+number+".txt";
		// Conflict analysis
		String conflictName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/Conflicts"+number;
		// Flight efficiency analysis
		String feName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/FlightEfficiencies"+number+".txt";
		// Air traffic density analysis
		String densName = "/Users/andreastheys/Documents/TAS/Stats/"
						+concept+"/"+density+"/"+daytime+"/Densities"+number+".txt";
		long t1 = System.currentTimeMillis();
		System.out.println(fileName);
		String status1 = "File names created. Runtime: "+(t1-t0)+" mseconds.";
		System.out.println(status1);
		
		// [1] INPUT & FILTERING
			// Read in datafile 
			ArrayList<Datapoint> datapoints = InputReader.read(fileName);
			// Collect unique flight IDs/Callsigns
			ArrayList<String> IDs = InputReader.ids(datapoints);
			// Collect unique flight time specifications
			ArrayList<Double> times = InputReader.times(datapoints);
			// Sort flight trajectories
			ArrayList<Trajectory> trajectories = InputReader.sortedDPoints(datapoints,IDs);
			// LINEAR INTERPOLATION 
			ArrayList<Trajectory> trajectories2 = InputReader.linearInterpolate(trajectories);
			// Sort datapoints in time
			ArrayList<ArrayList<Datapoint>> timeSortedPoints = InputReader.timeSort(trajectories, times);
			ArrayList<ArrayList<Datapoint>> timeSortedPoints2 = InputReader.timeSort(trajectories2, times);
			long t2 = System.currentTimeMillis();
			String status2 = "Data file succesfully readin, filtered and sorted. Runtime: "+(t2-t1)+" mseconds.";
			System.out.println(status2);
		
		// [2] DATA PROCESSING
			// [2.1] Intrusions
			PrintWriter intrusions = new PrintWriter(intrusionName);
				ArrayList<ID> ids = new ArrayList<ID>();
				ArrayList<Integer> numbers = new ArrayList<Integer>(); 
				for(int i=0; i<times.size()-1; i++){
					int temp = ids.size();
					Intrusion.compute(ids, timeSortedPoints.get(i), times.get(i+1)-times.get(i), precision);
					numbers.add(ids.size()-temp);
				}
				long t3 = System.currentTimeMillis();
				intrusions.println("-------------------------------------");	
				intrusions.println("Intrusion Analysis.");
				intrusions.println("Flight concept: "+concept);
				intrusions.println("Air traffic density: "+density);
				intrusions.println("Daytime: "+daytime);
				intrusions.println("Iteration: "+number);
				intrusions.println("Execution time: "+(t3-t2)+" msec");
				intrusions.println("-------------------------------------");
				for(int i=1; i<=numbers.size(); i++){
					intrusions.println("Time-interval "+i+": "+numbers.get(i-1));
				}
				System.out.println(ids.size());
				intrusions.println("-------------------------------------");
				for(int i=0; i<ids.size(); i++){
					intrusions.println(ids.get(i).t+","+ids.get(i).ID1+","+ids.get(i).ID2+","+ids.get(i).sever);
				}
				intrusions.println(trajectories.size());
			intrusions.close();
			String status3 = "Intrusion Analysis Executed. Runtime: "+(t3-t2)+" mseconds.";
			String status4 = "Data stored.";
			System.out.println(status3);
			System.out.println(status4);
			// [2.2] Conflicts
			for(double tah: tAhead){
				PrintWriter conflicts = new PrintWriter(conflictName+"_"+tah+".txt");
					long t41 = System.currentTimeMillis();
					int[] confl= Conflict.conflicts(timeSortedPoints, tah);
			 		long t42 = System.currentTimeMillis();
			 		conflicts.println("-------------------------------------");	
			 		conflicts.println("Conflict Analysis.");
			 		conflicts.println("Flight concept: "+concept);
			 		conflicts.println("Air traffic density: "+density);
			 		conflicts.println("Daytime: "+daytime);
			 		conflicts.println("Iteration: "+number);
			 		conflicts.println("Look-ahead time: "+tah+" sec");
			 		conflicts.println("Execution time: "+(t41-t42)+" msec");
			 		conflicts.println("-------------------------------------");
			 		for(int i=1; i<=confl.length; i++){
					conflicts.println("Time-interval "+i+": "+confl[i-1]);
					}
			 		conflicts.println(trajectories.size());
			 	conflicts.close();
			}
			long t4 = System.currentTimeMillis();
			String status5 = "Conflict Analysis Executed. Runtime: "+(t4-t3)+" mseconds.";
			String status6 = "Data stored.";
			System.out.println(status5);
			System.out.println(status6);
			// [2.3] Efficiencies
			PrintWriter efficiencies = new PrintWriter(feName);
				double[] eff = new double[trajectories.size()];
				double[] heights = new double[trajectories.size()];
				for(int i=0; i<trajectories.size(); i++){
					eff[i] = trajectories.get(i).efficiency();
					heights[i] = trajectories.get(i).work();
				}
				long t5 = System.currentTimeMillis();
				efficiencies.println("-------------------------------------");	
				efficiencies.println("Flight Efficiencies Analysis.");
				efficiencies.println("Flight concept: "+concept);
				efficiencies.println("Air traffic density: "+density);
			 	efficiencies.println("Daytime: "+daytime);
			 	efficiencies.println("Iteration: "+number);
			 	efficiencies.println("Execution time: "+(t5-t4)+" msec");
			 	efficiencies.println("-------------------------------------");
			 	for(int i=0; i<trajectories.size(); i++){
			 		efficiencies.println(trajectories.get(i).getTrajectory().get(0).getID()+","+eff[i]+","+heights[i]);
				}
			efficiencies.close();
			String status7 = "Flight Efficiency Analysis Executed. Runtime: "+(t5-t4)+" mseconds.";
			String status8 = "Data stored.";
			System.out.println(status7);
			System.out.println(status8);
			// [2.4] Densities
			PrintWriter densities = new PrintWriter(densName);
				double[] dens = Density.densities(timeSortedPoints);
				long t6 = System.currentTimeMillis();
				densities.println("-------------------------------------");	
				densities.println("Air Traffic Density Analysis.");
				densities.println("Flight concept: "+concept);
				densities.println("Air traffic density: "+density);
				densities.println("Daytime: "+daytime);
				densities.println("Iteration: "+number);
				densities.println("Execution time: "+(t6-t5)+" msec");
				densities.println("-------------------------------------");
				for(int i=1; i<=dens.length; i++){
					densities.println("Time "+i+": "+dens[i-1]);
				}
			densities.close();
			String status9 = "Density Analysis Executed. Runtime: "+(t6-t5)+" mseconds.";
			String status10 = "Data stored.";
			System.out.println(status9);
			System.out.println(status10);
		
		// [3] LOGBOOK OUTPUT
		long t7 = System.currentTimeMillis();
		String status11 = "Logbook updated.";
		PrintWriter log = new PrintWriter(logName);
			log.println("-------------------------------------");	
			log.println("Simulation logbook");
			log.println("Flight concept: "+concept);
			log.println("Air traffic density: "+density);
			log.println("Daytime: "+daytime);
			log.println("Iteration: "+number);
			log.println("Execution time: "+(t7-t0)+" msec");
			log.println("-------------------------------------");
			log.println(status1);
			log.println(status2);
			log.println(status3);
			log.println(status4);
			log.println(status5);
			log.println(status6);
			log.println(status7);
			log.println(status8);
			log.println(status9);
			log.println(status10);
			log.println(status11);
		log.close();
		System.out.println(status11);
		System.out.println();	
	}
		
	
	/***********************
	 * Run2 unit method.
	 * @param concept
	 * @param density
	 * @param daytime
	 * @param number
	 * @throws IOException
	 * @return
	 ***********************/
	public static void run2(String concept, String density, String daytime, String number,
			double[] tAhead, double precision) throws IOException{
		
		long t0 = System.currentTimeMillis();
		// [0] FILE NAMES --> See conventions
		// Adjusted datafile
		String fileName = "/Users/andreastheys/Documents/TAS_data/"
				+concept+"/"+density+"/"+daytime+"/"+number+number+number+".txt";
		// Datafile specific logbook
		String logName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/Logbook"+number+number+".txt";
		// Intrusion analysis
		String intrusionName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/Intrusions"+number+number+".txt";
		// Conflict analysis
		String conflictName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/Conflicts"+number+number;
		// Flight efficiency analysis
		String feName = "/Users/andreastheys/Documents/TAS/Stats/"
				+concept+"/"+density+"/"+daytime+"/FlightEfficiencies"+number+number+".txt";
		// Air traffic density analysis
		String densName = "/Users/andreastheys/Documents/TAS/Stats/"
						+concept+"/"+density+"/"+daytime+"/Densities"+number+number+".txt";
		long t1 = System.currentTimeMillis();
		System.out.println(fileName);
		String status1 = "File names created. Runtime: "+(t1-t0)+" mseconds.";
		System.out.println(status1);
		
		// [1] INPUT & FILTERING
			// Read in datafile 
			ArrayList<Datapoint> datapoints = InputReader.read(fileName);
			// Collect unique flight IDs/Callsigns
			ArrayList<String> IDs = InputReader.ids(datapoints);
			// Collect unique flight time specifications
			ArrayList<Double> times = InputReader.times(datapoints);
			// Sort flight trajectories
			ArrayList<Trajectory> trajectories = InputReader.sortedDPoints(datapoints,IDs);
			// LINEAR INTERPOLATION 
			ArrayList<Trajectory> trajectories2 = InputReader.linearInterpolate(trajectories);
			// Sort datapoints in time
			ArrayList<ArrayList<Datapoint>> timeSortedPoints = InputReader.timeSort(trajectories, times);
			ArrayList<ArrayList<Datapoint>> timeSortedPoints2 = InputReader.timeSort(trajectories2, times);
			long t2 = System.currentTimeMillis();
			String status2 = "Data file succesfully readin, filtered and sorted. Runtime: "+(t2-t1)+" mseconds.";
			System.out.println(status2);
		
		// [2] DATA PROCESSING
			// [2.1] Intrusions
			PrintWriter intrusions = new PrintWriter(intrusionName);
				ArrayList<ID> ids = new ArrayList<ID>();
				ArrayList<Integer> numbers = new ArrayList<Integer>(); 
				for(int i=0; i<times.size()-1; i++){
					int temp = ids.size();
					Intrusion.compute(ids, timeSortedPoints2.get(i), times.get(i+1)-times.get(i), precision);
					numbers.add(ids.size()-temp);
				}
				long t3 = System.currentTimeMillis();
				intrusions.println("-------------------------------------");	
				intrusions.println("Intrusion Analysis.");
				intrusions.println("Flight concept: "+concept);
				intrusions.println("Air traffic density: "+density);
				intrusions.println("Daytime: "+daytime);
				intrusions.println("Iteration: "+number);
				intrusions.println("Execution time: "+(t3-t2)+" msec");
				intrusions.println("-------------------------------------");
				for(int i=1; i<=numbers.size(); i++){
					intrusions.println("Time-interval "+i+": "+numbers.get(i-1));
				}
				System.out.println(ids.size());
				intrusions.println("-------------------------------------");
				for(int i=0; i<ids.size(); i++){
					intrusions.println(ids.get(i).t+","+ids.get(i).ID1+","+ids.get(i).ID2+","+ids.get(i).sever);
				}
				intrusions.println(trajectories.size());
			intrusions.close();
			String status3 = "Intrusion Analysis Executed. Runtime: "+(t3-t2)+" mseconds.";
			String status4 = "Data stored.";
			System.out.println(status3);
			System.out.println(status4);
			// [2.2] Conflicts
			for(double tah: tAhead){
				PrintWriter conflicts = new PrintWriter(conflictName+"_"+tah+".txt");
					long t41 = System.currentTimeMillis();
					int[] confl= Conflict.conflicts(timeSortedPoints, tah);
			 		long t42 = System.currentTimeMillis();
			 		conflicts.println("-------------------------------------");	
			 		conflicts.println("Conflict Analysis.");
			 		conflicts.println("Flight concept: "+concept);
			 		conflicts.println("Air traffic density: "+density);
			 		conflicts.println("Daytime: "+daytime);
			 		conflicts.println("Iteration: "+number);
			 		conflicts.println("Look-ahead time: "+tah+" sec");
			 		conflicts.println("Execution time: "+(t41-t42)+" msec");
			 		conflicts.println("-------------------------------------");
			 		for(int i=1; i<=confl.length; i++){
					conflicts.println("Time-interval "+i+": "+confl[i-1]);
					}
			 		conflicts.println(trajectories.size());
			 	conflicts.close();
			}
			long t4 = System.currentTimeMillis();
			String status5 = "Conflict Analysis Executed. Runtime: "+(t4-t3)+" mseconds.";
			String status6 = "Data stored.";
			System.out.println(status5);
			System.out.println(status6);
			// [2.3] Efficiencies
			PrintWriter efficiencies = new PrintWriter(feName);
				double[] eff = new double[trajectories.size()];
				double[] heights = new double[trajectories.size()];
				for(int i=0; i<trajectories.size(); i++){
					eff[i] = trajectories.get(i).efficiency();
					heights[i] = trajectories.get(i).work();
				}
				long t5 = System.currentTimeMillis();
				efficiencies.println("-------------------------------------");	
				efficiencies.println("Flight Efficiencies Analysis.");
				efficiencies.println("Flight concept: "+concept);
				efficiencies.println("Air traffic density: "+density);
			 	efficiencies.println("Daytime: "+daytime);
			 	efficiencies.println("Iteration: "+number);
			 	efficiencies.println("Execution time: "+(t5-t4)+" msec");
			 	efficiencies.println("-------------------------------------");
			 	for(int i=0; i<trajectories.size(); i++){
			 		efficiencies.println(trajectories.get(i).getTrajectory().get(0).getID()+","+eff[i]+","+heights[i]);
				}
			efficiencies.close();
			String status7 = "Flight Efficiency Analysis Executed. Runtime: "+(t5-t4)+" mseconds.";
			String status8 = "Data stored.";
			System.out.println(status7);
			System.out.println(status8);
			// [2.4] Densities
			PrintWriter densities = new PrintWriter(densName);
				double[] dens = Density.densities(timeSortedPoints);
				long t6 = System.currentTimeMillis();
				densities.println("-------------------------------------");	
				densities.println("Air Traffic Density Analysis.");
				densities.println("Flight concept: "+concept);
				densities.println("Air traffic density: "+density);
				densities.println("Daytime: "+daytime);
				densities.println("Iteration: "+number);
				densities.println("Execution time: "+(t6-t5)+" msec");
				densities.println("-------------------------------------");
				for(int i=1; i<=dens.length; i++){
					densities.println("Time "+i+": "+dens[i-1]);
				}
			densities.close();
			String status9 = "Density Analysis Executed. Runtime: "+(t6-t5)+" mseconds.";
			String status10 = "Data stored.";
			System.out.println(status9);
			System.out.println(status10);
		
		// [3] LOGBOOK OUTPUT
		long t7 = System.currentTimeMillis();
		String status11 = "Logbook updated.";
		PrintWriter log = new PrintWriter(logName);
			log.println("-------------------------------------");	
			log.println("Simulation logbook");
			log.println("Flight concept: "+concept);
			log.println("Air traffic density: "+density);
			log.println("Daytime: "+daytime);
			log.println("Iteration: "+number);
			log.println("Execution time: "+(t7-t0)+" msec");
			log.println("-------------------------------------");
			log.println(status1);
			log.println(status2);
			log.println(status3);
			log.println(status4);
			log.println(status5);
			log.println(status6);
			log.println(status7);
			log.println(status8);
			log.println(status9);
			log.println(status10);
			log.println(status11);
		log.close();
		System.out.println(status11);
		System.out.println();	
	}
	
	
	
	
	
	/***************************
	 * Main simulation.
	 ***************************/
	public static void main(String[]args) throws IOException{
		double[] tAhead = {15,30,45,60,90,300};
		//double[] tAheadtest = {};
		double precision = 0.001;
		for(String c: concepts){
			for(String d: densities){
				for(String t: daytimes){
					for(String n: numbers){
						run2(c,d,t,n,tAhead,precision);
					}
				}
			}
		}
	}
}
	

