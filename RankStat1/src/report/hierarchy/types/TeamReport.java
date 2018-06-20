package report.hierarchy.types;

import data.core.structure.Team;
import report.hierarchy.Report;
import report.hierarchy.ReportFunctions;
import simulation.simulate.Simulation;

/**
 * Team Analysis Report Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class TeamReport extends Report implements ReportFunctions {

	/**
	 * Class attributes.
	 */
	private Team t;

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
	 *            model generations.
	 * @param t
	 *            relevant Team-Object.
	 */
	public TeamReport(String name, Simulation s, double version, int generation, Team t) {
		super(name, s, version, generation);
		this.t = t;
	}
	
	/**
	 * Title complementation.
	 */
	public void complementTitle() {
		this.title += "\\begin{center} \n" + "\\textsc{\\LARGE \\textbf{Team Report} \\\\[0.2cm]} \n"
				+ "\\textsc{\\large {Performance $\\&$ Financial Details}} \n" + "\\end{center} \n";
	}
	
	public void makeGeneralInformation(){
		
	}
	
	public void makeGamesOverview(){
		
	}
	
	public void makeFinancialOverview(){
		
	}

}
