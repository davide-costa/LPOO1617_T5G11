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
		x = coords.y;
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
		x = coords.y;
	}
	
	public void Set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
