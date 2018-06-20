package input.handlers.datamining.oddsportal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.core.books.Bookie;
import data.core.books.Odds;
import data.core.paths.Path;
import data.core.structure.Competition;
import data.core.structure.Game;
import exceptions.CollectorException;
import input.finders.labels.Conventions;
import simulation.read.Reader;

public class OPMiningManager extends Conventions {

	/**
	 * Reads 
	 * 
	 * @param label
	 *            competition label.
	 * @param season
	 *            seasonal label.
	 * @param gDays
	 *            number of game days.
	 * @param teams
	 *            number of teams.
	 * @throws IOException
	 * @throws CollectorException
	 */
	public static ArrayList<Odds> read(String label, String season, int gDays, int teams) throws IOException, CollectorException {
		ArrayList<Odds> result = new ArrayList<Odds>();
		Competition c = Reader.read(label, season, gDays, teams);
		ArrayList<Bookie> bookies = Reader.createBookies();
		String toRead = Path.odds_rd + label + "/" + season + "-2.txt";
		ArrayList<String[]> lines = new ArrayList<String[]>();
		BufferedReader br = new BufferedReader(new FileReader(toRead));
		String line;
		while ((line = br.readLine()) != null) {
			String[] data = line.trim().split(",");
			lines.add(data);
		}
		br.close();
		String[][] data = new String[lines.size()][];
		for (int i = 0; i < data.length; i++) {
			data[i] = lines.get(i);
		}
		switch (label) {
		case "BB":
			data = BBModerator(data);
			break;
		case "BL":
			data = BLModerator(data);
			break;
		case "ED":
			data = EDModerator(data);
			break;
		case "LU":
			data = LUModerator(data);
			break;
		case "PD":
			data = PDModerator(data, season);
			break;
		case "PL":
			data = PLModerator(data);
			break;
		case "PP":
			data = PPModerator(data);
			break;
		case "SA":
			data = SAModerator(data);
			break;
		case "SL":
			data = SLModerator(data);
			break;
		case "TC":
			data = TCModerator(data);
			break;
		default:
			break;
		}
		int wrong = 0;
		for (String[] dat : data) {
			try {
				Bookie b = Reader.findName(dat[2], bookies);
				Game g = Reader.find(dat[0], dat[1], c);
				double home = Double.parseDouble(dat[3]);
				double draw = Double.parseDouble(dat[4]);
				double away = Double.parseDouble(dat[5]);
				Odds o = new Odds(g.getId(), b, g, home, draw, away);
				result.add(o);
			} catch (Exception e) {
				System.out.println(dat[0]+"-"+dat[1]);
				wrong++;
			}
		}
		System.out.println(wrong+" misfetches.");
		return result;
	}

	/*
	 * Moderates name differences between different mining managers (Brazilian
	 * Serie A).
	 */
	private static String[][] BBModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Vitoria":
					break;
				case "Internacional":
					break;
				case "Vasco":
					data[i][j] = "Vasco da Gama";
					break;
				case "Portuguesa":
					break;
				case "Corinthians":
					break;
				case "Botafogo RJ":
					break;
				case "Santos":
					data[i][j] = "Santos FC";
					break;
				case "Flamengo RJ":
					data[i][j] = "Flamengo";
					break;
				case "Gremio":
					break;
				case "Nautico":
					break;
				case "Ponte Preta":
					break;
				case "Sao Paulo":
					break;
				case "Criciuma":
					break;
				case "Bahia":
					break;
				case "Cruzeiro":
					break;
				case "Goias":
					break;
				case "Coritiba":
					break;
				case "Atletico-MG":
					data[i][j] = "Atletico MG";
					break;
				case "Fluminense":
					break;
				case "Atletico-PR":
					data[i][j] = "Atletico PR";
					break;
				case "Figueirense":
					break;
				case "Chapecoense-SC":
					data[i][j] = "Chapecoense AF";
					break;
				case "Sport Recife":
					data[i][j] = "Sport";
					break;
				case "Palmeiras":
					break;
				case "Joinville":
					break;
				case "Avai":
					data[i][j] = "Avai FC";
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (German
	 * Bundesliga).
	 */
	private static String[][] BLModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Wolfsburg":
					break;
				case "VfB Stuttgart":
					break;
				case "Dortmund":
					data[i][j] = "Borussia Dortmund";
					break;
				case "FC Koln":
					data[i][j] = "FC Cologne";
					break;
				case "1. FC Koln":
					data[i][j] = "FC Cologne";
					break;
				case "SV Werder Bremen":
					data[i][j] = "Werder Bremen";
					break;
				case "Eintracht Frankfurt":
					break;
				case "Hertha Berlin":
					break;
				case "Hannover":
					data[i][j] = "Hannover 96";
					break;
				case "Nuernberg":
					break;
				case "Nurnberg":
					data[i][j] = "Nuernberg";
					break;
				case "Schalke":
					data[i][j] = "Schalke 04";
					break;
				case "Mainz":
					data[i][j] = "Mainz 05";
					break;
				case "1. FSV Mainz 05":
					data[i][j] = "Mainz 05";
					break;
				case "Bayer Leverkusen":
					break;
				case "Hoffenheim":
					break;
				case "Bayern Munich":
					break;
				case "Bochum":
					break;
				case "B. Monchengladbach":
					data[i][j] = "Borussia M.Gladbach";
					break;
				case "Freiburg":
					break;
				case "SC Freiburg":
					data[i][j] = "Freiburg";
					break;
				case "Hamburger SV":
					break;
				case "Kaiserslautern":
					break;
				case "St. Pauli":
					break;
				case "FC Augsburg":
					data[i][j] = "Augsburg";
					break;
				case "Augsburg":
					break;
				case "Greuther Furth":
					data[i][j] = "Greuther Fuerth";
					break;
				case "Dusseldorf":
					data[i][j] = "Fortuna Duesseldorf";
					break;
				case "Braunschweig":
					data[i][j] = "Eintracht Braunschweig";
					break;
				case "Paderborn":
					break;
				case "Ingolstadt":
					break;
				case "Darmstadt":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (Dutch
	 * Eredivisie).
	 */
	private static String[][] EDModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Ajax":
					break;
				case "Roda":
					break;
				case "Den Haag":
					data[i][j] = "ADO Den Haag";
					break;
				case "PSV":
					data[i][j] = "PSV Eindhoven";
					break;
				case "Twente":
					break;
				case "Waalwijk":
					data[i][j] = "RKC Waalwijk";
					break;
				case "Nijmegen":
					data[i][j] = "NEC Nijmegen";
					break;
				case "Groningen":
					data[i][j] = "FC Groningen";
					break;
				case "Heerenveen":
					data[i][j] = "SC Heerenveen";
					break;
				case "AZ Alkmaar":
					break;
				case "Cambuur":
					break;
				case "Breda":
					data[i][j] = "NAC Breda";
					break;
				case "Utrecht":
					data[i][j] = "FC Utrecht";
					break;
				case "G.A. Eagles":
					data[i][j] = "Go Ahead Eagles";
					break;
				case "Vitesse":
					break;
				case "Heracles":
					break;
				case "Zwolle":
					data[i][j] = "PEC Zwolle";
					break;
				case "Feyenoord":
					break;
				case "Dordrecht":
					data[i][j] = "FC Dordrecht";
					break;
				case "Excelsior":
					break;
				case "Willem II":
					break;
				case "Graafschap":
					data[i][j] = "De Graafschap";
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (French
	 * Ligue 1).
	 */
	private static String[][] LUModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Auxerre":
					break;
				case "Sochaux":
					break;
				case "Grenoble":
					break;
				case "Marseille":
					break;
				case "Le Mans":
					break;
				case "Lyon":
					break;
				case "Monaco":
					break;
				case "Toulouse":
					break;
				case "Montpellier":
					break;
				case "Paris SG":
					data[i][j] = "Paris Saint Germain";
					break;
				case "Rennes":
					break;
				case "Boulogne":
					break;
				case "St Etienne":
					data[i][j] = "Saint-Etienne";
					break;
				case "Nice":
					break;
				case "Valenciennes":
					break;
				case "Nancy":
					break;
				case "Lille":
					break;
				case "Lorient":
					break;
				case "Bordeaux":
					break;
				case "Lens":
					break;
				case "Caen":
					break;
				case "Arles-Avignon":
					break;
				case "Brest":
					break;
				case "AC Ajaccio":
					break;
				case "Evian TG":
					data[i][j] = "Evian Thonon Gaillard";
					break;
				case "Dijon":
					break;
				case "Bastia":
					data[i][j] = "SC Bastia";
					break;
				case "Troyes":
					break;
				case "Reims":
					break;
				case "Nantes":
					break;
				case "Guingamp":
					break;
				case "Metz":
					break;
				case "Angers":
					break;
				case "GFC Ajaccio":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (Spanish
	 * Primera Division).
	 */
	private static String[][] PDModerator(String[][] data, String season) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (season.equals("1516") && data[i][j].equals("Ath Bilbao")) {
					data[i][j] = "Athletic Club";
				}
				if (!season.equals("1516") && data[i][j].equals("Ath Bilbao")) {
					data[i][j] = "Athletic Bilbao";
				}
				switch (data[i][j]) {
				case "Real Madrid":
					break;
				case "Dep. La Coruna":
					data[i][j] = "Deportivo La Coruna";
					break;
				case "Zaragoza":
					break;
				case "Tenerife":
					break;
				case "Santander":
					data[i][j] = "Racing Santander";
					break;
				case "Getafe":
					break;
				case "Malaga":
					break;
				case "Atl. Madrid":
					data[i][j] = "Atletico Madrid";
					break;
				case "Espanyol":
					break;
				case "Mallorca":
					break;
				case "Xerez CD":
					data[i][j] = "Xerez";
					break;
				case "Osasuna":
					break;
				case "Villarreal":
					break;
				case "Valencia":
					break;
				case "Sevilla":
					break;
				case "Almeria":
					break;
				case "Valladolid":
					break;
				case "Barcelona":
					break;
				case "Gijon":
					data[i][j] = "Sporting Gijon";
					break;
				case "Hercules":
					break;
				case "Levante":
					break;
				case "Real Sociedad":
					break;
				case "Granada CF":
					data[i][j] = "Granada";
					break;
				case "Betis":
					data[i][j] = "Real Betis";
					break;
				case "Rayo Vallecano":
					break;
				case "Celta Vigo":
					break;
				case "Elche":
					break;
				case "Eibar":
					break;
				case "Cordoba":
					break;
				case "Las Palmas":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (English
	 * Premier League).
	 */
	private static String[][] PLModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Chelsea":
					break;
				case "Hull City":
					data[i][j] = "Hull";
					break;
				case "Stoke City":
					data[i][j] = "Stoke";
					break;
				case "Burnley":
					break;
				case "Portsmouth":
					break;
				case "Fulham":
					break;
				case "Bolton":
					break;
				case "Sunderland":
					break;
				case "Blackburn":
					break;
				case "Manchester City":
					break;
				case "Wolves":
					data[i][j] = "Wolverhampton Wanderers";
					break;
				case "West Ham":
					break;
				case "Aston Villa":
					break;
				case "Wigan":
					break;
				case "Everton":
					break;
				case "Arsenal":
					break;
				case "Manchester United":
					break;
				case "Birmingham":
					break;
				case "Tottenham":
					break;
				case "Liverpool":
					break;
				case "Blackpool":
					break;
				case "West Brom":
					data[i][j] = "West Bromwich Albion";
					break;
				case "Newcastle Utd":
					data[i][j] = "Newcastle United";
					break;
				case "QPR":
					data[i][j] = "Queens Park Rangers";
					break;
				case "Norwich":
					break;
				case "Swansea":
					break;
				case "Reading":
					break;
				case "Southampton":
					break;
				case "Cardiff":
					break;
				case "Crystal Palace":
					break;
				case "Leicester":
					break;
				case "Bournemouth":
					break;
				case "Watford":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (Russian
	 * Premier League).
	 */
	private static String[][] PPModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Rubin Kazan":
					break;
				case "Spartak Moscow":
					break;
				case "Ural":
					break;
				case "M. Saransk":
					data[i][j] = "Mordovya";
					break;
				case "Arsenal Tula":
					break;
				case "Zenit Petersburg":
					data[i][j] = "Zenit St. Petersburg";
					break;
				case "CSKA Moscow":
					break;
				case "T. Moscow":
					data[i][j] = "Torpedo Moscow";
					break;
				case "Dynamo Moscow":
					data[i][j] = "Dinamo Moscow";
					break;
				case "FK Rostov":
					data[i][j] = "FC Rostov";
					break;
				case "Kuban":
					data[i][j] = "Kuban Krasnodar";
					break;
				case "Ufa":
					data[i][j] = "FC Ufa";
					break;
				case "Lokomotiv Moscow":
					break;
				case "Krasnodar":
					data[i][j] = "FC Krasnodar";
					break;
				case "Terek Grozni":
					data[i][j] = "Terek Grozny";
					break;
				case "Amkar":
					break;
				case "FK Anzi Makhackala":
					data[i][j] = "Anzhi Makhachkala";
					break;
				case "FK Krylya Sovetov Samara":
					data[i][j] = "Krylya Sovetov Samara";
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (Italian
	 * Serie A).
	 */
	private static String[][] SAModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Bologna":
					break;
				case "Fiorentina":
					break;
				case "Siena":
					data[i][j] = "Robur Siena";
					break;
				case "AC Milan":
					break;
				case "Inter":
					break;
				case "Bari":
					break;
				case "Juventus":
					break;
				case "Chievo":
					break;
				case "Lazio":
					break;
				case "Atalanta":
					break;
				case "Livorno":
					break;
				case "Cagliari":
					break;
				case "Palermo":
					break;
				case "Napoli":
					break;
				case "Udinese":
					break;
				case "Parma":
					break;
				case "Catania":
					break;
				case "Sampdoria":
					break;
				case "Genoa":
					break;
				case "AS Roma":
					data[i][j] = "Roma";
					break;
				case "Cesena":
					break;
				case "Brescia":
					break;
				case "Lecce":
					break;
				case "Novara":
					break;
				case "Pescara":
					break;
				case "Torino":
					break;
				case "Verona":
					break;
				case "Sassuolo":
					break;
				case "Empoli":
					break;
				case "Carpi":
					break;
				case "Frosinone":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (Turkish
	 * Super Lig).
	 */
	private static String[][] SLModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Balikesirspor":
					break;
				case "Akhisar Genclik Spor":
					data[i][j] = "Akhisar Belediyespor";
					break;
				case "Rizespor":
					break;
				case "Genclerbirligi":
					break;
				case "Eskisehirspor":
					break;
				case "Konyaspor":
					break;
				case "Bursaspor":
					break;
				case "Galatasaray":
					break;
				case "Basaksehir":
					data[i][j] = "Istanbul Basaksehir";
					break;
				case "Kasimpasa":
					break;
				case "Mersin":
					break;
				case "Besiktas":
					break;
				case "Kayseri Erciyesspor":
					break;
				case "Trabzonspor":
					break;
				case "Sivasspor":
					break;
				case "Gaziantepspor":
					break;
				case "Fenerbahce":
					break;
				case "Kardemir Karabuk":
					data[i][j] = "Karabukspor";
					break;
				case "Antalyaspor":
					break;
				case "Osmanlispor":
					data[i][j] = "Osmanlispor FK";
					break;
				case "Kayserispor":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

	/*
	 * Moderates name differences between different mining managers (English
	 * Championship).
	 */
	private static String[][] TCModerator(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case "Burnley":
					break;
				case "Bolton":
					break;
				case "Barnsley":
					break;
				case "Wigan":
					break;
				case "Birmingham":
					break;
				case "Watford":
					break;
				case "Bournemouth":
					break;
				case "Charlton":
					break;
				case "Doncaster":
					break;
				case "Blackpool":
					break;
				case "Leeds":
					break;
				case "Brighton":
					break;
				case "Middlesbrough":
					break;
				case "Leicester":
					break;
				case "Millwall":
					break;
				case "Yeovil":
					break;
				case "Nottingham":
					data[i][j] = "Nottingham Forest";
					break;
				case "Huddersfield":
					break;
				case "QPR":
					data[i][j] = "Queens Park Rangers";
					break;
				case "Sheffield Wed":
					data[i][j] = "Sheffield Wednesday";
					break;
				case "Reading":
					break;
				case "Ipswich":
					break;
				case "Derby":
					break;
				case "Blackburn":
					break;
				case "Cardiff":
					break;
				case "Rotherham":
					break;
				case "Brentford":
					break;
				case "Fulham":
					break;
				case "Wolves":
					data[i][j] = "Wolverhampton Wanderers";
					break;
				case "Norwich":
					break;
				case "Milton Keynes Dons":
					break;
				case "Bristol City":
					break;
				case "Hull City":
					data[i][j] = "Hull";
					break;
				case "Preston":
					break;
				default:
					break;
				}
			}
		}
		return data;
	}

}
