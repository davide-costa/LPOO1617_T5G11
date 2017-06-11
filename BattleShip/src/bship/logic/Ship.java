package bship.logic;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents a Ship. Stores all the information necessary to represent a Ship in the game. Implements all the logic necessary to operate over a Ship during a game.
 */
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
	
	/**
	 * Returns a new separate (unlinked) copy of this ship. Used by the logic package and tests package (just for testing if it performs well).
	 * @return A new separate (unlinked) copy of this ship.
	 */
	public abstract Ship getCopy();
	
	/**
	 * Returns the and ArrayList of coords that represents the coords where the ship is placed on the map.
	 * @return An ArrayList of Coords that represents the coords where the ship is placed on the map.
	 */
	public ArrayList<Coords> getCoords()
	{
		return coords;
	}
	
	/**
	 * Returns the initial coords where the ship is placed on the map, i. e., the coords of the first cell that belongs to this Ship.
	 * @return A Coords class that respect to the map cell where the Ship is placed.
	 */
	public Coords getInitCoords()
	{
		return coords.get(0);
	}
	
	/**
	 * Returns the size of the ship, i. e., how much cells it is occupying on the map.
	 * @return An integer representing how much cells belong to this Ship.
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Returns the facing direction of the Ship. It can either be "horizontal" or "vertical".
	 * @return A String representing the current facing direction of the Ship. The possible values are "horizontal" and "vertical".
	 */
	public String getDirection()
	{
		return direction;
	}
	
	/**
	 * Returns the name of the Ship.
	 * @return A String representing the name of the Ship.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the facing direction of the Ship.
	 * @param direction The Ship's new facing direction.
	 */
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	
	/**
	 * Sets the coords of the Ship, i. e., the ArrayList of coords where it is placed on the map to be equal to the ArrayList of coords it receives as parameter.
	 * @param direction The Ship's new facing direction.
	 */
	public void setCoords(ArrayList<Coords> coords)
	{
		this.coords = coords;
	}

	public void shoot()
	{
		health--;
	}

	/**
	 * Tells if the Ship is destroyed, i. e., if all its cells have been hit.
	 * @return A boolean value indicating weather the Ship is currently destroyed or not.
	 */
	public boolean isDestroyed()
	{
		return health == 0;
	}
	
	/**
	 * Forces the full destruction of a Ship by setting its health equal to zero.
	 */
	public void Destroy()
	{
		health = 0;
	}
	
	/**
	 * Returns the current health of the ship, i. e., the number of cells that haven't still been hit. The value is zero if it is destroyed. It is equal to its size if it has still not been hit.
	 * @return An int value representing the current health of the ship, i. e., the number of cells that haven't still been hit.
	 */
	public int getHealth()
	{
		return health;
	}
	
	/**
	 * Adds a coord to the ArrayList of coords of the ship, i. e., the coords it is occupying on the map.
	 * @param coord The new coords to the added to the ArrayList of Coords that represents a new coords that the ship is occupying on the map.
	 */
	public void addCoord(Coords coord)
	{
		coords.add(coord);
	}

	public void clearCoords() 
	{
		coords.clear();	
	}
	
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
