package dkeep.logic;

public class Rookie extends Guard
{	
	public Rookie(int x, int y)
	{
		super(x, y);
	}
	
	public void Move()
	{
		if (movement_step == 24)
			movement_step = 0;

		SetCoords(x_path[movement_step], y_path[movement_step]);
		
		movement_step++;
	}
}
