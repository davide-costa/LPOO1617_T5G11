package bship.logic;

import java.util.ArrayList;

public class BattleShip extends Ship 
{
	public BattleShip()
	{
		super(4, "BattleShip");
	}
	
	public BattleShip(ArrayList<Coords> coords, String direction) 
	{
		super(4, coords, direction, "BattleShip");
	}

}
