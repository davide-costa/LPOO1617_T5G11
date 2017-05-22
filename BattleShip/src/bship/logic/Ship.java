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
		this.coords = new ArrayList<Coords>();
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
		return health == 0;
	}
	
	public void Destroy()
	{
		health = 0;
	}
	
	public void addCoord(Coords coord)
	{
		coords.add(coord);
	}

	public void cleanCoords() 
	{
		coords.clear();	
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		System.out.println(1);
		if (this == obj)
			return true;
		System.out.println(1);
		if (obj == null)
			return false;
		System.out.println(1);
		if (getClass() != obj.getClass())
			{System.out.println(2);
			return false;}
		System.out.println(3);
		Ship other = (Ship) obj;
		if (coords == null) {
			if (other.coords != null)
			{
				System.out.println(4);
				return false;}
			System.out.println(5);
		} else if (!coords.equals(other.coords))
		{System.out.println(6);
			return false;}
		System.out.println(1);
		if (direction == null) {
			System.out.println(1);
			if (other.direction != null)
				return false;
			
		} else if (!direction.equals(other.direction))
			return false;
		System.out.println(1);
		if (health != other.health)
			return false;
		System.out.println(1);
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		return true;
	}
}
