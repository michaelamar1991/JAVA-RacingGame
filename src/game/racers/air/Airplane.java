package game.racers.air;

import javafx.beans.InvalidationListener;
import factory.RaceBuilder;
import game.arenas.Arena;
import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.land.ILandRacer;
import utilities.EnumContainer.Color;

/**
 * This Airplane class exyends Racer
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Airplane extends Racer implements IAerialRacer{
	private static final String CLASS_NAME = "Airplane";

	private static final int DEFAULT_WHEELS = 3;
	private static final double DEFAULT_MAX_SPEED = 885;
	private static final double DEFAULT_ACCELERATION = 100;
	private static final Color DEFAULT_color = Color.BLACK;
	private Wheeled wheeled;
	private String typeArena = "AerialArena";


	public String getTypeArena() {
		return typeArena;
	}

	public Airplane() {
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
	public Airplane(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color)
	{
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
		
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
		return this.wheeled.describeSpecific();
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
			
			racerObject = (Airplane) super.clone();
			System.out.println(racerObject);
			//racerObject.setSerialNumber(Racer.lastSerialNumber++);
		} catch (CloneNotSupportedException e) {

		}
		
		return racerObject;
	}



	

}
