package input.handlers.datamining.oddsportal;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.core.books.Odds;
import data.core.paths.Path;

public class Main {

	private static ArrayList<Odds> findOdds(String label,ArrayList<Odds> odds){
		ArrayList<Odds> relevant = new ArrayList<Odds>();
		for(Odds o: odds){
			if(o.getGame().getId().equals(label)){
				relevant.add(o);
			}
		}
		return relevant;
	}
	
	public static void main(String[] args) {
		String label = "BL";
		String season = "1516";
		int gDays = 34;
		int teams = 18;
		int check= 0;
		try {
			ArrayList<Odds> odds = OPMiningManager.read(label, season, gDays, teams);
			System.out.println(odds.size());
			for(int i=1; i<=gDays; i++){
				for(int j=1; j<=teams/2; j++){
					String lab = label+"-"+season+"-"+i+"-"+j;
					ArrayList<Odds> relevant = findOdds(lab,odds);
					System.out.println(lab+": "+relevant.size());
					check+=relevant.size();
					String datapath = Path.odds_dat+label+"/"+season+"/"+i+"/";
					new File(datapath).mkdirs();
					PrintWriter pw = new PrintWriter(datapath+lab+".txt");
					for(Odds o: relevant){
						String dataline = "Odds,"+o.getBookie().getId()+","+o.getBookie().getName()+","+
								o.getGame().getId()+","+o.getWinHome()+","+o.getDraw()+","+o.getWinAway(); 
						pw.println(dataline);
					}
					pw.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(check);
	}

}
