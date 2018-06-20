package models.general.base;

import java.util.ArrayList;
import mathematics.distributions.Normal;

/**
 * Match Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Match {

	/**
	 * Class attributes.
	 */
	// General features
	private String label;
	// Offensive Profiles
	private double[] mu_home_O;
	private double[] sigma_home_O;
	private double[] mu_home_adv_O;
	private double[] sigma_home_adv_O;
	private double[] mu_away_O;
	private double[] sigma_away_O;
	private double[] mu_away_adv_O;
	private double[] sigma_away_adv_O;
	// Defensive Profiles
	private double[] mu_home_D;
	private double[] sigma_home_D;
	private double[] mu_home_adv_D;
	private double[] sigma_home_adv_D;
	private double[] mu_away_D;
	private double[] sigma_away_D;
	private double[] mu_away_adv_D;
	private double[] sigma_away_adv_D;
	// Offensive Aggression Profiles
	private double[] mu_home_OA;
	private double[] sigma_home_OA;
	private double[] mu_home_adv_OA;
	private double[] sigma_home_adv_OA;
	private double[] mu_away_OA;
	private double[] sigma_away_OA;
	private double[] mu_away_adv_OA;
	private double[] sigma_away_adv_OA;
	// Defensive Aggression Profiles
	private double[] mu_home_DA;
	private double[] sigma_home_DA;
	private double[] mu_home_adv_DA;
	private double[] sigma_home_adv_DA;
	private double[] mu_away_DA;
	private double[] sigma_away_DA;
	private double[] mu_away_adv_DA;
	private double[] sigma_away_adv_DA;
	// Possesion Profiles
	private double[] mu_home_P;
	private double[] sigma_home_P;
	private double[] mu_home_adv_P;
	private double[] sigma_home_adv_P;
	private double[] mu_away_P;
	private double[] sigma_away_P;
	private double[] mu_away_adv_P;
	private double[] sigma_away_adv_P;
	// Evaluations
	private ArrayList<Double> means_H;
	private ArrayList<Double> sdevs_H;
	private ArrayList<Double> means_A;
	private ArrayList<Double> sdevs_A;

	/**
	 * General constructor.
	 * 
	 * @param sb
	 *            relevant StatsBase-Object.
	 * @param scheme
	 *            filtering scheme.
	 * @param i
	 *            backtrack indicator.
	 * @throws Exception
	 */
	public Match(StatsBase sb, String[] scheme, int i) throws Exception {
		this.means_H = new ArrayList<Double>();
		this.sdevs_H = new ArrayList<Double>();
		this.means_A = new ArrayList<Double>();
		this.sdevs_A = new ArrayList<Double>();
		for (String s : scheme) {
			assign(sb, s, i);
			compute(s, i);
		}
	}

	/**
	 * Dataline constructor.
	 * 
	 * @param dataline
	 *            data line String.
	 * @param scheme
	 *            filtering scheme.
	 * @throws Exception
	 */
	public Match(String dataline, String[] scheme) throws Exception {
		this.means_H = new ArrayList<Double>();
		this.sdevs_H = new ArrayList<Double>();
		this.means_A = new ArrayList<Double>();
		this.sdevs_A = new ArrayList<Double>();
		String[] data = dataline.split(",");
		this.label = data[2];
		if (data[0].equals("Match")) {
			for (String profile : scheme) {
				assign(data, profile);
			}
		} else {
			throw new Exception("Wrong data input!");
		}
	}

	/**
	 * Convert number string to double value.
	 * 
	 * @param number
	 *            number String.
	 * @return corresponding double value.
	 */
	private double convert(String number) {
		return Double.parseDouble(number);
	}

	/**
	 * Assigns the appropriate data arrays.
	 * 
	 * @param sb
	 *            relevant StatsBase-Object.
	 * @param profile
	 *            matching profile String indicator.
	 * @param i
	 *            backtrack indicator.
	 */
	private void assign(StatsBase sb, String profile, int i) {
		switch (profile) {
		case "O":
			this.mu_home_O = StatsCollector.mu_O(sb.getHomeTeamAverage());
			this.mu_home_adv_O = StatsCollector.mu_O(sb.getHomeAdversaryAverage());
			this.mu_away_O = StatsCollector.mu_O(sb.getAwayTeamAverage());
			this.mu_away_adv_O = StatsCollector.mu_O(sb.getAwayAdversaryAverage());
			if (i > 1) {
				this.sigma_home_O = StatsCollector.sigma_O(sb.getHomeTeamDeviation());
				this.sigma_home_adv_O = StatsCollector.sigma_O(sb.getHomeAdversaryDeviation());
				this.sigma_away_O = StatsCollector.sigma_O(sb.getAwayTeamDeviation());
				this.sigma_away_adv_O = StatsCollector.sigma_O(sb.getAwayAdversaryDeviation());
			}
			break;
		case "D":
			this.mu_home_D = StatsCollector.mu_D(sb.getHomeTeamAverage());
			this.mu_home_adv_D = StatsCollector.mu_D(sb.getHomeAdversaryAverage());
			this.mu_away_D = StatsCollector.mu_D(sb.getAwayTeamAverage());
			this.mu_away_adv_D = StatsCollector.mu_D(sb.getAwayAdversaryAverage());
			if (i > 1) {
				this.sigma_home_D = StatsCollector.sigma_D(sb.getHomeTeamDeviation());
				this.sigma_home_adv_D = StatsCollector.sigma_D(sb.getHomeAdversaryDeviation());
				this.sigma_away_D = StatsCollector.sigma_D(sb.getAwayTeamDeviation());
				this.sigma_away_adv_D = StatsCollector.sigma_D(sb.getAwayAdversaryDeviation());
			}
			break;
		case "OA":
			this.mu_home_OA = StatsCollector.mu_OA(sb.getHomeTeamAverage());
			this.mu_home_adv_OA = StatsCollector.mu_OA(sb.getHomeAdversaryAverage());
			this.mu_away_OA = StatsCollector.mu_OA(sb.getAwayTeamAverage());
			this.mu_away_adv_OA = StatsCollector.mu_OA(sb.getAwayAdversaryAverage());
			if (i > 1) {
				this.sigma_home_OA = StatsCollector.sigma_OA(sb.getHomeTeamDeviation());
				this.sigma_home_adv_OA = StatsCollector.sigma_OA(sb.getHomeAdversaryDeviation());
				this.sigma_away_OA = StatsCollector.sigma_OA(sb.getAwayTeamDeviation());
				this.sigma_away_adv_OA = StatsCollector.sigma_OA(sb.getAwayAdversaryDeviation());
			}
			break;
		case "DA":
			this.mu_home_DA = StatsCollector.mu_DA(sb.getHomeTeamAverage());
			this.mu_home_adv_DA = StatsCollector.mu_DA(sb.getHomeAdversaryAverage());
			this.mu_away_DA = StatsCollector.mu_DA(sb.getAwayTeamAverage());
			this.mu_away_adv_DA = StatsCollector.mu_DA(sb.getAwayAdversaryAverage());
			if (i > 1) {
				this.sigma_home_DA = StatsCollector.sigma_DA(sb.getHomeTeamDeviation());
				this.sigma_home_adv_DA = StatsCollector.sigma_DA(sb.getHomeAdversaryDeviation());
				this.sigma_away_DA = StatsCollector.sigma_DA(sb.getAwayTeamDeviation());
				this.sigma_away_adv_DA = StatsCollector.sigma_DA(sb.getAwayAdversaryDeviation());
			}
			break;
		case "P":
			this.mu_home_P = StatsCollector.mu_P(sb.getHomeTeamAverage());
			this.mu_home_adv_P = StatsCollector.mu_P(sb.getHomeAdversaryAverage());
			this.mu_away_P = StatsCollector.mu_P(sb.getAwayTeamAverage());
			this.mu_away_adv_P = StatsCollector.mu_P(sb.getAwayAdversaryAverage());
			if (i > 1) {
				this.sigma_home_P = StatsCollector.sigma_P(sb.getHomeTeamDeviation());
				this.sigma_home_adv_P = StatsCollector.sigma_P(sb.getHomeAdversaryDeviation());
				this.sigma_away_P = StatsCollector.sigma_P(sb.getAwayTeamDeviation());
				this.sigma_away_adv_P = StatsCollector.sigma_P(sb.getAwayAdversaryDeviation());
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Assigns the appropriate data arrays.
	 * 
	 * @param data
	 *            data line values array.
	 * @param profile
	 *            matching profile String indicator.
	 */
	private void assign(String[] data, String profile) {
		double[] means_H = {};
		double[] means_A = {};
		switch (profile) {
		case "O":
			double[] perc_H_O = { convert(data[10]), convert(data[11]), convert(data[12]), convert(data[13]) };
			double[] perc_A_O = { convert(data[28]), convert(data[29]), convert(data[30]), convert(data[31]) };
			means_H = perc_H_O;
			means_A = perc_A_O;
			break;
		case "D":
			double[] perc_H_D = { convert(data[14]), convert(data[15]), convert(data[16]), convert(data[19]) };
			double[] perc_A_D = { convert(data[32]), convert(data[33]), convert(data[34]), convert(data[37]) };
			means_H = perc_H_D;
			means_A = perc_A_D;
			break;
		case "OA":
			double[] perc_H_OA = { convert(data[8]), convert(data[9]), convert(data[12]), convert(data[13]) };
			double[] perc_A_OA = { convert(data[26]), convert(data[27]), convert(data[30]), convert(data[31]) };
			means_H = perc_H_OA;
			means_A = perc_A_OA;
			break;
		case "DA":
			double[] perc_H_DA = { convert(data[14]), convert(data[15]), convert(data[18]), convert(data[19]) };
			double[] perc_A_DA = { convert(data[32]), convert(data[33]), convert(data[36]), convert(data[37]) };
			means_H = perc_H_DA;
			means_A = perc_A_DA;
			break;
		case "P":
			double[] perc_H_P = { convert(data[4]), convert(data[7]), convert(data[12]) };
			double[] perc_A_P = { convert(data[22]), convert(data[25]), convert(data[30]) };
			means_H = perc_H_P;
			means_A = perc_A_P;
			break;
		default:
			break;
		}
		double mu_H = mean(means_H);
		double sigma_H = sdev(means_H);
		if (mu_H > -1. && sigma_H > -1.) {
			this.means_H.add(mu_H);
			this.sdevs_H.add(mu_H);
		}
		double mu_A = mean(means_A);
		double sigma_A = sdev(means_A);
		if (mu_A > -1. && sigma_A > -1.) {
			this.means_A.add(mu_A);
			this.sdevs_A.add(mu_A);
		}
	}

	/**
	 * Creates simple matching figures.
	 * 
	 * @param mu_1
	 *            first average array.
	 * @param mu_2
	 *            second average array.
	 * @return corresponding matching figures array.
	 */
	private double[] simple_matches(double[] mu_1, double[] mu_2) {
		boolean check = (mu_1.length == mu_2.length);
		if (check) {
			double[] d = new double[mu_1.length];
			for (int i = 0; i < mu_1.length; i++) {
				double ratio = Math.abs(Math.min(mu_1[i], mu_2[i]) / Math.max(mu_1[i], mu_2[i]));
				d[i] = ratio;
			}
			return d;
		} else
			return new double[0];
	}

	/**
	 * Creates matching figures.
	 * 
	 * @param mu_1
	 *            first average array.
	 * @param sigma_1
	 *            first deviation array.
	 * @param mu_2
	 *            second average array.
	 * @param sigma_2
	 *            second deviation array.
	 * @return corresponding matching figures array.
	 */
	private double[] matches(double[] mu_1, double[] sigma_1, double[] mu_2, double[] sigma_2) {
		boolean check = (mu_1.length == sigma_1.length) && (mu_2.length == sigma_2.length)
				&& (mu_1.length == mu_2.length);
		if (check) {
			double[] d = new double[mu_1.length];
			for (int i = 0; i < mu_1.length; i++) {
				Normal n1 = new Normal(mu_1[i] / mu_2[i], sigma_1[i] / sigma_2[i]);
				Normal n2 = new Normal(1., 1.);
				d[i] = Normal.matchPercentage(n1, n2);
			}
			return d;
		} else
			return new double[0];
	}

	/**
	 * Computes mean of a double value array.
	 * 
	 * @param mu
	 *            means array.
	 * @return resulting average.
	 */
	private double mean(double[] mu) {
		if (mu.length > 0) {
			double sum = 0.;
			int n = 0;
			for (double d : mu) {
				if (0. <= d && d <= 1.) {
					sum += d;
					n++;
				}
			}
			return (double) sum / n;
		}
		return -1.;
	}

	/**
	 * Computes standard deviation of a double value array.
	 * 
	 * @param mu
	 *            means array.
	 * @return resulting standard deviation.
	 */
	private double sdev(double[] mu) {
		if (mu.length > 0) {
			double m = mean(mu);
			double sum = 0.;
			int n = 0;
			for (double d : mu) {
				if (0. <= d && d <= 1.) {
					sum += Math.pow(d - m, 2);
					n++;
				}
			}
			return (double) sum / (n - 1);
		}
		return -1.;
	}

	/**
	 * Computes relevant matching values.
	 * 
	 * @param profile
	 *            matching profile indicator.
	 * @param i
	 *            backtrack indicator.
	 */
	private void compute(String profile, int i) {
		double[] H;
		double[] A;
		double mu_H;
		double sigma_H;
		double mu_A;
		double sigma_A;
		switch (profile) {
		case "O":
			if (i > 1) {
				H = matches(this.mu_home_O, this.sigma_home_O, this.mu_away_adv_O, this.sigma_away_adv_O);
			} else {
				H = simple_matches(this.mu_home_O, this.mu_away_adv_O);
			}
			mu_H = mean(H);
			sigma_H = sdev(H);
			if (mu_H > -1. && sigma_H > -1.) {
				this.means_H.add(mu_H);
				this.sdevs_H.add(sigma_H);
			}
			if (i > 1) {
				A = matches(this.mu_away_O, this.sigma_away_O, this.mu_home_adv_O, this.sigma_home_adv_O);
			} else {
				A = simple_matches(this.mu_away_O, this.mu_home_adv_O);
			}
			mu_A = mean(A);
			sigma_A = sdev(A);
			if (mu_A > -1. && sigma_A > -1.) {
				this.means_A.add(mu_A);
				this.sdevs_A.add(sigma_A);
			}
			break;
		case "D":
			if (i > 1) {
				H = matches(this.mu_home_D, this.sigma_home_D, this.mu_away_adv_D, this.sigma_away_adv_D);
			} else {
				H = simple_matches(this.mu_home_D, this.mu_away_adv_D);
			}
			mu_H = mean(H);
			sigma_H = sdev(H);
			if (mu_H > -1.)
				this.means_H.add(mu_H);
			if (sigma_H > -1.)
				this.sdevs_H.add(sigma_H);
			if (i > 1) {
				A = matches(this.mu_away_D, this.sigma_away_D, this.mu_home_adv_D, this.sigma_home_adv_D);
			} else {
				A = simple_matches(this.mu_away_D, this.mu_home_adv_D);
			}
			mu_A = mean(A);
			sigma_A = sdev(A);
			if (mu_A > -1.)
				this.means_A.add(mu_A);
			if (sigma_A > -1.)
				this.sdevs_A.add(sigma_A);
			break;
		case "OA":
			if (i > 1) {
				H = matches(this.mu_home_OA, this.sigma_home_OA, this.mu_away_adv_OA, this.sigma_away_adv_OA);
			} else {
				H = simple_matches(this.mu_home_OA, this.mu_away_adv_OA);
			}
			mu_H = mean(H);
			sigma_H = sdev(H);
			if (mu_H > -1.)
				this.means_H.add(mu_H);
			if (sigma_H > -1.)
				this.sdevs_H.add(sigma_H);
			if (i > 1) {
				A = matches(this.mu_away_OA, this.sigma_away_OA, this.mu_home_adv_OA, this.sigma_home_adv_OA);
			} else {
				A = simple_matches(this.mu_away_OA, this.mu_home_adv_OA);
			}
			mu_A = mean(A);
			sigma_A = sdev(A);
			if (mu_A > -1.)
				this.means_A.add(mu_A);
			if (sigma_A > -1.)
				this.sdevs_A.add(sigma_A);
			break;
		case "DA":
			if (i > 1) {
				H = matches(this.mu_home_DA, this.sigma_home_DA, this.mu_away_adv_DA, this.sigma_away_adv_DA);
			} else {
				H = simple_matches(this.mu_home_DA, this.mu_away_adv_DA);
			}
			mu_H = mean(H);
			sigma_H = sdev(H);
			if (mu_H > -1.)
				this.means_H.add(mu_H);
			if (sigma_H > -1.)
				this.sdevs_H.add(sigma_H);
			if (i > 1) {
				A = matches(this.mu_away_DA, this.sigma_away_DA, this.mu_home_adv_DA, this.sigma_home_adv_DA);
			} else {
				A = simple_matches(this.mu_away_DA, this.mu_home_adv_DA);
			}
			mu_A = mean(A);
			sigma_A = sdev(A);
			if (mu_A > -1.)
				this.means_A.add(mu_A);
			if (sigma_A > -1.)
				this.sdevs_A.add(sigma_A);
			break;
		case "P":
			if (i > 1) {
				H = matches(this.mu_home_P, this.sigma_home_P, this.mu_away_adv_P, this.sigma_away_adv_P);
			} else {
				H = simple_matches(this.mu_home_P, this.mu_away_adv_P);
			}
			mu_H = mean(H);
			sigma_H = sdev(H);
			if (mu_H > -1.)
				this.means_H.add(mu_H);
			if (sigma_H > -1.)
				this.sdevs_H.add(sigma_H);
			if (i > 1) {
				A = matches(this.mu_away_P, this.sigma_away_P, this.mu_home_adv_P, this.sigma_home_adv_P);
			} else {
				A = simple_matches(this.mu_away_P, this.mu_home_adv_P);
			}
			mu_A = mean(A);
			sigma_A = sdev(A);
			if (mu_A > -1.)
				this.means_A.add(mu_A);
			if (sigma_A > -1.)
				this.sdevs_A.add(sigma_A);
			break;
		default:
			break;
		}

	}

	/**
	 * Home team matching means getter.
	 * 
	 * @return home team matching means.
	 */
	public ArrayList<Double> getMeans_H() {
		return means_H;
	}

	/**
	 * Home team matching deviations getter.
	 * 
	 * @return home team matching deviations.
	 */
	public ArrayList<Double> getSdevs_H() {
		return sdevs_H;
	}

	/**
	 * Away team matching means getter.
	 * 
	 * @return away team matching means
	 */
	public ArrayList<Double> getMeans_A() {
		return means_A;
	}

	/**
	 * Away team matching deviations getter.
	 * 
	 * @return away team matching deviations
	 */
	public ArrayList<Double> getSdevs_A() {
		return sdevs_A;
	}

	public String getLabel() {
		return label;
	}

}