package bship.network.sockets;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.gui.Lobby;
import bship.gui.ShipPlacementPanel;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.ReadyForGameData;
import bship.network.data.ShipPlacementData;
import bship.network.data.StartGameData;

public class ShipPlacementIntermediate extends SocketIntermediate implements Observer
{
	private ShipPlacementPanel gui;
	private Client socket;
	
	public ShipPlacementIntermediate(ShipPlacementPanel gui) throws IOException
	{
		this.gui = gui;
		socket = Client.getInstance();
		socket.refreshObserver(this);
	}
	
	@Override
	public void update(Observable clientSocket, Object object) 
	{
		if (!(object instanceof ShipPlacementData))
			return;
		
		if (object instanceof ReadyForGameData)
		{
			gui.opponentIsReady();
		}
		else if (object instanceof StartGameData)
		{
			gui.startGame();
		}
		
	}

	public void playerIsReady() throws IOException
	{
		ReadyForGameData data = new ReadyForGameData();
		socket.sendBattleShipData(data);
	}

}
