package logistics.scheme;

import java.util.ArrayList;
import data.core.books.Bookie;
import data.core.structure.GameDay;
import logistics.base.Bookster;
import logistics.base.Currency;
import logistics.base.FrontMan;
import logistics.base.Payment;
import logistics.base.Regulator;

/**
 * Availability dispatcher.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Dispatcher {

	/**
	 * Class attributes.
	 */
	private GameDay gd;
	// Available FrontMan-Objects
	private ArrayList<FrontMan> frontmen;
	// Bookie-Object related to all these FrontMan-Objects
	private ArrayList<Bookie> bookies;
	// List of all Regulator-Objects
	private ArrayList<Regulator> regulators;
	// Payment-Object options related to all these FrontMan-Objects
	private ArrayList<Payment> payments;
	// Currencies FrontMan-Objects can pay with
	private ArrayList<Currency> currencies;

	/**
	 * General Constructor.
	 * 
	 * @param gd
	 *            relevant GameDay-Object.
	 * @param frontmen
	 *            list of all FrontMan-Objects.
	 * @param regulators
	 *            list of all bookie-related Regulator-Objects.
	 */
	public Dispatcher(GameDay gd, ArrayList<FrontMan> frontmen, ArrayList<Regulator> regulators) {
		this.gd = gd;
		this.assembleFrontMen(frontmen);
		this.regulators = regulators;
		this.assembleBookies();
		this.assemblePaymentOptions();
		this.assembleCurrencies();
		this.rankRegulators();
	}

	/**
	 * Assembles all available FrontMan-Objects.
	 */
	private void assembleFrontMen(ArrayList<FrontMan> frontmen) {
		this.frontmen = new ArrayList<FrontMan>();
		for (FrontMan fm : frontmen) {
			if (fm.available(this.gd) && !this.frontmen.contains(fm)) {
				this.frontmen.add(fm);
			}
		}
	}

	/**
	 * Assembles all relevant Bookie-Objects.
	 */
	private void assembleBookies() {
		ArrayList<Bookie> b1 = new ArrayList<Bookie>();
		for (Regulator r : this.regulators) {
			if (!b1.contains(r.getB())) {
				b1.add(r.getB());
			}
		}
		ArrayList<Bookie> b2 = new ArrayList<Bookie>();
		for (FrontMan fm : this.frontmen) {
			for (Bookster b : fm.getBookies()) {
				if (!b2.contains(b.getB())) {
					b2.add(b.getB());
				}
			}
		}
		this.bookies = sectionBookie(b1, b2);
	}

	/**
	 * Assembles all relevant payment options.
	 * 
	 * @param regulators
	 *            list of relevant payment options.
	 */
	private void assemblePaymentOptions() {
		ArrayList<Payment> p1 = new ArrayList<Payment>();
		for (Regulator r : this.regulators) {
			for (Payment p : r.getOptions()) {
				if (this.bookies.contains(r.getB()) && !p1.contains(p)) {
					p1.add(p);
				}
			}
		}
		ArrayList<Payment> p2 = new ArrayList<Payment>();
		for (FrontMan fm : this.frontmen) {
			for (Payment p : fm.getPayments()) {
				if (!p2.contains(p)) {
					p2.add(p);
				}
			}
		}
		this.payments = sectionPayment(p1, p2);
	}

	/**
	 * Assembles all relevant currencies to pay with.
	 */
	private void assembleCurrencies() {
		ArrayList<Currency> first = new ArrayList<Currency>();
		for (FrontMan fm : this.frontmen) {
			for (Currency c : fm.getCurrencies()) {
				if (!first.contains(c)) {
					first.add(c);
				}
			}
		}
		ArrayList<Currency> second = new ArrayList<Currency>();
		for (Regulator r : this.regulators) {
			for (Payment p : r.getOptions()) {
				for (Currency c : p.getCurrencies()) {
					if (!second.contains(c)) {
						second.add(c);
					}
				}
			}
		}
		this.currencies = sectionCurrency(first, second);
	}

	/**
	 * Ranks Regulator-Objects.s
	 */
	private void rankRegulators() {
	}

	/**
	 * GameDay getter.
	 * 
	 * @return relevant GameDay-Object.
	 */
	public GameDay getGd() {
		return gd;
	}

	/**
	 * Frontmen getter.
	 * 
	 * @return list of available FrontMan-Objects.s
	 */
	public ArrayList<FrontMan> getFrontmen() {
		return frontmen;
	}

	/**
	 * Bookies getter.
	 * 
	 * @return list of available Bookie-Objects.
	 */
	public ArrayList<Bookie> getBookies() {
		return bookies;
	}

	/**
	 * Regulators getter.
	 * 
	 * @return list of (ranked) Regulator-Objects.
	 */
	public ArrayList<Regulator> getRegulators() {
		return regulators;
	}

	/**
	 * Payment options getter.
	 * 
	 * @return list of possible payment options.
	 */
	public ArrayList<Payment> getPayments() {
		return payments;
	}

	/**
	 * Currencies getter.
	 * 
	 * @return list of possible currency types to pay with.
	 */
	public ArrayList<Currency> getCurrencies() {
		return currencies;
	}

	/**
	 * Takes section of two Currency lists.
	 * 
	 * @param c1
	 *            first Currency-list.
	 * @param c2
	 *            second Currency-list.
	 * @return section Currency-list.
	 */
	private ArrayList<Currency> sectionCurrency(ArrayList<Currency> c1, ArrayList<Currency> c2) {
		ArrayList<Currency> section = new ArrayList<Currency>();
		if (c1.size() == 0)
			return section;
		if (c2.size() == 0)
			return section;
		for (Currency c : c1) {
			if (c2.contains(c)) {
				section.add(c);
			}
		}
		return section;
	}

	/**
	 * Takes section of two FrontMan-lists.
	 * 
	 * @param fm1
	 *            first FrontMan-list.
	 * @param fm2
	 *            second FrontMan-list.
	 * @return section FrontMan-list.
	 */
	private ArrayList<FrontMan> sectionFrontMan(ArrayList<FrontMan> fm1, ArrayList<FrontMan> fm2) {
		ArrayList<FrontMan> section = new ArrayList<FrontMan>();
		if (fm1.size() == 0)
			return section;
		if (fm2.size() == 0)
			return section;
		for (FrontMan fm : fm1) {
			if (fm2.contains(fm)) {
				section.add(fm);
			}
		}
		return section;
	}

	/**
	 * Takes section of two Bookie-lists.
	 * 
	 * @param b1
	 *            first Bookie-list.
	 * @param b2
	 *            second Bookie-list.
	 * @return section Bookie-list.
	 */
	private ArrayList<Bookie> sectionBookie(ArrayList<Bookie> b1, ArrayList<Bookie> b2) {
		ArrayList<Bookie> section = new ArrayList<Bookie>();
		if (b1.size() == 0)
			return section;
		if (b2.size() == 0)
			return section;
		for (Bookie b : b1) {
			if (b2.contains(b)) {
				section.add(b);
			}
		}
		return section;
	}

	/**
	 * Takes section of two Payment-lists.
	 * 
	 * @param p1
	 *            first Payment-list.
	 * @param p2
	 *            second Payment-list.
	 * @return section Payment-list.
	 */
	private ArrayList<Payment> sectionPayment(ArrayList<Payment> p1, ArrayList<Payment> p2) {
		ArrayList<Payment> section = new ArrayList<Payment>();
		if (p1.size() == 0)
			return section;
		if (p2.size() == 0)
			return section;
		for (Payment p : p1) {
			if (p2.contains(p)) {
				section.add(p);
			}
		}
		return section;
	}

	/**
	 * Checks currency standard for a given Bookie-Object.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param c
	 *            relevant Currency-Object.
	 * @return standard check evaluation.
	 */
	private boolean currencyCheck(Bookie b, Currency c) {
		try {
			Regulator r = this.findRegulator(b);
			for (Payment p : r.getOptions()) {
				for (Currency curr : p.getCurrencies()) {
					if (curr.equals(c)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks currency standard for a given Bookie-Object.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param c
	 *            relevant Currency-Object.
	 * @return standard check evaluation.
	 */
	private boolean paymentCheck(Bookie b, Payment p) {
		try {
			Regulator r = this.findRegulator(b);
			for (Payment pay : r.getOptions()) {
				if (pay.equals(p))
					return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Finds Regulator-Object related to a certain Bookie-Object.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @return corresponding Regulator-Object.
	 * @throws Exception
	 *             in case no such Regulator-Object is found.
	 */
	public Regulator findRegulator(Bookie b) throws Exception {
		for (Regulator r : this.regulators) {
			if (r.getB().equals(b)) {
				return r;
			}
		}
		throw new Exception("No Bookie-related Regulator found.");
	}

	/**
	 * Finds all Bookie-Objects where one can pay with a given currency.
	 * 
	 * @param c
	 *            relevant Currency-Object.
	 * @return corresponding list of Bookie-Objects.
	 */
	public ArrayList<Bookie> findBookies(Currency c) {
		ArrayList<Bookie> books = new ArrayList<Bookie>();
		for (Bookie b : this.bookies) {
			if (this.currencyCheck(b, c) && !books.contains(b)) {
				books.add(b);
			}
		}
		return books;
	}

	/**
	 * Finds all Bookie-Objects where one can pay with a given method.
	 * 
	 * @param p
	 *            relevant Payment-Object.
	 * @return corresponding list of Bookie-Objects.
	 */
	public ArrayList<Bookie> findBookies(Payment p) {
		ArrayList<Bookie> books = new ArrayList<Bookie>();
		for (Bookie b : this.bookies) {
			if (this.paymentCheck(b, p) && !books.contains(b)) {
				books.add(b);
			}
		}
		return books;
	}

	/**
	 * Provides a list of FrontMan-Objects for a given Bookie-Object.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @return corresponding list of FrontMan-Objects.
	 */
	public ArrayList<FrontMan> allFrontMen(Bookie b) {
		ArrayList<FrontMan> men = new ArrayList<FrontMan>();
		if (!this.bookies.contains(b))
			return men;
		for (FrontMan fm : this.frontmen) {
			for (Bookster bs : fm.getBookies()) {
				if (bs.getB().equals(b)) {
					men.add(fm);
					break;
				}
			}
		}
		return men;
	}

	/**
	 * Provides a list of all FrontMan-Objects that can pay using a certain
	 * payment option.
	 * 
	 * @param p
	 *            relevant Payment-Object.
	 * @return correspondign list of FrontMan-Objects.
	 */
	public ArrayList<FrontMan> allFrontMen(Payment p) {
		ArrayList<FrontMan> fm = new ArrayList<FrontMan>();
		if (!this.payments.contains(p))
			return fm;
		for (FrontMan man : this.frontmen) {
			for (Payment pay : man.getPayments()) {
				if (pay.equals(p))
					fm.add(man);
			}
		}
		return fm;
	}

	/**
	 * Provides a list of FrontMan-Objects for a given Bookie-Object.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @return corresponding list of FrontMan-Objects.
	 */
	public ArrayList<FrontMan> allFrontMen(Currency c) {
		ArrayList<FrontMan> men = new ArrayList<FrontMan>();
		if (!this.currencies.contains(c))
			return men;
		for (FrontMan fm : this.frontmen) {
			for (Currency curr : fm.getCurrencies()) {
				if (curr.equals(c)) {
					men.add(fm);
					break;
				}
			}
		}
		return men;
	}

	/**
	 * Finds all FrontMan-Objects that can pay with a certain currency at a
	 * certain bookie.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param c
	 *            relevant Currency-Object.
	 * @return corresponding list of FrontMan-Objects.
	 */
	public ArrayList<FrontMan> allFrontMen(Bookie b, Currency c) {
		ArrayList<FrontMan> fm = new ArrayList<FrontMan>();
		if (!currencyCheck(b, c))
			return fm;
		ArrayList<FrontMan> fm1 = allFrontMen(b);
		ArrayList<FrontMan> fm2 = allFrontMen(c);
		return sectionFrontMan(fm1, fm2);
	}

	/**
	 * Finds all FrontMan-Objects that can place bets at a given Bookie using a
	 * given Payment method.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param p
	 *            relevant Payment-object.
	 * @return corresponding FrontMan-list.
	 */
	public ArrayList<FrontMan> allFrontMen(Bookie b, Payment p) {
		ArrayList<FrontMan> men = new ArrayList<FrontMan>();
		try {
			Regulator r = findRegulator(b);
			if (r.getOptions().contains(p)) {
				for (FrontMan fm : this.frontmen) {
					if (fm.getPayments().contains(p) && !men.contains(fm)) {
						men.add(fm);
					}
				}
			}
			return men;
		} catch (Exception e) {
			return men;
		}
	}

	/**
	 * Finds all relevant payment options for a given currency at a certain
	 * bookie.
	 * 
	 * @param b
	 *            relevant Bookie-Object.
	 * @param c
	 *            relevant Currency-Object.
	 * @return correspondign list of payment options.
	 */
	public ArrayList<Payment> findPaymentOptions(Bookie b, Currency c) {
		ArrayList<Payment> payments = new ArrayList<Payment>();
		try {
			Regulator r = this.findRegulator(b);
			for (Payment p : r.getOptions()) {
				for (Currency curr : p.getCurrencies()) {
					if (curr.equals(c)) {
						payments.add(p);
						break;
					}
				}
			}
			return payments;
		} catch (Exception e) {
			return payments;
		}
	}

	/**
	 * Takes intersection of two Currency lists.
	 * 
	 * @param c1
	 *            first Currency-list.
	 * @param c2
	 *            second Currency-list.
	 * @return section Currency-list.
	 */
	public static ArrayList<Currency> intersectionC(ArrayList<Currency> c1, ArrayList<Currency> c2) {
		ArrayList<Currency> section = new ArrayList<Currency>();
		if (c1.size() == 0)
			return section;
		if (c2.size() == 0)
			return section;
		for (Currency c : c1) {
			if (c2.contains(c)) {
				section.add(c);
			}
		}
		return section;
	}

	/**
	 * Takes union of two Currency lists.
	 * 
	 * @param c1
	 *            first Currency-list.
	 * @param c2
	 *            second Currency-list.
	 * @return section Currency-list.
	 */
	public static ArrayList<Currency> unionC(ArrayList<Currency> c1, ArrayList<Currency> c2) {
		if (c1.size() == 0)
			return c2;
		if (c2.size() == 0)
			return c1;
		for (Currency c : c1) {
			if (!c2.contains(c)) {
				c2.add(c);
			}
		}
		return c2;
	}

	/**
	 * Takes intersection of two FrontMan-lists.
	 * 
	 * @param fm1
	 *            first FrontMan-list.
	 * @param fm2
	 *            second FrontMan-list.
	 * @return section FrontMan-list.
	 */
	public static ArrayList<FrontMan> intersectionFM(ArrayList<FrontMan> fm1, ArrayList<FrontMan> fm2) {
		ArrayList<FrontMan> section = new ArrayList<FrontMan>();
		if (fm1.size() == 0)
			return section;
		if (fm2.size() == 0)
			return section;
		for (FrontMan fm : fm1) {
			if (fm2.contains(fm)) {
				section.add(fm);
			}
		}
		return section;
	}

	/**
	 * Takes union of two FrontMan-lists.
	 * 
	 * @param fm1
	 *            first FrontMan-list.
	 * @param fm2
	 *            second FrontMan-list.
	 * @return section FrontMan-list.
	 */
	public static ArrayList<FrontMan> unionFM(ArrayList<FrontMan> fm1, ArrayList<FrontMan> fm2) {
		if (fm1.size() == 0)
			return fm2;
		if (fm2.size() == 0)
			return fm1;
		for (FrontMan fm : fm1) {
			if (!fm2.contains(fm)) {
				fm2.add(fm);
			}
		}
		return fm2;
	}

	/**
	 * Takes intersection of two Bookie-lists.
	 * 
	 * @param b1
	 *            first Bookie-list.
	 * @param b2
	 *            second Bookie-list.
	 * @return section Bookie-list.
	 */
	public static ArrayList<Bookie> intersectionB(ArrayList<Bookie> b1, ArrayList<Bookie> b2) {
		ArrayList<Bookie> section = new ArrayList<Bookie>();
		if (b1.size() == 0)
			return section;
		if (b2.size() == 0)
			return section;
		for (Bookie b : b1) {
			if (b2.contains(b)) {
				section.add(b);
			}
		}
		return section;
	}

	/**
	 * Takes intersection of two Bookie-lists.
	 * 
	 * @param b1
	 *            first Bookie-list.
	 * @param b2
	 *            second Bookie-list.
	 * @return section Bookie-list.
	 */
	public static ArrayList<Bookie> unionB(ArrayList<Bookie> b1, ArrayList<Bookie> b2) {
		if (b1.size() == 0)
			return b2;
		if (b2.size() == 0)
			return b1;
		for (Bookie b : b1) {
			if (!b2.contains(b)) {
				b2.add(b);
			}
		}
		return b2;
	}

	/**
	 * Takes intersection of two Payment-lists.
	 * 
	 * @param p1
	 *            first Payment-list.
	 * @param p2
	 *            second Payment-list.
	 * @return section Payment-list.
	 */
	public static ArrayList<Payment> intersectionP(ArrayList<Payment> p1, ArrayList<Payment> p2) {
		ArrayList<Payment> section = new ArrayList<Payment>();
		if (p1.size() == 0)
			return section;
		if (p2.size() == 0)
			return section;
		for (Payment p : p1) {
			if (p2.contains(p)) {
				section.add(p);
			}
		}
		return section;
	}

	/**
	 * Takes union of two Payment-lists.
	 * 
	 * @param p1
	 *            first Payment-list.
	 * @param p2
	 *            second Payment-list.
	 * @return section Payment-list.
	 */
	public static ArrayList<Payment> unionP(ArrayList<Payment> p1, ArrayList<Payment> p2) {
		if (p1.size() == 0)
			return p2;
		if (p2.size() == 0)
			return p1;
		for (Payment p : p1) {
			if (!p2.contains(p)) {
				p2.add(p);
			}
		}
		return p2;
	}

}