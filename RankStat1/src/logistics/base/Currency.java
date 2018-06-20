package logistics.base;

/**
 * Currency Unit Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Currency {

	/**
	 * Class attributes.
	 */
	private String label;
	private String name;

	/**
	 * Copy constructor.
	 * 
	 * @param c
	 *            Currency-Object to copy.
	 */
	public Currency(Currency c) {
		this.label = c.label;
		this.name = c.name;
	}

	/**
	 * General constructor.
	 * 
	 * @param label
	 *            currency label.
	 * @param name
	 *            currency name.
	 */
	public Currency(String label, String name) {
		this.label = label;
		this.name = name;
	}

	/**
	 * Label getter.
	 * 
	 * @return currency label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Label setter.
	 * 
	 * @param label
	 *            new currency label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Name getter.
	 * 
	 * @return currency name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter
	 * 
	 * @param name
	 *            new currency name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Compares two Currency-Objects to one another.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Currency) {
			Currency that = (Currency) obj;
			return this.label.equals(that.label);
		}
		return false;
	}

}