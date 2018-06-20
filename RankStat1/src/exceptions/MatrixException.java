package exceptions;

/**
 * Customized exception class for Matrix class usage.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class MatrixException extends Exception {

	/**
	 * Class attributes.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * String constructor
	 * 
	 * @param s
	 *            exception message.
	 */
	public MatrixException(String s) {
		super(s);
	}
	
}