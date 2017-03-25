package dkeep.logic;

import java.io.Serializable;

public class GameCreature implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5210983083815627858L;
	protected Coords coords;
	protected char symbol;
	
	public GameCreature(int x, int y, char symbol)
	{
		coords = new Coords(x, y);
		this.symbol = symbol;
	}
	
	public GameCreature(Coords coords, char symbol)
	{
		this.coords = new Coords(coords);
		this.symbol = symbol;
	}
	//TODO: consider removing this
	/*
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
	*/
	/**
	 * Function that returns the coords of the game creature on the map.
	 * @return the coords of the game creature on the map.
	 */
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
	
	/**
	 * Function that returns the symbol of the game creature.
	 * @return the symbol of the game creature.
	 */
	public char GetSymbol()
	{
		return symbol;
	}
	
	public void SetSymbol(char symbol) 
	{
		this.symbol = symbol;
	}
}
