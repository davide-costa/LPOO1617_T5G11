package bship.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import bship.logic.Coords;
import bship.logic.Game;
import bship.logic.MultiplayerOpponent;
import bship.network.data.GameShootData;
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
		GameShootData correctData = new GameShootData(shootCoords);
		opponent.shoot(shootCoords);
		assertTrue(clientSocket.getLastBattleShipDataSent() instanceof GameShootData);
		GameShootData receivedData =  (GameShootData)clientSocket.getLastBattleShipDataSent();
		assertEquals(correctData.getCoords(), receivedData.getCoords());
	}
	
	@Test
	public void TestUpdateGameShootData() throws IOException
	{
		GameTests game = new GameTests();
		ClientSocketTests clientSocket = new ClientSocketTests();
		MultiplayerOpponent opponent = new MultiplayerOpponent(game, clientSocket);
		Coords shootCoords = new Coords(3, 5);
		GameShootData shootData = new GameShootData(shootCoords);
		GameResult result = GameResult.HIT;
		GameResultData resultData = new GameResultData(result, false);
		game.setCurrResult(result);
		game.setEndOfGame(false);
		clientSocket.simulateReceptionOfData(shootData);
		
		assertEquals(shootCoords, game.getLastReceivedCoords());
		assertEquals(resultData, clientSocket.getLastBattleShipDataSent());
	}
}
