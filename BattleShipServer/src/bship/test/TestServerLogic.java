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
import bship.logic.InLobby;
import bship.logic.InShipPlacement;
import bship.logic.Player;
import bship.logic.PlayerState;
import bship.network.data.*;

public class TestServerLogic
{
	BattleShipServer server;
	Player player1;
	Player player2;
	Player player1Opponent;
	Player player2Opponent;
	PlayerState player1State;
	PlayerState player2State;
	
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
		
		/**
		* For reading input from server.
		*/
		ObjectInputStream socket_input;

		/**
		* For writing output to server.
		*/
		ObjectOutputStream socket_output;
		
		Socket socket = new Socket("127.0.0.1", 5555);
		//get I/O from socket
		socket_output = new ObjectOutputStream(socket.getOutputStream());
		socket_input = new ObjectInputStream(socket.getInputStream());
		BattleShipData data = new LoginRequestData("battleship", "lpoo");
		socket_output.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(1, server.getOnlinePlayers().size());

		LoginResponseData response = (LoginResponseData)socket_input.readObject();
		assertNotNull(response);
		assertTrue(response.isSucceeded());
		
		//disconnect
		socket.close();
		Thread.sleep(200);
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(0, server.getOnlinePlayers().size());
		

		server.stopServer();
	}

	@Test
	public void TestLoginWithAccountPreviouslyCreated() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		/**
		* For reading input from server.
		*/
		ObjectInputStream socket_input;

		/**
		* For writing output to server.
		*/
		ObjectOutputStream socket_output;
		
		Socket socket = new Socket("127.0.0.1", 5555);
		//get I/O from socket
		socket_output = new ObjectOutputStream(socket.getOutputStream());
		socket_input = new ObjectInputStream(socket.getInputStream());
		
		//Create account
		BattleShipData data = new LoginRequestData("battleship", "lpoo");
		socket_output.writeObject(data);
		//Logout
		socket.close();
		
		Thread.sleep(200); //wait for server to close the socket on the other side
		
		//Attempt to log in with wrong password
		socket = new Socket("127.0.0.1", 5555);
		socket_output = new ObjectOutputStream(socket.getOutputStream());
		socket_input = new ObjectInputStream(socket.getInputStream());
		data = new LoginRequestData("battleship", "lpoo");
		socket_output.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket

		LoginResponseData response = (LoginResponseData)socket_input.readObject();
		//Ensure server responses with login failed
		assertNotNull(response);
		assertTrue(response.isSucceeded());
		//Ensure the server does not add the player to the list of online players
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(1, server.getOnlinePlayers().size());
		
		//disconnect
		socket.close();
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestWrongPasswordLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		/**
		* For reading input from server.
		*/
		ObjectInputStream socket_input;

		/**
		* For writing output to server.
		*/
		ObjectOutputStream socket_output;
		
		Socket socket = new Socket("127.0.0.1", 5555);
		//get I/O from socket
		socket_output = new ObjectOutputStream(socket.getOutputStream());
		socket_input = new ObjectInputStream(socket.getInputStream());
		
		//Create account
		BattleShipData data = new LoginRequestData("battleship", "lpoo");
		socket_output.writeObject(data);
		//Logout
		socket.close();
		
		Thread.sleep(200); //wait for server to close the socket on the other side
		
		//Attempt to log in with wrong password
		socket = new Socket("127.0.0.1", 5555);
		socket_output = new ObjectOutputStream(socket.getOutputStream());
		socket_input = new ObjectInputStream(socket.getInputStream());
		data = new LoginRequestData("battleship", "wrongpwd");
		socket_output.writeObject(data);
		
		Thread.sleep(200); //wait for the other thread to read information from the socket

		LoginResponseData response = (LoginResponseData)socket_input.readObject();
		//Ensure server responses with login failed
		assertNotNull(response);
		assertFalse(response.isSucceeded());
		//Ensure the server does not add the player to the list of online players
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(0, server.getOnlinePlayers().size());
		
		//disconnect
		socket.close();
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestInviteLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServer();
		
		ObjectInputStream socket1_input;
		ObjectOutputStream socket1_output;
		ObjectInputStream socket2_input;
		ObjectOutputStream socket2_output;
	

		
		Socket socket1 = new Socket("127.0.0.1", 5555);
		Socket socket2 = new Socket("127.0.0.1", 5555);
		
		//get I/O from socket
		socket1_output = new ObjectOutputStream(socket1.getOutputStream());
		socket1_input = new ObjectInputStream(socket1.getInputStream());
		socket2_output = new ObjectOutputStream(socket2.getOutputStream());
		socket2_input = new ObjectInputStream(socket2.getInputStream());
		
		//Create account
		BattleShipData data1 = new LoginRequestData("battleship1", "lpoo");
		BattleShipData data2 = new LoginRequestData("battleship2", "lpoo");
		
		socket1_output.writeObject(data1);
		socket2_output.writeObject(data2);
		
		Thread.sleep(200); //wait for server to acknowledge the logins
		
		LoginResponseData response1 = (LoginResponseData)socket1_input.readObject();
		LoginResponseData response2 = (LoginResponseData)socket2_input.readObject();
		
		//Ensure server responses with login failed
		assertNotNull(response1);
		assertNotNull(response2);
		
		assertTrue(response1.isSucceeded());
		assertTrue(response2.isSucceeded());
		
		LobbyInviteData invite;
		LobbyInvitedData receivedInvite;
		LobbyInviteResponseData inviteResponse;
		
		
		//try invite to nonexistent player
		invite = new LobbyInviteData("notfound");
		socket1_output.writeObject(invite);
		inviteResponse = (LobbyInviteResponseData) socket1_input.readObject();
		assertFalse(inviteResponse.wasAccepted());
		
		//try inviting existing player and he responds yes
		//try inviting existing player and he responds no
		invite = new LobbyInviteData("battleship2");
		socket1_output.writeObject(invite);
		receivedInvite = (LobbyInvitedData) socket2_input.readObject();
		assertNotNull(receivedInvite);
		inviteResponse = new LobbyInviteResponseData(false);
		socket2_output.writeObject(inviteResponse);
		inviteResponse = (LobbyInviteResponseData) socket1_input.readObject();
		assertFalse(inviteResponse.wasAccepted());
		Thread.sleep(200);
		GetCurrentPlayersInfo();
		assertNull(player1Opponent);
		assertNull(player2Opponent);
		assertTrue(player1State instanceof InLobby);
		assertTrue(player2State instanceof InLobby);
		
		
		invite = new LobbyInviteData("battleship2");
		socket1_output.writeObject(invite);
		receivedInvite = (LobbyInvitedData) socket2_input.readObject();
		assertNotNull(receivedInvite);
		inviteResponse = new LobbyInviteResponseData(true);
		socket2_output.writeObject(inviteResponse);
		inviteResponse = (LobbyInviteResponseData) socket1_input.readObject();
		assertTrue(inviteResponse.wasAccepted());
		Thread.sleep(200);
		GetCurrentPlayersInfo();
		assertNotNull(player1Opponent);
		assertNotNull(player2Opponent);
		assertSame(player1, player2Opponent);
		assertSame(player2, player1Opponent);
		assertTrue(player1State instanceof InShipPlacement);
		assertTrue(player2State instanceof InShipPlacement);

		
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	private void GetCurrentPlayersInfo()
	{
		player1 = server.getBattleshipPlayers().get("battleship1");
		player2 = server.getBattleshipPlayers().get("battleship2");
		player1Opponent = player1.getOpponent();
		player2Opponent = player2.getOpponent();
		player1State = player1.getState();
		player2State = player2.getState();
	}
}
