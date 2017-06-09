package bship.network.data;

public class LobbyInvitedData extends LobbyData
{
	private static final long serialVersionUID = -3514748392120310547L;
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
