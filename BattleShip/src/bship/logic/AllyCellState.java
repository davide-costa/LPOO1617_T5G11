package bship.logic;

import java.io.Serializable;

public class AllyCellState extends CellState implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7632021326436969715L;

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
	public boolean hasShipDestroyed()
	{
		if (hasShip())
			if (ship.isDestroyed())
				return true;
		
		return false;
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
