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
	
}
