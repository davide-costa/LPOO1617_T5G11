package bship.logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Coords implements Serializable, Comparable<Coords>
{
	private static final long serialVersionUID = 8723525205856284319L;
	private int x;
	private int y;
	
	/**  
	 * Constructor of Coords class. Creates a new instance of Coords from the values of abcissa and ordinate of a point.
     * @param x The x (i. e. the abcissa) coord of the point.
	 * @param y The y (i. e. the ordinate) coord of the point.
	 */ 
	public Coords(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	/**  
	 * Copy constructor of Coords class. Creates a new instance of Coords from another instance of Coords. Creates a new separate copy, no aliasing will ocurr because they are not the same object.
     * @param coords The instance of coords to be copied.
	 */ 
	public Coords(Coords coords)
	{
		x = coords.x;
		y = coords.y;
	}
	/**  
	 * Constructor of Coords class. Creates a new instance of Coords from an instance of Point.
     * @param point The point that will fill the coords.
	 */ 
	public Coords(Point point)
	{
		x = point.x;
		y = point.y;
	}
	/**  
	 * Returns the x (i. e. the abcissa) coord of the point
     * @return The x (i. e. the abcissa) coord.
	 */ 
	public int GetX()
	{
		return x;
	}
	/**  
	 * Sets the x (i. e. the abcissa) coord of the point
     * @param x The new value to be assigned to the x (i. e. the abcissa) coord. 
	 */  
	public void SetX(int x)
	{
		this.x = x;
	}
	/**  
	 * Returns the y (i. e. the ordinate) coord of the point
     * @return The y (i. e. the ordinate) coord.
	 */ 
	public int GetY()
	{
		return y;
	}
	/**  
	 * Sets the y (i. e. the ordinate) coord of the point
     * @param y The new value to be assigned to the y (i. e. the ordinate) coord. 
	 */ 
	public void SetY(int y)
	{
		this.y = y;
	}
	/**  
	 * Sets the coords of this instance of Coords  (i. e. the x and y coords (abcissa and ordinate)) to be equal to the Coords object it receives as parameter. (It does not link both instances, creates new values of coords, no aliasing will ocurr because they are not the same object.)
     * @param coords The new coords to be assigned to this Coords object.
	 */ 
	public void Set(Coords coords)
	{
		x = coords.x;
		y = coords.y;
	}
	/**  
	 * Sets the coords of this instance of Coords  (i. e. the x and y coords (abcissa and ordinate)) to be equal to the x and y coords it receives as parameter.
     * @param x The new value to be assigned to the x (i. e. the abcissa) coord.
      * @param y The new value to be assigned to the y (i. e. the ordinate) coord.
	 */ 
	public void Set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**  
	 * Increments x coord of this instance by the increment it receives as parameter.
     * @param increment The increment in x.
	 */ 
	public void incrementX(int increment)
	{
		this.x += increment;
	}
	
	/**  
	 * Increments y coord of this instance by the increment it receives as parameter.
     * @param increment The increment in y.
	 */ 
	public void incrementY(int increment)
	{
		this.y += increment;
	}
	
	/**  
	 * Compares two Coords objects. They are equal if they respect to the same coord in 2D space (if the x and y coords are the same).
     * @param coords2 The other Coords object to compare to this object.
     * @return true if the two coords are equal, false if not.
	 */ 
	@Override
	public boolean equals(Object coords2)
	{
		return (this.x == ((Coords)coords2).x) && (this.y == ((Coords)coords2).y);
	}
	/**
	 * Compares two coords (this object and the coords it receives as parameter). They are equal if they respect to the same coord in 2D space (if the x and y coords are the same).
	 * @param x The other x (i. e. the abcissa) coord to compare to this obejct.
	 * @param y The other y (i. e. the ordinate) coord to compare to this obejct.
	 * @return true if the two coords are equal, false if not.
	 */
	public boolean equals(int x, int y)
	{
		return (this.x == x) && (this.y == y);
	}
	/**  
	 * Verifies if two Coords objects are adjacent (respect to adjacent points in the 2D space). They are adjacent if they respect to points immediately at the left, right, up or bottom of each other.
     * @param coords2 The other Coords object to compare to this object.
     * @return true if the two coords are adjacent, false if not.
	 */
	public boolean adjacentTo(Coords coords2)
	{
		int cell1_x = this.GetX();
		int cell1_y = this.GetY();
		int cell2_x = coords2.GetX();
		int cell2_y = coords2.GetY();
		
		if (cell1_x == (cell2_x - 1) && cell1_y == cell2_y)
			return true;
		else if (cell1_x == (cell2_x + 1) && cell1_y == cell2_y)
			return true;
		else if (cell1_x == cell2_x && cell1_y == (cell2_y - 1))
			return true;
		else if (cell1_x == cell2_x && cell1_y == (cell2_y + 1))
			return true;
		else
			return false;
	}
	/**  
	 * Verifies if two coords objects are adjacent (this object and the coords it receives as parameter) (it verifies if they respect to adjacent points in the 2D space). They are adjacent if they respect to points immediately at the left, right, up or bottom of each other.
     * @param x The other x (i. e. the abcissa) coord to compare to this obejct.
	 * @param y The other y (i. e. the ordinate) coord to compare to this obejct.
     * @return true if the two coords are adjacent, false if not.
	 */
	public boolean adjacentTo(int x, int y)
	{
		return adjacentTo(new Coords(x, y));
	}
	
	/**  
	 * Returns the coords of the left adjacent cell of this instance of coords.
     * @return coords of the left adjacent cell.
	 */
	public Coords left()
	{
		return new Coords(x - 1, y);
	}
	
	/**  
	 * Returns the coords of the right adjacent cell of this instance of coords.
     * @return coords of the left adjacent cell.
	 */
	public Coords right()
	{
		return new Coords(x + 1, y);
	}
	
	/**  
	 * Returns the coords of the upper adjacent cell of this instance of coords.
     * @return coords of the left adjacent cell.
	 */
	public Coords up()
	{
		return new Coords(x, y - 1);
	}
	
	/**  
	 * Returns the coords of the bottom adjacent cell of this instance of coords.
     * @return coords of the left adjacent cell.
	 */
	public Coords down()
	{
		return new Coords(x, y + 1);
	}
	/**  
	 * Generates a random cell (or point) in the 2D space that is adjacent to the coords that this object represents. Being adjacent means it the new object respects to a point immediately at the left, right, up or bottom of each other. Creates a new separate copy, no aliasing will ocurr because they are not the same object.
     * @return A new (random) Coords object that is adjacent to the this object.
	 */
	public Coords randomAdjacentCell()
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		int temp_x = GetX();
		int temp_y = GetY();
		
		if (randomNum == 1)
			temp_x++;
		else if (randomNum == 2)
			temp_x--;
		else if (randomNum == 3)
			temp_y++;
		else if (randomNum == 4)
			temp_y--;
		
		return new Coords(temp_x, temp_y);
	}
	
	static public ArrayList<Coords> getSurroundingCoords(ArrayList<Coords> coordsArray)
	{
		ArrayList<Coords> surroundingCoords = new ArrayList<Coords>();
		int coordsArraySize = coordsArray.size();
		if(coordsArraySize == 0)
			return surroundingCoords;
		
		int xIncrement;
		int yIncrement;
		
		if(coordsArraySize == 1)
		{
			xIncrement = 1;
			yIncrement = 0;
		}
		else
		{
			yIncrement = coordsArray.get(1).GetX() - coordsArray.get(0).GetX();
			xIncrement = coordsArray.get(1).GetY() - coordsArray.get(0).GetY();
		}
		
		for(int i = 0; i < coordsArraySize; i++)
			getSurroundingCoordsAux(coordsArray.get(i), xIncrement, yIncrement, surroundingCoords);

		Coords coords = new Coords(coordsArray.get(0));
		coords.incrementX(-yIncrement);
		coords.incrementY(-xIncrement);
		surroundingCoords.add(coords);
		getSurroundingCoordsAux(coords, xIncrement, yIncrement, surroundingCoords);
		
		coords = new Coords(coordsArray.get(coordsArraySize - 1));
		coords.incrementX(yIncrement);
		coords.incrementY(xIncrement);
		surroundingCoords.add(coords);
		getSurroundingCoordsAux(coords, xIncrement, yIncrement, surroundingCoords);
		
		return surroundingCoords;
	}
	
	static public void getSurroundingCoordsAux(Coords coords, int xIncrement, int yIncrement, ArrayList<Coords> surroundingCoords)
	{
		Coords tempCoords;
		
		tempCoords = new Coords(coords);
		tempCoords.incrementX(xIncrement);
		tempCoords.incrementY(yIncrement);
		surroundingCoords.add(tempCoords);
		
		tempCoords = new Coords(coords);
		tempCoords.incrementX(-xIncrement);
		tempCoords.incrementY(-yIncrement);
		surroundingCoords.add(tempCoords);
	}

	@Override
	public int compareTo(Coords c2)
	{
		if (y != c2.y)
		{
			if (y < c2.y)
				return -1;
			else
				return 1;
		}
		
		if (x < c2.x)
			return -1;
		else if(x > c2.x)
			return 1;
		else
			return 0;
	}
}