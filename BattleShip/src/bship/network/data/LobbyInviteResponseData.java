package bship.network.data;


public class LobbyInviteResponseData extends LobbyData
{
	private static final long serialVersionUID = 7815873134067172265L;
	public enum InviteResponse {ACCEPTED, REJECTED, UNSUCESSFUL};
	private InviteResponse response;
	
	public LobbyInviteResponseData(InviteResponse response)
	{
		this.response = response;
	}
	
	public boolean successfullySent()
	{
		return response != InviteResponse.UNSUCESSFUL;
	}
	
	public boolean wasAccepted()
	{
		return response == InviteResponse.ACCEPTED;
	}
}
