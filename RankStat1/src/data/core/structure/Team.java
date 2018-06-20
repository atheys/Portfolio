package data.core.structure;

import java.util.ArrayList;

/**
 * Team class. Handles soccer team specific stats and features.
 * 
 * @author Andreas Theys
 * @version 3.0
 */
public class Team {

	/**
	 * Class attributes.
	 */
	private String name;
	private Competition competition;
	private ArrayList<Game> games;

	/**
	 * Copy constructor.
	 * 
	 * @param t
	 *            Team-object to copy.
	 */
	public Team(Team t) {
		this.name = t.name;
		this.competition = t.competition;
		this.games = t.games;
	}

	/**
	 * General constructor.
	 * 
	 * @param name
	 *            name of team.
	 * @param competition
	 *            competition in which the team plays.
	 * @param alpha
	 *            offensive coefficient.
	 * @param beta
	 *            defensive coefficient.
	 * @param lambda
	 *            average goals scored.
	 * @param gamma
	 *            psychological coefficient.
	 * @param games
	 *            list of game to play.
	 */
	public Team(String name, Competition competition, double alpha, double beta, double lambda, double gamma,
			ArrayList<Game> games) {
		this.name = name;
		this.competition = competition;
		this.games = games;
		this.sortGames();
		this.checkGames();
		this.updateCompetition();
	}

	/**
	 * Name-getter.
	 * 
	 * @return name of the team.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name-setter.
	 * 
	 * @param name
	 *            name of the team.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Competition-getter.
	 * 
	 * @return competition in which the team plays.
	 */
	public Competition getCompetition() {
		return competition;
	}

	/**
	 * Competition-setter.
	 * 
	 * @param new
	 *            competition in which the team plays.
	 */
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	/**
	 * Getter games list.
	 * 
	 * @return list of games for the team.
	 */
	public ArrayList<Game> getGames() {
		return games;
	}

	/**
	 * Setter games list.
	 * 
	 * @param games
	 *            new list of games for the team.
	 */
	public void setGames(ArrayList<Game> games) {
		this.games = games;
		this.checkGames();
		this.sortGames();
	}

	/**
	 * Determines the number of games played by the team.
	 * 
	 * @return number of games played.
	 */
	public int gamesPlayed(){
		int gp = 0;
		for(Game g: games){
			if(g.isPlayed()){
				gp++;
			}
		}
		return gp;
	}
	
	/*
	 * Sorts games according to their date. Uses bubble sort algorithm.
	 */
	public void sortGames() {
		if (this.games.size() > 1) {
			for (int i = games.size()-1; i>=0; i--) {
				for (int j = 1; j <= i; j++) {
					if (games.get(j-1).getGameDay().after(games.get(j).getGameDay())) {
						Game temp = games.get(j-1);
						games.add(j-1, games.get(j ));
						games.remove(j);
						games.add(j, temp);
						games.remove(j + 1);
					}
				}
			}
		}
	}

	/**
	 * Checks if each game in the game list is compatible with the Team-object.
	 */
	private void checkGames() {
		for (int i = games.size() - 1; i >= 0; i--) {
			if (!games.get(i).getHome().equals(this) && !games.get(i).getAway().equals(this)) {
				games.remove(i);
			}
		}
	}

	/**
	 * Updates competition-object.
	 */
	private void updateCompetition() {
		ArrayList<Team> temp = this.competition.getTeams();
		for (int i = temp.size() - 1; i >= 0; i--) {
			if (temp.get(i).equals(this)) {
				temp.add(i, this);
				temp.remove(i + 1);
				this.competition.setTeams(temp);
				return;
			}
		}
		temp.add(this);
		this.competition.setTeams(temp);
		this.competition.sortTeams();
		this.competition.orderRanking();
	}

	/**
	 * Compares input object to current Team-object.
	 * 
	 * @param obj
	 *            Object to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			Team that = (Team) obj;
			return this.name.equals(that.name) && this.competition.equals(that.competition);
		}
		return false;
	}

}