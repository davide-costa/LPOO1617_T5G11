package bship.logic;

public class AllyCellState extends CellState
{

	public AllyCellState(Ship ship)
	{
		super(ship);
	}
	
	public AllyCellState(Ship ship, boolean discovered)
	{
		super(ship);
		this.discovered = discovered;
	}
	
	public boolean hasShip()
	{
		return ship != null;
	}
	
	public boolean isDiscoveredAndShip()
	{
		return discovered && hasShip();
	}

	@Override
	public CellState getCopy()
	{
		Ship ship = null;
		if(this.ship != null)
			ship = this.ship.getCopy();
		CellState cell = new AllyCellState(ship, discovered);
		return cell;
	}
}
