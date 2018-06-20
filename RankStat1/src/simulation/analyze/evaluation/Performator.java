package simulation.analyze.evaluation;

import java.util.ArrayList;
import data.core.structure.Game;
import exceptions.MatrixException;
import models.general.performance.DynamicMatrix;
import models.general.performance.Performance;
import models.general.performance.Prediction;
import simulation.analyze.body.ModelCap;

/**
 * Performator Class for performance scheme development.
 * 
 * @author Andreas Theys.
 * @version 9.0
 */
public class Performator {

	/**
	 * Collects all unit Prediction-Objects.
	 * 
	 * @param e
	 *            respective Evaluator-Object.
	 * @return unit models list.
	 */
	private static ArrayList<ModelCap> collectModels(Evaluator e) {
		return e.getUnit_models();
	}

	/**
	 * Collects all unit Prediction-Objects.
	 * 
	 * @param e
	 *            respective Evaluator-Object.
	 * @return unit predictions list.
	 */
	private static ArrayList<Prediction> collectPredictions(Evaluator e) {
		return e.getUnit_predictions();
	}

	/**
	 * Collects all unit Prediction-Objects.
	 * 
	 * @param e
	 *            respective Evaluator-Object.
	 * @return super models list.
	 */
	private static ArrayList<ModelCap> collectSuperModels(Evaluator e) {
		return e.getSuper_models();
	}

	/**
	 * Collects all super Prediction-Objects.
	 * 
	 * @param e
	 *            respective Evaluator-Object.
	 * @return super predictions list.
	 */
	private static ArrayList<Prediction> collectSuperPredictions(Evaluator e) {
		return e.getSuper_predictions();
	}

	/**
	 * Collects all unit Prediction-Objects.
	 * 
	 * @param e
	 *            respective Evaluator-Object list.
	 * @return raw nested unit predictions list.
	 */
	private static ArrayList<ArrayList<Prediction>> collectPredictions(ArrayList<Evaluator> e) {
		ArrayList<ArrayList<Prediction>> predictions = new ArrayList<ArrayList<Prediction>>();
		for (Evaluator eval : e) {
			predictions.add(collectPredictions(eval));
		}
		return predictions;
	}

	/**
	 * Collects all super Prediction-Objects.
	 * 
	 * @param e
	 *            respective Evaluator-Object list.
	 * @return raw nested super predictions list.
	 */
	private static ArrayList<ArrayList<Prediction>> collectSuperPredictions(ArrayList<Evaluator> e) {
		ArrayList<ArrayList<Prediction>> predictions = new ArrayList<ArrayList<Prediction>>();
		for (Evaluator eval : e) {
			predictions.add(collectSuperPredictions(eval));
		}
		return predictions;
	}

	/**
	 * Gathers all model relevant model IDs.
	 * 
	 * @param preds
	 *            nested Prediction-list structure.
	 * @return list of all relevant model IDs.
	 */
	private static ArrayList<String> IDs(ArrayList<ArrayList<Prediction>> preds) {
		ArrayList<String> ids = new ArrayList<String>();
		for (ArrayList<Prediction> pred : preds) {
			for (Prediction p : pred) {
				if (!ids.contains(p.getModel_id())) {
					ids.add(p.getModel_id());
				}
			}
		}
		return ids;
	}

	/**
	 * Arranges and organizes all Prediction-Object by ID feature.
	 * 
	 * @return nested (and ordered) list structure of Prediction-Objects.
	 */
	private static ArrayList<ArrayList<Prediction>> arrange(ArrayList<ArrayList<Prediction>> preds) {
		ArrayList<String> ids = IDs(preds);
		ArrayList<ArrayList<Prediction>> predictions = new ArrayList<ArrayList<Prediction>>();
		for (int i = 0; i < ids.size(); i++) {
			predictions.add(new ArrayList<Prediction>());
		}
		for (ArrayList<Prediction> pred : preds) {
			for (Prediction p : pred) {
				int index = ids.indexOf(p.getModel_id());
				if (index > -1)
					predictions.get(index).add(p);
			}
		}
		return predictions;
	}

	/**
	 * Collects and arranges all Prediction-Objects related to one particular
	 * list of Evaluators.
	 * 
	 * @param evals
	 *            list of relevant Evaluator-Objects.
	 * @return corresponding nested list structure of Prediction-Objects.
	 */
	private static ArrayList<ArrayList<Prediction>> collect(ArrayList<Evaluator> evals) {
		return arrange(collectPredictions(evals));
	}

	/**
	 * Collects and arranges all super-Prediction-Objects related to one
	 * particular list of Evaluators.
	 * 
	 * @param evals
	 *            list of relevant Evaluator-Objects.
	 * @param model_type
	 *            model type indicator.
	 * @return corresponding nested list structure of Prediction-Objects.
	 */
	private static ArrayList<ArrayList<Prediction>> collectSuper(ArrayList<Evaluator> evals) {
		return arrange(collectSuperPredictions(evals));
	}

	/**
	 * Makes DynamicMatrix-entity.
	 * 
	 * @param preds
	 *            organized list of Prediction-Objects.
	 * @param e_type
	 *            evaluation type indicator.
	 * @param factor
	 *            decision weight factor.
	 * @return corresponding DynamicMatrix-Object.
	 * @throws MatrixException
	 */
	private static DynamicMatrix makeMatrix(ArrayList<ArrayList<Prediction>> preds, String e_type) throws Exception {
		ArrayList<Performance> perf = new ArrayList<Performance>();
		for (ArrayList<Prediction> pred : preds) {
			if (pred.size() > 0) {
				try {
					Performance p = new Performance(pred, 0, e_type);
					perf.add(p);
				} catch (Exception E) {
					// Logger capacity
				}
			}
		}
		return new DynamicMatrix(perf, 0.33333);
	}

	/**
	 * Gathers all models that backtrack n games.
	 * 
	 * @param preds
	 *            organized and nested list structure of Prediction-Objects.
	 * @param n
	 *            backtracked games number.
	 * @return corresponding nested Prediction-list structure.
	 */
	private static ArrayList<ArrayList<ArrayList<Prediction>>> gatherN(ArrayList<ArrayList<Prediction>> preds,
			int n_max) {
		ArrayList<ArrayList<ArrayList<Prediction>>> pred = new ArrayList<ArrayList<ArrayList<Prediction>>>();
		for (int i = 0; i < n_max; i++) {
			pred.add(new ArrayList<ArrayList<Prediction>>());
		}
		for (ArrayList<Prediction> p : preds) {
			if (p.size() > 0) {
				int n = Integer.parseInt(p.get(0).getModel_id().split("-")[1]);
				if (0 < n && n <= n_max) {
					pred.get(n - 1).add(p);
				}
			}
		}
		return pred;
	}

	/**
	 * Creates new (pseudo-)ModelCap-Object.
	 * 
	 * @param model
	 *            list of relevant ModelCap-Objects.
	 * @param dm
	 *            Dynamic-Matrix entity for evaluation weight purposes.
	 * @return corresponding ModelCap-Object.
	 */
	private static ModelCap makeModel(String modelID, String gameID, ArrayList<ModelCap> models, DynamicMatrix dm) {
		double H = 0.;
		double D = 0.;
		double A = 0.;
		double W = 0.;
		for (ModelCap mc : models) {
			double weight = dm.findModel(mc.getModel_id());
			if (weight > 0.) {
				H += weight * mc.getHome();
				D += weight * mc.getDraw();
				A += weight * mc.getAway();
				W += weight;
			}
		}
		if (W > 0.)
			return new ModelCap(modelID, gameID, H / W, D / W, A / W);
		else
			return new ModelCap(modelID, gameID, 0., 0., 0.);
	}

	/**
	 * Appends super-ModelCap-list to a certain Evaluator-Object.
	 * 
	 * @param e
	 *            relevant Evaluator-Object.
	 * @param model_type
	 *            model type indicator.
	 * @param models
	 *            ModelCap-list structure to append.
	 */
	private static void appendSuper(Evaluator e, ArrayList<ModelCap> models) {
		e.setSuper_models(models);
	}

	/**
	 * Appends final-ModelCap-list to a certain Evaluator-Object.
	 * 
	 * @param e
	 *            relevant Evaluator-Object.
	 * @param model_type
	 *            model type indicator.
	 * @param models
	 *            ModelCap-list structure to append.
	 */
	private static void appendFinal(Evaluator e, ModelCap mc) {
		e.setFinal_model(mc);
	}

	/**
	 * Makes super-ModelCap-Objects for a certain Evaluator-Object.
	 * 
	 * @param e
	 *            relevant Evaluator-Object.
	 * @param model_type
	 *            model type indicator.
	 * @param ints
	 *            relevant super-model backtrack numbers.
	 * @param dms
	 *            corresponding DynamicMatrix-Object.
	 */
	private static void makeSuperModels(Evaluator e, ArrayList<Integer> ints, ArrayList<DynamicMatrix> dms) {
		ArrayList<ModelCap> mcs = new ArrayList<ModelCap>();
		ArrayList<ModelCap> models = collectModels(e);
		Game g = e.getGame();
		for (int i = 0; i < ints.size(); i++) {
			String id = "B-" + ints.get(i);
			ModelCap mc = makeModel(id, g.getId(), models, dms.get(i));
			if (Math.abs(1 - (mc.getHome() + mc.getDraw() + mc.getAway())) < 0.02) {
				mcs.add(mc);
			}
		}
		appendSuper(e, mcs);
	}

	/**
	 * Creates final ModelCap-Objects for a certain Evaluator-Object.
	 * 
	 * @param e
	 *            relevant Evaluator-Object.
	 * @param model_type
	 *            model type indicator.
	 * @param dm
	 *            relevant DynamicMatrix-Object.
	 */
	private static void makeFinalModel(Evaluator e, DynamicMatrix dm) {
		ArrayList<ModelCap> models = collectSuperModels(e);
		Game g = e.getGame();
		ModelCap mc = makeModel("B", g.getId(), models, dm);
		appendFinal(e, mc);
	}

	/**
	 * Makes list of all relevant DynamicMatrix-Object for super-model creation.
	 * 
	 * @param preds
	 *            nested Prediction-list structure.
	 * @param n_max
	 *            maximum backtrack number for super-model creation.
	 * @param e_type
	 *            evluation type indicator.
	 * @param factor
	 *            decision power factor.
	 * @param ints
	 *            relevant super-model numbers.
	 * @return corresponding list of DynamicMatrix-Objects.
	 */
	private static ArrayList<DynamicMatrix> makeMatrices(ArrayList<ArrayList<Prediction>> preds, int n_max,
			String e_type, ArrayList<Integer> ints) {
		ArrayList<DynamicMatrix> matrices = new ArrayList<DynamicMatrix>();
		ArrayList<ArrayList<ArrayList<Prediction>>> p = gatherN(preds, n_max);
		for (int i = 1; i <= n_max; i++) {
			ArrayList<ArrayList<Prediction>> p_i = p.get(i - 1);
			if (p_i.size() > 0) {
				try {
					DynamicMatrix dm = makeMatrix(p_i, e_type);
					matrices.add(dm);
					ints.add(i);
				} catch (Exception e) {
					// Logger capacity
				}
			}
		}
		return matrices;
	}

	/**
	 * Converts nested (and sorted) Prediction-list into a corresponding
	 * Performance-Object list.
	 * 
	 * @param preds
	 *            nested Prediction-list structure.
	 * @param e_type
	 *            evaluation type-indicator.
	 * @return corresponding list of Performance-Objects.
	 */
	private static ArrayList<Performance> convert(ArrayList<ArrayList<Prediction>> preds, String e_type) {
		ArrayList<Performance> perf = new ArrayList<Performance>();
		for (ArrayList<Prediction> p : preds) {
			try {
				Performance pf = new Performance(p, 0, e_type);
				perf.add(pf);
			} catch (Exception e) {
				// Logger capacity
			}
		}
		return perf;
	}

	/**
	 * Initiates super-models before maximal backtrack-number has been reached.
	 * 
	 * @param es
	 *            relevant list of Evaluator-Objects.
	 * @param model_type
	 *            model type indicator.
	 * @param e_type
	 *            evaluation type indicator.
	 * @param n_max
	 *            maximum backtrack number.
	 */
	private static void initiateSuper(ArrayList<Evaluator> evals, String e_type, int n_max) {
		ArrayList<ArrayList<Prediction>> preds = collect(evals);
		ArrayList<DynamicMatrix> dms = new ArrayList<DynamicMatrix>();
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ArrayList<ArrayList<ArrayList<Prediction>>> p = gatherN(preds, n_max);
		for (int i = 1; i <= n_max; i++) {
			ArrayList<ArrayList<Prediction>> p_i = p.get(i - 1);
			if (p_i.size() > 0) {
				ArrayList<Performance> perfs = convert(p_i, e_type);
				DynamicMatrix dm = DynamicMatrix.uniformMatrix(perfs);
				dms.add(dm);
				ints.add(i);
			}
		}
		for (Evaluator e : evals) {
			makeSuperModels(e, ints, dms);
		}
	}

	/**
	 * Initiates final models before maximal backtrack-number has been reached.
	 * 
	 * @param es
	 *            relevant list of Evaluator-Objects.
	 * @param model_type
	 *            model type indicator.
	 * @param e_type
	 *            evaluation type indicator.
	 */
	private static void initiateFinal(ArrayList<Evaluator> es, String e_type) {
		ArrayList<ArrayList<Prediction>> preds = collectSuper(es);
		ArrayList<Performance> perfs = new ArrayList<Performance>();
		for (ArrayList<Prediction> p : preds) {
			try {
				Performance pf = new Performance(p, 0, e_type);
				perfs.add(pf);
			} catch (Exception e) {
				// Logger capacity
			}
		}
		DynamicMatrix dm = DynamicMatrix.uniformMatrix(perfs);
		for (Evaluator e : es) {
			makeFinalModel(e, dm);
		}
	}

	/**
	 * Merges the indicated part of one nested Evaluator-list into one
	 * Evaluator-list entity.
	 * 
	 * @param evals
	 *            relevant nest Evaluator-list.
	 * @param begin
	 *            begin index
	 * @param limit
	 *            limitation index (not included)
	 * @return corresponding Evaluator-list.
	 */
	private static ArrayList<Evaluator> merge(ArrayList<ArrayList<Evaluator>> evals, int begin, int limit) {
		ArrayList<Evaluator> e = new ArrayList<Evaluator>();
		for (int i = begin; i < Math.min(limit, evals.size()); i++) {
			e.addAll(evals.get(i));
		}
		return e;
	}

	/**
	 * Assembles (and merges) the appropriate Evaluator-list Objects.
	 * 
	 * @param evals
	 *            nested Evaluator-list object.
	 * @param index
	 *            current list index.
	 * @param backtrack
	 *            number of indices to backtrack for.
	 * @return corresponding Evaluator-list.
	 */
	private static ArrayList<Evaluator> assemble(ArrayList<ArrayList<Evaluator>> evals, int index, int backtrack) {
		return merge(evals, Math.max(0, index - backtrack), index);
	}

	/**
	 * Initiation sequence for a given backtrack number of dynamic performance.
	 * 
	 * @param evals
	 *            nested Evaluator-list structure.
	 * @param backtrack
	 *            relevant backtrack number.
	 * @param n_max
	 *            maximum model backtrack number.
	 * @param e_type
	 *            evaluation type indicator.
	 */
	private static void INITIATE(ArrayList<ArrayList<Evaluator>> evals, int backtrack, int n_max, String e_type) {
		ArrayList<Evaluator> es = merge(evals, 0, backtrack);
		initiateSuper(es, e_type, n_max);
		initiateSuper(es, e_type, n_max);
		initiateFinal(es, e_type);
		initiateFinal(es, e_type);
	}

	/**
	 * Creates dynamic performance based super- and final models.
	 * 
	 * @param historic
	 *            past Evaluator-Object list (reference list).
	 * @param current
	 *            current Evaluator-Object list to make super- and final models
	 *            for.
	 * @param model_type
	 *            model type indicator.
	 * @param n_max
	 *            maximum model backtrack number.
	 * @param e_type
	 *            evaluator type indicator.
	 * @param factor
	 *            decision power factor.
	 */
	private static void CREATE(ArrayList<Evaluator> historic, ArrayList<Evaluator> current, int n_max, String e_type) {
		ArrayList<ArrayList<Prediction>> predictions = collect(historic);
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ArrayList<DynamicMatrix> dms = makeMatrices(predictions, n_max, e_type, ints);
		for (Evaluator e : current) {
			makeSuperModels(e, ints, dms);
		}
		predictions = collectSuper(historic);
		try {
			DynamicMatrix dm = makeMatrix(predictions, e_type);
			for (Evaluator e : current) {
				makeFinalModel(e, dm);
			}
		} catch (Exception e) {
			// Logger capacity
		}
	}

	/**
	 * Computes dynamic performance.
	 * 
	 * @param dynamic_perf
	 *            dynamic performance indicator.
	 * @param evals
	 *            nested Evaluator-list.
	 * @param backtrack
	 *            periodical backtrack number.
	 * @param n_max
	 *            maximum model backtrack number.
	 * @param e_type
	 *            evaluation type indicator.
	 * @param factor
	 *            decision factor.
	 * @param B1
	 *            B1-models indicator.
	 * @param B3
	 *            B3-models indicator.
	 */
	public static void PERFORM(ArrayList<ArrayList<Evaluator>> evals, int backtrack, int n_max, String e_type) {
		if (evals.size() > backtrack) {
			INITIATE(evals, backtrack, n_max, e_type);
		}
		for (int i = backtrack; i < evals.size(); i++) {
			System.out.print(i + " ");
			ArrayList<Evaluator> historic = assemble(evals, i, backtrack);
			CREATE(historic, evals.get(i), n_max, e_type);

		}
		System.out.println();
	}
}