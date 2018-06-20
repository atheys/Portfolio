package input.finders.labels;

import java.io.IOException;

/**
 * Main class for data mining based on label data structures.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class LabelMiner {

	/**
	 * Main method.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		/*
		 * Label[] allLabels = { new DataLabels_BB(), new DataLabels_BL(), new
		 * DataLabels_ED(), new DataLabels_LU(), new DataLabels_PD(), new
		 * DataLabels_PL(), new DataLabels_PP(), new DataLabels_SA(), new
		 * DataLabels_SL(), new DataLabels_TC() };
		 */
		Label[] allLabels = { new DataLabels_PD() };
		//ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < allLabels.length; i++) {
			Label toRun = allLabels[i];
			toRun.mine();
			//executor.execute(toRun);
		}
		//executor.shutdown();
		//Wait until all threads are finish
		//while (!executor.isTerminated()) {
		//}
		System.out.println("Terminated.");
	}

}
