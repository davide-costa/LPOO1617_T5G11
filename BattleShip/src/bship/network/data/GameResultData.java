package bship.network.data;

import java.util.ArrayList;

public class GameResultData extends GameData
{
	private boolean endOfGame;
	public enum Result { WATER, HIT, SINK_CARRIER, SINK_BATTLESHIP, SINK_DESTROYER, SINK_SUBMARINE, SINK_CRUISER }
	
	
	public GameResultData(boolean endOfGame)
	{
		this.endOfGame = endOfGame;
	}
	
	public boolean isEndOfGame()
	{
		return endOfGame;
	}
}
