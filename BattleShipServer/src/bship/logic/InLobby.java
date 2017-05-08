package bship.logic;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyInviteData;

public class InLobby extends PlayerState
{
	public InLobby(Player player) 
	{
		super(player);
	}

	@Override
	public void HandleReceivedData(BattleShipData data) 
	{
		if (!(data instanceof LobbyInviteData))
			return;
	}

}
