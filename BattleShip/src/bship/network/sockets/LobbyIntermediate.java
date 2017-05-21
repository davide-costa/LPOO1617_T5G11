package bship.network.sockets;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.gui.Lobby;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInviteResponseData;
import bship.network.data.LobbyInvitedData;

public class LobbyIntermediate implements Observer
{
	private Lobby gui;
	
	public LobbyIntermediate(Lobby gui) throws IOException
	{
		this.gui = gui;
		Client clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
	}

	@Override
	public void update(Observable clientSocket, Object object)
	{
		if (!(object instanceof LobbyData))
			return;
		
		if (object instanceof LobbyInvitedData)
		{
			LobbyInvitedData invite = (LobbyInvitedData)object;
			gui.handleInvite(invite.getInviterName());
		}
		else if (object instanceof LobbyInviteResponseData)
		{
			LobbyInviteResponseData invite = (LobbyInviteResponseData)object;
			gui.handleInviteResponse(invite.wasAccepted());
		}
	}
}