package bship.logic;

import bship.network.data.BattleShipData;
import bship.network.sockets.ClientThread;

public class Player
{
	private ClientThread thread;
	private String username;
	private String password;
	private Player opponent;
	private PlayerState state;
	
	
	public Player(String username, String password)
	{
		this.state = new InLobby();
		this.username = username;
		this.password = password;
	}
	
	public void setThread(ClientThread thread) 
	{
		this.thread = thread;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void HandleReceivedData(BattleShipData receivedData)
	{
		state.HandleReceivedData(receivedData);
	}
}
