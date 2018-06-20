package data.core.structure;

import java.util.List;

/**
 * Class Rank. Unit for making up a competition ranking.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Rank {

	/**
	 * Class attributes.
	 */
	private int rank;
	private Team team;
	private int gamesPlayed;
	private int won;
	private int drew;
	private int lost;
	private int points;
	private int goalsScored;
	private int goalsAgainst;

	/**
	 * Copy constructor.
	 * 
	 * @param r
	 *            Rank-object to copy.
	 */
	public Rank(Rank r) {
		this.rank = r.rank;
		this.team = r.team;
		this.gamesPlayed = r.gamesPlayed;
		this.won = r.won;
		this.drew = r.drew;
		this.lost = r.lost;
		this.points = r.points;
		this.goalsScored = r.goalsScored;
		this.goalsAgainst = r.goalsAgainst;
	}

	/**
	 * General constructor.
	 * 
	 * @param team
	 *            Team-object corresponding to a the rank.
	 */
	public Rank(Team team) {
		this.team = team;
		this.process();
	}

	/**
	 * Getter rank.
	 * 
	 * @return rank number.
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Getter team.
	 * 
	 * @return team corresponding to Rank-object.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Games played getter.
	 * 
	 * @return number of games played.
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * Won getter.
	 * 
	 * @return number of games won.
	 */
	public int getWon() {
		return won;
	}

	/**
	 * Drew getter.
	 * 
	 * @return number of games drew.
	 */
	public int getDrew() {
		return drew;
	}

	/**
	 * Lost getter.
	 * 
	 * @return number of games lost.
	 */
	public int getLost() {
		return lost;
	}

	/**
	 * Points getter.
	 * 
	 * @return number of points.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Goals scored getter.
	 * 
	 * @return number of goals scored.
	 */
	public int getGoalsScored() {
		return goalsScored;
	}

	/**
	 * Goals against getter.
	 * 
	 * @return number of goals scored against.
	 */
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * Processes main game number from Team-object.
	 */
	private void process() {
		int nGamesPlayed = 0;
		int nWon = 0;
		int nDrew = 0;
		int nLost = 0;
		int nPoints = 0;
		int nGoalsScored = 0;
		int nGoalsAgainst = 0;
		List<Game> games = this.team.getGames();
		for (Game game : games) {
			if (game.isPlayed()) {
				nGamesPlayed++;
				if (game.getHome().equals(team)) {
					nGoalsScored += Math.min(game.getHomeScore(), 5);
					nGoalsAgainst += Math.min(game.getAwayScore(), 5);
				}
				if (game.getAway().equals(team)) {
					nGoalsScored += Math.min(game.getAwayScore(), 5);
					nGoalsAgainst += Math.min(game.getHomeScore(), 5);
				}
				if (game.won(team)) {
					nWon++;
				}
				if (game.drew(team)) {
					nDrew++;
				}
				if (!game.won(team) && !game.drew(team)) {
					nLost++;
				}

			}
		}
		nPoints = 3 * nWon + nDrew;
		this.gamesPlayed = nGamesPlayed;
		this.won = nWon;
		this.drew = nDrew;
		this.lost = nLost;
		this.points = nPoints;
		this.goalsScored = nGoalsScored;
		this.goalsAgainst = nGoalsAgainst;
	}

	/**
	 * Assigns correct ranking number to Rank-object.
	 */
	public void assignRank(List<Rank> ranking) {
		rank = 0;
		for (int i = 0; i < ranking.size(); i++) {
			if (this.equals(ranking.get(i))) {
				this.rank = i + 1;
			}
		}

	}

	/**
	 * Compares two Rank-Objects.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Rank) {
			Rank that = (Rank) obj;
			return this.team == that.team && this.gamesPlayed == that.gamesPlayed && this.won == that.won
					&& this.drew == that.drew && this.lost == that.lost && this.goalsAgainst == that.goalsAgainst
					&& this.goalsScored == that.goalsScored;
		}
		return false;
	}

}