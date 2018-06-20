package exceptions;

/**
 * Customized exception class for Collector class usage.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class PerformanceException extends Exception {

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
	public PerformanceException(String s) {
		super(s);
	}

}