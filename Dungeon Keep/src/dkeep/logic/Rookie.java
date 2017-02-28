package dkeep.logic;

public class Rookie extends Guard
{	
	public Rookie(int x, int y, char symbol)
	{
		super(x, y, 'G');
	}
	
	public void Move()
	{
		if (movement_step == 24)
			movement_step = 0;

		x = x_path[movement_step];
		y = y_path[movement_step];
		
		movement_step++;
	}
}
