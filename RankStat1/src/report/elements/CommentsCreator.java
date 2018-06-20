package report.elements;

/**
 * LaTeX-based CommentsCreator Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class CommentsCreator {

	/**
	 * Comments line.
	 * 
	 * @param comment
	 *            line to comment.
	 * @return corresponding comment line.
	 */
	public static String makeComment(String comment) {
		return "% " + comment + "\n";
	}

}