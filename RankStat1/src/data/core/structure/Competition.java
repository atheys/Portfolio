package data.core.structure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import data.core.books.Bookie;
import data.core.books.Odds;
import data.core.paths.Path;
import exceptions.CollectorException;
import models.general.handlers.Collector;

/**
 * Class competition. Main entity for competition-related analysis.
 * 
 * @author Andreas Theys
 * @version 6.0
 */
public class Competition {

	/**
	 * Class attributes.
	 */
	private String id;
	private ArrayList<Team> teams;
	private ArrayList<GameDay> gameDays;
	private ArrayList<Game> games;
	private ArrayList<Rank> ranking;
	private ArrayList<Bookie> bookies;

	/**
	 * Copy constructor.
	 * 
	 * @param c
	 *            Competition-object to copy.
	 */
	public Competition(Competition c) {
		this.id = c.id;
		this.teams = c.teams;
		this.gameDays = c.gameDays;
		this.games = c.games;
		this.ranking = c.ranking;
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the competition.
	 * @param teams
	 *            list of teams in competition.
	 * @param gameDays
	 *            list of foreseen game days.
	 * @param games
	 *            list of foreseen games.
	 */
	public Competition(String id, ArrayList<Team> teams, ArrayList<GameDay> gameDays, ArrayList<Game> games) {
		this.id = id;
		this.teams = teams;
		this.gameDays = gameDays;
		this.games = games;
		this.sortTeams();
		this.sortGames();
		// Updating
		this.updateTeams();
		this.updateGames();
		this.mineGameDays(this.games);
		this.orderRanking();
	}

	/**
	 * ID-getter.
	 * 
	 * @return ID of the competition.
	 */
	public String getId() {
		return id;
	}

	/**
	 * ID-setter.
	 * 
	 * @param id
	 *            ID of the competition.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter list of teams.
	 * 
	 * @return list of teams in competition.
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}

	/**
	 * Setter list of teams.
	 * 
	 * @param teams
	 *            new list of teams in competition.
	 */
	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
	}

	/**
	 * Getter list of game days.
	 * 
	 * @return list of competition game days.
	 */
	public ArrayList<GameDay> getGameDays() {
		return gameDays;
	}

	/**
	 * Setter list of game days.
	 * 
	 * @param gameDays
	 *            new list of game days in competition.
	 */
	public void setGameDays(ArrayList<GameDay> gameDays) {
		this.gameDays = gameDays;
	}

	/**
	 * Getter list of games.
	 * 
	 * @return list of competition games.
	 */
	public ArrayList<Game> getGames() {
		return games;
	}

	/**
	 * Setter list of games.
	 * 
	 * @param gameDays
	 *            new list of games in competition.
	 */
	public void setGames(ArrayList<Game> games) {
		this.games = games;
		this.sortGames();
	}

	/**
	 * Getter ranking.
	 * 
	 * @return ranking of the competition.
	 */
	public ArrayList<Rank> getRanking() {
		return ranking;
	}

	/**
	 * Setter ranking.
	 * 
	 * @param ranking
	 *            new ranking of the competition.
	 */
	public void setRanking(ArrayList<Rank> ranking) {
		this.ranking = ranking;
	}

	/**
	 * Getter bookies list.
	 * 
	 * @return bookies bookies list.
	 */
	public ArrayList<Bookie> getBookies() {
		return bookies;
	}

	/**
	 * Setter bookies list.
	 * 
	 * @param bookies
	 *            new list of bookies.
	 */
	public void setBookies(ArrayList<Bookie> bookies) {
		this.bookies = bookies;
	}

	/**
	 * Team getter.
	 * 
	 * @param teamName
	 *            name of the team.
	 * @return corresponding team.
	 * @throws CollectorException
	 */
	public Team getTeam(String teamName) throws CollectorException {
		for (Team t : teams) {
			if (t.getName().equals(teamName)) {
				return t;
			}
		}
		throw new CollectorException("Team not found.");
	}

	/**
	 * Games getter.
	 * 
	 * @param gd
	 *            GameDay-Object to search for.
	 * @return corresponding list of games.
	 */
	public ArrayList<Game> getGames(GameDay gd) {
		ArrayList<Game> gam = new ArrayList<Game>();
		if (this.gameDays.contains(gd)) {
			for (Game g : this.games) {
				if (g.getGameDay().before(gd)) {
					gam.add(g);
				}
			}
		}
		return gam;
	}

	/**
	 * Mines game days.
	 * 
	 * @param list
	 *            of Game-Objects to mine for.
	 */
	public void mineGameDays(ArrayList<Game> games) {
		for (Game g : games) {
			if (!this.gameDays.contains(g.getGameDay())) {
				this.gameDays.add(g.getGameDay());
			}
		}
		this.sortGameDays();
	}

	/**
	 * Sorts teams list alphabetically. Uses insertion sort algorithm.
	 */
	public void sortTeams() {
		if (this.teams.size() > 1) {
			for (int i = teams.size() - 1; i >= 0; i--) {
				for (int j = 1; j <= i; j++) {
					String name1 = this.teams.get(j - 1).getName();
					String name2 = this.teams.get(j).getName();
					if (name1.compareToIgnoreCase(name2) > 0) {
						Team temp = teams.get(j - 1);
						teams.add(j - 1, teams.get(j));
						teams.remove(j);
						teams.add(j, temp);
						teams.remove(j + 1);
					}
				}
			}
		}
	}

	/*
	 * Sorts games according to their date. Uses insertion sort algorithm.
	 */
	private void sortGames() {
		if (this.games.size() > 1) {
			for (int i = games.size() - 1; i >= 0; i--) {
				for (int j = 1; j <= i; j++) {
					if (games.get(j - 1).getGameDay().after(games.get(j).getGameDay())) {
						Game temp = games.get(j - 1);
						games.add(j - 1, games.get(j));
						games.remove(j);
						games.add(j, temp);
						games.remove(j + 1);
					}
				}
			}
		}
	}

	/*
	 * Sorts gamedays according to their date. Uses bubble sort algorithm.
	 */
	private void sortGameDays() {
		if (this.gameDays.size() > 1) {
			for (int i = gameDays.size() - 1; i >= 0; i--) {
				for (int j = 1; j <= i; j++) {
					if (gameDays.get(j - 1).after(gameDays.get(j))) {
						GameDay temp = gameDays.get(j - 1);
						gameDays.add(j - 1, gameDays.get(j));
						gameDays.remove(j);
						gameDays.add(j, temp);
						gameDays.remove(j + 1);
					}
				}
			}
		}
	}

	/**
	 * Updates teams.
	 */
	private void updateTeams() {
		for (Team t : teams) {
			t.setCompetition(this);
		}
	}

	/**
	 * Updates list of games.
	 */
	private void updateGames() {
		for (Game g : games) {
			g.setCompetition(this);
		}

	}

	/*
	 * Filters out games based on a specific game day date.
	 * 
	 * @param gd GameDay-object to filter on.
	 */
	public ArrayList<Game> filterGames(GameDay gd) {
		if (this.games.size() >= 1) {
			ArrayList<Game> filtered = new ArrayList<Game>();
			for (int i = 0; i < games.size(); i++) {
				if (games.get(i).getGameDay().equals(gd)) {
					filtered.add(games.get(i));
				}
			}
			return filtered;
		}
		return new ArrayList<Game>();
	}

	/**
	 * Orders ranking according to rank number.
	 */
	public void orderRanking() {
		ArrayList<Rank> new_ranking = new ArrayList<Rank>();
		for (Team t : this.teams) {
			Rank r = new Rank(t);
			new_ranking.add(r);
		}
		this.setRanking(new_ranking);
		this.sortRanking();
		for (Rank r : this.ranking) {
			r.assignRank(this.ranking);
		}
	}

	/**
	 * Sorts ranking list.
	 */
	private void sortRanking() {
		Rank[] ranks = new Rank[this.ranking.size()];
		for (int i = 0; i < ranks.length; i++) {
			ranks[i] = this.ranking.get(i);
		}
		for (int i = 1; i < ranks.length; i++) {
			for (int j = i; j > 0; j--) {
				boolean eval = (ranks[j].getPoints() < ranks[j - 1].getPoints())
						|| (ranks[j].getPoints() == ranks[j - 1].getPoints() && ((ranks[j].getGoalsScored()
								- ranks[j].getGoalsAgainst()) < (ranks[j - 1].getGoalsScored()
										- ranks[j - 1].getGoalsAgainst())));
				if (eval) {
					Rank temp = ranks[j];
					ranks[j] = ranks[j - 1];
					ranks[j - 1] = temp;
				}
			}
		}
		ArrayList<Rank> new_ranking = new ArrayList<Rank>();
		for (int i = ranks.length - 1; i >= 0; i--) {
			new_ranking.add(ranks[i]);
		}
		this.ranking = new_ranking;
	}

	/**
	 * Determines rank number of a Team-object in competition.
	 * 
	 * @param t
	 *            Team-object to check for.
	 * @return rank number.
	 */
	public int getRank(Team t) {
		for (Rank r : ranking) {
			if (r.getTeam().equals(t)) {
				return r.getRank();
			}
		}
		return 0;
	}

	/**
	 * Create pseudo-Competition object with standing before a given date.
	 * 
	 * @param gd
	 *            GameDay-object to consider.
	 * @return pseudo-Competition feature.
	 * @throws CollectorException
	 */
	public Competition pseudoCompetition(GameDay gd) throws CollectorException {
		Competition c_copy = new Competition(this.getId(), new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
		for (Team t : this.teams) {
			ArrayList<Game> gs = Collector.gatherGames(t, gd);
			ArrayList<Game> gs_copy = new ArrayList<Game>();
			for (Game g : gs) {
				Game g_copy = new Game(g);
				gs_copy.add(g_copy);
				g_copy.setCompetition(c_copy);
				g_copy.getHome().setCompetition(c_copy);
				g_copy.getAway().setCompetition(c_copy);
			}
			Team copy = new Team(t.getName(), c_copy, 0, 0, 0, 0, gs_copy);
		}
		ArrayList<Rank> ranking = new ArrayList<Rank>();
		for (Team t : c_copy.getTeams()) {
			Rank r = new Rank(t);
			ranking.add(r);
		}
		c_copy.setRanking(ranking);
		c_copy.orderRanking();
		return c_copy;
	}
	
	/**
	 * Create pseudo-Competition object with standing before a given date.
	 * 
	 * @param gd
	 *            GameDay-object to consider.
	 * @return pseudo-Competition feature.
	 * @throws CollectorException
	 */
	public Competition pseudoCompetitionHome(GameDay gd) throws CollectorException {
		Competition c_copy = new Competition(this.getId(), new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
		for (Team t : this.teams) {
			ArrayList<Game> gs = Collector.gatherHomeGames(t, gd);
			ArrayList<Game> gs_copy = new ArrayList<Game>();
			for (Game g : gs) {
				Game g_copy = new Game(g);
				gs_copy.add(g_copy);
				g_copy.setCompetition(c_copy);
				g_copy.getHome().setCompetition(c_copy);
				g_copy.getAway().setCompetition(c_copy);
			}
			Team copy = new Team(t.getName(), c_copy, 0, 0, 0, 0, gs_copy);
		}
		ArrayList<Rank> ranking = new ArrayList<Rank>();
		for (Team t : c_copy.getTeams()) {
			Rank r = new Rank(t);
			ranking.add(r);
		}
		c_copy.setRanking(ranking);
		c_copy.orderRanking();
		return c_copy;
	}
	
	/**
	 * Create pseudo-Competition object with standing before a given date.
	 * 
	 * @param gd
	 *            GameDay-object to consider.
	 * @return pseudo-Competition feature.
	 * @throws CollectorException
	 */
	public Competition pseudoCompetitionAway(GameDay gd) throws CollectorException {
		Competition c_copy = new Competition(this.getId(), new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
		for (Team t : this.teams) {
			ArrayList<Game> gs = Collector.gatherAwayGames(t, gd);
			ArrayList<Game> gs_copy = new ArrayList<Game>();
			for (Game g : gs) {
				Game g_copy = new Game(g);
				gs_copy.add(g_copy);
				g_copy.setCompetition(c_copy);
				g_copy.getHome().setCompetition(c_copy);
				g_copy.getAway().setCompetition(c_copy);
			}
			Team copy = new Team(t.getName(), c_copy, 0, 0, 0, 0, gs_copy);
		}
		ArrayList<Rank> ranking = new ArrayList<Rank>();
		for (Team t : c_copy.getTeams()) {
			Rank r = new Rank(t);
			ranking.add(r);
		}
		c_copy.setRanking(ranking);
		c_copy.orderRanking();
		return c_copy;
	}

	/**
	 * Create pseudo-Competition object with standing before a given date.
	 * 
	 * @param gd
	 *            GameDay-object to consider.
	 * @param n
	 *            number of games to back-track for.
	 * @return pseudo-Competition feature.
	 * @throws CollectorException
	 */
	public Competition pseudoCompetition(GameDay gd, int n) throws CollectorException {
		Competition c_copy = new Competition(this.getId(), new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
		for (Team t : this.teams) {
			ArrayList<Game> gs = Collector.gatherGames(t, gd, n);
			ArrayList<Game> gs_copy = new ArrayList<Game>();
			for (Game g : gs) {
				Game g_copy = new Game(g);
				gs_copy.add(g_copy);
				g_copy.setCompetition(c_copy);
				g_copy.getHome().setCompetition(c_copy);
				g_copy.getAway().setCompetition(c_copy);
			}
			Team copy = new Team(t.getName(), c_copy, 0, 0, 0, 0, gs_copy);
		}
		ArrayList<Rank> ranking = new ArrayList<Rank>();
		for (Team t : c_copy.getTeams()) {
			Rank r = new Rank(t);
			ranking.add(r);
		}
		c_copy.setRanking(ranking);
		c_copy.orderRanking();
		return c_copy;
	}
	
	/**
	 * Create pseudo-Competition object with standing before a given date.
	 * 
	 * @param gd
	 *            GameDay-object to consider.
	 * @param n
	 *            number of games to back-track for.
	 * @return pseudo-Competition feature.
	 * @throws CollectorException
	 */
	public Competition pseudoCompetitionHome(GameDay gd, int n) throws CollectorException {
		Competition c_copy = new Competition(this.getId(), new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
		for (Team t : this.teams) {
			ArrayList<Game> gs = Collector.gatherHomeGames(t, gd, n);
			ArrayList<Game> gs_copy = new ArrayList<Game>();
			for (Game g : gs) {
				Game g_copy = new Game(g);
				gs_copy.add(g_copy);
				g_copy.setCompetition(c_copy);
				g_copy.getHome().setCompetition(c_copy);
				g_copy.getAway().setCompetition(c_copy);
			}
			Team copy = new Team(t.getName(), c_copy, 0, 0, 0, 0, gs_copy);
		}
		ArrayList<Rank> ranking = new ArrayList<Rank>();
		for (Team t : c_copy.getTeams()) {
			Rank r = new Rank(t);
			ranking.add(r);
		}
		c_copy.setRanking(ranking);
		c_copy.orderRanking();
		return c_copy;
	}
	
	/**
	 * Create pseudo-Competition object with standing before a given date.
	 * 
	 * @param gd
	 *            GameDay-object to consider.
	 * @param n
	 *            number of games to back-track for.
	 * @return pseudo-Competition feature.
	 * @throws CollectorException
	 */
	public Competition pseudoCompetitionAway(GameDay gd, int n) throws CollectorException {
		Competition c_copy = new Competition(this.getId(), new ArrayList<Team>(), new ArrayList<GameDay>(),
				new ArrayList<Game>());
		for (Team t : this.teams) {
			ArrayList<Game> gs = Collector.gatherAwayGames(t, gd, n);
			ArrayList<Game> gs_copy = new ArrayList<Game>();
			for (Game g : gs) {
				Game g_copy = new Game(g);
				gs_copy.add(g_copy);
				g_copy.setCompetition(c_copy);
				g_copy.getHome().setCompetition(c_copy);
				g_copy.getAway().setCompetition(c_copy);
			}
			Team copy = new Team(t.getName(), c_copy, 0, 0, 0, 0, gs_copy);
		}
		ArrayList<Rank> ranking = new ArrayList<Rank>();
		for (Team t : c_copy.getTeams()) {
			Rank r = new Rank(t);
			ranking.add(r);
		}
		c_copy.setRanking(ranking);
		c_copy.orderRanking();
		return c_copy;
	}

	/**
	 * Finds Bookie-Object based on an ID feature.
	 * 
	 * @param id
	 *            ID of the Bookie-Object.
	 * @return corresponding Bookie-Object.
	 * @throws CollectorException
	 */
	private Bookie findBookie(String id) throws CollectorException {
		for (Bookie b : bookies) {
			if (id.equals(b.getId())) {
				return b;
			}
		}
		throw new CollectorException("Bookie not found.");
	}

	private ArrayList<Odds> digOdds(Game g) throws IOException {
		ArrayList<Odds> odds = new ArrayList<Odds>();
		String[] data = g.getId().split("-");
		try {
			String location = Path.odds_dat + data[0] + "/" + data[1] + "/" + data[2] + "/" + data[0] + "-" + data[1]
					+ "-" + data[2] + "-" + data[3] + ".txt";
			BufferedReader br = new BufferedReader(new FileReader(location));
			String line;
			while ((line = br.readLine()) != null) {
				try {
					line = line.trim();
					String[] features = line.split(",");
					Bookie b = findBookie(features[1]);
					double H = Double.parseDouble(features[4]);
					double D = Double.parseDouble(features[5]);
					double A = Double.parseDouble(features[6]);
					Odds o = new Odds(g.getId(), b, g, H, D, A);
					odds.add(o);
				} catch (Exception e) {

				}
			}
			br.close();
		} catch (Exception e) {

		}
		return odds;
	}

	/**
	 * Finds Game-Objects based on a given ID feature.
	 * 
	 * @param game_id
	 *            ID of the Game-Object.
	 * @return corresponding Game-Object.
	 * @throws CollectorException
	 */
	public Game findGame(String game_id) throws CollectorException {
		for (Game g : this.games) {
			if (g.getId().equals(game_id)) {
				return g;
			}
		}
		throw new CollectorException("No Game-object found.");
	}

	/**
	 * Finds corresponding odds for a specific Game-object.
	 * 
	 * @param g
	 *            Game-object to search for.
	 * @return corresponding list of odds.
	 * @throws CollectorException
	 */
	public ArrayList<Odds> findOdds(String game_id) {
		try {
			return findOdds(findGame(game_id));
		} catch (Exception e) {
			return new ArrayList<Odds>();
		}
	}

	/**
	 * Finds corresponding odds for a specific Game-object.
	 * 
	 * @param g
	 *            Game-object to search for.
	 * @return corresponding list of odds.
	 */
	public ArrayList<Odds> findOdds(Game g) {
		ArrayList<Odds> odds1 = new ArrayList<Odds>();
		for (Bookie b : this.bookies) {
			for (Odds o : b.getOdds()) {
				if (o.getId().equals(g.getId())) {
					odds1.add(o);
				}
			}
		}
		ArrayList<Odds> odds2 = new ArrayList<Odds>();
		if (odds1.size() == 0) {
			try {
				odds2 = digOdds(g);
			} catch (Exception e) {
			}
		}
		return mergeOdds(odds1, odds2);
	}

	/**
	 * Merges two lists of Odds.
	 * 
	 * @param odds1
	 *            first Odds-list.
	 * @param odds2
	 *            second Odds-list.
	 * @return merged Odds-list.
	 */
	private ArrayList<Odds> mergeOdds(ArrayList<Odds> odds1, ArrayList<Odds> odds2) {
		if (odds1.size() == 0)
			return odds2;
		if (odds2.size() == 0)
			return odds1;
		for (int i = 0; i < odds1.size(); i++) {
			boolean evaluation = false;
			for (int j = 0; j < odds2.size(); j++) {
				if (odds1.get(i).equals(odds2.get(j))) {
					evaluation = true;
				}
			}
			if (!evaluation) {
				odds2.add(odds1.get(i));
			}
		}
		return odds2;
	}

	/**
	 * Compares Competition-objects to one another.
	 * 
	 * @param obj
	 *            Object to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Competition) {
			Competition that = (Competition) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}