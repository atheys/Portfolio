package simulation.merge.strategy;

import java.util.ArrayList;
import simulation.simulate.Dividend;

public class WildTurtle extends Turtle {

	protected double factor;

	public WildTurtle(Dividend d, String model_id, double capital, double initial_amount, double costs, double dividend,
			double factor) {
		super(d, model_id, capital, initial_amount, costs, dividend);
		this.factor = factor;
		this.calculateProfits();
		this.calculateProfit();
	}


	private void calculateProfits() {
		this.profits = new ArrayList<Double>();
		this.dividends = new ArrayList<Double>();
		for (int i = 0; i < this.values.size(); i++) {
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

	public double getFactor() {
		return factor;
	}

	

}