package models.general.base;

/**
 * Average Data Container Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Average {

	/**
	 * Class attributes.
	 */

	// Offensive stats
	private double avGoals;
	private double avPossession;
	private double avSuccessPasses;
	private double avTotalPasses;
	private double avPassSuccess;
	private double avAerialsWon;
	private double avShots;
	private double avShotsOnTarget;
	private double avDribbles;
	private double avFouled;
	private double avOffSides;
	// Defensive stats
	private double avTackles;
	private double avFouls;
	private double avOffsidesAgainst;
	private double avCleanSheets;
	private double avYellowCards;
	private double avRedCards;
	private double avSubstitutions;
	// Psychological stats
	private double homePercentage;
	private double awayPercentage;
	private double overPercentage;
	private double underPercentage;
	private double importance;

	/**
	 * Copy constructor.
	 * 
	 * @param a
	 *            Average-object to copy.
	 */
	public Average(Average a) {
		this.avGoals = a.avGoals;
		this.avPossession = a.avPossession;
		this.avSuccessPasses = a.avSuccessPasses;
		this.avTotalPasses = a.avTotalPasses;
		this.avPassSuccess = a.avPassSuccess;
		this.avAerialsWon = a.avAerialsWon;
		this.avShots = a.avShots;
		this.avShotsOnTarget = a.avShotsOnTarget;
		this.avDribbles = a.avDribbles;
		this.avFouled = a.avFouled;
		this.avOffSides = a.avOffSides;
		this.avTackles = a.avTackles;
		this.avFouls = a.avFouls;
		this.avOffsidesAgainst = a.avOffsidesAgainst;
		this.avCleanSheets = a.avCleanSheets;
		this.avYellowCards = a.avYellowCards;
		this.avRedCards = a.avRedCards;
		this.avSubstitutions = a.avSubstitutions;
		this.homePercentage = a.homePercentage;
		this.awayPercentage = a.awayPercentage;
		this.overPercentage = a.overPercentage;
		this.underPercentage = a.underPercentage;
		this.importance = a.importance;
	}

	/**
	 * General constructor.
	 * 
	 * @param avGoals
	 *            average goals scored.
	 * @param avPossession
	 *            average ball possession.
	 * @param avSuccessPasses
	 *            average successful passes.
	 * @param avTotalPasses
	 *            average total passes.
	 * @param avPassSuccess
	 *            average successful passing rate.
	 * @param avAerialsWon
	 *            average number of aerials won.
	 * @param avShots
	 *            average shots taken.
	 * @param avShotsOnTarget
	 *            average shots on target.
	 * @param avDribbles
	 *            average number of dribbles.
	 * @param avFouled
	 *            average number of times fouled.
	 * @param avOffSides
	 *            average number of offsides committed.
	 * @param avTackles
	 *            average number of tackles.
	 * @param avFouls
	 *            average number of fouls committed.
	 * @param avOffsidesAgainst
	 *            average number of offsides against.
	 * @param avCleanSheets
	 *            average number of clean sheets.
	 * @param avYellowCards
	 *            average number of yellow cards.
	 * @param avRedCards
	 *            average number of red cards.
	 * @param avSubstitutions
	 *            average number of substitutions.
	 * @param homeAdvantage
	 *            average home advantage number.
	 * @param awayAdvantage
	 *            average away advantage number.
	 * @param overestimating
	 *            average overestimating number.
	 * @param underestimating
	 *            average underestimating number.
	 * @param importance
	 *            average importance number.
	 */
	public Average(double avGoals, double avPossession, double avSuccessPasses, double avTotalPasses,
			double avPassSuccess, double avAerialsWon, double avShots, double avShotsOnTarget, double avDribbles,
			double avFouled, double avOffSides, double avTackles, double avFouls, double avOffsidesAgainst,
			double avCleanSheets, double avYellowCards, double avRedCards, double avSubstitutions,
			double homePercentage, double awayPercentage, double overPercentage, double underPercentage,
			double importance) {
		this.avGoals = avGoals;
		this.avPossession = avPossession;
		this.avSuccessPasses = avSuccessPasses;
		this.avTotalPasses = avTotalPasses;
		this.avPassSuccess = avPassSuccess;
		this.avAerialsWon = avAerialsWon;
		this.avShots = avShots;
		this.avShotsOnTarget = avShotsOnTarget;
		this.avDribbles = avDribbles;
		this.avFouled = avFouled;
		this.avOffSides = avOffSides;
		this.avTackles = avTackles;
		this.avFouls = avFouls;
		this.avOffsidesAgainst = avOffsidesAgainst;
		this.avCleanSheets = avCleanSheets;
		this.avYellowCards = avYellowCards;
		this.avRedCards = avRedCards;
		this.avSubstitutions = avSubstitutions;
		this.homePercentage = homePercentage;
		this.awayPercentage = awayPercentage;
		this.overPercentage = overPercentage;
		this.underPercentage = underPercentage;
		this.importance = importance;
	}

	/**
	 * Array constructor.
	 * 
	 * @param o
	 *            offensive stat averages.
	 * @param d
	 *            defensive stat averages.
	 * @param p
	 *            psychological stat averages.
	 */
	public Average(double[] o, double[] d, double[] p) {
		// Offensive averages
		this.avGoals = o[0];
		this.avPossession = o[1];
		this.avSuccessPasses = o[2];
		this.avTotalPasses = o[3];
		this.avPassSuccess = o[4];
		this.avAerialsWon = o[5];
		this.avShots = o[6];
		this.avShotsOnTarget = o[7];
		this.avDribbles = o[8];
		this.avFouled = o[9];
		this.avOffSides = o[10];
		// Defensive averages
		this.avTackles = d[0];
		this.avFouls = d[1];
		this.avOffsidesAgainst = d[2];
		this.avCleanSheets = d[3];
		this.avYellowCards = d[4];
		this.avRedCards = d[5];
		this.avSubstitutions = d[6];
		// Psychological averages
		this.homePercentage = p[0];
		this.awayPercentage = p[1];
		this.overPercentage = p[2];
		this.underPercentage = p[3];
		this.importance = p[4];
	}

	/**
	 * Average goals getter.
	 * 
	 * @return average number of goals.
	 */
	public double getAvGoals() {
		return avGoals;
	}

	/**
	 * Average possession getter.
	 * 
	 * @return average possession number.
	 */
	public double getAvPossession() {
		return avPossession;
	}

	/**
	 * Average successful passes getter.
	 * 
	 * @return average number of successful passes.
	 */
	public double getAvSuccessPasses() {
		return avSuccessPasses;
	}

	/**
	 * Average total passes getter.
	 * 
	 * @return average number of total passes.
	 */
	public double getAvTotalPasses() {
		return avTotalPasses;
	}

	/**
	 * Average passing rate getter.
	 * 
	 * @return average passing rate.
	 */
	public double getAvPassSuccess() {
		return avPassSuccess;
	}

	/**
	 * Average aerials won getter.
	 * 
	 * @return average aerials won.
	 */
	public double getAvAerialsWon() {
		return avAerialsWon;
	}

	/**
	 * Average shots getter.
	 * 
	 * @return average shots number.
	 */
	public double getAvShots() {
		return avShots;
	}

	/**
	 * Average shots on target getter.
	 * 
	 * @return average shots on target number.
	 */
	public double getAvShotsOnTarget() {
		return avShotsOnTarget;
	}

	/**
	 * Average dribbles getter.
	 * 
	 * @return average number of dribbles.
	 */
	public double getAvDribbles() {
		return avDribbles;
	}

	/**
	 * Average fouled number getter.
	 * 
	 * @return average number of being fouled.
	 */
	public double getAvFouled() {
		return avFouled;
	}

	/**
	 * Average offsides getter.
	 * 
	 * @return average offsides number.
	 */
	public double getAvOffSides() {
		return avOffSides;
	}

	/**
	 * Average tackles getter.
	 * 
	 * @return average number of tackles.
	 */
	public double getAvTackles() {
		return avTackles;
	}

	/**
	 * Average fouls getter.
	 * 
	 * @return average number of fouls.
	 */
	public double getAvFouls() {
		return avFouls;
	}

	/**
	 * Average offsides against number getter.
	 * 
	 * @return average number of offsides against.
	 */
	public double getAvOffsidesAgainst() {
		return avOffsidesAgainst;
	}

	/**
	 * Average clean sheets of number getter.
	 * 
	 * @return average number of clean sheets.
	 */
	public double getAvCleanSheets() {
		return avCleanSheets;
	}

	/**
	 * Average yellow cards getter.
	 * 
	 * @return average number of yellow cards.
	 */
	public double getAvYellowCards() {
		return avYellowCards;
	}

	/**
	 * Average red cards getter.
	 * 
	 * @return average numbers of red cards.
	 */
	public double getAvRedCards() {
		return avRedCards;
	}

	/**
	 * Average substitutions getter.
	 * 
	 * @return average number of subsitutions.
	 */
	public double getAvSubstitutions() {
		return avSubstitutions;
	}

	/**
	 * Home percentage getter.
	 * 
	 * @return home percentage figure.
	 */
	public double getHomePercentage() {
		return homePercentage;
	}

	/**
	 * Away percentage getter.
	 * 
	 * @return away percentage figure.
	 */
	public double getAwayPercentage() {
		return awayPercentage;
	}

	/**
	 * Over-percentage getter.
	 * 
	 * @return over percentage figure.
	 */
	public double getOverPercentage() {
		return overPercentage;
	}

	/**
	 * Under-percentage getter.
	 * 
	 * @return under percentage figure.
	 */
	public double getUnderPercentage() {
		return underPercentage;
	}

	/**
	 * Importance getter.
	 * 
	 * @return average importance number.
	 */
	public double getImportance() {
		return importance;
	}

}