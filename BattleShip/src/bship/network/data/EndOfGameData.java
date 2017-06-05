package bship.network.data;

import java.io.Serializable;

public class EndOfGameData extends GameData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4295712540081879055L;
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
