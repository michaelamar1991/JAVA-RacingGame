package game.racers.decorator;

import utilities.EnumContainer.Color;

public class ColoredRacer extends RacerDecorator{

	private static final String ATTRIBUTENAME = "color";
	
	public ColoredRacer(IRacer ir , Color color)
	{
		super(ir);
		ir.addAttribute(ATTRIBUTENAME, color);
		
	}

	@Override
	public void addAttribute(String str, Object obj) {
		// TODO Auto-generated method stub
		System.out.println("int ColoredRacer class!!");
	}

	

}
