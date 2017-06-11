package bship.logic;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Ship implements Serializable
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
		this.direction = new String();
	}
	
	public Ship(int size, ArrayList<Coords> coords, String direction, String name)
	{
		this.size = size;
		health = size;
		this.coords = coords;
		this.direction = new String(direction);
		this.name = new String(name);
	}
	
	public abstract Ship getCopy();
	
	public ArrayList<Coords> getCoords()
	{
		return coords;
	}
	
	public Coords getInitCoords()
	{
		return coords.get(0);
	}
	
	public int getSize()
	{
		return size;
	}

	public String getDirection()
	{
		return direction;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	
	public void setCoords(ArrayList<Coords> coords)
	{
		this.coords = coords;
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
	
	public int getHealth()
	{
		return health;
	}
	
	public void addCoord(Coords coord)
	{
		coords.add(coord);
	}

	/**  
	 * Make ship coords empty.
	 */
	public void clearCoords() 
	{
		coords.clear();	
	}
	
	/**  
	 * Fills the ship coords given the init coord and knowing the direction of the ship
	 * @param initCoord The ship init coord.
	 */
	public void fillCoordsByInitCoord(Coords initCoord)
	{
		int xInc = 0, yInc = 0;
		
		if(direction == "vertical")
			yInc = 1;
		else if(direction == "horizontal")
			xInc = 1;
		else
			throw new IllegalArgumentException();
		
		coords.clear();
		int currX = initCoord.GetX();
		int currY = initCoord.GetY();
		coords.add(initCoord);
		for(int i = 0; i < size - 1; i++)
		{
			coords.add(new Coords(currX + xInc, currY + yInc));
			currX += xInc;
			currY += yInc;
		}
	}
	
	/**  
	 * Checks if the this ship is equal to the ship passed by argument.
	 * @param obj The ship against the this will be compared.
	 * @return true if equals. False  otherwise.
	 */ 
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Ship other = (Ship) obj;
		if (coords == null)
			if (other.coords != null)
				return false;
		else if (!coords.equals(other.coords))
			return false;
			
		if (direction == null) 
			if (other.direction != null)
				return false;
		else if (!direction.equals(other.direction))
			return false;
	
		if (health != other.health)
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} 
		else if (!name.equals(other.name))
			return false;
		
		if (size != other.size)
			return false;
		return true;
	}
}
