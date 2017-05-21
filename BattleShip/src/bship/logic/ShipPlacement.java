package bship.logic;

import java.util.ArrayList;

public abstract class ShipPlacement 
{
	private GameMap map;
	
	public ShipPlacement(GameMap map)
	{
		this.map = map;
	}
	
	public boolean isShipDropValid(Ship ship)
	{
		ArrayList<Coords> coordsArray = new ArrayList<Coords>();
		ArrayList<Coords> shipCoords = ship.getCoords();
		coordsArray = Coords.getSurroundingCoords(shipCoords);
		
		if(!map.areListOfCoordsInMapRange(shipCoords))
			return false;
		
		for(Coords currCoords: coordsArray)
		{
			if(!map.areCoordsInMapRange(currCoords))
				continue;
			
			System.out.println(currCoords.GetX());
			if(map.getCellState(currCoords) != null)
				if(map.getCellState(currCoords).hasShip())
					return false;
		}
		System.out.println("GOOD");
		return true;
	}
	
	public boolean DropShip(Ship ship)
	{
		if(!isShipDropValid(ship))
		{
			ship.cleanCoords();
			return false;
		}
			
		ArrayList<Coords> shipCoords = ship.getCoords();
		CellState cellState = new AllyCellState(ship);
		for(Coords currCoords: shipCoords)
		{
			map.setCellState(currCoords, cellState);
		}
			
		return true;	
	}
}
