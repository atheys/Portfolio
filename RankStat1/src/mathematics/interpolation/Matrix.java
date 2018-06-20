package mathematics.interpolation;

import exceptions.MatrixException;

/**
 * Matrix-class for intensified matrix calculations.
 * 
 * @author Andreas Theys
 * @version 8.0
 */
public class Matrix {

	/**
	 * Class-specific attributes.
	 */
	private double[][] matrix;
	private int rows;
	private int columns;
	private double determinant;

	/**
	 * Default constructor.
	 */
	public Matrix() {
	}

	/**
	 * Copy constructor.
	 * 
	 * @param m
	 *            Matrix-object to copy.
	 */
	public Matrix(Matrix m) {
		this.matrix = m.matrix;
		this.rows = m.rows;
		this.columns = m.columns;
		this.determinant = m.determinant;
	}

	/**
	 * Array constructor.
	 * 
	 * @param matrix
	 *            double nested array to construct Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		if (this.rows == 0) {
			this.columns = 0;
			this.determinant = 0;
		} else {
			this.columns = matrix[0].length;
			try {
				this.determinant = this.determinant();
			} catch (Exception e) {
				// Logger capacity
			}
		}
	}

	/**
	 * Getter matrix.
	 * 
	 * @return matrix grid of the object (nested double array).
	 */
	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * Setter matrix.
	 * 
	 * @param matrix
	 *            new matrix grid for the object.
	 */
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		if (this.rows == 0) {
			this.columns = 0;
			this.determinant = 0;
		} else {
			this.columns = matrix[0].length;
			try {
				this.determinant = this.determinant();
			} catch (Exception e) {
				// Logger capacity
			}
		}
	}

	/**
	 * Getter rows.
	 * 
	 * @return number of rows in matrix.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Getter columns.
	 * 
	 * @return number of columns in matrix.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Getter determinant.
	 * 
	 * @return determinant of the matrix.
	 */
	public double getDeterminant() {
		return determinant;
	}

	/**
	 * Checks if no null-objects or -values are present in matrix array.
	 * 
	 * @return evaluation of the being null of the matrix coefficients.
	 */
	private boolean checkNotNull() {
		return this.matrix != null;
	}

	/**
	 * Capsule method for checkNotNull function for testing purposes.
	 * 
	 * @return evaluation of the being null of the matrix coefficients.
	 */
	boolean checkNotNullCapsule() {
		return this.checkNotNull();
	}

	/**
	 * Checks compatibility for addition between two matrices.
	 * 
	 * @param m
	 *            Matrix-object to check for.
	 * @return compatibility evaluation.
	 */
	private boolean compatibleForAdd(Matrix m) {
		return m != null && this.rows == m.rows && this.columns == m.columns;
	}

	/**
	 * Capsule method compatibleForAdd function for testing purposes.
	 * 
	 * @param m
	 *            Matrix-object to check for.
	 * @return compatibility evaluation.
	 */
	boolean compatibleForAddCapsule(Matrix m) {
		return this.compatibleForAdd(m);
	}

	/**
	 * Checks compatibility for right multiplication with Matrix-object m.
	 * 
	 * @param m
	 *            Matrix-object to multiply with.
	 * @return compatibility evaluation.
	 */
	private boolean compatibleForMultiply(Matrix m) {
		return this.columns == m.rows;
	}

	/**
	 * Capsule method for compatibleForMultiply function for testing purposes.
	 * 
	 * @param m
	 *            Matrix-object to multiply with.
	 * @return compatibility evaluation.
	 */
	boolean compatibleForMultiplyCapsule(Matrix m) {
		return this.compatibleForMultiply(m);
	}

	/**
	 * Checks compatibility for augmentation with Matrix-object b.
	 * 
	 * @param b
	 *            Matrix-object to augment with.
	 * @return compatibility evaluation.
	 */
	private boolean compatibleForAugment(Matrix b) {
		return this.rows == b.rows && b.columns == 1;
	}

	/**
	 * Capsule method compatibleForAugment function for testing purposes.
	 * 
	 * @param b
	 *            Matrix-object to augment with.
	 * @return compatibility evaluation.
	 */
	boolean compatibleForAugmentCapsule(Matrix b) {
		return this.compatibleForAugment(b);
	}

	/**
	 * Checks compatibility for matrix reduction for a given row and column
	 * number.
	 * 
	 * @param row
	 *            matrix row to reduce for.
	 * @param column
	 *            matrix column to reduce for.
	 * @return compatibility evaluation.
	 */
	private boolean reducable(int row, int column) {
		return this.rows >= 2 && this.columns >= 2 && row < this.rows && column < this.columns;
	}

	/**
	 * Capsule method for reducable function for testing purposes.
	 * 
	 * @param row
	 *            matrix row to reduce for.
	 * @param column
	 *            matrix column to reduce for.
	 * @return compatibility evaluation.
	 */
	boolean reducableCapsule(int row, int column) {
		return this.reducable(row, column);
	}

	/**
	 * Checks if Matrix-object represents square matrix.s
	 * 
	 * @return evaluation of matrix squareness.
	 */
	private boolean isSquare() {
		return this.rows == this.columns;
	}

	/**
	 * Capsule method isSquare method for testing purposes.
	 * 
	 * @return evaluation of matrix squareness.
	 */
	boolean isSquareCapsule() {
		return this.isSquare();
	}

	/**
	 * Transposes current Matrix-object while creating and storing result in new
	 * Matrix-object.
	 * 
	 * @return transposed Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix T() throws MatrixException {
		if (checkNotNull()) {
			double[][] temp = new double[this.columns][this.rows];
			for (int i = 0; i < this.matrix.length; i++) {
				for (int j = 0; j < this.matrix[i].length; j++) {
					temp[j][i] = this.matrix[i][j];
				}
			}
			return new Matrix(temp);
		} else
			throw new MatrixException("Matrix is null-Object.");
	}

	/**
	 * Adds up Matrix-object to current Matrix-object while creating and storing
	 * result in new Matrix-object.
	 * 
	 * @param m
	 * @return new summed Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix add(Matrix m) throws MatrixException {
		if (checkNotNull() && compatibleForAdd(m)) {
			double[][] temp = new double[this.rows][this.columns];
			for (int i = 0; i < this.matrix.length; i++) {
				for (int j = 0; j < this.matrix[i].length; j++) {
					temp[i][j] = this.matrix[i][j] + m.matrix[i][j];
				}
			}
			return new Matrix(temp);
		} else
			throw new MatrixException("Matrix is not compatible for addition.");
	}

	/**
	 * Multiplies current matrix with scalar value while creating and storing
	 * result in new Matrix-object.
	 * 
	 * @param s
	 *            scalar value to multiply with.
	 * @return newly multiplied Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix scalar(double s) throws MatrixException {
		if (checkNotNull()) {
			for (int i = 0; i < this.matrix.length; i++) {
				for (int j = 0; j < this.matrix[i].length; j++) {
					this.matrix[i][j] *= s;
				}
			}
			return new Matrix(this.matrix);
		} else
			throw new MatrixException("Matrix is null-Object.");
	}

	/**
	 * Right multiplies current matrix with given Matrix-object while creating
	 * and storing result in new Matrix-object.
	 * 
	 * @param m
	 * @return newly multiplied Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix multiply(Matrix m) throws MatrixException {
		if (m != null && m.checkNotNull() && checkNotNull() && compatibleForMultiply(m)) {
			double[][] temp = new double[this.rows][m.columns];
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < m.columns; j++) {
					temp[i][j] = 0;
					for (int k = 0; k < this.columns; k++) {
						temp[i][j] += this.matrix[i][k] * m.matrix[k][j];
					}
				}
			}
			return new Matrix(temp);
		} else
			throw new MatrixException("Matrix is not compatible for multiplication.");
	}

	/**
	 * Augments current Matrix-object with a given single-column Matrix-object
	 * while creating and storing result in new Matrix-object.
	 * 
	 * @param b
	 *            single-columns to augment with.
	 * @return augmented Matrix object.
	 * @throws MatrixException
	 */
	public Matrix augment(Matrix b) throws MatrixException {
		if (b != null && b.checkNotNull() && this.checkNotNull() && this.compatibleForAugment(b)) {
			double[][] temp = new double[this.rows][this.columns + 1];
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns + 1; j++) {
					if (j == this.columns) {
						temp[i][j] = b.matrix[i][0];
					} else {
						temp[i][j] = this.matrix[i][j];
					}

				}
			}
			return new Matrix(temp);
		} else
			throw new MatrixException("Matrix is not compatible for augmentation.");
	}

	/**
	 * Reduces Matrix-object by deleting one particular row and column element
	 * while creating and storing result in new Matrix-object.
	 * 
	 * @param row
	 *            row number to delete.
	 * 
	 * @param column
	 *            column number to delete.
	 * @return Reduced Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix reduce(int row, int column) throws MatrixException {
		if (checkNotNull() && reducable(row, column)) {
			double[][] temp = new double[this.rows - 1][this.columns - 1];
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					if (i < row) {
						if (j < column) {
							temp[i][j] = this.matrix[i][j];
						}
						if (j > column) {
							temp[i][j - 1] = this.matrix[i][j];
						}
					}
					if (i > row) {
						if (j < column) {
							temp[i - 1][j] = this.matrix[i][j];

						}
						if (j > column) {
							temp[i - 1][j - 1] = this.matrix[i][j];
						}
					}
				}
			}
			return new Matrix(temp);
		} else
			throw new MatrixException("Matrix is not compatible for reduction.");
	}

	/**
	 * Determines adjunct Matrix-object while creating and storing result in new
	 * Matrix-object.
	 * 
	 * @return adjunct Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix adjunct() throws MatrixException {
		if (checkNotNull() && isSquare()) {
			double[][] temp = new double[this.rows][this.columns];
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					temp[i][j] = Math.pow(-1, i + j) * this.reduce(i, j).determinant;
				}
			}
			return new Matrix(temp);
		} else
			throw new MatrixException("Matrix is not compatible for adjunction.");
	}

	/**
	 * Determines inverse matrix from current Matrix-object while creating and
	 * storing result in new Matrix-object.
	 * 
	 * @return inverse Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix inverse() throws MatrixException {
		if (checkNotNull() && isSquare()) {
			if (this.rows == 0 || this.determinant == 0) {
				return null;
			}
			if (this.rows == 1) {
				double[][] matrix = { { 1 / this.matrix[0][0] } };
				return new Matrix(matrix);
			}
			double[][] temp = new double[this.rows][this.columns];
			Matrix adj = this.adjunct();
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					temp[i][j] = (1 / this.determinant) * adj.matrix[i][j];
				}
			}
			return new Matrix(temp).T();
		} else
			throw new MatrixException("Matrix is not compatible for inversion.");
	}

	/**
	 * Provides a least-squares solution for a given observation matrix b while
	 * creating and storing result in new Matrix-object..
	 * 
	 * @param b
	 *            observation Matrix-object.
	 * @return least-squares solution Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix leastSquares(Matrix b) throws MatrixException {
		if (augment(b) != null) {
			return ((this.T().multiply(this)).inverse()).multiply(this.T().multiply(b));
		}
		throw new MatrixException("Matrix is not compatible for a least-squares solution.");
	}

	/**
	 * Provides a least-squares solution for a given observation matrix b and
	 * weighted Matrix-object while creating and storing result in new
	 * Matrix-object.
	 * 
	 * @param b
	 *            observation Matrix-object.
	 * @param weights
	 *            weighted Matrix-object.
	 * @return least-squares solution Matrix-object.
	 * @throws MatrixException
	 */
	public Matrix leastSquares(Matrix b, Matrix weights) throws MatrixException {
		if (augment(b) != null && weights.isSquare() && weights.rows == b.rows) {
			Matrix aTWA = this.multiply(weights.multiply(weights)).multiply(this.T());
			Matrix aTWB = this.multiply(weights.multiply(weights)).multiply(b);
			return aTWA.inverse().multiply(aTWB);
		}
		throw new MatrixException("Matrix is not compatible for a weighted least-squares solution.");
	}

	/**
	 * Determines determinant of the current Matrix-object.
	 * 
	 * @return determinant of the matrix.
	 * @throws MatrixException
	 */
	private double determinant() throws MatrixException {
		double determinant = 0;
		if (checkNotNull() && this.rows != 0 && isSquare()) {
			if (this.rows == 1) {
				determinant = this.matrix[0][0];
			}
			if (this.rows == 2) {
				determinant = this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];
			}
			if (this.rows > 2) {
				for (int i = 0; i < this.rows; i++) {
					determinant += Math.pow(-1, i) * this.matrix[i][0] * reduce(i, 0).determinant();
				}
			}
		}
		return determinant;
	}

	/**
	 * Determines whether two matrices are equal.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Matrix) {
			Matrix that = (Matrix) obj;
			boolean eval = (this.rows == that.rows) && (this.columns == that.columns)
					&& (this.determinant == that.determinant);
			if (eval) {
				for (int i = 0; i < this.matrix.length; i++) {
					for (int j = 0; j < this.matrix[i].length; j++) {
						eval = eval && this.matrix[i][j] == that.matrix[i][j];
					}
				}
			}
			return eval;
		}
		return false;
	}

}