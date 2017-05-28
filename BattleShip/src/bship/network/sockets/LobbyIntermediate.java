package bship.network.sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import bship.gui.Lobby;
import bship.network.data.BattleShipData;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInviteData;
import bship.network.data.LobbyInviteResponseData;
import bship.network.data.LobbyInviteResponseData.InviteResponse;
import bship.network.data.LobbyInvitedData;

public class LobbyIntermediate extends SocketIntermediate implements Observer
{
	private Lobby gui;
	private Client socket;
	private ArrayList<String> onlinePlayersNames;
	
	public LobbyIntermediate(Lobby gui) throws IOException
	{
		this.gui = gui;
		socket = Client.getInstance();
		socket.refreshObserver(this);
	}
	
	public void setOnlinePlayersNames(ArrayList<String> onlinePlayersNames)
	{
		this.onlinePlayersNames = onlinePlayersNames;
	}
	

	@Override
	public void update(Observable clientSocket, Object object)
	{
		if (!(object instanceof LobbyData))
			return;
		
		if (object instanceof LobbyInfoData)
		{
			LobbyInfoData info = (LobbyInfoData) object;
			this.onlinePlayersNames = info.getOnlinePlayersNames();
			gui.setNamesInModel(onlinePlayersNames);
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
	
	public void invitePlayer(String opponentUsername) throws IOException
	{
		LobbyInviteData inviteData = new LobbyInviteData(opponentUsername);
		socket.sendBattleShipData((BattleShipData) inviteData);
	}

	public void inviteResponse(boolean accepted) throws IOException
	{
		InviteResponse response;
		if(accepted)
			response = InviteResponse.ACCEPTED;
		else
			response = InviteResponse.REJECTED;
		
		LobbyInviteResponseData responseData = new LobbyInviteResponseData(response);
		socket.sendBattleShipData((BattleShipData) responseData);
	}
}
