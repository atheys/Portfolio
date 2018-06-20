package mathematics.interpolation;

/**
 * Class for the abstract relationship entity.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Relation {

	/**
	 * Class attributes.
	 */
	private String id;

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of relationship.
	 */
	public Relation(String id) {
		this.id = id;
	}

	/**
	 * ID-getter.
	 * 
	 * @return ID of the relationship.
	 */
	public String getId() {
		return id;
	}

	/**
	 * ID-setter.
	 * 
	 * @param id
	 *            ID of relationship.
	 */
	public void setId(String id) {
		this.id = id;
	}

}