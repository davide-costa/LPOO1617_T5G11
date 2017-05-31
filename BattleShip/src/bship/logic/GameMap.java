package bship.logic;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameMap implements Serializable
{
	protected int sizeX;
	protected int sizeY;
	protected CellState map[][];
	
	public GameMap(boolean isOpponent, int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		if (isOpponent)
			map = new OpponentCellState[sizeX][sizeY];
		else
			map = new AllyCellState[sizeX][sizeY];
	}
	
	public void fill(CellState cell)
	{
		for (int y = 0; y < sizeX; y++)
		{
			for (int x = 0; x < sizeY; x++)
			{
				setCellState(new Coords(x, y), cell.getCopy());
			}
		}
	}
	
	public CellState[][] getMap()
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
	public void setCellState(Coords coords, CellState state)
	{
		if(state instanceof AllyCellState)
			setAllyCellState(coords, (AllyCellState) state);
		else if(state instanceof OpponentCellState)
			setAllyCellState(coords, (OpponentCellState) state);
		else
			throw new IllegalArgumentException();
			
	}
	
	private void setAllyCellState(Coords coords, AllyCellState state)
	{
		map[coords.GetY()][coords.GetX()] = state;
	}
	
	private void setAllyCellState(Coords coords, OpponentCellState state)
	{
		map[coords.GetY()][coords.GetX()] = state;
	}
	
	/**
	 * Returns the state (i.e. representation or symbol) of a the given coords in the map.
	 * @param coords the coords of the cell we want to get the state.
	 * @return the state of the cell.
	 */
	public CellState getCellState(Coords coords)
	{
		return map[coords.GetY()][coords.GetX()];
	}
	
	public boolean areCoordsInMapRange(Coords coords)
	{
		int currCoordsX = coords.GetX();
		if(currCoordsX < 0 || currCoordsX >= sizeX) 
			return false;
				
		int currCoordsY = coords.GetY();
		if(currCoordsY < 0 || currCoordsY >= sizeY)
			return false;

		return true;
	}
	
	public boolean areListOfCoordsInMapRange(ArrayList<Coords> coords)
	{
		for(Coords currCoords: coords)
			if(!areCoordsInMapRange(currCoords))
				return false;
		
		return true;
	}
}
