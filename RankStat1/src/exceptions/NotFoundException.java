package exceptions;

/**
 * Customized exception class for Collector class.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class NotFoundException extends Exception {

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
	public NotFoundException(String s) {
		super(s);
	}

}