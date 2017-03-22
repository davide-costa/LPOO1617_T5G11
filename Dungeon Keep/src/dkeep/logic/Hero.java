package dkeep.logic;

import java.io.Serializable;

public class Hero extends GameCreature
{
	private static final long serialVersionUID = -8999856368923230928L;

	public Hero(int x, int y)
	{
		super(x, y, 'H');
	}
	
	public Hero(Coords coords)
	{
		super(coords, 'H');
	}
}
