package simulation.merge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.core.paths.Path;
import data.core.structure.GameDay;
import mathematics.portfolio.Asset;
import mathematics.portfolio.Portfolio;
import simulation.analyze.selection.Capsule;
import simulation.process.POptimizer;

public class Merger {

	private String serial_number;
	private MCapsule[] simulations;
	private String model_type;
	private GameDay begin;
	private GameDay end;
	private boolean optimize;
	private ArrayList<POptimizer> po;

	public Merger(MCapsule[] simulations, String model_type, String serial_number, boolean optimize) {
		this.simulations = simulations;
		this.model_type = model_type;
		this.optimize = optimize;
		if (optimize) {
			this.serial_number = "T-" + serial_number;
		} else {
			this.serial_number = "F-" + serial_number;
		}
		this.begin();
		this.end();
		this.merge();
	}

	private void begin() {
		GameDay begin = new GameDay(1, 1, 2100);
		for (MCapsule m : simulations) {
			GameDay gd = MergeSettings.BeginDate((Capsule) m);
			if (gd.before(begin)) {
				begin = gd;
			}
		}
		this.begin = begin;
	}

	private void end() {
		GameDay end = new GameDay(1, 1, 0);
		for (MCapsule m : simulations) {
			GameDay gd = MergeSettings.EndDate((Capsule) m);
			if (gd.after(end)) {
				end = gd;
			}
		}
		this.end = end;
	}

	private ArrayList<Asset> read(String path) throws IOException {
		ArrayList<Asset> assets = new ArrayList<Asset>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			if (data[0].equals("Asset")) {
				String game_id = data[1];
				String model_id = data[2];
				String bookie_id = data[3];
				double expected = Double.parseDouble(data[4]);
				double risk = Double.parseDouble(data[5]);
				double won  = Double.parseDouble(data[7]);
				assets.add(new Asset(game_id, model_id, bookie_id, expected, risk, won));
			}
		}
		br.close();
		return assets;
	}

	private void merge() {
		this.po = new ArrayList<POptimizer>();
		for (GameDay gd = new GameDay(this.begin); gd.before(this.end); gd = gd.plus(7)) {
			ArrayList<Asset> assets = new ArrayList<Asset>();
			for (MCapsule m : this.simulations) {
				String[] data = MergeSettings.CompetitionLabels((Capsule) m);
				String path = Path.results_sims + m.sn + "/" + data[0] + "/" + data[1] + "/Portfolios/"
						+ this.model_type + "/" + gd.toStringReverse() + ".txt";
				try {
					assets.addAll(read(path));
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			if (assets.size() > 0) {
				Portfolio p = new Portfolio(this.model_type, assets);
				POptimizer po = new POptimizer(p, gd, this.optimize);
				this.po.add(po);
			}
		}
	}

	public String getSerial_number() {
		return serial_number;
	}

	public String getModel_type() {
		return model_type;
	}

	public boolean isOptimize() {
		return optimize;
	}

	public ArrayList<POptimizer> getPo() {
		return po;
	}

	public GameDay getBegin() {
		return begin;
	}

	public GameDay getEnd() {
		return end;
	}

	public MCapsule[] getSimulations() {
		return simulations;
	}

}