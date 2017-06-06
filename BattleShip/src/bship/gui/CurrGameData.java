package bship.gui;

public class CurrGameData
{
	private static String playerName;
	private static String opponentName;
	
	public static String getPlayerName()
	{
		return playerName;
	}
	
	public static void setPlayerName(String playerName)
	{
		CurrGameData.playerName = playerName;
	}
	
	public static String getOpponentName()
	{
		return opponentName;
	}
	
	public static void setOpponentName(String opponentName)
	{
		CurrGameData.opponentName = opponentName;
	}
	
}
