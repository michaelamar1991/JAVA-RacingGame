package builder;


public class CarsRaceEngineer {
	
	private CarsRaceBuilder carsRaceBuilder;

	public CarsRaceEngineer(CarsRaceBuilder carsRaceBuilder)
	{
		this.carsRaceBuilder = carsRaceBuilder;
	}
	
	public CarsRace getCarsRace()
	{
		return this.carsRaceBuilder.getCarsRace();
	}
	
	public void makeCarsRace(){
		this.carsRaceBuilder.buildRaceArena();
		this.carsRaceBuilder.buildRaceCar();
	}

}
