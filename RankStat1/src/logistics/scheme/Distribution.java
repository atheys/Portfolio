package logistics.scheme;

import java.util.ArrayList;

import data.core.books.Odds;
import data.core.structure.Game;
import data.core.structure.GameDay;
import logistics.base.FrontMan;
import logistics.base.Regulator;
import logistics.units.DUnit;
import mathematics.portfolio.Asset;
import simulation.process.POptimizer;

/**
 * Distribution Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Distribution {

	/**
	 * Class attributes.
	 */
	private Dispatcher dp;
	private ArrayList<POptimizer> portfolios;
	private ArrayList<DUnit> dunits;

	/**
	 * General Constructor.
	 * 
	 * @param gd
	 *            relevant GameDay-Object.
	 * @param frontmen
	 *            list of all FrontMan-Objects.
	 * @param regulators
	 *            list of all Regulator-Objects.
	 * @param portfolios
	 *            list of all relevant POptimizer-Objects.
	 */
	public Distribution(GameDay gd, ArrayList<FrontMan> frontmen, ArrayList<Regulator> regulators,
			ArrayList<POptimizer> portfolios) {
		this.dp = new Dispatcher(gd, frontmen, regulators);
		this.portfolios = portfolios;
		this.makeDUnits();
	}

	/**
	 * Makes all relevant DUnit-Objects.
	 */
	private void makeDUnits() {
		this.dunits = new ArrayList<DUnit>();
		for (FrontMan fm : this.dp.getFrontmen()) {
			this.dunits.add(new DUnit(this.dp.getGd(), fm));
		}
	}

	/**
	 * Checks if a Game-Object is eligible for distribution.
	 * 
	 * @param g
	 *            Game-Object to check for.
	 * @return relevance evaluation.
	 */
	private boolean relevant(Game g) {
		for (POptimizer po : this.portfolios) {
			for (Asset a : po.getPseudo_portfolio().getAssets()) {
				if (a.getGame_id().equals(g.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Arranges odds in function of the Regulator-list.
	 * 
	 * @param odds
	 *            list of relevant Odds.
	 * @return corresponding arranged Odds-list.
	 */
	private ArrayList<Odds> arrangeOdds(ArrayList<Odds> odds) {
		ArrayList<Odds> new_odds = new ArrayList<Odds>();
		for (Regulator r : this.dp.getRegulators()) {
			for (Odds o : odds) {
				if (o.getBookie().getId().equals(r.getB().getId())) {
					new_odds.add(o);
				}
			}
		}
		return new_odds;
	}

	/**
	 * Optimal distribution algorithm.
	 */
	private void distribute(Game g) {
		if (relevant(g)) {
			ArrayList<Odds> odds = arrangeOdds(g.getCompetition().findOdds(g));
		}
	}

	/**
	 * Dispatcher getter.
	 * 
	 * @return relevant Dispatcher-Object.
	 */
	public Dispatcher getDp() {
		return dp;
	}

	/**
	 * Portfolios getter.
	 * 
	 * @return list of relevant POptimizer-Objects.
	 */
	public ArrayList<POptimizer> getPortfolios() {
		return portfolios;
	}

	/**
	 * DUnits getter.
	 * 
	 * @return list of DUnit-Objects.
	 */
	public ArrayList<DUnit> getDunits() {
		return dunits;
	}

}