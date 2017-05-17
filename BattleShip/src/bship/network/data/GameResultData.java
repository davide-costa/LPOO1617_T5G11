package bship.network.data;

import java.util.ArrayList;

public class GameResultData extends GameData
{
	public enum Result { WATER, HIT, SINK_CARRIER, SINK_BATTLESHIP, SINK_DESTROYER, SINK_SUBMARINE, SINK_CRUISER };
	private boolean endOfGame;
	private Result result;
	
	
	public GameResultData(Result result, boolean endOfGame)
	{
		this.result = result;
		this.endOfGame = endOfGame;
	}
	
	public boolean isEndOfGame()
	{
		return endOfGame;
	}
	
	public Result getResult()
	{
		return result;
	}
}
