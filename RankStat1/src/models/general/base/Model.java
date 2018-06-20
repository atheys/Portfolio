package models.general.base;

/**
 * Standard Model class for statistical analysis.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Model {

	/**
	 * Class attributes.
	 */
	protected String id;
	protected StatsBase sb;

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID feature of the model.
	 * @param successes
	 *            number of successful predictions.
	 * @param failures
	 *            number of failed predictions.
	 * @param stats
	 *            statistic bases.
	 */
	public Model(String id, StatsBase sb) {
		this.id = id;
		this.sb = sb;
	}

	/**
	 * ID getter.
	 * 
	 * @return ID of the model.
	 */
	public String getId() {
		return id;
	}

	/**
	 * StatsBase getter.
	 * 
	 * @return model StatsBase-Object.
	 */
	public StatsBase getSb() {
		return sb;
	}

	/**
	 * Method to find the appropriate bonus feature.
	 * 
	 * @param b_index
	 *            bonus grid index number.
	 * @param value
	 *            value to search for in grid array.
	 * @return corresponding bonus value.
	 */
	protected double find(int b_index, double value) {
		double bonus = 0.;
		double[] values = this.sb.getBonus()[b_index][0];
		double[] bonusses = this.sb.getBonus()[b_index][1];
		double min = Double.MAX_VALUE;
		int min_index = -1;
		for (int i = 0; i < values.length; i++) {
			double check = Math.abs(value - values[i]);
			if (check < min) {
				min = check;
				min_index = i;
			}
		}
		bonus = bonusses[min_index];
		return bonus;
	}
	
	/**
	 * Method to find the appropriate bonus feature.
	 * 
	 * @param b_index
	 *            bonus grid index number.
	 * @param value
	 *            value to search for in grid array.
	 * @return corresponding bonus value.
	 */
	protected double findHome(int b_index, double value) {
		double bonus = 0.;
		double[] values = this.sb.getBonus_home()[b_index][0];
		double[] bonusses = this.sb.getBonus_home()[b_index][1];
		double min = Double.MAX_VALUE;
		int min_index = -1;
		for (int i = 0; i < values.length; i++) {
			double check = Math.abs(value - values[i]);
			if (check < min) {
				min = check;
				min_index = i;
			}
		}
		bonus = bonusses[min_index];
		return bonus;
	}
	
	/**
	 * Method to find the appropriate bonus feature.
	 * 
	 * @param b_index
	 *            bonus grid index number.
	 * @param value
	 *            value to search for in grid array.
	 * @return corresponding bonus value.
	 */
	protected double findAway(int b_index, double value) {
		double bonus = 0.;
		double[] values = this.sb.getBonus_away()[b_index][0];
		double[] bonusses = this.sb.getBonus_away()[b_index][1];
		double min = Double.MAX_VALUE;
		int min_index = -1;
		for (int i = 0; i < values.length; i++) {
			double check = Math.abs(value - values[i]);
			if (check < min) {
				min = check;
				min_index = i;
			}
		}
		bonus = bonusses[min_index];
		return bonus;
	}

	/**
	 * Gathers all average numbers of a team and its former opponents.
	 * 
	 * @param team
	 *            team averages.
	 * @param team_adv
	 *            team adversary averages.
	 * @return corresponding values array.
	 */
	protected double[] gatherValues(Average team, Average team_adv) {
		double[] team_values = { team.getAvGoals(), team.getAvPossession(), team.getAvSuccessPasses(),
				team.getAvTotalPasses(), team.getAvPassSuccess(), team.getAvAerialsWon(), team.getAvShots(),
				team.getAvShotsOnTarget(), team.getAvDribbles(), team.getAvFouled(), team.getAvOffSides(),
				team.getAvTackles(), team.getAvFouls(), team.getAvOffsidesAgainst(), team.getAvCleanSheets(),
				team.getAvYellowCards(), team.getAvRedCards(), team.getAvSubstitutions(), team_adv.getAvGoals(),
				team_adv.getAvPossession(), team_adv.getAvSuccessPasses(), team_adv.getAvTotalPasses(),
				team_adv.getAvPassSuccess(), team_adv.getAvAerialsWon(), team_adv.getAvShots(),
				team_adv.getAvShotsOnTarget(), team_adv.getAvDribbles(), team_adv.getAvFouled(),
				team_adv.getAvOffSides(), team_adv.getAvTackles(), team_adv.getAvFouls(),
				team_adv.getAvOffsidesAgainst(), team_adv.getAvCleanSheets(), team_adv.getAvYellowCards(),
				team_adv.getAvRedCards(), team_adv.getAvSubstitutions(), team.getAvGoals(), team.getAvPossession(),
				team.getAvSuccessPasses(), team.getAvTotalPasses(), team.getAvPassSuccess(), team.getAvAerialsWon(),
				team.getAvShots(), team.getAvShotsOnTarget(), team.getAvDribbles(), team.getAvFouled(),
				team.getAvOffSides(), team.getAvTackles(), team.getAvFouls(), team.getAvOffsidesAgainst(),
				team.getAvCleanSheets(), team.getAvYellowCards(), team.getAvRedCards(), team.getAvSubstitutions(),
				team_adv.getAvGoals(), team_adv.getAvPossession(), team_adv.getAvSuccessPasses(),
				team_adv.getAvTotalPasses(), team_adv.getAvPassSuccess(), team_adv.getAvAerialsWon(),
				team_adv.getAvShots(), team_adv.getAvShotsOnTarget(), team_adv.getAvDribbles(), team_adv.getAvFouled(),
				team_adv.getAvOffSides(), team_adv.getAvTackles(), team_adv.getAvFouls(),
				team_adv.getAvOffsidesAgainst(), team_adv.getAvCleanSheets(), team_adv.getAvYellowCards(),
				team_adv.getAvRedCards(), team_adv.getAvSubstitutions() };
		return team_values;
	}

	/**
	 * Evaluates sum of products of two value arrays.
	 * 
	 * @param first
	 *            first values array.
	 * @param second
	 *            second values array.
	 * @return corresponding sum value.
	 */
	protected double eval(double[] first, double[] second) {
		double result = 0.;
		if (first.length == second.length) {
			for (int i = 0; i < first.length; i++) {
				if (first[i] > 0.)
					result += first[i] * second[i];
			}
		}
		return result;
	}

	/**
	 * Compares Model-instance to a given Object.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Model) {
			Model that = (Model) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}