package bship.logic;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public abstract class CellState extends Observable implements Serializable
{
	private static final long serialVersionUID = 198033874357001837L;
	protected boolean discovered;
	protected Ship ship;
	private Observer currObserver;
	
	public CellState(Ship ship)
	{
		this.setShip(ship);
		this.discovered = false;
		currObserver = null;
	}
	
	public abstract CellState getCopy();
	
	public boolean isDiscovered()
	{
		return discovered;
	}

	public void setDiscovered(boolean discovered)
	{
		this.discovered = discovered;
		notifyObserver();
	}

	public Ship getShip()
	{
		return ship;
	}

	public void setShip(Ship ship)
	{
		this.ship = ship;
		notifyObserver();
	}
	
	public abstract boolean hasShip();
	
	public abstract boolean isDiscoveredAndShip();
	
	public boolean isDiscoveredAndWater()
	{
		return isDiscovered() && !hasShip();
	}
	
	public abstract boolean hasShipDestroyed();
	
	public void refreshObserver(Observer newObserver)
	{
		if (currObserver != null)
			deleteObserver(currObserver);
		currObserver = newObserver;
		addObserver(newObserver);
	}
	
	private void notifyObserver()
	{
		if (currObserver != null)
		{
			setChanged();
			notifyObservers();
		}
	}
	
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