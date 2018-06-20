package simulation.merge.strategy;

import simulation.simulate.Dividend;

public class ReallySafeTurtle extends SafeTurtle {

	public ReallySafeTurtle(Dividend d, String model_id, double capital, double initial_amount, double costs, double dividend,
			int contingency) {
		super(d, model_id, capital, initial_amount, costs, dividend, contingency);
		this.initial_amount = this.capital*this.reinvestPerc();
		this.calculateProfits();
		this.calculateProfit();
	}
	
}
