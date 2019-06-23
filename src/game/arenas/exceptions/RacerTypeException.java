package game.arenas.exceptions;

import game.racers.Racer;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
/**
 * This RacerTypeException class 
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class RacerTypeException extends Exception {
	public RacerTypeException(String racerType, String arenaType) {
		super("Invalid Racer of type \"" + racerType + "\" for " + arenaType + " arena.");
		JOptionPane.showMessageDialog(null, "Arena type is different from racer type!","Message",JOptionPane.INFORMATION_MESSAGE);
		//printStackTrace();
	}

}
