package simulation.simulate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import data.core.paths.Path;
import data.core.structure.Competition;
import data.core.structure.Game;
import data.core.structure.GameDay;
import data.core.structure.Team;
import exceptions.NotFoundException;
import mathematics.portfolio.Asset;
import mathematics.portfolio.Portfolio;
import simulation.analyze.body.ModelCap;
import simulation.analyze.evaluation.Evaluator;
import simulation.analyze.evaluation.Performator;
import simulation.analyze.selection.Capsule;
import simulation.except.Except;
import simulation.process.AssetCreator;
import simulation.process.MFilter;
import simulation.process.POptimizer;
import simulation.read.Reader;

/**
 * Main Simulation class.
 * 
 * @author Andreas Theys.
 * @version 8.0
 */
public class Simulation {

	/**
	 * Simulation attributes.
	 */
	private boolean executed;
	// [1-4] Main Features
	private String serial_number; // Auto-generated (see method)
	private Capsule data_capsule; // See Manifest-Class
	private boolean ex; // Auto-generated
	private Except except; // Competition-specific
	private Competition competition; // Auto-read-in
	private GameDay begin_date; // Auto-generated
	private ArrayList<GameDay> dates; // Automatically updated
	private ArrayList<ArrayList<Evaluator>> periods; // Auto-arranged
	private String evaluation_type; // Usually E or F
	private int min_evals; // Higher than 1
	// [5] Statistical Matching Features
	private double minimum_percentage; // Choice between 0.5, 0.55 and 0.6
	private String[] filter_scheme; // Usually O & D
	private String[] models;
	private int n_max; // Between 5 and 8
	// [6] Dynamic Performance Features
	private int backtrack; // Between 1 and 5
	private double var_importance; // Taken at 0.15
	// [7*] Financial Portfolio Features (hidden variables)
	private boolean port_optimization;
	private double min_return;
	private ArrayList<ArrayList<POptimizer>> portfolios;

	/**
	 * Default constructor.
	 */
	public Simulation() {
		this.executed = false;
	}

	/**
	 * General Simulation Constructor.
	 * 
	 * @param data_capsule
	 *            Competition data Capsule-Object.
	 * @param except
	 *            Except-body.
	 * @param evaluation_type
	 *            type of performance evaluation.
	 * @param min_perc
	 *            minimum required matching percentage.
	 * @param filter_scheme
	 *            data-filtering schemes.
	 * @param n_max
	 *            maximum number of games to backtrack for.
	 * @param backtrack
	 *            maximum number of periods to backtrack for.
	 * @param min_evals
	 *            minimally required number of games per period.
	 */
	public Simulation(Capsule data_capsule, Except except, String evaluation_type, String[] models, int min_evals,
			double min_perc, String[] filter_scheme, int n_max, int backtrack, double var_importance) {
		this.executed = true;
		// [1] First Simulation Phase
		this.data_capsule = data_capsule;
		this.except = except;
		this.ex = this.except.check();
		this.determineBDate();
		this.evaluation_type = evaluation_type;
		this.minimum_percentage = min_perc;
		this.filter_scheme = filter_scheme;
		this.models = models;
		this.n_max = n_max;
		this.backtrack = backtrack;
		this.min_evals = min_evals;
		this.var_importance = var_importance;
		System.out.println("First Simulation Phase Complete: All Variables Assigned.");
		// [2] Second Simulation Phase
		this.createSerialNumber(false);
		System.out.println("Second Simulation Phase Complete: Serial Label Created - " + this.serial_number);
		// [3] Third Simulation Phase
		this.readCompetition();
		System.out.println("Third Simulation Phase Complete: Competition-Object Read In.");
		// [4] Fourth Simulation Phase
		this.arrangePeriods();
		System.out.println("Fourth Simulation Phase Complete: All Game-Objects Arranged in Periods.");
		// [5] Fifth Simulation Phase (Statistic Filtering)
		long t0 = System.currentTimeMillis();
		System.out.println("===== INITIATE Statistic Filtering Sequence =====");
		this.filter();
		System.out.println("===== TERMINATE Statistic Filtering Sequence =====");
		System.out.println("Fifth Simulation Phase Complete: Statistical Filtering Applied. ["
				+ 0.001 * (System.currentTimeMillis() - t0) + " sec]");
		// [6] SIxth Simulation Phase (Dynamic Performance)
		t0 = System.currentTimeMillis();
		System.out.println("===== INITIATE Dynamic Performance Sequence =====");
		this.performance();
		System.out.println("===== TERMINATE Dynamic Performance Sequence =====");
		System.out.println("Sixth Simulation Phase Complete: Dynamic Performance Computed. ["
				+ 0.001 * (System.currentTimeMillis() - t0) + " sec]");
	}

	/**
	 * Determines begin date of the simulation.
	 */
	private void determineBDate() {
		switch (this.data_capsule.season) {
		case "0910":
			this.begin_date = SimulationSettings.begin_date_2009;
			break;
		case "1011":
			this.begin_date = SimulationSettings.begin_date_2010;
			break;
		case "1112":
			this.begin_date = SimulationSettings.begin_date_2011;
			break;
		case "1213":
			this.begin_date = SimulationSettings.begin_date_2012;
			break;
		case "1314":
			this.begin_date = SimulationSettings.begin_date_2013;
			break;
		case "1415":
			this.begin_date = SimulationSettings.begin_date_2014;
			break;
		case "1516":
			this.begin_date = SimulationSettings.begin_date_2015;
			break;
		case "1617":
			this.begin_date = SimulationSettings.begin_date_2016;
			break;
		default:
			this.begin_date = new GameDay(2, 7, 2008);
			break;
		}
	}

	/**
	 * Appends minimum percentage feature.
	 * 
	 * @param sn
	 *            preliminary serial number String.
	 */
	private String minPercAppendix(String sn) {
		if (this.minimum_percentage == 0.5) {
			sn += "1|2-";
			return sn;
		}
		if (this.minimum_percentage == 0.55) {
			sn += "11|20-";
			return sn;
		}
		if (this.minimum_percentage == 0.6) {
			sn += "3|5-";
			return sn;
		}
		sn += "0|0-";
		return sn;
	}

	/**
	 * Appends variance importance feature.
	 * 
	 * @param sn
	 *            preliminary serial number String.
	 */
	private String varAppendix(String sn) {
		if (this.var_importance == 0.1) {
			sn += "1|10";
			return sn;
		}
		if (this.var_importance == 0.11) {
			sn += "11|100";
			return sn;
		}
		if (this.var_importance == 0.12) {
			sn += "3|25";
			return sn;
		}
		if (this.var_importance == 0.13) {
			sn += "13|100";
			return sn;
		}
		if (this.var_importance == 0.14) {
			sn += "7|50";
			return sn;
		}
		if (this.var_importance == 0.15) {
			sn += "3|20";
			return sn;
		}
		if (this.var_importance == 0.16) {
			sn += "4|25";
			return sn;
		}
		if (this.var_importance == 0.17) {
			sn += "17|100";
			return sn;
		}
		if (this.var_importance == 0.18) {
			sn += "9|50";
			return sn;
		}
		if (this.var_importance == 0.19) {
			sn += "19|100";
			return sn;
		}
		if (this.var_importance == 0.2) {
			sn += "1|5";
			return sn;
		}
		sn += "0|0";
		return sn;
	}

	/**
	 * Appends minimum return feature.
	 * 
	 * @param sn
	 *            preliminary serial number String.
	 */
	private String returnAppendix(String sn) {
		if (this.min_return == 0.) {
			sn += "0|1";
			return sn;
		}
		if (this.min_return == 0.05) {
			sn += "1|20";
			return sn;
		}
		if (this.min_return == 0.1) {
			sn += "1|10";
			return sn;
		}
		if (this.min_return == 0.15) {
			sn += "3|20";
			return sn;
		}
		if (this.min_return == 0.2) {
			sn += "1|5";
			return sn;
		}
		if (this.min_return == 0.25) {
			sn += "1|4";
			return sn;
		}
		if (this.min_return == 0.3) {
			sn += "3|10";
			return sn;
		}
		if (this.min_return == 0.35) {
			sn += "7|20";
			return sn;
		}
		if (this.min_return == 0.4) {
			sn += "2|5";
			return sn;
		}
		if (this.min_return == 0.45) {
			sn += "9|20";
			return sn;
		}
		if (this.min_return == 0.5) {
			sn += "1|2";
			return sn;
		}
		if (this.min_return == 0.55) {
			sn += "11|20";
			return sn;
		}
		if (this.min_return == 0.6) {
			sn += "3|5";
			return sn;
		}
		if (this.min_return == 0.65) {
			sn += "13|20";
			return sn;
		}
		if (this.min_return == 0.7) {
			sn += "7|10";
			return sn;
		}
		if (this.min_return == 0.75) {
			sn += "3|4";
			return sn;
		}
		if (this.min_return == 0.8) {
			sn += "4|5";
			return sn;
		}
		if (this.min_return == 0.85) {
			sn += "17|20";
			return sn;
		}
		if (this.min_return == 0.9) {
			sn += "9|10";
			return sn;
		}
		if (this.min_return == 0.95) {
			sn += "19|20";
			return sn;
		}
		if (this.min_return == 1.) {
			sn += "1|1";
			return sn;
		}
		sn += "0|0";
		return sn;
	}

	/**
	 * Creates serial label/number.
	 * 
	 * @param number
	 *            label/number indicator.
	 */
	public void createSerialNumber(boolean number) {
		this.serial_number = new String();
		String sn = new String();
		sn += this.evaluation_type + "-";
		sn += this.min_evals + "-";
		sn = this.minPercAppendix(sn);
		for (String s : this.filter_scheme) {
			sn += s;
		}
		sn += "-";
		sn += this.n_max + "-";
		sn += this.backtrack + "-";
		sn = this.varAppendix(sn);
		if (number) {
			sn += "-";
			if (this.port_optimization) {
				sn += "T-";
			} else {
				sn += "F-";
			}
			sn = this.returnAppendix(sn);
		}
		this.serial_number = sn;
	}

	/**
	 * Determines first game day.
	 * 
	 * @return chronologically first GameDay-Object.
	 */
	public GameDay first() {
		GameDay f = new GameDay(1, 1, 2100);
		for (GameDay gd : this.competition.getGameDays()) {
			if (gd.before(f))
				f = gd;
		}
		return f;
	}

	/**
	 * Determines last game day.
	 * 
	 * @return chronologically last GameDay-Object.
	 */
	public GameDay last() {
		GameDay f = new GameDay(1, 1, 1900);
		for (GameDay gd : this.competition.getGameDays()) {
			if (gd.after(f))
				f = gd;
		}
		return f;
	}

	private int index(Game g) {
		int ind = -1;
		for (int i = 0; i < this.dates.size() - 1; i++) {
			if (g.getGameDay().after(this.dates.get(i))
					&& (g.getGameDay().before(this.dates.get(i + 1)) || g.getGameDay().equals(this.dates.get(i + 1)))) {
				ind = i;
				break;
			}
		}
		return ind;
	}

	private int indexInverse(Game g) {
		int ind = -1;
		for (int i = this.dates.size() - 2; i >= 0; i--) {
			if (g.getGameDay().after(this.dates.get(i))
					&& (g.getGameDay().before(this.dates.get(i + 1)) || g.getGameDay().equals(this.dates.get(i + 1)))) {
				ind = i;
				break;
			}
		}
		return ind;
	}

	/**
	 * Collects all game-related Evaluator-Objects within a certain period.
	 * 
	 * @return corresponding list of Evaluator-Objects.
	 */
	private void collect() {
		int N = this.competition.getGames().size();
		List<Game> games = this.competition.getGames();
		for (int i = 0; i < games.size(); i++) {
			Game g = games.get(i);
			int ind = -1;
			if (i < N / 2) {
				ind = this.index(g);
			} else {
				ind = this.indexInverse(g);
			}
			if (ind > -1 && g.predictable()) {
				Evaluator e = new Evaluator(g, this.var_importance);
				if (e.getUnit_models().size() > 0 && e.getOdds().size() > 0) {
					this.periods.get(ind).add(e);
				}
			}
		}
	}

	/**
	 * Reads in fully updated competition data through static Reader-class
	 * methods.
	 */
	private void readCompetition() {
		try {
			this.competition = Reader.READ(this.data_capsule);
		} catch (Exception e) {
			// Logger capacity
		}

	}

	/**
	 * Arranges/Fixes all game-related Evaluator-Objects in discrete periods.
	 */
	private void arrangePeriods() {
		this.dates = new ArrayList<GameDay>();
		this.periods = new ArrayList<ArrayList<Evaluator>>();
		GameDay temp = new GameDay(this.begin_date);
		while (temp.before(this.last())) {
			this.dates.add(temp);
			this.periods.add(new ArrayList<Evaluator>());
			temp = new GameDay(temp.plus(7));
		}
		this.dates.add(temp);
		this.collect();
		this.dates.remove(this.dates.size() - 1);
		for (int i = this.periods.size() - 1; i >= 0; i--) {
			if (this.periods.get(i).size() < this.min_evals) {
				this.dates.remove(i);
				this.periods.remove(i);
			}
		}
		System.out.println(this.periods.size());
	}

	/**
	 * Match filtering component.
	 */
	private void filter() {
		for (int i = 0; i < this.periods.size(); i++) {
			for (Evaluator e : this.periods.get(i)) {
				MFilter.FILTER( e,this.filter_scheme, this.models,this.n_max,this.minimum_percentage);
			}
		}
	}

	private void store() throws IOException {
		String path = Path.results_base + this.serial_number + "/" + this.data_capsule.competition + "/"
				+ this.data_capsule.season + "/";
		new File(path).mkdirs();
		for (ArrayList<Evaluator> evals : this.periods) {
			for (Evaluator e : evals) {
				String datapath = path + e.getGame().getId() + ".txt";
				PrintWriter pw = new PrintWriter(datapath);
				String dline;
				for (ModelCap mc : e.getSuper_models()) {
					dline = "Model," + mc.getGame_id() + "," + mc.getModel_id() + "," + mc.getHome() + ","
							+ mc.getDraw() + "," + mc.getAway();
					pw.println(dline);
				}
				ModelCap m = e.getFinal_model();
				dline = "Model," + m.getGame_id() + "," + m.getModel_id() + "," + m.getHome() + "," + m.getDraw()
							+ "," + m.getAway();
				pw.println(dline);
				
				
				pw.close();
			}
		}
	}

	private void gatherModels(String datapath, Evaluator e) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(datapath));
		String line;
		ArrayList<ModelCap> models = new ArrayList<ModelCap>();
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			if (data[0].equals("Model")) {
				String[] model_name = data[2].split("-");
				if (model_name.length == 1) {
					double home = Double.parseDouble(data[3]);
					double draw = Double.parseDouble(data[4]);
					double away = Double.parseDouble(data[5]);
					ModelCap mc = new ModelCap(data[2], data[1], home, draw, away);
					e.setFinal_model(mc);
				}
				if (model_name.length == 2) {
					double home = Double.parseDouble(data[3]);
					double draw = Double.parseDouble(data[4]);
					double away = Double.parseDouble(data[5]);
					ModelCap mc = new ModelCap(data[2], data[1], home, draw, away);
					models.add(mc);	
				}

			}
		}
		br.close();
		e.setSuper_models(models);
	}

	/**
	 * Dynamic Performance component.
	 */
	private void performance() {
		try {
			String path = Path.results_base + this.serial_number + "/" + this.data_capsule.competition + "/"
					+ this.data_capsule.season + "/";
			for (ArrayList<Evaluator> evals : this.periods) {
				for (Evaluator e : evals) {
					String datapath = path + e.getGame().getId() + ".txt";
					gatherModels(datapath, e);
				}
			}
		} catch (Exception e) {
			Performator.PERFORM(this.periods, this.backtrack, this.n_max, this.evaluation_type);
			try {
				this.store();
			} catch (Exception e_store) {
				// Logger capacity
			}
		}
	}

	private ArrayList<String> modelsIDs() {
		ArrayList<String> IDs = new ArrayList<String>();
		for (ArrayList<Evaluator> evals : this.periods) {
			for (Evaluator e : evals) {
				for (ModelCap mc : e.getSuper_models()) {
					if (!IDs.contains(mc.getModel_id()))
						IDs.add(mc.getModel_id());
					
				}
				if (!IDs.contains(e.getFinal_model().getModel_id()))
					IDs.add(e.getFinal_model().getModel_id());
			}
		}
		return IDs;
	}

	private ArrayList<Asset> makeAllAssets(ArrayList<Evaluator> evals, String model_id) {
		ArrayList<Asset> assets = new ArrayList<Asset>();
		for (Evaluator e : evals) {
			Game g = e.getGame();
			ModelCap mc = e.findModel(model_id);
			if (!this.ex && mc != null) {
				if (mc.isRelevant()) {
					assets.addAll(AssetCreator.CREATE(mc, e.getOdds(), this.var_importance, this.evaluation_type, 15));
				}
			} else {
				String e_type = this.except.type(g, this.evaluation_type);
				if (mc != null && mc.isRelevant()) {
					assets.addAll(AssetCreator.CREATE(mc, e.getOdds(), this.var_importance, e_type, 15));
				}
			}
		}
		return assets;
	}

	public void makePortfolios() {
		this.portfolios = new ArrayList<ArrayList<POptimizer>>();
		ArrayList<String> IDs = modelsIDs();
		for (int i = 0; i < IDs.size(); i++) {
			this.portfolios.add(new ArrayList<POptimizer>());
		}
		for (int l = 0; l < this.periods.size(); l++) {
			for (int i = 0; i < IDs.size(); i++) {
				ArrayList<Asset> assets = makeAllAssets(periods.get(l), IDs.get(i));
				if (assets.size() > 0) {
					POptimizer po = new POptimizer(new Portfolio(IDs.get(i), assets), this.dates.get(l),
							this.var_importance, this.min_return, this.port_optimization);
					this.portfolios.get(i).add(po);
				} else {
				}
			}
		}
	}

	public boolean isExecuted() {
		return executed;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public Capsule getData_capsule() {
		return data_capsule;
	}

	public Competition getCompetition() {
		return competition;
	}

	public ArrayList<ArrayList<Evaluator>> getPeriods() {
		return periods;
	}

	public GameDay getBegin_date() {
		return begin_date;
	}

	public double getMin_perc() {
		return minimum_percentage;
	}

	public String[] getFilter_scheme() {
		return filter_scheme;
	}

	public int getMin_evals() {
		return min_evals;
	}

	public String getEvaluation_type() {
		return evaluation_type;
	}

	public int getBacktrack() {
		return backtrack;
	}

	public boolean isPort_optimization() {
		return port_optimization;
	}

	public double getVar_importance() {
		return var_importance;
	}

	public double getMin_return() {
		return min_return;
	}

	public ArrayList<GameDay> getDates() {
		return dates;
	}

	public int getN_max() {
		return n_max;
	}

	public void setVar_importance(double var_importance) {
		this.var_importance = var_importance;
	}

	public void setPort_optimization(boolean port_optimization) {
		this.port_optimization = port_optimization;
	}

	public void setMin_return(double min_return) {
		this.min_return = min_return;
	}

	/**
	 * Unit portfolios getter.
	 * 
	 * @return unit portfolios.
	 */
	public ArrayList<ArrayList<POptimizer>> getPortfolios() {
		return portfolios;
	}

	public Evaluator findGame(String game_id) throws NotFoundException {
		for (ArrayList<Evaluator> evals : this.periods) {
			for (Evaluator e : evals) {
				if (e.getGame().getId().equals(game_id)) {
					return e;
				}
			}
		}
		throw new NotFoundException("Corresponding Evaluator-Object not found.");
	}

	public ArrayList<POptimizer> findPortfolio(String id) {
		for (ArrayList<POptimizer> po : this.portfolios) {
			if (po.get(0).getPortfolio().getId().equals(id)) {
				return po;
			}
		}
		return new ArrayList<POptimizer>();
	}

	/**
	 * Determines is a Team-Object is involved in a particular game.
	 * 
	 * @note: helper method.
	 * @param t
	 *            relevant Team-Object.
	 * @param e
	 *            relevant Evaluator-Object.
	 * @return evaluation indicator.
	 */
	private boolean isInGame(Team t, Evaluator e) {
		return e.getGame().getHome().equals(t) || e.getGame().getAway().equals(t);
	}

	/**
	 * Searches arranged periods for games of a particular Team-Object.
	 * 
	 * @param t
	 *            relevant Team-Object.
	 * @return list of corresponding and arranged games.
	 */
	public ArrayList<Evaluator> findPeriodGames(Team t) {
		ArrayList<Evaluator> games = new ArrayList<Evaluator>();
		for (ArrayList<Evaluator> evals : this.periods) {
			for (Evaluator e : evals) {
				if (isInGame(t, e)) {
					games.add(e);
				}
			}
		}
		return games;
	}

	/**
	 * Searches arranged (pseudo-)portfolios for games of a particular
	 * Team-Object.
	 * 
	 * @param game_id
	 *            game ID feature.
	 * @return evaluation indicator.
	 */
	private boolean isInPortfolios(String game_id) {
		for (ArrayList<POptimizer> pos : this.portfolios) {
			for (POptimizer po : pos) {
				for (Asset a : po.getPseudo_portfolio().getAssets()) {
					if (a.getGame_id().equals(game_id)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Collects all games of a certain Team-Object that were financially
	 * evaluated.
	 * 
	 * @param t relevant Team-Object.
	 * @return corresponding list of Evaluator-Objects.
	 */
	public ArrayList<Evaluator> findPortfolioGames(Team t) {
		ArrayList<Evaluator> games = findPeriodGames(t);
		ArrayList<Evaluator> ports = new ArrayList<Evaluator>();
		for (Evaluator e : games) {
			if (isInPortfolios(e.getGame().getId())) {
				ports.add(e);
			}
		}
		return ports;
	}

}