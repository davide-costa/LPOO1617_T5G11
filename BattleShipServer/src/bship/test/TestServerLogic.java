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
	public void TestAccountCreation() throws UnknownHostException, IOException, InterruptedException
	{
		BattleShipServer server = new BattleShipServer();
		assertEquals(server.getOnlinePlayers().size(), 0);
		assertEquals(server.getBattleshipPlayers().size(), 0);
		
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
		
		Thread.sleep(2); //wait for the other thread to read information from the socket
		//TODO
		//read response from server and ensure it is correct
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(1, server.getOnlinePlayers().size());
		
		//disconnect
		socket.close();
		Thread.sleep(2);
		assertEquals(1, server.getBattleshipPlayers().size());
		assertEquals(0, server.getOnlinePlayers().size());
		
		
	}
	
	
}
