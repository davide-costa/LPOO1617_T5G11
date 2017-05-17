package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import bship.network.data.GameResultData.Result;

public class Game
{
	private static Game gameInstance = null;
	private GameMap map;
	private GameMap opponentMap;
	private Opponent opponent;
	private int aliveShips;
	final HashMap<String, Result> shipNameToResult = new HashMap<String, Result>();;
	
	private Game()
	{
		this.aliveShips = 5;
		this.map = new DefaultMap(false);
		FillShipNameToResult(); 
	}
	
	private void FillShipNameToResult() 
	{
		shipNameToResult.put("Carrier", Result.SINK_CARRIER);
		shipNameToResult.put("Battleship", Result.SINK_BATTLESHIP);
		shipNameToResult.put("Cruiser", Result.SINK_CRUISER);
		shipNameToResult.put("Submarine", Result.SINK_SUBMARINE);
		shipNameToResult.put("Destroyer", Result.SINK_DESTROYER);
	}

	public static Game getInstance()
	{
		if(gameInstance == null)
			gameInstance = new Game();

		return gameInstance;
	}
	
	public CellState getCellState(Coords coords)
	{
		return map.getCellState(coords);
	}
	
	public void setCellState(Coords coords, CellState state)
	{
		map.setCellState(coords, state);
	}
	
	public boolean isEndOfGame()
	{
		return aliveShips == 0;
	}
	
	public void getCellStatesOfCoords(ArrayList<Coords> coords, ArrayList<CellState> states)
	{
		states.clear();
		for (Coords coord : coords)
		{
			states.add(getCellState(coord));
		}
	}
	
	public ArrayList<Coords> getSurroundingCoordsOfShip(Ship ship) 
	{
		ArrayList<Coords> shipCoords = ship.getCoords();
		ArrayList<Coords> surroundingCoords = Coords.getSurroundingCoords(shipCoords);
	
		for(int i = 0; i < surroundingCoords.size(); i++)
		{
			if(surroundingCoords.get(i).GetX() < 0 || surroundingCoords.get(i).GetY() < 0)
			{
				surroundingCoords.remove(i);
				i--;
			}
			else if(surroundingCoords.get(i).GetX() >= map.sizeX || surroundingCoords.get(i).GetY() >= map.sizeY)
			{
				surroundingCoords.remove(i);
				i--;
			}
		}
		
		return surroundingCoords;
	}
	
	//this method is called by GUI to shoot the opponent and informs Opponent of the shot
	public void shootOpponent(Coords coords) throws IOException
	{
		if (getCellState(coords).isDiscovered())
			return;
		
		opponent.shoot(coords);
	}
	
	//this method is called by Opponent class when the opponent shoots this player and informs the effects to Opponent class. The GUI is notified by observing that the map changed
	public void shootAlly(Coords shootCoords)
	{
		CellState state = getCellState(shootCoords);
		
		if (state.isDiscovered())
			return;
		
		//Set cell as discovered
		state.setDiscovered(true);
		
		Ship ship = state.getShip();
		ship.shoot();
		
		if(!ship.isDestroyed())
			return;
		
		ArrayList<Coords> coordsArray = new ArrayList<Coords>();
		coordsArray = ship.getCoords();
		coordsArray.addAll(getSurroundingCoordsOfShip(ship));
		setSurroundingCoordsAsDiscovered(coordsArray, ship.getSize() - 1);
		aliveShips--;
	}
	
	
	public Result getPlayEffects(Coords shootCoords)
	{
		CellState cell = getCellState(shootCoords);
		if (!cell.hasShip())
			return Result.WATER;
	
		Ship ship = cell.getShip();
		if(ship.isDestroyed())
			return shipNameToResult.get(ship.getName());
		else
			return Result.HIT;
	}
	
	
	public void setSurroundingCoordsAsDiscovered(ArrayList<Coords> coordsArray, int offset)
	{
		for (int i = offset; i < coordsArray.size(); i++)
		{
			Coords coords = coordsArray.get(i);
			CellState state = getCellState(coords);
			state.setDiscovered(true);
		}

	}
	
}
