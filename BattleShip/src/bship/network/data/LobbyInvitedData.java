package bship.network.data;

public class LobbyInvitedData extends LobbyData
{
	private String inviterName;
	
	public LobbyInvitedData(String inviterName) 
	{
		this.inviterName = inviterName;
	}
	
	public String getInviterName()
	{
		return inviterName;
	}

}
