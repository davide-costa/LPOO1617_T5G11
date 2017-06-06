package bship.gui;

public class CurrGameData
{
	private static String allyName;
	private static String opponentName;
	
	public static String getAllyName()
	{
		return allyName;
	}
	
	public static void setAllyName(String playerName)
	{
		CurrGameData.allyName = playerName;
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
