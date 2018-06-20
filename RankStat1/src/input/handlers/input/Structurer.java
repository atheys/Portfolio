package input.handlers.input;

/**
 * Imported packages.
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import input.finders.labels.Conventions;

/**
 * Main structural builder class for data storage.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class Structurer extends Conventions {

	/**
	 * Creates the empty files that are later used by all data-mining for a
	 * specific entity.
	 * 
	 * /** Mines raw statistical data off website.
	 * 
	 * @param gamenumber
	 *            game ID number on website.
	 * @param competition
	 *            competition specification.
	 * @param season
	 *            seasonal specification.
	 * @param gameDay
	 *            game day specification.
	 * @param game
	 *            game specification.
	 * @throws IOException
	 */
	public static void createFile(String competition, String season, String gameDay, String game) throws IOException {
		
	}

	/**
	 * Creates all necessary files for the given list of primary competition
	 * specifications.
	 * 
	 * @throws IOException
	 */
	private static void createAllPrimaryFiles() throws IOException {
		for (int i = 0; i < pShort.length; i++) {
			for (int j = 0; j < pSeasons[i].length; j++) {
				for (int k = 1; k <= pGameDays[i]; k++) {
					for (int l = 1; l <= pTeams[i] / 2; l++) {
						createFile(pShort[i], pSeasons[i][j], "" + k, "" + l);
					}
				}
			}
		}
	}

	/**
	 * Creates all necessary files for the given list of primary competition
	 * specifications.
	 * 
	 * @throws IOException
	 */
	private static void createAllSecondaryFiles() throws IOException {
		for (int i = 0; i < sShort.length; i++) {
			for (int j = 0; j < sSeasons[i].length; j++) {
				for (int k = 1; k <= sGameDays[i]; k++) {
					for (int l = 1; l <= sTeams[i] / 2; l++) {
						createFile(sShort[i], sSeasons[i][j], "" + k, "" + l);
					}
				}
			}
		}
	}

	/**
	 * Creates all necessary files.
	 * 
	 * @throws IOException
	 */
	public static void createAllFiles() throws IOException {
		createAllPrimaryFiles();
		createAllSecondaryFiles();
	}

	/**
	 * Main test.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		createAllFiles();
	}

}