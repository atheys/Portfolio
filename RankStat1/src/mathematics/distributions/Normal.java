package mathematics.distributions;

/**
 * Imported packages.
 */
import java.util.List;

import exceptions.NormalException;

/**
 * Normal distribution class.
 * 
 * @author Andreas Theys
 * @version 3.0
 */
public class Normal {

	/**
	 * Class-attributes.
	 */
	private double mu;
	private double sigma;

	/**
	 * Copy constructor.
	 * 
	 * @param n
	 *            Normal-object to copy.s
	 */
	public Normal(Normal n) {
		this.mu = n.mu;
		this.sigma = n.sigma;
	}

	/**
	 * General constructor.
	 * 
	 * @param mean
	 *            mean value of the distribution.
	 * @param sigma
	 *            standard deviation of the distribution.
	 */
	public Normal(double mu, double sigma) {
		this.mu = mu;
		this.sigma = sigma;
	}

	/**
	 * Data array constructor.
	 * 
	 * @param values
	 *            list of value to distribute over.
	 */
	public Normal(double[] values) {
		this.mu = mean(values);
		this.sigma = sDev(values);
	}

	/**
	 * Data list constructor.
	 * 
	 * @param values
	 *            list of value to distribute over.
	 */
	public Normal(List<Double> values) {
		this.mu = mean(values);
		this.sigma = sDev(values);
	}

	/**
	 * Getter mean value.
	 * 
	 * @return mean value of the distribution.
	 */
	public double getMu() {
		return mu;
	}

	/**
	 * Getter standard deviation.
	 * 
	 * @return standard deviation value of the distribution.
	 */
	public double getSigma() {
		return sigma;
	}

	/**
	 * Computes mean value.
	 * 
	 * @param numbers
	 *            double array to compute mean for.
	 * @return mean value.
	 */
	private double mean(double[] numbers) {
		double sum = 0;
		for (int i = 0; i < numbers.length; i++) {
			sum += numbers[i];
		}
		return sum / numbers.length;
	}

	/**
	 * Computes mean value.
	 * 
	 * @param numbers
	 *            integer list to compute mean for.
	 * @return mean value.
	 */
	private double mean(List<Double> numbers) {
		double sum = 0;
		for (Double i : numbers) {
			sum += i;
		}
		return (double) sum / numbers.size();
	}

	/**
	 * Computes mean value.
	 * 
	 * @param numbers
	 *            double array to compute mean for.
	 * @return mean value.
	 */
	private double sDev(double[] numbers) {
		double sum = 0;
		double mean = mean(numbers);
		for (int i = 0; i < numbers.length; i++) {
			sum += Math.pow(numbers[i] - mean, 2);
		}
		return Math.sqrt(sum / numbers.length);
	}

	/**
	 * Computes mean value.
	 * 
	 * @param numbers
	 *            integer list to compute mean for.
	 * @return mean value.
	 */
	private double sDev(List<Double> numbers) {
		double sum = 0;
		double mean = mean(numbers);
		for (Double i : numbers) {
			sum += Math.pow(i - mean, 2);
		}
		return Math.sqrt(sum / numbers.size());
	}

	/**
	 * Computes probability density P(Z<=z). Note: method is uses the standard
	 * normal distribution values.
	 * 
	 * @param z
	 *            boundary value.
	 * @param precision
	 *            precision for computation purposes.
	 * @return probability
	 * @throws NormalException
	 */
	private double Prob(double z, double precision) throws NormalException {
		double prob = 0;
		if (z > 10)
			throw new NormalException("Wrong computation.");
		for (double d = 0.; d <= Math.abs(z); d += precision) {
			prob += precision * (1 / Math.sqrt(2 * Math.PI))
					* Math.pow(Math.E, -0.5 * Math.pow(d + 0.5 * precision, 2));
		}
		if (z < 0)
			return 0.5 - prob;
		else
			return 0.5 + prob;
	}

	/**
	 * Capsule method Prob-function for testing purposes.
	 * 
	 * @param z
	 *            boundary value.
	 * @param precision
	 *            precision for computation purposes.
	 * @return probability
	 * @throws NormalException
	 */
	public double ProbCapsule(double z, double precision) throws NormalException {
		return this.Prob(z, precision);
	}

	/**
	 * Computes probability density P(Z<=z). Uses the given normal distribution.
	 * 
	 * @param x
	 *            boundary value.
	 * @param precision
	 *            precision for computation purposes.
	 * @return probability
	 * @throws NormalException
	 */
	public double P(double x, double precision) throws NormalException {
		double z = (x - this.mu) / this.sigma;
		return Prob(z, precision);
	}

	/**
	 * Computes probability density P(Z>z). Uses the given normal distribution.
	 * 
	 * @param x
	 *            boundary value.
	 * @param precision
	 *            precision for computation purposes.
	 * @return probability
	 * @throws NormalException
	 */
	public double P_inv(double x, double precision) throws NormalException {
		return 1 - P(x, precision);
	}

	/**
	 * Computes probability density P(a<=Z<=b). Uses the given normal
	 * distribution.
	 * 
	 * @param a
	 *            boundary value (lower).
	 * @param b
	 *            boundary value (upper).
	 * @param precision
	 *            precision for computation purposes.
	 * @return probability value for the given distribution.
	 * @throws NormalException
	 */
	public double P_bound(double a, double b, double precision) throws NormalException {
		double min = Math.min(a, b);
		double max = Math.max(a, b);
		return P(max, precision) - P(min, precision);
	}

	/**
	 * Approximates the probability density P(I-0.5<=I<I+0.5). Uses the given
	 * normal distribution.
	 * 
	 * @param i
	 *            integer to approximated discrete probability for.
	 * @param precision
	 *            precision for computation purposes.
	 * @return probability value for the given distribution.
	 * @throws NormalException
	 */
	public double P_discrete(int i, double precision) throws NormalException {
		if (i <= 0) {
			return this.P(0.5, precision);
		}
		double min = i - 0.5;
		double max = i + 0.5;
		return P(max, precision) - P(min, precision);
	}

	/**
	 * Computes z-factor necessary to cover a certain percentile.
	 * 
	 * @param percentage
	 *            percentile to cover.
	 * @param precision
	 *            precision for computation purposes.
	 * @return corresponding z-factor.
	 */
	public static double zFactor(double percentage, double precision) {
		double covered = 0;
		double z = 0;
		while (covered < percentage / 2) {
			covered += (1 / Math.sqrt(2 * Math.PI)) * precision
					* Math.pow(Math.E, -0.5 * Math.pow(z + 0.5 * precision, 2));
			z += precision;
		}
		return z;
	}

	/**
	 * Computes x-factor necessary to cover a certain percentile.
	 * 
	 * @param percentage
	 *            percentile to cover.
	 * @param precision
	 *            precision for computation purposes.
	 * @return corresponding x-factor.
	 */
	public double xFactor(double percentage, double precision) {
		return this.zFactor(percentage, precision) * this.sigma + this.mu;
	}

	/**
	 * Computes function value for value x.
	 * 
	 * @param x
	 *            respective value.
	 * @return corresponding function value.
	 */
	public double Norm(double x) {
		return (double) (1. / (this.sigma * Math.sqrt(2. * Math.PI)))
				* Math.pow(Math.E, -1. * ((Math.pow(x - this.mu, 2)) / (2 * Math.pow(this.sigma, 2))));
	}

	/**
	 * Determines area difference between two normal distributions.
	 * 
	 * @param n1
	 *            first Normal-Object.
	 * @param n2
	 *            second Normal-Object.
	 * @param precision
	 *            precision figure.
	 * @return absolute surface measure.
	 */
	private static double absoluteSurface(Normal n1, Normal n2, double precision) {
		double surface = 0.;
		double min_mu = Math.min(n1.mu, n2.mu);
		double max_mu = Math.max(n1.mu, n2.mu);
		double max_sigma = Math.max(n1.sigma, n2.sigma);
		double min = Math.max(min_mu - 50. * max_sigma, -250.);
		double max = Math.min(max_mu + 50. * max_sigma, 250.);
		for (double i = min; i <= max; i += precision) {
			surface += (precision / 6.)
					* Math.abs((n1.Norm(i) + 4. * n1.Norm(i + 0.5 * precision) + n1.Norm(i + precision))
							- (n2.Norm(i) + 4. * n2.Norm(i + 0.5 * precision) + n2.Norm(i + precision)));
		}
		return surface;
	}

	/**
	 * Determines matching number between two Normal-Objects.
	 * 
	 * @param n1
	 *            first Normal-Object.
	 * @param n2
	 *            second Normal-Object.
	 * @return matching percentage.
	 */
	public static double matchPercentage(Normal n1, Normal n2) {
		if (n1.sigma == 0 && n2.sigma == 0) {
			if (Math.max(n1.mu, n2.mu) != 0) {
				return Math.min(n1.mu, n2.mu) / Math.max(n1.mu, n2.mu);
			} else {
				return 1.;
			}
		}
		if (n1.sigma == 0 || n2.sigma == 0) {
			return -1.;
		}
		double A = absoluteSurface(n1, n2, 0.01);
		return 1. - 0.5 * A;
	}

	/**
	 * Compares two Normal-objects.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Normal) {
			Normal that = (Normal) obj;
			return this.mu == that.mu && this.sigma == that.sigma;
		}
		return false;
	}

}