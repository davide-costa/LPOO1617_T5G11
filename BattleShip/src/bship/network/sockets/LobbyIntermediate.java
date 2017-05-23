package bship.network.sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import bship.gui.Lobby;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInviteResponseData;
import bship.network.data.LobbyInvitedData;

public class LobbyIntermediate extends SocketIntermediate implements Observer
{
	private Lobby gui;
	private ArrayList<String> onlinePlayersNames;
	
	public LobbyIntermediate(Lobby gui) throws IOException
	{
		this.gui = gui;
		Client clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
		System.out.println("lobby intermediate constructor");
	}
	
	public void setOnlinePlayersNames(ArrayList<String> onlinePlayersNames)
	{
		this.onlinePlayersNames = onlinePlayersNames;
	}

	@Override
	public void update(Observable clientSocket, Object object)
	{
		System.out.println("update");
		if (!(object instanceof LobbyData))
			return;
		
		if (object instanceof LobbyInfoData)
		{
			LobbyInfoData info = (LobbyInfoData) object;
			this.onlinePlayersNames = info.getOnlinePlayersNames();
		}
		else if (object instanceof LobbyInvitedData)
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
