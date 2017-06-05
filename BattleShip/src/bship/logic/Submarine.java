package bship.logic;

import java.util.ArrayList;

public class Submarine extends Ship
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1197241785301391714L;

	public Submarine()
	{
		super(3, "Submarine");
	}
	
	public Submarine(ArrayList<Coords> coords,  String direction)
	{
		super(3, coords, direction, "Submarine"); 
	}
	
	@Override
	public Ship getCopy()
	{
		Ship newShip = new Cruiser(coords, direction);
		return newShip;
	}
}
