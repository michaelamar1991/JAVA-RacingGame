package game.racers.sea;

import javafx.beans.InvalidationListener;
import factory.RaceBuilder;
import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.air.Airplane;
import utilities.EnumContainer;
import utilities.EnumContainer.BoatType;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Team;

/**
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class RowBoat extends Racer implements INavalRacer {
	private static final String CLASS_NAME = "RowBoat";

	private static final int DEFAULT_WHEELS = 0;
	private static final double DEFAULT_MAX_SPEED = 75;
	private static final double DEFAULT_ACCELERATION = 10;
	private static final Color DEFAULT_color = Color.RED;
	private String typeArena = "NavalArena";
	private Wheeled wheeled;


	public String getTypeArena() {
		return typeArena;
	}

	private EnumContainer.BoatType type;
	private EnumContainer.Team team;

	public RowBoat() {
		this(CLASS_NAME + " #" + lastSerialNumber, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		RaceBuilder.getArena().getHt().put(getSerialNumber(), this);

	}

	public RowBoat(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		super(name, maxSpeed, acceleration, color);
		this.type = BoatType.SKULLING;
		this.team = Team.SINGLE;
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
		String s = "";
		s += ", Type: " + this.type;
		s += ", Team: " + this.team;

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
			
			racerObject = (RowBoat) super.clone();
			System.out.println(racerObject);
			//racerObject.setSerialNumber(Racer.lastSerialNumber++);
		} catch (CloneNotSupportedException e) {

		}
		
		return racerObject;
	}



}
