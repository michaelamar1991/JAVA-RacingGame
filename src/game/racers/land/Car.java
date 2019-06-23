package game.racers.land;

import javafx.beans.InvalidationListener;
import factory.RaceBuilder;
import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.air.Airplane;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Engine;

/**
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Car extends Racer implements ILandRacer {

	private static final String CLASS_NAME = "Car";

	private static final int DEFAULT_WHEELS = 4;
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 20;
	private static final Color DEFAULT_color = Color.RED;
	private String typeArena = "LandArena";
	private Engine engine;
	private Wheeled wheeled;

	public Car() {
		this(CLASS_NAME + " #" + lastSerialNumber, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		//RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
}

	public Car(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color)
	{
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		this.engine = Engine.FOURSTROKE;
		//RaceBuilder.getArena().getHt().put(getSerialNumber(), this);
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
		s += ", Engine Type: " + this.engine;

		return s;
	}

	@Override
	public String getTypeArena() {
		return typeArena;
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
			
			racerObject = (Car) super.clone();
			System.out.println(racerObject);
			//racerObject.setSerialNumber(Racer.lastSerialNumber++);
		} catch (CloneNotSupportedException e) {

		}
		
		return racerObject;
	}


	
	
}
