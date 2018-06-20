package mathematics.portfolio;

/**
 * Asset definition class. Basic financial portfolio unit.
 * 
 * @author Andreas Theys.
 * @version 5.0
 */
public class Asset {

	/**
	 * Class attributes.
	 */
	private String game_id;
	private String model_id;
	private String bookie_id;
	private double expected;
	private double risk;
	private double stake;
	private String e_type;
	private double won;

	/**
	 * Copy constructor.
	 * 
	 * @param a
	 *            Asset-object to copy.
	 */
	public Asset(Asset a) {
		this.game_id = a.game_id;
		this.model_id = a.model_id;
		this.bookie_id = a.bookie_id;
		this.expected = a.expected;
		this.risk = a.risk;
		this.stake = a.stake;
		this.e_type = a.e_type;
		this.won = a.won;
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the asset.
	 * @param expected
	 *            expected return of the asset.
	 * @param risk
	 *            risk assessment of the asset (eq. standard deviation).
	 */
	public Asset(String game_id, String model_id, String bookie_id, double expected, double risk, String e_type) {
		this.game_id = game_id;
		this.model_id = model_id;
		this.bookie_id = bookie_id;
		this.expected = expected;
		this.risk = risk;
		this.stake = 0.;
		this.e_type = e_type;
	}

	/**
	 * General constructor2.
	 * 
	 * @param id
	 *            ID of the asset.
	 * @param expected
	 *            expected return of the asset.
	 * @param risk
	 *            risk assessment of the asset (eq. standard deviation).
	 */
	public Asset(String game_id, String model_id, String bookie_id, double expected, double risk, double won) {
		this.game_id = game_id;
		this.model_id = model_id;
		this.bookie_id = bookie_id;
		this.expected = expected;
		this.risk = risk;
		this.stake = 0.;
		this.e_type = "";
		this.won = won;
	}

	public String getGame_id() {
		return game_id;
	}

	public String getModel_id() {
		return model_id;
	}

	public String getBookie_id() {
		return bookie_id;
	}

	/**
	 * Getter expected return.
	 * 
	 * @return expected return of the asset.
	 */
	public double getExpected() {
		return expected;
	}

	/**
	 * Getter risk assessment.
	 * 
	 * @return risk assessment of the asset.
	 */
	public double getRisk() {
		return risk;
	}

	/**
	 * Stake getter.
	 * 
	 * @return stake figure.
	 */
	public double getStake() {
		return stake;
	}

	/**
	 * Stake setter.
	 * 
	 * @param stake
	 *            new stake number.
	 */
	public void setStake(double stake) {
		this.stake = stake;
	}

	/**
	 * Evaluation type getter.
	 * 
	 * @return evaluation type String.
	 */
	public String getE_type() {
		return e_type;
	}

	/**
	 * Evaluation type setter.
	 * 
	 * @param e_type
	 *            new evaluation type String.
	 */
	public void setE_type(String e_type) {
		this.e_type = e_type;
	}

	/**
	 * Won getter.
	 * 
	 * @return effective profit ratio.
	 */
	public double getWon() {
		return won;
	}

	/**
	 * Calculates overall return profit.
	 * 
	 * @param invest
	 *            amount to invest.
	 * @return expected profit.
	 */
	public double ret(double invest) {
		return invest * this.expected;
	}

	/**
	 * Compares two Asset-objects.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Asset) {
			Asset that = (Asset) obj;
			return this.game_id.equals(that.game_id);
		}
		return false;
	}

	/**
	 * Selects the most profitable asset.
	 * 
	 * @note method mostly used in POptimizer-Objects.
	 * @param a
	 *            first Asset-Object.
	 * @param b
	 *            second Asset_Object.
	 * @return most profitable asset.
	 * 
	 */
	public static Asset select(Asset a, Asset b) {
		if (a.equals(b)) {
			if (a.getExpected() > b.getExpected()) {
				return a;
			}
			return b;
		}
		return null;
	}

}