package builder;

import factory.RaceBuilder;
import game.arenas.Arena;
import game.arenas.land.LandArena;
import game.racers.Racer;
import game.racers.land.Car;

public class OldCarsRaceBuilder implements CarsRaceBuilder{

	private CarsRace carsRace;
	
	public OldCarsRaceBuilder(){
		
		this.carsRace = new CarsRace();
	}
	
	@Override
	public void buildRaceArena() {
		
		carsRace.setArena(new LandArena());
		
	
	}

	@Override
	public void buildRaceCar() {
		
		carsRace.setCarRacer(new Car());
	}

	@Override
	public CarsRace getCarsRace() {
		return carsRace;
	}

}
