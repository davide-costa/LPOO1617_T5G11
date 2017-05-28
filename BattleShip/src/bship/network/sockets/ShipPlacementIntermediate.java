package bship.network.sockets;

import java.util.Observable;
import java.util.Observer;

import bship.network.data.LobbyData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.ReadyForGameData;
import bship.network.data.ShipPlacementData;
import bship.network.data.StartGameData;

public class ShipPlacementIntermediate extends SocketIntermediate implements Observer
{
	public ShipPlacementIntermediate()
	{
		
	}
	
	@Override
	public void update(Observable clientSocket, Object object) 
	{
		if (!(object instanceof ShipPlacementData))
			return;
		
		if (object instanceof ReadyForGameData)
		{
		
		}
		else if (object instanceof StartGameData)
		{
			
		}
		
	}

}
