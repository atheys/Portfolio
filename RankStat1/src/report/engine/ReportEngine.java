package report.engine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import data.core.paths.Path;
import data.core.structure.Game;
import mathematics.portfolio.Asset;
import models.general.performance.Prediction;
import report.hierarchy.types.GameReport;
import report.hierarchy.types.MergerSpecReport;
import report.hierarchy.types.PortOverReport;
import report.hierarchy.types.PortReport;
import report.hierarchy.types.SimSpecReport;
import simulation.analyze.evaluation.Evaluator;
import simulation.merge.Merger;
import simulation.process.POptimizer;
import simulation.simulate.Dividend;
import simulation.simulate.Simulation;

/**
 * ReportEngine Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class ReportEngine {

	/**
	 * Class attributes.
	 */
	private Dividend d;
	private double version;
	private int generation;
	// Simulation-based attributes
	private Simulation s;
	private SimSpecReport ssp;
	private String ssp_location;
	private ArrayList<ArrayList<GameReport>> grs;
	private String grs_location;
	private ArrayList<PortOverReport> por;
	private ArrayList<ArrayList<PortReport>> prs;
	private String p_location;
	// Merger-based attributes
	private Merger[] mergers;
	private ArrayList<MergerSpecReport> msp;
	private String msp_location;

	/**
	 * Simulation constructor.
	 * 
	 * @param s
	 *            relevant Simulation-Object.
	 * @param d
	 *            relevant Dividend-Object.
	 * @param version
	 *            software version.
	 * @param generation
	 *            model generation.
	 */
	public ReportEngine(Simulation s, Dividend d, double version, int generation) {
		this.s = s;
		this.d = d;
		this.version = version;
		this.generation = generation;
		this.makeSimulationReports();
	}

	/**
	 * Merger constructor.
	 * 
	 * @param mergers
	 *            relevant array or Merger-Objects.
	 * @param d
	 *            relevant Dividend-Object.
	 * @param version
	 *            software version.
	 * @param generation
	 *            model generation.
	 */
	public ReportEngine(Merger[] mergers, Dividend d, double version, int generation) {
		this.mergers = mergers;
		this.d = d;
		this.version = version;
		this.generation = generation;
		this.makeMergerReports();
	}

	/**
	 * Creates all Simulation-based reports.
	 */
	private void makeSimulationReports() {
		this.makeSimSpecReport();
		this.makeGameReports();
		this.makeSimPortOverReports();
		this.makeSimPortReports();
	}

	/**
	 * Creates all Merger-based reports.
	 */
	private void makeMergerReports() {
		this.makeMergerSpecReport();
		this.makeMergerPortOverReports();
		this.makeMergerPortReports();
	}

	/**
	 * Makes Simulation specification report.
	 */
	private void makeSimSpecReport() {
		this.ssp = new SimSpecReport(this.s.getSerial_number(), this.s, this.version, this.generation);
		String[] comp = this.s.getCompetition().getId().split("-");
		this.ssp_location = Path.results_sims + this.s.getSerial_number() + "/" + comp[0] + "/" + comp[1] + "/";
	}

	/**
	 * Makes Merger specification report.
	 */
	private void makeMergerSpecReport() {
		this.msp = new ArrayList<MergerSpecReport>();
		for (Merger m : this.mergers) {
			this.msp.add(new MergerSpecReport(m.getSerial_number(), m, this.version, this.generation));
		}
		this.msp_location = Path.results_merg;
	}

	/**
	 * Makes Game-reports.
	 */
	private void makeGameReports() {
		this.grs = new ArrayList<ArrayList<GameReport>>();
		for (ArrayList<Evaluator> evals : this.s.getPeriods()) {
			ArrayList<GameReport> gr = new ArrayList<GameReport>();
			for (Evaluator e : evals) {
				GameReport g = new GameReport(this.s.getSerial_number(), this.s, this.version, this.generation, e);
				gr.add(g);
			}
			this.grs.add(gr);
		}
		this.grs_location = this.ssp_location + "Periods/";
	}

	/**
	 * Makes Simulation-based Portfolio Overview report.
	 */
	private void makeSimPortOverReports() {
		this.por = new ArrayList<PortOverReport>();
		for (int i = 0; i < this.s.getPortfolios().size(); i++) {
			PortOverReport p = new PortOverReport(this.s.getPortfolios().get(i).get(0).getPortfolio().getId(), this.s,
					this.version, this.generation, this.s.getPortfolios().get(i), this.d, this.d.getValues().get(i),
					this.d.getDividends().get(i));
			this.por.add(p);
		}
		this.p_location = this.ssp_location + "Portfolios/";
	}

	/**
	 * Makes Merger-based Portfolio Overview report.
	 */
	private void makeMergerPortOverReports() {
		this.por = new ArrayList<PortOverReport>();
		for (int i = 0; i < this.mergers.length; i++) {
			PortOverReport p = new PortOverReport(this.mergers[i].getPo().get(0).getPseudo_portfolio().getId(),
					new Simulation(), this.version, this.generation, this.mergers[i].getPo(), this.d,
					this.d.getValues().get(i), this.d.getDividends().get(i));
			this.por.add(p);
		}
		this.p_location = this.msp_location;
	}

	/**
	 * Makes Simulation-based Portfolio report.
	 */
	private void makeSimPortReports() {
		this.prs = new ArrayList<ArrayList<PortReport>>();
		for (ArrayList<POptimizer> pos : this.s.getPortfolios()) {
			ArrayList<PortReport> pr = new ArrayList<PortReport>();
			for (POptimizer po : pos) {
				PortReport p = new PortReport(po.getPortfolio().getId(), this.s, this.version, this.generation, po);
				pr.add(p);
			}
			this.prs.add(pr);
		}
	}

	/**
	 * Makes Merger-based Portfolio report.
	 */
	private void makeMergerPortReports() {
		this.prs = new ArrayList<ArrayList<PortReport>>();
		for (Merger m : this.mergers) {
			ArrayList<POptimizer> pos = m.getPo();
			ArrayList<PortReport> pr = new ArrayList<PortReport>();
			for (POptimizer po : pos) {
				PortReport p = new PortReport(po.getPortfolio().getId(), new Simulation(), this.version,
						this.generation, po);
				pr.add(p);
			}
			this.prs.add(pr);
		}
	}

	/**
	 * Writes Simulation-based specification report.
	 */
	private void writeSimSpecReportFile() throws IOException {
		new File(this.ssp_location).mkdirs();
		PrintWriter pw = new PrintWriter(this.ssp_location + "Simulation_Spec_Report.tex");
		pw.println(this.ssp.write());
		pw.close();
	}

	/**
	 * Makes Merger-based specification report.
	 */
	private void writeMergerSpecReportFiles() throws IOException {
		for (MergerSpecReport m : this.msp) {
			String directory = this.msp_location + m.getM().getSerial_number() + "/" + m.getM().getModel_type() + "/";
			new File(directory).mkdirs();
			String path = directory + "Merger_Spec_Report.tex";
			PrintWriter pw = new PrintWriter(path);
			pw.println(m.write());
			pw.close();
		}
	}

	/**
	 * Writers Game-report files.
	 * 
	 * @throws IOException
	 */
	private void writeGameReportFiles() throws IOException {
		for (int i = 1; i <= this.grs.size(); i++) {
			String directory = this.grs_location + this.s.getDates().get(i - 1).toStringReverse() + "/";
			new File(directory).mkdirs();
			ArrayList<GameReport> gr = this.grs.get(i - 1);
			for (int j = 0; j < gr.size(); j++) {
				String path = directory + gr.get(j).getE().getGame().getId() + ".tex";
				PrintWriter pw = new PrintWriter(path);
				pw.println(this.grs.get(i - 1).get(j).write());
				pw.close();
			}
			String file_loc = directory + "Games.txt";
			PrintWriter pw = new PrintWriter(file_loc);
			for (int j = 0; j < gr.size(); j++) {
				pw.println(gr.get(j).getE().getGame().getId());
			}
			pw.close();
		}
	}

	/**
	 * Writes Simulation-based Portfolio Overview report.
	 */
	private void writeSimPortOverReportFiles() throws IOException {
		for (PortOverReport p : this.por) {
			String path = this.p_location + p.getName() + "/";
			new File(path).mkdirs();
			PrintWriter pw = new PrintWriter(path + "Portfolio_Overview_Report.tex");
			pw.println(p.write());
			pw.close();
		}
	}

	/**
	 * Writes Merger-based Portfolio Overview report.
	 */
	private void writeMergerPortOverReportFiles() throws IOException {
		for (int i = 0; i < this.por.size(); i++) {
			String directory = this.p_location + this.mergers[i].getSerial_number() + "/"
					+ this.mergers[i].getModel_type() + "/Portfolios/";
			new File(directory).mkdirs();
			String path = directory + "Portfolio_Overview_Report.tex";
			PrintWriter pw = new PrintWriter(path);
			pw.println(this.por.get(i).write());
			pw.close();
		}
	}

	/**
	 * Writes Simulation-based Portfolio report.
	 */
	private void writeSimPortReportFiles() throws IOException {
		for (ArrayList<PortReport> pr : this.prs) {
			for (int i = 1; i <= pr.size(); i++) {
				String path = this.p_location + pr.get(i - 1).getName() + "/";
				new File(path).mkdirs();
				PrintWriter pw = new PrintWriter(path + "Portfolio_" + i + ".tex");
				pw.println(pr.get(i - 1).write());
				pw.close();
			}
		}
		for (int i = 0; i < prs.size(); i++) {
			for (int j = 1; j <= prs.get(i).size(); j++) {
				String path = this.p_location + prs.get(i).get(j - 1).getName() + "/";
				new File(path).mkdirs();
				String file_loc = path + prs.get(i).get(j - 1).getPo().getBegin().toStringReverse() + ".txt";
				PrintWriter pw = new PrintWriter(file_loc);
				POptimizer po = prs.get(i).get(j - 1).getPo();
				for (int k = 0; k < po.getPseudo_portfolio().getAssets().size(); k++) {
					Asset a = po.getPseudo_portfolio().getAssets().get(k);
					String data = "Asset," + a.getGame_id() + "," + a.getModel_id() + "," + a.getBookie_id() + ","
							+ a.getExpected() + "," + a.getRisk() + "," + a.getStake();
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
					data += "," + odds;
					pw.println(data);
				}
				pw.close();
			}
		}
	}

	/**
	 * Writes Merger-based Portfolio report.
	 */
	private void writeMergerPortReportFiles() throws IOException {
		for (int i = 0; i < this.prs.size(); i++) {
			for (int j = 1; j <= prs.get(i).size(); j++) {
				String path = this.p_location + this.mergers[i].getSerial_number() + "/"
						+ this.mergers[i].getModel_type() + "/Portfolios/";
				new File(path).mkdirs();
				PrintWriter pw = new PrintWriter(path + "Portfolio_" + j + ".tex");
				pw.println(prs.get(i).get(j - 1).write());
				pw.close();
			}
		}
		for (int i = 0; i < prs.size(); i++) {
			for (int j = 1; j <= prs.get(i).size(); j++) {
				String path = this.p_location + this.mergers[i].getSerial_number() + "/"
						+ this.mergers[i].getModel_type() + "/Portfolios/";
				new File(path).mkdirs();
				String file_loc = path + prs.get(i).get(j - 1).getPo().getBegin().toStringReverse() + ".txt";
				PrintWriter pw = new PrintWriter(file_loc);
				POptimizer po = prs.get(i).get(j - 1).getPo();
				for (int k = 0; k < po.getPseudo_portfolio().getAssets().size(); k++) {
					Asset a = po.getPseudo_portfolio().getAssets().get(k);
					String data = "Asset," + a.getGame_id() + "," + a.getModel_id() + "," + a.getBookie_id() + ","
							+ a.getExpected() + "," + a.getRisk() + "," + a.getStake();
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
					data += "," + odds;
					pw.println(data);
				}
				pw.close();
			}
		}
	}

	/**
	 * Writes all Simulation-based Portfolio type of reports.
	 * 
	 * @throws IOException
	 */
	private void writeSimPortfolioReportFiles() throws IOException {
		this.writeSimPortOverReportFiles();
		this.writeSimPortReportFiles();
	}

	/**
	 * Writes all Merger-based Portfolio type of reports.
	 * 
	 * @throws IOException
	 */
	private void writeMergerPortfolioReportFiles() throws IOException {
		this.writeMergerPortOverReportFiles();
		this.writeMergerPortReportFiles();
	}

	/**
	 * Writes all Simulation-based reports.
	 * 
	 * @throws IOException
	 */
	public void writeSimFiles() throws IOException {
		this.writeSimSpecReportFile();
		this.writeGameReportFiles();
		this.writeSimPortfolioReportFiles();
	}

	/**
	 * Writes all Merger-based reports.
	 * 
	 * @throws IOException
	 */
	public void writeMergerFiles() throws IOException {
		this.writeMergerSpecReportFiles();
		this.writeMergerPortfolioReportFiles();
	}

}