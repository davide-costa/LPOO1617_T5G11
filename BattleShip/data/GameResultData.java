package bship.network.data;

import java.util.ArrayList;

public class GameResultData extends GameData
{
	private ArrayList<Object> coordsArray;
	private ArrayList<Object> resultStates;
	private boolean endOfGame;
	
	
	public GameResultData(ArrayList<Object> coordsArray, ArrayList<Object> resultStates, boolean endOfGame)
	{
		this.coordsArray = coordsArray;
		this.resultStates = resultStates;
		this.endOfGame = endOfGame;
	}
	
	public ArrayList<Object> getCoordsArray()
	{
		return coordsArray;
	}
	
	public ArrayList<Object> getResultStates()
	{
		return resultStates;
	}
	
	public boolean isEndOfGame()
	{
		return endOfGame;
	}
}
