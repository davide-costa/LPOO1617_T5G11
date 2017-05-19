package bship.network.data;

public class LobbyInviteData extends LobbyData
{
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
