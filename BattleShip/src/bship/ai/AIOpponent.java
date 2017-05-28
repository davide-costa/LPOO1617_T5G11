package bship.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import bship.logic.AllyCellState;
import bship.logic.Coords;
import bship.logic.DefaultMap;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.SinglePlayerShipPlacement;

public class AIOpponent
{
	private Game game;
	private GameMap map;
	//private AIState state;
	private HashMap<Integer, String> generatedDirection = new HashMap<Integer, String>();
	private enum Direction {UP, DOWN, RIGHT, LEFT};
	private ArrayList<Direction> mainDirections;
	private ArrayList<Direction> secondaryDirections;
	private 
	private static int lastRandomHitCoords;
//	private static boolean upChecked;
//	private static boolean downChecked;
//	private static boolean rightChecked;
//	private static boolean leftChecked;
	
	public AIOpponent()
	{
		map = new DefaultMap(false);
		mainDirections = new ArrayList<Direction>();
		secondaryDirections = new ArrayList<Direction>();
	}
	
	//tem de ser chamado quando se passa a ai search...quando fazendo random se atinge um barco
	private void FillDirections() 
	{
		mainDirections.add(Direction.UP);
		mainDirections.add(Direction.DOWN);
		mainDirections.add(Direction.RIGHT);
		mainDirections.add(Direction.LEFT);
		
		secondaryDirections.addAll(mainDirections);
	}


	private void initializeAllCellsToWater()
	{
		map.fill(new AllyCellState(null));
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
	
	public void PerformShipPlacement(Game game, ArrayList<Ship> ships)
	{
		fillGeneratedDirection();
		initializeAllCellsToWater();
		SinglePlayerShipPlacement shipPlacement = new SinglePlayerShipPlacement(map);
		PlaceShips(shipPlacement, ships);
		this.game = game;
		game.setAllyMap(map);
		
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
	
	//precisa do mapa para saber as coords que ja foram descobertas...
	public Coords generateRandomShootCoords()
	{
		Coords shootCoords = new Coords(0,0);
		
		int maxXCoord = map.getMapXSize() - 1;
		int maxYCoord = map.getMapYSize() - 1;
		Random rand = new Random();
		do
		{
			shootCoords.SetX(rand.nextInt(maxXCoord));
			shootCoords.SetY(rand.nextInt(maxYCoord));
		}
		while(!shootCoordsValid(shootCoords));
		
		return shootCoords;
	}


	private boolean shootCoordsValid(Coords shootCoords) 
	{
		if(map.getCellState(shootCoords).isDiscovered())
			return false;
		
		return true;
	}
	
	public Coords generateAIShootCoords()
	{
		
		switch(mainState)
		{
			case Horizontal:
				switch(secondaryState)
				{
					case Left:
						break;
					case Right:
						break;
				}
				
			case Vertical:
				switch(secondaryState)
				{
					case Up:
						break;
					case Down:
						break;
				}
		}
		Coords shootCoords = new Coords(0,0);

		int maxXCoord = map.getMapXSize() - 1;
		int maxYCoord = map.getMapYSize() - 1;
		Random rand = new Random();
		
		do
		{
			shootCoords.SetX(rand.nextInt(maxXCoord));
			shootCoords.SetY(rand.nextInt(maxYCoord));
		}
		while(!shootCoordsValid(shootCoords));
		
		return shootCoords;
	}
}
