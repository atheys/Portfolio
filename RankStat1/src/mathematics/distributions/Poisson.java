package mathematics.distributions;

/**
 * Poisson Distribution Class.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class Poisson {

	/**
	 * Class attributes.
	 */
	private double lambda;

	/**
	 * Copy constructor.
	 * 
	 * @param p
	 *            Poisson distribution to copy.
	 */
	public Poisson(Poisson p) {
		this.lambda = p.lambda;
	}

	/**
	 * General constructor.
	 * 
	 * @param lambda
	 *            distribution-specific factor.
	 */
	public Poisson(double lambda) {
		this.lambda = lambda;
	}

	/**
	 * Lambda-getter.
	 * 
	 * @return lambda-factor of the distribution.
	 */
	public double getLambda() {
		return lambda;
	}

	/**
	 * Lambda-setter.
	 * 
	 * @param lambda
	 *            new lambda-factor of the distribution.
	 */
	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	/**
	 * Computes factorial for a given integer.
	 * 
	 * @param k
	 *            input integer.
	 * @return factorial number.
	 */
	private int factorial(int k) {
		if (k < 0) {
			return -1;
		}
		if (k == 0) {
			return 1;
		}
		return k * this.factorial(k - 1);
	}

	/**
	 * Capsule method factorial function for testing purposes.
	 * 
	 * @param k
	 *            input integer.
	 * @return factorial number.
	 */
	public int factorialCapsule(int k) {
		return this.factorial(k);
	}

	/**
	 * Computes Poisson ditributed probability.
	 * 
	 * @param k
	 *            input integer.
	 * @return discrete probability number.
	 */
	public double p(int k) {
		if (k >= 0) {
			return (Math.pow(lambda, k) * Math.pow(Math.E, -1 * this.lambda)) / factorial(k);
		}
		return 0;
	}

	/**
	 * Computes Poisson ditributed cumulative probability (<=k).
	 * 
	 * @param k
	 *            input integer.
	 * @return discrete cumulative probability number.
	 */
	public double P(int k) {
		double P = 0;
		for (int i = 0; i <= k; i++) {
			P += p(i);
		}
		return P;
	}

	/**
	 * Computes Poisson ditributed cumulative probability (k<).
	 * 
	 * @param k
	 *            input integer.
	 * @return discrete cumulative probability number.
	 */
	public double P_inv(int k) {
		return 1 - P(k);
	}

	/**
	 * Determines whether two Poisson distributions are equal.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Poisson) {
			Poisson that = (Poisson) obj;
			return this.lambda == that.lambda;
		}
		return false;
	}

}