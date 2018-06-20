package input.handlers.datamining.footballdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import data.core.books.Bookie;
import data.core.books.Odds;
import data.core.paths.Path;
import data.core.structure.Competition;
import data.core.structure.Game;
import exceptions.CollectorException;
import input.finders.labels.DataLabels_BL;
import input.finders.labels.DataLabels_ED;
import input.finders.labels.DataLabels_LU;
import input.finders.labels.DataLabels_PD;
import input.finders.labels.DataLabels_PL;
import input.finders.labels.DataLabels_SA;
import input.finders.labels.DataLabels_SL;
import input.finders.labels.DataLabels_TC;
import input.finders.labels.Label;
import simulation.read.Reader;

/**
 * Data Mining Manager Class for stats at football-data.co.uk.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class FBDMiningManager {

	/**
	 * Class attributes.
	 */
	private static String pathBookieData = Path.odds_b + "bookies.txt";

	/**
	 * Gather basic Bookie data from fixed file.
	 * 
	 * @return basic bookie data list.
	 * @throws IOException
	 */
	private static ArrayList<Bookie> gatherBookies() throws IOException {
		ArrayList<Bookie> bookies = new ArrayList<Bookie>();
		BufferedReader br = new BufferedReader(new FileReader(pathBookieData));
		String line;
		while ((line = br.readLine()) != null) {
			String[] lines = line.split(",");
			assert lines.length == 2;
			bookies.add(new Bookie(lines[0], lines[1], new ArrayList<Odds>()));
		}
		br.close();
		return bookies;
	}

	/**
	 * Bookie-object find function.
	 * 
	 * @param id
	 *            ID feature of the Bookie.
	 * @param bookies
	 *            list of respective Bookie-objects.
	 * @return corresponding Bookie-object.
	 */
	private static Bookie find(String id, ArrayList<Bookie> bookies) throws CollectorException {
		for (int i = 0; i < bookies.size(); i++) {
			if (bookies.get(i).getId().equals(id)) {
				return bookies.remove(i);
			}
		}
		throw new CollectorException("Bookie not found.");
	}

	/**
	 * Game-object find function.
	 * 
	 * @param home
	 *            home team name.
	 * @param away
	 *            away team name.
	 * @param c
	 *            Competition-object to check for.
	 * @return corresponding Game-object.
	 * @throws CollectorException
	 */
	private static Game find(String home, String away, Competition c) throws CollectorException {
		for (Game g : c.getGames()) {
			if (g.getHome().getName().equals(home) && g.getAway().getName().equals(away)) {
				return g;
			}
		}
		throw new CollectorException("Game not found.");
	}

	/**
	 * Arranges (and updates) Bookie-objects list.
	 * 
	 * @param indicator
	 *            indicator String array.
	 * @param lines
	 *            read-in lines list.
	 * @param bookies
	 *            list of Bookie-objects.
	 * @param c
	 *            corresponding Competition-object.
	 * @return corresponding Bookie list.
	 * @throws CollectorException
	 */
	private static ArrayList<Bookie> arrange(String[] indicator, ArrayList<String[]> lines, ArrayList<Bookie> bookies, Competition c) {
		for (String[] line : lines) {
			int HomeTeam = -1;
			int AwayTeam = -1;
			int B365H = -1;
			int B365D = -1;
			int B365A = -1;
			int BSH = -1;
			int BSD = -1;
			int BSA = -1;
			int BWH = -1;
			int BWD = -1;
			int BWA = -1;
			int GBH = -1;
			int GBD = -1;
			int GBA = -1;
			int IWH = -1;
			int IWD = -1;
			int IWA = -1;
			int LBH = -1;
			int LBD = -1;
			int LBA = -1;
			int PSH = -1;
			int PSD = -1;
			int PSA = -1;
			int SOH = -1;
			int SOD = -1;
			int SOA = -1;
			int SBH = -1;
			int SBD = -1;
			int SBA = -1;
			int SJH = -1;
			int SJD = -1;
			int SJA = -1;
			int SYH = -1;
			int SYD = -1;
			int SYA = -1;
			int VCH = -1;
			int VCD = -1;
			int VCA = -1;
			int WHH = -1;
			int WHD = -1;
			int WHA = -1;
			for (int i = 0; i < line.length; i++) {
				switch (indicator[i]) {
				case "HomeTeam":
					HomeTeam = i;
					break;
				case "AwayTeam":
					AwayTeam = i;
					break;
				case "B365H":
					B365H = i;
					break;
				case "B365D":
					B365D = i;
					break;
				case "B365A":
					B365A = i;
					break;
				case "BSH":
					BSH = i;
					break;
				case "BSD":
					BSD = i;
					break;
				case "BSA":
					BSA = i;
					break;
				case "BWH":
					BWH = i;
					break;
				case "BWD":
					BWD = i;
					break;
				case "BWA":
					BWA = i;
					break;
				case "GBH":
					GBH = i;
					break;
				case "GBD":
					GBD = i;
					break;
				case "GBA":
					GBA = i;
					break;
				case "IWH":
					IWH = i;
					break;
				case "IWD":
					IWD = i;
					break;
				case "IWA":
					IWA = i;
					break;
				case "LBH":
					LBH = i;
					break;
				case "LBD":
					LBD = i;
					break;
				case "LBA":
					LBA = i;
					break;
				case "PSH":
					PSH = i;
					break;
				case "PSD":
					PSD = i;
					break;
				case "PSA":
					PSA = i;
					break;
				case "SOH":
					SOH = i;
					break;
				case "SOD":
					SOD = i;
					break;
				case "SOA":
					SOA = i;
					break;
				case "SBH":
					SBH = i;
					break;
				case "SBD":
					SBD = i;
					break;
				case "SBA":
					SBA = i;
					break;
				case "SJH":
					SJH = i;
					break;
				case "SJD":
					SJD = i;
					break;
				case "SJA":
					SJA = i;
					break;
				case "SYH":
					SYH = i;
					break;
				case "SYD":
					SYD = i;
					break;
				case "SYA":
					SYA = i;
					break;
				case "VCH":
					VCH = i;
					break;
				case "VCD":
					VCD = i;
					break;
				case "VCA":
					VCA = i;
					break;
				case "WHH":
					WHH = i;
					break;
				case "WHD":
					WHD = i;
					break;
				case "WHA":
					WHA = i;
					break;
				default:
					break;
				}
				Game game;
				try {
					game = find(line[HomeTeam], line[AwayTeam], c);
				} catch (Exception e) {
					continue;
				}
				int[][] odds = { { B365H, B365D, B365A }, { BSH, BSD, BSA }, { BWH, BWD, BWA }, { GBH, GBD, GBA },
						{ IWH, IWD, IWA }, { LBH, LBD, LBA }, { PSH, PSD, PSA }, { SOH, SOD, SOA }, { SBH, SBD, SBA },
						{ SJH, SJD, SJA }, { SYH, SYD, SYA }, { VCH, VCD, VCA }, { WHH, WHD, WHA } };
				String[] ids = { "B365", "BS", "BW", "GB", "IW", "LB", "PS", "SO", "SB", "SJ", "SY", "VC", "WH" };
				for (int j = 0; j < odds.length; j++) {
					if ((odds[j][0] != -1) && (odds[j][1] != -1) && (odds[j][2] != -1)) {
						try {
							Bookie bookie = find(ids[j], bookies);
							double winHome = Double.parseDouble(line[odds[j][0]]);
							double draw = Double.parseDouble(line[odds[j][1]]);
							double winAway = Double.parseDouble(line[odds[j][1]]);
							Odds o = new Odds(game.getId(), bookie, game, winHome, draw, winAway);
							bookies.add(bookie);
						} catch (Exception e) {
						}
					}
					if (!((odds[j][0] != -1) && (odds[j][1] != -1) && (odds[j][2] != -1)))
						System.out.println("FUCK: " + j);

				}
			}
		}
		return bookies;
	}

	/**
	 * Overall data gathering functionality.
	 * 
	 * @param label
	 *            Competition label.
	 * @param season
	 *            seasonal feature.
	 * @param gDays
	 *            number of competition game days.
	 * @param teams
	 *            number of teams in competition.
	 * @return corresponding Bookie-object list.
	 * @throws IOException
	 */
	private static ArrayList<Bookie> gather(String label, String season, int gDays, int teams) throws IOException {
		ArrayList<Bookie> bookies = gatherBookies();
		Competition c = Reader.createGames(label, season, gDays, teams);
		String raw_data_path = Path.odds_rd + label + "/" + season + ".csv";
		ArrayList<String[]> lines = new ArrayList<String[]>();
		BufferedReader br = new BufferedReader(new FileReader(raw_data_path));
		String line;
		while ((line = br.readLine()) != null) {
			String[] temp = line.split(",");
			lines.add(temp);
		}
		br.close();
		String[] indicator = lines.remove(0);
		bookies = arrange(indicator, lines, bookies, c);
		return bookies;
	}

	/**
	 * Odds finder.
	 * 
	 * @param g
	 *            Game-object to search odds for.
	 * @param bookies
	 *            list of bookies.
	 * @return
	 */
	private static ArrayList<Odds> find(Game g, ArrayList<Bookie> bookies) {
		ArrayList<Odds> odds = new ArrayList<Odds>();
		for (Bookie b : bookies) {
			for (Odds o : b.getOdds()) {
				if (g.getId().equals(o.getId())) {
					odds.add(o);
				}
			}
		}
		return odds;
	}

	/**
	 * Overall data mining functionality.
	 * 
	 * @param label
	 *            Competition label.
	 * @param season
	 *            seasonal feature.
	 * @param gDays
	 *            number of competition game days.
	 * @param teams
	 *            number of teams in competition.
	 * @throws IOException
	 */
	public static void mine(String label, String season, int gDays, int teams) throws IOException {
		Competition c = Reader.createGames(label, season, gDays, teams);
		ArrayList<Bookie> bookies = gather(label, season, gDays, teams);
		for (Game g : c.getGames()) {
			System.out.println(g.getId());
			String[] id_items = g.getId().split("-");
			assert id_items.length == 4;
			String data_path = Path.odds_dat + id_items[0] + "/" + id_items[1] + "/" + id_items[2] + "/" + g.getId()
					+ ".txt";
			ArrayList<Odds> odds = find(g, bookies);
			PrintWriter pw = new PrintWriter(new File(data_path));
			for (Odds o : odds) {
				String toPrint = "Odds," + o.getBookie().getId() + "," + o.getBookie().getName() + "," + o.getId() + ","
						+ o.getWinHome() + "," + o.getDraw() + "," + o.getWinAway();
				pw.println(toPrint);
			}
			pw.close();
		}
	}

}
