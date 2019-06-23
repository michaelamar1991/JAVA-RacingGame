package factory;

import game.arenas.Arena;
import game.arenas.air.AerialArena;
import game.arenas.land.LandArena;
import game.arenas.sea.NavalArena;

public class ArenaFactory {
	
	public Arena makeArena(String newArenaType, double length, int maxRacers)
	{
		
		if(newArenaType.equals("AerialArena"))
			return new AerialArena(length,maxRacers);
		
		else if(newArenaType.equals("LandArena"))
			return new LandArena(length,maxRacers);
		
		else if(newArenaType.equals("NavalArena"))
			return new NavalArena(length,maxRacers);
		else
			return null;
	}

}
