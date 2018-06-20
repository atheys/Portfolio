package logistics.base;

import java.util.ArrayList;

import data.core.books.Bookie;
import data.core.structure.GameDay;
import mathematics.portfolio.Asset;

/**
 * FrontMan Class.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class FrontMan {

	/**
	 * Class attributes.
	 */
	private String ID;
	private String name;
	private ArrayList<GameDay> availability;
	private ArrayList<Bookster> bookies;
	private ArrayList<Payment> payments;
	private ArrayList<Currency> currencies;

	/**
	 * Copy constructor.
	 * 
	 * @param fm
	 *            FrontMan-Object to copy.
	 */
	public FrontMan(FrontMan fm) {
		this.ID = fm.ID;
		this.name = fm.name;
		this.availability = fm.availability;
		this.bookies = fm.bookies;
		this.payments = fm.payments;
		this.currencies = fm.currencies;
	}

	/**
	 * General constructor.
	 * 
	 * @param ID
	 *            ID feature.
	 * @param name
	 *            name features.
	 */
	public FrontMan(String ID, String name) {
		this.ID = ID;
		this.name = name;
		this.availability = new ArrayList<GameDay>();
		this.bookies = new ArrayList<Bookster>();
		this.payments = new ArrayList<Payment>();
		this.currencies = new ArrayList<Currency>();
	}

	/**
	 * General constructor.
	 * 
	 * @param ID
	 *            ID feature.
	 * @param name
	 *            name feature.
	 * @param availability
	 *            list of available dates.
	 * @param bookies
	 *            list of relevant Bookster-Objects.
	 */
	public FrontMan(String ID, String name, ArrayList<GameDay> availability, ArrayList<Bookster> bookies,
			ArrayList<Payment> payments, ArrayList<Currency> currencies) {
		this.ID = ID;
		this.name = name;
		this.availability = availability;
		this.bookies = bookies;
		this.payments = payments;
		this.currencies = currencies;
		this.sortDates();
	}

	/**
	 * ID getter
	 * 
	 * @return ID feature.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Name getter.
	 * 
	 * @return name feature.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Availability getter.
	 * 
	 * @return availability dates.
	 */
	public ArrayList<GameDay> getAvailability() {
		return availability;
	}

	/**
	 * Availability setter.
	 * 
	 * @param availability
	 *            new availability list.
	 */
	public void setAvailability(ArrayList<GameDay> availability) {
		this.availability = availability;
	}

	/**
	 * Booksters getter.
	 * 
	 * @return list of relevant Bookster-Objects.
	 */
	public ArrayList<Bookster> getBookies() {
		return bookies;
	}

	/**
	 * Booksters setter.
	 * 
	 * @param bookies
	 *            new list of relevant Booksters.
	 */
	public void setBookies(ArrayList<Bookster> bookies) {
		this.bookies = bookies;
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

	/**
	 * Payments getter.
	 * 
	 * @return list of possible payment methods.
	 */
	public ArrayList<Payment> getPayments() {
		return payments;
	}

	/**
	 * Payments setter.
	 * 
	 * @param payments
	 *            new list of possible payment methods.
	 */
	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * Determines if FrontMan is available at a certain date.
	 * 
	 * @param date
	 *            relevant GameDay-Object.
	 * @return availability indicator.
	 */
	public boolean available(GameDay date) {
		for (GameDay gd : this.availability) {
			if ((date.equals(gd) || date.after(gd)) && date.before(gd.plus(7))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sorts GameDay-Objects in availability list.
	 */
	private void sortDates() {
		if (this.availability.size() > 1) {
			for (int i = this.availability.size() - 1; i >= 0; i--) {
				for (int j = 1; j <= i; j++) {
					if (this.availability.get(j - 1).after(availability.get(j))) {
						GameDay temp = this.availability.get(j - 1);
						this.availability.add(j - 1, this.availability.get(j));
						this.availability.remove(j);
						this.availability.add(j, temp);
						this.availability.remove(j + 1);
					}
				}
			}
		}
	}

	/**
	 * Adds GameDay-Object to availability list.
	 * 
	 * @param date
	 *            relevant GameDay-Object.
	 */
	public void addDate(GameDay date) {
		if (available(date)) {
			return;
		}
		this.availability.add(date);
		this.sortDates();
	}

	/**
	 * Removes GameDay-Object from availability list.
	 * 
	 * @note availability list must be sorted beforehand.
	 * @param date
	 *            relevant GameDay-Object.
	 */
	public void removeDate(GameDay date) {
		for (GameDay gd : this.availability) {
			if ((date.equals(gd) || date.after(gd)) && date.before(gd.plus(7))) {
				this.availability.remove(gd);
				return;
			}
		}
	}

	/**
	 * Adds Bookster-Object to bookies list.
	 * 
	 * @param bs
	 *            relevant Bookster-Object to add.
	 */
	public void addBookster(Bookster bs) {
		for (Bookster b : this.bookies) {
			if (b.equals(bs)) {
				this.bookies.remove(b);
				this.bookies.add(bs);
				return;
			}
		}
		this.bookies.add(bs);
	}

	/**
	 * Removes Bookster-Object from bookies list.
	 * 
	 * @param bs
	 *            relevant Bookster-Object to remove.
	 */
	public void removeBookster(Bookster bs) {
		for (Bookster b : this.bookies) {
			if (b.equals(bs)) {
				this.bookies.remove(b);
			}
		}
	}

	/**
	 * Removes Bookster-Object from bookies list.
	 * 
	 * @param bs
	 *            relevant Bookie-Object to remove.
	 */
	public void removeBookster(Bookie bs) {
		for (Bookster b : this.bookies) {
			if (b.getB().equals(bs)) {
				this.bookies.remove(b);
			}
		}
	}

	/**
	 * Determines if FrontMan-Object can allocate a certain Asset-Object.
	 * 
	 * @param a
	 *            Asset-Object to allocate.
	 * @return allocation evaluation.
	 */
	public boolean possibleAllocation(Asset a) {
		for (Bookster b : this.bookies) {
			if (b.getB().getId().equals(a.getBookie_id())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if FrontMan can pay in a certain currency.s
	 * 
	 * @param c
	 *            currency type to pay in.
	 * @return evaluation boolean.f
	 */
	public boolean canPay(Currency c) {
		for (Currency curr : this.currencies) {
			if (curr.equals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Compares two FrontMan-Objects.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof FrontMan) {
			FrontMan that = (FrontMan) obj;
			return this.ID.equals(that.ID);
		}
		return false;
	}

}