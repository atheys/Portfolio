package simulation.read;

public class TicketData {

	private boolean into_account;
	private String competition;
	private String season;
	private int gDays;
	private int teams;
	// Available tickets
	private boolean first_division;
	private int position;
	private int CL_HT;
	private int CL_PO;
	private int CL_1;
	private int CL_2;
	private int CL_3;
	private int EL_HT;
	private int EL_1;
	private int EL_2;
	private int EL_3;
	private int promotion;
	private int promotion_playoff;
	private int degradation_playoff;
	private int degradation;

	public TicketData(String competition, String season) {
		this.into_account = true;
		this.competition = competition;
		this.season = season;
		this.assignFeatures();
		this.assignTickets();
	}

	private void assignFeatures() {
		switch (this.competition) {
		case "BL":
			this.gDays = 34;
			this.teams = 18;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 1;
			this.degradation = 2;
			switch (this.season) {
			case "0910":
				this.position = 4;
				break;
			case "1011":
				this.position = 3;
				break;
			case "1112":
				this.position = 3;
				break;
			case "1213":
				this.position = 3;
				break;
			case "1314":
				this.position = 3;
				break;
			case "1415":
				this.position = 3;
				break;
			case "1516":
				this.position = 2;
				break;
			case "1617":
				this.position = 2;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "ED":
			this.gDays = 34;
			this.teams = 18;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 2;
			this.degradation = 1;
			switch (this.season) {
			case "1314":
				this.position = 8;
				break;
			case "1415":
				this.position = 9;
				break;
			case "1516":
				this.position = 10;
				break;
			case "1617":
				this.position = 13;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "LU":
			this.gDays = 38;
			this.teams = 20;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 0;
			this.degradation = 3;
			switch (this.season) {
			case "0910":
				this.position = 5;
				break;
			case "1011":
				this.position = 5;
				break;
			case "1112":
				this.position = 6;
				break;
			case "1213":
				this.position = 6;
				break;
			case "1314":
				this.position = 6;
				break;
			case "1415":
				this.position = 6;
				break;
			case "1516":
				this.position = 6;
				break;
			case "1617":
				this.position = 5;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "PD":
			this.gDays = 38;
			this.teams = 20;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 0;
			this.degradation = 3;
			switch (this.season) {
			case "0910":
				this.position = 2;
				break;
			case "1011":
				this.position = 2;
				break;
			case "1112":
				this.position = 2;
				break;
			case "1213":
				this.position = 1;
				break;
			case "1314":
				this.position = 1;
				break;
			case "1415":
				this.position = 1;
				break;
			case "1516":
				this.position = 1;
				break;
			case "1617":
				this.position = 1;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "PL":
			this.gDays = 38;
			this.teams = 20;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 0;
			this.degradation = 3;
			switch (this.season) {
			case "0910":
				this.position = 1;
				break;
			case "1011":
				this.position = 1;
				break;
			case "1112":
				this.position = 1;
				break;
			case "1213":
				this.position = 2;
				break;
			case "1314":
				this.position = 2;
				break;
			case "1415":
				this.position = 2;
				break;
			case "1516":
				this.position = 3;
				break;
			case "1617":
				this.position = 3;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "PP":
			this.gDays = 30;
			this.teams = 16;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 2;
			this.degradation = 2;
			switch (this.season) {
			case "1415":
				this.position = 7;
				break;
			case "1516":
				this.position = 7;
				break;
			case "1617":
				this.position = 6;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "SA":
			this.gDays = 38;
			this.teams = 20;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 0;
			this.degradation = 3;
			switch (this.season) {
			case "0910":
				this.position = 3;
				break;
			case "1011":
				this.position = 4;
				break;
			case "1112":
				this.position = 4;
				break;
			case "1213":
				this.position = 4;
				break;
			case "1314":
				this.position = 4;
				break;
			case "1415":
				this.position = 4;
				break;
			case "1516":
				this.position = 4;
				break;
			case "1617":
				this.position = 4;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "SL":
			this.gDays = 34;
			this.teams = 18;
			this.first_division = true;
			this.promotion = 0;
			this.promotion_playoff = 0;
			this.degradation_playoff = 0;
			this.degradation = 3;
			switch (this.season) {
			case "1415":
				this.position = 12;
				break;
			case "1516":
				this.position = 11;
				break;
			case "1617":
				this.position = 10;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		case "TC":
			this.gDays = 46;
			this.teams = 24;
			this.first_division = false;
			this.promotion = 2;
			this.promotion_playoff = 4;
			this.degradation_playoff = 0;
			this.degradation = 3;
			switch (this.season) {
			case "1314":
				this.position = 0;
				break;
			case "1415":
				this.position = 0;
				break;
			case "1516":
				this.position = 0;
				break;
			default:
				this.into_account = false;
				break;
			}
			break;
		default:
			this.into_account = false;
			break;
		}
	}

	private void assignTickets() {
		if (this.position >= 1 && this.position <= 3) {
			this.CL_HT = 3;
			this.CL_PO = 1;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 0;
			this.EL_HT = 2;
			this.EL_1 = 0;
			this.EL_2 = 0;
			this.EL_3 = 1;
			return;
		}
		if (this.position == 4) {
			this.CL_HT = 2;
			this.CL_PO = 1;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 0;
			this.EL_HT = 2;
			this.EL_1 = 0;
			this.EL_2 = 0;
			this.EL_3 = 1;
			return;
		}
		if (this.position == 5) {
			this.CL_HT = 2;
			this.CL_PO = 1;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 0;
			this.EL_HT = 1;
			this.EL_1 = 0;
			this.EL_2 = 0;
			this.EL_3 = 2;
			return;
		}
		if (this.position == 6) {
			this.CL_HT = 2;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 1;
			this.EL_HT = 1;
			this.EL_1 = 0;
			this.EL_2 = 0;
			this.EL_3 = 2;
			return;
		}
		if (this.position >= 7 && this.position <= 9) {
			this.CL_HT = 1;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 1;
			this.EL_HT = 1;
			this.EL_1 = 0;
			this.EL_2 = 0;
			this.EL_3 = 2;
			return;
		}
		if (this.position >= 10 && this.position <= 12) {
			this.CL_HT = 1;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 1;
			this.EL_HT = 1;
			this.EL_1 = 0;
			this.EL_2 = 1;
			this.EL_3 = 1;
			return;
		}
		if (this.position >= 13 && this.position <= 15) {
			this.CL_HT = 0;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 0;
			this.CL_3 = 2;
			this.EL_HT = 0;
			this.EL_1 = 0;
			this.EL_2 = 1;
			this.EL_3 = 2;
			return;
		}
		if (this.position == 16 || this.position == 17) {
			this.CL_HT = 0;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 1;
			this.CL_3 = 0;
			this.EL_HT = 0;
			this.EL_1 = 1;
			this.EL_2 = 1;
			this.EL_3 = 1;
			return;
		}
		if (this.position >= 18 && this.position <= 26) {
			this.CL_HT = 0;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 1;
			this.CL_3 = 0;
			this.EL_HT = 0;
			this.EL_1 = 2;
			this.EL_2 = 1;
			this.EL_3 = 0;
			return;
		}
		if (this.position >= 27 && this.position <= 46) {
			this.CL_HT = 0;
			this.CL_PO = 0;
			this.CL_1 = 0;
			this.CL_2 = 1;
			this.CL_3 = 0;
			this.EL_HT = 0;
			this.EL_1 = 3;
			this.EL_2 = 0;
			this.EL_3 = 0;
			return;
		}
		if (this.position >= 47 && this.position <= 51) {
			this.CL_HT = 0;
			this.CL_PO = 0;
			this.CL_1 = 1;
			this.CL_2 = 0;
			this.CL_3 = 0;
			this.EL_HT = 0;
			this.EL_1 = 3;
			this.EL_2 = 0;
			this.EL_3 = 0;
			return;
		}
		if (this.position == 52 || this.position == 53) {
			this.CL_HT = 0;
			this.CL_PO = 0;
			this.CL_1 = 1;
			this.CL_2 = 0;
			this.CL_3 = 0;
			this.EL_HT = 0;
			this.EL_1 = 2;
			this.EL_2 = 0;
			this.EL_3 = 0;
			return;
		}
		this.CL_HT = 0;
		this.CL_PO = 0;
		this.CL_1 = 0;
		this.CL_2 = 0;
		this.CL_3 = 0;
		this.EL_HT = 0;
		this.EL_1 = 0;
		this.EL_2 = 0;
		this.EL_3 = 0;
	}

	public boolean isInto_account() {
		return into_account;
	}

	public int getgDays() {
		return gDays;
	}

	public int getTeams() {
		return teams;
	}

	public boolean isFirst_division() {
		return first_division;
	}

	public int getPosition() {
		return position;
	}

	public int getCL_HT() {
		return CL_HT;
	}

	public int getCL_PO() {
		return CL_PO;
	}

	public int getCL_1() {
		return CL_1;
	}

	public int getCL_2() {
		return CL_2;
	}

	public int getCL_3() {
		return CL_3;
	}

	public int getEL_HT() {
		return EL_HT;
	}

	public int getEL_1() {
		return EL_1;
	}

	public int getEL_2() {
		return EL_2;
	}

	public int getEL_3() {
		return EL_3;
	}

	public int getPromotion() {
		return promotion;
	}

	public int getPromotion_playoff() {
		return promotion_playoff;
	}

	public int getDegradation_playoff() {
		return degradation_playoff;
	}

	public int getDegradation() {
		return degradation;
	}

	public int promotionTickets() {
		return promotion + promotion_playoff;
	}

	public int degradationTickets() {
		return degradation + degradation_playoff;
	}

	public int CLTickets() {
		if (!this.first_division)
			return 0;
		int total = CL_HT + CL_PO + CL_1 + CL_2 + CL_3;
		return total;
	}

	public int ELTickets() {
		if (!this.first_division)
			return 0;
		int total = EL_HT + EL_1 + EL_2 + EL_3 - 1;
		if (this.season.equals("LU") || this.season.equals("PL"))
			total = -1;
		return total;
	}

	public int europeanTickets() {
		if (!this.first_division)
			return 0;
		int total = CL_HT + CL_PO + CL_1 + CL_2 + CL_3 + EL_HT + EL_1 + EL_2 + EL_3 - 1;
		if (this.season.equals("LU") || this.season.equals("PL"))
			total = -1;
		return total;
	}
	
}