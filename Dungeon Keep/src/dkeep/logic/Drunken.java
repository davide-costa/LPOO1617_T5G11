package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Drunken extends Guard
{
	private int sleep_turns = 0;
	private int movement_increment = 1;
	public Drunken(int x, int y, char symbol)
	{
		super(x, y, 'G');
	}
	
	public void Move()
	{
		if (movement_step == 24)
			movement_step = 0;
		else if (movement_step == -1)
			movement_step = 23;
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, 6);
		
		if (symbol == 'g')
			if (sleep_turns == 2) //If the drunken is sleeping for two turns, wake him up and starting moving
			{
				symbol = 'G';
				if (randomNum % 2 == 1) //the probability of the drunken guard inverting movement direction is 1/2
					movement_increment = -movement_increment;
			}
			else
			{
				sleep_turns++;
				return;
			}
		else if (randomNum == 1) //put the drunken sleeping
		{
			symbol = 'g';
			sleep_turns = 0;
			return;
		}
		
		x = x_path[movement_step];
		y = y_path[movement_step];

		movement_step += movement_increment;
	}
}

