package bship.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
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
import bship.network.data.GameResultData.GameResult;
import bship.network.data.LobbyInviteResponseData.InviteResponse;

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
		ReadInLobbyPlayersFromServer(socket1Input);
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
		ReadInLobbyPlayersFromServer(socket2Input);
	}
	
	private void ReadInLobbyPlayersFromServer(ObjectInputStream socketInput) throws ClassNotFoundException, IOException
	{
		LobbyInfoData playersData;
		playersData  = (LobbyInfoData) socketInput.readObject();
		ArrayList<Player> actualPlayers = new ArrayList<Player>(server.getInLobbyPlayers());
		assertNotNull(actualPlayers);
		ArrayList<String> actualPlayersNames = new ArrayList<String>();
		for (Player player : actualPlayers)
		{
			actualPlayersNames.add(player.getUsername());
		}
		ArrayList<String> dataPlayersNames = playersData.getOnlinePlayersNames();
		assertNotNull(dataPlayersNames);
		Collections.sort(actualPlayersNames);
		Collections.sort(dataPlayersNames);
		assertEquals(actualPlayersNames, dataPlayersNames);
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
	
	private void AssertPlayersAreInGame()
	{
		AssertPlayer1AndPlayer2AreOpponents();
		assertTrue(player1State instanceof InGame);
		assertTrue(player2State instanceof InGame);
	}
	
	@Test
	public void TestConstructor() throws InterruptedException
	{
		server = new BattleShipServerTests();
		assertEquals(server.getOnlinePlayers().size(), 0);
		assertEquals(server.getBattleshipPlayers().size(), 0);
		
		Thread.sleep(200);
		server.stopServer();
	}
	
	@Test
	public void TestAccountCreationLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServerTests();
		
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
		server = new BattleShipServerTests();
		
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
		assertEquals(1, server.getInLobbyPlayers().size());
		
		//disconnect
		socket1.close();
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestWrongPasswordLogin() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServerTests();
		
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
		server = new BattleShipServerTests();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		ReadInLobbyPlayersFromServer(socket1Input);
		
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
		assertEquals("player1", receivedInvite.getInviterName());
		inviteResponse = new LobbyInviteResponseData(InviteResponse.REJECTED);
		socket2Output.writeObject(inviteResponse);
		inviteResponse = (LobbyInviteResponseData) socket1Input.readObject();
		assertFalse(inviteResponse.wasAccepted());
		Thread.sleep(200);
		GetCurrentPlayersInfo();
		assertNull(player1Opponent);
		assertNull(player2Opponent);
		assertTrue(player1State instanceof InLobby);
		assertTrue(player2State instanceof InLobby);
		assertTrue(server.getInLobbyPlayers().contains(player1));
		assertTrue(server.getInLobbyPlayers().contains(player2));
		
		//try inviting existing player and he responds yes
		invite = new LobbyInviteData("player2");
		socket1Output.writeObject(invite);
		receivedInvite = (LobbyInvitedData) socket2Input.readObject();
		assertNotNull(receivedInvite);
		assertEquals("player1", receivedInvite.getInviterName());
		inviteResponse = new LobbyInviteResponseData(InviteResponse.ACCEPTED);
		socket2Output.writeObject(inviteResponse);
		inviteResponse = (LobbyInviteResponseData) socket1Input.readObject();
		assertTrue(inviteResponse.wasAccepted());
		Thread.sleep(200);
		AssertPlayer1AndPlayer2AreOpponents();
		assertTrue(player1State instanceof InShipPlacement);
		assertTrue(player2State instanceof InShipPlacement);
		assertFalse(server.getInLobbyPlayers().contains(player1));
		assertFalse(server.getInLobbyPlayers().contains(player2));
		
		Thread.sleep(200);
		

		server.stopServer();
	}
	
	@Test
	public void TestShipPlacementLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServerTests();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		ReadInLobbyPlayersFromServer(socket1Input);
		
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
		StartGameData startData1 = (StartGameData) socket1Input.readObject();
		assertNotNull(startData1);
		StartGameData startData2 = (StartGameData) socket2Input.readObject();
		assertNotNull(startData2);
		GetCurrentPlayersInfo();
		assertTrue(player1State instanceof InGame);
		assertTrue(player2State instanceof InGame);
				
		Thread.sleep(200);
		server.stopServer();
	}
	
	@Test
	public void TestInGameLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServerTests();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		ReadInLobbyPlayersFromServer(socket1Input);
		
		
		
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		player1.setState(new InGame(player1));
		player2.setState(new InGame(player2));
		AssertPlayer1AndPlayer2AreOpponents();
		
		//Make 50 plays each one in reverse order of the last one and ensure all goes well
		for (int i = 0; i < 50; i++)
		{
			MakeAPlayerAndReadResults(socket1Input, socket1Output, socket2Input, socket2Output, false);
			MakeAPlayerAndReadResults(socket2Input, socket2Output, socket1Input, socket1Output, false);
		}
		
		Thread.sleep(200);
		server.stopServer();
		
	}
	
	private void MakeAPlayerAndReadResults(ObjectInputStream socket1Input, ObjectOutputStream socket1Output, ObjectInputStream socket2Input, ObjectOutputStream socket2Output, boolean endOfGame) throws IOException, ClassNotFoundException
	{
		//This is not actually a string but since it is an Object inside the GameShootData class, we use a String to test it because it works with any Object if it works with a String
		//The client currently doesn't send a String inside the class GameShootData but it could send anything without having to modify the server code (maintainable code)
		GameShootData shootData1;
		GameShootData shootData2;
		GameResultData resultData1;
		GameResultData resultData2;
		//Assert shoot coords arrive correctly
		shootData1 = new GameShootData(3,5);
		socket1Output.writeObject(shootData1);
		shootData2 = (GameShootData) socket2Input.readObject();
		assertNotNull(shootData2);
		assertEquals(shootData2.getX(), shootData1.getX());
		assertEquals(shootData2.getY(), shootData1.getY());
		AssertPlayersAreInGame();

		// Assert game results arrive correctly
		resultData1 = new GameResultData(GameResult.WATER, endOfGame);
		socket1Output.writeObject(resultData1);
		resultData2 = (GameResultData) socket2Input.readObject();
		assertNotNull(shootData2);
		GameResult result = resultData2.getResult();
		boolean endOfGameResult = resultData2.isEndOfGame();
		assertNotNull(result);
		assertNotNull(endOfGameResult);
		assertEquals(GameResult.WATER, result);
		assertEquals(endOfGame, endOfGameResult);
		AssertPlayersAreInGame();
		
		if (endOfGame)
			PerformEndOfGameProcedure(socket1Input, socket1Output, socket2Input, socket2Output);
	}
	
	private void PerformEndOfGameProcedure(ObjectInputStream socket1Input, ObjectOutputStream socket1Output, ObjectInputStream socket2Input, ObjectOutputStream socket2Output) throws IOException, ClassNotFoundException
	{
		EndOfGameData endData1;
		EndOfGameData endData2;
		String winnerGameMap1;
		String winnerGameMap2;
		
		winnerGameMap1 = new String("6, 4, 2, 4,6 f, sd, 56, 6, d");
		endData1 = new EndOfGameData(winnerGameMap1);
		socket1Output.writeObject(endData1);
		endData2 = (EndOfGameData) socket2Input.readObject();
		assertNotNull(endData2);
		assertTrue(endData2 instanceof EndOfGameData);
		winnerGameMap2 = (String) endData2.getWinnerGameMap();
		assertNotNull(winnerGameMap2);
		assertEquals(winnerGameMap1, winnerGameMap2);
		GetCurrentPlayersInfo();
		assertNull(player1Opponent);
		assertNull(player2Opponent);
		assertTrue(player1State instanceof InLobby);
		assertTrue(player2State instanceof InLobby);
		ReadInLobbyPlayersFromServer(socket1Input);
		ReadInLobbyPlayersFromServer(socket2Input);
	}
	
	@Test
	public void TestEndOfGameLogic() throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		AssertEndOfGamePerformsCorrectly(false);
		AssertEndOfGamePerformsCorrectly(true);	
	}
	
	private void AssertEndOfGamePerformsCorrectly(boolean shootFirst) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException
	{
		server = new BattleShipServerTests();
		
		LoginPlayer1("player1", "lpoo");
		LoginPlayer2("player2", "lpoo");
		ReadInLobbyPlayersFromServer(socket1Input);
		
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		player1.setState(new InGame(player1));
		player2.setState(new InGame(player2));
		AssertPlayersAreInGame();
		
		if (shootFirst)
			MakeAPlayerAndReadResults(socket1Input, socket1Output, socket2Input, socket2Output, false);
		
		MakeAPlayerAndReadResults(socket2Input, socket2Output, socket1Input, socket1Output, true);
		Thread.sleep(200);
		GetCurrentPlayersInfo();
		assertTrue (player1State instanceof InLobby);
		assertTrue (player1State instanceof InLobby);
		assertNull(player1Opponent);
		assertNull(player2Opponent);

		Thread.sleep(200);
		server.stopServer();
	}
	
	
}
