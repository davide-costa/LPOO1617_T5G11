package bship.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class GameMap extends Observable implements Serializable, Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5027329701106832930L;
	protected int sizeX;
	protected int sizeY;
	protected CellState map[][];
	Observer currObserver;
	
	public GameMap(boolean isOpponent, int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		if (isOpponent)
			map = new OpponentCellState[sizeX][sizeY];
		else
		{
			map = new AllyCellState[sizeX][sizeY];
			fill(new AllyCellState(null));
		}
	}
	
	public void fill(CellState cell)
	{
		for (int y = 0; y < sizeX; y++)
		{
			for (int x = 0; x < sizeY; x++)
			{
				CellState newCell = cell.getCopy();
				newCell.refreshObserver(this);
				setCellState(new Coords(x, y), newCell);
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
		else if (state == null)
			map[coords.GetY()][coords.GetX()] = state;
		else
			throw new IllegalArgumentException();
		
		if (state != null)
			state.refreshObserver(this);
		setChanged();
		notifyObservers();
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
	
	/**
	 * Returns the state (i.e. representation or symbol) of a the given coords in the map.
	 * @param xCoord the x coord of the cell we want to get the state.
	 * @param yCoord the y coord of the cell we want to get the state.
	 * @return the state of the cell.
	 */
	public CellState getCellState(int xCoord, int yCoord)
	{
		return map[xCoord][yCoord];
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
	
	@Override
	public void update(Observable cell, Object unsued)
	{
		setChanged();
		notifyObservers();
	}
	
	public void refreshObserver(Observer newObserver)
	{
		if (currObserver != null)
			deleteObserver(currObserver);
		addObserver(newObserver);
		currObserver = newObserver;
	}
	
	public void notifyObserver()
	{
		setChanged();
		notifyObservers();
	}
}
