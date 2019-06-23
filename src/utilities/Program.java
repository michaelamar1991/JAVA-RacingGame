/**
 * 
 */
package utilities;

import javax.swing.JFrame;
import factory.RaceBuilder;

/**
 * This Program class - driver
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Program {
	
	private static RaceBuilder builder = RaceBuilder.getInstance();
	
	public static void main(String[] args) {
		
		builder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		builder.setResizable(false);
		builder.setVisible(true);
	}

}
