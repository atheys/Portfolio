package models.general.handlers;

import java.util.ArrayList;
import data.core.stats.Defense;
import data.core.stats.Offense;
import data.core.stats.Psych;
import data.core.structure.Competition;
import data.core.structure.Game;
import data.core.structure.GameDay;
import data.core.structure.Team;
import models.general.base.Average;
import models.general.base.Deviation;
import exceptions.CollectorException;
import exceptions.NotFoundException;

/**
 * Collector class that only contains static functions for correct data
 * gathering for each model.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class Collector {

	/**
	 * Gathers all played games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherGames(Team t, GameDay gd) throws CollectorException {
		t.sortGames();
		ArrayList<Game> games = t.getGames();
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		ArrayList<Game> toReturn = new ArrayList<Game>();
		for (int i = games.size() - 1; i >= 0; i--) {
			Game g = games.get(i);
			if (g.isPlayed() && g.getGameDay().before(gd))
				toReturn.add(g);
		}
		if (toReturn.size() == 0)
			throw new CollectorException("No games gathered.");
		return toReturn;
	}

	/**
	 * Gathers all played games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherGames(Team t, GameDay begin, GameDay end) throws CollectorException {
		t.sortGames();
		ArrayList<Game> games = t.getGames();
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		ArrayList<Game> toReturn = new ArrayList<Game>();
		for (int i = games.size() - 1; i >= 0; i--) {
			Game g = games.get(i);
			if (g.isPlayed() && (g.getGameDay().after(begin) || g.getGameDay().equals(begin))
					&& g.getGameDay().before(end)) {
				toReturn.add(g);
			}
		}
		if (toReturn.size() == 0)
			throw new CollectorException("No games gathered.");
		return toReturn;
	}

	/**
	 * Gathers all played home games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherHomeGames(Team t, GameDay gd) throws CollectorException {
		t.sortGames();
		ArrayList<Game> games = t.getGames();
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		ArrayList<Game> toReturn = new ArrayList<Game>();
		for (int i = games.size() - 1; i >= 0; i--) {
			Game g = games.get(i);
			boolean eval = g.isPlayed() && g.getGameDay().before(gd) && g.getHome().equals(t);
			if (eval)
				toReturn.add(games.get(i));
		}
		if (toReturn.size() == 0)
			throw new CollectorException("No games gathered.");
		return toReturn;
	}

	/**
	 * Gathers all played away games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherAwayGames(Team t, GameDay gd) throws CollectorException {
		t.sortGames();
		ArrayList<Game> games = t.getGames();
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		ArrayList<Game> toReturn = new ArrayList<Game>();
		for (int i = games.size() - 1; i >= 0; i--) {
			Game g = games.get(i);
			boolean eval = g.isPlayed() && g.getGameDay().before(gd) && g.getHome().equals(t);
			if (eval)
				toReturn.add(games.get(i));
		}
		if (toReturn.size() == 0)
			throw new CollectorException("No games gathered.");
		return toReturn;
	}

	/**
	 * Gathers a select number of valid games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @param gd
	 *            GameDay-object for date references.
	 * @param n
	 *            number of valid games to gather.
	 * @return list of valid games gathered.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherGames(Team t, GameDay gd, int n) throws CollectorException {
		ArrayList<Game> allGames = gatherGames(t, gd);
		if (allGames.size() < n)
			throw new CollectorException("Not enough games gathered.");
		ArrayList<Game> games = new ArrayList<Game>();
		for (int i = 0; i < n; i++) {
			games.add(allGames.get(i));
		}
		return games;
	}

	/**
	 * Gathers a select number of valid home games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @param gd
	 *            GameDay-object for date references.
	 * @param n
	 *            number of valid home games to gather.
	 * @return list of valid games gathered.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherHomeGames(Team t, GameDay gd, int n) throws CollectorException {
		ArrayList<Game> allGames = gatherHomeGames(t, gd);
		if (allGames.size() < n)
			throw new CollectorException("Not enough games gathered.");
		ArrayList<Game> games = new ArrayList<Game>();
		for (int i = 0; i < n; i++) {
			games.add(allGames.get(i));
		}
		return games;
	}

	/**
	 * Gathers a select number of valid away games from Team-object.
	 * 
	 * @param t
	 *            Team-object to gather games from.
	 * @param gd
	 *            GameDay-object for date references.
	 * @param n
	 *            number of valid away games to gather.
	 * @return list of valid games gathered.
	 * @throws CollectorException
	 */
	public static ArrayList<Game> gatherAwayGames(Team t, GameDay gd, int n) throws CollectorException {
		ArrayList<Game> allGames = gatherAwayGames(t, gd);
		if (allGames.size() < n)
			throw new CollectorException("Not enough games gathered.");
		ArrayList<Game> games = new ArrayList<Game>();
		for (int i = 0; i < n; i++) {
			games.add(allGames.get(i));
		}
		return games;
	}

	/**
	 * Collects offensive stats for a team in a certain Game-object (regardless
	 * of whether or not it is the home/away team).
	 * 
	 * @param t
	 *            Team-object to collect stats for.
	 * @param g
	 *            Game-object to search in.
	 * @return offensive stats of Team t in Game g.
	 * @throws NotFoundException
	 *             in case Team t did not play in Game g.
	 */
	private static Offense collectTeamOffense(Team t, Game g) throws NotFoundException {
		if (g.getHome().equals(t)) {
			return g.getHomeStatsOff();
		}
		if (g.getAway().equals(t)) {
			return g.getAwayStatsOff();
		}
		throw new NotFoundException("Team not found.");
	}

	/**
	 * Collects defensive stats for a team in a certain Game-object (regardless
	 * of whether or not it is the home/away team).
	 * 
	 * @param t
	 *            Team-object to collect stats for.
	 * @param g
	 *            Game-object to search in.
	 * @return defensive stats of Team t in Game g.
	 * @throws NotFoundException
	 *             in case Team t did not play in Game g.
	 */
	private static Defense collectTeamDefense(Team t, Game g) throws NotFoundException {
		if (g.getHome().equals(t)) {
			return g.getHomeStatsDef();
		}
		if (g.getAway().equals(t)) {
			return g.getAwayStatsDef();
		}
		throw new NotFoundException("Team not found.");
	}

	/**
	 * Collects psychological stats for a team in a certain Game-object
	 * (regardless of whether or not it is the home/away team).
	 * 
	 * @param t
	 *            Team-object to collect stats for.
	 * @param g
	 *            Game-object to search in.
	 * @return psychological stats of Team t in Game g.
	 * @throws NotFoundException
	 *             in case Team t did not play in Game g.
	 */
	private static Psych collectTeamPsych(Team t, Game g) throws NotFoundException {
		if (g.getHome().equals(t)) {
			return g.getHomeStatsPsych();
		}
		if (g.getAway().equals(t)) {
			return g.getAwayStatsPsych();
		}
		throw new NotFoundException("Team not found.");
	}

	/**
	 * Collects offensive stats for the adversary team in a certain Game-object
	 * (regardless of whether or not it is the home/away team), provided a
	 * Team-object t.
	 * 
	 * @param t
	 *            Team-object to collect adversary stats for.
	 * @param g
	 *            Game-object to search in.
	 * @return offensive stats of the Team t adversary in Game g.
	 * @throws NotFoundException
	 *             in case Team t did not play in Game g.
	 */
	private static Offense collectAdvOffense(Team t, Game g) throws NotFoundException {
		if (g.getHome().equals(t)) {
			return g.getAwayStatsOff();
		}
		if (g.getAway().equals(t)) {
			return g.getHomeStatsOff();
		}
		throw new NotFoundException("Team not found.");
	}

	/**
	 * Collects defensive stats for the adversary team in a certain Game-object
	 * (regardless of whether or not it is the home/away team), provided a
	 * Team-object t.
	 * 
	 * @param t
	 *            Team-object to collect adversary stats for.
	 * @param g
	 *            Game-object to search in.
	 * @return defensive stats of the Team t adversary in Game g.
	 * @throws NotFoundException
	 *             in case Team t did not play in Game g.
	 */
	private static Defense collectAdvDefense(Team t, Game g) throws NotFoundException {
		if (g.getHome().equals(t)) {
			return g.getAwayStatsDef();
		}
		if (g.getAway().equals(t)) {
			return g.getHomeStatsDef();
		}
		throw new NotFoundException("Team not found.");
	}

	/**
	 * Collects psychological stats for the adversary team in a certain
	 * Game-object (regardless of whether or not it is the home/away team),
	 * provided a Team-object t.
	 * 
	 * @param t
	 *            Team-object to collect adversary stats for.
	 * @param g
	 *            Game-object to search in.
	 * @return psychological stats of the Team t adversary in Game g.
	 * @throws NotFoundException
	 *             in case Team t did not play in Game g.
	 */
	private static Psych collectAdvPsych(Team t, Game g) throws NotFoundException {
		if (g.getHome().equals(t)) {
			return g.getAwayStatsPsych();
		}
		if (g.getAway().equals(t)) {
			return g.getHomeStatsPsych();
		}
		throw new NotFoundException("Team not found.");
	}

	/**
	 * Computes (weighted) offensive stat averages.
	 * 
	 * @param offenses
	 *            list of Offense data containers.
	 * @param weights
	 *            repsective weights given to stats in a particular
	 *            Offense-object;
	 * @return array of offensive stat averages.
	 * @throws CollectorException
	 *             in case the number of elements do not match.
	 */
	private static double[] offenseAverage(ArrayList<Offense> offenses, double[] weights) throws CollectorException {
		if (offenses.size() == 0)
			throw new CollectorException("No offensive stats gathered.");
		if (offenses.size() != weights.length)
			throw new CollectorException("No averages computed.");
		double[] averages = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < offenses.size(); i++) {
			Offense o = offenses.get(i);
			double w = weights[i];
			averages[0] += (double) w * o.getGoals();
			averages[1] += (double) w * (o.getPossession() / 100.0);
			averages[2] += (double) w * o.getSuccessPasses();
			if (o.getTotalPasses() == 0)
				o.setTotalPasses((int) Math.round(o.getSuccessPasses() * (4. / 3.)));
			averages[3] += (double) w * o.getTotalPasses();
			averages[4] += (double) w * (averages[2] / averages[3]);
			averages[5] += (double) w * o.getAerialsWon();
			averages[6] += (double) w * o.getShots();
			averages[7] += (double) w * o.getShotsOnTarget();
			averages[8] += (double) w * o.getDribbles();
			averages[9] += (double) w * o.getFouled();
			averages[10] += (double) w * o.getOffSides();
		}
		return averages;
	}

	/**
	 * Computes (weighted) defensive stat averages.
	 * 
	 * @param defenses
	 *            list of Defense data containers.
	 * @param weights
	 *            repsective weights given to stats in a particular
	 *            Defense-object;
	 * @return array of defensive stat averages.
	 * @throws CollectorException
	 *             in case the number of elements do not match.
	 */
	private static double[] defenseAverage(ArrayList<Defense> defenses, double[] weights) throws CollectorException {
		if (defenses.size() == 0)
			throw new CollectorException("No defensive stats gathered.");
		if (defenses.size() != weights.length)
			throw new CollectorException("No averages computed.");
		double[] averages = { 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < defenses.size(); i++) {
			Defense d = defenses.get(i);
			double w = weights[i];
			averages[0] += (double) w * d.getTackles();
			averages[1] += (double) w * d.getFouls();
			averages[2] += (double) w * d.getOffsidesAgainst();
			double cs = (d.isCleanSheet()) ? 1.0 : 0.0;
			averages[3] += (double) w * cs;
			averages[4] += (double) w * d.getYellowCards();
			averages[5] += (double) w * d.getRedCards();
			averages[6] += (double) w * d.getSubstitutions();
		}
		return averages;
	}

	/**
	 * Computes (weighted) psychological stat averages.
	 * 
	 * @param psychs
	 *            list of psychological data containers.
	 * @param weights
	 *            repsective weights given to stats in a particular
	 *            Psych-object;
	 * @return array of psychological stat averages.
	 * @throws CollectorException
	 *             in case the number of elements do not match.
	 */
	private static double[] psychAverage(ArrayList<Psych> psychs, ArrayList<Game> games, double[] weights)
			throws CollectorException {
		if (psychs.size() == 0)
			throw new CollectorException("No psychological stats gathered.");
		if (psychs.size() != games.size())
			throw new CollectorException("No averages computed.");
		if (psychs.size() != weights.length)
			throw new CollectorException("No averages computed.");
		double[] averages = { 0., 0., 0., 0., 0. };
		ArrayList<Game> homeGames = new ArrayList<Game>();
		ArrayList<Game> awayGames = new ArrayList<Game>();
		ArrayList<Game> overGames = new ArrayList<Game>();
		ArrayList<Game> underGames = new ArrayList<Game>();
		int homeGamesWon = 0;
		int homeGamesDrew = 0;
		int awayGamesWon = 0;
		int awayGamesDrew = 0;
		int overGamesWon = 0;
		int overGamesDrew = 0;
		int underGamesWon = 0;
		int underGamesDrew = 0;
		for (int i = 0; i < psychs.size(); i++) {
			Psych p = psychs.get(i);
			Game g = games.get(i);
			Psych gp_home = g.getHomeStatsPsych();
			Psych gp_away = g.getAwayStatsPsych();
			averages[4] += (double) weights[i] * psychs.get(i).getImportance();
			if (p.equals(gp_home)) {
				homeGames.add(g);
				if (g.won(g.getHome())) {
					homeGamesWon++;
				}
				if (g.drew(g.getHome())) {
					homeGamesDrew++;
				}
			}
			if (p.equals(gp_away)) {
				awayGames.add(g);
				if (g.won(g.getAway())) {
					awayGamesWon++;
				}
				if (g.drew(g.getAway())) {
					awayGamesDrew++;
				}
			}
			try {
				Competition c = g.getCompetition().pseudoCompetition(g.getGameDay());
				if (p.equals(g.getHomeStatsPsych())) {
					if (c.getRank(g.getHome()) >= c.getRank(g.getAway())) {
						underGames.add(g);
						if (g.won(g.getHome())) {
							underGamesWon++;
						}
						if (g.drew(g.getHome())) {
							underGamesDrew++;
						}
					} else {
						overGames.add(g);
						if (g.won(g.getHome())) {
							overGamesWon++;
						}
						if (g.drew(g.getHome())) {
							overGamesDrew++;
						}
					}
				}
				if (p.equals(g.getAwayStatsPsych())) {
					if (c.getRank(g.getAway()) >= c.getRank(g.getHome())) {
						underGames.add(g);
						if (g.won(g.getAway())) {
							underGamesWon++;
						}
						if (g.drew(g.getAway())) {
							underGamesDrew++;
						}
					} else {
						overGames.add(g);
						if (g.won(g.getAway())) {
							overGamesWon++;
						}
						if (g.drew(g.getAway())) {
							overGamesDrew++;
						}
					}
				}
			} catch (CollectorException e) {
				// Logger capacity
			}
		}
		double perc;
		if (homeGames.size() > 0) {
			perc = (3*homeGamesWon+homeGamesDrew)/(3*homeGames.size());
			averages[0] = perc;
		}
		if (awayGames.size() > 0) {
			perc = (3*awayGamesWon+awayGamesDrew)/(3*awayGames.size());
			averages[1] = perc;
		}
		if (overGames.size() > 0) {
			perc = (3*overGamesWon+overGamesDrew)/(3*overGames.size());
			averages[2] = perc;
		}
		if (underGames.size() > 0) {
			perc = (3*underGamesWon+underGamesDrew)/(3*underGames.size());
			averages[3] = perc;
		}
		return averages;
	}

	/**
	 * Computes team averages stats.
	 * 
	 * @param t
	 *            Team-Object to compute stats for.
	 * @param games
	 *            list of Game-objects to take into account.
	 * @param weights
	 *            weight coefficients of the respective Game-objects.
	 * @return Average-object containing (data container) average stats.
	 * @throws CollectorException
	 *             in case numbers or amount do not match.
	 * @throws NotFoundException
	 *             in case a Game-object is found that does not incorporate Team
	 *             t.
	 */
	public static Average teamAverage(Team t, ArrayList<Game> games, double[] weights)
			throws CollectorException, NotFoundException {
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		if (games.size() != weights.length)
			throw new CollectorException("No averages computed.");
		ArrayList<Offense> offenses = new ArrayList<Offense>();
		ArrayList<Defense> defenses = new ArrayList<Defense>();
		ArrayList<Psych> psychs = new ArrayList<Psych>();
		try {
			for (Game g : games) {
				offenses.add(collectTeamOffense(t, g));
				defenses.add(collectTeamDefense(t, g));
				psychs.add(collectTeamPsych(t, g));
			}
		} catch (Exception e) {
			throw new NotFoundException("Team is somewhere not found.");
		}
		double[] o = offenseAverage(offenses, weights);
		double[] d = defenseAverage(defenses, weights);
		double[] p = psychAverage(psychs, games, weights);
		return new Average(o, d, p);
	}

	/**
	 * Computes adversary team averages stats.
	 * 
	 * @param t
	 *            Team-Object to compute adversary stats for.
	 * @param games
	 *            list of Game-objects to take into account.
	 * @param weights
	 *            weight coefficients of the respective Game-objects.
	 * @return Average-object containing (data container) average stats.
	 * @throws CollectorException
	 *             in case numbers or amount do not match.
	 * @throws NotFoundException
	 *             in case a Game-object is found that does not incorporate Team
	 *             t.
	 */
	public static Average advAverage(Team t, ArrayList<Game> games, double[] weights)
			throws CollectorException, NotFoundException {
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		if (games.size() != weights.length)
			throw new CollectorException("No averages computed.");
		ArrayList<Offense> offenses = new ArrayList<Offense>();
		ArrayList<Defense> defenses = new ArrayList<Defense>();
		ArrayList<Psych> psychs = new ArrayList<Psych>();
		try {
			for (Game g : games) {
				offenses.add(collectAdvOffense(t, g));
				defenses.add(collectAdvDefense(t, g));
				psychs.add(collectAdvPsych(t, g));
			}
		} catch (NotFoundException e) {
			throw new NotFoundException("Team is somewhere not found.");
		}
		double[] o = offenseAverage(offenses, weights);
		double[] d = defenseAverage(defenses, weights);
		double[] p = psychAverage(psychs, games, weights);
		return new Average(o, d, p);
	}

	/**
	 * Computes (weighted) offensive stat averages.
	 * 
	 * @param offenses
	 *            list of Offense data containers.
	 * @param weights
	 *            repsective weights given to stats in a particular
	 *            Offense-object;
	 * @return array of offensive stat averages.
	 * @throws CollectorException
	 *             in case the number of elements do not match.
	 */
	private static double[] offenseDeviation(ArrayList<Offense> offenses, double[] weights, Average a)
			throws CollectorException {
		if (offenses.size() == 0)
			throw new CollectorException("No offensive stats gathered.");
		if (offenses.size() != weights.length)
			throw new CollectorException("No deviations computed.");
		double[] deviations = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < offenses.size(); i++) {
			Offense o = offenses.get(i);
			double w = weights[i];
			deviations[0] += (double) w * Math.pow(o.getGoals() - a.getAvGoals(), 2);
			deviations[1] += (double) w * Math.pow((double) o.getPossession() / 100.0 - a.getAvPossession(), 2);
			deviations[2] += (double) w * Math.pow(o.getSuccessPasses() - a.getAvPassSuccess(), 2);
			if (o.getTotalPasses() == 0)
				o.setTotalPasses((int) Math.round(o.getSuccessPasses() * (4. / 3.)));
			deviations[3] += (double) w * Math.pow(o.getTotalPasses() - a.getAvTotalPasses(), 2);
			if (deviations[3] == 0) {
				deviations[4] = 0;
			} else {
				deviations[4] += (double) w * (deviations[2] / deviations[3]);
			}
			deviations[5] += (double) w * Math.pow(o.getAerialsWon() - a.getAvAerialsWon(), 2);
			deviations[6] += (double) w * Math.pow(o.getShots() - a.getAvShots(), 2);
			deviations[7] += (double) w * Math.pow(o.getShotsOnTarget() - a.getAvShotsOnTarget(), 2);
			deviations[8] += (double) w * Math.pow(o.getDribbles() - a.getAvDribbles(), 2);
			deviations[9] += (double) w * Math.pow(o.getFouled() - a.getAvFouled(), 2);
			deviations[10] += (double) w * Math.pow(o.getOffSides() - a.getAvOffSides(), 2);
		}
		for (int i = 0; i < deviations.length; i++) {
			deviations[i] = Math.sqrt(deviations[i]);
		}
		return deviations;
	}

	/**
	 * Computes (weighted) defensive stat deviations.
	 * 
	 * @param defenses
	 *            list of Defense data containers.
	 * @param weights
	 *            repsective weights given to stats in a particular
	 *            Defense-object;
	 * @return array of defensive stat deviations.
	 * @throws CollectorException
	 *             in case the number of elements do not match.
	 */
	private static double[] defenseDeviation(ArrayList<Defense> defenses, double[] weights, Average a)
			throws CollectorException {
		if (defenses.size() == 0)
			throw new CollectorException("No defensive stats gathered.");
		if (defenses.size() != weights.length)
			throw new CollectorException("No deviations computed.");
		double[] deviations = { 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < defenses.size(); i++) {
			Defense d = defenses.get(i);
			double w = weights[i];
			deviations[0] += (double) w * Math.pow(d.getTackles() - a.getAvTackles(), 2);
			deviations[1] += (double) w * Math.pow(d.getFouls() - a.getAvFouls(), 2);
			deviations[2] += (double) w * Math.pow(d.getOffsidesAgainst() - a.getAvOffsidesAgainst(), 2);
			double cs = (d.isCleanSheet()) ? 1.0 : 0.0;
			deviations[3] += (double) w * Math.pow(cs - a.getAvCleanSheets(), 2);
			deviations[4] += (double) w * Math.pow(d.getYellowCards() - a.getAvYellowCards(), 2);
			deviations[5] += (double) w * Math.pow(d.getRedCards() - a.getAvRedCards(), 2);
			deviations[6] += (double) w * Math.pow(d.getSubstitutions() - a.getAvSubstitutions(), 2);
		}
		for (int i = 0; i < deviations.length; i++) {
			deviations[i] = Math.sqrt(deviations[i]);
		}
		return deviations;
	}

	/**
	 * Computes (weighted) psychological stat deviations.
	 * 
	 * @param psychs
	 *            list of psychological data containers.
	 * @param weights
	 *            repsective weights given to stats in a particular
	 *            Psych-object;
	 * @return array of psychological stat deviations.
	 * @throws CollectorException
	 *             in case the number of elements do not match.
	 */
	private static double[] psychDeviation(ArrayList<Psych> psychs, double[] weights, Average a)
			throws CollectorException {
		if (psychs.size() == 0)
			throw new CollectorException("No psychological stats gathered.");
		if (psychs.size() != weights.length)
			throw new CollectorException("No deviations computed.");
		double[] deviations = { 0. };
		for (int i = 0; i < psychs.size(); i++) {
			double w = weights[i];
			deviations[0] += (double) w * Math.pow(psychs.get(i).getImportance() - a.getImportance(), 2);
		}
		for (int i = 0; i < deviations.length; i++) {
			deviations[i] = Math.sqrt(deviations[i]);
		}
		return deviations;
	}

	/**
	 * Computes team stat deviation.
	 * 
	 * @param t
	 *            Team-Object to compute deviations for.
	 * @param games
	 *            list of Game-objects to take into account.
	 * @param weights
	 *            weight coefficients of the respective Game-objects.
	 * @return Deviation-object containing (data container) stat deviations.
	 * @throws CollectorException
	 *             in case numbers or amount do not match.
	 * @throws NotFoundException
	 *             in case a Game-object is found that does not incorporate Team
	 *             t.
	 */
	public static Deviation teamDeviation(Team t, ArrayList<Game> games, double[] weights)
			throws CollectorException, NotFoundException {
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		if (games.size() != weights.length)
			throw new CollectorException("No averages computed.");
		Average a = teamAverage(t, games, weights);
		ArrayList<Offense> offenses = new ArrayList<Offense>();
		ArrayList<Defense> defenses = new ArrayList<Defense>();
		ArrayList<Psych> psychs = new ArrayList<Psych>();
		try {
			for (Game g : games) {
				offenses.add(collectTeamOffense(t, g));
				defenses.add(collectTeamDefense(t, g));
				psychs.add(collectTeamPsych(t, g));
			}
		} catch (NotFoundException e) {
			throw new NotFoundException("Team is somewhere not found.");
		}
		double[] o = offenseDeviation(offenses, weights, a);
		double[] d = defenseDeviation(defenses, weights, a);
		double[] p = psychDeviation(psychs, weights, a);
		return new Deviation(o, d, p);
	}

	/**
	 * Computes adversary team stat deviation.
	 * 
	 * @param t
	 *            Team-Object to compute adversary deviations for.
	 * @param games
	 *            list of Game-objects to take into account.
	 * @param weights
	 *            weight coefficients of the respective Game-objects.
	 * @return Deviation-object containing (data container) stat deviations.
	 * @throws CollectorException
	 *             in case numbers or amount do not match.
	 * @throws NotFoundException
	 *             in case a Game-object is found that does not incorporate Team
	 *             t.
	 */
	public static Deviation advDeviation(Team t, ArrayList<Game> games, double[] weights)
			throws CollectorException, NotFoundException {
		if (games.size() == 0)
			throw new CollectorException("No games gathered.");
		if (games.size() != weights.length)
			throw new CollectorException("No averages computed.");
		Average a = advAverage(t, games, weights);
		ArrayList<Offense> offenses = new ArrayList<Offense>();
		ArrayList<Defense> defenses = new ArrayList<Defense>();
		ArrayList<Psych> psychs = new ArrayList<Psych>();
		try {
			for (Game g : games) {
				offenses.add(collectAdvOffense(t, g));
				defenses.add(collectAdvDefense(t, g));
				psychs.add(collectAdvPsych(t, g));
			}
		} catch (NotFoundException e) {
			throw new NotFoundException("Team is somewhere not found.");
		}
		double[] o = offenseDeviation(offenses, weights, a);
		double[] d = defenseDeviation(defenses, weights, a);
		double[] p = psychDeviation(psychs, weights, a);
		return new Deviation(o, d, p);
	}

}