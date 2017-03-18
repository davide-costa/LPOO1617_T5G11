package dkeep.logic;

import java.io.Serializable;

public class Hero extends GameCreature implements Serializable
{
	public Hero(int x, int y)
	{
		super(x, y, 'H');
	}
}
