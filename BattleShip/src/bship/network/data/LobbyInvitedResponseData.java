package bship.network.data;

public class LobbyInvitedResponseData extends LobbyData
{
	private boolean accepted;
	
	public LobbyInvitedResponseData(boolean accepted)
	{
		this.accepted = accepted;
	}
	
	public boolean wasAccepted()
	{
		return accepted;
	}
}
