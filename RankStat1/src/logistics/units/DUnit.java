package logistics.units;

import java.util.ArrayList;
import data.core.structure.GameDay;
import logistics.base.FrontMan;

/**
 * Distribution Unit Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class DUnit {

	/**
	 * Class attributes;
	 */
	private GameDay gd;
	private FrontMan fm;
	private ArrayList<AUnit> assets;

	/**
	 * General constructor.
	 * 
	 * @param gd
	 *            relevant date.
	 * @param fm
	 *            responsible FrontMan-Object.
	 */
	public DUnit(GameDay gd, FrontMan fm) {
		this.gd = gd;
		this.fm = fm;
		this.assets = new ArrayList<AUnit>();
	}

	/**
	 * General constructor.
	 * 
	 * @param gd
	 *            relevant date.
	 * @param fm
	 *            responsible FrontMan-Object.
	 * @param assets
	 *            responsibility assets.
	 */
	public DUnit(GameDay gd, FrontMan fm, ArrayList<AUnit> assets) {
		this.gd = gd;
		this.fm = fm;
		this.assets = assets;
	}

	/**
	 * GameDay getter.
	 * 
	 * @return relevant GameDay-Object.
	 */
	public GameDay getGd() {
		return gd;
	}

	/**
	 * FrontMan getter.
	 * 
	 * @return relevant FrontMan-Object.
	 */
	public FrontMan getFm() {
		return fm;
	}

	/**
	 * Assets Getter.
	 * 
	 * @return list of relevant AUnit-Objects.
	 */
	public ArrayList<AUnit> getAssets() {
		return assets;
	}

	/**
	 * Assets Setter.
	 * 
	 * @param assets
	 *            new list of relevant AUnit-Objects.
	 */
	public void setAssets(ArrayList<AUnit> assets) {
		this.assets = assets;
	}

	/**
	 * Adds an AUnit asset to the list of assets.
	 * 
	 * @param a
	 *            AUnit-Object to add.
	 */
	public void addAsset(AUnit a) {
		for (AUnit ass : this.assets) {
			if (ass.equals(a)) {
				this.assets.remove(ass);
				this.assets.add(a);
				return;
			}
		}
		this.assets.add(a);
	}
}