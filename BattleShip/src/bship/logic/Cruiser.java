package bship.logic;

import java.util.ArrayList;

public class Cruiser extends Ship 
{

	public Cruiser()
	{
		super(3, "Cruiser");
	}
	public Cruiser(ArrayList<Coords> coords,  String direction)
	{
		super(3, coords, direction, "Cruiser");
	}
	@Override
	public Ship getCopy()
	{
		Ship newShip = new Cruiser(coords, direction);
		return newShip;
	}

}
