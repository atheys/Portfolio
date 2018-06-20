package models.general.base;

import java.util.ArrayList;

import data.core.structure.Competition;
import data.core.structure.Game;
import exceptions.CollectorException;
import exceptions.NotFoundException;
import models.general.handlers.Collector;
import models.general.handlers.RegressionHandler;

/**
 * StatsBase class for the statistical stat base for game prediction.
 * 
 * @author Andreas Theys
 * @version 2.0
 */
public class StatsBase {

	/**
	 * Class attributes.
	 */
	private Game game;
	private int nHome;
	private int nAway;
	private double[] weightsHome;
	private double[] weightsAway;
	// Home team
	private Average homeTeamAverage;
	private Deviation homeTeamDeviation;
	private Average homeAdversaryAverage;
	private Deviation homeAdversaryDeviation;
	// Away team
	private Average awayTeamAverage;
	private Deviation awayTeamDeviation;
	private Average awayAdversaryAverage;
	private Deviation awayAdversaryDeviation;
	// Estimation factors
	private boolean homeOverEstimation;
	private boolean awayOverEstimation;
	private boolean warning;
	// Advantage fectors
	private double homeAdvantage;
	private double awayAdvantage;
	// Corresponding regressions
	private double[][][] bonus;
	private double[][][] bonus_home;
	private double[][][] bonus_away;

	/**
	 * Copy constructor.
	 * 
	 * @param sb
	 *            StatsBase-object to copy.
	 */
	public StatsBase(StatsBase sb) {
		this.game = sb.game;
		this.nHome = sb.nHome;
		this.nAway = sb.nAway;
		this.weightsHome = sb.weightsHome;
		this.weightsAway = sb.weightsAway;
		this.homeTeamAverage = sb.homeTeamAverage;
		this.homeTeamDeviation = sb.homeTeamDeviation;
		this.homeAdversaryAverage = sb.homeAdversaryAverage;
		this.homeAdversaryDeviation = sb.homeAdversaryDeviation;
		this.awayTeamAverage = sb.awayTeamAverage;
		this.awayTeamDeviation = sb.awayTeamDeviation;
		this.awayAdversaryAverage = sb.awayAdversaryAverage;
		this.awayAdversaryDeviation = sb.awayAdversaryDeviation;
		this.bonus = sb.bonus;
	}

	/**
	 * General constructor (weighted).
	 * 
	 * @param g
	 *            Game-object to make predictions for.
	 * @param n
	 *            number of games to backtrack for.
	 * @param weights
	 *            weighted of the respective games taken into account.
	 * @throws CollectorException
	 * @throws NotFoundException
	 */
	public StatsBase(Game g, int n, double[] weights) throws CollectorException, NotFoundException {
		if (n == 0)
			throw new CollectorException("No games gathered.");
		if (n != weights.length)
			throw new CollectorException("No computations performed.");
		this.game = g;
		ArrayList<Game> homeTeamGames = null;
		ArrayList<Game> awayTeamGames = null;
		try {
			homeTeamGames = Collector.gatherGames(g.getHome(), g.getGameDay(), n);
			awayTeamGames = Collector.gatherGames(g.getAway(), g.getGameDay(), n);
		} catch (Exception e) {
			throw new CollectorException("Not enough games gathered.");
		}
		this.nHome = n;
		this.nAway = n;
		this.weightsHome = weights;
		this.weightsAway = weights;
		this.determineEstimation();
		this.homeTeamAverage = Collector.teamAverage(g.getHome(), homeTeamGames, weightsHome);
		this.homeTeamDeviation = Collector.teamDeviation(g.getHome(), homeTeamGames, weightsHome);
		this.homeAdversaryAverage = Collector.advAverage(g.getHome(), homeTeamGames, weightsHome);
		this.homeAdversaryDeviation = Collector.advDeviation(g.getHome(), homeTeamGames, weightsHome);
		this.awayTeamAverage = Collector.teamAverage(g.getAway(), awayTeamGames, weightsAway);
		this.awayTeamDeviation = Collector.teamDeviation(g.getAway(), awayTeamGames, weightsAway);
		this.awayAdversaryAverage = Collector.advAverage(g.getAway(), awayTeamGames, weightsAway);
		this.awayAdversaryDeviation = Collector.advDeviation(g.getAway(), awayTeamGames, weightsAway);
		this.computeAdvantages();
		try {
			this.bonus = RegressionHandler.createBonus(g, n, weights);
		} catch (Exception e) {
			System.out.println("No bonusses computed.");
			throw new CollectorException("No bonusses computed.");
		}
	}

	/**
	 * General constructor (weighted).
	 * 
	 * @param g
	 *            Game-object to make predictions for.
	 * @param n
	 *            number of games to backtrack for.
	 * @param weights
	 *            weighted of the respective games taken into account.
	 * @throws CollectorException
	 * @throws NotFoundException
	 */
	public StatsBase(Game g, double[] weights, int n) throws CollectorException, NotFoundException {
		if (n == 0)
			throw new CollectorException("No games gathered.");
		if (n != weights.length)
			throw new CollectorException("No computations performed.");
		this.game = g;
		ArrayList<Game> homeTeamGames = null;
		ArrayList<Game> awayTeamGames = null;
		try {
			homeTeamGames = Collector.gatherHomeGames(g.getHome(), g.getGameDay(), n);
			awayTeamGames = Collector.gatherAwayGames(g.getAway(), g.getGameDay(), n);
		} catch (Exception e) {
			throw new CollectorException("Not enough games gathered.");
		}
		this.nHome = n;
		this.nAway = n;
		this.weightsHome = weights;
		this.weightsAway = weights;
		this.determineEstimation();
		this.homeTeamAverage = Collector.teamAverage(g.getHome(), homeTeamGames, weightsHome);
		this.homeTeamDeviation = Collector.teamDeviation(g.getHome(), homeTeamGames, weightsHome);
		this.homeAdversaryAverage = Collector.advAverage(g.getHome(), homeTeamGames, weightsHome);
		this.homeAdversaryDeviation = Collector.advDeviation(g.getHome(), homeTeamGames, weightsHome);
		this.awayTeamAverage = Collector.teamAverage(g.getAway(), awayTeamGames, weightsAway);
		this.awayTeamDeviation = Collector.teamDeviation(g.getAway(), awayTeamGames, weightsAway);
		this.awayAdversaryAverage = Collector.advAverage(g.getAway(), awayTeamGames, weightsAway);
		this.awayAdversaryDeviation = Collector.advDeviation(g.getAway(), awayTeamGames, weightsAway);
		this.computeAdvantages();
		try {
			this.bonus_home = RegressionHandler.createBonusHome(g, n, weights);
			this.bonus_away = RegressionHandler.createBonusAway(g, n, weights);
		} catch (Exception e) {
			System.out.println("No bonusses computed.");
			throw new CollectorException("No bonusses computed.");
		}
	}

	/**
	 * Determines estimation factors.
	 */
	private void determineEstimation() {
		try {
			Competition pseudo = this.game.getCompetition().pseudoCompetition(game.getGameDay());
			if (pseudo.getRank(this.game.getHome()) > pseudo.getRank(this.game.getAway())) {
				this.homeOverEstimation = false;
				this.awayOverEstimation = true;
				this.warning = false;
				return;
			}
			if (pseudo.getRank(this.game.getHome()) == pseudo.getRank(this.game.getAway())) {
				this.homeOverEstimation = false;
				this.awayOverEstimation = false;
				this.warning = false;
				return;
			}
			this.homeOverEstimation = true;
			this.awayOverEstimation = false;
			this.warning = false;
			return;
		} catch (Exception e) {
			this.homeOverEstimation = false;
			this.awayOverEstimation = false;
			this.warning = true;
			return;
		}
	}

	/**
	 * Computes advantage figures.
	 */
	private void computeAdvantages() {
		double hp = this.homeTeamAverage.getHomePercentage();
		double ap = this.awayTeamAverage.getAwayPercentage();
		double h = this.homeTeamAverage.getImportance();
		double a = this.awayTeamAverage.getImportance();
		double hest;
		double aest;
		if (this.warning) {
			hest = 0.;
			aest = 0.;
		} else {
			if (this.homeOverEstimation) {
				hest = this.homeTeamAverage.getOverPercentage();
			} else {
				hest = this.homeTeamAverage.getUnderPercentage();
			}
			if (this.awayOverEstimation) {
				aest = this.awayTeamAverage.getOverPercentage();
			} else {
				aest = this.awayTeamAverage.getUnderPercentage();
			}
		}
		this.homeAdvantage = 1. + 0.1 * hp + 0.05 * hest + 0.05*h;
		this.awayAdvantage = 1. + 0.1 * ap + 0.05 * aest + 0.05*a;
		;
	}

	/**
	 * Game getter.
	 * 
	 * @return corresponding Game-object.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Getter number of home team games.
	 * 
	 * @return number of home team games.
	 */
	public int getnHome() {
		return nHome;
	}

	/**
	 * Getter number of away team games.
	 * 
	 * @return number of away team games.
	 */
	public int getnAway() {
		return nAway;
	}

	/**
	 * Getter weights array of home team games.
	 * 
	 * @return weights array of home team games.
	 */
	public double[] getWeightsHome() {
		return weightsHome;
	}

	/**
	 * Getter weights array of away team games.
	 * 
	 * @return weights array of away team games.
	 */
	public double[] getWeightsAway() {
		return weightsAway;
	}

	/**
	 * Getter home team average.
	 * 
	 * @return home team average.
	 */
	public Average getHomeTeamAverage() {
		return homeTeamAverage;
	}

	/**
	 * Getter home team deviation.
	 * 
	 * @return home team deviation.
	 */
	public Deviation getHomeTeamDeviation() {
		return homeTeamDeviation;
	}

	/**
	 * Getter home team (adversary) average.
	 * 
	 * @return home team (adversary) average.
	 */
	public Average getHomeAdversaryAverage() {
		return homeAdversaryAverage;
	}

	/**
	 * Getter home team (adversary) deviation.
	 * 
	 * @return home team (adversary) deviation.
	 */
	public Deviation getHomeAdversaryDeviation() {
		return homeAdversaryDeviation;
	}

	/**
	 * Getter away team average.
	 * 
	 * @return away team average.
	 */
	public Average getAwayTeamAverage() {
		return awayTeamAverage;
	}

	/**
	 * Getter away team deviation.
	 * 
	 * @return away team deviation.
	 */
	public Deviation getAwayTeamDeviation() {
		return awayTeamDeviation;
	}

	/**
	 * Getter away team (adversary) average.
	 * 
	 * @return away team (adversary) average.
	 */
	public Average getAwayAdversaryAverage() {
		return awayAdversaryAverage;
	}

	/**
	 * Getter away team (adversary) deviation.
	 * 
	 * @return away team (adversary) deviation.
	 */
	public Deviation getAwayAdversaryDeviation() {
		return awayAdversaryDeviation;
	}

	/**
	 * Home advantage getter.
	 * 
	 * @return home advantage figure.
	 */
	public double getHomeAdvantage() {
		return homeAdvantage;
	}

	/**
	 * Away advantage getter.
	 * 
	 * @return away advantage figure.
	 */
	public double getAwayAdvantage() {
		return awayAdvantage;
	}

	/**
	 * Getter bonus features.
	 * 
	 * @return bonus feature 3D grid.
	 */
	public double[][][] getBonus() {
		return bonus;
	}

	/**
	 * Getter home bonus features.
	 * 
	 * @return home bonus feature 3D grid.
	 */
	public double[][][] getBonus_home() {
		return bonus_home;
	}

	/**
	 * Getter away bonus features.
	 * 
	 * @return away bonus feature 3D grid.
	 */
	public double[][][] getBonus_away() {
		return bonus_away;
	}

}