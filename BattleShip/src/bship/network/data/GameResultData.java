package bship.network.data;

import java.util.ArrayList;

import bship.logic.CellState;
import bship.logic.Coords;

public class GameResultData extends GameData
{
	private ArrayList<Coords> coordsArray;
	private ArrayList<CellState> resultStates;
	private boolean endOfGame;
	
	
	public GameResultData(ArrayList<Coords> coordsArray, ArrayList<CellState> resultStates, boolean endOfGame)
	{
		this.coordsArray = coordsArray;
		this.resultStates = resultStates;
		this.endOfGame = endOfGame;
	}
	
	public ArrayList<Coords> getCoordsArray()
	{
		return coordsArray;
	}
	
	public ArrayList<CellState> getResultStates()
	{
		return resultStates;
	}
	
	public boolean isEndOfGame()
	{
		return endOfGame;
	}
}
