package bship.network.data;

import java.util.ArrayList;

public class LobbyInfoData extends LobbyData
{
	private static final long serialVersionUID = -6318024591084146157L;
	private ArrayList<String> onlinePlayersNames;
	
	public LobbyInfoData(ArrayList<String> onlinePlayersNames)
	{
		this.onlinePlayersNames = onlinePlayersNames;
	}
	
	public ArrayList<String> getOnlinePlayersNames()
	{
		return onlinePlayersNames;
	}
}
