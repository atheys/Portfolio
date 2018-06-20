package report.hierarchy.types;

import java.util.ArrayList;
import data.core.books.Odds;
import data.core.stats.Defense;
import data.core.stats.Offense;
import data.core.structure.Game;
import models.general.performance.Prediction;
import report.elements.StandardCreator;
import report.elements.TableCreator;
import report.hierarchy.Report;
import report.hierarchy.ReportFunctions;
import simulation.analyze.body.ModelCap;
import simulation.analyze.evaluation.Evaluator;
import simulation.simulate.Simulation;

/**
 * GameReport class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class GameReport extends Report implements ReportFunctions {

	/**
	 * Class attributes.
	 */
	private Evaluator e;

	/**
	 * General constructor.
	 * 
	 * @param name
	 *            report name.
	 * @param s
	 *            relevant Simulation-Object.
	 * @param version
	 *            software version.
	 * @param generation
	 *            model generation.
	 * @param e
	 *            relevant Evaluator-Object.
	 */
	public GameReport(String name, Simulation s, double version, int generation, Evaluator e) {
		super(name, s, version, generation);
		this.e = e;
		this.complementTitle();
		this.makeGeneralInformation();
		this.makeGameStatistics();
		this.makeBookieOddsData();
		try {
			this.makeModelDataOutline();
		} catch (Exception E) {
			// Logger capacity
		}
	}

	/**
	 * Title complementation.
	 */
	public void complementTitle() {
		this.title += "\\begin{center} \n" + "\\textsc{\\LARGE \\textbf{Game Technical Report}\\\\[0.2cm]} \n"
				+ "\\textsc{\\large {Stats, Bookie Odds $\\&$ Model Data Outlines}} \n" + "\\end{center} \n";
	}

	/**
	 * Creates General Information section.
	 */
	private void makeGeneralInformation() {
		String gi = new String();
		gi += StandardCreator.makeSection("General Information");
		Game g = this.e.getGame();
		String[][] elements = { { "ID", g.getId() }, { "Date", g.getGameDay().toString() },
				{ "Home Team", g.getHome().getName() }, { "Away Team", g.getAway().getName() },
				{ "Score", g.getHomeScore() + "-" + g.getAwayScore() } };
		gi += TableCreator.createFeatureTable(elements);
		this.sections.add(gi);
	}

	/**
	 * Creates Offensive Statistics section String
	 * 
	 * @param o
	 *            relevant Offense-Object.
	 * @return corresponding section String.
	 */
	private String makeOffensiveStatsTable(Offense o) {
		String ost = new String();
		String[][] elements = { { "Goals", "" + o.getGoals() }, { "Possession", "" + o.getPossession() },
				{ "Success Passes", "" + o.getSuccessPasses() }, { "Total Passes", "" + o.getTotalPasses() },
				{ "Pass Success Rate", "" + o.getPassSuccess() }, { "Aerials Won", "" + o.getAerialsWon() },
				{ "Shots", "" + o.getShots() }, { "Shots on Target", "" + o.getShotsOnTarget() },
				{ "Dribbles", "" + o.getDribbles() }, { "Fouled", "" + o.getFouled() },
				{ "Offsides", "" + o.getOffSides() } };
		ost += TableCreator.createStatisticsTable(elements);
		return ost;
	}

	/**
	 * Creates Defensive Statistics section String
	 * 
	 * @param d
	 *            relevant Defense-Object.
	 * @return corresponding section String.
	 */
	private String makeDefensiveStatsTable(Defense d) {
		String dst = new String();
		String cs = (d.isCleanSheet()) ? "YES" : "NO";
		String[][] elements = { { "Tackles", "" + d.getTackles() }, { "Fouls", "" + d.getFouls() },
				{ "Offsides Against", "" + d.getOffsidesAgainst() }, { "Clean Sheet?", cs },
				{ "Yellow Cards", "" + d.getYellowCards() }, { "Red Cards", "" + d.getRedCards() },
				{ "Substitutions", "" + d.getSubstitutions() } };
		dst += TableCreator.createStatisticsTable(elements);
		return dst;
	}

	/**
	 * Creates Game Statistics section.
	 */
	private void makeGameStatistics() {
		String gs = new String();
		Game g = this.e.getGame();
		gs += StandardCreator.makeSection("Game Statistics");
		gs += StandardCreator.makeSubSection("Home Team Statistics");
		gs += StandardCreator.makeSubSubSection("Offensive Statistics");
		gs += makeOffensiveStatsTable(g.getHomeStatsOff());
		gs += StandardCreator.makeSubSubSection("Defensive Statistics");
		gs += makeDefensiveStatsTable(g.getHomeStatsDef());
		gs += StandardCreator.makeSubSection("Away Team Statistics");
		gs += StandardCreator.makeSubSubSection("Offensive Statistics");
		gs += makeOffensiveStatsTable(g.getAwayStatsOff());
		gs += StandardCreator.makeSubSubSection("Defensive Statistics");
		gs += makeDefensiveStatsTable(g.getAwayStatsDef());
		this.sections.add(gs);
	}

	/**
	 * Creates Bookies Odds Data section.
	 */
	private void makeBookieOddsData() {
		String bod = new String();
		bod += StandardCreator.makeSection("Bookie Odds Data");
		String[] titles = { "Bookie ID", "Bookie Name", "Home Win", "Draw", "Away Win" };
		String[][] elements;
		boolean[][] green;
		boolean[][] red;
		int rows = this.e.getOdds().size();
		int columns = 5;
		if (rows > 0) {
			elements = new String[this.e.getOdds().size()][columns];
			green = new boolean[this.e.getOdds().size()][columns];
			red = new boolean[this.e.getOdds().size()][columns];
		} else {
			return;
		}
		for (int i = 0; i < this.e.getOdds().size(); i++) {
			Odds o = this.e.getOdds().get(i);
			elements[i][0] = "" + o.getBookie().getId();
			elements[i][1] = "" + o.getBookie().getName();
			elements[i][2] = "" + o.getWinHome();
			elements[i][3] = "" + o.getDraw();
			elements[i][4] = "" + o.getWinAway();
		}
		boolean home = this.e.getGame().won(this.e.getGame().getHome());
		boolean draw = this.e.getGame().drew(this.e.getGame().getHome());
		boolean away = this.e.getGame().won(this.e.getGame().getAway());
		for (int i = 0; i < green.length; i++) {
			green[i][2] = home;
			green[i][3] = draw;
			green[i][4] = away;
			red[i][2] = !home;
			red[i][3] = !draw;
			red[i][4] = !away;
		}
		bod += TableCreator.createColoredTable(titles, elements, green, red);
		this.sections.add(bod);
	}

	/**
	 * Color String helper function.
	 * 
	 * @param eval
	 *            indicator.
	 * @return corresponding color String.
	 */
	private String color(boolean eval) {
		if (eval)
			return "\\cellcolor{green!35}";
		return "\\cellcolor{red!35}";
	}

	/**
	 * Creates Model Data Outline section.
	 */
	private void makeModelDataOutline() {
		String mdo = new String();
		mdo += StandardCreator.makeSection("Model Data Outline");
		String[] titles = { "Model ID", "Home Win", "Draw", "Away Win", "M", "O", "F", "E" };
		int rows = this.e.getSuper_models().size()+1;
		int columns = 8;
		if (rows == 0)
			return;
		ArrayList<ModelCap> models = new ArrayList<ModelCap>();
		try {
			for (ModelCap mc : this.e.getSuper_models()) {
				models.add(mc);
			}
			models.add(this.e.getFinal_model());
		} catch (Exception e) {
		}
		String[][] elements = new String[rows][columns];
		for (int i = 0; i < elements.length; i++) {
			elements[i][0] = models.get(i).getModel_id();
			elements[i][1] = "" + models.get(i).getHome();
			elements[i][2] = "" + models.get(i).getDraw();
			elements[i][3] = "" + models.get(i).getAway();
			Prediction p = this.e.findPrediction(models.get(i).getModel_id());
			if (p.isRelevant()) {
				elements[i][4] = color(p.isEvaluation_M());
				elements[i][5] = color(p.isEvaluation_O());
				elements[i][6] = color(p.isEvaluation_F());
				elements[i][7] = color(p.isEvaluation_E());
			} else {
				elements[i][4] = color(false);
				elements[i][5] = color(false);
				elements[i][6] = color(false);
				elements[i][7] = color(false);
			}
		}
		mdo += TableCreator.createStandardTable(titles, elements);
		this.sections.add(mdo);
	}

	/**
	 * Evaluator getter.
	 * 
	 * @return report Evaluator-Object.
	 */
	public Evaluator getE() {
		return e;
	}

}