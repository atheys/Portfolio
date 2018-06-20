package mathematics.interpolation;

/**
 * Regression class. Main body for regression analyses.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class Regression {

	/**
	 * Class instances.
	 */
	protected String id;
	// Steady increments
	protected Matrix x;
	// Observation
	protected Matrix y;

	/**
	 * Default constructor.
	 */
	public Regression() {
	}

	/**
	 * Copy constructor.
	 * 
	 * @param r
	 *            Regression-object to copy.
	 */
	public Regression(Regression r) {
		this.id = r.id;
		this.x = r.x;
		this.y = r.y;
	}

	/**
	 * General constructor.
	 * 
	 * @param x
	 *            increment matrix.
	 * @param y
	 *            observation matrix.
	 */
	public Regression(String id, Matrix x, Matrix y) {
		this.id = id;
		if (x.getColumns() == 1 && y.getColumns() == 1 && x.getRows() == y.getRows()) {
			this.x = x;
			this.y = y;
		} else {
			this.x = null;
			this.y = null;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Compares two Regression-objects.
	 * 
	 * @param obj
	 *            Object to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Regression) {
			Regression that = (Regression) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}