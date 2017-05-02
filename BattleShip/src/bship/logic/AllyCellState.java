package bship.logic;

public class AllyCellState extends CellState
{

	public AllyCellState(Ship ship)
	{
		super(ship);
	}
	
	public boolean hasShip()
	{
		return ship != null;
	}
	
	public boolean isDiscoveredAndBoat()
	{
		return isDiscovered() && hasShip();
	}
}
