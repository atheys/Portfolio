package simulation.analyze.selection;

/**
 * Simulation Capsule class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Capsule {

	/**
	 * Class attributes.
	 */
	public String competition;
	public String season;
	public int gDays;
	public int teams;

	/**
	 * Copy constructor.
	 * 
	 * @param c
	 *            Capsule-Object to copy.
	 */
	public Capsule(Capsule c) {
		this.competition = c.competition;
		this.season = c.season;
		this.gDays = c.gDays;
		this.teams = c.teams;
	}

	/**
	 * General constructor.
	 * 
	 * @param competition
	 *            competition label.
	 * @param season
	 *            seasonal label.
	 * @param gDays
	 *            number of competition game days.
	 * @param teams
	 *            number of teams in competition.
	 */
	public Capsule(String competition, String season, int gDays, int teams) {
		this.competition = competition;
		this.season = season;
		this.gDays = gDays;
		this.teams = teams;
	}

}