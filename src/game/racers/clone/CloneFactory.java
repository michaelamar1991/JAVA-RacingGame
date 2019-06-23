package game.racers.clone;

import game.racers.Racer;

public class CloneFactory {
	
	public Racer getClone(Racer racerSimple){
		
		return racerSimple.makeCopy();
	}

}
