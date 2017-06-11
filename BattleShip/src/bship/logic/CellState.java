package bship.logic;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**  
 * Represents a cell state form the game map. Has information if the cell has a ship and if is discovered.
 */ 
public abstract class CellState extends Observable implements Serializable
{
	private static final long serialVersionUID = 198033874357001837L;
	protected boolean discovered;
	protected Ship ship;
	private Observer currObserver;
	
	/**  
	 * Constructor of CellState class.
     * @param ship The ship in that cell. null if none.
	 */ 
	public CellState(Ship ship)
	{
		this.setShip(ship);
		this.discovered = false;
		currObserver = null;
	}
	
	/**  
	 * Getter of a copy of the cell.
     * @return A copy if the cell state.
	 */ 
	public abstract CellState getCopy();
	
	/**  
	 * Checks if the cell is discovered.
     * @return true if discovered. False otherwise.
	 */ 
	public boolean isDiscovered()
	{
		return discovered;
	}

	/**  
	 * Setter of a cell discoverness.
     * @param discovered a boolean indicating if the cell is discovered or not.
	 */ 
	public void setDiscovered(boolean discovered)
	{
		this.discovered = discovered;
		notifyObserver();
	}

	/**  
	 * Getter of the ship in the cell.
     * @return The ship in the cell. null if none.
	 */ 
	public Ship getShip()
	{
		return ship;
	}

	/**  
	 * Setter of a cell ship.
     * @param The ship to be set in the cell.
	 */ 
	public void setShip(Ship ship)
	{
		this.ship = ship;
		notifyObserver();
	}
	
	/**  
	 * Checks if the cell has ship.
     * @return true has ship. False otherwise.
	 */ 
	public abstract boolean hasShip();
	
	/**  
	 * Checks if the cell has ship and is discovered.
     * @return true if has ship and is discovered. False otherwise.
	 */ 
	public abstract boolean isDiscoveredAndShip();
	
	/**  
	 * Checks if the cell has no ship and is discovered.
     * @return true if has no ship and is discovered. False otherwise.
	 */ 
	public boolean isDiscoveredAndWater()
	{
		return isDiscovered() && !hasShip();
	}
	
	/**  
	 * Checks if the cell has a ship destroyed.
     * @return true if has a ship destroyed. False otherwise.
	 */ 
	public abstract boolean hasShipDestroyed();
	
	/**  
	 * Refreshes the observes of the cell, i.e. deletes the old observer if exists and add the new observer.
     * @param The newObserver to be set.
	 */ 
	public void refreshObserver(Observer newObserver)
	{
		if (currObserver != null)
			deleteObserver(currObserver);
		currObserver = newObserver;
		addObserver(newObserver);
	}
	
	/**  
	 * Notify the observers that the cell has been changed.
	 */ 
	private void notifyObserver()
	{
		if (currObserver != null)
		{
			setChanged();
			notifyObservers();
		}
	}
	
	/**  
	 * Checks if the this cell is equal to the cell passed by argument.
	 * @param obj The cell state against the this will be compared.
	 * @return true if equals. False  otherwise.
	 */ 
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellState other = (CellState) obj;
		if (discovered != other.discovered)
			return false;
		if (ship == null) 
		{
			if (other.ship != null)
				return false;
		} 
		else if (!ship.equals(other.ship))
			return false;
		
		return true;
	}
}