package simulation.simulate;

import data.core.structure.GameDay;

/**
 * Simulation Settings Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class SimulationSettings {

	/**
	 * Simulation begin dates.
	 */
	public static final GameDay begin_date_2009 = new GameDay(1, 7, 2009);
	public static final GameDay begin_date_2010 = new GameDay(7, 7, 2010);
	public static final GameDay begin_date_2011 = new GameDay(6, 7, 2011);
	public static final GameDay begin_date_2012 = new GameDay(4, 7, 2012);
	public static final GameDay begin_date_2013 = new GameDay(3, 7, 2013);
	public static final GameDay begin_date_2014 = new GameDay(2, 7, 2014);
	public static final GameDay begin_date_2015 = new GameDay(1, 7, 2015);
	public static final GameDay begin_date_2016 = new GameDay(6, 7, 2016);
	// Collection array
	public static final GameDay[] begin_dates = { begin_date_2009, begin_date_2010, begin_date_2011, begin_date_2012,
			begin_date_2013, begin_date_2014, begin_date_2015, begin_date_2016 };

	/**
	 * Filtering Schemes.
	 */
	public static final String[] filter_scheme1 = { "O" };
	public static final String[] filter_scheme2 = { "D" };
	public static final String[] filter_scheme3 = { "OA" };
	public static final String[] filter_scheme4 = { "DA" };
	public static final String[] filter_scheme5 = { "P" };
	public static final String[] filter_scheme6 = { "O", "D" };
	public static final String[] filter_scheme7 = { "OA", "DA" };
	// Collection array
	public static final String[][] filter_scheme = { filter_scheme1, filter_scheme2, filter_scheme3, filter_scheme4,
			filter_scheme5, filter_scheme6, filter_scheme7 };

}