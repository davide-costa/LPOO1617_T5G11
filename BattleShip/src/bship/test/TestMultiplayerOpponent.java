package bship.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import bship.logic.Coords;
import bship.logic.DefaultMap;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.MultiplayerOpponent;
import bship.network.data.GameShootData;
import bship.network.data.PlayerDisconnectedData;
import bship.network.data.EndOfGameData;
import bship.network.data.GameResultData;
import bship.network.data.GameResultData.GameResult;
import bship.network.sockets.Client;

public class TestMultiplayerOpponent
{
	@Test
	public void TestShoot() throws IOException
	{
		Game game = new GameTests();
		ClientSocketTests clientSocket = new ClientSocketTests();
		MultiplayerOpponent opponent = new MultiplayerOpponent(game, clientSocket);
		Coords shootCoords = new Coords(3, 5);
		GameShootData correctData = new GameShootData(3, 5);
		opponent.shoot(shootCoords);
		assertTrue(clientSocket.getLastBattleShipDataSent() instanceof GameShootData);
		GameShootData receivedData =  (GameShootData)clientSocket.getLastBattleShipDataSent();
		assertEquals(correctData.getX(), receivedData.getX());
		assertEquals(correctData.getY(), receivedData.getY());
	}
	
	@Test
	public void TestUpdateGameShootData() throws IOException
	{
		GameTests game = new GameTests();
		ClientSocketTests clientSocket = new ClientSocketTests();
		MultiplayerOpponent opponent = new MultiplayerOpponent(game, clientSocket);
		Coords shootCoords; 
		GameShootData shootData;
		GameResult result;
		GameResultData sentData;
		
		shootCoords = new Coords(3, 5);
		shootData = new GameShootData(3, 5);
		result = GameResult.HIT;
		game.setCurrResult(result);
		game.setEndOfGame(false);
		clientSocket.simulateReceptionOfData(shootData);
		
		assertEquals(shootCoords, game.getLastReceivedCoords());
		sentData = (GameResultData) clientSocket.getLastBattleShipDataSent();
		assertEquals(result, sentData.getResult());
		assertEquals(false, sentData.isEndOfGame());
		
		shootCoords = new Coords(1, 7);
		shootData = new GameShootData(1, 7);
		result = GameResult.WATER;
		game.setCurrResult(result);
		game.setEndOfGame(true);
		clientSocket.simulateReceptionOfData(shootData);
		
		assertEquals(shootCoords, game.getLastReceivedCoords());
		sentData = (GameResultData) clientSocket.getLastBattleShipDataSent();
		assertEquals(result, sentData.getResult());
		assertEquals(true, sentData.isEndOfGame());
	}
	
	@Test
	public void TestUpdateGameResultData() throws IOException
	{
		GameTests game = new GameTests();
		ClientSocketTests clientSocket = new ClientSocketTests();
		MultiplayerOpponent opponent = new MultiplayerOpponent(game, clientSocket);
		GameResultData resultData;
		GameResult result;
		EndOfGameData sentData;
		String mapImage = new String("map image");
		
		result = GameResult.HIT;
		resultData = new GameResultData(result, false);
		game.setEndOfGame(false);
		clientSocket.simulateReceptionOfData(resultData);
		
		assertEquals(result, game.getCurrResult());
		sentData = (EndOfGameData) clientSocket.getLastBattleShipDataSent();
		assertNull(sentData);
		
		result = GameResult.HIT;
		resultData = new GameResultData(result, true);
		game.setEndOfGame(true);
		game.setAllyMapImage(mapImage);
		clientSocket.simulateReceptionOfData(resultData);
		
		assertEquals(result, game.getCurrResult());
		sentData = (EndOfGameData) clientSocket.getLastBattleShipDataSent();
		assertNotNull(sentData);
		assertNotNull(sentData.getWinnerGameMap());
	}
	
	@Test
	public void TestUpdateEndOfGameData() throws IOException
	{
		GameTests game = new GameTests();
		ClientSocketTests clientSocket = new ClientSocketTests();
		MultiplayerOpponent opponent = new MultiplayerOpponent(game, clientSocket);
		EndOfGameData endData;
		
		String map = new String("map image");
		endData = new EndOfGameData(map);
		game.setOpponentMap(null);
		clientSocket.simulateReceptionOfData(endData);
		assertEquals(map, game.getWinnerMapImage());
	}
	
	@Test
	public void TestUpdatePlayerDisconnected() throws IOException
	{
		GameTests game = new GameTests();
		ClientSocketTests clientSocket = new ClientSocketTests();
		MultiplayerOpponent opponent = new MultiplayerOpponent(game, clientSocket);
		PlayerDisconnectedData data = new PlayerDisconnectedData(); 

		clientSocket.simulateReceptionOfData(data);
	}
}
