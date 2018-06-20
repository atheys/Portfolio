package simulation.merge.strategy;

import java.util.ArrayList;

import simulation.merge.Merger;
import simulation.process.POptimizer;
import simulation.simulate.Dividend;


public class Strategy {

	
	protected Dividend d;
	protected String model_id;
	protected double capital;
	protected double profit;
	protected ArrayList<Double> values;

	
	public Strategy(Dividend d, String model_id, double capital) {
		this.d = d;
		this.model_id = model_id;
		this.capital = capital;
		this.profit = 0.;
		this.values = this.findValues(this.model_id);
	}

	
	private ArrayList<Double> findValues(String model_id) {
		ArrayList<Double> values = new ArrayList<Double>();
		if(this.d.getSimulation() != null){
			for(int i=0; i< this.d.getSimulation().getPortfolios().size(); i++){
				ArrayList<POptimizer> po = this.d.getSimulation().getPortfolios().get(i);
				String id = new String();
				try{
					id = po.get(0).getPortfolio().getId();
				} catch(Exception e){
					// Logger capacity
				}
				if(id.equals(model_id)){
					values = this.d.getValues().get(i);
				}
				
			}
		}
		if(this.d.getMergers() != null){
			for(int i=0; i<this.d.getMergers().length;i++){
				Merger m = this.d.getMergers()[i];
				if(m.getModel_type().equals(model_id)){
					values = this.d.getValues().get(i);
				}
			}
		}
		if(values.size() == 0){
			// Logger
		}
		return values;
	}


	public Dividend getD() {
		return d;
	}


	public String getModel_id() {
		return model_id;
	}


	public double getCapital() {
		return capital;
	}


	public double getProfit() {
		return profit;
	}


	public ArrayList<Double> getValues() {
		return values;
	}

	

	
	

}