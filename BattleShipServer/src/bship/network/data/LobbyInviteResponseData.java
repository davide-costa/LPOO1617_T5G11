package bship.network.data;

public class LobbyInviteResponseData extends LobbyData
{
	public enum Response {ACCEPTED, UNACCEPTED, UNSUCESSFUL};
	private Response response;
	
	public LobbyInviteResponseData(Response response)
	{
		this.response = response;
	}
	
	public boolean wasAccepted()
	{
		return response == Response.ACCEPTED;
	}
}
