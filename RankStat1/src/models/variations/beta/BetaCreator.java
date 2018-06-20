package models.variations.beta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.core.paths.Path;
import data.core.structure.Competition;
import data.core.structure.Game;
import exceptions.CollectorException;
import models.general.base.StatsBase;
import models.general.handlers.Weightor;
import simulation.analyze.body.ModelCap;
import simulation.read.Reader;
import simulation.simulate.Manifest;

/**
 * Creator class for generations of Beta-models.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class BetaCreator {

	/**
	 * Makes uniform weight array.
	 * 
	 * @param n
	 *            array length.
	 * @return corresponding weight array.
	 */
	public static double[] uniform(int n) {
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			result[i] = (double) 1 / n;
		}
		return result;
	}

	/**
	 * Creates all uniform statistics bases.
	 * 
	 * @param g
	 *            Game-Object in question.
	 * @param n
	 *            number of games to backtrack for.
	 * @return corresponding StatsBase-Objects.
	 */
	public static StatsBase[][] uni(Game g, int n, boolean split) {
		StatsBase[][] bases = new StatsBase[n][];
		for (int i = 1; i <= n; i++) {
			try {
				StatsBase sb;
				if (split) {
					sb = new StatsBase(g, uniform(i), i);
				} else {
					sb = new StatsBase(g, i, uniform(i));
				}
				StatsBase[] element_i = { sb };
				bases[i - 1] = element_i;
			} catch (Exception e) {
				StatsBase[] element_i = {};
				bases[i - 1] = element_i;
			}
		}
		return bases;
	}

	/**
	 * Creates all partition-based statistics bases.
	 * 
	 * @param g
	 *            Game-Object in question.
	 * @param n
	 *            number of games to backtrack for.
	 * @return corresponding StatsBase-Objects.
	 */
	public static StatsBase[][] par(Game g, int n, boolean split) {
		StatsBase[][] bases = new StatsBase[n][];
		ArrayList<StatsBase> temp_bases;
		for (int i = 1; i <= n; i++) {
			try {
				temp_bases = new ArrayList<StatsBase>();
				double[][] PAR = Weightor.partitions(i);
				for (double[] par : PAR) {
					StatsBase sb;
					if (split) {
						sb = new StatsBase(g, par, i);
					} else {
						sb = new StatsBase(g, i, par);
					}
					temp_bases.add(sb);
				}
				StatsBase[] segment = new StatsBase[temp_bases.size()];
				for (int j = 0; j < segment.length; j++) {
					segment[j] = temp_bases.get(j);
				}
				bases[i - 1] = segment;
			} catch (Exception e) {
				StatsBase[] element_i = {};
				bases[i - 1] = element_i;
			}
		}
		return bases;
	}

	/**
	 * Check helper function.
	 * 
	 * @param w
	 *            value to check for.
	 * @return check evaluation.
	 */
	private static boolean check(double w) {
		return 0. <= w && w < 1.;
	}

	/**
	 * Second generation Beta model creation.
	 * 
	 * @param id
	 *            ID of the Beta model Object.
	 * @param sb
	 *            corresponding StatsBase-Object.
	 * @param precision
	 *            precision measure.
	 * @return list of corresponding Beta models.
	 */
	public static double[][][] generationOneCorrelations(double precision) {
		if (!check(precision))
			precision = 0.1;
		ArrayList<double[][]> temp_corr = new ArrayList<double[][]>();
		double start = 0.;
		for (double w16 = start; w16 < 1.; w16 += precision) {
			for (double w49 = start; w49 < 1.; w49 += precision) {
				for (double w50 = start; w50 < 1.; w50 += precision) {
					double w4 = 2. * w49;
					double w5 = w16 + w49;
					double w7 = w50;
					double w8 = 1. - w16 - 3. * w49 - w50;
					double w12 = 1 - w16 - 2. * w49 - w50;
					double w46 = w49;
					boolean eval = check(w4) && check(w5) && check(w7) && check(w8) && check(w12) && check(w46);
					if (eval) {
						double[] off = new double[72];
						double[] def = new double[72];
						off[4] = w4;
						off[5] = w5;
						off[7] = w7;
						off[8] = w8;
						def[12] = w12;
						def[16] = w16;
						def[46] = w46;
						def[49] = w49;
						def[50] = w50;
						double[][] temp = { off, def };
						temp_corr.add(temp);
					}
				}
			}
		}
		double[][][] correlations = new double[temp_corr.size()][][];
		for (int i = 0; i < correlations.length; i++) {
			correlations[i] = temp_corr.get(i);
		}
		return correlations;
	}

	/**
	 * Creates Model Label.
	 * 
	 * @param model
	 *            model ID.
	 * @param games
	 *            number of games to backtrack for.
	 * @param w_type
	 *            weight type.
	 * @param w_index
	 *            weight index.
	 * @param cor_type
	 *            correlation type.
	 * @param cor_index
	 *            correlation index.
	 * @return corresponding model label.
	 */
	private static String makeLabel(String model, String games, String w_type, String w_index, String cor_type,
			String cor_index) {
		return model + "-" + games + "-" + w_type + "-" + w_index + "-" + cor_type + "-" + cor_index;
	}

	private static void writeModelFiles(Game g, ArrayList<Beta1> beta1, ArrayList<Beta3> beta3, ArrayList<Beta4> beta4,
			ArrayList<Beta5> beta5) throws FileNotFoundException {
		String gameID = g.getId();
		String[] data = gameID.split("-");
		String path = Path.results_mod + data[0] + "/" + data[1] + "/" + data[2] + "/";
		new File(path).mkdirs();
		path += gameID + ".txt";
		PrintWriter pw = new PrintWriter(path);
		for (Beta1 b1 : beta1) {
			String dataline = "Model," + gameID + "," + b1.getId() + "," + b1.homeWin() + "," + b1.draw() + ","
					+ b1.awayWin();
			pw.println(dataline);
		}
		for (Beta3 b3 : beta3) {
			String dataline = "Model," + gameID + "," + b3.getId() + "," + b3.homeWin() + "," + b3.draw() + ","
					+ b3.awayWin();
			pw.println(dataline);
		}
		for (Beta4 b4 : beta4) {
			String dataline = "Model," + gameID + "," + b4.getId() + "," + b4.homeWin() + "," + b4.draw() + ","
					+ b4.awayWin();
			pw.println(dataline);
		}
		for (Beta5 b5 : beta5) {
			String dataline = "Model," + gameID + "," + b5.getId() + "," + b5.homeWin() + "," + b5.draw() + ","
					+ b5.awayWin();
			pw.println(dataline);
		}
		pw.close();
	}

	/**
	 * Creates non-split Beta-models.
	 * 
	 * @param g
	 *            relevant Game-Object.
	 * @param n_max
	 *            maximum backtrack number.
	 * @param precision
	 *            precision figure.
	 * @param genCor
	 *            generational correlations body.
	 */
	private static void createBetaModels(Game g, int n_max, double[][][] genCor) {
		ArrayList<Beta1> beta1 = new ArrayList<Beta1>();
		ArrayList<Beta3> beta3 = new ArrayList<Beta3>();
		ArrayList<Beta4> beta4 = new ArrayList<Beta4>();
		ArrayList<Beta5> beta5 = new ArrayList<Beta5>();
		long t = System.currentTimeMillis();
		StatsBase[][] bases = par(g, n_max, false);
		for (int backtrack = 1; backtrack <= bases.length; backtrack++) {
			for (int base = 1; base <= bases[backtrack - 1].length; base++) {
				for (int cor = 1; cor <= genCor.length; cor++) {
					String ID_1 = makeLabel("B1", Integer.toString(backtrack), "PAR", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					String ID_4 = makeLabel("B4", Integer.toString(backtrack), "PAR", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					try {
						Beta1 b1 = new Beta1(ID_1, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta1.add(b1);
					} catch (Exception e) {
						// Logger capacity
					}
					try {
						Beta4 b4 = new Beta4(ID_4, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta4.add(b4);
					} catch (Exception e) {
						// Logger capacity
					}
				}
			}
		}
		bases = par(g, n_max, true);
		for (int backtrack = 1; backtrack <= bases.length; backtrack++) {
			for (int base = 1; base <= bases[backtrack - 1].length; base++) {
				for (int cor = 1; cor <= genCor.length; cor++) {
					String ID_3 = makeLabel("B3", Integer.toString(backtrack), "PARSPLIT", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					String ID_5 = makeLabel("B5", Integer.toString(backtrack), "PARSPLIT", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					try {
						Beta3 b3 = new Beta3(ID_3, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta3.add(b3);
					} catch (Exception e) {
						// Logger capacity
					}
					try {
						Beta5 b5 = new Beta5(ID_5, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta5.add(b5);
					} catch (Exception e) {
						// Logger capacity
					}
				}
			}
		}
		try {
			writeModelFiles(g, beta1, beta3, beta4, beta5);
			System.out.println(g.getId() + ": succes. (" + 0.001 * (System.currentTimeMillis() - t) + "sec)");
		} catch (Exception e) {
			System.out.println(g.getId() + ": failure. (" + 0.001 * (System.currentTimeMillis() - t) + "sec)");
		}
	}

	private static ArrayList<ModelCap> mergeModels(ArrayList<Beta1> beta1, ArrayList<Beta3> beta3,
			ArrayList<Beta4> beta4, ArrayList<Beta5> beta5) {
		ArrayList<ModelCap> models = new ArrayList<ModelCap>();
		for (Beta1 b : beta1) {
			String modelID = b.getId();
			String gameID = b.getSb().getGame().getId();
			ModelCap temp = new ModelCap(modelID, gameID, b.homeWin(), b.draw(), b.awayWin());
			models.add(temp);
		}
		for (Beta3 b : beta3) {
			String modelID = b.getId();
			String gameID = b.getSb().getGame().getId();
			ModelCap temp = new ModelCap(modelID, gameID, b.homeWin(), b.draw(), b.awayWin());
			models.add(temp);
		}
		for (Beta4 b : beta4) {
			String modelID = b.getId();
			String gameID = b.getSb().getGame().getId();
			ModelCap temp = new ModelCap(modelID, gameID, b.homeWin(), b.draw(), b.awayWin());
			models.add(temp);
		}
		for (Beta5 b : beta5) {
			String modelID = b.getId();
			String gameID = b.getSb().getGame().getId();
			ModelCap temp = new ModelCap(modelID, gameID, b.homeWin(), b.draw(), b.awayWin());
			models.add(temp);
		}
		return models;
	}

	public static ArrayList<ModelCap> createBetaModels(Game g, double[][][] genCor, int n_max) {
		ArrayList<Beta1> beta1 = new ArrayList<Beta1>();
		ArrayList<Beta3> beta3 = new ArrayList<Beta3>();
		ArrayList<Beta4> beta4 = new ArrayList<Beta4>();
		ArrayList<Beta5> beta5 = new ArrayList<Beta5>();
		StatsBase[][] bases = par(g, n_max, false);
		for (int backtrack = 1; backtrack <= bases.length; backtrack++) {
			for (int base = 1; base <= bases[backtrack - 1].length; base++) {
				for (int cor = 1; cor <= genCor.length; cor++) {
					String ID_1 = makeLabel("B1", Integer.toString(backtrack), "PAR", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					String ID_4 = makeLabel("B4", Integer.toString(backtrack), "PAR", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					try {
						Beta1 b1 = new Beta1(ID_1, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta1.add(b1);
					} catch (Exception e) {
						// Logger capacity
					}
					try {
						Beta4 b4 = new Beta4(ID_4, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta4.add(b4);
					} catch (Exception e) {
						// Logger capacity
					}
				}
			}
		}
		bases = par(g, n_max, true);
		for (int backtrack = 1; backtrack <= bases.length; backtrack++) {
			for (int base = 1; base <= bases[backtrack - 1].length; base++) {
				for (int cor = 1; cor <= genCor.length; cor++) {
					String ID_3 = makeLabel("B3", Integer.toString(backtrack), "PARSPLIT", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					String ID_5 = makeLabel("B5", Integer.toString(backtrack), "PARSPLIT", Integer.toString(base),
							Integer.toString(1), Integer.toString(cor));
					try {
						Beta3 b3 = new Beta3(ID_3, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta3.add(b3);
					} catch (Exception e) {
						// Logger capacity
					}
					try {
						Beta5 b5 = new Beta5(ID_5, bases[backtrack - 1][base - 1], genCor[cor - 1][0],
								genCor[cor - 1][1]);
						beta5.add(b5);
					} catch (Exception e) {
						// Logger capacity
					}
				}
			}
		}
		return mergeModels(beta1, beta3, beta4, beta5);
	}

	/**
	 * Creates all Beta-models and variations.
	 * 
	 * @param c
	 *            relevant Competition-Object.
	 * @param n_max
	 *            maximum backtrack number.
	 * @param precision
	 *            precision figure.
	 */
	public static void createModel(Competition c, double precision) {
		double[][][] genCor = BetaCreator.generationOneCorrelations(precision);
		for (Game g : c.getGames()) {
			createBetaModels(g, 5, genCor);
		}
	}

	public static void main(String[] args) throws IOException, CollectorException {
		  Competition c = Reader.READ(Manifest.c8);
		  System.out.println("Competition read in."); 
		  createModel(c, 0.1);
		  c = Reader.READ(Manifest.c41);
		  System.out.println("Competition read in."); 
		  createModel(c, 0.1);
	}

}