 package bship.network.sockets;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.gui.ShipPlacementPanel;
import bship.network.data.PlayerDisconnectedData;
import bship.network.data.ReadyForGameData;
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
		if (object instanceof ReadyForGameData)
		{
			gui.opponentIsReady();
		}
		else if (object instanceof StartGameData)
		{
			gui.startGame();
		}
		else if(object instanceof PlayerDisconnectedData)
		{
			gui.opponentQuit();
		}
		
	}

	public void playerIsReady() throws IOException
	{
		ReadyForGameData data = new ReadyForGameData();
		socket.sendBattleShipData(data);
	}
}
