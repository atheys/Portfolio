package mathematics.distributions;

import mathematics.interpolation.Relation;

/**
 * Piecewise correction function class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class PieceWise extends Relation {

	/**
	 * Class attributes.
	 */
	private String id;

	/**
	 * Default constructor.
	 * 
	 * @param id
	 *            ID feature.
	 */
	public PieceWise(String id) {
		super(id);
	}

	/**
	 * Compares two PieceWise-objects.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof PieceWise) {
			PieceWise that = (PieceWise) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}