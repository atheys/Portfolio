package simulation.merge.strategy;

import java.util.ArrayList;
import simulation.simulate.Dividend;

public class Turtle extends Strategy {

	protected double initial_amount;
	protected ArrayList<Double> profits;
	protected double costs;
	protected double dividend;
	protected ArrayList<Double> dividends;

	public Turtle(Dividend d, String model_id, double capital, double initial_amount, double costs, double dividend) {
		super(d, model_id, capital);
		this.initial_amount = initial_amount;
		this.costs = costs;
		this.dividend = dividend;
		this.calculateProfits();
		this.calculateProfit();
	}
	
	protected int power(int i) {
		int p = 0;
		while (i >= 1 && this.values.get(i - 1) < 0) {
			p++;
			i--;
		}
		return p;
	}
	
	protected double previousProfit(int i) {
		double profit = 0.;
		if (i > 0) {
			profit = this.profits.get(i - 1);
		}
		return profit;
	}

	private void calculateProfits() {
		this.profits = new ArrayList<Double>();
		this.dividends = new ArrayList<Double>();
		for (double d : this.values) {
			double profit;
			if (d > 0.) {
				profit = this.initial_amount * d * (1. - this.costs);
			} else {
				profit = this.initial_amount * d;
			}
			if (profit > 0.) {
				this.profits.add(profit * (1. - this.dividend));
				this.dividends.add(profit * this.dividend);
			} else {
				this.profits.add(profit);
				this.dividends.add(0.);
			}
		}
	}

	protected void calculateProfit() {
		this.profit = 0.;
		for (double d : this.profits) {
			this.profit += d;
		}
	}

	public double getInitial_amount() {
		return initial_amount;
	}

	public ArrayList<Double> getProfits() {
		return profits;
	}

	public double getCosts() {
		return costs;
	}

	public double getDividend() {
		return dividend;
	}

	public ArrayList<Double> getDividends() {
		return dividends;
	}
	
	
	

}
