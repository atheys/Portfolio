package simulation.merge;

import data.core.structure.GameDay;
import simulation.analyze.selection.Capsule;
import simulation.except.Except;
import simulation.simulate.SimulationSettings;

/**
 * Merger Settings Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class MergeSettings {

	public static String serialNumber(Capsule data_capsule, Except except, GameDay begin_date, String evaluation_type,
			boolean match_filtering, boolean port_optimization, int perc, double[] min_perc, String[] filter_scheme,
			int n_max, int backtrack, int min_evals, int factorT, int factorN, int var_importanceT, int var_importanceN,
			int min_returnT, int min_returnN) {
		String sn = new String();
		sn += evaluation_type;
		if (match_filtering) {
			sn += "T";
		} else {
			sn += "F";
		}
		if (port_optimization) {
			sn += "T-";
		} else {
			sn += "F-";
		}
		sn += perc + "-";
		for (String s : filter_scheme) {
			sn += s;
		}
		sn += "-" + n_max + "-" + backtrack + "-" + min_evals + "-";
		sn += factorT + "|" + factorN + "-";
		sn += var_importanceT + "|" + var_importanceN + "-";
		sn += min_returnT + "|" + min_returnN;
		return sn;
	}

	public static String[] CompetitionLabels(Capsule c) {
		String[] labels = { c.competition, c.season };
		return labels;
	}

	public static GameDay BeginDate(Capsule c) {
		GameDay gd = new GameDay(1,1,0);
		switch (c.season) {
		case "0910":
			gd =  SimulationSettings.begin_date_2009;
			break;
		case "1011":
			gd =  SimulationSettings.begin_date_2010;
			break;
		case "1112":
			gd = SimulationSettings.begin_date_2011;
			break;
		case "1213":
			gd = SimulationSettings.begin_date_2012;
			break;
		case "1314":
			gd = SimulationSettings.begin_date_2013;
			break;
		case "1415":
			gd = SimulationSettings.begin_date_2014;
			break;
		case "1516":
			gd = SimulationSettings.begin_date_2015;
			break;
		case "1617":
			gd = SimulationSettings.begin_date_2016;
			break;
		default:
			break;
		}
		return gd;
	}
	
	public static GameDay EndDate(Capsule c) {
		GameDay gd = new GameDay(1,1,0);
		switch (c.season) {
		case "0910":
			gd =  SimulationSettings.begin_date_2010;
			break;
		case "1011":
			gd =  SimulationSettings.begin_date_2011;
			break;
		case "1112":
			gd = SimulationSettings.begin_date_2012;
			break;
		case "1213":
			gd = SimulationSettings.begin_date_2013;
			break;
		case "1314":
			gd = SimulationSettings.begin_date_2014;
			break;
		case "1415":
			gd = SimulationSettings.begin_date_2015;
			break;
		case "1516":
			gd = SimulationSettings.begin_date_2016;
			break;
		case "1617":
			break;
		default:
			break;
		}
		return gd;
	}

}
