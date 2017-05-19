package bship.network.data;

import java.util.ArrayList;

public class LobbyInfoData extends LobbyData
{
	ArrayList<String> onlinePlayersNames;
	
	public LobbyInfoData(ArrayList<String> onlinePlayersNames)
	{
		this.onlinePlayersNames = onlinePlayersNames;
	}
	
	public ArrayList<String> getOnlinePlayersNames()
	{
		return onlinePlayersNames;
	}
}
