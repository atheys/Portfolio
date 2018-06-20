package report.hierarchy.types;

import report.elements.StandardCreator;
import report.elements.TableCreator;
import report.hierarchy.Report;
import report.hierarchy.ReportFunctions;
import simulation.simulate.Simulation;

/**
 * Simulation Specification Report.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class SimSpecReport extends Report implements ReportFunctions {

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
	 */
	public SimSpecReport(String name, Simulation s, double version, int generation) {
		super(name, s, version, generation);
		this.complementTitle();
		this.makeGeneralInformation();
		this.makeMatchFilteringFeatures();
		this.makeDynamicPerformance();
		this.makePortfolioOptimizationFeatures();
	}

	/**
	 * Title complementation.
	 */
	public void complementTitle() {
		this.title += "\\begin{center} \n"
				+ "\\textsc{\\LARGE \\textbf{Simulation Specifications Report}\\\\[0.2cm]} \n"
				+ "\\textsc{\\large \\textbf{Detailed simulation features}} \n" + "\\end{center} \n";
	}

	/**
	 * Creates General Information section.
	 */
	private void makeGeneralInformation() {
		String gi = new String();
		gi += StandardCreator.makeSection("General Information");
		String[][] elements = { { "ID", this.s.getSerial_number() },
				{ "Begin Date", this.s.getBegin_date().toString() }, { "Competition", this.s.getCompetition().getId() },
				{ "Periods", "" + this.s.getPeriods().size() },
				{ "$\\mathrm{\\textbf{N}}_{\\mathrm{\\textbf{MAX}}}$-number", "" + this.s.getN_max() } };
		gi += TableCreator.createFeatureTable(elements);
		this.sections.add(gi);
	}

	/**
	 * Creates String of filtering scheme array.
	 * 
	 * @param scheme
	 *            filtering scheme array.
	 * @return corresponding String.
	 */
	private String makeArrayString(String[] scheme) {
		String array = new String();
		String line = new String();
		array += "$\\{";
		for (String s : scheme) {
			line += "," + s;
		}
		line = line.substring(1);
		array += line;
		array += "\\}$";
		return array;
	}

	/**
	 * Creates Match Filtering section.
	 */
	private void makeMatchFilteringFeatures() {
		String mff = new String();
		mff += StandardCreator.makeSection("Match Filtering Features");
		String filtering = "\\cellcolor{green!35} YES";
		String[][] elements = { { "Filtering?", filtering },
				{ "Filter Scheme", makeArrayString(this.s.getFilter_scheme()) },
				{ "Minimum Percentage", "" + this.s.getMin_perc() } };
		mff += TableCreator.createFeatureTable(elements);
		this.sections.add(mff);
	}

	/**
	 * Creates Dynamic Performance section.
	 */
	private void makeDynamicPerformance() {
		String dp = new String();
		dp += StandardCreator.makeSection("Dynamic Performance Features");
		String[][] elements = { { "Minimum Evaluations", "" + this.s.getMin_evals() },
				{ "Evaluation Type", this.s.getEvaluation_type() },
				{ "Backtrack Number", "" + this.s.getBacktrack() }, };
		dp += TableCreator.createFeatureTable(elements);
		this.sections.add(dp);
	}

	/**
	 * Creates Portfolio Optimization section.
	 */
	private void makePortfolioOptimizationFeatures() {
		String pof = new String();
		pof += StandardCreator.makeSection("Portfolio Optimization Features");
		String optimization = (this.s.isPort_optimization()) ? "\\cellcolor{green!35} YES" : "\\cellcolor{red!35} NO";
		String[][] elements = { { "Optimization?", optimization },
				{ "Variance Importance", "" + this.s.getVar_importance() },
				{ "Minimal Expected Return", "" + this.s.getMin_return() },
				{ "Portfolios", "" + this.s.getPortfolios().size() } };
		pof += TableCreator.createFeatureTable(elements);
		this.sections.add(pof);
	}

}