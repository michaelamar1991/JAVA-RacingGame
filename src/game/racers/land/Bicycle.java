package game.racers.land;

import javafx.beans.InvalidationListener;
import factory.RaceBuilder;
import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.air.Airplane;
import utilities.EnumContainer;
import utilities.EnumContainer.BicycleType;
import utilities.EnumContainer.Color;

/**
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Bicycle extends Racer implements ILandRacer {

	private static final String CLASS_NAME = "Bicycle";
	private static final String DEFUALT_NAME = CLASS_NAME + " #" + lastSerialNumber;

	private static final int DEFAULT_WHEELS = 2;
	private static final double DEFAULT_MAX_SPEED = 270;
	private static final double DEFAULT_ACCELERATION = 10;
	private static final Color DEFAULT_color = Color.GREEN;
	private String typeArena = "LandArena";
	private EnumContainer.BicycleType type;
	private Wheeled wheeled;

	public String getTypeArena() {
		return typeArena;
	}



	public Bicycle() {
		this(DEFUALT_NAME, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
	}

	public Bicycle(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color)
	{
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		this.type = BicycleType.MOUNTAIN;
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
		String s = "";
		s += this.wheeled.describeSpecific();
		s += ", Bicycle Type: " + this.type;
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
			
			racerObject = (Bicycle) super.clone();
			System.out.println(racerObject);
			//racerObject.setSerialNumber(Racer.lastSerialNumber++);
		} catch (CloneNotSupportedException e) {

		}
		
		return racerObject;
	}



}
