package simulation.analyze.body;

import mathematics.portfolio.Asset;

/**
 * ModelCap class for model performance data.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class ModelCap {

	/**
	 * Class attributes.
	 */
	private boolean relevant;
	private String model_id;
	private String game_id;
	private double home;
	private double draw;
	private double away;

	/**
	 * Default constructor.
	 */
	public ModelCap() {
		this.relevant = false;
	}

	/**
	 * General constructor.
	 * 
	 * @param model_id
	 *            model ID feature.
	 * @param game_id
	 *            game ID feature.
	 * @param home
	 *            home win odds.
	 * @param draw
	 *            draw odds.
	 * @param away
	 *            away win odds.
	 */
	public ModelCap(String model_id, String game_id, double home, double draw, double away) {
		this.relevant = true;
		this.model_id = model_id;
		this.game_id = game_id;
		this.home = home;
		this.draw = draw;
		this.away = away;
	}

	/**
	 * Relevance getter.
	 * 
	 * @return relevance indicator.
	 */
	public boolean isRelevant() {
		return relevant;
	}

	/**
	 * Model ID getter.
	 * 
	 * @return model ID.
	 */
	public String getModel_id() {
		return model_id;
	}

	/**
	 * Game ID getter.
	 * 
	 * @return game ID.
	 */
	public String getGame_id() {
		return game_id;
	}

	/**
	 * Home win probability getter.
	 * 
	 * @return home win probability.
	 */
	public double getHome() {
		return home;
	}

	/**
	 * Draw probability getter.
	 * 
	 * @return draw probability.
	 */
	public double getDraw() {
		return draw;
	}

	/**
	 * Away win probability getter.
	 * 
	 * @return away win probability.
	 */
	public double getAway() {
		return away;
	}

	/**
	 * Maximum helper function.
	 * 
	 * @param H
	 *            first variable.
	 * @param D
	 *            second variable.
	 * @param A
	 *            third variable.
	 * @return maximum of all three values.
	 */
	private double max(double H, double D, double A) {
		return Math.max(Math.max(H, A), D);
	}

	/**
	 * Minimum helper function.
	 * 
	 * @param H
	 *            first variable.
	 * @param D
	 *            second variable.
	 * @param A
	 *            third variable.
	 * @return minimum of all three values.
	 */
	private double min(double H, double D, double A) {
		return Math.min(Math.min(H, A), D);
	}

	/**
	 * Decision making for the highest probable odds.
	 * 
	 * @param homeOdds
	 *            home win odds.
	 * @param drawOdds
	 *            draw odds.
	 * @param awayOdds
	 *            away win odds.
	 * @return decision array.
	 */
	private boolean[] decideM() {
		double H = this.home;
		double D = this.draw;
		double A = this.away;
		boolean[] option1 = { true, false, false };
		boolean[] option2 = { false, true, false };
		boolean[] option3 = { false, false, true };
		boolean[] option4 = { false, false, false };
		if (max(H, D, A) == H) {
			return option1;
		}
		if (max(H, D, A) == D) {
			return option2;
		}
		if (max(H, D, A) == A) {
			return option3;
		}
		return option4;
	}

	/**
	 * Decision making for the highest profitable odds.
	 * 
	 * @param homeOdds
	 *            home win odds.
	 * @param drawOdds
	 *            draw odds.
	 * @param awayOdds
	 *            away win odds.
	 * @return decision array.
	 */
	private boolean[] decideO(double homeOdds, double drawOdds, double awayOdds) {
		double H = homeOdds;
		double D = drawOdds;
		double A = awayOdds;
		boolean[] option1 = { true, false, false };
		boolean[] option2 = { false, true, false };
		boolean[] option3 = { false, false, true };
		boolean[] option4 = { false, false, false };
		if (min(H, D, A) == H) {
			return option1;
		}
		if (min(H, D, A) == D) {
			return option2;
		}
		if (min(H, D, A) == A) {
			return option3;
		}
		return option4;
	}

	/**
	 * Decision making for the highest profitable odds.
	 * 
	 * @param homeOdds
	 *            home win odds.
	 * @param drawOdds
	 *            draw odds.
	 * @param awayOdds
	 *            away win odds.
	 * @return decision array.
	 */
	private boolean[] decideF(double homeOdds, double drawOdds, double awayOdds) {
		double H = homeOdds * this.home;
		double D = drawOdds * this.draw;
		double A = awayOdds * this.away;
		boolean[] option1 = { true, false, false };
		boolean[] option2 = { false, true, false };
		boolean[] option3 = { false, false, true };
		boolean[] option4 = { false, false, false };
		if (max(H, D, A) == H) {
			return option1;
		}
		if (max(H, D, A) == D) {
			return option2;
		}
		if (max(H, D, A) == A) {
			return option3;
		}
		return option4;
	}

	/**
	 * Decision making for the highest profitable odds.
	 * 
	 * @param homeOdds
	 *            home win odds.
	 * @param drawOdds
	 *            draw odds.
	 * @param awayOdds
	 *            away win odds.
	 * @return decision array.
	 */
	private boolean[] decideE(double homeOdds, double drawOdds, double awayOdds, double importance) {
		double E_H = homeOdds * this.home - 1.;
		double E_D = drawOdds * this.draw - 1.;
		double E_A = awayOdds * this.away - 1.;
		double V_H = 0.5 * (this.home * Math.pow(homeOdds - 1. - E_H, 2) + (1 - this.home) * Math.pow(-1. - E_H, 2));
		double V_D = 0.5 * (this.draw * Math.pow(drawOdds - 1. - E_D, 2) + (1 - this.draw) * Math.pow(-1. - E_D, 2));
		double V_A = 0.5 * (this.away * Math.pow(awayOdds - 1. - E_A, 2) + (1 - this.away) * Math.pow(-1. - E_A, 2));
		boolean[] option1 = { true, false, false };
		boolean[] option2 = { false, true, false };
		boolean[] option3 = { false, false, true };
		boolean[] option4 = { false, false, false };
		double H = E_H / Math.pow(V_H, importance);
		double D = E_D / Math.pow(V_D, importance);
		double A = E_A / Math.pow(V_A, importance);
		if (max(H, D, A) == H) {
			return option1;
		}
		if (max(H, D, A) == D) {
			return option2;
		}
		if (max(H, D, A) == A) {
			return option3;
		}
		return option4;
	}

	/**
	 * Creates Asset-Object based on given input data.
	 * 
	 * @param bookie_id
	 *            Bookie ID-feature.
	 * @param prob
	 * @param odds
	 * @return
	 */
	private Asset createAsset(String bookie_id, double prob, double odds, String e_type) {
		double ret = prob * odds - 1.;
		double risk = Math.sqrt(0.5 * (prob * Math.pow(odds - 1. - ret, 2) + (1 - prob) * Math.pow(-1. - ret, 2)));
		return new Asset(this.game_id, this.model_id, bookie_id, ret, risk, e_type);
	}

	/**
	 * Creates array of corresponding Asset-Objects.
	 * 
	 * @param bookie_id
	 *            bookie ID.
	 * @param homeOdds
	 *            home odds.
	 * @param drawOdds
	 *            draw odds.
	 * @param awayOdds
	 *            away odds.
	 * @param importance
	 *            variance importance figure.
	 * @param e_type
	 *            evaluation type indicator.
	 * @return Asset-Object array.
	 */
	public Asset[] createAsset(String bookie_id, double homeOdds, double drawOdds, double awayOdds, double importance,
			String e_type) {
		boolean[] decisionM = decideM();
		assert decisionM.length == 3;
		boolean[] decisionO = decideO(homeOdds, drawOdds, awayOdds);
		assert decisionO.length == 3;
		boolean[] decisionF = decideF(homeOdds, drawOdds, awayOdds);
		assert decisionF.length == 3;
		boolean[] decisionE = decideE(homeOdds, drawOdds, awayOdds, importance);
		assert decisionE.length == 3;
		Asset[] assets = new Asset[4];
		if (decisionM[0]) {
			assets[0] = createAsset(bookie_id, this.home, homeOdds, e_type);
		}
		if (decisionM[1]) {
			assets[0] = createAsset(bookie_id, this.draw, drawOdds, e_type);
		}
		if (decisionM[2]) {
			assets[0] = createAsset(bookie_id, this.away, awayOdds, e_type);
		}
		if (!decisionM[0] && !decisionM[1] && !decisionM[2]) {
			assets[0] = new Asset(this.game_id, this.model_id, bookie_id, 0, 0, e_type);
		}
		if (decisionO[0]) {
			assets[1] = createAsset(bookie_id, this.home, homeOdds, e_type);
		}
		if (decisionO[1]) {
			assets[1] = createAsset(bookie_id, this.draw, drawOdds, e_type);
		}
		if (decisionO[2]) {
			assets[1] = createAsset(bookie_id, this.away, awayOdds, e_type);
		}
		if (!decisionO[0] && !decisionO[1] && !decisionO[2]) {
			assets[1] = new Asset(this.game_id, this.model_id, bookie_id, 0, 0, e_type);
		}
		if (decisionF[0]) {
			assets[2] = createAsset(bookie_id, this.home, homeOdds, e_type);
		}
		if (decisionF[1]) {
			assets[2] = createAsset(bookie_id, this.draw, drawOdds, e_type);
		}
		if (decisionF[2]) {
			assets[2] = createAsset(bookie_id, this.away, awayOdds, e_type);
		}
		if (!decisionF[0] && !decisionF[1] && !decisionF[2]) {
			assets[2] = new Asset(this.game_id, this.model_id, bookie_id, 0, 0, e_type);
		}
		if (decisionE[0]) {
			assets[3] = createAsset(bookie_id, this.home, homeOdds, e_type);
		}
		if (decisionE[1]) {
			assets[3] = createAsset(bookie_id, this.draw, drawOdds, e_type);
		}
		if (decisionE[2]) {
			assets[3] = createAsset(bookie_id, this.away, awayOdds, e_type);
		}
		if (!decisionE[0] && !decisionE[1] && !decisionE[2]) {
			assets[3] = new Asset(this.game_id, this.model_id, bookie_id, 0, 0, e_type);
		}
		return assets;
	}

}