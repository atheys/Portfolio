package input.finders.labels;

/**
 * Class for file storage conventiones.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public abstract class Conventions {

	/**
	 * Class attributes.
	 */

	// Primary competitions
	protected final static String[] p = { "Premier League", "Primera Division", "Ligue 1", "Serie A", "Bundesliga" };
	protected final static String[] pShort = { "PL", "PD", "LU", "SA", "BL" };

	protected final static String[][] pSeasons = { { "0910", "1011", "1112", "1213", "1314", "1415", "1516" },
			{ "0910", "1011", "1112", "1213", "1314", "1415", "1516" },
			{ "0910", "1011", "1112", "1213", "1314", "1415", "1516" },
			{ "0910", "1011", "1112", "1213", "1314", "1415", "1516" },
			{ "0910", "1011", "1112", "1213", "1314", "1415", "1516" } };
	protected final static int[] pGameDays = { 38, 38, 38, 38, 34 };
	protected final static int[] pTeams = { 20, 20, 20, 20, 18 };

	// Secondary competitions
	protected final static String[] s = { "The Championship", "Eredivisie", "Super Lig", "Premier League (RUS)",
			"Serie A (BRA)" };
	protected final static String[] sShort = { "TC", "ED", "SL", "PP", "BB" };
	protected final static String[][] sSeasons = { { "1314", "1415", "1516" }, { "1314", "1415", "1516" },
			{ "1415", "1516" }, { "1415", "1516" }, { "1313", "1414", "1515" } };
	protected final static int[] sGameDays = { 46, 34, 34, 30, 38 };
	protected final static int[] sTeams = { 24, 18, 18, 16, 20 };

}
