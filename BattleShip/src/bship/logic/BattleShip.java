package bship.logic;

import java.util.ArrayList;

public class BattleShip extends Ship 
{
	private static final long serialVersionUID = -3176693110082996869L;

	public BattleShip()
	{
		super(4, "BattleShip");
	}
	
	public BattleShip(ArrayList<Coords> coords, String direction) 
	{
		super(4, coords, direction, "BattleShip");
	}

	@Override
	public Ship getCopy()
	{
		Ship newShip = new BattleShip(coords, direction);
		return newShip;
	}
}
