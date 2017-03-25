package dkeep.logic;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Coords implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8723525205856284319L;
	private int x;
	private int y;
	
	public Coords(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Coords(Coords coords)
	{
		x = coords.x;
		y = coords.y;
	}
	
	public int GetX()
	{
		return x;
	}
	
	public void SetX(int x)
	{
		this.x = x;
	}
	
	public int GetY()
	{
		return y;
	}
	
	public void SetY(int y)
	{
		this.y = y;
	}
	
	public void Set(Coords coords)
	{
		x = coords.x;
		y = coords.y;
	}
	
	public void Set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object coords2)
	{
		return (this.x == ((Coords)coords2).x) && (this.y == ((Coords)coords2).y);
	}
	
	public boolean equals(int x, int y)
	{
		return (this.x == x) && (this.y == y);
	}
	
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
}
