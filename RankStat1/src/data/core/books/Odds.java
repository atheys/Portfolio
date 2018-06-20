package data.core.books;

import data.core.structure.Game;

/**
 * Odds class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Odds {

	/**
	 * Class attributes.
	 */
	private String id;
	private Bookie bookie;
	private Game game;
	private double winHome;
	private double draw;
	private double winAway;

	/**
	 * Copy constructor.
	 * 
	 * @param o
	 *            Odds-object to copy.
	 */
	public Odds(Odds o) {
		this.id = o.id;
		this.bookie = o.bookie;
		this.game = o.game;
		this.winHome = o.winHome;
		this.draw = o.draw;
		this.winAway = o.winAway;
	}

	/**
	 * General constructor.
	 * 
	 * @param bookie
	 *            bookie that communicates the odds.
	 * @param game
	 *            game the odds are on.
	 * @param winHome
	 *            return value in case the home team wins.
	 * @param draw
	 *            return value in case of a draw.
	 * @param winAway
	 *            return value in case the away team wins.
	 */
	public Odds(String id, Bookie bookie, Game game, double winHome, double draw, double winAway) {
		this.id = id;
		this.bookie = bookie;
		this.game = game;
		this.winHome = winHome;
		this.draw = draw;
		this.winAway = winAway;
		this.updateBookie();
	}

	/**
	 * Getter ID.
	 * 
	 * @return ID label of the odds.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter ID.
	 * 
	 * @param id
	 *            new ID label for the odds.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter bookie.
	 * 
	 * @return bookie that provides these odds.
	 */
	public Bookie getBookie() {
		return bookie;
	}

	/**
	 * Setter bookie.
	 * 
	 * @param bookie
	 *            new bookie that provides the odds.
	 */
	public void setBookie(Bookie bookie) {
		this.bookie = bookie;
	}

	/**
	 * Getter game.
	 * 
	 * @return game corresponding to these odds.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Setter game.
	 * 
	 * @param game
	 *            new game corresponding to the odds.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Getter home win return.
	 * 
	 * @return return in case of a home win.
	 */
	public double getWinHome() {
		return winHome;
	}

	/**
	 * Setter home win return.
	 * 
	 * @param winHome
	 *            new return in case of a home win.
	 */
	public void setWinHome(double winHome) {
		this.winHome = winHome;
	}

	/**
	 * Getter draw return.
	 * 
	 * @return return in case of a draw.
	 */
	public double getDraw() {
		return draw;
	}

	/**
	 * Setter draw return.
	 * 
	 * @param draw
	 *            new return in case of a draw.
	 */
	public void setDraw(double draw) {
		this.draw = draw;
	}

	/**
	 * Getter away win return.
	 * 
	 * @return return in case of an away win.
	 */
	public double getWinAway() {
		return winAway;
	}

	/**
	 * Setter away win return.
	 * 
	 * @param winAway
	 *            new return in case of an away win.
	 */
	public void setWinAway(double winAway) {
		this.winAway = winAway;
	}

	/**
	 * Updates bookie for the current Odds-object.
	 */
	private void updateBookie() {
		this.bookie.addOdds(this);
	}

	/**
	 * Compares two Odds-object.
	 * 
	 * @param obj
	 *            Object to compare with.
	 * @return comparison evaluation
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Odds) {
			Odds that = (Odds) obj;
			return this.bookie.equals(that.bookie) && this.id.equals(that.id);
		}
		return false;
	}

}