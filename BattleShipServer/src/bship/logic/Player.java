package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;
import bship.network.sockets.ClientThread;

public class Player
{
	private BattleShipServer battleShipServer;
	private ClientThread thread;
	private String username;
	private String password;
	private Player opponent;

	private PlayerState state;
	
	
	public Player(BattleShipServer battleShipServer, String username, String password)
	{
		this.battleShipServer = battleShipServer;
		this.username = new String(username);
		this.password = new String(password);
	}
	
	public void setThread(ClientThread thread) 
	{
		this.thread = thread;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public String getPassword() 
	{
		return password;
	}

	public Player getOpponent()
	{
		return opponent;
	}
	
	public void HandleReceivedData(BattleShipData receivedData) throws IOException
	{
		state.HandleReceivedData(receivedData);
	}

	public void sendData(BattleShipData data) throws IOException 
	{
		thread.sendData(data);	
	}
	
	public void setState(PlayerState state) 
	{
		this.state = state;
	}
	
	public PlayerState getState() 
	{
		return state;
	}
	
	public BattleShipServer getBattleShipServer() 
	{
		return battleShipServer;
	}

	public boolean isBusy() 
	{
		if(state instanceof InGame || opponent != null)
			return false;
			
		return true;	
	}
}
