package bship.logic;

public class OpponentCellState extends CellState 
{
	private boolean hasShip;
	
	public OpponentCellState(Ship ship, boolean hasShip) 
	{
		super(ship);
		this.hasShip = hasShip;
	}
	
	public OpponentCellState(Ship ship, boolean hasShip, boolean discovered) 
	{
		super(ship);
		this.hasShip = hasShip;
		this.discovered = discovered;
	}

	public boolean hasShip() 
	{
		return hasShip;
	}

	public boolean isDiscoveredAndShip()
	{
		return hasShip && discovered;
	}

	@Override
	public boolean hasShipDestroyed()
	{
		return ship != null;
	}
	
	@Override
	public CellState getCopy()
	{
		Ship ship = null;
		if(this.ship != null)
			ship = this.ship.getCopy();
		CellState cell = new OpponentCellState(ship, hasShip, discovered);
		return cell;
	}
}
