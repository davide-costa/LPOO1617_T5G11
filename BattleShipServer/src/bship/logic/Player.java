package bship.logic;

import bship.network.sockets.ClientThread;

public class Player
{
	private ClientThread thread;
	private String username;
	private String password;
	
	public Player()
	{
		
	}
	
	public Player(ClientThread thread)
	{
		this.thread = thread;
	}
	
	public void setThread(ClientThread thread) 
	{
		this.thread = thread;
	}
	
	public String getPassword() 
	{
		return password;
	}
}
