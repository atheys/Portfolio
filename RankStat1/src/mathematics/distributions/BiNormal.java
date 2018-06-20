package mathematics.distributions;

import exceptions.NormalException;
/**
 * Imported packages.
 */
import mathematics.interpolation.Relation;

/**
 * Class for binormally distributed random variables.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class BiNormal {

	/**
	 * Class attributes.
	 */
	private Normal n1;
	private Normal n2;
	private Relation correction;
	
	/**
	 * Copy constructor.
	 * 
	 * @param bn
	 *            binormal distribution to copy.
	 */
	public BiNormal(BiNormal bn) {
		this.n1 = bn.n1;
		this.n2 = bn.n2;
		this.correction = bn.correction;
	}
	
	/**
	 * General constructor.
	 * 
	 * @param n1
	 *            first Normal distribution.
	 * @param n2
	 *            second Normal distribution.
	 * @param correction
	 *            corrector function.
	 */
	public BiNormal(Normal n1, Normal n2, Relation correction) {
		this.n1 = n1;
		this.n2 = n2;
		this.correction = correction;
	}
	
	/**
	 * Returns expected value (n1 and n2 independent).
	 * 
	 * @return expected value of the binormal distribution.
	 */
	public double E() {
		return this.n1.getMu() * this.n2.getMu();
	}
	
	/**
	 * Returns variance number (n1 and n2 independent).
	 * 
	 * @return variance of the binormal distribution.
	 */
	public double Var() {
		double sigma1 = this.n1.getSigma();
		double sigma2 = this.n2.getSigma();
		return sigma1 * sigma2 + sigma1 * Math.pow(sigma2, 2) + sigma2 * Math.pow(sigma1, 2);
	}
	
	/**
	 * Returns standard deviation number (n1 and n2 independent).
	 * 
	 * @return standard deviation of the binormal distribution.
	 */
	public double Sigma() {
		return Math.sqrt(this.Var());
	}
	
	/**
	 * Computes probability X(n1) > Y(n2).
	 * 
	 * @return probablity that stochast X with distribution n1 is higher than
	 *         stochast Y with distribution n2.
	 * @throws NormalException 
	 */
	public double P1() throws NormalException {
		double prob = 0;
		for (int i = 0; i <= 10; i++) {
			prob += this.n2.P_discrete(i,0.001)*n1.P_inv(i+0.5,0.001);
		}
		return prob;
	}
	
	/**
	 * Computes probability X(n1) < Y(n2).
	 * 
	 * @return probablity that stochast X with distribution n1 is lower than
	 *         stochast Y with distribution n2.
	 * @throws NormalException 
	 */
	public double P2() throws NormalException {
		double prob = 0;
		for (int i = 0; i <= 10; i++) {
			prob += this.n1.P_discrete(i,0.001)*n2.P_inv(i+0.5,0.001);
		}
		return prob;
	}
	
	/**
	 * Computes probability X(n1) = Y(n2).
	 * 
	 * @return probablity that stochast X with distribution p1 is equal to
	 *         stochast Y with distribution p2.
	 * @throws NormalException 
	 */
	public double P1P2() throws NormalException {
		double prob = 0;
		for (int i = 0; i <= 10; i++) {
			prob += this.n1.P_discrete(i,0.001)*this.n2.P_discrete(i,0.001);
		}
		return prob;
	}
	
	/**
	 * Determines whether two BiPoisson-objects are equals.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof BiNormal) {
			BiNormal that = (BiNormal) obj;
			return this.n1.equals(that.n1) && this.n2.equals(that.n2) && this.correction.equals(that.correction);
		}
		return false;
	}
	
}