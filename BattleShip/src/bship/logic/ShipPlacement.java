package bship.logic;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ShipPlacement 
{
	private GameMap map;
	private HashMap<String, Ship> shipsByName = new HashMap<String, Ship>();
	
	public ShipPlacement(GameMap map)
	{
		this.map = map;
		fillShipsByName();
	}
	
	private void fillShipsByName() 
	{
		shipsByName.put("BattleShip", new BattleShip());
		shipsByName.put("Carrier", new Carrier());
		shipsByName.put("Cruiser1", new Cruiser());
		shipsByName.put("Cruiser2", new Cruiser());
		shipsByName.put("Destroyer", new Destroyer());
		shipsByName.put("Submarine", new Submarine());
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
		
		return true;
	}
	
	public boolean dropShip(Point initCoord, String shipName)
	{
		Ship ship = shipsByName.get(shipName);
		ship.fillCoordsByInitCoord(initCoord, direction);
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
