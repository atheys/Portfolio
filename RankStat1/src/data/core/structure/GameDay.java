package data.core.structure;

/**
 * GameDay Class for date handling of soccer games.
 * 
 * @author Andreas Theys.
 * @version 3.0
 */
public class GameDay {

	/**
	 * Class attributes.
	 */
	private int day;
	private int month;
	private int year;

	/**
	 * Copy constructor.
	 * 
	 * @param gd
	 *            GameDay-object to copy.
	 */
	public GameDay(GameDay gd) {
		this.day = gd.day;
		this.month = gd.month;
		this.year = gd.year;
	}

	/**
	 * General constructor.
	 * 
	 * @param day
	 *            day implementation.
	 * @param month
	 *            month implementation.
	 * @param year
	 *            year implementation.
	 */
	public GameDay(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	/**
	 * Day getter.
	 * 
	 * @return day of the date.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Month getter.
	 * 
	 * @return month of the date.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Year getter.
	 * 
	 * @return year of the date.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Determines whether or not a date comes before another, given one.
	 * 
	 * @param gd
	 *            GameDay-object to compare to.
	 * @return determined evaluation.
	 */
	public boolean before(GameDay gd) {
		boolean eval1 = this.year < gd.year;
		boolean eval2 = this.year == gd.year && this.month < gd.month;
		boolean eval3 = this.year == gd.year && this.month == gd.month && this.day < gd.day;
		if (eval1 || eval2 || eval3) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether or not a date comes after another, given one.
	 * 
	 * @param gd
	 *            GameDay-object to compare to.
	 * @return determined evaluation.
	 */
	public boolean after(GameDay gd) {
		if (this.equals(gd) || this.before(gd)) {
			return false;
		}
		return true;
	}

	/**
	 * Adds a number of days to a given date.
	 * 
	 * @param days
	 *            number of days to add.
	 * @return resulting GameDay-Object.
	 */
	public GameDay plus(int days) {
		int new_day = -1;
		int new_month = -1;
		int new_year = this.year;
		int[] months;
		if (this.year % 4 == 0 && this.year % 400 != 0) {
			int[] div = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			months = div;
		} else {
			int[] div = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			months = div;
		}
		new_day = (this.day + days) % months[this.month - 1];
		if (new_day == 0)
			new_day += months[this.month - 1];
		if (new_day == this.day + days) {
			new_month = this.month;
		} else {
			new_month = (this.month + 1) % 12;
			if (new_month == 0) {
				new_month = 12;
			}
			if (new_month == 1) {
				new_year += 1;
			}
		}
		return new GameDay(new_day, new_month, new_year);
	}

	/**
	 * Compares dates.
	 * 
	 * @param obj
	 *            Object entity to compare with.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof GameDay) {
			GameDay that = (GameDay) obj;
			return this.day == that.day && this.month == that.month && this.year == that.year;
		}
		return false;
	}

	/**
	 * Creates String containing GameDay-object data.
	 * 
	 * @return corresponding String.
	 */
	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;
	}

	/**
	 * Creates String containing GameDay data (reverse order).
	 * 
	 * @return corresponding String.
	 */
	public String toStringReverse() {
		return this.year + "-" + this.month + "-" + this.day;
	}

}