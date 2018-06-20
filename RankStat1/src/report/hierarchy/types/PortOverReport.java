package report.hierarchy.types;

import java.util.ArrayList;

import report.elements.StandardCreator;
import report.elements.TableCreator;
import report.hierarchy.Report;
import report.hierarchy.ReportFunctions;
import simulation.process.POptimizer;
import simulation.simulate.Dividend;
import simulation.simulate.Simulation;

/**
 * Portfolio Overview Report Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class PortOverReport extends Report implements ReportFunctions {

	/**
	 * Class attributes.
	 */
	private ArrayList<POptimizer> po;
	private Dividend d;
	private ArrayList<Double> values;
	private ArrayList<Double> dividends;

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
	 *            POptimizer-list of evolution.
	 * @param d
	 *            relevant Dividend-Object.
	 * @param values
	 *            relevant values list.
	 * @param dividends
	 *            relevant dividends list.
	 */
	public PortOverReport(String name, Simulation s, double version, int generation, ArrayList<POptimizer> po,
			Dividend d, ArrayList<Double> values, ArrayList<Double> dividends) {
		super(name, s, version, generation);
		this.po = po;
		this.d = d;
		assert po.size() == values.size() && po.size() == dividends.size();
		this.values = values;
		this.dividends = dividends;
		this.complementTitle();
		this.makeGeneralInformation();
		this.makeEvolutionaryHistory();
	}

	/**
	 * Title complementation.
	 */
	public void complementTitle() {
		this.title += "\\begin{center} \n" + "\\textsc{\\LARGE \\textbf{Portfolio Overview Report}\\\\[0.2cm]} \n"
				+ "\\textsc{\\large \\textbf{Evolutionary History Scope}} \n" + "\\end{center} \n";
	}

	/**
	 * Creates General Information section.
	 */
	private void makeGeneralInformation() {
		String gi = new String();
		gi += StandardCreator.makeSection("General Information");
		String[][] elements = { { "Portfolio ID", this.po.get(0).getPortfolio().getId() },
				{ "Periodic Investment", "" + (100. * this.d.getInvest()) + "$\\%$" },
				{ "Profit Reinvestment", "" + (100. * this.d.getReinvest()) + "$\\%$" },
				{ "Dividend Return", "" + (100. * this.d.getDividend_return()) + "$\\%$" } };
		gi += TableCreator.createFeatureTable(elements);
		this.sections.add(gi);
	}

	/**
	 * Creates Evolutionary History section.
	 */
	private void makeEvolutionaryHistory() {
		String eh = new String();
		eh += StandardCreator.makeSection("Evolutionary History");
		String[] titles = { "Period", "Date","Growth", "Dividend" };
		if (this.po.size() == 0)
			return;
		String[][] elements = new String[this.po.size()][4];
		boolean[][] green = new boolean[this.po.size()][4];
		boolean[][] red = new boolean[this.po.size()][4];
		for (int i = 0; i < this.po.size(); i++) {
			POptimizer p = this.po.get(i);
			elements[i][0] = "" + (i + 1);
			elements[i][1] = p.getBegin().toString();
			elements[i][2] = "" + (100. * this.values.get(i) + "$\\%$");
			elements[i][3] = "" + (100. * this.dividends.get(i) + "$\\%$");
			green[i][2] = this.values.get(i) > 0.;
			red[i][2] = this.values.get(i) < 0.;
		}
		eh += TableCreator.createColoredTable(titles, elements, green, red);
		this.sections.add(eh);
	}

}