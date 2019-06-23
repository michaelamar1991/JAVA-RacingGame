package game.racers.decorator;

import utilities.EnumContainer.Color;

public class WheeledRacer extends RacerDecorator{

	private static final String ATTRIBUTENAME = "numOfWheels";
	
	public WheeledRacer(IRacer ir , int wheelsAmount)
	{
					super(ir);
					System.out.println("in WheeledRacer C'tor!!");
					ir.addAttribute(ATTRIBUTENAME, wheelsAmount);
		
	}

	@Override
	public void addAttribute(String str, Object obj) {
		System.out.println("int WheeledRacer class!!");

	}


}
