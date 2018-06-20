package mathematics.distributions.confidence;

/**
 * Imported packages.
 */
import java.util.List;
import mathematics.distributions.Normal;

/**
 * Confidence interval class. Note: confidence intervals are only determined for
 * an known standard deviation.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class cInterval {

	/**
	 * Class attributes.
	 */
	private String id;
	private double p;
	private Normal n;
	private int population;

	/**
	 * Copy constructor.
	 * 
	 * @param ci
	 *            cInterval-object to copy.
	 */
	public cInterval(cInterval ci) {
		this.id = ci.id;
		this.p = ci.p;
		this.n = ci.n;
		this.population = ci.population;
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID brand of the interval.
	 * @param p
	 *            coverage percentile.
	 * @param n
	 *            normal distribution.
	 */
	public cInterval(String id, double p, Normal n, int population) {
		this.id = id;
		this.p = p;
		this.n = n;
		this.population = population;
	}

	/**
	 * Data array constructor.
	 * 
	 * @param id
	 *            ID brand of the interval.
	 * @param p
	 *            coverage percentile.
	 * @param values
	 *            data array.
	 */
	public cInterval(String id, double p, double[] values) {
		this.id = id;
		this.p = p;
		this.n = new Normal(values);
		this.population = values.length;
	}

	/**
	 * Data list constructor.
	 * 
	 * @param id
	 *            ID brand of the interval.
	 * @param p
	 *            coverage percentile.
	 * @param values
	 *            data list.
	 */
	public cInterval(String id, double p, List<Double> values) {
		this.id = id;
		this.p = p;
		this.n = new Normal(values);
		this.population = values.size();
	}

	/**
	 * Computes lower boundary value of the confidence interval.
	 * 
	 * @return lower boundary value.
	 */
	public double min() {
		double z = Normal.zFactor(this.p, 0.0001);
		return this.n.getMu() - z * (this.n.getSigma() / Math.sqrt(this.population));
	}

	/**
	 * Computes upper boundary value of the confidence interval.
	 * 
	 * @return upper boundary value.
	 */
	public double max() {
		double z = Normal.zFactor(this.p, 0.0001);
		return this.n.getMu() + z * (this.n.getSigma() / Math.sqrt(this.population));
	}

	/**
	 * Compares cInterval-object to a given object.
	 * 
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof cInterval) {
			cInterval that = (cInterval) obj;
			return this.id.equals(that.id) && this.p == that.p && this.n.equals(that.n)
					&& this.population == that.population;
		}
		return false;
	}

}