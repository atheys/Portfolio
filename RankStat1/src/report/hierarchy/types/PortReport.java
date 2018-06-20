package report.hierarchy.types;

import data.core.structure.Game;
import mathematics.portfolio.Asset;
import models.general.performance.Prediction;
import report.elements.StandardCreator;
import report.elements.TableCreator;
import report.hierarchy.Report;
import report.hierarchy.ReportFunctions;
import simulation.analyze.evaluation.Evaluator;
import simulation.process.POptimizer;
import simulation.simulate.Simulation;

/**
 * Portfolio Report Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class PortReport extends Report implements ReportFunctions {

	/**
	 * Class attributes.
	 */
	private POptimizer po;

	/**
	 * General constructor.
	 * 
	 * @param name
	 *            report name.
	 * @param s
	 *            report Simulation-Object.
	 * @param version
	 *            software version.
	 * @param generation
	 *            model generation.
	 * @param po
	 *            relevant POptimizer-Object.
	 */
	public PortReport(String name, Simulation s, double version, int generation, POptimizer po) {
		super(name, s, version, generation);
		this.po = po;
		this.complementTitle();
		this.makeGeneralInformation();
		this.makePortfolioAssetList();
		this.makePseudoPortfolioAssetList();
	}

	/**
	 * Title complementation.
	 */
	public void complementTitle() {
		this.title += "\\begin{center} \n" + "\\textsc{\\LARGE \\textbf{Portfolio Report} \\\\[0.2cm]} \n"
				+ "\\textsc{\\large {Financial Details $\\&$ Evaluation}} \n" + "\\end{center} \n";
	}

	/**
	 * Creates General Information section.
	 */
	private void makeGeneralInformation() {
		String gi = new String();
		gi += StandardCreator.makeSection("General Information");
		String[][] elements = { { "ID", this.po.getPortfolio().getId() },
				{ "Portfolio Size", "" + this.po.getPortfolio().getAssets().size() },
				{ "Pseudo-Portfolio Size", "" + this.po.getPseudo_portfolio().getAssets().size() },
				{ "Variance Importance", "" + this.po.getImportance() } };
		gi += TableCreator.createFeatureTable(elements);
		this.sections.add(gi);
	}

	/**
	 * Creates Portfolio Asset List section.
	 */
	private void makePortfolioAssetList() {
		String pal = new String();
		pal += StandardCreator.makeSection("Portfolio Asset List");
		String[] titles = { "Game ID", "Bookie ID", "Expected Return", "Risk" };
		if (this.po.getPortfolio().getAssets().size() == 0)
			return;
		String[][] elements = new String[this.po.getPortfolio().getAssets().size()][4];
		for (int i = 0; i < this.po.getPortfolio().getAssets().size(); i++) {
			Asset a = this.po.getPortfolio().getAssets().get(i);
			elements[i][0] = a.getGame_id();
			elements[i][1] = a.getBookie_id();
			elements[i][2] = "" + a.getExpected();
			elements[i][3] = "" + a.getRisk();
		}
		pal += TableCreator.createStandardTable(titles, elements);
		this.sections.add(pal);
	}

	/**
	 * Creates pseudo-Portfolio Asset List section.
	 */
	private void makePseudoPortfolioAssetList() {
		String pal = new String();
		pal += StandardCreator.makeSection("Pseudo-Portfolio Asset List");
		String[] titles = { "Game ID", "Bookie ID", "Expected Return", "Risk", "Stake", "Odds Return" };
		if (this.po.getPseudo_portfolio().getAssets().size() == 0)
			return;
		String[][] elements = new String[this.po.getPseudo_portfolio().getAssets().size()][6];
		boolean[][] green = new boolean[this.po.getPseudo_portfolio().getAssets().size()][6];
		boolean[][] red = new boolean[this.po.getPseudo_portfolio().getAssets().size()][6];
		for (int i = 0; i < this.po.getPseudo_portfolio().getAssets().size(); i++) {
			Asset a = this.po.getPseudo_portfolio().getAssets().get(i);
			elements[i][0] = a.getGame_id();
			elements[i][1] = a.getBookie_id();
			elements[i][2] = "" + a.getExpected();
			elements[i][3] = "" + a.getRisk();
			elements[i][4] = "" + (100. * a.getStake()) + "$\\%$";
			double odds = 0.;
			try {
				Evaluator e = this.s.findGame(a.getGame_id());
				Prediction p = e.findPrediction(a.getModel_id());
				if (p.isRelevant() && p.evaluate(this.s.getEvaluation_type())) {
					double[] h_odds = e.highestOdds();
					Game g = e.getGame();
					if (g.won(g.getHome())) {
						odds = h_odds[0];
					}
					if (g.drew(g.getHome())) {
						odds = h_odds[1];
					}
					if (g.won(g.getAway())) {
						odds = h_odds[2];
					}
				}
			} catch (Exception e) {
				// Logger capacity
			}
			elements[i][5] = "" + odds;
			if (odds > 0.) {
				green[i][5] = true;
			} else {
				red[i][5] = true;
			}
		}
		pal += TableCreator.createColoredTable(titles, elements, green, red);
		this.sections.add(pal);
	}

	/**
	 * POptimizer getter.
	 * 
	 * @return corresponding POptimizer-Object.
	 */
	public POptimizer getPo() {
		return po;
	}

}