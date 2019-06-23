package game.racers.air;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.beans.InvalidationListener;
import factory.RaceBuilder;
import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer.Color;

/**
 * This Helicopter class
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Helicopter extends Racer implements IAerialRacer {
	private static final String CLASS_NAME = "Helicopter";

	private static final int DEFAULT_WHEELS = 0;
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 50;
	private static final Color DEFAULT_color = Color.BLUE;
	private Wheeled wheeled;
	private String typeArena = "AerialArena";

	public Helicopter() {
		this(CLASS_NAME + " #" + lastSerialNumber, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
}

	/**
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 */
	public Helicopter(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
}

	@Override
	public String className() {
		return CLASS_NAME;
	}

	public Wheeled getWheeled() {
		return wheeled;
	}
	
	@Override
	public String describeSpecific() {
		return "";
	}
	
	public String getTypeArena() {
		return typeArena;
	}

	public static int getDefaultWheels() {
		return DEFAULT_WHEELS;
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
			
			racerObject = (Helicopter) super.clone();
			System.out.println(racerObject);
			//racerObject.setSerialNumber(Racer.lastSerialNumber++);
		} catch (CloneNotSupportedException e) {

		}
		
		return racerObject;
	}









	
	
	
	
}
