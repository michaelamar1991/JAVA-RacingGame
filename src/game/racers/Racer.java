package game.racers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.omg.CORBA.INTERNAL;

import java.util.Hashtable;
import java.util.Observer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
//import java.util.Observable;

import factory.RaceBuilder;
import factory.RacePanel;
import game.arenas.Arena;
import game.racers.decorator.IRacer;
import utilities.EnumContainer;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.RacerEvent;
import utilities.Fate;
import utilities.Mishap;
import utilities.Point;

@SuppressWarnings("restriction")
/**
 * This Racer class
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public abstract class Racer extends IRacer implements Observable, Runnable, Cloneable{
	protected static int lastSerialNumber = 1;
	private static int last_cur_x=0,last_cur_y=-Arena.getMinYGap();
	
	private int serialNumber; // Each racer has an unique number, assigned by arena in addRacer() method
	private String name;
	private Point currentLocation;
	private double fakeLocation;
	private Point finish;
	private Arena arena;
	private double maxSpeed;
	private double acceleration;
	private double currentSpeed;
	private double failureProbability = 0.05; // Chance to break down
	private EnumContainer.Color color; // (RED,GREEN,BLUE,BLACK,YELLOW)
	private Mishap mishap;
	private int width, height;
	private BufferedImage image;
	private static int SIZE_IMAGE = 50;
	

	/**
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 */
	public Racer(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		this.serialNumber = Racer.lastSerialNumber++;
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.color = color;
		this.width = SIZE_IMAGE;
		this.height = SIZE_IMAGE;
		this.currentLocation = new Point(last_cur_x,last_cur_y += Arena.getMinYGap());
		
		
	}
	
	public abstract void addAttribute(String name,Object obj);
	
	public abstract String className();
	
	public abstract Racer makeCopy();
	
	public EnumContainer.Color getColor() {
		return color;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String describeRacer() {
		String s = "";
		s += "name: " + this.name + ", ";
		s += "SerialNumber: " + this.serialNumber + ", ";
		s += "maxSpeed: " + this.maxSpeed + ", ";
		s += "acceleration: " + this.acceleration + ", ";
		s += "color: " + this.color + ", ";
		s = s.substring(0, s.length() - 2);
		// returns a string representation of the racer, including: general attributes
		// (color name, number) and specific ones (numberOfWheels, etc.)
		s += this.describeSpecific();
		return s;
	}

	public abstract String describeSpecific();

	public int getSerialNumber() {
		return serialNumber;
	}

	private boolean hasMishap() {
		if (this.mishap != null && this.mishap.getTurnsToFix() == 0)
			this.mishap = null;
		return this.mishap != null;
	}

	public void initRace(Arena arena, Point start, Point finish) {
		this.arena = arena;
		this.currentLocation = new Point(start);
		this.finish = new Point(finish);
	}

	public void introduce() {
		// Prints a line, obtained from describeRacer(), with its type
		System.out.println("[" + this.className() + "] " + this.describeRacer());
	}

	public Point move(double friction) {

		RaceBuilder.getRacePanel().loadRacer(RaceBuilder.getArena(),this,RaceBuilder.getPath() ,(String)RaceBuilder.getRacerCombo().getSelectedItem(), (String)RaceBuilder.getColorCombo().getSelectedItem());
		
		double reductionFactor = 1;
		if (!(this.hasMishap()) && Fate.breakDown(failureProbability)) {
			this.mishap = Fate.generateMishap();			
			if (!this.mishap.isFixable())
			{
				this.notifyObservers(EnumContainer.RacerEvent.DISABLED);
			}
			
			if (this.mishap.isFixable())
			{
				this.notifyObservers(EnumContainer.RacerEvent.BROKENDOWN);
			}
				
		}

		if (this.hasMishap()) {
			reductionFactor = mishap.getReductionFactor();
			this.mishap.nextTurn();
			
			if (this.mishap.getTurnsToFix() == 0)
				this.notifyObservers(EnumContainer.RacerEvent.REPAIRED);
		}
		
		if (this.currentSpeed < this.maxSpeed) {
			this.currentSpeed += this.acceleration * friction * reductionFactor;
		}
		
		if (this.currentSpeed > this.maxSpeed) {
			this.currentSpeed = this.maxSpeed;
		}
		double newX = ((this.currentLocation.getX() + (this.currentSpeed)));
		
		this.fakeLocation = ((this.currentLocation.getX() + this.currentSpeed)) * (RaceBuilder.getRacePanel().getWidth()/arena.getLength());

		if(fakeLocation >= RaceBuilder.getRacePanel().getWidth() - getSIZE_IMAGE())
		{
			fakeLocation = RaceBuilder.getRacePanel().getWidth() - getSIZE_IMAGE();
			setCurrentSpeed(0);
			
			newX = arena.getLength();
			this.notifyObservers(EnumContainer.RacerEvent.FINISHED);
		}
			
		
		
		Point newLocation = new Point(newX, this.currentLocation.getY());
		setCurrentLocation(newLocation);
		return this.currentLocation;

	}

	private void notifyObservers(RacerEvent arg) {
		arena.update((javafx.beans.Observable) this, arg);
		
	}

	public void drawObject(Graphics g)
	{
		g.drawImage(getImage(), (int)fakeLocation, (int)getCurrentLocation().getY(),getWidth(),getHeight(), null); // see javadoc for more info on the parameters
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public static void initCur()
	{
		last_cur_x=0;
		last_cur_y=-Arena.getMinYGap(); 
	}

	public abstract String getTypeArena();

	public static int getSIZE_IMAGE() {
		return SIZE_IMAGE;
	}

	public static void setSIZE_IMAGE( int size) {
		SIZE_IMAGE = size;
	}
	
	public static int getLast_cur_x() {
		return last_cur_x;
	}

	public static void setLast_cur_x(int last_cur_x) {
		Racer.last_cur_x = last_cur_x;
	}

	public static int getLast_cur_y() {
		return last_cur_y;
	}

	public static void setLast_cur_y(int last_cur_y) {
		Racer.last_cur_y = last_cur_y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Set current location of racer
	 * @param currentLocation
	 */
	public boolean setCurrentLocation(Point currentLocation) {
		if (currentLocation instanceof Point)
		{
		this.currentLocation = new Point(currentLocation.getX(), currentLocation.getY());
		return true;
		}
		else
			return false;
	}

	



	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setColor(EnumContainer.Color color) {
		this.color = color;
	}


	public double getFakeLocation() {
		return fakeLocation;
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	@Override
	public void addListener(InvalidationListener arg0) {
		System.out.println("Racer.addListener(...) method <- \n\n");
		
	}

	
	public Arena getArena() {
		return arena;
	}

	
	
	public static int getLastSerialNumber() {
		return lastSerialNumber;
	}

	public static void setLastSerialNumber(int lastSerialNumber) {
		Racer.lastSerialNumber = lastSerialNumber;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
		System.out.println("Racer.removeListener(...) method <- \n\n");		
	}

	@Override
	synchronized public void run() {
		while(this.fakeLocation < RaceBuilder.getRacePanel().getWidth()-getSIZE_IMAGE())
		{
			move(arena.getFRICTION());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}			
	}

	public abstract Wheeled getWheeled();
}
