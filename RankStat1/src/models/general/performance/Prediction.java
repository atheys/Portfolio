package models.general.performance;

import data.core.structure.Game;

/**
 * Prediction class for evaluation purposes.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class Prediction {

	/**
	 * Class attributes.
	 */
	private boolean relevant;
	private Game game;
	private String model_id;
	private boolean evaluation_M;
	private boolean evaluation_O;
	private boolean evaluation_F;
	private boolean evaluation_E;

	/**
	 * Default constructor.
	 */
	public Prediction() {
		this.relevant = false;
	}

	/**
	 * General constructor.
	 * 
	 * @param game
	 *            relevant Game-Object.
	 * @param model_id
	 *            model ID feature.
	 */
	public Prediction(Game game, String model_id) {
		this.relevant = true;
		this.game = game;
		this.model_id = model_id;
		this.evaluation_M = false;
		this.evaluation_O = false;
		this.evaluation_F = false;
		this.evaluation_E = false;
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
	 * Game getter.
	 * 
	 * @return corresponding Game-Object.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Game setter.
	 * 
	 * @param game
	 *            new Game-Object.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Model ID getter.
	 * 
	 * @return model ID feature.
	 */
	public String getModel_id() {
		return model_id;
	}

	/**
	 * M-evaluation getter.
	 * 
	 * @return mathematical evaluation.
	 */
	public boolean isEvaluation_M() {
		return evaluation_M;
	}

	/**
	 * M-evaluation setter.
	 * 
	 * @param evaluation_M
	 *            new mathematical evaluation.
	 */
	public void setEvaluation_M(boolean evaluation_M) {
		this.evaluation_M = evaluation_M;
	}

	/**
	 * O-evaluation getter.
	 * 
	 * @return odds-based evaluation.
	 */
	public boolean isEvaluation_O() {
		return evaluation_O;
	}

	/**
	 * O-evaluation setter.
	 * 
	 * @param evaluation_O
	 *            new odds-based evaluation.
	 */
	public void setEvaluation_O(boolean evaluation_O) {
		this.evaluation_O = evaluation_O;
	}

	/**
	 * F-evaluation getter.
	 * 
	 * @return financial evaluation.
	 */
	public boolean isEvaluation_F() {
		return evaluation_F;
	}

	/**
	 * F-evaluation setter.
	 * 
	 * @param evaluation_F
	 *            new financial evaluation.
	 */
	public void setEvaluation_F(boolean evaluation_F) {
		this.evaluation_F = evaluation_F;
	}

	/**
	 * E-evaluation getter.
	 * 
	 * @return efficient evaluation.
	 */
	public boolean isEvaluation_E() {
		return evaluation_E;
	}

	/**
	 * E-evaluation setter.
	 * 
	 * @param evaluation_E
	 *            new efficient evaluation.
	 */
	public void setEvaluation_E(boolean evaluation_E) {
		this.evaluation_E = evaluation_E;
	}

	/**
	 * Provides evaluation features for a given evaluation type.
	 * 
	 * @param e_type
	 *            evaluation type indicator.
	 * @return evaluation feature.
	 */
	public boolean evaluate(String e_type) {
		switch (e_type) {
		case "M":
			return this.isEvaluation_M();
		case "O":
			return this.isEvaluation_O();
		case "F":
			return this.isEvaluation_F();
		case "E":
			return this.isEvaluation_E();
		default:
			break;
		}
		return false;
	}

}