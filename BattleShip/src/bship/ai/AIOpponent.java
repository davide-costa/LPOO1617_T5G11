package bship.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import bship.logic.AllyCellState;
import bship.logic.Coords;
import bship.logic.DefaultMap;
import bship.logic.GameMap;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.SinglePlayerShipPlacement;

public class AIOpponent
{
	private GameMap map;
	private HashMap<Integer, String> generatedDirection = new HashMap<Integer, String>();
	
	public AIOpponent()
	{
		map = new DefaultMap(false);
	}
	
	
	private void initializeAllCellsToWater()
	{
		for (int y = 0; y < map.getMapXSize(); y++)
		{
			for (int x = 0; x < map.getMapYSize(); x++)
			{
				map.setCellState(new Coords(x, y), new AllyCellState(null));
			}
		}
	}


	private void fillGeneratedDirection()
	{
		generatedDirection.put(0, "Vertical");
		generatedDirection.put(1, "Horizontal");
	}
	
	public GameMap getGameMap()
	{
		return map;
	}
	
	public void PerformShipPlacement(ArrayList<Ship> ships)
	{
		fillGeneratedDirection();
		initializeAllCellsToWater();
		SinglePlayerShipPlacement shipPlacement = new SinglePlayerShipPlacement(map);
		PlaceShips(shipPlacement, ships);
	}
	
	public void PlaceShips(SinglePlayerShipPlacement shipPlacement, ArrayList<Ship> ships)
	{
		for(Ship ship: ships)
		{
			do
			{
				GenerateShipPosition(ship);
			}
			while(!shipPlacement.DropShip(ship));

		}
	}

	private void GenerateShipPosition(Ship ship) 
	{	
		int xCoord = ThreadLocalRandom.current().nextInt(0, map.getMapXSize() - 1 + 1);
		int yCoord = ThreadLocalRandom.current().nextInt(0, map.getMapYSize() - 1 + 1);
		int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		String direction = generatedDirection.get(randomNum);
	
		int xInc, yInc;
		if(direction.equals("Vertical"))
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
			System.out.println(i);
			Coords coords = new Coords(currCoords);
			ship.addCoord(coords);
			currCoords.incrementX(xInc);
			currCoords.incrementY(yInc);
			System.out.println(i);
		}
		
		for(int i = 0; i < ship.getCoords().size();i++)
		{
			System.out.println(ship.getCoords().get(i).GetX() + "  " + ship.getCoords().get(i).GetY());
		}
		
		System.out.println("saiu do for");
			
	}
}
