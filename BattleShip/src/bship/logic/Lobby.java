package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import bship.network.data.BattleShipData;
import bship.network.data.EndOfGameData;
import bship.network.data.GameResultData;
import bship.network.data.GameShootData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInviteResponseData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.GameResultData.Result;

public class Lobby implements Observer
{
	private ArrayList<String> onlinePlayersNames;
	
	public Lobby()
	{
		
	}
	
	public void setOnlinePlayersNames(ArrayList<String> onlinePlayersNames)
	{
		this.onlinePlayersNames = onlinePlayersNames;
	}
	
	@Override
	public void update(Observable clientSocket, Object object)
	{
		BattleShipData gameData = (BattleShipData)object;
		if (gameData instanceof LobbyInfoData)
		{
			LobbyInfoData info = (LobbyInfoData) gameData;
			this.onlinePlayersNames = info.getOnlinePlayersNames();
		}
		else if (gameData instanceof LobbyInvitedData)
		{
			//avisar que chegou um convite
		}
		else if (gameData instanceof LobbyInviteResponseData)
		{
			//avisar que chegou a resposta ao concite feito
		}
	}
	
}
