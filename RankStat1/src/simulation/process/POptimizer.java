package simulation.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import data.core.paths.Path;
import data.core.structure.GameDay;
import exceptions.CollectorException;
import mathematics.portfolio.Asset;
import mathematics.portfolio.Portfolio;

/**
 * Portfolio optimizer class (including process builder).
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class POptimizer {

	/**
	 * Class attributes.
	 */
	private GameDay begin;
	private Portfolio portfolio;
	private Portfolio pseudo_portfolio;
	private double importance;

	public POptimizer(Portfolio p, GameDay begin, boolean optimize){
		this.begin = begin;
		this.portfolio = p;
		this.pseudo_portfolio = p;
		this.importance = 1.;
		if (optimize) {
			this.run();
		} else {
			this.uniform();
		}
	}
	
	/**
	 * General constructor.
	 * 
	 * @param portfolio
	 *            Portfolio-Object to optimize for.
	 * @param importance
	 *            importance feature for variance portfolio.
	 * @param min_ret
	 *            minimal return feature.
	 */
	public POptimizer(Portfolio portfolio, GameDay begin, double importance, double min_ret, boolean optimize) {
		this.portfolio = PFilter.filter(portfolio, min_ret);
		this.begin = begin;
		this.importance = importance;
		this.filter();
		if (optimize) {
			this.run();
		} else {
			this.uniform();
		}
	}

	/**
	 * Filter function for multiple included assets.
	 */
	private void filter() {
		ArrayList<Asset> assets = portfolio.getAssets();
		ArrayList<Asset> assets_filtered = new ArrayList<Asset>();
		for (int i = 0; i < assets.size(); i++) {
			Asset a_i = assets.get(i);
			Asset a = a_i;
			for (int j = 0; j < assets.size(); j++) {
				Asset a_j = assets.get(j);
				if (i != j && a_i.equals(a_j)) {
					a = Asset.select(a_i, a_j);
				}
			}
			if (!assets_filtered.contains(a)) {
				assets_filtered.add(a);
			}
		}
		this.pseudo_portfolio = new Portfolio(portfolio.getId(), assets_filtered);
	}

	/**
	 * Full optimization scheme run function.
	 */
	private void run() {
		try {
			this.write();
			this.make_temp_runnable();
			this.runPythonFile();
			this.gatherStakes();
		} catch (Exception e) {
			this.uniform();
		}
	}

	/**
	 * Uniform stakes distributions.
	 */
	private void uniform() {
		double stake = (double) 1 / this.pseudo_portfolio.getAssets().size();
		for (Asset a : this.pseudo_portfolio.getAssets()) {
			a.setStake(stake);
		}
	}

	/**
	 * Write out pseudo-portfolio values.
	 * 
	 * @throws IOException
	 *             in case the file is not found.
	 */
	private void write() throws IOException {
		String datapath = Path.results_opt + "not_optimized.txt";
		PrintWriter pw = new PrintWriter(datapath);
		for (Asset a : this.pseudo_portfolio.getAssets()) {
			String dataline = "Asset," + a.getGame_id() + "," + a.getModel_id() + "," + a.getBookie_id() + ","
					+ a.getExpected() + "," + a.getRisk() + "," + a.getStake()+","+a.getE_type();
			pw.println(dataline);
		}
		pw.close();
	}

	/**
	 * Create Python-based runnable for portfolio optimization.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void make_temp_runnable() throws IOException, InterruptedException {
		String datapath = Path.results_opt + "OPT.py";
		PrintWriter pw = new PrintWriter(datapath);
		pw.println();
		pw.println("from Optimizer import *");
		pw.println();
		pw.println("optimize('" + Path.results_opt + "not_optimized.txt', " + this.importance + ", '" + Path.results_opt
				+ "optimized.txt')");
		pw.close();
		return;
	}

	/**
	 * Runs the created Python-based runnable.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws CollectorException
	 */
	private void runPythonFile() throws IOException, InterruptedException, CollectorException {
		ProcessBuilder pb = new ProcessBuilder("/Users/andreastheys/anaconda/bin/python", Path.results_opt + "OPT.py");
		Process p = pb.start();
		BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		if (bre.readLine() != null) {
			throw new CollectorException("No optimization process.");
		}
		bre.close();
		p.waitFor();
	}

	/**
	 * Renews pseudo-portfolio with optimized stake values.
	 * 
	 * @throws IOException
	 */
	private void gatherStakes() throws IOException {
		String stakes = Path.results_opt + "optimized.txt";
		BufferedReader br = new BufferedReader(new FileReader(stakes));
		ArrayList<Asset> assets = new ArrayList<Asset>();
		String line;
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			assert data.length == 7;
			if (data[0].equals("Asset")) {
				String game_id = data[1];
				String model_id = data[3];
				String bookie_id = data[3];
				double expected = Double.parseDouble(data[4]);
				double risk = Double.parseDouble(data[5]);
				double stake = Double.parseDouble(data[6]);
				String e_type = data[7];
				Asset a = new Asset(game_id, model_id, bookie_id, expected, risk, e_type);
				a.setStake(stake);
				assets.add(a);
			}
		}
		br.close();
		this.pseudo_portfolio.setAssets(assets);
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public Portfolio getPseudo_portfolio() {
		return pseudo_portfolio;
	}

	public double getImportance() {
		return importance;
	}

	public GameDay getBegin() {
		return begin;
	}

	public boolean unique() {
		boolean eval = true;
		for (int i = 0; i < this.pseudo_portfolio.getAssets().size(); i++) {
			for (int j = 0; j < i; j++) {
				if (i != j) {
					Asset ai = this.pseudo_portfolio.getAssets().get(i);
					Asset aj = this.pseudo_portfolio.getAssets().get(j);
					eval = eval && !(ai.getGame_id().equals(aj.getGame_id()));
				}
			}
		}
		return eval;
	}

	/**
	 * Private Portfolio Filter Class.
	 * 
	 * @author Andreas Theys.
	 * @version 1.0
	 */
	private static class PFilter {
		public static Portfolio filter(Portfolio p, double min_ret) {
			ArrayList<Asset> assets = new ArrayList<Asset>();
			for (Asset a : p.getAssets()) {
				if (a.getExpected() >= min_ret) {
					assets.add(a);
				}
			}
			Portfolio p_new = new Portfolio(p);
			p_new.setAssets(assets);
			return p_new;
		}
	}

}