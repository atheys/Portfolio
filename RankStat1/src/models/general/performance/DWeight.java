package models.general.performance;

/**
 * Dynamic weight data container class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class DWeight {

	/**
	 * Class attributes.
	 */
	private String model_id;
	private double weight;

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID feature.
	 * @param weight
	 *            weight of decision.
	 */
	public DWeight(String model_id, double weight) {
		this.model_id = model_id;
		this.weight = weight;
	}

	/**
	 * ID getter.
	 * 
	 * @return ID feature of the DWeight-object.
	 */
	public String getModel_id() {
		return model_id;
	}

	/**
	 * Weight getter.
	 * 
	 * @return weight feature of the DWeight-object.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Weight setter.
	 * 
	 * @param weight
	 *            new dynamic weight value.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

}