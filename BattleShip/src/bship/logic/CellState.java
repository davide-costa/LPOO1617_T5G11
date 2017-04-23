package bship.logic;

public class CellState
{
	private boolean discovered;
	private Ship ship;
	
	public CellState(Ship ship)
	{
		this.ship = ship;
	}
}
