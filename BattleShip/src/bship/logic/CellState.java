package bship.logic;

public class CellState
{
	private boolean discovered;
	private Ship ship;
	
	public CellState(Ship ship)
	{
		this.setShip(ship);
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
	
	public boolean hasBoat()
	{
		return ship != null;
	}
	
	public boolean isDiscoveredAndBoat()
	{
		return isDiscovered() && hasBoat();
	}
}
