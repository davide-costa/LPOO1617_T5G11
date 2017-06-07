package bship.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import bship.logic.Coords;
import bship.logic.GameMap;
import bship.logic.Ship;
import bship.logic.ShipPlacement;

public class AIShipPlacer
{
	static private HashMap<Integer, String> generatedDirection = new HashMap<Integer, String>();
	static
	{
		generatedDirection.put(0, "vertical");
		generatedDirection.put(1, "horizontal");
	}
	
	public static void removePlacedShipsOfMap(ShipPlacement shipPlacement)
	{
		ArrayList<Ship> placedShips = shipPlacement.getPlacedShips();
		for (Ship ship : placedShips)
			shipPlacement.pickUpShip(ship);
	}
	
	public static void placeShipsInMap(ShipPlacement shipPlacement)
	{
		ArrayList<Ship> shipsToPlace = shipPlacement.getShipsToPlace();
		
		for(Ship ship : shipsToPlace)
		{
			do
			{
				generateShipPosition(ship, shipPlacement.getMap());
			}
			while(!shipPlacement.dropShip(ship));
		}
	}
	
	private static void generateShipPosition(Ship ship, GameMap map) 
	{	
		int xCoord = ThreadLocalRandom.current().nextInt(0, map.getMapXSize() - 1 + 1);
		int yCoord = ThreadLocalRandom.current().nextInt(0, map.getMapYSize() - 1 + 1);
		int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		String direction = generatedDirection.get(randomNum);
		ship.setDirection(direction);
	
		int xInc, yInc;
		if(direction.equals("vertical"))
		{
			xInc = 0;
			yInc = 1;
		}
		else
		{
			xInc = 1;
			yInc = 0;
		}
		
		Coords currCoords = new Coords(xCoord, yCoord);
		for(int i = 0; i < ship.getSize(); i++)
		{
			Coords coords = new Coords(currCoords);
			ship.addCoord(coords);
			currCoords.incrementX(xInc);
			currCoords.incrementY(yInc);
		}		
	}
}
