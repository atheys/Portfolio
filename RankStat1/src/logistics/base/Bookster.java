package logistics.base;

import data.core.books.Bookie;

/**
 * Bookster Capsule Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Bookster {

	/**
	 * Class attributes.
	 */
	private Bookie b;
	private boolean online;
	private boolean physical;
	private boolean live;

	/**
	 * Copy constructor.
	 * 
	 * @param bs
	 *            Bookster-Object to copy.
	 */
	public Bookster(Bookster bs) {
		this.b = bs.b;
		this.online = bs.online;
		this.physical = bs.physical;
		this.live = bs.live;
	}

	/**
	 * General constructor.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param online
	 *            online use indicator.
	 * @param physical
	 *            physical use indicator.
	 */
	public Bookster(Bookie b, boolean online, boolean physical, boolean live) {
		this.b = b;
		this.online = online;
		this.physical = physical;
		this.live = live;
	}

	/**
	 * Bookie getter.
	 * 
	 * @return corresponding Bookie-Object.
	 */
	public Bookie getB() {
		return b;
	}

	/**
	 * Online use getter.
	 * 
	 * @return online use indicator.
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * Online use setter.
	 * 
	 * @param online
	 *            new online use indicator.
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}

	/**
	 * Physical use getter.
	 * 
	 * @return physical use indicator.s
	 */
	public boolean isPhysical() {
		return physical;
	}

	/**
	 * Physical use setter.
	 * 
	 * @param physical
	 *            new physical use indicator.
	 */
	public void setPhysical(boolean physical) {
		this.physical = physical;
	}

	/**
	 * Live getter.
	 * 
	 * @return live betting indicator.
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * Live setter.
	 * 
	 * @param live
	 *            new live betting indicator.
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * Compares two Bookster-Objects.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Bookster) {
			Bookster that = (Bookster) obj;
			return this.b.equals(that.b);
		}
		return false;
	}

}