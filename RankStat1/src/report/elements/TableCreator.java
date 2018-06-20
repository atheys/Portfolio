package report.elements;

/**
 * LaTeX-based TableCreator Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class TableCreator {

	/**
	 * Creates feature table.
	 * 
	 * @param elements
	 *            structured elements array.
	 * @return corresponding feature table.
	 */
	public static String createFeatureTable(String[][] elements) {
		for (String[] element : elements) {
			if (element.length != 2)
				return "";
		}
		String table = new String();
		table += "\\begin{center} \n";
		table += "\\begin{longtable}{|c||c|} \\hline \n";
		table += "\\cellcolor{gray!35} \\textbf{Feature} & \\cellcolor{gray!35}  \\textbf{Description} \\\\ \\hline \n";
		for (String[] element : elements) {
			table += "\\textbf{" + element[0] + "} & " + element[1] + " \\\\ \\hline \n";
		}
		table += "\\end{longtable} \n";
		table += "\\end{center} \n";
		table += "\\noindent \n";
		return table;
	}

	/**
	 * Creates statistics table.
	 * 
	 * @param elements
	 *            structured elements array.
	 * @return corresponding statistics table.
	 */
	public static String createStatisticsTable(String[][] elements) {
		for (String[] element : elements) {
			if (element.length != 2)
				return "";
		}
		String table = new String();
		table += "\\begin{center}\n";
		table += "\\begin{longtable}{|c||c|} \\hline \n";
		table += "\\cellcolor{gray!35} \\textbf{Statistic} & \\cellcolor{gray!35}  \\textbf{Description} \\\\ \\hline \n";
		for (String[] element : elements) {
			table += "\\textbf{" + element[0] + "} & " + element[1] + " \\\\ \\hline \n";
		}
		table += "\\end{longtable} \n";
		table += "\\end{center}\n";
		table += "\\noindent \n";
		return table;
	}

	/**
	 * Creates competition table.
	 * 
	 * @param elements
	 *            structured elements array.
	 * @return corresponding competition table.
	 */
	public static String createCompetitionTable(String[][] elements) {
		for (String[] element : elements) {
			if (element.length != 2)
				return "";
		}
		String table = new String();
		table += "\\begin{center}\n";
		table += "\\begin{longtable}{|c||c|} \\hline \n";
		table += "\\cellcolor{gray!35} \\textbf{Competition Label} & \\cellcolor{gray!35}  \\textbf{Seasonal Label} \\\\ \\hline \n";
		for (String[] element : elements) {
			table += "\\textbf{" + element[0] + "} & " + element[1] + " \\\\ \\hline \n";
		}
		table += "\\end{longtable} \n";
		table += "\\end{center}\n";
		table += "\\noindent \n";
		return table;
	}

	/**
	 * Creates standard table.
	 * 
	 * @param elements
	 *            structured elements array.
	 * @return corresponding standard table.
	 */
	public static String createStandardTable(String[] titles, String[][] elements) {
		for (String[] element : elements) {
			if (element.length != titles.length)
				return "";
		}
		String positioning = "|";
		for (int i = 0; i < titles.length; i++) {
			positioning += "c|";
		}
		String table = new String();
		table += "\\begin{center} \n";
		table += "\\begin{longtable}{" + positioning + "} \\hline \n";
		String tits = new String();
		for (String title : titles) {
			tits += " & \\cellcolor{gray!35} \\textbf{" + title + "}";
		}
		tits = tits.substring(2);
		tits += " \\\\ \\hline \n";
		table += tits;
		for (String[] element : elements) {
			tits = new String();
			for (String el : element) {
				tits += " & " + el;
			}
			tits = tits.substring(2);
			tits += " \\\\ \\hline \n";
			table += tits;
		}
		table += "\\end{longtable} \n";
		table += "\\end{center} \n";
		table += "\\noindent \n";
		return table;
	}

	/**
	 * Helper control function for input.
	 * 
	 * @param titles
	 *            titles array.
	 * @param elements
	 *            structured elements array.
	 * @param colorGreen
	 *            green color indicators.
	 * @param colorRed
	 *            red color indicators.
	 * @return corresponding input control indicator.
	 */
	private static boolean inputControl(String[] titles, String[][] elements, boolean[][] colorGreen,
			boolean[][] colorRed) {
		for (String[] element : elements) {
			if (element.length != titles.length)
				return false;
		}
		if (elements.length != colorGreen.length)
			return false;
		if (elements.length != colorRed.length)
			return false;
		if (colorGreen.length != colorRed.length)
			return false;
		for (int i = 0; i < colorGreen.length; i++) {
			if (elements[i].length != colorGreen[i].length)
				return false;
			if (elements[i].length != colorRed[i].length)
				return false;
			if (colorGreen[i].length != colorRed[i].length)
				return false;
			for (int j = 0; j < colorGreen[i].length; j++) {
				if (colorGreen[i][j] && colorRed[i][j])
					return false;
			}
		}
		return true;
	}

	/**
	 * Creates coloredtable.
	 * 
	 * @param titles
	 *            titles array.
	 * @param elements
	 *            structured elements array.
	 * @param colorGreen
	 *            green color indicators.
	 * @param colorRed
	 *            red color indicators.
	 * @return corresponding colored table.
	 */
	public static String createColoredTable(String[] titles, String[][] elements, boolean[][] colorGreen,
			boolean[][] colorRed) {
		if (!inputControl(titles, elements, colorGreen, colorRed))
			return "";
		String positioning = "|";
		for (int i = 0; i < titles.length; i++) {
			positioning += "c|";
		}
		String table = new String();
		table += "\\begin{center} \n";
		table += "\\begin{longtable}{" + positioning + "} \\hline \n";
		String tits = new String();
		for (String title : titles) {
			tits += " & \\cellcolor{gray!35} \\textbf{" + title + "}";
		}
		tits = tits.substring(2);
		tits += " \\\\ \\hline \n";
		table += tits;
		for (int i = 0; i < elements.length; i++) {
			tits = new String();
			for (int j = 0; j < elements[i].length; j++) {
				if (colorGreen[i][j]) {
					tits += " & \\cellcolor{green!35} " + elements[i][j];
					continue;
				}
				if (colorRed[i][j]) {
					tits += " & \\cellcolor{red!35} " + elements[i][j];
					continue;
				}
				tits += " & " + elements[i][j];
			}
			tits = tits.substring(2);
			tits += " \\\\ \\hline \n";
			table += tits;
		}
		table += "\\end{longtable} \n";
		table += "\\end{center} \n";
		table += "\\noindent \n";
		return table;
	}

}