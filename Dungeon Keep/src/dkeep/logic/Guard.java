package dkeep.logic;

public abstract class Guard extends GameCreature
{
	protected int movement_step = 0;
	protected int x_path[] = {1};
	protected int y_path[] = {2};
	
	
	public Guard(int x, int y, char symbol)
	{
		super(x, y, 'G');
	}
	
	public abstract void Move();
	
}
