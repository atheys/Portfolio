package report.hierarchy.types;

import report.elements.StandardCreator;
import report.elements.TableCreator;
import report.hierarchy.Report;
import report.hierarchy.ReportFunctions;
import simulation.merge.MCapsule;
import simulation.merge.Merger;
import simulation.simulate.Simulation;

/**
 * Merger Specification Report Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class MergerSpecReport extends Report implements ReportFunctions {

	/**
	 * Class attributes.
	 */
	protected Merger m;

	/**
	 * General constructor.
	 * 
	 * @param name
	 *            report name.
	 * @param m
	 *            relevant Merger-Object.
	 * @param version
	 *            software version.
	 * @param generation
	 *            model generation.
	 */
	public MergerSpecReport(String name, Merger m, double version, int generation) {
		super(name, new Simulation(), version, generation);
		this.m = m;
		this.complementTitle();
		this.makeGeneralInformation();
		this.makeIncludedCompetitions();
	}

	/**
	 * Title complementation.
	 */
	public void complementTitle() {
		this.title += "\\begin{center} \n" + "\\textsc{\\LARGE \\textbf{Merger Specifications Report}\\\\[0.2cm]} \n"
				+ "\\textsc{\\large \\textbf{Detailed merger features}} \n" + "\\end{center} \n";
	}

	/**
	 * Creates General Information section.
	 */
	private void makeGeneralInformation() {
		String gi = new String();
		String optimization = (this.m.isOptimize()) ? "\\cellcolor{green!35} YES" : "\\cellcolor{red!35} NO";
		gi += StandardCreator.makeSection("General Information");
		String[][] elements = { { "ID", this.m.getSerial_number() }, { "Model Type", this.m.getModel_type() },
				{ "Begin Date", this.m.getBegin().toString() }, { "End Date", this.m.getEnd().toString() },
				{ "Optimization?", optimization }, { "Portfolio Size?", "" + this.m.getPo().size() } };
		gi += TableCreator.createFeatureTable(elements);
		this.sections.add(gi);
	}

	/**
	 * Creates Included Competitions section.
	 */
	private void makeIncludedCompetitions() {
		String ic = new String();
		ic += StandardCreator.makeSection("Included Competitions");
		MCapsule[] mcs = this.m.getSimulations();
		String[][] elements = new String[mcs.length][2];
		for (int i = 0; i < mcs.length; i++) {
			elements[i][0] = mcs[i].competition;
			elements[i][1] = mcs[i].season;
		}
		ic += TableCreator.createCompetitionTable(elements);
		this.sections.add(ic);
	}

	/**
	 * Merger getter.
	 * 
	 * @return report Merger-Object.
	 */
	public Merger getM() {
		return m;
	}

}