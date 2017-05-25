package bship.test;

import bship.logic.Coords;
import bship.logic.Game;
import bship.network.data.GameResultData.GameResult;

public class GameTests extends Game
{
	private Coords lastReceivedCoords;
	private GameResult currResult;
	
	public GameTests()
	{
		super();
	}

	public void shootAlly(Coords coords)
	{
		lastReceivedCoords = coords;
	}
	
	public GameResult getPlayEffects(Coords coords)
	{
		lastReceivedCoords = coords;
		return currResult;
	}
	
	public boolean isEndOfGame()
	{
		return false;
		
	}
	
	public void handleResultData()
	{
		
	}
	
	public Coords getLastReceivedCoords()
	{
		return lastReceivedCoords;
	}

	public void setLastReceivedCoords(Coords lastReceivedCoords)
	{
		this.lastReceivedCoords = lastReceivedCoords;
	}

	public GameResult getCurrResult()
	{
		return currResult;
	}

	public void setCurrResult(GameResult currResult)
	{
		this.currResult = currResult;
	}
}