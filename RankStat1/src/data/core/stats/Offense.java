package data.core.stats;

/**
 * Offense class. Main offensive stats data container.
 * 
 * @author Andreas Theys.
 * @version 3.0
 */
public class Offense {

	/**
	 * Class attributes.
	 */
	private int goals;
	private double possession;
	private int successPasses;
	private int totalPasses;
	private double passSuccess;
	private int aerialsWon;
	private int shots;
	private int shotsOnTarget;
	private int dribbles;
	private int fouled;
	private int offSides;

	/**
	 * Copy constructor.
	 * 
	 * @param o
	 *            Offense data container to copy.
	 */
	public Offense(Offense o) {
		this.goals = o.goals;
		this.possession = o.possession;
		this.successPasses = o.successPasses;
		this.totalPasses = o.totalPasses;
		this.passSuccess = o.passSuccess;
		this.aerialsWon = o.aerialsWon;
		this.shots = o.shots;
		this.shotsOnTarget = o.shotsOnTarget;
		this.dribbles = o.dribbles;
		this.fouled = o.fouled;
		this.offSides = o.offSides;
	}

	/**
	 * General constructor.
	 * 
	 * @param goals
	 *            number of goals made.
	 * @param attackLeft
	 *            attack percentage on the left side of the pitch.
	 * @param attackMiddle
	 *            attack percentage on the middle side of the pitch.
	 * @param attackRight
	 *            attack percentage on the right side of the pitch.
	 * @param shots
	 *            number of shots made.
	 * @param shotsOnTarget
	 *            number of shots on target.
	 * @param shotsLeft
	 *            percentage of shots on left side of the goal.
	 * @param shotsMiddle
	 *            percentage of shots in the middle of the goal.
	 * @param shotsRight
	 *            percentage of shots on the right side of the goal.
	 * @param shotsLow
	 *            percentage of shots in the low distance range of the goal.
	 * @param shotsMedium
	 *            percentage of shots in the medium distance range of the goal.
	 * @param shotsHigh
	 *            percentage of shots in the long distance range of the goal.
	 * @param dribbles
	 *            number of (successful) dribbles performed.
	 * @param fouled
	 *            number of times being fouled.
	 * @param crosses
	 *            number of (successful) cross passes.
	 * @param throughPasses
	 *            number of (successful) through passes.
	 * @param longPasses
	 *            number of (successful) long passes.
	 * @param shortPasses
	 *            number of (successful) short passes.
	 */
	public Offense(int goals, double possession, int successPasses, int totalPasses, double passSuccess, int aerialsWon,
			int shots, int shotsOnTarget, int dribbles, int fouled, int offSides) {
		this.goals = goals;
		this.possession = possession;
		this.successPasses = successPasses;
		this.totalPasses = totalPasses;
		this.passSuccess = passSuccess;
		this.aerialsWon = aerialsWon;
		this.shots = shots;
		this.shotsOnTarget = shotsOnTarget;
		this.dribbles = dribbles;
		this.fouled = fouled;
		this.offSides = offSides;
	}

	/**
	 * Goals getter.
	 * 
	 * @return number of goals.
	 */
	public int getGoals() {
		return goals;
	}

	/**
	 * Possession getter.
	 * 
	 * @return possession number.
	 */
	public double getPossession() {
		return possession;
	}

	/**
	 * Successful passes getter.
	 * 
	 * @return number of successfull passes.
	 */
	public int getSuccessPasses() {
		return successPasses;
	}

	/**
	 * Total passes getter.
	 * 
	 * @return number of total passes.
	 */
	public int getTotalPasses() {
		return totalPasses;
	}

	/**
	 * Total passes setter.
	 * 
	 * @param totalPasses
	 *            new number of total passes.
	 * @note only used in Collector-Class.
	 */
	public void setTotalPasses(int totalPasses) {
		this.totalPasses = totalPasses;
	}

	/**
	 * Successful passing rate getter.
	 * 
	 * @return successful passing rate.
	 */
	public double getPassSuccess() {
		return passSuccess;
	}

	/**
	 * Aerials won getter.
	 * 
	 * @return number of aerials won.
	 */
	public int getAerialsWon() {
		return aerialsWon;
	}

	/**
	 * Shots getter.
	 * 
	 * @return number of shots.
	 */
	public int getShots() {
		return shots;
	}

	/**
	 * Shots on target getter.
	 * 
	 * @return number of shots on target.
	 */
	public int getShotsOnTarget() {
		return shotsOnTarget;
	}

	/**
	 * Dribbles getter.
	 * 
	 * @return number of dribbles.
	 */
	public int getDribbles() {
		return dribbles;
	}

	/**
	 * Fouled getter.
	 * 
	 * @return fouled number.
	 */
	public int getFouled() {
		return fouled;
	}

	/**
	 * Offsides getter.
	 * 
	 * @return number of offsides.
	 */
	public int getOffSides() {
		return offSides;
	}

	/**
	 * Compares input object to current offensive data container.
	 * 
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Offense) {
			Offense that = (Offense) obj;
			return this.goals == that.goals && this.possession == that.possession
					&& this.passSuccess == that.passSuccess && this.aerialsWon == that.aerialsWon
					&& this.shots == that.shots && this.shotsOnTarget == that.shotsOnTarget
					&& this.dribbles == that.dribbles && this.fouled == that.fouled && this.offSides == that.offSides;
		}
		return false;
	}

}