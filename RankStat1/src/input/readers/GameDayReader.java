package input.readers;

/**
 * Imported packages.
 */
import data.core.structure.GameDay;

/**
 * Reader class for GameDay-object.
 * 
 * @author Andreas Theys
 * @version 1.0
 */
public class GameDayReader {

	/**
	 * Reading method for GameDay-objects.
	 * 
	 * @param gameDay
	 *            read-in date for GameDay-object.
	 * @return corresponding GameDay-object.
	 */
	public static GameDay read(String gameDay) {
		gameDay.replaceAll("-", "/");
		gameDay.replaceAll(" ", "");
		String[] gd = gameDay.split("/");
		if (gd.length == 3) {
			int[] date = { 0, 0, 0 };
			for (int i = 0; i < date.length; i++) {
				date[i] = Integer.parseInt(gd[i]);
			}
			return new GameDay(date[1], date[0], date[2]);
		}
		return null;
	}
	
}