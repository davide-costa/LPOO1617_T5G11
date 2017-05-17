package bship.logic;

import java.util.ArrayList;

public class Submarine extends Ship 
{

	public Submarine()
	{
		super(3, "Submarine");
	}
	public Submarine(ArrayList<Coords> coords,  String direction)
	{
		super(3, coords, direction, "Submarine");
	}

}
