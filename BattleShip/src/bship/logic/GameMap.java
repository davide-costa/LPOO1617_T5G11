package bship.logic;


public abstract class GameMap
{
	protected int sizeX;
	protected int sizeY;
	protected char map[][];
	public char[][] getMap()
	{
		return map;
	}
	
	public int getMapXSize()
	{
		return sizeX;
	}
	
	public int getMapYSize()
	{
		return sizeY;
	}
	
	/**
	 * Sets the state of a cell in the given coords with the given symbol.
	 * @param coords the coords of the cell we want to get the state.
	 * @param symbol the symbol we want to set in the given coords.
	 */
	public void setCellState(Coords coords, char symbol)
	{
		map[coords.GetY()][coords.GetX()] = symbol;
	}
	
	/**
	 * Returns the state (i.e. representation or symbol) of a the given coords in the map.
	 * @param coords the coords of the cell we want to get the state.
	 * @return the state of the cell.
	 */
	public char getCellState(Coords coords)
	{
		return map[coords.GetY()][coords.GetX()];
	}
}
