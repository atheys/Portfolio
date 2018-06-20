package models.general.performance;

import java.util.ArrayList;
import exceptions.MatrixException;
import models.general.handlers.Weightor;

/**
 * Dynamic Matrix Class.
 * 
 * @author Andreas Theys.
 * @version 4.0
 */
public class DynamicMatrix {

	/**
	 * Class attributes.
	 */
	private boolean empty;
	private ArrayList<Performance> backbone;
	private double factor;
	private ArrayList<Performance> performances;
	private ArrayList<DWeight> weights;

	/**
	 * Default constructor.
	 */
	public DynamicMatrix() {
		this.empty = true;
	}

	/**
	 * General (List) constructor.
	 * 
	 * @param performances
	 *            list of performance measures.
	 * @throws MatrixException
	 */
	public DynamicMatrix(ArrayList<Performance> perf, double factor) throws Exception {
		this.backbone = perf;
		this.factor = factor;
		this.performances = new ArrayList<Performance>();
		for (Performance p : perf) {
			if (p.performance() > 0.)
				this.performances.add(p);
		}
		if (this.performances.size() == 0)
			throw new Exception("No decent Performance-Objects considered.");
		this.empty = false;
		int p_size = this.performances.size();
		double[] perform = new double[p_size];
		int under = 0;
		for (int i = 0; i < p_size; i++) {
			if (this.performances.get(i).performance() < this.factor) {
				under++;
			}
		}
		for (int i = 0; i < p_size; i++) {
			if ((double) under / p_size < 0.9 && this.performances.get(i).performance() < this.factor) {
				perform[i] = 0.;
			} else {
				perform[i] = Math.pow(this.performances.get(i).performance() / this.performances.get(0).performance(),
						Math.max((Math.log(p_size - under) / Math.log(1. / this.factor)),1.));
			}
		}
		double[] wM = Weightor.normalize(perform);
		this.weights = new ArrayList<DWeight>();
		DWeight dw;
		for (int i = 0; i < wM.length; i++) {
			dw = new DWeight(this.performances.get(i).getModel_id(), wM[i]);
			this.weights.add(dw);
		}
	}

	/**
	 * Empty evaluation getter.
	 * 
	 * @return empty matrix evaluation boolean.
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Backbone list getter.
	 * 
	 * @return backbone list of Performance-Object.
	 */
	public ArrayList<Performance> getBackbone() {
		return backbone;
	}

	/**
	 * Mathematically eligible Performance list getter.
	 * 
	 * @return mathematically eligible Performance list.
	 */
	public ArrayList<Performance> getPerformances() {
		return performances;
	}

	/**
	 * Mathematical dynamic weights getter.
	 * 
	 * @return mathematical dynamic weights.
	 */
	public ArrayList<DWeight> getWeights() {
		return weights;
	}

	/**
	 * Function to find decision weight.
	 * 
	 * @param model_id
	 *            Model ID feature to search for.
	 * @return corresponding weight value.
	 */
	public double findModel(String model_id) {
		for (DWeight dw : this.weights) {
			if (dw.getModel_id().equals(model_id))
				return dw.getWeight();
		}
		return 0.;
	}

	/**
	 * Makes uniform DynamicMatrix.
	 * 
	 * @param performances
	 *            list of relevant performances.
	 * @return uniform DynamicMatrix-Object.
	 */
	public static DynamicMatrix uniformMatrix(ArrayList<Performance> performances) {
		DynamicMatrix dm = new DynamicMatrix();
		dm.empty = false;
		dm.backbone = performances;
		dm.performances = performances;
		dm.factor = 1.;
		ArrayList<DWeight> dws = new ArrayList<DWeight>();
		for (Performance p : performances) {
			DWeight dw = new DWeight(p.getModel_id(), (double) 1 / performances.size());
			dws.add(dw);
		}
		dm.weights = dws;
		return dm;
	}

}