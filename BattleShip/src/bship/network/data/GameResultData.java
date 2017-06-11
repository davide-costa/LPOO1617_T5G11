package bship.network.data;


public class GameResultData extends GameData
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3413165844542224470L;

	public enum GameResult { WATER, HIT, SINK_CARRIER, SINK_BATTLESHIP, SINK_DESTROYER, SINK_SUBMARINE, SINK_CRUISER };
	private boolean endOfGame;
	private GameResult result;
	
	public GameResultData(GameResult result, boolean endOfGame)
	{
		this.result = result;
		this.endOfGame = endOfGame;
	}
	
	public boolean isEndOfGame()
	{
		return endOfGame;
	}
	
	public GameResult getResult()
	{
		return result;
	}
}
