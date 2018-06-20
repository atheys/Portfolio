package models.general.base;

/**
 * Average data container class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Deviation {

	/**
	 * Class attributes.
	 */

	// Offensive stat deviations
	private double devGoals;
	private double devPossession;
	private double devSuccessPasses;
	private double devTotalPasses;
	private double devPassSuccess;
	private double devAerialsWon;
	private double devShots;
	private double devShotsOnTarget;
	private double devDribbles;
	private double devFouled;
	private double devOffSides;

	// Defensive stat deviations
	private double devTackles;
	private double devFouls;
	private double devOffsidesAgainst;
	private double devCleanSheets;
	private double devYellowCards;
	private double devRedCards;
	private double devSubstitutions;

	// Psychological stat deviations
	private double devImportance;

	/**
	 * Copy constructor.
	 * 
	 * @param a
	 *            Deviation-object to copy.
	 */
	public Deviation(Deviation d) {
		this.devGoals = d.devGoals;
		this.devPossession = d.devPossession;
		this.devSuccessPasses = d.devSuccessPasses;
		this.devTotalPasses = d.devTotalPasses;
		this.devPassSuccess = d.devPassSuccess;
		this.devAerialsWon = d.devAerialsWon;
		this.devShots = d.devShots;
		this.devShotsOnTarget = d.devShotsOnTarget;
		this.devDribbles = d.devDribbles;
		this.devFouled = d.devFouled;
		this.devOffSides = d.devOffSides;
		this.devTackles = d.devTackles;
		this.devFouls = d.devFouls;
		this.devOffsidesAgainst = d.devOffsidesAgainst;
		this.devCleanSheets = d.devCleanSheets;
		this.devYellowCards = d.devYellowCards;
		this.devRedCards = d.devRedCards;
		this.devSubstitutions = d.devSubstitutions;
		this.devImportance = d.devImportance;
	}

	/**
	 * General constructor.
	 * 
	 * @param avGoals
	 *            deviation goals scored.
	 * @param avPossession
	 *            deviation ball possession.
	 * @param avSuccessPasses
	 *            deviation successful passes.
	 * @param avTotalPasses
	 *            deviation total passes.
	 * @param avPassSuccess
	 *            deviation successful passing rate.
	 * @param avAerialsWon
	 *            deviation number of aerials won.
	 * @param avShots
	 *            deviation shots taken.
	 * @param avShotsOnTarget
	 *            deviation shots on target.
	 * @param avDribbles
	 *            deviation number of dribbles.
	 * @param avFouled
	 *            deviation number of times fouled.
	 * @param avOffSides
	 *            deviation number of offsides committed.
	 * @param avTackles
	 *            deviation number of tackles.
	 * @param avFouls
	 *            deviation number of fouls committed.
	 * @param avOffsidesAgainst
	 *            deviation number of offsides against.
	 * @param avCleanSheets
	 *            deviation number of clean sheets.
	 * @param avYellowCards
	 *            deviation number of yellow cards.
	 * @param avRedCards
	 *            deviation number of red cards.
	 * @param avSubstitutions
	 *            deviation number of substitutions.
	 * @param homeAdvantage
	 *            deviation home advantage number.
	 * @param awayAdvantage
	 *            deviation away advantage number.
	 * @param overestimating
	 *            deviation overestimating number.
	 * @param underestimating
	 *            deviation underestimating number.
	 * @param importance
	 *            deviation importance number.
	 */
	public Deviation(double devGoals, double devPossession, double devSuccessPasses, double devTotalPasses,
			double devPassSuccess, double devAerialsWon, double devShots, double devShotsOnTarget, double devDribbles,
			double devFouled, double devOffSides, double devTackles, double devFouls, double devOffsidesAgainst,
			double devCleanSheets, double devYellowCards, double devRedCards, double devSubstitutions, double devImportance) {
		this.devGoals = devGoals;
		this.devPossession = devPossession;
		this.devSuccessPasses = devSuccessPasses;
		this.devTotalPasses = devTotalPasses;
		this.devPassSuccess = devPassSuccess;
		this.devAerialsWon = devAerialsWon;
		this.devShots = devShots;
		this.devShotsOnTarget = devShotsOnTarget;
		this.devDribbles = devDribbles;
		this.devFouled = devFouled;
		this.devOffSides = devOffSides;
		this.devTackles = devTackles;
		this.devFouls = devFouls;
		this.devOffsidesAgainst = devOffsidesAgainst;
		this.devCleanSheets = devCleanSheets;
		this.devYellowCards = devYellowCards;
		this.devRedCards = devRedCards;
		this.devSubstitutions = devSubstitutions;
		this.devImportance = devImportance;
	}

	/**
	 * Array constructor.
	 * 
	 * @param o
	 *            offensive stat deviations.
	 * @param d
	 *            defensive stat deviations.
	 * @param p
	 *            psychological stat deviations.
	 */
	public Deviation(double[] o, double[] d, double[] p) {
		this.devGoals = o[0];
		this.devPossession = o[1];
		this.devSuccessPasses = o[2];
		this.devTotalPasses = o[3];
		this.devPassSuccess = o[4];
		this.devAerialsWon = o[5];
		this.devShots = o[6];
		this.devShotsOnTarget = o[7];
		this.devDribbles = o[8];
		this.devFouled = o[9];
		this.devOffSides = o[10];
		this.devTackles = d[0];
		this.devFouls = d[1];
		this.devOffsidesAgainst = d[2];
		this.devCleanSheets = d[3];
		this.devYellowCards = d[4];
		this.devRedCards = d[5];
		this.devSubstitutions = d[6];
		this.devImportance = p[0];
	}

	public double getDevGoals() {
		return devGoals;
	}

	public double getDevPossession() {
		return devPossession;
	}

	public double getDevSuccessPasses() {
		return devSuccessPasses;
	}

	public double getDevTotalPasses() {
		return devTotalPasses;
	}

	public double getDevPassSuccess() {
		return devPassSuccess;
	}

	public double getDevAerialsWon() {
		return devAerialsWon;
	}

	public double getDevShots() {
		return devShots;
	}

	public double getDevShotsOnTarget() {
		return devShotsOnTarget;
	}

	public double getDevDribbles() {
		return devDribbles;
	}

	public double getDevFouled() {
		return devFouled;
	}

	public double getDevOffSides() {
		return devOffSides;
	}

	public double getDevTackles() {
		return devTackles;
	}

	public double getDevFouls() {
		return devFouls;
	}

	public double getDevOffsidesAgainst() {
		return devOffsidesAgainst;
	}

	public double getDevCleanSheets() {
		return devCleanSheets;
	}

	public double getDevYellowCards() {
		return devYellowCards;
	}

	public double getDevRedCards() {
		return devRedCards;
	}

	public double getDevSubstitutions() {
		return devSubstitutions;
	}

	public double getDevImportance() {
		return devImportance;
	}
	
}