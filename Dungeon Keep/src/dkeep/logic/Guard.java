package dkeep.logic;

public class Guard extends GameCreature
{
	private int movement_step = 0;
	
	public Guard(int x, int y, char symbol)
	{
		super(x, y, 'G');
	}
}
