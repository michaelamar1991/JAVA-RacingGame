package utilities;

import java.text.DecimalFormat;

/**
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Mishap {

	private boolean fixable;
	private int turnsToFix;
	private double reductionFactor;

	/**
	 * @param fixable
	 * @param turnsToFix
	 * @param reductionFactor
	 */
	public Mishap(boolean fixable, int turnsToFix, double reductionFactor) {
		super();
		this.setFixable(fixable);
		this.setTurnsToFix(turnsToFix);
		this.setReductionFactor(reductionFactor);
	}

	public double getReductionFactor() {
		return reductionFactor;
	}

	public int getTurnsToFix() {
		return turnsToFix;
	}

	public boolean isFixable() {
		return fixable;
	}

	public void nextTurn() {
		if (this.fixable) {
			this.turnsToFix--;
		}
	}

	private void setFixable(boolean fixable) {
		this.fixable = fixable;
	}

	private void setReductionFactor(double reductionFactor) {
		this.reductionFactor = reductionFactor;
	}

	private void setTurnsToFix(int turnsToFix) {
		this.turnsToFix = turnsToFix;
	}

	@Override
	public String toString() {
		String s = "";
		s += this.fixable + ", ";
		s += this.turnsToFix + ", ";
		s += new DecimalFormat("0.00").format(this.reductionFactor);
		return s;
	}
}
