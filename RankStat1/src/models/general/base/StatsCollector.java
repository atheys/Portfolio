package models.general.base;

/**
 * StatsCollector Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class StatsCollector {

	/**
	 * Collects all average statistic numbers.
	 * 
	 * @param a
	 *            relevant Average-Object.
	 * @return corresponding values array.
	 */
	public static double[] mu(Average a) {
		double[] mu = { a.getAvGoals(), a.getAvPossession(), a.getAvSuccessPasses(), a.getAvTotalPasses(),
				a.getAvPassSuccess(), a.getAvAerialsWon(), a.getAvShots(), a.getAvShotsOnTarget(), a.getAvDribbles(),
				a.getAvFouled(), a.getAvOffSides(), a.getAvTackles(), a.getAvFouls(), a.getAvOffsidesAgainst(),
				a.getAvCleanSheets(), a.getAvYellowCards(), a.getAvRedCards(), a.getAvSubstitutions() };
		return mu;
	}

	/**
	 * Collects all deviation statistic numbers.
	 * 
	 * @param d
	 *            relevant Deviation-Object.
	 * @return corresponding values array.
	 */
	public static double[] sigma(Deviation d) {
		double[] mu = { d.getDevGoals(), d.getDevPossession(), d.getDevSuccessPasses(), d.getDevTotalPasses(),
				d.getDevPassSuccess(), d.getDevAerialsWon(), d.getDevShots(), d.getDevShotsOnTarget(),
				d.getDevDribbles(), d.getDevFouled(), d.getDevOffSides(), d.getDevTackles(), d.getDevFouls(),
				d.getDevOffsidesAgainst(), d.getDevCleanSheets(), d.getDevYellowCards(), d.getDevRedCards(),
				d.getDevSubstitutions() };
		return mu;
	}

	/**
	 * Collects all average statistic numbers (offensive profile).
	 * 
	 * @param a
	 *            relevant Average-Object.
	 * @return corresponding values array.
	 */
	public static double[] mu_O(Average a) {
		double[] mu = { a.getAvShotsOnTarget(), a.getAvDribbles(), a.getAvFouled(), a.getAvOffSides() };
		return mu;
	}

	/**
	 * Collects all deviation statistic numbers (offensive profile).
	 * 
	 * @param d
	 *            relevant Deviation-Object.
	 * @return corresponding values array.
	 */
	public static double[] sigma_O(Deviation d) {
		double[] sigma = { d.getDevShotsOnTarget(), d.getDevDribbles(), d.getDevFouled(), d.getDevOffSides() };
		return sigma;
	}

	/**
	 * Collects all average statistic numbers (defensive profile).
	 * 
	 * @param a
	 *            relevant Average-Object.
	 * @return corresponding values array.
	 */
	public static double[] mu_D(Average a) {
		double[] mu = { a.getAvTackles(), a.getAvFouls(), a.getAvOffsidesAgainst(), a.getAvRedCards() };
		return mu;
	}

	/**
	 * Collects all deviation statistic numbers (defensive profile).
	 * 
	 * @param d
	 *            relevant Deviation-Object.
	 * @return corresponding values array.
	 */
	public static double[] sigma_D(Deviation d) {
		double[] sigma = { d.getDevTackles(), d.getDevFouls(), d.getDevOffsidesAgainst(), d.getDevRedCards() };
		return sigma;
	}

	/**
	 * Collects all average statistic numbers (offensive-aggressive profile).
	 * 
	 * @param a
	 *            relevant Average-Object.
	 * @return corresponding values array.
	 */
	public static double[] mu_OA(Average a) {
		double[] mu = { a.getAvAerialsWon(), a.getAvShots(), a.getAvFouled(), a.getAvOffSides() };
		return mu;
	}

	/**
	 * Collects all deviation statistic numbers (offensive-aggressive profile).
	 * 
	 * @param d
	 *            relevant Deviation-Object.
	 * @return corresponding values array.
	 */
	public static double[] sigma_OA(Deviation d) {
		double[] sigma = { d.getDevAerialsWon(), d.getDevShots(), d.getDevFouled(), d.getDevOffSides() };
		return sigma;
	}

	/**
	 * Collects all average statistic numbers (defensive-aggressive profile).
	 * 
	 * @param a
	 *            relevant Average-Object.
	 * @return corresponding values array.
	 */
	public static double[] mu_DA(Average a) {
		double[] mu = { a.getAvTackles(), a.getAvFouls(), a.getAvYellowCards(), a.getAvRedCards() };
		return mu;
	}

	/**
	 * Collects all deviation statistic numbers (defensive-aggressive profile).
	 * 
	 * @param d
	 *            relevant Deviation-Object.
	 * @return corresponding values array.
	 */
	public static double[] sigma_DA(Deviation d) {
		double[] sigma = { d.getDevTackles(), d.getDevFouls(), d.getDevYellowCards(), d.getDevRedCards() };
		return sigma;
	}

	/**
	 * Collects all average statistic numbers (possessive profile).
	 * 
	 * @param a
	 *            relevant Average-Object.
	 * @return corresponding values array.
	 */
	public static double[] mu_P(Average a) {
		double[] mu = { a.getAvPossession(), a.getAvPassSuccess(), a.getAvFouled() };
		return mu;
	}

	/**
	 * Collects all deviation statistic numbers (possessive profile).
	 * 
	 * @param d
	 *            relevant Deviation-Object.
	 * @return corresponding values array.
	 */
	public static double[] sigma_P(Deviation d) {
		double[] sigma = { d.getDevPossession(), d.getDevPassSuccess(), d.getDevFouled() };
		return sigma;
	}

}