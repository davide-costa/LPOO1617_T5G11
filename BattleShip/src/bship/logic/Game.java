package bship.logic;

import java.io.IOException;
import java.util.ArrayList;

public class Game
{
	private GameMap map;
	private GameMap opponentMap;
	Opponent opponent;
	
	public Game(GameMap gameMap)
	{
		map = gameMap;
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
	
	//this method is called by GUI to shoot the opponent and informs Opponent of the shot
	public void shootOpponent(Coords coords) throws IOException
	{
		if (getCellState(coords).isDiscovered())
			return;
		
		opponent.shoot(coords);
	}
	
	//this method is called by Opponent class when the opponent shoots this player and informs the effects to Opponent class. The GUI is notified by observing that the map changed
	public void shootAlly(Coords shootCoords, ArrayList<Coords> coords, ArrayList<CellState> resultStates)
	{
		CellState state = getCellState(shootCoords);
		coords.clear();
		resultStates.clear();
		
		if (state.isDiscovered())
			return;
		
		coords.add(shootCoords);
		if (!state.hasShip())
		{
			CellState resultState = null;
			resultState.setDiscovered(true);
			resultStates.add(resultState);
			return;
		}
		
		Ship ship = state.getShip();
		if(ship.isDestroyed())
		{
			ship.getCoords(coords);
			getCellStatesOfCoords(coords, resultStates);
			getSurroundingCoordsOfShip(coords);
		}
		else
		{
			Ship maskedShip = new BattleShip(0, shootCoords, "");
			CellState resultState = new CellState(maskedShip);
			resultState.setDiscovered(true);
			resultStates.add(resultState);
		}
	
		//set all cells as discovered
		
	}

	private void getSurroundingCoordsOfShip(ArrayList<Coords> coords) 
	{
		
	}


	
}
