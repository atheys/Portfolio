package input.handlers.datamining.whoscored;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import data.core.paths.Path;

/**
 * Data Mining Manager Class for stats at whoscored.com
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class WSMiningManager {

	/**
	 * Runs Python script.
	 * 
	 * @param directory
	 *            directory of the Python process.
	 * @throws IOException
	 *             in case file is not found.
	 * @throws InterruptedException 
	 */
	private static void runPythonProcess(String directory) throws IOException, InterruptedException {
		String line;
		ProcessBuilder pb = new ProcessBuilder("/Users/andreastheys/anaconda/bin/python",directory);
		Process p = pb.start();
		BufferedReader bri = new BufferedReader
		        (new InputStreamReader(p.getInputStream()));
		      BufferedReader bre = new BufferedReader
		        (new InputStreamReader(p.getErrorStream()));
		      while ((line = bri.readLine()) != null) {
		        System.out.println(line);
		      }
		      bri.close();
		      while ((line = bre.readLine()) != null) {
		        System.out.println(line);
		      }
		      bre.close();
		      p.waitFor();
		      System.out.println("Done.");
	}	
	
	/**
	 * Mines raw statistical data off website.
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
	 *             in case file is not found.
	 * @throws InterruptedException 
	 */
	private static void mine(String gamenumber, String competition, String season, String gameDay, String game)
			throws IOException, InterruptedException {
		String directory = Path.games_dm + "temp.py";
		PrintWriter writer = new PrintWriter(new File(directory));
		writer.println("from WebDriver import *");
		writer.println();
		writer.println("gamenumber = " + "'" + gamenumber + "'");
		writer.println("competition = " + "'" + competition + "'");
		writer.println("season = " + "'" + season + "'");
		writer.println("gameday = " + "'" + gameDay + "'");
		writer.println("game = " + "'" + game + "'");
		writer.println();
		writer.println("mineSite(gamenumber,competition,season,gameday,game)");
		writer.println();
		writer.close();
		runPythonProcess(directory);
	}

	/**
	 * Reads in raw statistics object.
	 * 
	 * @param competition
	 *            competition ID.
	 * @param season
	 *            seasonal specification.
	 * @param gameDay
	 *            game day specification.
	 * @param game
	 *            game specification.
	 * @return base String value for further use in data gathering.
	 * @throws IOException
	 *             in case the proper file is not found.
	 */
	private static String read_raw_stats(String competition, String season, String gameDay, String game)
			throws IOException {
		String gameID = competition + "-" + season + "-" + gameDay + "-" + game;
		String directory = Path.games_rs + competition + "/" + season + '/' + gameDay + '/' + gameID + ".txt";
		FileReader fr = new FileReader(new File(directory));
		BufferedReader br = new BufferedReader(fr);
		String stats = "", line;
		while ((line = br.readLine()) != null) {
			line = line.replaceAll(",,,", ",0,0,").replaceAll(",,", ",0,").replaceAll("[^\\x00-\\x7F]", "");
			;
			stats += line.replaceAll("\n", "") + "\\" + "\n";
		}
		br.close();
		fr.close();
		return stats;
	}

	/**
	 * Writes correct Python files to compute statistics conventions.
	 * 
	 * @param competition
	 *            competition ID.
	 * @param season
	 *            seasonal specification.
	 * @param gameDay
	 *            game day specification.
	 * @param game
	 *            game specification.
	 * @return base String value for further use in data gathering.
	 * @throws IOException
	 *             in case the proper file is not found.
	 * @throws InterruptedException 
	 */
	private static void write_raw_data(String competition, String season, String gameDay, String game)
			throws IOException, InterruptedException {
		String gameID = competition + "-" + season + "-" + gameDay + "-" + game;
		String directory = Path.games_rd + gameID + ".py";
		String rawStats = read_raw_stats(competition, season, gameDay, game);
		PrintWriter writer = new PrintWriter(new File(directory));
		writer.println("from DataMiner import *");
		writer.println();
		writer.println("competition = " + "'" + competition + "'");
		writer.println("season = " + "'" + season + "'");
		writer.println("gameday = " + "'" + gameDay + "'");
		writer.println("game = " + "'" + game + "'");
		writer.println();
		writer.println("stats = " + rawStats);
		writer.println();
		writer.println("writeFile(competition, season, gameday, game, stats)");
		writer.close();
		runPythonProcess(directory);
	}

	/**
	 * Main datamining method (website to data storage).
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
	 *             in case file is not found.
	 * @throws InterruptedException 
	 */
	public static void DataMine(String gameNumber, String competition, String season, String gameDay, String game)
			throws IOException, InterruptedException {
		mine(gameNumber, competition, season, gameDay, game);
		write_raw_data(competition, season, gameDay, game);
	}

}