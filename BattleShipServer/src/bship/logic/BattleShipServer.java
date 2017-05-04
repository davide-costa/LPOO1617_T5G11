package bship.logic;

import java.util.ArrayList;
import java.util.HashMap;

import bship.network.data.BattleShipData;
import bship.network.data.LoginRequestData;
import bship.network.data.LoginResponseData;
import bship.network.sockets.ClientThread;
import bship.network.sockets.Server;

public class BattleShipServer
{
	private Server server;
	private ArrayList<Player> onlinePlayers;
	private HashMap<String, Player> battleshipPlayers;
	
	public static void main(String argv[])
	{
		BattleShipServer battleShipServer = new BattleShipServer();
	}
	
	public BattleShipServer()
	{
		server = new Server(this);
		server.startServer();
		battleshipPlayers = new HashMap<String, Player>();
		onlinePlayers = new ArrayList<Player>();
	}
	
	public ArrayList<Player> getOnlinePlayers()
	{
		return onlinePlayers;
	}

	public HashMap<String, Player> getBattleshipPlayers()
	{
		return battleshipPlayers;
	}

	public LoginResponseData newPlayerConnected(ClientThread thread, BattleShipData data)
	{
		LoginRequestData login = (LoginRequestData) data;
		String username = login.getUsername();
		String password = login.getPassword();
		
		Player newPlayer;
		if(battleshipPlayers.containsKey(username))
		{
			newPlayer = battleshipPlayers.get(username);
			if(password != newPlayer.getPassword() || !(newPlayer.getState() instanceof Offline))
				return new LoginResponseData(false);
		}
		else
		{
			newPlayer = new Player(username, password);
			battleshipPlayers.put(username, newPlayer);
		}
		
		newPlayer.setThread(thread);
		thread.setPlayer(newPlayer);
		onlinePlayers.add(newPlayer);
		
		return new LoginResponseData(true);
	}
	
	public void playerDisconnected(ClientThread thread)
	{
		Player player = thread.getPlayer();
		player.setState(new Offline(player)); //TODO this player somehow is null when it disconnects and an exception is thrown
		onlinePlayers.remove(player);
	}
	
}
