package bship.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import bship.logic.BattleShipServer;
import bship.network.data.*;

public class TestServerLogic
{
	@Test
	public void TestConstructor()
	{
		BattleShipServer server = new BattleShipServer();
		assertEquals(server.getOnlinePlayers().size(), 0);
		assertEquals(server.getBattleshipPlayers().size(), 0);
	}
	
	@Test
	public void TestAccountCreationLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		BattleShipServer server = new BattleShipServer();
		
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
		
		
	}
	
	@Test
	public void TestWrongPasswordLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		BattleShipServer server = new BattleShipServer();
		
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
		
		
	}
	
}
