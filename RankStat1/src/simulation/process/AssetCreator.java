package simulation.process;

import java.util.ArrayList;
import data.core.books.Odds;
import mathematics.portfolio.Asset;
import simulation.analyze.body.ModelCap;

/**
 * Asset Creator class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class AssetCreator {

	/**
	 * Asset-creating function.
	 * 
	 * @param mc
	 *            ModelCap-Object in question.
	 * @param odds
	 *            corresponding list of game odds.
	 * @param importance
	 *            figure for variance/efficiency computations
	 * @param type
	 *            type of prediction indicator.
	 * @return corresponding list of Asset-Objects.
	 */
	public static ArrayList<Asset> CREATE(ModelCap mc, ArrayList<Odds> odds, double importance, String e_type, double max) {
		String e_copy = new String(e_type);
		ArrayList<Asset> assets = new ArrayList<Asset>();
		for (Odds o : odds) {
			Asset[] a = mc.createAsset(o.getBookie().getId(), o.getWinHome(), o.getDraw(), o.getWinAway(), importance,
					e_type);
			double ratio11 = o.getWinHome() / o.getDraw();
			double ratio12 = o.getWinHome() / o.getWinAway();
			double ratio21 = o.getDraw() / o.getWinHome();
			double ratio22 = o.getDraw() / o.getWinAway();
			double ratio31 = o.getWinAway() / o.getDraw();
			double ratio32 = o.getWinAway() / o.getWinHome();
			double max1 = Math.max(ratio11, ratio12);
			double max2 = Math.max(ratio21, ratio22);
			double max3 = Math.max(ratio31, ratio32);
			double m = Math.max(Math.max(max1, max2), max3);
			if (m > max)
				e_type = "O";
			switch (e_type) {
			case "M":
				if (a[0].getRisk() > 0.)
					assets.add(a[0]);
				break;
			case "O":
				if (a[1].getRisk() > 0.)
					assets.add(a[1]);
			case "F":
				if (a[2].getRisk() > 0.)
					assets.add(a[2]);
			case "E":
				if (a[3].getRisk() > 0.)
					assets.add(a[3]);
			default:
				break;
			}
			e_type = e_copy;
		}
		return assets;
	}

}