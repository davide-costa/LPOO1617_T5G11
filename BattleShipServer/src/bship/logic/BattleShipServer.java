package bship.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.LoginRequestData;
import bship.network.data.LoginResponseData;
import bship.network.data.PlayerDisconnectedData;
import bship.network.sockets.ClientThread;
import bship.network.sockets.Server;

public class BattleShipServer
{
	private final String battleShipPlayersFileName = "bshipPlayers.bship";
	private Server server;
	private ArrayList<Player> inLobbyPlayers;
	private ArrayList<Player> inGamePlayers;
	private HashMap<String, Player> battleshipPlayers;
	
	public static void main(String argv[])
	{
		BattleShipServer battleShipServer = new BattleShipServer();
	}
	
	public BattleShipServer()
	{
		server = new Server(this);
		server.startServer();
		loadBattleShipPlayersFromFile();
		inLobbyPlayers = new ArrayList<Player>();
		inGamePlayers = new ArrayList<Player>();
	}
	
	public ArrayList<Player> getInLobbyPlayers()
	{
		return inLobbyPlayers;
	}
	
	public ArrayList<Player> getInGamePlayers()
	{
		return inGamePlayers;
	}
	
	public ArrayList<Player> getOnlinePlayers() 
	{
		ArrayList<Player> onlinePlayers = new ArrayList<Player>();
		onlinePlayers.addAll(inLobbyPlayers);
		onlinePlayers.addAll(inGamePlayers);
		
		return onlinePlayers;
	}

	public HashMap<String, Player> getBattleshipPlayers()
	{
		return battleshipPlayers;
	}
	
	public void saveBattleShipPlayersFromFile()
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(battleShipPlayersFileName);
	        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
	        objOut.writeObject(battleshipPlayers);
	        objOut.close();
	        fileOut.close();
		}
		catch (FileNotFoundException e)
		{
            System.out.println("An error ocurered saving the file containing your battleshipPlayers. The server will try again on the next change needed to save. Error: " + e.getMessage());
		}
		catch (IOException e)
		{	
			System.out.println("An error ocurered saving the file containing your battleshipPlayers. The server will try again on the next change needed to save. Error: " + e.getMessage());
		}
	}
	
	public void loadBattleShipPlayersFromFile()
	{
		try
		{
			FileInputStream fileIn = new FileInputStream(battleShipPlayersFileName);
	        ObjectInputStream objIn = new ObjectInputStream(fileIn);
	        battleshipPlayers = (HashMap<String, Player>) objIn.readObject();
	        fileIn.close();
	        objIn.close();
		}
		catch (FileNotFoundException | ClassNotFoundException e)
		{
            System.out.println("An error ocurered loading the file containing your battleshipPlayers. The server will try again on the next change needed to save. Error: " + e.getMessage());
		}
		catch (IOException e)
		{	
			System.out.println("An error ocurered loading the file containing your battleshipPlayers. The server will try again on the next change needed to save. Error: " + e.getMessage());
		}
	}
	
	public boolean newPlayerConnected(ClientThread thread, BattleShipData data)
	{
		LoginRequestData login = (LoginRequestData) data;
		LoginResponseData response;
		String username = login.getUsername();
		String password = login.getPassword();
		
		Player newPlayer;
		if(battleshipPlayers.containsKey(username))
		{
			newPlayer = battleshipPlayers.get(username);
			if(!password.equals(newPlayer.getPassword()) || !(newPlayer.getState() instanceof Offline))
			{
				response = new LoginResponseData(false);
				try
				{
					thread.sendData(response);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		}
		else
		{
			newPlayer = new Player(this, username, password);
			battleshipPlayers.put(username, newPlayer);
		}
		
		newPlayer.setThread(thread);
		thread.setPlayer(newPlayer);
		inLobbyPlayers.add(newPlayer);
		
		newPlayer.setState(new InLobby(newPlayer));
		response = new LoginResponseData(true);
		try
		{
			newPlayer.sendData(response);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendOnlinePlayersInfoToAllPlayers();
		saveBattleShipPlayersFromFile();
		return true;
	}
	
	public void sendOnlinePlayersInfoToPlayer(Player player)
	{
		ArrayList<String> playerNames = new ArrayList<String>();
		for (Player currPlayer : inLobbyPlayers)
			playerNames.add(currPlayer.getUsername());
		
		LobbyData namesData = new LobbyInfoData(playerNames);
		try
		{
			player.sendData(namesData);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendOnlinePlayersInfoToAllPlayers()
	{
		ArrayList<String> playerNames = new ArrayList<String>();
		for (Player currPlayer : inLobbyPlayers)
			playerNames.add(currPlayer.getUsername());
		
		LobbyData namesData = new LobbyInfoData(playerNames);
		try
		{
			for (Player currPlayer : inLobbyPlayers)
				currPlayer.sendData(namesData);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playerDisconnected(ClientThread thread)
	{
		Player player = thread.getPlayer();
		
		//In case the player failed to log in, the thread will terminate but no PLayer has been assign to it
		if(player == null)
			return;
		PlayerState currState = player.getState();
		player.setState(new Offline(player));
		
		//only is removed from one of the lists, but we dont know in which one the player is, so we need to check in both
		inLobbyPlayers.remove(player);
		inGamePlayers.remove(player);
		
		if (currState instanceof InLobby)
			sendOnlinePlayersInfoToAllPlayers();
		else
		{
			try
			{
				player.getOpponent().sendData(new PlayerDisconnectedData());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void stopServer()
	{
		server.stopServer();
	}

	public boolean invitePlayer(String inviterPlayerName, String invitedPlayerName) 
	{
		Player inviterPlayer = battleshipPlayers.get(inviterPlayerName);
		Player invitedPlayer = battleshipPlayers.get(invitedPlayerName);

		
		if (invitedPlayer == null)
		{
			return false;
		}
		
		if(invitedPlayer.isBusy())
			return false;
		
		invitedPlayer.setOpponent(inviterPlayer);
		LobbyInvitedData inviteData = new LobbyInvitedData(inviterPlayerName);
		try
		{
			invitedPlayer.sendData(inviteData);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean canGameBeInitiated(Player player1, Player player2)
	{
		PlayerState player1State = player1.getState();
		PlayerState player2State = player2.getState();
		
		return (player1State instanceof ReadyForGame) && (player2State instanceof ReadyForGame);
	}
	
}
