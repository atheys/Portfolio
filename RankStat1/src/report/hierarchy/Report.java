package report.hierarchy;

import java.util.ArrayList;

import report.elements.StandardCreator;
import simulation.simulate.Simulation;

/**
 * Report superclass.
 * 
 * @author Andreas Theys.
 * @version 1.0
 * @note LaTeX-based code features included.
 */
public class Report {

	/**
	 * Class attributes.
	 */
	// General features
	protected String name;
	protected Simulation s;
	// Report-specific features
	protected String preamble;
	protected String document_begin;
	protected String title;
	protected ArrayList<String> sections;
	protected String document_end;

	/**
	 * General constructor type.
	 * 
	 * @param name
	 * @param s
	 * @param version
	 * @param generation
	 * 
	 * @note Software version and generational type must be specified by hand.
	 */
	public Report(String name, Simulation s, double version, int generation) {
		this.name = name;
		this.s = s;
		this.preamble = StandardCreator.createStandardPreamble();
		this.document_begin = StandardCreator.createDocumentBegin();
		this.title = StandardCreator.createBasicTitle(version, generation);
		this.sections = new ArrayList<String>();
		this.document_end = StandardCreator.createDocumentEnd();
	}

	/**
	 * Creates report String.
	 * 
	 * @return corresponding report String.
	 */
	public String write() {
		String report = new String();
		report += this.preamble;
		report += this.document_begin;
		report += this.title;
		for (String section : this.sections) {
			report += section;
		}
		report += this.document_end;
		return report;
	}

	/**
	 * Name getter.
	 * 
	 * @return name of the report.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Preamble getter.
	 * 
	 * @return preamble String.
	 */
	public String getPreamble() {
		return preamble;
	}

	/**
	 * Document begin getter.
	 * 
	 * @return document begin String.
	 */
	public String getDocument_begin() {
		return document_begin;
	}

	/**
	 * Document title getter.
	 * 
	 * @return document title String.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Document sections getter.
	 * 
	 * @return document sections String list.
	 */
	public ArrayList<String> getSections() {
		return sections;
	}

	/**
	 * Document end getter.
	 * 
	 * @return document end String.
	 */
	public String getDocument_end() {
		return document_end;
	}

}