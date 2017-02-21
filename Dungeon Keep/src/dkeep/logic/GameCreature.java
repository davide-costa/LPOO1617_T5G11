package dkeep.logic;

public class GameCreature
{
	protected int x;
	protected int y;
	protected char symbol;
	
	public GameCreature(int x, int y)
	{
		this.x = x;
		this.y = y;
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
	
	public char GetSymbol()
	{
		return symbol;
	}
	
	
	public void SetSymbol(char symbol) 
	{
		this.symbol = symbol;
	}
}
