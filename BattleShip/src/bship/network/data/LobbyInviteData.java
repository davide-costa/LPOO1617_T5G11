package bship.network.data;

public class LobbyInviteData extends LobbyData
{
	private static final long serialVersionUID = 5600403720671536585L;
	private String invitedPlayerName;
	
	public LobbyInviteData(String invitedPlayerName)
	{
		this.invitedPlayerName = invitedPlayerName;
	}

	public String getInvitedPlayerName()
	{
		return invitedPlayerName;
	}
}
