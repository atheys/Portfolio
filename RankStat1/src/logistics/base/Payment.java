package logistics.base;

import java.util.ArrayList;

/**
 * Payment Option Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Payment {

	/**
	 * Class attributes.
	 */
	// General Information
	private String ID;
	private String name;
	private boolean online;
	private boolean physical;
	private ArrayList<Currency> currencies;
	// Money Transfer
	private double transferFee;
	private double minTransfer;
	private double maxTransfer;
	private double transferTime; // hours
	// Money Withdrawal
	private double withdrawalFee;
	private double minWithdrawal;
	private double maxWithdrawal;
	private double withdrawalTime; // hours

	/**
	 * Copy constructor.
	 * 
	 * @param p
	 *            Payment-Object to copy.
	 */
	public Payment(Payment p) {
		this.ID = p.ID;
		this.name = p.name;
		this.online = p.online;
		this.physical = p.physical;
		this.transferFee = p.transferFee;
		this.minTransfer = p.minTransfer;
		this.maxTransfer = p.maxTransfer;
		this.transferTime = p.transferTime;
		this.withdrawalFee = p.withdrawalFee;
		this.minWithdrawal = p.minWithdrawal;
		this.maxWithdrawal = p.maxWithdrawal;
		this.withdrawalTime = p.withdrawalTime;
		this.currencies = p.currencies;
	}

	/**
	 * General constructor.
	 * 
	 * @param ID
	 *            ID feature.
	 * @param name
	 *            name feature.
	 * @param online
	 *            online indicator.
	 * @param physical
	 *            physical indicator.
	 * @param transferFee
	 *            transfer fee.
	 * @param minTransfer
	 *            minimum transfer amount.
	 * @param maxTransfer
	 *            maximum transfer amount.
	 * @param transferTime
	 *            transfer processing time.
	 * @param withdrawalFee
	 *            withdrawal fee.
	 * @param minWithdrawal
	 *            minimum withdrawal amount.
	 * @param maxWithdrawal
	 *            maximum withdrawal amount.
	 * @param withdrawalTime
	 *            withdrawal processing time.
	 * @param currencies
	 *            currency types list.
	 */
	public Payment(String ID, String name, boolean online, boolean physical, double transferFee, double minTransfer,
			double maxTransfer, double transferTime, double withdrawalFee, double minWithdrawal, double maxWithdrawal,
			double withdrawalTime, ArrayList<Currency> currencies) {
		this.ID = ID;
		this.name = name;
		this.online = online;
		this.physical = physical;
		this.transferFee = transferFee;
		this.minTransfer = minTransfer;
		this.maxTransfer = maxTransfer;
		this.transferTime = transferTime;
		this.withdrawalFee = withdrawalFee;
		this.minWithdrawal = minWithdrawal;
		this.maxWithdrawal = maxWithdrawal;
		this.withdrawalTime = withdrawalTime;
		this.currencies = currencies;
	}

	/**
	 * Payment option ID getter.
	 * 
	 * @return payment option ID.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Payment option name getter.
	 * 
	 * @return payment option name.
	 */
	public String getName() {
		return name;
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
	 * Transfer fee getter.
	 * 
	 * @return transfer fee.
	 */
	public double getTransferFee() {
		return transferFee;
	}

	/**
	 * Transfer fee setter.
	 * 
	 * @param transferFee
	 *            new transfer fee.s
	 */
	public void setTransferFee(double transferFee) {
		this.transferFee = transferFee;
	}

	/**
	 * Minimum transfer amount getter.
	 * 
	 * @return minimum transfer amount.
	 */
	public double getMinTransfer() {
		return minTransfer;
	}

	/**
	 * Minimum transfer amount setter.
	 * 
	 * @param minTransfer
	 *            new minimum transfer amount.
	 */
	public void setMinTransfer(double minTransfer) {
		this.minTransfer = minTransfer;
	}

	/**
	 * Maximum transfer amount getter.
	 * 
	 * @return maximum transfer amount.
	 */
	public double getMaxTransfer() {
		return maxTransfer;
	}

	/**
	 * Maximum transfer amount setter.
	 * 
	 * @param maxTransfer
	 *            new maximum transfer amount.
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
	 * Withdrawal fee getter.
	 * 
	 * @return withdrawal fee.
	 */
	public double getWithdrawalFee() {
		return withdrawalFee;
	}

	/**
	 * Withdrawal fee setter.
	 * 
	 * @param withdrawalFee
	 *            new withdrawal fee.
	 */
	public void setWithdrawalFee(double withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	/**
	 * Minimum withdrawal amount getter.
	 * 
	 * @return minimum withdrawal amount.
	 */
	public double getMinWithdrawal() {
		return minWithdrawal;
	}

	/**
	 * Minimum withdrawal amount setter.
	 * 
	 * @param maxWithdrawal
	 *            new minimum withdrawal amount.
	 */
	public void setMinWithdrawal(double minWithdrawal) {
		this.minWithdrawal = minWithdrawal;
	}

	/**
	 * Maximum withdrawal amount getter.
	 * 
	 * @return maximum withdrawal amount.
	 */
	public double getMaxWithdrawal() {
		return maxWithdrawal;
	}

	/**
	 * Maximum withdrawal amount setter.
	 * 
	 * @param maxTransfer
	 *            new maximum withdrawal amount.
	 */
	public void setMaxWithdrawal(double maxWithdrawal) {
		this.maxWithdrawal = maxWithdrawal;
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
	 * Currencies getter.
	 * 
	 * @return list of relevant currencies.
	 */
	public ArrayList<Currency> getCurrencies() {
		return currencies;
	}

	/**
	 * Currencies setter.
	 * 
	 * @param currencies
	 *            new list of relevant currencies.
	 */
	public void setCurrencies(ArrayList<Currency> currencies) {
		this.currencies = currencies;
	}

}