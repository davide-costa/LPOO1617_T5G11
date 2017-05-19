package bship.network.data;

public class LobbyInviteResponseData extends LobbyData
{
	private boolean accepted;
	
	public LobbyInviteResponseData(boolean accepted)
	{
		this.accepted = accepted;
	}
	
	public boolean wasAccepted()
	{
		return accepted;
	}
}
