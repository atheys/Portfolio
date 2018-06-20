package mathematics.distributions;

import mathematics.distributions.PieceWise;

/**
 * Standard mathematical class for bipoisson distributed processes.
 * 
 * @author Andreas Theys.
 * @version 3.0
 */
public class BiPoisson {

	/**
	 * Class attributes.
	 */
	private Poisson p1;
	private Poisson p2;
	private PieceWise correction;

	/**
	 * Copy constructor.
	 * 
	 * @param bp
	 *            bipoisson distribution to copy.
	 */
	public BiPoisson(BiPoisson bp) {
		this.p1 = bp.p1;
		this.p2 = bp.p2;
		this.correction = bp.correction;
	}

	/**
	 * General constructor.
	 * 
	 * @param p1
	 *            first Poisson distribution.
	 * @param p2
	 *            second Poisson distribution.
	 * @param correction
	 *            corrector function.
	 */
	public BiPoisson(String id, Poisson p1, Poisson p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.correction = new PieceWise(id);
	}

	/**
	 * First Poisson getter.
	 * 
	 * @return first Poisson distribution.
	 */
	public Poisson getP1() {
		return p1;
	}

	/**
	 * Second Poisson getter.
	 * 
	 * @return second Poisson distribution.
	 */
	public Poisson getP2() {
		return p2;
	}

	/**
	 * Correction function getter.
	 * 
	 * @return correction function.
	 */
	public PieceWise getCorrection() {
		return correction;
	}

	/**
	 * Returns expected value (p1 and p2 independent).
	 * 
	 * @return expected value of the bipoisson distribution.
	 */
	public double E() {
		return this.p1.getLambda() * this.p2.getLambda();
	}

	/**
	 * Returns variance number (p1 and p2 independent).
	 * 
	 * @return variance of the biPoisson distribution.
	 */
	public double Var() {
		double lambda1 = this.p1.getLambda();
		double lambda2 = this.p2.getLambda();
		return lambda1 * lambda2 + lambda1 * Math.pow(lambda2, 2) + lambda2 * Math.pow(lambda1, 2);
	}

	/**
	 * Returns standard deviation number (p1 and p2 independent).
	 * 
	 * @return standard deviation of the biPoisson distribution.
	 */
	public double Sigma() {
		return Math.sqrt(this.Var());
	}

	/**
	 * Computes probability X(p1) > Y(p2).
	 * 
	 * @return probablity that stochast X with distribution p1 is higher than
	 *         stochast Y with distribution p2.
	 */
	public double P1() {
		double prob = 0;
		for (int i = 0; i <= 12; i++) {
			for (int j = 0; j < i; j++) {
				prob +=  this.p1.p(i) * this.p2.p(j);
			}
		}
		return prob;
	}

	/**
	 * Computes probability X(p1) < Y(p2).
	 * 
	 * @return probablity that stochast X with distribution p1 is lower than
	 *         stochast Y with distribution p2.
	 */
	public double P2() {
		double prob = 0;
		for (int i = 0; i <= 12; i++) {
			for (int j = 0; j < i; j++) {
				prob += this.p2.p(i) * this.p1.p(j);
			
			}
		}
		return prob;
	}

	/**
	 * Computes probability X(p1) = Y(p2).
	 * 
	 * @return probablity that stochast X with distribution p1 is equal to
	 *         stochast Y with distribution p2.
	 */
	public double P1P2() {
		double prob = 0;
		for (int i = 0; i <= 12; i++) {
			prob += p1.p(i) * p2.p(i);
		}
		return prob;
	}

	/**
	 * Determines whether two BiPoisson-objects are equals.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof BiPoisson) {
			BiPoisson that = (BiPoisson) obj;
			return this.p1.equals(that.p1) && this.p2.equals(that.p2); //&& this.correction.equals(that.correction);
		}
		return false;
	}

}