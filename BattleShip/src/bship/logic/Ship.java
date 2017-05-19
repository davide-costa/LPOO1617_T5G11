package bship.logic;

import java.util.ArrayList;

public abstract class Ship
{
	protected int size;
	protected int health;
	protected ArrayList<Coords> coords;
	protected String direction;
	protected String name;
	
	public Ship(int size, String name)
	{
		this.size = size;
		health = size;
		this.name = name;
	}
	
	public Ship(int size, ArrayList<Coords> coords, String direction, String name)
	{
		this.size = size;
		health = size;
		this.coords = coords;
		this.direction = new String(direction);
		this.name = new String(name);
	}
	
	public ArrayList<Coords> getCoords()
	{
		return coords;
	}
	
	public int getSize()
	{
		return size;
	}

	public String getDirection()
	{
		return direction;
	}
	
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	
	public String getName()
	{
		return name;
	}

	public void shoot()
	{
		health--;
	}

	public boolean isDestroyed()
	{
		return health == size;
	}
	
	public void Destroy()
	{
		health = 0;
	}
	
	public void addCoord(Coords coord)
	{
		coords.add(coord);
	}
}