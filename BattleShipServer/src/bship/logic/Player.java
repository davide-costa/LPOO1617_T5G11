package bship.logic;

import java.io.IOException;
import java.io.Serializable;

import bship.network.data.BattleShipData;
import bship.network.sockets.ClientThread;

public class Player implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4859207641846767803L;
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
	
	public Player(BattleShipServer battleShipServer, StoragePlayer player)
	{
		this.battleShipServer = battleShipServer;
		username = new String(player.getUsername());
		password = new String(player.getPassword());
		thread = null;
		opponent = null;
		state = new Offline(this);
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

	public void setOpponent(Player opponent)
	{
		this.opponent = opponent;
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
		return (!(state instanceof InLobby) || opponent != null);
	}

	public boolean isReady() 
	{
		
		return false;
	}

}
