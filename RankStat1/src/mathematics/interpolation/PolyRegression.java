package mathematics.interpolation;

/**
 * Polynomial regression class.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class PolyRegression extends Regression {

	/**
	 * Additional class instances.
	 */
	private int degree;
	private Polynomial regression;

	/**
	 * Default constructor.
	 */
	public PolyRegression() {
		super();
		this.id = "Null";
	}

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the regression relationship.
	 * @param x
	 *            increment matrix.
	 * @param y
	 *            observation matrix.
	 * @param degree
	 */
	public PolyRegression(String id, Matrix x, Matrix y, int degree) {
		super(id, x, y);
		this.degree = degree;
		this.regression = this.regress(id);
	}

	/**
	 * Degree getter.
	 * 
	 * @return degree of polynomial regression.
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Degree getter.
	 * 
	 * @param degree
	 *            degree of polynomial regression.
	 */
	public void setDegree(int degree) {
		this.degree = degree;
		this.regression = this.regress(this.id);
	}

	/**
	 * Polynomial regression getter.
	 * 
	 * @return polynomial regression.
	 */
	public Polynomial getRegression() {
		return regression;
	}

	/**
	 * Determines exact coefficients of the polynomial regression.
	 * 
	 * @param id
	 *            ID of the regression relationship.
	 * @return polynomial regression relationship.
	 */
	private Polynomial regress(String id) {
		double[][] coefficients = new double[this.x.getRows()][degree + 1];
		for (int i = 0; i < coefficients.length; i++) {
			for (int j = 0; j < coefficients[i].length; j++) {
				coefficients[i][j] = Math.pow(this.x.getMatrix()[i][0], degree - j);
			}
		}
		Matrix A = new Matrix(coefficients);
		try {
			Matrix sol = A.leastSquares(this.y);
			double[] s = new double[sol.getRows()];
			for (int i = 0; i < sol.getRows(); i++) {
				s[i] = sol.getMatrix()[i][0];
			}
			return new Polynomial(id, s);
		} catch (Exception e) {
			return new Polynomial("Null",0);
		}
	}

	/**
	 * Compares two polynomial regression objects.
	 * 
	 * @param obj
	 *            Object to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof PolyRegression) {
			PolyRegression that = (PolyRegression) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

}