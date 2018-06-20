package data.core.paths;

/**
 * Path Definitions Class for convenient purposes.
 * 
 * @author Andreas Theys.
 * @version 6.0
 */
public class Path {

	/**
	 * RankStatData file hierarchy.
	 */
	public static final String source_path = "/Users/andreastheys/Documents/The_RankStat_Initiative/RankStatData/";
	public static final String source_path_games = source_path + "1_games/";
	public static final String source_path_odds = source_path + "2_odds/";
	public static final String source_path_results = source_path + "3_results/";

	/**
	 * Game data file hierarchy.
	 */
	public static final String games_dm = source_path_games + "00_DataMiner/";
	public static final String games_rs = source_path_games + "0_raw_stats/";
	public static final String games_rd = source_path_games + "1_raw_data/";
	public static final String games_dat = source_path_games + "2_data/";
	
	/**
	 * Odds data file hierarchy.
	 */
	public static final String odds_b = source_path_odds + "0_bookies/";
	public static final String odds_dm = odds_b + "00_DataMiner/";
	public static final String odds_rd = source_path_odds + "1_raw_data/";
	public static final String odds_dat = source_path_odds + "2_data/";
	
	/**
	 * (Optimized) Portfolio data file hierarchy.
	 */
	public static final String results_mat = source_path_results+"0_matches/";
	public static final String results_mod = source_path_results+"1_models/";
	public static final String results_base = source_path_results+"2_simulations/1_bases/";
	public static final String results_sims = source_path_results+"2_simulations/2_computations/";
	public static final String results_merg = source_path_results+"3_mergers/";
	public static final String results_opt = source_path_results+"4_optimization/";

}