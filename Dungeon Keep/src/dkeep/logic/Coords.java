package dkeep.logic;

public class Coords
{
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
	
	public Coords left()
	{
		return new Coords(x - 1, y);
	}
	
	public Coords right()
	{
		return new Coords(x + 1, y);
	}
	
	public Coords up()
	{
		return new Coords(x, y - 1);
	}
	
	public Coords down()
	{
		return new Coords(x, y + 1);
	}
	
}
