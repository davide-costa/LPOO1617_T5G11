package bship.logic;

import bship.network.sockets.ClientThread;

public class Player
{
	private ClientThread thread;
	private String nickname;
	
	public Player(ClientThread thread)
	{
		this.thread = thread;
	}
}
