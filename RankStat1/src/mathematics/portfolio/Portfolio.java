package mathematics.portfolio;

import java.util.ArrayList;

/**
 * Portfolio class for basic investment strategies.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Portfolio {

	/**
	 * Class attributes.
	 */
	private String id;
	private ArrayList<Asset> assets;

	/**
	 * Copy constructor.
	 * 
	 * @param p
	 *            Portfolio-object to copy.
	 */
	public Portfolio(Portfolio p) {
		this.id = p.id;
		this.assets = p.assets;
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the portfolio.
	 * @param assets
	 *            list of portfolio assets.
	 */
	public Portfolio(String id, ArrayList<Asset> assets) {
		this.id = id;
		this.assets = assets;
	}
	
	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the portfolio.
	 * @param assets
	 *            list of portfolio assets.
	 */
	public Portfolio(String id, ArrayList<Asset> assets, double worth) {
		this.id = id;
		this.assets = assets;
	}

	/**
	 * ID getter.
	 * 
	 * @return ID of the portfolio.
	 */
	public String getId() {
		return id;
	}

	/**
	 * ID setter.
	 * 
	 * @param id
	 *            new ID of the portfolio.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Assets getter.
	 * 
	 * @return list of portfolio assets.
	 */
	public ArrayList<Asset> getAssets() {
		return assets;
	}

	/**
	 * Assets setter.
	 * 
	 * @param assets
	 *            new list of portfolio assets.
	 */
	public void setAssets(ArrayList<Asset> assets) {
		this.assets = assets;
	}

	/**
	 * Compares Portfolio-objects.
	 */
	public boolean equals(Object obj){
		if(obj instanceof Portfolio){
			Portfolio that = (Portfolio) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}