package bship.logic;

import java.util.ArrayList;

public class Carrier extends Ship
{
	public Carrier()
	{
		super(5, "Carrier");
	}
	
	public Carrier(ArrayList<Coords> coords,  String direction) 
	{
		super(5, coords, direction, "Carrier");
	}

	@Override
	public Ship getCopy()
	{
		Ship newShip = new Carrier(coords, direction);
		return newShip;
	}

}
