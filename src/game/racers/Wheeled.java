package game.racers;

/**
 * This Wheeled class
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Wheeled {

	private int numOfWheels;

	public Wheeled(int numOfWheels) {
		this.numOfWheels = numOfWheels;
	}

	public String describeSpecific() {
		return ", Number of Wheels: " + this.getNumOfWheels();
	}

	public int getNumOfWheels() {
		return numOfWheels;
	}

	public void setNumOfWheels(int numOfWheels) {
		this.numOfWheels = numOfWheels;
	}
	
	
}
