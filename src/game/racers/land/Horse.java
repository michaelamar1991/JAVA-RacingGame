package game.racers.land;

import java.awt.Graphics;

import javafx.beans.InvalidationListener;
import factory.RaceBuilder;
import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.air.Airplane;
import utilities.EnumContainer;
import utilities.EnumContainer.Breed;
import utilities.EnumContainer.Color;

/**
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Horse extends Racer implements ILandRacer {

	private static final String CLASS_NAME = "Horse";

	private static final int DEFAULT_WHEELS = 0;
	private static final double DEFAULT_MAX_SPEED = 50;
	private static final double DEFAULT_ACCELERATION = 3;
	private static final Color DEFAULT_color = Color.BLACK;
	private String typeArena = "LandArena";
	private EnumContainer.Breed breed;
	private Wheeled wheeled;

	public String getTypeArena() {
		return typeArena;
	}

	public Horse() {
		this(CLASS_NAME + " #" + lastSerialNumber, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
	}

	public Horse(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		this.breed = Breed.THOROUGHBRED;
	}

	public Wheeled getWheeled() {
		return wheeled;
	}

	public static int getDefaultWheels() {
		return DEFAULT_WHEELS;
	}
	
	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public String describeSpecific() {
		String s = "";
		s += ", Breed: " + this.breed;
		return s;
	}

	@Override
	public void addAttribute(String str, Object obj) {
		if(str.equals("numOfWheels"))
			wheeled.setNumOfWheels((int) obj);
		
		else if(str.equals("color"))
			setColor((Color) obj);
	}

	@Override
	public Racer makeCopy() {
		
		Racer racerObject = null;
		
		try {
			
			racerObject = (Horse) super.clone();
			System.out.println(racerObject);
			//racerObject.setSerialNumber(Racer.lastSerialNumber++);
		} catch (CloneNotSupportedException e) {

		}
		
		return racerObject;
	}


}
