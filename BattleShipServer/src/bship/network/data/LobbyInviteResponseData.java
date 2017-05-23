package bship.network.data;

import bship.network.data.LobbyInviteResponseData.InviteResponse;

public class LobbyInviteResponseData extends LobbyData
{
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