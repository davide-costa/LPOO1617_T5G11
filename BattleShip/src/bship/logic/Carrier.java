package bship.logic;

import java.util.ArrayList;

public class Carrier extends Ship {

	public Carrier(ArrayList<Coords> coords,  String direction, String name) 
	{
		super(5, coords, direction, "Carrier");
	}

}
