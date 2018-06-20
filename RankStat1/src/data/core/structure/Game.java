package data.core.structure;

import java.util.ArrayList;
import data.core.stats.Defense;
import data.core.stats.Offense;
import data.core.stats.Psych;

/**
 * Class game for processing soccer game entities.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class Game {

	/**
	 * Class attributes.
	 */
	// General
	private String id;
	private Team home;
	private Team away;
	private GameDay gameDay;
	private Competition competition;
	// Game results
	private boolean played;
	private int homeScore;
	private int awayScore;
	// Home team
	private Offense homeStatsOff;
	private Defense homeStatsDef;
	private Psych homeStatsPsych;
	// Away team
	private Offense awayStatsOff;
	private Defense awayStatsDef;
	private Psych awayStatsPsych;

	/**
	 * Copy constructor.
	 * 
	 * @param g
	 *            Game-object to copy.
	 */
	public Game(Game g) {
		this.id = g.id;
		this.home = g.home;
		this.away = g.away;
		this.gameDay = g.gameDay;
		this.competition = g.competition;
		this.played = g.played;
		if (this.played) {
			this.homeScore = g.homeScore;
			this.awayScore = g.awayScore;
			this.homeStatsOff = g.homeStatsOff;
			this.homeStatsDef = g.homeStatsDef;
			this.homeStatsPsych = g.homeStatsPsych;
			this.awayStatsOff = g.awayStatsOff;
			this.awayStatsDef = g.awayStatsDef;
			this.awayStatsPsych = g.awayStatsPsych;
		} else {
			this.homeScore = 0;
			this.awayScore = 0;
			this.homeStatsOff = null;
			this.homeStatsDef = null;
			this.homeStatsPsych = null;
			this.awayStatsOff = null;
			this.awayStatsDef = null;
			this.awayStatsPsych = null;
			this.updateTeam(this.home);
			this.updateTeam(this.away);
			this.updateCompetition();
		}
	}

	/**
	 * Restricted constructor. No advanced stats are incorporated.
	 * 
	 * @param home
	 *            home team.
	 * @param away
	 *            away team.
	 * @param gameDay
	 *            game date.
	 * @param playedindicator
	 *            to determine if a game has been played yet.
	 * @param homeScore
	 *            score home team.
	 * @param awayScore
	 *            score away team.
	 */
	public Game(String id, Team home, Team away, GameDay gameDay, Competition competition, boolean played,
			int homeScore, int awayScore) {
		this.id = id;
		this.home = home;
		this.away = away;
		this.gameDay = gameDay;
		this.competition = competition;
		this.played = played;
		if (this.played) {
			this.homeScore = homeScore;
			this.awayScore = awayScore;
		} else {
			this.homeScore = 0;
			this.awayScore = 0;
		}
		this.homeStatsOff = null;
		this.homeStatsDef = null;
		this.homeStatsPsych = null;
		this.awayStatsOff = null;
		this.awayStatsDef = null;
		this.awayStatsPsych = null;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * General constructor.
	 * 
	 * @param home
	 *            home team.
	 * @param away
	 *            away team.
	 * @param gameDay
	 *            game date.
	 * @param playedindicator
	 *            to determine if a game has been played yet.
	 * @param homeScore
	 *            score home team.
	 * @param awayScore
	 *            score away team.
	 * @param homeStatsOff
	 *            home team offensive stats.
	 * @param homeStatsDef
	 *            home team defensive stats.
	 * @param homeStatsPsych
	 *            home team psychological stats.
	 * @param awayStatsOff
	 *            away team offensive stats.
	 * @param awayStatsDef
	 *            away team defensive stats.
	 * @param awayStatsPsych
	 *            away team psychological stats.
	 */
	public Game(String id, Team home, Team away, GameDay gameDay, Competition competition, boolean played,
			int homeScore, int awayScore, Offense homeStatsOff, Defense homeStatsDef, Psych homeStatsPsych,
			Offense awayStatsOff, Defense awayStatsDef, Psych awayStatsPsych) {
		this.id = id;
		this.home = home;
		this.away = away;
		this.gameDay = gameDay;
		this.competition = competition;
		this.played = played;
		if (this.played) {
			this.homeScore = homeScore;
			this.awayScore = awayScore;
			this.homeStatsOff = homeStatsOff;
			this.homeStatsDef = homeStatsDef;
			this.homeStatsPsych = homeStatsPsych;
			this.awayStatsOff = awayStatsOff;
			this.awayStatsDef = awayStatsDef;
			this.awayStatsPsych = awayStatsPsych;
		} else {
			this.homeScore = 0;
			this.awayScore = 0;
			this.homeStatsOff = null;
			this.homeStatsDef = null;
			this.homeStatsPsych = null;
			this.awayStatsOff = null;
			this.awayStatsDef = null;
			this.awayStatsPsych = null;
		}
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter ID
	 * 
	 * @return ID of the Game-object.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter ID
	 * 
	 * @param id
	 *            new ID of the game.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter home team.
	 * 
	 * @return home team of the game.
	 */
	public Team getHome() {
		return home;
	}

	/**
	 * Getter away team.
	 * 
	 * @return away team of the game.
	 */
	public Team getAway() {
		return away;
	}

	/**
	 * Getter game day.
	 * 
	 * @return game day.
	 */
	public GameDay getGameDay() {
		return gameDay;
	}

	/**
	 * Getter competition.
	 * 
	 * @return competition in which the game is played.
	 */
	public Competition getCompetition() {
		return competition;
	}

	/**
	 * Setter competition.
	 * 
	 * @param competition
	 *            new competition to play the game in.
	 */
	public void setCompetition(Competition competition) {
		this.competition = competition;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter has-been-played indicator.
	 * 
	 * @return has-been-played indicator.
	 */
	public boolean isPlayed() {
		return played;
	}

	/**
	 * Setter has-been-played indicator.
	 * 
	 * @param played
	 *            new has-been-played indicator.
	 */
	public void setPlayed(boolean played) {
		this.played = played;
	}

	/**
	 * Getter home team score.
	 * 
	 * @return home team score.
	 */
	public int getHomeScore() {
		return homeScore;
	}

	/**
	 * Setter home team score.
	 * 
	 * @param homeScore
	 *            new home team score.
	 */
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	/**
	 * Getter away team score.
	 * 
	 * @return away team score.
	 */
	public int getAwayScore() {
		return awayScore;
	}

	/**
	 * Setter away team score.
	 * 
	 * @param awayScore
	 *            new away team score.
	 */
	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}

	/**
	 * Getter home team offensive stats.
	 * 
	 * @return home team offensive stats.
	 */
	public Offense getHomeStatsOff() {
		return homeStatsOff;
	}

	/**
	 * Setter home team offensive stats.
	 * 
	 * @param homeStatsOff
	 *            new home team offensive stats.
	 */
	public void setHomeStatsOff(Offense homeStatsOff) {
		this.homeStatsOff = homeStatsOff;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter home team defensive stats.
	 * 
	 * @return home team defensive stats.
	 */
	public Defense getHomeStatsDef() {
		return homeStatsDef;
	}

	/**
	 * Setter home team defensive stats.
	 * 
	 * @param homeStatsDef
	 *            new home team defensive stats.
	 */
	public void setHomeStatsDef(Defense homeStatsDef) {
		this.homeStatsDef = homeStatsDef;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter home team psychological stats.
	 * 
	 * @return home team psychological stats.
	 */
	public Psych getHomeStatsPsych() {
		return homeStatsPsych;
	}

	/**
	 * Setter home team psychological stats.
	 * 
	 * @param homeStatsPsych
	 *            new home team psychological stats.
	 */
	public void setHomeStatsPsych(Psych homeStatsPsych) {
		this.homeStatsPsych = homeStatsPsych;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter away team offensive stats.
	 * 
	 * @return away team offensive stats.
	 */
	public Offense getAwayStatsOff() {
		return awayStatsOff;
	}

	/**
	 * Setter away team offensive stats.
	 * 
	 * @param awaysStatsOff
	 *            new away team offensive stats.
	 */
	public void setAwayStatsOff(Offense awayStatsOff) {
		this.awayStatsOff = awayStatsOff;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter away team defensive stats.
	 * 
	 * @return away team defensive stats.
	 */
	public Defense getAwayStatsDef() {
		return awayStatsDef;
	}

	/**
	 * Setter away team defensive stats.
	 * 
	 * @param awayStatsDef
	 *            new away team defensive stats.
	 */
	public void setAwayStatsDef(Defense awayStatsDef) {
		this.awayStatsDef = awayStatsDef;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Getter away team psychological stats.
	 * 
	 * @return away team psychological stats.
	 */
	public Psych getAwayStatsPsych() {
		return awayStatsPsych;
	}

	/**
	 * Setter away team psychological stats.
	 * 
	 * @param awayStatsPsych
	 *            new away team psychological stats.
	 */
	public void setAwayStatsPsych(Psych awayStatsPsych) {
		this.awayStatsPsych = awayStatsPsych;
		this.updateTeam(this.home);
		this.updateTeam(this.away);
		this.updateCompetition();
	}

	/**
	 * Updates team with respect to this game.
	 * 
	 * @param t
	 *            Team-object to update.
	 */
	private void updateTeam(Team t) {
		ArrayList<Game> temp = t.getGames();
		for (int i = temp.size() - 1; i >= 0; i--) {
			if (temp.get(i).equals(this)) {
				temp.add(i, this);
				temp.remove(i + 1);
				t.setGames(temp);
				return;
			}
		}
		temp.add(this);
		t.setGames(temp);
		t.getCompetition().mineGameDays(t.getGames());
		t.getCompetition().orderRanking();
	}

	/**
	 * Updates competition-object.
	 */
	private void updateCompetition() {
		ArrayList<Game> temp = this.competition.getGames();
		for (int i = temp.size() - 1; i >= 0; i--) {
			if (temp.get(i).equals(this)) {
				temp.add(i, this);
				temp.remove(i + 1);
				this.competition.setGames(temp);
				return;
			}
		}
		temp.add(this);
		this.competition.setGames(temp);
		this.competition.mineGameDays(this.competition.getGames());
	}

	/**
	 * Determines if a given Team has won the game.
	 * 
	 * @param t
	 *            Team-object to check for.
	 */
	public boolean won(Team t) {
		if (t.equals(home)) {
			return this.homeScore > this.awayScore;
		}
		if (t.equals(away)) {
			return this.homeScore < this.awayScore;
		}
		return false;
	}

	/**
	 * Determines if a given Team drew the game.
	 * 
	 * @param t
	 *            Team-object to check for.
	 */
	public boolean drew(Team t) {
		if (t.equals(home) || t.equals(away)) {
			return this.homeScore == this.awayScore;
		}
		return false;
	}

	/**
	 * Psychologically evaluates a game's importance.
	 * 
	 * @return evaluation indicator.
	 */
	public boolean predictable() {
		return this.homeStatsPsych.getImportance() > 0.5 && this.awayStatsPsych.getImportance() > 0.5;
	}

	/**
	 * Determines whether or not two Game-objects embody the same soccer game.
	 * 
	 * @param obj
	 *            Object to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Game) {
			Game that = (Game) obj;
			return this.home.equals(that.home) && this.away.equals(that.away) && this.gameDay.equals(that.gameDay);
		}
		return false;
	}

}