package simulation.analyze.selection;

import java.util.ArrayList;
import data.core.structure.Competition;
import data.core.structure.Game;
import data.core.structure.GameDay;
import simulation.analyze.evaluation.Evaluator;

/**
 * Selector Class for Portfolio-Object creation.
 * 
 * @author andreastheys
 *
 */
public class Selector {

	/**
	 * Select all Game-Objects within a certain time period.
	 * 
	 * @param games
	 *            initial list of Game-Objects.
	 * @param begin
	 *            begin date.
	 * @param end
	 *            end date.
	 * @return list of selected Game-Objects.
	 */
	private static ArrayList<Game> select(ArrayList<Game> games, GameDay begin, GameDay end) {
		ArrayList<Game> sGames = new ArrayList<Game>();
		for (Game g : games) {
			GameDay date = g.getGameDay();
			if (date.after(begin) && (date.before(end) || date.equals(end))) {
				sGames.add(g);
			}
		}
		return sGames;
	}

	/**
	 * Select all Game-Objects within a certain time period.
	 * 
	 * @param competitions
	 *            initial list of Competition-Objects.
	 * @param begin
	 *            begin date.
	 * @param end
	 *            end date.
	 * @return list of selected Game-Objects.
	 */
	private static ArrayList<Game> selectGames(ArrayList<Competition> competitions, GameDay begin, GameDay end) {
		ArrayList<Game> sGames = new ArrayList<Game>();
		for (Competition c : competitions) {
			ArrayList<Game> games = select(c.getGames(), begin, end);
			sGames.addAll(games);
		}
		return sGames;
	}

	/**
	 * Determines latest competition game day.
	 * 
	 * @param c
	 *            relevant Competition-Object.
	 * @return corresponding GameDay-Object.
	 */
	private static GameDay max(Competition c) {
		GameDay date = new GameDay(1, 1, 1900);
		for (GameDay gd : c.getGameDays()) {
			if (gd.after(date)) {
				date = gd;
			}
		}
		return date;
	}

	/**
	 * Determines latest competition game day.
	 * 
	 * @param competitions
	 *            relevant list of Competition-Objects.
	 * @return corresponding GameDay-Object.
	 */
	private static GameDay max(ArrayList<Competition> competitions) {
		GameDay date = new GameDay(1, 1, 1900);
		for (Competition c : competitions) {
			GameDay gd = max(c);
			if (gd.after(date)) {
				date = gd;
			}
		}
		return date;
	}

	/**
	 * Arranges nested Evaluator-Object list on a weekly basis.
	 * 
	 * @param competitions
	 *            relevant list of Competition-Objects.
	 * @param begin
	 *            begin date.
	 * @note begin date should usually be on a Wednesday.
	 * @return nested Evaluator-Object list
	 */
	public static ArrayList<ArrayList<Evaluator>> SELECT(ArrayList<Competition> competitions, GameDay begin, double importance, String type) {
		ArrayList<ArrayList<Evaluator>> evaluators = new ArrayList<ArrayList<Evaluator>>();
		GameDay latest = max(competitions);
		while (begin.before(latest)) {
			GameDay end = begin.plus(7);
			ArrayList<Evaluator> temp = new ArrayList<Evaluator>();
			ArrayList<Game> games = selectGames(competitions, begin, end);
			if (games.size() > 0) {
				for(Game g: games){
					temp.add(new Evaluator(g, importance));
				}
				evaluators.add(temp);
			}
			begin = end;
		}
		return evaluators;
	}

}