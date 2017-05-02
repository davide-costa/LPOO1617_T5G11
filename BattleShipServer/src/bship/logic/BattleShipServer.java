package bship.logic;

import java.util.ArrayList;

import bship.network.sockets.ClientThread;
import bship.network.sockets.Server;

public class BattleShipServer
{
	private Server server;
	private ArrayList<Player> players;
	
	public static void main(String argv[])
	{
		BattleShipServer battleShipServer = new BattleShipServer();
	}
	
	public BattleShipServer()
	{
		server = new Server(this);
		server.startServer();
		players = new ArrayList<Player>();
	}
	
	public void newPlayerConnected(ClientThread thread)
	{
		Player newPlayer = new Player(thread);
		thread.setPlayer(newPlayer);
		players.add(newPlayer);
	}
}
