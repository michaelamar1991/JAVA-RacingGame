package game.arenas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.Observable;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import utilities.EnumContainer;
import utilities.Point;

@SuppressWarnings("restriction")
/**
 * This RacePanel class create jpanel racer
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public abstract class Arena implements Observer{

	private static int MIN_Y_GAP = 50;
	private ArrayList<Racer> activeRacers;
	private ArrayList<Racer> compleatedRacers;
	
	private ArrayList<Racer> brokenRacers;
	private ArrayList<Racer> disabledRacers;
	private ArrayList<Racer> allRacers;


	private double length;
	private final int MAX_RACERS;
	private final double FRICTION;
	private BufferedImage image;
	private ExecutorService pool;
	private Hashtable<Integer, Racer> ht;

	/**
	 * 
	 * @param length
	 *            the x value for the finish line
	 * @param maxRacers
	 *            Maximum number of racers
	 * @param friction
	 *            Coefficient of friction
	 * 
	 */
	protected Arena(double length, int maxRacers, double friction) {
		this.length = length;
		this.MAX_RACERS = maxRacers;
		this.FRICTION = friction;
		this.activeRacers = new ArrayList<Racer>();
		this.compleatedRacers = new ArrayList<Racer>();
		this.brokenRacers = new ArrayList<Racer>();
		this.disabledRacers = new ArrayList<Racer>();
		this.allRacers = new ArrayList<Racer>();
		this.ht = new Hashtable<Integer, Racer>();
	}

	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {

		if (this.activeRacers.size() == this.MAX_RACERS) {
			newRacer.getCurrentLocation().setY(newRacer.getCurrentLocation().getX()- MIN_Y_GAP);
			throw new RacerLimitException(this.MAX_RACERS, newRacer.getSerialNumber());
		}
		else if(!this.getClass().getSimpleName().equals(newRacer.getTypeArena()) && newRacer.getWheeled().getNumOfWheels() == 0)
		{
			newRacer.getCurrentLocation().setY(newRacer.getCurrentLocation().getX()- MIN_Y_GAP);
			throw new RacerTypeException(newRacer.getTypeArena(), this.getClass().getSimpleName());
		}
		else{
			this.activeRacers.add(newRacer);
			this.allRacers.add(newRacer);
		}
	}
	
	public void startRace()
	{
		pool = Executors.newFixedThreadPool(activeRacers.size());
		
		for(Racer racer : getActiveRacers())
		{
			racer.introduce();
			pool.submit(racer);
		}
		
		
		
	}

	public Hashtable<Integer, Racer> getHt() {
		return ht;
	}
	
	public ArrayList<Racer> getActiveRacers() {
		return activeRacers;
	}

	public ArrayList<Racer> getCompleatedRacers() {
		return compleatedRacers;
	}

	public boolean hasActiveRacers() {
		return this.activeRacers.size() > 0;
	}

	public void initRace() {
		int y = 0;
		for (Racer racer : this.activeRacers) {
			Point s = new Point(0, y);
			Point f = new Point(this.length, y);
			racer.initRace(this, s, f);
			y += Arena.MIN_Y_GAP;
		}
	}

	public void showResults() {
		for (Racer r : this.compleatedRacers) {
			String s = "#" + this.compleatedRacers.indexOf(r) + " -> ";
			s += r.describeRacer();
			System.out.println(s);
		}
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public int getMAX_RACERS() {
		return MAX_RACERS;
	}

	public void setActiveRacers(ArrayList<Racer> activeRacers) {
		this.activeRacers = activeRacers;
	}

	public void setCompleatedRacers(ArrayList<Racer> compleatedRacers) {
		this.compleatedRacers = compleatedRacers;
	}

	public ArrayList<Racer> getBrokenRacers() {
		return brokenRacers;
	}

	public void setBrokenRacers(ArrayList<Racer> brokenRacers) {
		this.brokenRacers = brokenRacers;
	}

	public ArrayList<Racer> getDisabledRacers() {
		return disabledRacers;
	}

	public void setDisabledRacers(ArrayList<Racer> disabledRacers) {
		this.disabledRacers = disabledRacers;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	@SuppressWarnings("restriction")
	public void update (Observable racer, Object msg)
	{
		if (msg == EnumContainer.RacerEvent.DISABLED)
		{
			this.getActiveRacers().remove(racer);
			this.getDisabledRacers().add((Racer) racer);
			((Racer) racer).setCurrentSpeed(0);
			for (Racer r : getDisabledRacers())
			{
				try {
					r.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
		}
		else if (msg == EnumContainer.RacerEvent.BROKENDOWN)
		{
			this.getActiveRacers().remove(racer);
			this.getBrokenRacers().add((Racer) racer);
		}
		else if (msg == EnumContainer.RacerEvent.REPAIRED)
		{
			this.getActiveRacers().add((Racer) racer);
			this.getBrokenRacers().remove(racer);
		}
		else if (msg == EnumContainer.RacerEvent.FINISHED)
		{
			this.getActiveRacers().remove(racer);
			this.getCompleatedRacers().add((Racer) racer);
		}
	}
	
	public static int getMinYGap() {
		return MIN_Y_GAP;
	}
	
	public static void setMIN_Y_GAP(int mIN_Y_GAP) {
		MIN_Y_GAP = mIN_Y_GAP;
	}

	public double getFRICTION() {
		return FRICTION;
	}

	public ExecutorService getPool() {
		return pool;
	}
	
	public ArrayList<Racer> getAllRacers() {
		return allRacers;
	}

	public void setAllRacers(ArrayList<Racer> allRacers) {
		this.allRacers = allRacers;
	}
	
}
