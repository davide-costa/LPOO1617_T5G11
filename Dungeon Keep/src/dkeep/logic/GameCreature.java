package dkeep.logic;

public class GameCreature
{
	protected Coords coords;
	protected char symbol;
	
	public GameCreature(int x, int y, char symbol)
	{
		coords = new Coords(x, y);
		this.symbol = symbol;
	}
	
	public int GetX()
	{
		return coords.GetX();
	}
	
	public void SetX(int x)
	{
		coords.SetX(x);
	}
	
	public int GetY()
	{
		return coords.GetY();
	}
	
	public void SetY(int y)
	{
		coords.SetY(y);
	}
	
	public Coords GetCoords()
	{
		return coords;
	}
	
	public void SetCoords(Coords coords)
	{
		this.coords.Set(coords);
	}
	
	public void SetCoords(int x, int y)
	{
		coords.Set(x, y);
	}
	
	public char GetSymbol()
	{
		return symbol;
	}
	
	public void SetSymbol(char symbol) 
	{
		this.symbol = symbol;
	}
}
