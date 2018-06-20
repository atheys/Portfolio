package models.general.performance;

import java.util.ArrayList;
import data.core.structure.GameDay;
import exceptions.PerformanceException;

/**
 * Model Performance Class.
 * 
 * @author Andreas Theys.
 * @version 4.0
 */
public class Performance {

	/**
	 * Class attributes.
	 */
	// General features
	private String model_id;
	private String type;
	private GameDay earliest;
	private GameDay latest;
	private Prediction[] predictions;
	// Performance parameters
	private int nPred;
	private boolean eligible;
	private int index;
	private boolean[] perf;

	/**
	 * Copy constructor.
	 * 
	 * @param p
	 *            Performance-Object to copy.
	 */
	public Performance(Performance p) {
		this.model_id = p.model_id;
		this.type = p.type;
		this.earliest = p.earliest;
		this.latest = p.latest;
		this.predictions = p.predictions;
		this.index = p.index;
		this.nPred = p.nPred;
		this.eligible = p.eligible;
		this.index = p.index;
		this.perf = p.perf;
	}

	/**
	 * List constructor.
	 * 
	 * @param predictions
	 *            list of Prediction-Objects.
	 * @param max_stack
	 *            maximum stack size number.
	 * @note
	 * @throws PerformanceException
	 */
	public Performance(ArrayList<Prediction> predictions, int max_stack, String type) throws PerformanceException {
		boolean check = true;
		this.earliest = new GameDay(1, 1, 2100);
		this.latest = new GameDay(1, 1, 1900);
		for (int i = 0; i < predictions.size(); i++) {
			for (int j = 0; j < i; j++) {
				check = check && predictions.get(i).getModel_id().equals(predictions.get(j).getModel_id());
			}
		}
		if (check && relevantPredictions(predictions) > 0) {
			int n = Math.max(relevantPredictions(predictions), max_stack);
			this.model_id = predictions.get(0).getModel_id();
			this.nPred = n;
			this.index = 0;
			this.predictions = new Prediction[n];
			this.perf = new boolean[n];
			for (Prediction p : predictions) {
				this.update(p, type);
			}
			if (this.index == 0)
				this.eligible = true;
			return;
		}
		else {
			throw new PerformanceException("List of predictions not valid.");
		}
	}

	/**
	 * Determines number of all relevant Prediction-Objects.
	 * 
	 * @param predictions
	 *            Prediction-Object list.
	 * @return number of relevant Prediction-Objects.
	 */
	private int relevantPredictions(ArrayList<Prediction> predictions) {
		int result = 0;
		for (Prediction p : predictions) {
			if (p.isRelevant()) {
				result++;
			}
		}
		return result;
	}

	/**
	 * Updates for a particular Prediction-Object.
	 * 
	 * @param p
	 *            Prediction-Object to update for.
	 */
	public void update(Prediction p, String type) {
		if (p.isRelevant() && p.getModel_id().equals(this.model_id)) {
			this.updatePredictions(p, type);
		}
	}

	/**
	 * Updates predictions list with all possible prediction features.
	 * 
	 * @param p
	 *            Prediction-Object to update for.
	 */
	private void updatePredictions(Prediction p, String type) {
		this.predictions[this.index] = p;
		GameDay date = p.getGame().getGameDay();
		if (date.before(this.earliest))
			this.earliest = date;
		if (date.after(this.latest))
			this.latest = date;
		switch (type) {
		case "M":
			this.update_M(p.isEvaluation_M());
			break;
		case "O":
			this.update_O(p.isEvaluation_O());
			break;
		case "F":
			this.update_F(p.isEvaluation_F());
			break;
		case "E":
			this.update_E(p.isEvaluation_E());
			break;
		default:
			break;
		}
		this.index = (this.index + 1) % this.predictions.length;
	}

	/**
	 * Update mathematical performance stack.
	 * 
	 * @param M
	 *            mathematical performance evaluation.
	 */
	private void update_M(boolean M) {
		this.perf[this.index] = M;
	}

	/**
	 * Update odds-based performance stack.
	 * 
	 * @param O
	 *            odds-based performance evaluation.
	 */
	private void update_O(boolean O) {
		this.perf[this.index] = O;
	}

	/**
	 * Update financial performance stack.
	 * 
	 * @param F
	 *            financial performance evaluation.
	 */
	private void update_F(boolean F) {
		this.perf[this.index] = F;
	}

	/**
	 * Update financial performance stack.
	 * 
	 * @param F
	 *            financial performance evaluation.
	 */
	private void update_E(boolean E) {
		this.perf[this.index] = E;
	}

	/**
	 * Model ID getter.
	 * 
	 * @return Model ID.
	 */
	public String getModel_id() {
		return model_id;
	}

	/**
	 * Earliest GameDay getter.
	 * 
	 * @return earliest GameDay-Object.
	 */
	public GameDay getEarliest() {
		return earliest;
	}

	/**
	 * Latest GameDay getter.
	 * 
	 * @return latest GameDay-Object.
	 */
	public GameDay getLatest() {
		return latest;
	}

	/**
	 * Determines mathematical performance features.
	 * 
	 * @return mathematical performance feature.
	 */
	public double performance() {
		if (!this.eligible)
			return 0.;
		int success = 0;
		for (boolean b : this.perf) {
			if (b) {
				success++;
			}
		}
		return (double) success / this.nPred;
	}

}