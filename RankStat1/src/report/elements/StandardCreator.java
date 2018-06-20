package report.elements;

/**
 * LaTeX-based report StandardCreator Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class StandardCreator {

	/**
	 * Creates a standard LaTeX preamble.
	 * 
	 * @return corresponding preamble String.
	 */
	public static String createStandardPreamble() {
		String preamble = new String();
		preamble += CommentsCreator.makeComment("Standard Preamble");
		preamble += "\\documentclass{article} \n" + "\\usepackage[utf8]{inputenc} \n" + "\\usepackage{amssymb} \n"
				+ "\\usepackage{enumitem} \n" + "\\usepackage{amsmath} \n" + "\\usepackage{graphicx} \n"
				+ "\\usepackage{float} \n" + "\\usepackage[table]{xcolor} \n" + "\\usepackage{longtable}"
				+ "\\usepackage[a4paper,total={6in, 9in}]{geometry} \n";
		return preamble;
	}

	/**
	 * Makes document begin.
	 * 
	 * @return document begin String.
	 */
	public static String createDocumentBegin() {
		String begin = new String();
		begin += CommentsCreator.makeComment("Document Begin");
		begin += "\\begin{document} \n";
		return begin;
	}

	/**
	 * Makes document end.
	 * 
	 * @return document end String.
	 */
	public static String createDocumentEnd() {
		String begin = new String();
		begin += CommentsCreator.makeComment("Document End");
		begin += "\\end{document} \n";
		return begin;
	}

	/**
	 * Makes basic title.
	 * 
	 * @param version
	 *            software version
	 * @param generation
	 *            generation of model.
	 * @return title String.
	 */
	public static String createBasicTitle(double version, int generation) {
		String title = new String();
		title += CommentsCreator.makeComment("Title");
		title += "\\begin{center} \n" + "\\textsc{The RankStat Project \\\\} \n" + "\\textsc{\\large \\textbf{RankStat "
				+ version + ": Generation " + generation + " Simulations} \\\\[0.5cm]} \n" + "\\end{center} \n";
		return title;
	}

	/**
	 * Makes section.
	 * 
	 * @param title
	 *            title of the section.
	 * @return section begin String.
	 */
	public static String makeSection(String title) {
		return CommentsCreator.makeComment(title) + "\\section{" + title + "} \n";
	}

	/**
	 * Makes subsection.
	 * 
	 * @param title
	 *            title of the subsection.
	 * @return subsection begin String.
	 */
	public static String makeSubSection(String title) {
		return "\\subsection{" + title + "} \n";
	}

	/**
	 * Makes subsubsection.
	 * 
	 * @param title
	 *            title of the subsubsection.
	 * @return subsubsection begin String.
	 */
	public static String makeSubSubSection(String title) {
		return "\\subsubsection{" + title + "} \n";
	}

}