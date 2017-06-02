package bship.logic;

public abstract class CellState
{
	protected boolean discovered;
	protected Ship ship;
	
	public CellState(Ship ship)
	{
		this.setShip(ship);
		this.discovered = false;
	}
	
	public abstract CellState getCopy();
	
	public boolean isDiscovered()
	{
		return discovered;
	}

	public void setDiscovered(boolean discovered)
	{
		this.discovered = discovered;
	}

	public Ship getShip()
	{
		return ship;
	}

	public void setShip(Ship ship)
	{
		this.ship = ship;
	}
	
	public abstract boolean hasShip();
	
	public abstract boolean isDiscoveredAndShip();
	
	public boolean isDiscoveredAndWater()
	{
		return isDiscovered() && !hasShip();
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
