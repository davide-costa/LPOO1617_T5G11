package bship.logic;

import java.util.HashMap;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInviteData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.LobbyInvitedResponseData;

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
			boolean invitedSucessfully = battleShipServer.invitePlayer(inviteData.getInvitedPlayerName());
		}
		else if(data instanceof LobbyInvitedData)
		{
			//inform user that a new convite arrived
		}
		else if(data instanceof LobbyInvitedResponseData)
		{
			LobbyInvitedResponseData responseData = (LobbyInvitedResponseData)data;
			if(responseData.wasAccepted())
				return;//inform o jogador que o convite foi aceite
		}
				
		
	}

}
