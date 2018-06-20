package input.finders.labels;

import java.io.IOException;

/**
 * Main class for checking data labels.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class LabelChecker {

	/**
	 * Main method.
	 * 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		String[] comp = { "Brazilian Serie A", "Bundesliga", "Eredivisie", "Ligue 1", "Primera Division",
				"Premier League", "Russian Premier League", "Serie A", "Turkish Super Lig", "The Championship" };
		Label[] allLabels = { new DataLabels_BB(), new DataLabels_BL(), new DataLabels_ED(), new DataLabels_LU(),
				new DataLabels_PD(), new DataLabels_PL(), new DataLabels_PP(), new DataLabels_SA(), new DataLabels_SL(),
				new DataLabels_TC() };
		for (int i=0; i<allLabels.length; i++) {
			System.out.println(comp[i]);
			allLabels[i].check();
		}
	}

}