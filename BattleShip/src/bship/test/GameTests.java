package bship.test;

import bship.logic.Coords;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.network.data.GameResultData.GameResult;

public class GameTests extends Game
{
	private Coords lastReceivedCoords;
	private GameResult currResult;
	private boolean endOfGame;
	
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
		return endOfGame;
	}
	
	public void handleResultData(Coords lastShootCoords, GameResult result)
	{
		lastReceivedCoords = lastShootCoords;
		currResult = result;
	}
	
	public void setOpponentMap(GameMap opponentGameMap)
	{
		this.opponentMap = opponentGameMap;
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
	
	public void setEndOfGame(boolean endOfGame)
	{
		this.endOfGame = endOfGame;
	}

	public Object getOpponentMap()
	{
		return opponentMap;
	}
}
