package simulation.merge.strategy;

import java.util.ArrayList;

import simulation.simulate.Dividend;

public class SmartTurtle extends WildTurtle {

	protected int contingency;

	public SmartTurtle(Dividend d, String model_id, double capital, double initial_amount, double costs,
			double dividend, double factor, int contingency) {
		super(d, model_id, capital, initial_amount, costs, dividend, factor);
		this.contingency = contingency;
		this.calculateProfits();
		this.calculateProfit();
	}
	

	protected double reinvestPerc() {
		double total = 0.;
		for (int i = 0; i < this.contingency; i++) {
			total += Math.pow(this.factor, i);
		}
		if (total > 0.)
			return (double) 1. / total;
		else
			return total;
	}

	protected void calculateProfits() {
		this.profits = new ArrayList<Double>();
		this.dividends = new ArrayList<Double>();
		for (int i = 0; i < this.values.size(); i++) {
			int power = power(i);
			if (power == 0) {
				this.initial_amount = this.initial_amount+reinvestPerc() * previousProfit(i);
			}
			double profit;
			if (this.values.get(i) > 0.) {
				profit = this.initial_amount * Math.pow(this.factor, power(i)) * this.values.get(i) * (1. - this.costs);
			} else {
				profit = this.initial_amount * Math.pow(this.factor, power(i)) * this.values.get(i);
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
