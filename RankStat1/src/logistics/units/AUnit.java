package logistics.units;

import logistics.base.Currency;
import mathematics.portfolio.Asset;

/**
 * Asset Unit Class.
 * 
 * @author Andreas Theys.
 * @version 1.0.
 */
public class AUnit {

	/**
	 * Class attributes.
	 */
	private Asset a;
	private double value;
	private Currency currency;

	/**
	 * Copy Constructor.
	 * 
	 * @param au
	 *            AUnit-Object to copy.
	 */
	public AUnit(AUnit au) {
		this.a = au.a;
		this.value = au.value;
		this.currency = au.currency;
	}

	/**
	 * General Constructor.
	 * 
	 * @param a
	 *            relevant Asset-Object.
	 * @param value
	 *            asset value.
	 * @param currency
	 *            currency type.
	 */
	public AUnit(Asset a, double value, Currency currency) {
		this.a = a;
		this.value = value;
		this.currency = currency;
	}

	/**
	 * Asset getter.
	 * 
	 * @return corresponding Asset-Object.
	 */
	public Asset getA() {
		return a;
	}

	/**
	 * Asset setter.
	 * 
	 * @param a
	 *            new Asset-Object.
	 */
	public void setA(Asset a) {
		this.a = a;
	}

	/**
	 * Value getter.
	 * 
	 * @return asset value.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Value setter.
	 * 
	 * @param value
	 *            new asset value.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Currency getter.
	 * 
	 * @return currency type.
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Currency setter.
	 * 
	 * @param currency
	 *            new currency type.
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * Compares two AUnit-Objects.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof AUnit) {
			AUnit that = (AUnit) obj;
			return this.a.equals(that.a) && this.value == that.value && this.currency.equals(that.currency);
		}
		return false;
	}

}