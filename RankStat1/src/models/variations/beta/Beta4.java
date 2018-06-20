package models.variations.beta;

import exceptions.ModelException;
import mathematics.distributions.BiPoisson;
import mathematics.distributions.Poisson;
import models.general.base.Average;
import models.general.base.Model;
import models.general.base.StatsBase;
import models.general.handlers.ModelFunctions;
import models.general.handlers.Weightor;

/**
 * Beta4 Model Class.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public class Beta4 extends Model implements ModelFunctions {

	/**
	 * Class attributes.
	 */
	private BiPoisson bp;
	private double[] wOffCorrelation;
	private double[] wDefCorrelation;

	/**
	 * General constructor.
	 * 
	 * @param id
	 *            ID of the model.
	 * @param sb
	 *            StatsBase-object of the model.
	 * @throws ModelException
	 */
	public Beta4(String id, StatsBase sb, double[] wOffCorrelation, double[] wDefCorrelation) {
		super(id, sb);
		this.wOffCorrelation = Weightor.normalize(wOffCorrelation);
		this.wDefCorrelation = Weightor.normalize(wDefCorrelation);
		this.developBiPoisson();
	}

	/**
	 * Bipoisson getter.
	 * 
	 * @return BiPoisson-Object.
	 */
	public BiPoisson getBp() {
		return bp;
	}

	/**
	 * Bipoisson setter.
	 * 
	 * @param bp
	 *            new BiPoisson-Object.
	 */
	public void setBp(BiPoisson bp) {
		this.bp = bp;
	}

	/**
	 * Create corresponding BiPoisson-object.
	 * 
	 * @throws ModelException
	 */
	private void developBiPoisson() {
		Average home = this.sb.getHomeTeamAverage();
		Average home_adv = this.sb.getHomeAdversaryAverage();
		Average away = this.sb.getAwayTeamAverage();
		Average away_adv = this.sb.getAwayAdversaryAverage();
		double[] home_values = gatherValues(home, home_adv);
		double[] home_bonus = new double[home_values.length];
		double[] away_values = gatherValues(away, away_adv);
		double[] away_bonus = new double[away_values.length];
		for (int i = 0; i < home_values.length; i++) {
			home_bonus[i] = find(i, home_values[i]);
			away_bonus[i] = find(i, away_values[i]);
		}
		double a_H = eval(this.wOffCorrelation, home_bonus);
		double b_H = eval(this.wDefCorrelation, home_bonus);
		double a_A = eval(this.wOffCorrelation, away_bonus);
		double b_A = eval(this.wDefCorrelation, away_bonus);
		double h = this.sb.getHomeAdvantage();
		Poisson H = new Poisson(h * a_H * b_A * home.getAvGoals());
		Poisson A = new Poisson(a_A * b_H * away.getAvGoals());
		this.bp = new BiPoisson(this.id, H, A);
	}

	/**
	 * Returns odds of a home win.
	 */
	public double homeWin() {
		return this.bp.P1();
	}

	/**
	 * Returns odds of a draw.
	 */
	public double draw() {
		return this.bp.P1P2();
	}

	/**
	 * Returns odds of an away win.
	 */
	public double awayWin() {
		return this.bp.P2();
	}

}