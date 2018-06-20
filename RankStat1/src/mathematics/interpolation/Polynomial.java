package mathematics.interpolation;

/**
 * Polynomial relation class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Polynomial extends Relation {

	/**
	 * Additional class attributes.
	 */
	private int degree;
	private double[] coefficients;

	/**
	 * Default-ish constructor.
	 * 
	 * @param id
	 *            ID of relationship.
	 * @param degree
	 *            degree of polynomial.
	 */
	public Polynomial(String id, int degree) {
		super(id);
		this.degree = degree;
		this.coefficients = new double[degree + 1];
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[i] = 0;
		}
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of relationship.
	 * @param coefficients
	 *            coefficient list (starting with highest degree).
	 */
	public Polynomial(String id, double[] coefficients) {
		super(id);
		this.degree = coefficients.length - 1;
		this.coefficients = coefficients;
	}

	/**
	 * Degree getter.
	 * 
	 * @return degree of the polynomial.
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Coefficient array getter.
	 * 
	 * @return coefficients getter.
	 */
	public double[] getCoefficients() {
		return coefficients;
	}

	/**
	 * Coefficient array setter.
	 * 
	 * @param coefficients
	 *            coefficients array.
	 * 
	 */
	public void setCoefficients(double[] coefficients) {
		this.coefficients = coefficients;
		this.degree = this.coefficients.length - 1;
	}

	/**
	 * Computes the value of a polynomial in a specific value.
	 * 
	 * @param x
	 *            value to compute for.
	 * @return value of polynomial in x.
	 */
	public double eval(double x) {
		double result = 0;
		if (x == 0) {
			return this.coefficients[this.coefficients.length - 1];
		}
		for (int i = 0; i <= degree; i++) {
			result += this.coefficients[i] * Math.pow(x, degree - i);
		}
		return result;
	}

	/**
	 * Derives the current polynomial.
	 * 
	 * @return derivative of current polynomial.
	 */
	public Polynomial derive() {
		String newID = this.getId() + "'";
		double[] coeff;
		if (this.degree == 0) {
			coeff = new double[1];
			coeff[0] = 0;
			return new Polynomial(newID, coeff);
		} else {
			coeff = new double[this.degree];
			for (int i = 0; i < coeff.length; i++) {
				coeff[i] = (degree - i) * this.coefficients[i];
			}
		}
		return new Polynomial(newID, coeff);
	}

	/**
	 * Determines whether two polynomials are equal.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Polynomial) {
			Polynomial that = (Polynomial) obj;
			boolean eval = this.getId().equals(that.getId());
			for (int i = 0; i < this.coefficients.length; i++) {
				eval = eval && this.coefficients[i] == that.coefficients[i];
			}
			return eval;
		}
		return false;
	}

}