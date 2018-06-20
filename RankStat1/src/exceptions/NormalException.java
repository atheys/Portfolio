package exceptions;

/**
 * Customized exception class for Normal class usage.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class NormalException extends Exception {

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
	public NormalException(String s) {
		super(s);
	}
	
}