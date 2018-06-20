package simulation.read;

import java.util.ArrayList;

import data.core.structure.Competition;
import data.core.structure.Game;
import data.core.structure.Rank;
import data.core.structure.Team;
import exceptions.CollectorException;

/**
 * Psychological Factor Analyzer.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class PsychAnalyzer {

	private static void setImportance(Team t, Game g, double imp) {
		if (g.getHome().equals(t)) {
			g.getHomeStatsPsych().setImportance(imp);
			return;
		}
		if (g.getAway().equals(t)) {
			g.getAwayStatsPsych().setImportance(imp);
			return;
		}
	}

	private static boolean reachable(Rank r1, Rank r2, int gDays) {
		return ((double) (r1.getPoints() - r2.getPoints()) / 3. <= (gDays - r2.getGamesPlayed()));
	}

	private static void detImportance(Team t, Game g, Competition pseudo, TicketData td) {
		if (td.isInto_account()) {
			ArrayList<Rank> ranking = pseudo.getRanking();
			int rank = pseudo.getRank(t);
			Rank trank = ranking.get(rank - 1);
			Rank temp = ranking.get(1);
			// CHAMPION
			boolean champion = trank.getRank() == 1 && !reachable(trank, temp, td.getgDays());
			if (champion) {
				setImportance(t, g, 0.);
				return;
			}
			// TITLE
			temp = ranking.get(0);
			boolean title = reachable(temp, trank, td.getgDays());
			if (title) {
				return;
			}
			// EUROPEAN TICKETS or PROMOTION
			if (td.isFirst_division()) {
				int eur = td.europeanTickets();
				temp = ranking.get(eur - 1);
				boolean european = trank.getRank() > eur && reachable(temp, trank, td.getgDays());
				if (european) {
					return;
				}
				temp = ranking.get(eur);
				boolean ticket = trank.getRank() <= eur && !reachable(trank, temp, td.getgDays());
				if (ticket) {
					int CL = td.CLTickets();
					temp = ranking.get(CL - 1);
					boolean CLeague = trank.getRank() > CL && reachable(temp, trank, td.getgDays());
					if (CLeague) {
						setImportance(t, g, 0.75);
						return;
					}
					temp = ranking.get(CL);
					boolean CLticket = trank.getRank() <= CL && !reachable(trank, temp, td.getgDays());
					if (CLticket) {
						setImportance(t, g, 0.);
						return;
					}
				}
			} else {
				int prom = td.promotionTickets();
				temp = ranking.get(prom - 1);
				boolean promotion = trank.getRank() > prom && reachable(temp, trank, td.getgDays());
				if (promotion) {
					return;
				}
				temp = ranking.get(prom);
				boolean promoted = trank.getRank() <= prom && !reachable(trank, temp, td.getgDays());
				if (promoted) {
					int direct = td.getPromotion();
					temp = ranking.get(direct - 1);
					boolean direct_prom = trank.getRank() > direct && reachable(temp, trank, td.getgDays());
					if (direct_prom) {
						setImportance(t, g, 0.75);
						return;
					}
					temp = ranking.get(direct);
					boolean done = trank.getRank() <= direct && !reachable(trank, temp, td.getgDays());
					if (done) {
						setImportance(t, g, 0.);
						return;
					}
				}
			}
			int degrade = td.getTeams() - td.degradationTickets();
			temp = ranking.get(degrade - 1);
			boolean safeable = trank.getRank() > degrade && reachable(temp, trank, td.getgDays());
			if (safeable) {
				return;
			}
			boolean unsafeable = trank.getRank() > degrade && !reachable(temp, trank, td.getgDays());
			temp = ranking.get(degrade);
			boolean safe = trank.getRank() <= degrade && !reachable(trank, temp, td.getgDays());
			if (safe) {
				setImportance(t, g, 0.);
				return;
			}
			if(unsafeable && td.getDegradation_playoff()>0){
				int direct_degrade = td.getTeams() - td.getDegradation();
				temp = ranking.get(direct_degrade - 1);
				boolean partial = trank.getRank() > direct_degrade && reachable(temp, trank, td.getgDays());
				if(partial){
					return;
				}
				boolean doomed = trank.getRank() > direct_degrade && !reachable(temp, trank, td.getgDays());
				if(doomed){
					setImportance(t, g, 0.);
					return;
				}
				temp = ranking.get(direct_degrade);
				boolean playoff = trank.getRank() <= direct_degrade && !reachable(trank, temp, td.getgDays());
				if(playoff){
					setImportance(t, g, 0.75);
					return;
				}
			}
		}
	}

	public static void ANALYZE(Competition c) {
		TicketData td = new TicketData(c.getId().split("-")[0], c.getId().split("-")[1]);
		for (int i = c.getGames().size() / 2; i < c.getGames().size(); i++) {
			try {
				Competition pseudo = c.pseudoCompetition(c.getGames().get(i).getGameDay());
				detImportance(c.getGames().get(i).getHome(), c.getGames().get(i), pseudo, td);
				detImportance(c.getGames().get(i).getAway(), c.getGames().get(i), pseudo, td);
			} catch (CollectorException e) {
			}
		}
	}

}
