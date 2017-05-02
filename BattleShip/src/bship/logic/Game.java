package bship.logic;

import java.io.IOException;
import java.util.ArrayList;

public class Game
{
	private static Game gameInstance = null;
	private GameMap map;
	private GameMap opponentMap;
	Opponent opponent;
	
	private Game()
	{
		
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
	
	private void getCellStatesOfCoords(ArrayList<Coords> coords, ArrayList<CellState> states)
	{
		states.clear();
		for (Coords coord : coords)
		{
			states.add(getCellState(coord));
		}
	}
	
	private ArrayList<Coords> getSurroundingCoordsOfShip(Ship ship) 
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
	}
	
	public void getPlayEffects(Coords shootCoords, ArrayList<Coords> coordsArray, ArrayList<CellState> resultStates)
	{
		CellState state = getCellState(shootCoords);
		coordsArray.add(shootCoords);
		if (!state.hasShip())
		{
			CellState resultState = new OpponentCellState(null, false);
			resultState.setDiscovered(true);
			resultStates.add(resultState);
			return;
		}
		
		Ship ship = state.getShip();
		if(ship.isDestroyed())
		{
			coordsArray = ship.getCoords();
			getCellStatesOfCoords(coordsArray, resultStates);
			coordsArray.addAll(getSurroundingCoordsOfShip(ship));
			setSorroundingCoordsAsDiscovered(coordsArray, ship.getSize() - 1);
		}
		else
		{
			CellState resultState = new OpponentCellState(null, true);
			resultState.setDiscovered(true);
			resultStates.add(resultState);
		}
	}
	
	
	private void setSurroundingCoordsAsDestroyed(ArrayList<Coords> coordsArray, int offset)
	{
		for (int i = offset; i < coordsArray.size(); i++)
		{
			Coords coords = coordsArray.get(i);
			CellState state = getCellState(coords);
			state.setDiscovered(true);
		}

	}
	
}
