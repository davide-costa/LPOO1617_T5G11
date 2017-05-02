package bship.logic;

public class OpponentCellState extends CellState 
{
	private boolean hasShip;
	
	public OpponentCellState(Ship ship, boolean hasShip) 
	{
		super(ship);
		this.hasShip = hasShip;
	}


	public boolean hasShip() 
	{
		return hasShip;
	}

	
	public boolean isDiscoveredAndShip()
	{
		return hasShip && discovered;
	}

}
