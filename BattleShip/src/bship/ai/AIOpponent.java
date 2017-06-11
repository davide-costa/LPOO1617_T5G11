package bship.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import bship.logic.AllyCellState;
import bship.logic.Coords;
import bship.logic.DefaultMap;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.Ship;
import bship.logic.SinglePlayerShipPlacement;

public class AIOpponent
{
	private Game game;
	private GameMap map;
	private HashMap<Integer, String> generatedDirection = new HashMap<Integer, String>();
	private enum Direction {UP, DOWN, RIGHT, LEFT};
	private ArrayList<Direction> mainDirections;
	private ArrayList<Direction> secondaryDirections;

	
	public AIOpponent()
	{
		map = new DefaultMap(false);
		mainDirections = new ArrayList<Direction>();
		secondaryDirections = new ArrayList<Direction>();
		fillDirections();
	}
	
	private void fillDirections() 
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
	
	public void performShipPlacement(Game game, ArrayList<Ship> ships)
	{
		fillGeneratedDirection();
		initializeAllCellsToWater();
		SinglePlayerShipPlacement shipPlacement = new SinglePlayerShipPlacement(map);
		AIShipPlacer.placeShipsInMap(shipPlacement);
		this.game = game;
		game.setAllyMap(map);	
	}
	
	public Coords generateRandomShootCoords()
	{
		int maxXCoord = map.getMapXSize() - 1;
		int maxYCoord = map.getMapYSize() - 1;
		Coords shootCoords = new Coords(0,0);
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
		GameMap opponentMap = game.getOpponentMap();
		if(opponentMap.getCellState(shootCoords).isDiscovered())
			return false;
		
		return true;
	}
	
	public Coords generateAIShootCoords()
	{
		return null;
	}
}
