package simulation.merge.strategy;

import java.util.ArrayList;

import simulation.simulate.Dividend;

public class SafeTurtle extends Turtle {

	protected int contingency;

	public SafeTurtle(Dividend d, String model_id, double capital, double initial_amount, double costs, double dividend,
			int contingency) {
		super(d, model_id, capital, initial_amount, costs, dividend);
		this.contingency = contingency;
		this.calculateProfits();
		this.calculateProfit();
	}

	protected double reinvestPerc() {
		if (this.contingency > 0.)
			return (double) 1 / this.contingency;
		return 0.;
	}

	protected void calculateProfits() {
		this.profits = new ArrayList<Double>();
		this.dividends = new ArrayList<Double>();
		for (int i = 0; i < this.values.size(); i++) {
			int power = power(i);
			if (power == 0) {
				this.initial_amount = this.initial_amount + this.reinvestPerc() * previousProfit(i);
			}
			double profit;
			if (this.values.get(i) > 0.) {
				profit = this.initial_amount * this.values.get(i) * (1. - this.costs);
			} else {
				profit = this.initial_amount * this.values.get(i);
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

	public int getContingency() {
		return contingency;
	}
	
	

}
