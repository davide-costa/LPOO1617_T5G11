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
		CellState cell = new AllyCellState(ship.getCopy(), discovered);
		return cell;
	}
}
