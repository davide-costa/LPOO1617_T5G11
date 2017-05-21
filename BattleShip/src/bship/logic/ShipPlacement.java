package bship.logic;

import java.util.ArrayList;

public class ShipPlacement 
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
		coordsArray.addAll(shipCoords);
		coordsArray = Coords.getSurroundingCoords(shipCoords);
		
		int mapXSize = map.getMapXSize();
		int mapYSize = map.getMapYSize();
		for(Coords currCoords: coordsArray)
		{
			int currCoordsX = currCoords.GetX();
			if(currCoordsX < 0 || currCoordsX >= mapXSize || map.getCellState(currCoords).hasShip())
				return false;
			
			int currCoordsY = currCoords.GetY();
			if(currCoordsY < 0 || currCoordsY >= mapYSize || map.getCellState(currCoords).hasShip())
				return false;
		}
		
		return true;
	}
	
	public boolean DropShip(Ship ship)
	{
		if(!isShipDropValid(ship))
			return false;
			
		ArrayList<Coords> shipCoords = ship.getCoords();
		CellState cellState = new AllyCellState(ship);
		for(Coords currCoords: shipCoords)
		{
			map.setCellState(currCoords, cellState);
		}
			
		return true;	
	}
}
