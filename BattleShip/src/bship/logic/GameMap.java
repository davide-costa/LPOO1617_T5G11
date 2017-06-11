package bship.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents a map in a BattleShip game. During a game, two instances are used on each side: one to represent the ally map and the other to represent the opponent map. Also implements methods to help dealing with all the necessary logic regarding the map.
 */
public abstract class GameMap extends Observable implements Serializable, Observer
{
	private static final long serialVersionUID = 5027329701106832930L;
	protected int sizeX;
	protected int sizeY;
	protected CellState map[][];
	Observer currObserver;
	
	/**  
	 * Constructor of GameMap. Called as super for the the subclasses like DefaultMap.
     * @param isOpponent A boolean that indicate if the map will be for opponent or ally use.
	 * @param sizeX The x size of the map being created.
	 * @param sizeY The x size of the map being created.
	 */ 
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
	
	/**  
	 * Responsible for filling the map with a given cell state, but creating copies of the given cell, for no aliasing.
     * @param cell The cell which state will be filled in all map.
	 */
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
	
	/**  
	 * Getter for the map.
     * @return The map.
	 */ 
	public CellState[][] getMap()
	{
		return map;
	}
	
	/**  
	 * Getter for the map x size.
     * @return The map x size.
	 */ 
	public int getMapXSize()
	{
		return sizeX;
	}
	
	/**  
	 * Getter for the map y size.
     * @return The map y size.
	 */
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
	
	/**
	 * Verifies if the given coords are in map range.
	 * @param coords the coords to test.
	 * @return true if in map range. False otherwise.
	 */
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
	
	/**
	 * Verifies if the given list of coords are in map range.
	 * @param coords the list of coords to test.
	 * @return true if all in map range. False otherwise.
	 */
	public boolean areListOfCoordsInMapRange(ArrayList<Coords> coords)
	{
		for(Coords currCoords: coords)
			if(!areCoordsInMapRange(currCoords))
				return false;
		
		return true;
	}
	
	/**
	 * Called when one of the cells of the map has been altered. And then informs his observers (gui, in this case) that the map has changed.
	 * @param cell the cell that has been changed.
	 * @param unsued unused paramenter that cames with override method body..
	 */
	@Override
	public void update(Observable cell, Object unused)
	{
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Refreshes the observer of this class. Which means removes the current observer (if it exists) and adds the Observer it receives as parameter as new observer.
	 */
	public void refreshObserver(Observer newObserver)
	{
		if (currObserver != null)
			deleteObserver(currObserver);
		addObserver(newObserver);
		currObserver = newObserver;
	}
	
	/**
	 * Forces the notification of its observer. Is called whenever the gui needs to be notified that the map has changed without actually making changes to its fields.
	 * For example, when the GameMap changes to a new one during a game, this method is called to inform the Gui that the map needs to be repainted.
	 */
	public void notifyObserver()
	{
		setChanged();
		notifyObservers();
	}
}