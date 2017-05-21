package bship.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import bship.logic.Coords;
import bship.logic.DefaultMap;
import bship.logic.GameMap;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.SinglePlayerShipPlacement;

public class AIOpponent
{
	private ShipPlacement shipPlacement;
	public GameMap map;
	private HashMap<Integer, String> generatedDirection = new HashMap<Integer, String>();
	
	public AIOpponent()
	{
		map = new DefaultMap(false);
		shipPlacement = new SinglePlayerShipPlacement(map);
		fillGeneratedDirection();
	}
	
	
	private void fillGeneratedDirection()
	{
		generatedDirection.put(0, "Vertical");
		generatedDirection.put(1, "Horizontal");
	}
	
	public void PlaceShips(ArrayList<Ship> ships)
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
