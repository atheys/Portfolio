package simulation.analyze.evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import data.core.books.Odds;
import data.core.paths.Path;
import data.core.structure.Game;
import models.general.performance.Prediction;
import models.variations.beta.BetaCreator;
import simulation.analyze.body.ModelCap;

/**
 * Game Evaluator Object Class.
 * 
 * @author Andreas Theys.
 * @version 6.0
 */
public class Evaluator {

	/**
	 * Class attributes.
	 */
	// General Features
	private Game game;
	private double var_importance;
	private ArrayList<Odds> odds;
	// Unit Models
	private ArrayList<ModelCap> unit_models;
	private ArrayList<Prediction> unit_predictions;
	// Super Models
	private ArrayList<ModelCap> super_models;
	private ArrayList<Prediction> super_predictions;
	// Final Models
	private ModelCap final_model;
	private Prediction final_prediction;

	/**
	 * General constructor.
	 * 
	 * @param game
	 *            Game-Object to evaluate for.
	 * @param importance
	 *            importance figure for variance calculations.
	 * @param type
	 *            evaluation type indicator.
	 */
	public Evaluator(Game game, double var_importance) {
		this.game = game;
		this.var_importance = var_importance;
		this.odds = game.getCompetition().findOdds(game);
		this.collectModels();
	}

	/**
	 * Collects all stored model data and categorizes based on model type.
	 */
	private void collectModels() {
		this.unit_models = new ArrayList<ModelCap>();
		String[] data = this.game.getId().split("-");
		String path = Path.results_mod + data[0] + "/" + data[1] + "/" + data[2] + "/" + this.game.getId() + ".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				String[] datum = line.split(",");
				String gameID = datum[1];
				if (datum[0].equals("Model") && gameID.equals(this.game.getId())) {
					String model_id = datum[2];
					double home = Double.parseDouble(datum[3]);
					double draw = Double.parseDouble(datum[4]);
					double away = Double.parseDouble(datum[5]);
					ModelCap mc = new ModelCap(model_id, gameID, home, draw, away);
					this.unit_models.add(mc);
				}
			}
			br.close();
		} catch (Exception e) {
			this.unit_models = BetaCreator.createBetaModels(this.game, BetaCreator.generationOneCorrelations(0.1), 5);
		}
		this.unitPredictions();
		return;
	}

	/**
	 * Function to dig for highest odds (used in prediction computations).
	 * 
	 * @return array containing highest odds.
	 */
	public double[] highestOdds() {
		double max_home = Double.MIN_VALUE;
		double max_draw = Double.MIN_VALUE;
		double max_away = Double.MIN_VALUE;
		for (Odds o : this.odds) {
			if (o.getWinHome() > max_home) {
				max_home = o.getWinHome();
			}
			if (o.getDraw() > max_draw) {
				max_draw = o.getDraw();
			}
			if (o.getWinAway() > max_away) {
				max_away = o.getWinAway();
			}
		}
		double[] highest_odds = { max_home, max_draw, max_away };
		return highest_odds;
	}

	/**
	 * Makes predictions for all Beta1-models.
	 */
	private void unitPredictions() {
		if (!this.game.isPlayed())
			return;
		this.unit_predictions = new ArrayList<Prediction>();
		double[] odds = this.highestOdds();
		for (ModelCap mc : this.unit_models) {
			if (mc.isRelevant()) {
				double max_M = Math.max(Math.max(mc.getHome(), mc.getDraw()), mc.getAway());
				boolean M_home = max_M == mc.getHome();
				boolean M_draw = max_M == mc.getDraw();
				boolean M_away = max_M == mc.getAway();
				double min_O = Math.min(Math.min(odds[0], odds[1]), odds[2]);
				boolean O_home = min_O == odds[0];
				boolean O_draw = min_O == odds[1];
				boolean O_away = min_O == odds[2];
				double E_home = mc.getHome() * odds[0] - 1;
				double E_draw = mc.getDraw() * odds[1] - 1;
				double E_away = mc.getAway() * odds[2] - 1;
				double max_F = Math.max(Math.max(E_home, E_draw), E_away);
				boolean F_home = max_F == E_home;
				boolean F_draw = max_F == E_draw;
				boolean F_away = max_F == E_away;
				double V_home = mc.getHome() * Math.pow(odds[0] - 1 - E_home, 2)
						+ (1 - mc.getHome()) * Math.pow(-1 - E_home, 2);
				double V_draw = mc.getDraw() * Math.pow(odds[1] - 1 - E_draw, 2)
						+ (1 - mc.getDraw()) * Math.pow(-1 - E_draw, 2);
				double V_away = mc.getAway() * Math.pow(odds[2] - 1 - E_away, 2)
						+ (1 - mc.getAway()) * Math.pow(-1 - E_away, 2);
				double Eff_home = E_home / Math.pow(V_home, this.var_importance);
				double Eff_draw = E_draw / Math.pow(V_draw, this.var_importance);
				double Eff_away = E_away / Math.pow(V_away, this.var_importance);
				double max_E = Math.max(Math.max(Eff_home, Eff_draw), Eff_away);
				boolean Ef_home = max_E == Eff_home;
				boolean Ef_draw = max_E == Eff_draw;
				boolean Ef_away = max_E == Eff_away;
				boolean win_Home = this.game.won(this.game.getHome());
				boolean draw = this.game.drew(this.game.getHome());
				boolean win_Away = this.game.won(this.game.getAway());
				boolean eval_M = (win_Home && M_home) || (draw && M_draw) || (win_Away && M_away);
				boolean eval_O = (win_Home && O_home) || (draw && O_draw) || (win_Away && O_away);
				boolean eval_F = (win_Home && F_home) || (draw && F_draw) || (win_Away && F_away);
				boolean eval_E = (win_Home && Ef_home) || (draw && Ef_draw) || (win_Away && Ef_away);
				Prediction p = new Prediction(this.game, mc.getModel_id());
				p.setEvaluation_M(eval_M);
				p.setEvaluation_O(eval_O);
				p.setEvaluation_F(eval_F);
				p.setEvaluation_E(eval_E);
				this.unit_predictions.add(p);
			}
		}
		return;
	}

	/**
	 * Makes super-predictions for all Beta1-super-models.
	 */
	private void superPredictions() {
		if (!this.game.isPlayed())
			return;
		this.super_predictions = new ArrayList<Prediction>();
		double[] odds = this.highestOdds();
		assert odds.length == 3;
		for (ModelCap mc : this.super_models) {
			if (mc.isRelevant()) {
				double max_M = Math.max(Math.max(mc.getHome(), mc.getDraw()), mc.getAway());
				boolean M_home = max_M == mc.getHome();
				boolean M_draw = max_M == mc.getDraw();
				boolean M_away = max_M == mc.getAway();
				double min_O = Math.min(Math.min(odds[0], odds[1]), odds[2]);
				boolean O_home = min_O == odds[0];
				boolean O_draw = min_O == odds[1];
				boolean O_away = min_O == odds[2];
				double E_home = mc.getHome() * odds[0] - 1;
				double E_draw = mc.getDraw() * odds[1] - 1;
				double E_away = mc.getAway() * odds[2] - 1;
				double max_F = Math.max(Math.max(E_home, E_draw), E_away);
				boolean F_home = max_F == E_home;
				boolean F_draw = max_F == E_draw;
				boolean F_away = max_F == E_away;
				double V_home = mc.getHome() * Math.pow(odds[0] - 1 - E_home, 2)
						+ (1 - mc.getHome()) * Math.pow(-1 - E_home, 2);
				double V_draw = mc.getDraw() * Math.pow(odds[1] - 1 - E_draw, 2)
						+ (1 - mc.getDraw()) * Math.pow(-1 - E_draw, 2);
				double V_away = mc.getAway() * Math.pow(odds[2] - 1 - E_away, 2)
						+ (1 - mc.getAway()) * Math.pow(-1 - E_away, 2);
				double Eff_home = E_home / Math.pow(V_home, this.var_importance);
				double Eff_draw = E_draw / Math.pow(V_draw, this.var_importance);
				double Eff_away = E_away / Math.pow(V_away, this.var_importance);
				double max_E = Math.max(Math.max(Eff_home, Eff_draw), Eff_away);
				boolean Ef_home = max_E == Eff_home;
				boolean Ef_draw = max_E == Eff_draw;
				boolean Ef_away = max_E == Eff_away;
				boolean win_Home = this.game.won(this.game.getHome());
				boolean draw = this.game.drew(this.game.getHome());
				boolean win_Away = this.game.won(this.game.getAway());
				boolean eval_M = (win_Home && M_home) || (draw && M_draw) || (win_Away && M_away);
				boolean eval_O = (win_Home && O_home) || (draw && O_draw) || (win_Away && O_away);
				boolean eval_F = (win_Home && F_home) || (draw && F_draw) || (win_Away && F_away);
				boolean eval_E = (win_Home && Ef_home) || (draw && Ef_draw) || (win_Away && Ef_away);
				Prediction p = new Prediction(this.game, mc.getModel_id());
				p.setEvaluation_M(eval_M);
				p.setEvaluation_O(eval_O);
				p.setEvaluation_F(eval_F);
				p.setEvaluation_E(eval_E);
				this.super_predictions.add(p);
			}
		}
		return;
	}

	/**
	 * Makes final predictions for all Beta1-models.
	 */
	private void finalPrediction() {
		if (!this.game.isPlayed())
			return;
		double[] odds = this.highestOdds();
		assert odds.length == 3;
		if (this.final_model.isRelevant()) {
			double max_M = Math.max(Math.max(this.final_model.getHome(), this.final_model.getDraw()),
					this.final_model.getAway());
			boolean M_home = max_M == this.final_model.getHome();
			boolean M_draw = max_M == this.final_model.getDraw();
			boolean M_away = max_M == this.final_model.getAway();
			double min_O = Math.min(Math.min(odds[0], odds[1]), odds[2]);
			boolean O_home = min_O == odds[0];
			boolean O_draw = min_O == odds[1];
			boolean O_away = min_O == odds[2];
			double E_home = this.final_model.getHome() * odds[0] - 1;
			double E_draw = this.final_model.getDraw() * odds[1] - 1;
			double E_away = this.final_model.getAway() * odds[2] - 1;
			double max_F = Math.max(Math.max(E_home, E_draw), E_away);
			boolean F_home = max_F == E_home;
			boolean F_draw = max_F == E_draw;
			boolean F_away = max_F == E_away;
			double V_home = this.final_model.getHome() * Math.pow(odds[0] - 1 - E_home, 2)
					+ (1 - this.final_model.getHome()) * Math.pow(-1 - E_home, 2);
			double V_draw = this.final_model.getDraw() * Math.pow(odds[1] - 1 - E_draw, 2)
					+ (1 - this.final_model.getDraw()) * Math.pow(-1 - E_draw, 2);
			double V_away = this.final_model.getAway() * Math.pow(odds[2] - 1 - E_away, 2)
					+ (1 - this.final_model.getAway()) * Math.pow(-1 - E_away, 2);
			double Eff_home = E_home / Math.pow(V_home, this.var_importance);
			double Eff_draw = E_draw / Math.pow(V_draw, this.var_importance);
			double Eff_away = E_away / Math.pow(V_away, this.var_importance);
			double max_E = Math.max(Math.max(Eff_home, Eff_draw), Eff_away);
			boolean Ef_home = max_E == Eff_home;
			boolean Ef_draw = max_E == Eff_draw;
			boolean Ef_away = max_E == Eff_away;
			boolean win_Home = this.game.won(this.game.getHome());
			boolean draw = this.game.drew(this.game.getHome());
			boolean win_Away = this.game.won(this.game.getAway());
			boolean eval_M = (win_Home && M_home) || (draw && M_draw) || (win_Away && M_away);
			boolean eval_O = (win_Home && O_home) || (draw && O_draw) || (win_Away && O_away);
			boolean eval_F = (win_Home && F_home) || (draw && F_draw) || (win_Away && F_away);
			boolean eval_E = (win_Home && Ef_home) || (draw && Ef_draw) || (win_Away && Ef_away);
			Prediction p = new Prediction(this.game, this.final_model.getModel_id());
			p.setEvaluation_M(eval_M);
			p.setEvaluation_O(eval_O);
			p.setEvaluation_F(eval_F);
			p.setEvaluation_E(eval_E);
			this.final_prediction = p;
		}
		return;
	}

	/**
	 * Game getter.
	 * 
	 * @return relevant Game-Object.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Variance importance getter.
	 * 
	 * @return variance importance.
	 */
	public double getVar_importance() {
		return var_importance;
	}

	/**
	 * Odds getter.
	 * 
	 * @return list of game odds.
	 */
	public ArrayList<Odds> getOdds() {
		return odds;
	}

	/**
	 * Unit models getter.
	 * 
	 * @return list of unit models.
	 */
	public ArrayList<ModelCap> getUnit_models() {
		return unit_models;
	}

	/**
	 * Unit models setter.
	 * 
	 * @param unit_models
	 *            new list of unit models.
	 */
	public void setUnit_models(ArrayList<ModelCap> unit_models) {
		this.unit_models = unit_models;
		this.unitPredictions();
	}

	/**
	 * Unit predictions getter.
	 * 
	 * @return unit predictions.
	 */
	public ArrayList<Prediction> getUnit_predictions() {
		return unit_predictions;
	}

	/**
	 * Super models getter.
	 * 
	 * @return list of super models.
	 */
	public ArrayList<ModelCap> getSuper_models() {
		return super_models;
	}

	/**
	 * Super models setter.
	 * 
	 * @param super_models
	 *            new list of super models.
	 */
	public void setSuper_models(ArrayList<ModelCap> super_models) {
		this.super_models = super_models;
		this.superPredictions();
	}

	/**
	 * Super predictions getter.
	 * 
	 * @return list of super predictions.
	 */
	public ArrayList<Prediction> getSuper_predictions() {
		return super_predictions;
	}

	/**
	 * Final model getter.
	 * 
	 * @return final model.
	 */
	public ModelCap getFinal_model() {
		return final_model;
	}

	/**
	 * Final model setter.
	 * 
	 * @param final_model
	 *            new final model.
	 */
	public void setFinal_model(ModelCap final_model) {
		this.final_model = final_model;
		this.finalPrediction();
	}

	/**
	 * Final prediction getter.
	 * 
	 * @return final prediction.
	 */
	public Prediction getFinal_prediction() {
		return final_prediction;
	}

	/**
	 * Finds ModelCap-Object based on a given model ID.
	 * 
	 * @param modelID
	 *            model ID feature.
	 * @return corresponding ModelCap-Object.
	 */
	public ModelCap findModel(String modelID) {
		String[] data = modelID.split("-");
		if (data.length > 2) {
			for (ModelCap mc : this.unit_models) {
				if (mc.getModel_id().equals(modelID)) {
					return mc;
				}
			}
		}
		if (data.length == 2) {
			for (ModelCap mc : this.super_models) {
				if (mc.getModel_id().equals(modelID)) {
					return mc;
				}
			}
		}
		if (data.length == 1) {
			if (this.final_model.getModel_id().equals(modelID)) {
				return this.final_model;
			}
		}
		return new ModelCap(modelID, this.game.getId(), 0., 0., 0.);
	}

	/**
	 * Finds Prediction-Object based on a given model ID.
	 * 
	 * @param modelID
	 *            model ID feature.
	 * @return corresponding Prediction-Object.
	 */
	public Prediction findPrediction(String modelID) {
		String[] data = modelID.split("-");
		if (data.length > 2) {
			for (Prediction p : this.unit_predictions) {
				if (p.getModel_id().equals(modelID)) {
					return p;
				}
			}
		}
		if (data.length == 2) {
			for (Prediction p : this.super_predictions) {
				if (p.getModel_id().equals(modelID)) {
					return p;
				}
			}
		}
		if (data.length == 1) {
			if (this.final_prediction.getModel_id().equals(modelID)) {
				return this.final_prediction;
			}
		}
		return new Prediction(this.game, modelID);
	}

	public void deleteModel(String modelID) {
		for (int i = this.unit_models.size() - 1; i >= 0; i--) {
			if (this.unit_models.get(i).getModel_id().equals(modelID)) {
				this.unit_models.remove(i);
			}
		}
	}

	public void deletePrediction(String modelID) {
		for (int i = this.unit_predictions.size() - 1; i >= 0; i--) {
			if (this.unit_predictions.get(i).getModel_id().equals(modelID)) {
				this.unit_predictions.remove(i);
			}
		}
	}

}