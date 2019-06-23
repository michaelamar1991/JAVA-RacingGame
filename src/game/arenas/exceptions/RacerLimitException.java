package game.arenas.exceptions;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
/**
 * This RacerLimitException class 
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class RacerLimitException extends Exception {

	/**
	 * @param ""
	 *            + max_racers
	 */
	public RacerLimitException(int max_racers, int racerNumber) {
		super("Arena is full! (" + max_racers + " active racers exist). racer #" + racerNumber + " was not added");
		JOptionPane.showMessageDialog(null, "Arena is full!!","Message",JOptionPane.INFORMATION_MESSAGE);
	}

}
