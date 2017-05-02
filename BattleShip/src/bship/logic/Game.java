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
			CellState resultState = new CellState(null);
			resultState.setDiscovered(true);
			resultStates.add(resultState);
			return;
		}
		
		Ship ship = state.getShip();
		if(ship.isDestroyed())
		{
			coords = ship.getCoords();
			getCellStatesOfCoords(coords, resultStates);
			coords.addAll(getSurroundingCoordsOfShip(ship));
		}
		else
		{
			ArrayList<Coords> shipCoords = new ArrayList<Coords>();
			shipCoords.add(shootCoords);
			Ship maskedShip = new BattleShip(0, shipCoords, "");
			CellState resultState = new CellState(maskedShip);
			resultState.setDiscovered(true);
			resultStates.add(resultState);
		}
	
		//set all cells as discovered
		
	}
	
}
