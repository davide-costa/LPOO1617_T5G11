package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Suspicious extends Guard
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6323767492698851852L;
	private int movement_increment = 1;
	public Suspicious(int x, int y)
	{
		super(x, y);
	}
	
	public void Move()
	{
		if (movement_step == 24)
			movement_step = 0;
		else if (movement_step == -1)
			movement_step = 23;
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, 6);
		
		if (randomNum == 1) //change movement direction
			movement_increment = -movement_increment;
		
		SetCoords(x_path[movement_step], y_path[movement_step]);

		movement_step += movement_increment;
	}
}

