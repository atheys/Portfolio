package data.core.stats;

import data.core.structure.Team;

/**
 * Psych class. Main psychological stats data container.
 * 
 * @author Andreas Theys
 * @version 3.0
 */
public class Psych {

	/**
	 * Class attributes.
	 */
	private Team t;
	private double importance;

	/**
	 * Copy constructor.
	 * 
	 * @param p
	 *            Psych-object to copy
	 */
	public Psych(Psych p) {
		this.t = p.t;
		this.importance = p.importance;
	}

	/**
	 * General Constructor.
	 * 
	 * @param importance
	 *            importance factor (0<=factor<=1).
	 */
	public Psych(Team t, double importance) {
		this.t = t;
		this.importance = importance;
	}

	/**
	 * Importance getter.
	 * 
	 * @return importance figure.
	 */
	public double getImportance() {
		return importance;
	}

	/**
	 * Importance setter.
	 * 
	 * @param importance
	 *            new importance figure.
	 */
	public void setImportance(double importance) {
		this.importance = importance;
	}

	/**
	 * Compares input object to current psychological data container.
	 * 
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Psych) {
			Psych that = (Psych) obj;
			return this.t.equals(that.t) && this.importance == that.importance;
		}
		return false;
	}

}