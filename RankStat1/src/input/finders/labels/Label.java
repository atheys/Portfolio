package input.finders.labels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import data.core.paths.Path;
import input.handlers.datamining.whoscored.WSMiningManager;
import input.handlers.input.Structurer;

/**
 * Super class for data labels.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class Label implements Runnable {

	/**
	 * Class attributes.
	 */
	public String[][][] seasons;
	public String shortLabel;
	public String[] seasonLabels;
	public int gameDays;
	public int teams;

	/**
	 * Checks season label integrity.
	 */
	public boolean seasonCheck(String[][] season) {
		int nGames = season.length * ((season.length / 2 + 1) / 2);
		int evalNGames = 0;
		for (String[] s : season) {
			evalNGames += s.length;
		}
		boolean evaluate1 = nGames == evalNGames;
		if (!evaluate1) {
			System.out.println(nGames);
			System.out.println(evalNGames);
		}
		ArrayList<String> labels = new ArrayList<String>();
		for (int i = 0; i < season.length; i++) {
			for (int j = 0; j < season[i].length; j++) {
				labels.add(season[i][j]);
			}
		}
		boolean evaluate2 = false;
		for (int i = 0; i < labels.size(); i++) {
			for (int j = 0; j < i; j++) {
				boolean temp = labels.get(i).equals(labels.get(j));
				if (temp)
					System.out.println(i + "," + j);
				evaluate2 = evaluate2 || temp;
			}
		}
		return evaluate1 && !evaluate2;
	}

	/**
	 * Overall label integrity check.
	 */
	public void check() {
		System.out.println(this.seasons.length + " season(s)");
		for (int i = 0; i < this.seasons.length; i++) {
			if (seasonCheck(this.seasons[i])) {
				System.out.println("Season " + (i + 1) + ": approved.");
			} else {
				System.out.println("Season " + (i + 1) + ": disapproved.");
			}
		}
		System.out.println();
	}

	/**
	 * Mines data of the internet.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void mine() throws IOException, InterruptedException {
		for (int i = 0; i < this.seasonLabels.length; i++) {
			System.out.println("Season: " + this.seasonLabels[i]);
			for (int j = 0; j < this.gameDays; j++) {
				for (int k = 0; k < this.teams / 2; k++) {
					String gameNumber = this.seasons[i][j][k];
					String season = this.seasonLabels[i];
					String gameDay = "" + (j + 1);
					String game = "" + (k + 1);
					long t0 = System.currentTimeMillis();
					Structurer.createFile(this.shortLabel, season, gameDay, game);
					WSMiningManager.DataMine(gameNumber, this.shortLabel, season, gameDay, game);
					long t1 = System.currentTimeMillis();
					System.out.println((t1 - t0) + " mseconds");
				}
				System.out.print((j + 1) + " ");
			}
		}
	}

	public int mineExtra() throws IOException {
		int result = 0;
		for(int i = 0 ; i<this.seasonLabels.length; i++){
			for(int j = 0; j<this.gameDays; j++){
				for(int k=0; k<this.teams/2; k++){
					String path = Path.games_dat+this.shortLabel+'/';
					String fileName = this.shortLabel+'-'+this.seasonLabels[i]+'-'+(j+1)+'-'+(k+1)+".txt";
					path += this.seasonLabels[i]+'/';
					int prim = j+1;
					String temp = ""+prim;
					path += temp+'/';
					path += fileName;
					BufferedReader br = new BufferedReader(new FileReader(path));
					if(br.readLine()==null){
						System.out.println(path);
						result++;
					}
					br.close();
				}
			}
		}
		return result;
	}

	public void mineExtra2() throws IOException, InterruptedException {
		for(int i = 0 ; i<this.seasonLabels.length; i++){
			for(int j = 0; j<this.gameDays; j++){
				for(int k=0; k<this.teams/2; k++){
					String path = Path.games_dat+this.shortLabel+'/';
					String fileName = this.shortLabel+'-'+this.seasonLabels[i]+'-'+(j+1)+'-'+(k+1)+".txt";
					path += this.seasonLabels[i]+'/';
					int prim = j+1;
					String temp = ""+prim;
					path += temp+'/';
					path += fileName;
					BufferedReader br = new BufferedReader(new FileReader(path));
					if(br.readLine()==null){
						System.out.println(path);
						String gameNumber = this.seasons[i][j][k];
						String season = this.seasonLabels[i];
						String gameDay = "" + (j + 1);
						String game = "" + (k + 1);
						long t0 = System.currentTimeMillis();
						Structurer.createFile(this.shortLabel, season, gameDay, game);
						WSMiningManager.DataMine(gameNumber, this.shortLabel, season, gameDay, game);
						long t1 = System.currentTimeMillis();
						System.out.println((t1 - t0) + " mseconds");
					}
					br.close();
				}
			}
		}
	}
	
	/**
	 * Implemented run-method.
	 */
	public void run() {
		try {
			this.mine();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
