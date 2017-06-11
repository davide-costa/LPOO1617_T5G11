package bship.logic;

import java.util.ArrayList;

public class Carrier extends Ship
{
	private static final long serialVersionUID = -8011015906999712011L;

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