package data.core.books;

import java.util.ArrayList;

/**
 * Class Bookie.
 * 
 * @author Andreas Theys.
 * @version 3.0
 */
public class Bookie {

	/**
	 * Class attributes.
	 */
	private String id;
	private String name;
	private ArrayList<Odds> odds;

	/**
	 * Default constructor.
	 */
	public Bookie() {
		this.id = "ID";
		this.name = "NAME";
		this.odds = new ArrayList<Odds>();
	}

	/**
	 * Copy constructor.
	 * 
	 * @param b
	 *            Bookie-object to copy.
	 */
	public Bookie(Bookie b) {
		this.id = b.id;
		this.odds = b.odds;
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the bookie.
	 * @param odds
	 *            list of bookie Odds-Objects.
	 */
	public Bookie(String id, String name, ArrayList<Odds> odds) {
		this.id = id;
		this.name = name;
		this.odds = odds;
	}

	/**
	 * Getter ID.
	 * 
	 * @return ID of the bookie.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter ID.
	 * 
	 * @param id
	 *            new ID of the bookie.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Name getter.
	 * 
	 * @return name of the bookie.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter.
	 * 
	 * @param name
	 *            new name of the bookie.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter odds.
	 * 
	 * @return odds of the bookie.
	 */
	public ArrayList<Odds> getOdds() {
		return odds;
	}

	/**
	 * Setter odds.
	 * 
	 * @param odds
	 *            new odds of the bookie.
	 */
	public void setOdds(ArrayList<Odds> odds) {
		this.odds = odds;
	}

	/**
	 * Adds odds to list.
	 * 
	 * @param o
	 *            Odds-object to add.
	 */
	public void addOdds(Odds o) {
		for (int i = 0; i < odds.size(); i++) {
			// Updating in case Odds-Objects already exist
			if (odds.get(i).equals(o)) {
				odds.add(i, o);
				odds.remove(i + 1);
				return;
			}
		}
		// Add new Odds-Object
		odds.add(o);
	}

	/**
	 * Compares two Bookie-object.
	 * 
	 * @param obj
	 *            Object to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Bookie) {
			Bookie that = (Bookie) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}