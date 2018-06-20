package data.core.stats;

/**
 * Defense class. Main defensive stats data container.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class Defense {

	/**
	 * Class attributes.
	 */
	private int tackles;
	private int fouls;
	private int offsidesAgainst;
	private boolean cleanSheet;
	private int yellowCards;
	private int redCards;
	private int substitutions;

	/**
	 * Copy constructor.
	 * 
	 * @param d
	 *            Defense-object to copy.
	 */
	public Defense(Defense d) {
		this.tackles = d.tackles;
		this.fouls = d.fouls;
		this.offsidesAgainst = d.offsidesAgainst;
		this.cleanSheet = d.cleanSheet;
		this.yellowCards = d.yellowCards;
		this.redCards = d.redCards;
		this.substitutions = d.substitutions;
	}

	/**
	 * General constructor.
	 * 
	 * @param tackles
	 *            number of tackles performed.
	 * @param interceptions
	 *            number of successful interceptions.
	 * @param fouls
	 *            number of fouls.
	 * @param offsidesAgainst
	 *            number of offsides against.
	 * @param yellowCards
	 *            number of yellow cards received.
	 * @param redCards
	 *            number of red cards received.
	 */
	public Defense(int tackles, int fouls, int offsidesAgainst, boolean cleanSheet, int yellowCards, int redCards,
			int substitutions) {
		this.tackles = tackles;
		this.fouls = fouls;
		this.offsidesAgainst = offsidesAgainst;
		this.cleanSheet = cleanSheet;
		this.yellowCards = yellowCards;
		this.redCards = redCards;
		this.substitutions = substitutions;
	}

	/**
	 * Tackles getter.
	 * 
	 * @return number of tackles.
	 */
	public int getTackles() {
		return tackles;
	}

	/**
	 * Fouls getter.
	 * 
	 * @return number of fouls.
	 */
	public int getFouls() {
		return fouls;
	}

	/**
	 * Offsides agains getter.
	 * 
	 * @return number of offsides against.
	 */
	public int getOffsidesAgainst() {
		return offsidesAgainst;
	}

	/**
	 * Clean sheet getter.
	 * 
	 * @return number of clean sheets.
	 */
	public boolean isCleanSheet() {
		return cleanSheet;
	}

	/**
	 * Yellow cards getter.
	 * 
	 * @return number of yellow cards.
	 */
	public int getYellowCards() {
		return yellowCards;
	}

	/**
	 * Red cards getter.
	 * 
	 * @return number of red cards.
	 */
	public int getRedCards() {
		return redCards;
	}

	/**
	 * Substitutions getter.
	 * 
	 * @return number of substitutions.
	 */
	public int getSubstitutions() {
		return substitutions;
	}

	/**
	 * Compares input object to current defensive data container.
	 * 
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Defense) {
			Defense that = (Defense) obj;
			return this.tackles == that.tackles && this.fouls == that.fouls
					&& this.offsidesAgainst == that.offsidesAgainst && this.cleanSheet == that.cleanSheet
					&& this.yellowCards == that.yellowCards && this.redCards == that.redCards
					&& this.substitutions == that.substitutions;
		}
		return false;
	}

}