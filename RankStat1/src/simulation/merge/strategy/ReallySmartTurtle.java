package simulation.merge.strategy;


import simulation.simulate.Dividend;

public class ReallySmartTurtle extends SmartTurtle {

	public ReallySmartTurtle(Dividend d, String model_id, double capital, double initial_amount, double costs,
			double dividend, double factor, int contingency) {
		super(d, model_id, capital, initial_amount, costs, dividend, factor, contingency);
		this.initial_amount = this.reinvestPerc() * this.capital;
		this.calculateProfits();
		this.calculateProfit();
	}

}