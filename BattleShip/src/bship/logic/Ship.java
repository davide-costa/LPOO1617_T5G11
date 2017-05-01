package bship.logic;

import java.util.ArrayList;

public abstract class Ship
{
	protected int size;
	protected int health;
	ArrayList<Coords> coords;
	String direction;
	
	public Ship(int size, ArrayList<Coords> coords, String direction)
	{
		this.size = size;
		health = size;
		this.coords = coords;
		this.direction = new String(direction);
	}
	
	public void shoot()
	{
		health--;
	}
	
	public boolean isDestroyed()
	{
		return health == size;
	}
	
	public void getCoords(ArrayList<Coords> coords)
	{
		
	}
}
