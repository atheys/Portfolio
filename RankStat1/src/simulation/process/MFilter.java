package simulation.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.core.paths.Path;
import data.core.structure.Competition;
import data.core.structure.Game;
import mathematics.distributions.Normal;
import models.general.base.Match;
import models.general.base.StatsBase;
import models.general.base.StatsCollector;
import models.variations.beta.BetaCreator;
import simulation.analyze.body.ModelCap;
import simulation.analyze.evaluation.Evaluator;
import simulation.analyze.selection.Capsule;
import simulation.read.Reader;
import simulation.simulate.Manifest;

public class MFilter {

	private synchronized static void ERASE(Evaluator e, String model) {
		ArrayList<ModelCap> models = e.getUnit_models();
		for (int i = models.size() - 1; i >= 0; i--) {
			String m = models.get(i).getModel_id().split("-")[0];
			if (m.equals(model)) {
				e.deleteModel(models.get(i).getModel_id());
				e.deletePrediction(models.get(i).getModel_id());
			}
		}
	}

	private synchronized static void ERASE(Evaluator e, int n) {
		ArrayList<ModelCap> models = e.getUnit_models();
		for (int i= models.size()-1; i>=0; i--) {
			ModelCap mc = models.get(i);
			int N = Integer.parseInt(mc.getModel_id().split("-")[1]);
			if (N == n) {
				e.deleteModel(mc.getModel_id());
				e.deletePrediction(mc.getModel_id());
			}
		}
	}

	private static void ERASE(Evaluator e, int backtrack, String label, int number) {
		ArrayList<ModelCap> models = e.getUnit_models();
		for (ModelCap mc : models) {
			int n = Integer.parseInt(mc.getModel_id().split("-")[1]);
			String l = mc.getModel_id().split("-")[2];
			int k = Integer.parseInt(mc.getModel_id().split("-")[3]);
			if (n == backtrack && l.equals(label) && k == number) {
				e.deleteModel(mc.getModel_id());
				e.deletePrediction(mc.getModel_id());
			}
		}

	}

	private static boolean AbsMatch(ArrayList<Double> means_H, ArrayList<Double> means_A, double min) {
		boolean result = true;
		for (double d : means_H) {
			result = result && (d >= min);
		}
		for (double d : means_A) {
			result = result && (d >= min);
		}
		return result;
	}

	private static boolean AbsNoMatch(ArrayList<Double> means_H, ArrayList<Double> means_A, double min) {
		boolean result = true;
		for (double d : means_H) {
			result = result && (d < min);
		}
		for (double d : means_A) {
			result = result && (d < min);
		}
		return result;
	}

	private static double average(ArrayList<Double> numbers) {
		double sum = 0.;
		for (double d : numbers) {
			sum += d;
		}
		return (double) sum / numbers.size();
	}

	private static double max(ArrayList<Double> numbers) {
		double M = Double.MIN_VALUE;
		for (double d : numbers) {
			if (d > M)
				M = d;
		}
		return M;
	}

	private static double min(ArrayList<Double> numbers) {
		double m = Double.MAX_VALUE;
		for (double d : numbers) {
			if (d < m)
				m = d;
		}
		return m;
	}

	private static int fails(ArrayList<Double> numbers, double min) {
		int f = 0;
		for (double d : numbers) {
			if (d < min)
				f++;
		}
		return f;
	}

	private static int successes(ArrayList<Double> numbers, double min) {
		int s = 0;
		for (double d : numbers) {
			if (d >= min)
				s++;
		}
		return s;
	}

	private static boolean NuanceMatch(ArrayList<Double> means_H, ArrayList<Double> means_A, double min) {
		// Mean value checks
		double avg_H = average(means_H);
		if (avg_H < min)
			return false;
		double avg_A = average(means_A);
		if (avg_A < min)
			return false;
		// Range value checks
		double min_H = min(means_H);
		double max_H = max(means_H);
		if (means_H.size() == 2 && max_H < 0.5 * (1 + min) && Math.abs(max_H - min_H) >= 0.9 * min)
			return false;
		if (means_H.size() > 2 && max_H < 0.5 * (1 + min) && Math.abs(max_H - min_H) >= 0.9 * min)
			return false;
		double min_A = min(means_H);
		double max_A = max(means_H);
		if (means_A.size() == 2 && max_A < 0.5 * (1 + min) && Math.abs(max_A - min_A) >= 0.9 * min)
			return false;
		if (means_A.size() > 2 && max_A < 0.5 * (1 + min) && Math.abs(max_A - min_A) >= 0.9 * min)
			return false;
		// Quality number checks
		int fails_H = fails(means_H, min);
		int successes_H = successes(means_H, min);
		if (fails_H > successes_H)
			return false;
		int fails_A = fails(means_A, min);
		int successes_A = successes(means_A, min);
		if (fails_A > successes_A)
			return false;
		// More functionality (?)
		return true;
	}

	private static boolean ANALYZE(ArrayList<Double> means_H, ArrayList<Double> means_A, double min) {
		if (AbsMatch(means_H, means_A, min)) {
			return false;
		}
		if (AbsNoMatch(means_H, means_A, min)) {
			return true;
		}
		// Further filter reasoning & evaluation
		return !(NuanceMatch(means_H, means_A, min));
	}

	private static void filterBase(Evaluator e, double min_perc, String[] scheme, int n_max) throws Exception {
		String gameID = e.getGame().getId();
		String[] dat = gameID.split("-");
		String datapath = Path.results_mat + dat[0] + "/" + dat[1] + "/" + dat[2] + "/" + gameID + ".txt";
		BufferedReader br = new BufferedReader(new FileReader(datapath));
		String dataline;
		while ((dataline = br.readLine()) != null) {
			int i = Integer.parseInt(dataline.split(",")[2].split("-")[1]);
			if (i > n_max) {
				ERASE(e, i);
			} else {
				try {
					Match m = new Match(dataline, scheme);
					boolean eval = ANALYZE(m.getMeans_H(), m.getMeans_A(), min_perc);
					if (eval) {
						String[] data = m.getLabel().split("-");
						int n = Integer.parseInt(data[1]);
						String label = data[2];
						int k = Integer.parseInt(data[3]);
						ERASE(e, n, label, k);
					}
				} catch (Exception E) {
					// Logger capacity
				}

			}
		}
		br.close();
	}

	private static void filterCreate(Evaluator e, double min_perc, String[] scheme, int n_max) {
		ArrayList<String> lines = writeMatches(e.getGame(), n_max);
		for (String line : lines) {
			try {
				Match m = new Match(line, scheme);
				boolean eval = ANALYZE(m.getMeans_H(), m.getMeans_A(), min_perc);
				if (eval) {
					String[] data = m.getLabel().split("-");
					int n = Integer.parseInt(data[1]);
					String label = data[2];
					int k = Integer.parseInt(data[3]);
					ERASE(e, n, label, k);
				}
			} catch (Exception E) {
				// Logger capacity
			}
		}
	}

	public static void FILTER(Evaluator e, String[] scheme, String[] models, int n_max, double min_perc) {
		System.out.println(e.getGame().getId());
		for (String m : models) {
			ERASE(e, m);
		}
		try {
			filterBase(e, min_perc, scheme, n_max);
		} catch (Exception E) {
			E.printStackTrace();
			filterCreate(e, min_perc, scheme, n_max);
		}
	}

	/**
	 * Compares two value arrays.
	 * 
	 * @param a1
	 *            first values list.
	 * @param a2
	 *            second values list.
	 * @return resulting comparison list.
	 */
	private static double[] compare(double[] a1, double[] a2, boolean single) {
		double[] c = new double[0];
		if (a1.length == a2.length) {
			c = new double[a1.length];
			if (single) {
				for (int i = 0; i < a1.length; i++) {
					double min = Math.min(a1[i], a2[i]);
					double max = Math.max(a1[i], a2[i]);
					if (max != 0) {
						c[i] = min / max;
					} else {
						c[i] = -1.;
					}
				}
			} else {
				for (int i = 0; i < a1.length; i++) {
					if (a2[i] != 0) {
						c[i] = a1[i] / a2[i];
					} else {
						c[i] = -1.;
					}
				}
			}
		}
		return c;
	}

	private static String toDataLine(String line, double[] perc) {
		for (double d : perc) {
			line += "," + d;
		}
		return line;
	}

	private static String dataline(StatsBase sb, String label) {
		String line = "Match," + sb.getGame().getId() + "," + label;
		double[] mu_H;
		double[] mu_A;
		double[] perc;
		if (Integer.parseInt(label.split("-")[1]) > 1) {
			mu_H = compare(StatsCollector.mu(sb.getHomeTeamAverage()), StatsCollector.mu(sb.getAwayAdversaryAverage()),
					false);
			mu_A = compare(StatsCollector.mu(sb.getAwayTeamAverage()), StatsCollector.mu(sb.getHomeAdversaryAverage()),
					false);
			double[] sigma_H = compare(StatsCollector.sigma(sb.getHomeTeamDeviation()),
					StatsCollector.sigma(sb.getAwayAdversaryDeviation()), false);
			double[] sigma_A = compare(StatsCollector.sigma(sb.getAwayTeamDeviation()),
					StatsCollector.sigma(sb.getHomeAdversaryDeviation()), false);
			perc = new double[2 * mu_H.length];
			Normal n = new Normal(1., 1.);
			for (int i = 0; i < mu_H.length; i++) {
				if (mu_H[i] > -1. && sigma_H[i] > -1.) {
					Normal nh = new Normal(mu_H[i], sigma_H[i]);
					double p = Normal.matchPercentage(nh, n);
					perc[i] = (0. <= p && p <= 1.) ? p : -1.;
				}
				if (mu_A[i] > -1. && sigma_A[i] > -1.) {
					Normal na = new Normal(mu_A[i], sigma_A[i]);
					double p = Normal.matchPercentage(na, n);
					perc[i + mu_H.length] = (0. <= p && p <= 1.) ? p : -1.;
				}
			}
		} else {
			mu_H = compare(StatsCollector.mu(sb.getHomeTeamAverage()), StatsCollector.mu(sb.getAwayAdversaryAverage()),
					true);
			mu_A = compare(StatsCollector.mu(sb.getAwayTeamAverage()), StatsCollector.mu(sb.getHomeAdversaryAverage()),
					true);
			perc = new double[2 * mu_H.length];
			for (int i = 0; i < perc.length / 2; i++) {
				perc[i] = mu_H[i];
				perc[i + mu_H.length] = mu_A[i];
			}
		}
		return toDataLine(line, perc);
	}

	public static ArrayList<String> writeMatches(Game g, int n_max) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			String[] data = g.getId().split("-");
			String path = Path.results_mat + data[0] + "/" + data[1] + "/" + data[2] + "/";
			new File(path).mkdirs();
			path += g.getId() + ".txt";
			PrintWriter pw = new PrintWriter(path);
			StatsBase[][] non = BetaCreator.par(g, n_max, false);
			StatsBase[][] split = BetaCreator.par(g, n_max, true);
			for (int i = 1; i <= non.length; i++) {
				for (int j = 1; j <= non[i - 1].length; j++) {
					String label = "SB-" + i + "-PAR-" + j;
					StatsBase sb = non[i - 1][j - 1];
					String dline = dataline(sb, label);
					lines.add(dline);
					pw.println(dline);
				}
			}
			for (int i = 1; i <= split.length; i++) {
				for (int j = 1; j <= split[i - 1].length; j++) {
					String label = "SB-" + i + "-PARSPLIT-" + j;
					StatsBase sb = split[i - 1][j - 1];
					String dline = dataline(sb, label);
					lines.add(dline);
					pw.println(dline);
				}
			}
			pw.close();
		} catch (Exception e) {
			// Logger capacity
		}
		return lines;
	}

	public static void main(String[] args) throws Exception {
		Capsule[] cs = { Manifest.c28, Manifest.c21, Manifest.c14, Manifest.c4, Manifest.c37 };
		for (Capsule c : cs) {
			Competition comp = Reader.QUICK_READ(c);
			for (Game g : comp.getGames()) {
				long t = System.currentTimeMillis();
				writeMatches(g, 5);
				System.out.println(g.getId() + ": " + 0.001 * (System.currentTimeMillis() - t));
			}
		}

	}
}
