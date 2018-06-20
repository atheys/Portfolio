package simulation.except;

import data.core.structure.Game;

/**
 * Evaluation Except(ion) Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Except {

	/**
	 * Class attributes.
	 */
	// Permanent mathematical evaluation
	private String[] M;
	// Permanent odds-based evaluation
	private String[] O;
	// Permanent financial evaluation
	private String[] F;
	// Permanent efficient evaluation
	private String[] E;

	/**
	 * Default constructor.
	 */
	public Except() {
		String[] types = {};
		this.M = types;
		this.O = types;
		this.F = types;
		this.E = types;
	}

	/**
	 * General constructor.
	 * 
	 * @param M
	 * @param O
	 * @param F
	 * @param E
	 */
	public Except(String[] M, String[] O, String[] F, String[] E) {
		this.M = M;
		this.O = O;
		this.F = F;
		this.E = E;
	}

	/**
	 * Qualitative checking method.
	 * 
	 * @return quality check indicator.
	 */
	public boolean check() {
		if (this.M.length + this.O.length + this.F.length + this.E.length == 0)
			return false;
		for (String s : this.M) {
			for (String t : this.O) {
				if (s.equals(t))
					return false;
				for (String u : this.F) {
					if (s.equals(u))
						return false;
					if (t.equals(u))
						return false;
					for (String v : this.E) {
						if (s.equals(v))
							return false;
						if (t.equals(v))
							return false;
						if (u.equals(v))
							return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Determines if a name string is in the M-Except list.
	 * 
	 * @param s
	 *            name String.
	 * @return corresponding answer.
	 */
	private boolean inM(String s) {
		for (String t : this.M) {
			if (t.equals(s))
				return true;
		}
		return false;
	}

	/**
	 * Determines if a name string is in the O-Except list.
	 * 
	 * @param s
	 *            name String.
	 * @return corresponding answer.
	 */
	private boolean inO(String s) {
		for (String t : this.O) {
			if (t.equals(s))
				return true;
		}
		return false;
	}

	/**
	 * Determines if a name string is in the F-Except list.
	 * 
	 * @param s
	 *            name String.
	 * @return corresponding answer.
	 */
	private boolean inF(String s) {
		for (String t : this.F) {
			if (t.equals(s))
				return true;
		}
		return false;
	}

	/**
	 * Determines if a name string is in the E-Except list.
	 * 
	 * @param s
	 *            name String.
	 * @return corresponding answer.
	 */
	private boolean inE(String s) {
		for (String t : this.E) {
			if (t.equals(s))
				return true;
		}
		return false;
	}

	/**
	 * Determines if a name string is in any Except list.
	 * 
	 * @param s
	 *            name String.
	 * @return corresponding answer.
	 */
	public boolean in(String s) {
		return inM(s) || inO(s) || inF(s) || inE(s);
	}

	/**
	 * Determines if a Game-Object to be subjected the Except-Object.
	 * 
	 * @param g
	 *            relevant Game-Object.
	 * @return corresponding answer.
	 */
	public boolean eligible(Game g) {
		int n = 0;
		if (in(g.getHome().getName()))
			n++;
		if (in(g.getAway().getName()))
			n++;
		if (n == 1)
			return true;
		return false;
	}

	/**
	 * Gives evaluation type of a Game-Object..
	 * 
	 * @param g
	 *            relevant Game-Object.
	 * @param current_type
	 *            current evaluation type.
	 * @return evaluation type String.
	 */
	public String type(Game g, String current_type) {
		if (eligible(g)) {
			String name = new String();
			if (in(g.getHome().getName())) {
				name = g.getHome().getName();
			} else {
				name = g.getAway().getName();
			}
			if (inM(name))
				return "M";
			if (inO(name))
				return "O";
			if (inF(name))
				return "F";
			if (inE(name))
				return "E";
		}
		return current_type;
	}

}