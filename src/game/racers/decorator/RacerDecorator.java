package game.racers.decorator;

public abstract class RacerDecorator extends IRacer{
	
	private IRacer decoratedRacer;
	
	public RacerDecorator(IRacer newDecoratedRacer)
	{
		//System.out.println("in RacerDecorator C'tor");
		decoratedRacer = newDecoratedRacer;
		
		
	}

}
