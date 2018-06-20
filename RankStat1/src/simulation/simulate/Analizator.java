package simulation.simulate;

import report.engine.ReportEngine;
import simulation.except.Except;

public class Analizator {

	private Simulation s;
	private static final boolean[] optimize = { false };
	private static final double[] var = { 0.05, 0.1, 0.15, 0.2 };
	private static final double[] returns = { 0.5, 0.6, 0.7, 0.8 };

	public Analizator(Simulation s, double version, int generation) {
		this.s = s;
		if (s.isExecuted()) {
			if (s.getEvaluation_type().equals("E")) {
				this.sequence_1(version, generation);
			} else {
				this.sequence_2(version, generation);
			}
		}
	}

	private void sequence_1(double version, int generation) {
		for (boolean b : this.optimize) {
			for (double d : this.returns) {
				s.setPort_optimization(b);
				s.setMin_return(d);
				s.createSerialNumber(true);
				s.makePortfolios();
				Dividend D = new Dividend(s, 1., 1.);
				ReportEngine re = new ReportEngine(s, D, version, generation);
				try {
					re.writeSimFiles();
					System.out.println("Reports Written.");
				} catch (Exception e) {
				}
			}
		}
	}

	private void sequence_2(double version, int generation) {
		for (boolean b : this.optimize) {
			for (double d1 : this.var) {
				for (double d2 : this.returns) {
					s.setPort_optimization(b);
					s.setVar_importance(d1);
					s.setMin_return(d2);
					s.createSerialNumber(true);
					s.makePortfolios();
					Dividend D = new Dividend(s, 1., 1.);
					ReportEngine re = new ReportEngine(s, D, version, generation);
					try {
						re.writeSimFiles();
						System.out.println("Reports Written.");
					} catch (Exception e) {
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		String[] O = { "Barcelona", "Real Madrid", "Atletico Madrid" };
		Except E = new Except();
		String[] models = {};
		Simulation s = new Simulation(Manifest.c34, E, "F", models, 3, 0.5, SimulationSettings.filter_scheme6, 4, 3,
				0.1);
		Analizator a = new Analizator(s, 1.0, 0);
	}

}
