package bship.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import bship.network.data.BattleShipData;
import bship.network.data.LobbyData;
import bship.network.data.LobbyInfoData;
import bship.network.data.LobbyInvitedData;
import bship.network.data.LoginRequestData;
import bship.network.data.LoginResponseData;
import bship.network.data.PlayerDisconnectedData;
import bship.network.sockets.ClientThread;
import bship.network.sockets.Server;

/**
 * Represents a the BattleShipServer. It is the system class that implements all the server general logic. It is the commander of the server and stores all the information necessary when the server is running. Contains the a main method that should be run to start the server.
 */
public class BattleShipServer
{
	protected  String battleShipPlayersFileName = "bshipPlayers.bship";
	private Server server;
	private ArrayList<Player> inLobbyPlayers;
	private ArrayList<Player> inGamePlayers;
	protected HashMap<String, Player> battleshipPlayers;
	
	/*
	 * Main method. Should be called to start the server.
	 */
	public static void main(String argv[])
	{
		new BattleShipServer();
	}
	
	/*
	 * BattleShipServer constructor. It is called to create by the main method to start the server.
	 */
	public BattleShipServer()
	{
		server = new Server(this);
		server.startServer();
		loadBattleShipPlayersFromFile();
		inLobbyPlayers = new ArrayList<Player>();
		inGamePlayers = new ArrayList<Player>();
	}
	
	/*
	 * Returns an ArrayList containing all players that are currently in lobby in the server, i. e., the players that are waiting for players to invite them or waiting to be invited to a game. A player is placed in lobby immediately after it successfully logs in to the server or when a game ends.
	 */
	public ArrayList<Player> getInLobbyPlayers()
	{
		return getInLobbyPlayersSynchronized();
	}
	
	private synchronized ArrayList<Player> getInLobbyPlayersSynchronized()
	{
		ArrayList<Player> playersInLobby = new ArrayList<Player>();
		playersInLobby.addAll(inLobbyPlayers);
		return playersInLobby;
	}
	
	/*
	 * Is called to inform that a certain player has left from the lobby because the server needs to know when a player leaves from lobby. Receives the Player who left from lobby as parameter.
	 * @param player The player who left from lobby.
	 */
	public synchronized void playerLeftFromLobby(Player player)
	{
		inLobbyPlayers.remove(player);
	}
	
	private ArrayList<String> getInLobbyPlayersNames()
	{
		ArrayList<String> playerNames = new ArrayList<String>();
		for (Player currPlayer : getInLobbyPlayersSynchronized())
			playerNames.add(currPlayer.getUsername());
		
		return playerNames;
	}
	
	/*
	 * A getter method for the in game players. Returns and ArrayList containing all the players that are in game. It is used only by the tests package for validating that the information is well represented inside this class.
	 * @return An ArrayList containing all the players that are currently in game.
	 */
	public ArrayList<Player> getInGamePlayers()
	{
		return inGamePlayers;
	}
	
	/*
	 * Informs that a player has entered in a game. Is called by mathods of this package to inform so.
	 * @param player The player that has entered in game.
	 */
	public void addInGamePlayer(Player player) 
	{
		inGamePlayers.add(player);
	}	
	
	/*
	 * Returns an ArrayList containing wall the players that are currently online on the server. A player is online if it is in lobby, in ship placement or in game, i. e., if he is not offline.
	 * @return An ArrayList containing the players that are currently online on the server.
	 */
	public ArrayList<Player> getOnlinePlayers() 
	{
		ArrayList<Player> onlinePlayers = new ArrayList<Player>();
		onlinePlayers.addAll(inLobbyPlayers);
		onlinePlayers.addAll(inGamePlayers);
		
		return onlinePlayers;
	}

	/*
	 * Getter for all the BattleShip players, i. e., the ones that are registered on the server, despite of being online or offline.
	 * @return The HashMap containing all the registered players in the server.
	 */
	public HashMap<String, Player> getBattleshipPlayers()
	{
		return battleshipPlayers;
	}
	
	/*
	 * Saves all battleship registered players to a file
	 */
	protected void saveBattleShipPlayersToFile()
	{
		try
		{
			ArrayList<StoragePlayer> players = new ArrayList<StoragePlayer>();
			for (Player player : battleshipPlayers.values())
			{
				StoragePlayer storagePlayer = new StoragePlayer(player);
				players.add(storagePlayer);
			}

			FileOutputStream fileOut = new FileOutputStream(battleShipPlayersFileName);
	        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
	        objOut.writeObject(players);
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
	
	/*
	 * Loads all battleship registered players from a file
	 */
	public void loadBattleShipPlayersFromFile()
	{
		try
		{
			FileInputStream fileIn = new FileInputStream(battleShipPlayersFileName);
	        ObjectInputStream objIn = new ObjectInputStream(fileIn);
	        battleshipPlayers = new HashMap<String, Player>();
	        ArrayList<StoragePlayer> storagePlayers = (ArrayList<StoragePlayer>) objIn.readObject();
	        for (StoragePlayer currStoragePlayer : storagePlayers)
	        {
		        Player player = new Player(this, currStoragePlayer);
		        battleshipPlayers.put(player.getUsername(), player);		        
	        }
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
		LoginResponseData response = new LoginResponseData();
		String username = login.getUsername();
		String password = login.getPassword();
		
		if(username.isEmpty() || password.isEmpty())
		{
			response.setAsNotSuceeded();
			thread.sendData(response);
			return false;
		}
		
		Player newPlayer;
		if(battleshipPlayers.containsKey(username))
		{
			newPlayer = battleshipPlayers.get(username);

			if(!password.equals(newPlayer.getPassword()) || !(newPlayer.getState() instanceof Offline))
			{
				response.setAsNotSuceeded();
				thread.sendData(response);
				return false;
			}
		}
		else
		{
			newPlayer = new Player(this, username, password);
			battleshipPlayers.put(username, newPlayer);
			response.setAccountAsNew();
		}
		
		newPlayer.setThread(thread);
		thread.setPlayer(newPlayer);
		inLobbyPlayers.add(newPlayer);
		
		newPlayer.setState(new InLobby(newPlayer));
		response.setAsSuceeded();

		newPlayer.sendData(response);
		sendOnlinePlayersInfoToAllPlayers();
		saveBattleShipPlayersToFile();
		return true;
	}
	
	/**  
	 * Responsible for sending the names of all players in lobby to a player.
	 * @param player the player to which the info should be sent.
	 */
	public void sendOnlinePlayersInfoToPlayer(Player player)
	{
		ArrayList<String> inLobbyPlayersNames = getInLobbyPlayersNames();
		LobbyData namesData = new LobbyInfoData(inLobbyPlayersNames);
		
		player.sendData(namesData);
	}
	
	
	/**  
	 * Responsible for sending the names of all players in lobby all players on the lobby.
	 */
	public void sendOnlinePlayersInfoToAllPlayers()
	{
		ArrayList<String> inLobbyPlayersNames = getInLobbyPlayersNames();
		LobbyData namesData = new LobbyInfoData(inLobbyPlayersNames);

		for (Player currPlayer : getInLobbyPlayers())
			currPlayer.sendData(namesData);
	}
	
	/**  
	 * Responsible for handle a player disconnected, changing the player state to Offline and handling the effects of that player in other players (in case they are in game together)
	 * @param thread the thread that is communicating with the player that disconnected.
	 */ 
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
			player.getOpponent().sendData(new PlayerDisconnectedData());
	}
	
	/**  
	 * Responsible for stop the server.
	 */ 
	public void stopServer()
	{
		server.stopServer();
	}

	/**  
	 * Responsable for invite a player, checking if the invited player is not busy.
     * @param inviterPlayerName the name of the player that has made the invitation.
     * @param invitedPlayerName the name of the player that has been invited.
     * @return true if invited. False otherwise.
	 */ 
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
		invitedPlayer.sendData(inviteData);

		return true;
	}
	
	/**  
	 * Checks if the game can be initiated by checking if both players are in state ReadyForGame.
     * @param player1 One of the players that are in InShipPlacement.
     * @param player2 The other player that are in InShipPlacement.
     * @return true if can be initiated. False otherwise.
	 */ 
	public boolean canGameBeInitiated(Player player1, Player player2)
	{
		PlayerState player1State = player1.getState();
		PlayerState player2State = player2.getState();
		
		return (player1State instanceof ReadyForGame) && (player2State instanceof ReadyForGame);
	}
}
