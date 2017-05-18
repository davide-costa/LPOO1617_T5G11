package bship.network.data;

public class EndOfGameData extends GameData
{
	private Object winnerGameMap;
	
	public EndOfGameData(Object winnerGameMap)
	{
		this.winnerGameMap = winnerGameMap;
	}

	public Object getWinnerGameMap()
	{
		return winnerGameMap;
	}

}
