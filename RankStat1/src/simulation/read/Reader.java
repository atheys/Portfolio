package simulation.read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.core.books.Bookie;
import data.core.books.Odds;
import data.core.paths.Path;
import data.core.stats.Defense;
import data.core.stats.Offense;
import data.core.stats.Psych;
import data.core.structure.Competition;
import data.core.structure.Game;
import data.core.structure.GameDay;
import data.core.structure.Team;
import exceptions.CollectorException;
import input.readers.GameDayReader;
import simulation.analyze.selection.Capsule;
import simulation.simulate.Manifest;

/**
 * Simulation input reader class.
 * 
 * @author Andreas Theys.
 * @version 3.0
 */
public class Reader {

	/**
	 * Creates Competition-object.
	 * 
	 * @param label
	 *            label string of the competition.
	 * @param season
	 *            seasonal string appendix of the competition.
	 * @return corresponding Competition-object.
	 */
	private static Competition createCompetition(String label, String season) {
		return new Competition(label + "-" + season, new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
	}

	/**
	 * Creates Competition-object with corresponding teams.
	 * 
	 * @param label
	 *            label string of the competition.
	 * @param season
	 *            seasonal string appendix of the competition.
	 * @return corresponding Competition-object.
	 * @throws IOException
	 */
	private static Competition createTeams(String label, String season) throws IOException {
		String datapath = Path.games_dat + label + "/" + season + "/" + "Teams.txt";
		Competition c = createCompetition(label, season);
		BufferedReader br = new BufferedReader(new FileReader(datapath));
		String line;
		while ((line = br.readLine()) != null) {
			Team temp = new Team(line.trim(), c, 0, 0, 0, 0, new ArrayList<Game>());
		}
		br.close();
		return c;
	}

	/**
	 * Creates Competition-object with corresponding games (and teams).
	 * 
	 * @param label
	 *            label string of the competition.
	 * @param season
	 *            seasonal string appendix of the competition.
	 * @param gDays
	 *            number of game days.
	 * @param teams
	 *            number of teams in competition.
	 * @return corresponding Competition-object.
	 * @throws IOException
	 */
	public static Competition createGames(String label, String season, int gDays, int teams) throws IOException {
		Competition c = createTeams(label, season);
		String datapath = Path.games_dat + label + "/" + season + "/";
		for (int i = 1; i <= gDays; i++) {
			String datapath_1 = datapath + i + "/" + label + "-" + season + "-" + i + "-";
			for (int j = 1; j <= teams / 2; j++) {
				String datapath_2 = datapath_1 + j + ".txt";
				BufferedReader br = new BufferedReader(new FileReader(datapath_2));
				String line;
				Game g = null;
				while ((line = br.readLine()) != null) {
					String[] elements = line.split(",");
					if (elements[0].equals("Game")) {
						try {
							g = new Game(elements[2], c.getTeam(elements[3].trim()), c.getTeam(elements[4].trim()),
									GameDayReader.read(elements[5]), c, true, Integer.parseInt(elements[6]),
									Integer.parseInt(elements[7]));
						} catch (Exception e) {
							// Logger capacity
						}
					}
					if (g != null) {
						if (elements[0].equals("Offense")) {
							Offense o = new Offense(Integer.parseInt(elements[3]), Double.parseDouble(elements[4]),
									Integer.parseInt(elements[5]), Integer.parseInt(elements[6]),
									Double.parseDouble(elements[7]), Integer.parseInt(elements[8]),
									Integer.parseInt(elements[9]), Integer.parseInt(elements[10]),
									Integer.parseInt(elements[11]), Integer.parseInt(elements[12]),
									Integer.parseInt(elements[13]));
							if (g.getHome().getName().equals(elements[2])) {
								g.setHomeStatsOff(o);
							}
							if (g.getAway().getName().equals(elements[2])) {
								g.setAwayStatsOff(o);
							}
						}
						if (elements[0].equals("Defense")) {
							Defense d = new Defense(Integer.parseInt(elements[3]), Integer.parseInt(elements[4]),
									Integer.parseInt(elements[5]), Boolean.parseBoolean(elements[6]),
									Integer.parseInt(elements[7]), Integer.parseInt(elements[8]),
									Integer.parseInt(elements[9]));
							if (g.getHome().getName().equals(elements[2])) {
								g.setHomeStatsDef(d);
							}
							if (g.getAway().getName().equals(elements[2])) {
								g.setAwayStatsDef(d);
							}
						}
					}
					g.setHomeStatsPsych(new Psych(g.getHome(), 1.));
					g.setAwayStatsPsych(new Psych(g.getAway(), 1.));
				}
				br.close();
			}
		}
		return c;
	}

	/**
	 * Creates with all relevant bookie agencies (stored data file input).
	 * 
	 * @return corresponding list of bookies.
	 * @throws IOException
	 */
	public static ArrayList<Bookie> createBookies() throws IOException {
		ArrayList<Bookie> bookies = new ArrayList<Bookie>();
		String path = Path.odds_b + "bookiesOP.txt";
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		while ((line = br.readLine()) != null) {
			String[] labels = line.split(",");
			assert labels.length == 2;
			Bookie b = new Bookie(labels[0].trim(), labels[1].trim(), new ArrayList<Odds>());
			bookies.add(b);
		}
		br.close();
		return bookies;
	}

	/**
	 * Bookie finder function (based on ID).
	 * 
	 * @param id
	 *            bookie ID feature.
	 * @param bookies
	 *            list of Bookie-Objects to search in.
	 * @return relevant Bookie-Object.
	 * @throws CollectorException
	 */
	private static Bookie findID(String id, ArrayList<Bookie> bookies) throws CollectorException {
		for (int i = 0; i < bookies.size(); i++) {
			if (bookies.get(i).getId().equals(id)) {
				return bookies.get(i);
			}
		}
		throw new CollectorException("Bookie-object not found.");
	}

	/**
	 * Bookie finder function (based on name).
	 * 
	 * @param name
	 *            bookie name feature.
	 * @param bookies
	 *            list of Bookie-Objects to search in.
	 * @return relevant Bookie-Object.
	 * @throws CollectorException
	 */
	public static Bookie findName(String name, ArrayList<Bookie> bookies) throws CollectorException {
		for (int i = 0; i < bookies.size(); i++) {
			if (bookies.get(i).getName().equals(name)) {
				return bookies.get(i);
			}
		}
		throw new CollectorException("Bookie-object not found.");
	}

	/**
	 * Game-Object finder.
	 * 
	 * @param home
	 *            home team name.
	 * @param away
	 *            away team name.
	 * @param c
	 *            relevant Competition-Object.
	 * @return corresponding Game-Object.
	 * @throws CollectorException
	 */
	public static Game find(String home, String away, Competition c) throws CollectorException {
		for (int i = 0; i < c.getGames().size(); i++) {
			boolean eval = c.getGames().get(i).getHome().getName().equals(home)
					&& c.getGames().get(i).getAway().getName().equals(away);
			if (eval) {
				return c.getGames().get(i);
			}
		}
		throw new CollectorException("Game-object not found.");
	}

	/**
	 * Game-Object finder.
	 * 
	 * @param id
	 *            game ID feature.
	 * @param c
	 *            relevant Competition-Object.
	 * @return corresponding Game-Object.
	 * @throws CollectorException
	 */
	public static Game find(String id, Competition c) throws CollectorException {
		for (int i = 0; i < c.getGames().size(); i++) {
			if (c.getGames().get(i).getId().equals(id)) {
				return c.getGames().get(i);
			}
		}
		throw new CollectorException("Game-object not found.");
	}

	/**
	 * Odds creation function.
	 * 
	 * @param label
	 *            competition label.
	 * @param season
	 *            seasonal label.
	 * @param gDays
	 *            number of game days.
	 * @param teams
	 *            number of teams in competition.
	 * @param bookies
	 *            list of relevant Bookie-Objects.
	 * @param c
	 *            corresponding Competition-Object.
	 * @return corresponding Bookie-list.
	 * @throws IOException
	 * @throws CollectorException
	 */
	private static ArrayList<Bookie> createOdds(String label, String season, int gDays, int teams,
			ArrayList<Bookie> bookies, Competition c) throws IOException, CollectorException {
		String path = Path.odds_dat + label + "/" + season + "/";
		for (int i = 1; i <= gDays; i++) {
			for (int j = 1; j < teams / 2; j++) {
				String code = label + "-" + season + "-" + i + "-" + j + ".txt";
				String data_path = path + i + "/" + code;
				BufferedReader br = new BufferedReader(new FileReader(data_path));
				String line;
				while ((line = br.readLine()) != null) {
					String[] items = line.split(",");
					if (items[0].equals("Odds")) {
						Bookie b;
						try {
							b = findID(items[1], bookies);
						} catch (Exception e1) {
							try {
								b = findName(items[2], bookies);
							} catch (Exception e) {
								b = new Bookie(items[1], items[2], new ArrayList<Odds>());
								bookies.add(b);
							}
						}
						Game g = find(items[3], c);
						double winHome = Double.parseDouble(items[4]);
						double draw = Double.parseDouble(items[5]);
						double winAway = Double.parseDouble(items[6]);
						// Odds updating redundancy
						Odds o = new Odds(items[3], b, g, winHome, draw, winAway);
					}
				}
				br.close();
			}
		}
		return bookies;
	}

	/**
	 * Fully updated Competition-Object reader.
	 * 
	 * @param label
	 *            competition label.
	 * @param season
	 *            season label.
	 * @param gDays
	 *            number of game days.
	 * @param teams
	 *            number of teams in competition.
	 * @return corresponding Competition-Object.
	 * @throws IOException
	 * @throws CollectorException
	 */
	public static Competition read(String label, String season, int gDays, int teams)
			throws IOException, CollectorException {
		ArrayList<Bookie> bookies = createBookies();
		Competition c = createGames(label, season, gDays, teams);
		//bookies = createOdds(label, season, gDays, teams, bookies, c);
		c.setBookies(bookies);
		/*for (Game g : c.getGames()) {
			// Odds updating redundancy
			ArrayList<Odds> odds = c.findOdds(g);
		}*/
		return c;
	}

	/**
	 * Final read-in function.
	 * 
	 * @note: without anaylzing psychological situation.
	 * @param c
	 *            competition data capsule (Capsule-Object).
	 * @note relevant Capsule-Objects always found in Manifest-class.
	 * @return corresponding Competition-Object.
	 * @throws IOException
	 * @throws CollectorException
	 */
	public static Competition QUICK_READ(Capsule c) throws IOException, CollectorException {
		Competition comp = read(c.competition, c.season, c.gDays, c.teams);
		return comp;
	}
	
	
	/**
	 * Final read-in function.
	 * 
	 * @param c
	 *            competition data capsule (Capsule-Object).
	 * @note relevant Capsule-Objects always found in Manifest-class.
	 * @return corresponding Competition-Object.
	 * @throws IOException
	 * @throws CollectorException
	 */
	public static Competition READ(Capsule c) throws IOException, CollectorException {
		Competition comp = read(c.competition, c.season, c.gDays, c.teams);
		PsychAnalyzer.ANALYZE(comp);
		return comp;
	}

	public static void main(String[] args) throws IOException, CollectorException {
		Competition c = READ(Manifest.c20);
		Competition c_copy = new Competition(c);
		PsychAnalyzer.ANALYZE(c_copy);
		for(int i=0; i<c.getGames().size();i++){
			Game g = c.getGames().get(i);
			String s = g.getHomeStatsPsych().getImportance()+"-"+g.getAwayStatsPsych().getImportance();
			Game g_copy = c_copy.getGames().get(i);
			String s_copy = g_copy.getHomeStatsPsych().getImportance()+"-"+g_copy.getAwayStatsPsych().getImportance();
			System.out.println(s+" : "+s_copy);
		}
		
	}

}