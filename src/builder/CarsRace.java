package builder;

import game.arenas.Arena;
import game.racers.land.Car;

public class CarsRace implements CarsRacePlan{

	private Arena raceArena;
	private Car raceCarRacer;
	
	@Override
	public void setArena(Arena arena) {

		raceArena = arena;
		
	}

	@Override
	public void setCarRacer(Car carRacer) {
		
		raceCarRacer = carRacer;
	}

	public Arena getRaceArena() {
		return raceArena;
	}

	public Car getRaceCarRacer() {
		return raceCarRacer;
	}

	
}
