package bship.network.data;

public class LobbyInviteData extends LobbyData
{
	private String invitedPlayerName;
	
	public LobbyInviteData(String invitedPlayerName)
	{
		this.invitedPlayerName = invitedPlayerName;
	}
 //vai ao servidor e tem de ver se o jogador a que se tenta convidar nao
	//esta busy ou se convidou ele outro jogador que nao o que esta a convida-lo
}
