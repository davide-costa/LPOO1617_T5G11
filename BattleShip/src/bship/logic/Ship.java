package bship.logic;

import java.util.ArrayList;

public abstract class Ship
{
	protected int size;
	protected int health;
	Coords startPos;
	String direction;
	
	public Ship(int size, Coords startPos, String direction)
	{
		this.size = size;
		health = size;
		this.startPos = new Coords(startPos);
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
