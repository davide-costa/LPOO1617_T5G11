package bship.logic;

import java.io.IOException;
import java.util.HashMap;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInviteData;
import bship.network.data.LobbyInviteResponseData;
import bship.network.data.LobbyInvitedData;

public class InLobby extends PlayerState
{
	public InLobby(Player player) 
	{
		super(player);
	}

	@Override
	public void HandleReceivedData(BattleShipData data) 
	{
		if (!(data instanceof LobbyData))
			return;
		
		if(data instanceof LobbyInviteData)
		{
			LobbyInviteData inviteData = (LobbyInviteData) data;
			BattleShipServer battleShipServer= player.getBattleShipServer();
			boolean invitedSucessfully = battleShipServer.invitePlayer(player.getUsername(), inviteData.getInvitedPlayerName());
		}
		else if(data instanceof LobbyInviteResponseData)
		{
			LobbyInviteResponseData responseData = (LobbyInviteResponseData)data;
			try
			{
				player.getOpponent().sendData(responseData);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			if(responseData.wasAccepted())
				player.setState(new InShipPlacement(player));
		}
				
		
	}

}
