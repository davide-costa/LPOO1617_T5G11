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
	
	//this method is called by GUI to shoot the opponent and informs Opponent of the shot
	public void shootOpponent(Coords coords) throws IOException
	{
		if (getCellState(coords).isDiscovered())
			return;
		
		opponent.shoot(coords);
	}
	
	//this method is called by Opponent class when the opponent shoots this player and informs the effects to Opponent class. The GUI is notified by observing that the map changed
	public void shootAlly(Coords coords, ArrayList<Coords> coords, ArrayList<CellState> resultStates)
	{
		CellState state = getCellState(coords);
		
		if (state.hasBoat())
		
		CellState resultState = null;
		return resultState;
	}
	

	
	
}
