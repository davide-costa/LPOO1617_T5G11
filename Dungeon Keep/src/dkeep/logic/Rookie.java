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

		if (movement_step == 0 || (movement_step >= 5 && movement_step < 11)) //for left moves
			x--;
		else if (movement_step >= 12 && movement_step <= 18) //for right moves
			x++;
		else if (movement_step >= 19 && movement_step <= 23) //for up moves
			y--;
		else if ((movement_step >= 1 && movement_step <= 4) || movement_step == 11) //for down moves
			y++;

		movement_step++;
	}
}
