package logistics.base;

import java.util.ArrayList;
import data.core.books.Bookie;

/**
 * Regulator Class for Bookie Objects.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Regulator {

	/**
	 * Class attributes.
	 */
	// General Information
	private Bookie b;
	private ArrayList<Payment> options;
	private AccountOptions account;
	private boolean online;
	private boolean physical;
	private boolean live;
	private ArrayList<Currency> currencies;
	// Rules
	private double startBonus;
	private double minBet;
	private double maxBet;
	private double minTransfer;
	private double maxTransfer;
	private double transferTime; // hours
	private double minWithDrawal;
	private double maxWithDrawal;
	private double withdrawalTime; // hours
	// Tax Rules
	private double payout;
	private double taxPercentage;
	private double maxBoundary;
	private double maxTaxPercentage;

	/**
	 * Copy constructor.
	 * 
	 * @param r
	 *            Regulator-Object to copy.
	 */
	public Regulator(Regulator r) {
		this.b = r.b;
		this.options = r.options;
		this.account = r.account;
		this.online = r.online;
		this.physical = r.physical;
		this.live = r.live;
		this.startBonus = r.startBonus;
		this.minBet = r.minBet;
		this.maxBet = r.maxBet;
		this.minTransfer = r.minTransfer;
		this.maxTransfer = r.maxTransfer;
		this.transferTime = r.transferTime;
		this.minWithDrawal = r.minWithDrawal;
		this.maxWithDrawal = r.maxWithDrawal;
		this.withdrawalTime = r.withdrawalTime;
		this.payout = r.payout;
		this.taxPercentage = r.taxPercentage;
		this.maxBoundary = r.maxBoundary;
		this.maxTaxPercentage = r.maxTaxPercentage;
	}

	/**
	 * General constructor.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param options
	 *            payment options.
	 * @param account
	 *            account options.
	 * @param online
	 *            online betting indicator.
	 * @param physical
	 *            physical betting indicator.
	 * @param live
	 *            live betting indicator.
	 * @param startBonus
	 *            starting bonus amount.
	 * @param minBet
	 *            minimum betting amount.
	 * @param maxBet
	 *            maximum betting amount.
	 * @param minTransfer
	 *            minimum transfer amount.
	 * @param maxTransfer
	 *            maximum transfer amount.
	 * @param transferTime
	 *            transfer processing time.
	 * @param minWithDrawal
	 *            minimum withdrawal amount.
	 * @param maxWithDrawal
	 *            maximum withdrawal amount.
	 * @param withdrawalTime
	 *            withdrawal processing time.
	 * @param payout
	 *            pay-out rate (0<POR<1)
	 * @param taxPercentage
	 *            gambling taxation percentage.
	 * @param maxBoundary
	 *            maximum profit boundary.
	 * @param maxTaxPercentage
	 *            maximum taxation percentage.
	 */
	public Regulator(Bookie b, ArrayList<Payment> options, AccountOptions account, boolean online, boolean physical,
			boolean live, double startBonus, double minBet, double maxBet, double minTransfer, double maxTransfer,
			double transferTime, double minWithDrawal, double maxWithDrawal, double withdrawalTime, double payout,
			double taxPercentage, double maxBoundary, double maxTaxPercentage) {
		this.b = b;
		this.options = options;
		this.account = account;
		this.online = online;
		this.physical = physical;
		this.live = live;
		this.startBonus = startBonus;
		this.minBet = minBet;
		this.maxBet = maxBet;
		this.minTransfer = minTransfer;
		this.maxTransfer = maxTransfer;
		this.transferTime = transferTime;
		this.minWithDrawal = minWithDrawal;
		this.maxWithDrawal = maxWithDrawal;
		this.withdrawalTime = withdrawalTime;
		this.payout = payout;
		this.taxPercentage = taxPercentage;
		this.maxBoundary = maxBoundary;
		this.maxTaxPercentage = maxTaxPercentage;
		this.autoGenerateCurrecies();
	}

	/**
	 * Bookie getter.
	 * 
	 * @return relevant Bookie-Object.
	 */
	public Bookie getB() {
		return b;
	}

	/**
	 * Options getter.
	 * 
	 * @return list of payment options.
	 */
	public ArrayList<Payment> getOptions() {
		return options;
	}

	/**
	 * Options setter.
	 * 
	 * @param options
	 *            new list of payment options.
	 */
	public void setOptions(ArrayList<Payment> options) {
		this.options = options;
	}

	/**
	 * Account options getter.
	 * 
	 * @return relevant AccountOptions-Object.
	 */
	public AccountOptions getAccount() {
		return account;
	}

	/**
	 * Account options setter.
	 * 
	 * @param account
	 *            new relevant AccountOptions-Object.
	 */
	public void setAccount(AccountOptions account) {
		this.account = account;
	}

	/**
	 * Online availability indicator.
	 * 
	 * @return online availability.
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * Online availability setter.
	 * 
	 * @param online
	 *            new online availability.
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}

	/**
	 * Physical availability indicator.
	 * 
	 * @return physical availability.
	 */
	public boolean isPhysical() {
		return physical;
	}

	/**
	 * Physical availability setter.
	 * 
	 * @param physical
	 *            new physical availability.
	 */
	public void setPhysical(boolean physical) {
		this.physical = physical;
	}

	/**
	 * Live availability indicator.
	 * 
	 * @return live availability.
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * Live availability setter.
	 * 
	 * @param live
	 *            new live availability.
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * Start bonus getter.
	 * 
	 * @return start bonus.
	 */
	public double getStartBonus() {
		return startBonus;
	}

	/**
	 * Start bonus setter.
	 * 
	 * @param startBonus
	 *            new start bonus.
	 */
	public void setStartBonus(double startBonus) {
		this.startBonus = startBonus;
	}

	/**
	 * Minimal betting getter.
	 * 
	 * @return minimal betting amount.
	 */
	public double getMinBet() {
		return minBet;
	}

	/**
	 * Minimal betting setter.
	 * 
	 * @param minBet
	 *            new minimal betting amount.
	 */
	public void setMinBet(double minBet) {
		this.minBet = minBet;
	}

	/**
	 * Maximal betting getter.
	 * 
	 * @return maximal betting amount.
	 */
	public double getMaxBet() {
		return maxBet;
	}

	/**
	 * Maximal betting setter.
	 * 
	 * @param maxBet
	 *            new maximal betting amount.
	 */
	public void setMaxBet(double maxBet) {
		this.maxBet = maxBet;
	}

	/**
	 * Minimal transfer getter.
	 * 
	 * @return minimal transfer amount.
	 */
	public double getMinTransfer() {
		return minTransfer;
	}

	/**
	 * Minimal transfer setter.
	 * 
	 * @param minTransfer
	 *            new minimal transfer amount.
	 */
	public void setMinTransfer(double minTransfer) {
		this.minTransfer = minTransfer;
	}

	/**
	 * Maximal transfer getter.
	 * 
	 * @return maximal transfer amount.
	 */
	public double getMaxTransfer() {
		return maxTransfer;
	}

	/**
	 * Maximal transfer setter.
	 * 
	 * @param maxTransfer
	 *            new maximal transfer amount.
	 */
	public void setMaxTransfer(double maxTransfer) {
		this.maxTransfer = maxTransfer;
	}

	/**
	 * Transfer time getter.
	 * 
	 * @return transfer time (in hours).
	 */
	public double getTransferTime() {
		return transferTime;
	}

	/**
	 * Transfer time setter.
	 * 
	 * @param transferTime
	 *            new transfer time.
	 */
	public void setTransferTime(double transferTime) {
		this.transferTime = transferTime;
	}

	/**
	 * Minimum withdrawal getter.
	 * 
	 * @return minimal withdrawal amount.
	 */
	public double getMinWithDrawal() {
		return minWithDrawal;
	}

	/**
	 * Minimum withdrawal setter.
	 * 
	 * @param minWithDrawal
	 *            new minimal withdrawal amount.
	 */
	public void setMinWithDrawal(double minWithDrawal) {
		this.minWithDrawal = minWithDrawal;
	}

	/**
	 * Maximum withdrawal getter.
	 * 
	 * @return maximal withdrawal amount.
	 */
	public double getMaxWithDrawal() {
		return maxWithDrawal;
	}

	/**
	 * Maximum withdrawal setter.
	 * 
	 * @param maxWithDrawal
	 *            new maximal withdrawal amount.
	 */
	public void setMaxWithDrawal(double maxWithDrawal) {
		this.maxWithDrawal = maxWithDrawal;
	}

	/**
	 * Withdrawal time getter.
	 * 
	 * @return withdrawal time (in hours).
	 */
	public double getWithdrawalTime() {
		return withdrawalTime;
	}

	/**
	 * Withdrawal time setter.
	 * 
	 * @param withdrawalTime
	 *            new withdrawal time.
	 */
	public void setWithdrawalTime(double withdrawalTime) {
		this.withdrawalTime = withdrawalTime;
	}

	/**
	 * Payout rate getter.
	 * 
	 * @return payout rate.
	 */
	public double getPayout() {
		return payout;
	}

	/**
	 * Payout rate setter.
	 * 
	 * @param payout
	 *            new payout rate.
	 */
	public void setPayout(double payout) {
		this.payout = payout;
	}

	/**
	 * Tax percentage getter.
	 * 
	 * @return tax percentage.
	 */
	public double getTaxPercentage() {
		return taxPercentage;
	}

	/**
	 * Tax percentage setter
	 * 
	 * @param taxPercentage
	 *            new tax percentage.
	 */
	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	/**
	 * Maximum profit boundary getter.
	 * 
	 * @return maximum profit boundary value.
	 */
	public double getMaxBoundary() {
		return maxBoundary;
	}

	/**
	 * Maximum profit boundary setter.
	 * 
	 * @param maxBoundary
	 *            new maximum profit boundary value.
	 */
	public void setMaxBoundary(double maxBoundary) {
		this.maxBoundary = maxBoundary;
	}

	/**
	 * Maximum tax percentage getter.
	 * 
	 * @return maximum tax percentage.
	 */
	public double getMaxTaxPercentage() {
		return maxTaxPercentage;
	}

	/**
	 * Maximum tax percentage setter
	 * 
	 * @param maxTaxPercentage
	 *            new maximum tax percentage.
	 */
	public void setMaxTaxPercentage(double maxTaxPercentage) {
		this.maxTaxPercentage = maxTaxPercentage;
	}

	/**
	 * Auto-generates currency types list.
	 */
	private void autoGenerateCurrecies() {
		this.currencies = new ArrayList<Currency>();
		for (Payment pm : this.options) {
			for (Currency c : pm.getCurrencies()) {
				if (!this.currencies.contains(c)) {
					this.currencies.add(c);
				}
			}
		}
	}

}