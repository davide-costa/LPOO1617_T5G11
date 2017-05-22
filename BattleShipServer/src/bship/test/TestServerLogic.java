package bship.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.junit.Test;

import bship.logic.BattleShipServer;
import bship.logic.InGame;
import bship.logic.InLobby;
import bship.logic.InShipPlacement;
import bship.logic.Player;
import bship.logic.PlayerState;
import bship.logic.ReadyForGame;
import bship.network.data.*;

public class TestServerLogic
{
	BattleShipServer server;
	Socket socket1;
	Socket socket2;
	ObjectInputStream socket1Input;
	ObjectOutputStream socket1Output;
	ObjectInputStream socket2Input;
	ObjectOutputStream socket2Output;
	String player1Name;
	String player2Name;
	String player1Password;	
	String player2Password;	
	Player player1;
	Player player2;
	Player player1Opponent;
	Player player2Opponent;
	PlayerState player1State;
	PlayerState player2State;
	
	private void LoginPlayer(Player player, String playerName, String password, Socket socket, ObjectInputStream socketInput, ObjectOutputStream socketOutput) throws IOException, ClassNotFoundException, InterruptedException
	{
		socket = new Socket("127.0.0.1", 5555);
		socketOutput = new ObjectOutputStream(socket.getOutputStream());
		socketInput = new ObjectInputStream(socket.getInputStream());
		BattleShipData data = new LoginRequestData(playerName, password);
		socketOutput.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(1, server.getOnlinePlayers().size());

		LoginResponseData response = (LoginResponseData)socketInput.readObject();
		assertNotNull(response);
		assertTrue(response.isSucceeded());
	}
	
	private void LoginPlayer1(String player1Name, String player1Password) throws ClassNotFoundException, IOException, InterruptedException
	{
		int numOfOnlinePlayers = server.getOnlinePlayers().size();		
		this.player1Name = player1Name;
		this.player1Password = player1Password;
		socket1 = new Socket("127.0.0.1", 5555);
		socket1Output = new ObjectOutputStream(socket1.getOutputStream());
		socket1Input = new ObjectInputStream(socket1.getInputStream());
		BattleShipData data = new LoginRequestData(player1Name, player1Password);
		socket1Output.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket1
		assertEquals(numOfOnlinePlayers + 1, server.getOnlinePlayers().size());

		LoginResponseData response = (LoginResponseData)socket1Input.readObject();
		assertNotNull(response);
		assertTrue(response.isSucceeded());
		player1 = server.getBattleshipPlayers().get("player1");
	}
	
	private void LoginPlayer2(String player2Name, String player2Password) throws ClassNotFoundException, IOException, InterruptedException
	{
		int numOfOnlinePlayers = server.getOnlinePlayers().size();		
		this.player2Name = player2Name;
		this.player2Password = player2Password;
		socket2 = new Socket("127.0.0.1", 5555);
		socket2Output = new ObjectOutputStream(socket2.getOutputStream());
		socket2Input = new ObjectInputStream(socket2.getInputStream());
		BattleShipData data = new LoginRequestData(player2Name, player2Password);
		socket2Output.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket1
		assertEquals(numOfOnlinePlayers + 1, server.getOnlinePlayers().size());

		LoginResponseData response = (LoginResponseData)socket2Input.readObject();
		assertNotNull(response);
		assertTrue(response.isSucceeded());
		player2 = server.getBattleshipPlayers().get("player2");
	}
		
	@Test
	public void TestConstructor() throws InterruptedException
	{
		server = new BattleShipServer();
		assertEquals(server.getOnlinePlayers().size(), 0);
		assertEquals(server.getBattleshipPlayers().size(), 0);
		
		Thread.sleep(200);
		server.stopServer();
	}
	
	@Test
	public void TestAccountCreationLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		LoginPlayer1("battleship", "lpoo");
		Thread.sleep(200); //wait for the other thread to read information from the socket
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(1, server.getOnlinePlayers().size());

		//disconnect
		socket1.close();
		Thread.sleep(200);
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(0, server.getOnlinePlayers().size());
		

		server.stopServer();
	}

	@Test
	public void TestLoginWithAccountPreviouslyCreated() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		LoginPlayer1("battleship", "lpoo");
		assertEquals(1, server.getBattleshipPlayers().size());
		//Logout
		socket1.close();
		
		Thread.sleep(200); //wait for server to close the socket1 on the other side
		
		LoginRequestData data;
		
		//Attempt to log in with right password
		LoginPlayer1("battleship", "lpoo");
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(1, server.getOnlinePlayers().size());
		
		//disconnect
		socket1.close();
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestWrongPasswordLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		LoginPlayer1("battleship", "lpoo");
		assertEquals(1, server.getBattleshipPlayers().size());
		//Logout
		socket1.close();
		
		Thread.sleep(200); //wait for server to close the socket1 on the other side
		
		LoginRequestData data;
		//Attempt to log in with wrong password
		socket1 = new Socket("127.0.0.1", 5555);
		socket1Output = new ObjectOutputStream(socket1.getOutputStream());
		socket1Input = new ObjectInputStream(socket1.getInputStream());
		data = new LoginRequestData("battleship", "wrongpwd");
		assertEquals(1, server.getBattleshipPlayers().size());
		socket1Output.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket1

		LoginResponseData response = (LoginResponseData)socket1Input.readObject();
		//Ensure server responses with login failed
		assertNotNull(response);
		assertFalse(response.isSucceeded());
		//Ensure the server does not add the player to the list of online players
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(0, server.getOnlinePlayers().size());
		
		//disconnect
		socket1.close();
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestInviteLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		
		LobbyInviteData invite;
		LobbyInvitedData receivedInvite;
		LobbyInviteResponseData inviteResponse;
		
		
		//try invite to nonexistent player
		invite = new LobbyInviteData("notfound");
		socket1Output.writeObject(invite);
		inviteResponse = (LobbyInviteResponseData) socket1Input.readObject();
		assertFalse(inviteResponse.wasAccepted());
		
		//try inviting existing player and he responds no
		invite = new LobbyInviteData("player2");
		socket1Output.writeObject(invite);
		receivedInvite = (LobbyInvitedData) socket2Input.readObject();
		assertNotNull(receivedInvite);
		inviteResponse = new LobbyInviteResponseData(false);
		socket2Output.writeObject(inviteResponse);
		inviteResponse = (LobbyInviteResponseData) socket1Input.readObject();
		assertFalse(inviteResponse.wasAccepted());
		Thread.sleep(200);
		GetCurrentPlayersInfo();
		assertNull(player1Opponent);
		assertNull(player2Opponent);
		assertTrue(player1State instanceof InLobby);
		assertTrue(player2State instanceof InLobby);
		
		//try inviting existing player and he responds yes
		invite = new LobbyInviteData("player2");
		socket1Output.writeObject(invite);
		receivedInvite = (LobbyInvitedData) socket2Input.readObject();
		assertNotNull(receivedInvite);
		inviteResponse = new LobbyInviteResponseData(true);
		socket2Output.writeObject(inviteResponse);
		inviteResponse = (LobbyInviteResponseData) socket1Input.readObject();
		assertTrue(inviteResponse.wasAccepted());
		Thread.sleep(200);
		AssertPlayer1AndPlayer2AreOpponents();
		assertTrue(player1State instanceof InShipPlacement);
		assertTrue(player2State instanceof InShipPlacement);

		
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestShipPlacementLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		
		ReadyForGameData data;
		
		
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		player1.setState(new InShipPlacement(player1));
		player2.setState(new InShipPlacement(player2));
		AssertPlayer1AndPlayer2AreOpponents();
		
		data = new ReadyForGameData();
		socket1Output.writeObject(data);
		data = (ReadyForGameData) socket2Input.readObject();
		assertNotNull(data);
		GetCurrentPlayersInfo();
		assertTrue(player1State instanceof ReadyForGame);
		assertTrue(player2State instanceof InShipPlacement);
		data = new ReadyForGameData();
		socket2Output.writeObject(data);
		StartGameData startData = (StartGameData) socket1Input.readObject();
		assertNotNull(startData);
		GetCurrentPlayersInfo();
		assertTrue(player1State instanceof InGame);
		assertTrue(player2State instanceof InGame);
				
		Thread.sleep(200);
		server.stopServer();
	}
	
	@Test
	public void TestInGameLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		player1.setState(new InGame(player1));
		player2.setState(new InGame(player2));
		AssertPlayer1AndPlayer2AreOpponents();
		
		Thread.sleep(200);
		server.stopServer();
		
	}
	
	private void GetCurrentPlayersInfo()
	{
		player1 = server.getBattleshipPlayers().get("player1");
		player2 = server.getBattleshipPlayers().get("player2");
		player1Opponent = player1.getOpponent();
		player2Opponent = player2.getOpponent();
		player1State = player1.getState();
		player2State = player2.getState();
	}
	
	private void AssertPlayer1AndPlayer2AreOpponents()
	{
		GetCurrentPlayersInfo();
		assertNotNull(player1);
		assertNotNull(player2);
		assertNotNull(player1Opponent);
		assertNotNull(player2Opponent);
		assertSame(player1, player2Opponent);
		assertSame(player2, player1Opponent);
	}
}
