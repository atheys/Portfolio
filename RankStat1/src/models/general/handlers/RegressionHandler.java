package models.general.handlers;

import java.util.ArrayList;
import data.core.structure.Competition;
import data.core.structure.Game;
import data.core.structure.Team;
import mathematics.interpolation.Matrix;
import mathematics.interpolation.PolyRegression;
import models.general.base.Average;
import exceptions.CollectorException;
import exceptions.NotFoundException;

/**
 * Class of static methods for optimal regression determination and handling.
 * 
 * @author Andreas Theys.
 * @version 4.0
 */
public class RegressionHandler {

	/**
	 * Optimizes for most suitable polynomial regression (degree <= 24).
	 */
	public static PolyRegression optimize(Matrix x, Matrix y) {
		return new PolyRegression("tempID", x, y, x.getRows() - 1);
	}

	/**
	 * Optimizes for most suitable linear regression.
	 */
	public static PolyRegression optimize2(Matrix x, Matrix y) {
		return new PolyRegression("tempID", x, y, 1);
	}

	/**
	 * Ranks matrix coefficient in an increasing order.
	 * 
	 * @param m
	 *            input Matrix (only one column).
	 * @return ordered ranking Matrix-object.
	 */
	private static double[] rankIncreasing(double[] values) {
		double[] result = new double[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = 1.;
		}
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (i != j && values[i] >= values[j]) {
					result[i] += 1.;
				}
			}
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = (double)(result[i]*2)/(values.length + 1);
		}
		return result;
	}

	/**
	 * Ranks matrix coefficient in an increasing order.
	 * 
	 * @param m
	 *            input Matrix (only one column).
	 * @return ordered ranking Matrix-object.
	 */
	private static double[] rankDecreasing(double[] values) {
		double[] result = new double[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = values.length;
		}
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (i != j && values[i] >= values[j]) {
					result[i] -= 1.;
				}
			}
		}
		for (int i = 0; i < result.length; i++) {
			result[i] =  (double)(result[i]*2)/(values.length + 1);
		}
		return result;
	}

	/**
	 * Creates list of corresponding discrete bonus factors.
	 * 
	 * @param g
	 *            Game-object to consider.
	 * @param n
	 *            number of games to take into account.
	 * @param weights
	 *            corresponding weights array.
	 * @return list of corresponding polynomial regressions.
	 * @throws CollectorException
	 * @throws NotFoundException
	 */
	public static double[][][] createBonus(Game g, int n, double[] weights)
			throws CollectorException, NotFoundException {
		if (n == 0)
			throw new CollectorException("No games collected.");
		if (n != weights.length)
			throw new CollectorException("No games collected.");
		Competition pseudo = null;
		try {
			pseudo = g.getCompetition().pseudoCompetition(g.getGameDay(), n);
		} catch (Exception e) {
			throw new CollectorException("Pseudo-Competition Object not created.");
		}
		if (pseudo != null) {
			int nTeams = pseudo.getTeams().size();
			double[][][] bonus = new double[72][2][nTeams];
			for (int i = 0; i < nTeams; i++) {
				Team t = pseudo.getTeams().get(i);
				ArrayList<Game> games;
				Average avg;
				try {
					games = Collector.gatherGames(t, g.getGameDay(), n);
					avg = Collector.teamAverage(t, games, weights);
					bonus[0][0][i] = avg.getAvGoals();
					bonus[1][0][i] = avg.getAvPossession();
					bonus[2][0][i] = avg.getAvSuccessPasses();
					bonus[3][0][i] = avg.getAvTotalPasses();
					bonus[4][0][i] = avg.getAvPassSuccess();
					bonus[5][0][i] = avg.getAvAerialsWon();
					bonus[6][0][i] = avg.getAvShots();
					bonus[7][0][i] = avg.getAvShotsOnTarget();
					bonus[8][0][i] = avg.getAvDribbles();
					bonus[9][0][i] = avg.getAvFouled();
					bonus[10][0][i] = avg.getAvOffSides();
					bonus[11][0][i] = avg.getAvTackles();
					bonus[12][0][i] = avg.getAvFouls();
					bonus[13][0][i] = avg.getAvOffsidesAgainst();
					bonus[14][0][i] = avg.getAvCleanSheets();
					bonus[15][0][i] = avg.getAvYellowCards();
					bonus[16][0][i] = avg.getAvRedCards();
					bonus[17][0][i] = avg.getAvSubstitutions();
					bonus[36][0][i] = avg.getAvGoals();
					bonus[37][0][i] = avg.getAvPossession();
					bonus[38][0][i] = avg.getAvSuccessPasses();
					bonus[39][0][i] = avg.getAvTotalPasses();
					bonus[40][0][i] = avg.getAvPassSuccess();
					bonus[41][0][i] = avg.getAvAerialsWon();
					bonus[42][0][i] = avg.getAvShots();
					bonus[43][0][i] = avg.getAvShotsOnTarget();
					bonus[44][0][i] = avg.getAvDribbles();
					bonus[45][0][i] = avg.getAvFouled();
					bonus[46][0][i] = avg.getAvOffSides();
					bonus[47][0][i] = avg.getAvTackles();
					bonus[48][0][i] = avg.getAvFouls();
					bonus[49][0][i] = avg.getAvOffsidesAgainst();
					bonus[50][0][i] = avg.getAvCleanSheets();
					bonus[51][0][i] = avg.getAvYellowCards();
					bonus[52][0][i] = avg.getAvRedCards();
					bonus[53][0][i] = avg.getAvSubstitutions();
					avg = Collector.advAverage(t, games, weights);
					bonus[18][0][i] = avg.getAvGoals();
					bonus[19][0][i] = avg.getAvPossession();
					bonus[20][0][i] = avg.getAvSuccessPasses();
					bonus[21][0][i] = avg.getAvTotalPasses();
					bonus[22][0][i] = avg.getAvPassSuccess();
					bonus[23][0][i] = avg.getAvAerialsWon();
					bonus[24][0][i] = avg.getAvShots();
					bonus[25][0][i] = avg.getAvShotsOnTarget();
					bonus[26][0][i] = avg.getAvDribbles();
					bonus[27][0][i] = avg.getAvFouled();
					bonus[28][0][i] = avg.getAvOffSides();
					bonus[29][0][i] = avg.getAvTackles();
					bonus[30][0][i] = avg.getAvFouls();
					bonus[31][0][i] = avg.getAvOffsidesAgainst();
					bonus[32][0][i] = avg.getAvCleanSheets();
					bonus[33][0][i] = avg.getAvYellowCards();
					bonus[34][0][i] = avg.getAvRedCards();
					bonus[35][0][i] = avg.getAvSubstitutions();
					bonus[54][0][i] = avg.getAvGoals();
					bonus[55][0][i] = avg.getAvPossession();
					bonus[56][0][i] = avg.getAvSuccessPasses();
					bonus[57][0][i] = avg.getAvTotalPasses();
					bonus[58][0][i] = avg.getAvPassSuccess();
					bonus[59][0][i] = avg.getAvAerialsWon();
					bonus[60][0][i] = avg.getAvShots();
					bonus[61][0][i] = avg.getAvShotsOnTarget();
					bonus[62][0][i] = avg.getAvDribbles();
					bonus[63][0][i] = avg.getAvFouled();
					bonus[64][0][i] = avg.getAvOffSides();
					bonus[65][0][i] = avg.getAvTackles();
					bonus[66][0][i] = avg.getAvFouls();
					bonus[67][0][i] = avg.getAvOffsidesAgainst();
					bonus[68][0][i] = avg.getAvCleanSheets();
					bonus[69][0][i] = avg.getAvYellowCards();
					bonus[70][0][i] = avg.getAvRedCards();
					bonus[71][0][i] = avg.getAvSubstitutions();
				} catch (Exception e) {
					throw new CollectorException("Corresponding data could not be derived.");
				}
			}
			for (int j = 0; j < 36; j++) {
				bonus[j][1] = rankIncreasing(bonus[j][0]);
				bonus[j + 36][1] = rankDecreasing(bonus[j+36][0]);
				;
			}
			return bonus;
		}
		throw new CollectorException("Bonus features not created.");
	}
	
	/**
	 * Creates list of corresponding discrete bonus factors.
	 * 
	 * @param g
	 *            Game-object to consider.
	 * @param n
	 *            number of games to take into account.
	 * @param weights
	 *            corresponding weights array.
	 * @return list of corresponding polynomial regressions.
	 * @throws CollectorException
	 * @throws NotFoundException
	 */
	public static double[][][] createBonusHome(Game g, int n, double[] weights)
			throws CollectorException, NotFoundException {
		if (n == 0)
			throw new CollectorException("No games collected.");
		if (n != weights.length)
			throw new CollectorException("No games collected.");
		Competition pseudo = null;
		try {
			pseudo = g.getCompetition().pseudoCompetitionHome(g.getGameDay(), n);
		} catch (Exception e) {
			throw new CollectorException("Pseudo-Competition Object not created.");
		}
		if (pseudo != null) {
			int nTeams = pseudo.getTeams().size();
			double[][][] bonus = new double[72][2][nTeams];
			for (int i = 0; i < nTeams; i++) {
				Team t = pseudo.getTeams().get(i);
				ArrayList<Game> games;
				Average avg;
				try {
					games = Collector.gatherHomeGames(t, g.getGameDay(), n);
					avg = Collector.teamAverage(t, games, weights);
					bonus[0][0][i] = avg.getAvGoals();
					bonus[1][0][i] = avg.getAvPossession();
					bonus[2][0][i] = avg.getAvSuccessPasses();
					bonus[3][0][i] = avg.getAvTotalPasses();
					bonus[4][0][i] = avg.getAvPassSuccess();
					bonus[5][0][i] = avg.getAvAerialsWon();
					bonus[6][0][i] = avg.getAvShots();
					bonus[7][0][i] = avg.getAvShotsOnTarget();
					bonus[8][0][i] = avg.getAvDribbles();
					bonus[9][0][i] = avg.getAvFouled();
					bonus[10][0][i] = avg.getAvOffSides();
					bonus[11][0][i] = avg.getAvTackles();
					bonus[12][0][i] = avg.getAvFouls();
					bonus[13][0][i] = avg.getAvOffsidesAgainst();
					bonus[14][0][i] = avg.getAvCleanSheets();
					bonus[15][0][i] = avg.getAvYellowCards();
					bonus[16][0][i] = avg.getAvRedCards();
					bonus[17][0][i] = avg.getAvSubstitutions();
					bonus[36][0][i] = avg.getAvGoals();
					bonus[37][0][i] = avg.getAvPossession();
					bonus[38][0][i] = avg.getAvSuccessPasses();
					bonus[39][0][i] = avg.getAvTotalPasses();
					bonus[40][0][i] = avg.getAvPassSuccess();
					bonus[41][0][i] = avg.getAvAerialsWon();
					bonus[42][0][i] = avg.getAvShots();
					bonus[43][0][i] = avg.getAvShotsOnTarget();
					bonus[44][0][i] = avg.getAvDribbles();
					bonus[45][0][i] = avg.getAvFouled();
					bonus[46][0][i] = avg.getAvOffSides();
					bonus[47][0][i] = avg.getAvTackles();
					bonus[48][0][i] = avg.getAvFouls();
					bonus[49][0][i] = avg.getAvOffsidesAgainst();
					bonus[50][0][i] = avg.getAvCleanSheets();
					bonus[51][0][i] = avg.getAvYellowCards();
					bonus[52][0][i] = avg.getAvRedCards();
					bonus[53][0][i] = avg.getAvSubstitutions();
					avg = Collector.advAverage(t, games, weights);
					bonus[18][0][i] = avg.getAvGoals();
					bonus[19][0][i] = avg.getAvPossession();
					bonus[20][0][i] = avg.getAvSuccessPasses();
					bonus[21][0][i] = avg.getAvTotalPasses();
					bonus[22][0][i] = avg.getAvPassSuccess();
					bonus[23][0][i] = avg.getAvAerialsWon();
					bonus[24][0][i] = avg.getAvShots();
					bonus[25][0][i] = avg.getAvShotsOnTarget();
					bonus[26][0][i] = avg.getAvDribbles();
					bonus[27][0][i] = avg.getAvFouled();
					bonus[28][0][i] = avg.getAvOffSides();
					bonus[29][0][i] = avg.getAvTackles();
					bonus[30][0][i] = avg.getAvFouls();
					bonus[31][0][i] = avg.getAvOffsidesAgainst();
					bonus[32][0][i] = avg.getAvCleanSheets();
					bonus[33][0][i] = avg.getAvYellowCards();
					bonus[34][0][i] = avg.getAvRedCards();
					bonus[35][0][i] = avg.getAvSubstitutions();
					bonus[54][0][i] = avg.getAvGoals();
					bonus[55][0][i] = avg.getAvPossession();
					bonus[56][0][i] = avg.getAvSuccessPasses();
					bonus[57][0][i] = avg.getAvTotalPasses();
					bonus[58][0][i] = avg.getAvPassSuccess();
					bonus[59][0][i] = avg.getAvAerialsWon();
					bonus[60][0][i] = avg.getAvShots();
					bonus[61][0][i] = avg.getAvShotsOnTarget();
					bonus[62][0][i] = avg.getAvDribbles();
					bonus[63][0][i] = avg.getAvFouled();
					bonus[64][0][i] = avg.getAvOffSides();
					bonus[65][0][i] = avg.getAvTackles();
					bonus[66][0][i] = avg.getAvFouls();
					bonus[67][0][i] = avg.getAvOffsidesAgainst();
					bonus[68][0][i] = avg.getAvCleanSheets();
					bonus[69][0][i] = avg.getAvYellowCards();
					bonus[70][0][i] = avg.getAvRedCards();
					bonus[71][0][i] = avg.getAvSubstitutions();
				} catch (Exception e) {
					throw new CollectorException("Corresponding data could not be derived.");
				}
			}
			for (int j = 0; j < 36; j++) {
				bonus[j][1] = rankIncreasing(bonus[j][0]);
				bonus[j + 36][1] = rankDecreasing(bonus[j+36][0]);
				;
			}
			return bonus;
		}
		throw new CollectorException("Bonus features not created.");
	}
	
	/**
	 * Creates list of corresponding discrete bonus factors.
	 * 
	 * @param g
	 *            Game-object to consider.
	 * @param n
	 *            number of games to take into account.
	 * @param weights
	 *            corresponding weights array.
	 * @return list of corresponding polynomial regressions.
	 * @throws CollectorException
	 * @throws NotFoundException
	 */
	public static double[][][] createBonusAway(Game g, int n, double[] weights)
			throws CollectorException, NotFoundException {
		if (n == 0)
			throw new CollectorException("No games collected.");
		if (n != weights.length)
			throw new CollectorException("No games collected.");
		Competition pseudo = null;
		try {
			pseudo = g.getCompetition().pseudoCompetitionAway(g.getGameDay(), n);
		} catch (Exception e) {
			throw new CollectorException("Pseudo-Competition Object not created.");
		}
		if (pseudo != null) {
			int nTeams = pseudo.getTeams().size();
			double[][][] bonus = new double[72][2][nTeams];
			for (int i = 0; i < nTeams; i++) {
				Team t = pseudo.getTeams().get(i);
				ArrayList<Game> games;
				Average avg;
				try {
					games = Collector.gatherAwayGames(t, g.getGameDay(), n);
					avg = Collector.teamAverage(t, games, weights);
					bonus[0][0][i] = avg.getAvGoals();
					bonus[1][0][i] = avg.getAvPossession();
					bonus[2][0][i] = avg.getAvSuccessPasses();
					bonus[3][0][i] = avg.getAvTotalPasses();
					bonus[4][0][i] = avg.getAvPassSuccess();
					bonus[5][0][i] = avg.getAvAerialsWon();
					bonus[6][0][i] = avg.getAvShots();
					bonus[7][0][i] = avg.getAvShotsOnTarget();
					bonus[8][0][i] = avg.getAvDribbles();
					bonus[9][0][i] = avg.getAvFouled();
					bonus[10][0][i] = avg.getAvOffSides();
					bonus[11][0][i] = avg.getAvTackles();
					bonus[12][0][i] = avg.getAvFouls();
					bonus[13][0][i] = avg.getAvOffsidesAgainst();
					bonus[14][0][i] = avg.getAvCleanSheets();
					bonus[15][0][i] = avg.getAvYellowCards();
					bonus[16][0][i] = avg.getAvRedCards();
					bonus[17][0][i] = avg.getAvSubstitutions();
					bonus[36][0][i] = avg.getAvGoals();
					bonus[37][0][i] = avg.getAvPossession();
					bonus[38][0][i] = avg.getAvSuccessPasses();
					bonus[39][0][i] = avg.getAvTotalPasses();
					bonus[40][0][i] = avg.getAvPassSuccess();
					bonus[41][0][i] = avg.getAvAerialsWon();
					bonus[42][0][i] = avg.getAvShots();
					bonus[43][0][i] = avg.getAvShotsOnTarget();
					bonus[44][0][i] = avg.getAvDribbles();
					bonus[45][0][i] = avg.getAvFouled();
					bonus[46][0][i] = avg.getAvOffSides();
					bonus[47][0][i] = avg.getAvTackles();
					bonus[48][0][i] = avg.getAvFouls();
					bonus[49][0][i] = avg.getAvOffsidesAgainst();
					bonus[50][0][i] = avg.getAvCleanSheets();
					bonus[51][0][i] = avg.getAvYellowCards();
					bonus[52][0][i] = avg.getAvRedCards();
					bonus[53][0][i] = avg.getAvSubstitutions();
					avg = Collector.advAverage(t, games, weights);
					bonus[18][0][i] = avg.getAvGoals();
					bonus[19][0][i] = avg.getAvPossession();
					bonus[20][0][i] = avg.getAvSuccessPasses();
					bonus[21][0][i] = avg.getAvTotalPasses();
					bonus[22][0][i] = avg.getAvPassSuccess();
					bonus[23][0][i] = avg.getAvAerialsWon();
					bonus[24][0][i] = avg.getAvShots();
					bonus[25][0][i] = avg.getAvShotsOnTarget();
					bonus[26][0][i] = avg.getAvDribbles();
					bonus[27][0][i] = avg.getAvFouled();
					bonus[28][0][i] = avg.getAvOffSides();
					bonus[29][0][i] = avg.getAvTackles();
					bonus[30][0][i] = avg.getAvFouls();
					bonus[31][0][i] = avg.getAvOffsidesAgainst();
					bonus[32][0][i] = avg.getAvCleanSheets();
					bonus[33][0][i] = avg.getAvYellowCards();
					bonus[34][0][i] = avg.getAvRedCards();
					bonus[35][0][i] = avg.getAvSubstitutions();
					bonus[54][0][i] = avg.getAvGoals();
					bonus[55][0][i] = avg.getAvPossession();
					bonus[56][0][i] = avg.getAvSuccessPasses();
					bonus[57][0][i] = avg.getAvTotalPasses();
					bonus[58][0][i] = avg.getAvPassSuccess();
					bonus[59][0][i] = avg.getAvAerialsWon();
					bonus[60][0][i] = avg.getAvShots();
					bonus[61][0][i] = avg.getAvShotsOnTarget();
					bonus[62][0][i] = avg.getAvDribbles();
					bonus[63][0][i] = avg.getAvFouled();
					bonus[64][0][i] = avg.getAvOffSides();
					bonus[65][0][i] = avg.getAvTackles();
					bonus[66][0][i] = avg.getAvFouls();
					bonus[67][0][i] = avg.getAvOffsidesAgainst();
					bonus[68][0][i] = avg.getAvCleanSheets();
					bonus[69][0][i] = avg.getAvYellowCards();
					bonus[70][0][i] = avg.getAvRedCards();
					bonus[71][0][i] = avg.getAvSubstitutions();
				} catch (Exception e) {
					throw new CollectorException("Corresponding data could not be derived.");
				}
			}
			for (int j = 0; j < 36; j++) {
				bonus[j][1] = rankIncreasing(bonus[j][0]);
				bonus[j + 36][1] = rankDecreasing(bonus[j+36][0]);
				;
			}
			return bonus;
		}
		throw new CollectorException("Bonus features not created.");
	}

}