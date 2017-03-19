package dkeep.logic;

import java.io.Serializable;

public abstract class Guard extends GameCreature implements Serializable
{
	protected int movement_step = 1;
	protected int x_path[] = {8,7,7,7,7,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8,8,8,8,8};
	protected int y_path[] = {1,1,2,3,4,5,5,5,5,5,5,5,6,6,6,6,6,6,6,6,5,4,3,2};
	
	
	public Guard(int x, int y)
	{
		super(x, y, 'G');
	}
	
	public abstract void Move();
	
}
