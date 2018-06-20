package simulation.simulate;

import java.util.ArrayList;

import data.core.structure.Game;
import mathematics.portfolio.Asset;
import models.general.performance.Prediction;
import simulation.analyze.evaluation.Evaluator;
import simulation.merge.Merger;
import simulation.process.POptimizer;

/**
 * Dividend class for financial analyses.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Dividend {

	/**
	 * Class attributes.
	 */
	private Simulation simulation;
	private Merger[] mergers;
	private double invest;
	private double reinvest;
	private double dividend_return;
	private ArrayList<ArrayList<Double>> values;
	private ArrayList<ArrayList<Double>> dividends;

	/**
	 * General constructor.
	 * 
	 * @param simulation
	 *            Simulation-Object.
	 * @param initial_amount
	 *            initial value amount.
	 * @param reinvest
	 *            percentage of value to reinvest.
	 */
	public Dividend(Simulation simulation, double invest, double reinvest) {
		this.simulation = simulation;
		assert invest >= 0. && invest <= 1.;
		this.invest = invest;
		assert reinvest >= 0. && reinvest <= 1.;
		this.reinvest = reinvest;
		this.dividend_return = 1. - reinvest;
		this.values = new ArrayList<ArrayList<Double>>();
		this.dividends = new ArrayList<ArrayList<Double>>();
		for (ArrayList<POptimizer> po : this.simulation.getPortfolios()) {
			calculate(po);
		}
	}
	
	/**
	 * General constructor.
	 * 
	 * @param simulation
	 *            Simulation-Object.
	 * @param initial_amount
	 *            initial value amount.
	 * @param reinvest
	 *            percentage of value to reinvest.
	 */
	public Dividend(Merger[] mergers, double invest, double reinvest) {
		this.mergers = mergers;
		assert invest >= 0. && invest <= 1.;
		this.invest = invest;
		assert reinvest >= 0. && reinvest <= 1.;
		this.reinvest = reinvest;
		this.dividend_return = 1. - reinvest;
		this.values = new ArrayList<ArrayList<Double>>();
		this.dividends = new ArrayList<ArrayList<Double>>();
		for(Merger m: this.mergers){
			calculate2(m.getPo());
		}
	}

	private void calculate(ArrayList<POptimizer> portfolios) {
		ArrayList<Double> value = new ArrayList<Double>();
		ArrayList<Double> dividend = new ArrayList<Double>();
		for (POptimizer po : portfolios) {
			double leftover = (1 - this.invest);
			if (po.getPseudo_portfolio().getAssets().size() == 0) {
				value.add(0.);
				dividend.add(0.);

			} else {
				for (Asset a : po.getPseudo_portfolio().getAssets()) {
					double spend = this.invest * a.getStake();
					try {
						Evaluator e = this.simulation.findGame(a.getGame_id());
						Prediction p = e.findPrediction(a.getModel_id());
						if (p.isRelevant() && p.evaluate(a.getE_type())) {
							double[] h_odds = e.highestOdds();
							Game g = e.getGame();
							if (g.won(g.getHome())) {
								spend *= h_odds[0];
							}
							if (g.drew(g.getHome())) {
								spend *= h_odds[1];
							}
							if (g.won(g.getAway())) {
								spend *= h_odds[2];
							}
							leftover += spend;
						}
					} catch (Exception e) {
						// Logger capacity
					}
				}
				if (leftover > 1.) {
					double profit = leftover - 1.;
					value.add(profit);
					dividend.add(this.dividend_return * profit);
				} else {
					value.add(leftover-1.);
					dividend.add(0.);
				}
			}
		}
		this.dividends.add(dividend);
		this.values.add(value);
	}
	
	private void calculate2(ArrayList<POptimizer> portfolios) {
		ArrayList<Double> value = new ArrayList<Double>();
		ArrayList<Double> dividend = new ArrayList<Double>();
		for (POptimizer po : portfolios) {
			double leftover = (1 - this.invest);
			if (po.getPseudo_portfolio().getAssets().size() == 0) {
				value.add(0.);
				dividend.add(0.);

			} else {
				for (Asset a : po.getPseudo_portfolio().getAssets()) {
					double spend = this.invest * a.getStake();
					spend *= a.getWon();
					leftover += spend;
		
				}
				if (leftover > 1.) {
					double profit = leftover - 1.;
					value.add(profit);
					dividend.add(this.dividend_return * profit);
				} else {
					value.add(leftover-1.);
					dividend.add(0.);
				}
			}
		}
		this.dividends.add(dividend);
		this.values.add(value);
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public Merger[] getMergers() {
		return mergers;
	}

	public double getInvest() {
		return invest;
	}

	public double getReinvest() {
		return reinvest;
	}

	public double getDividend_return() {
		return dividend_return;
	}

	public ArrayList<ArrayList<Double>> getValues() {
		return values;
	}

	public ArrayList<ArrayList<Double>> getDividends() {
		return dividends;
	}
	
}
