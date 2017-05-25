package bship.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import bship.logic.Coords;
import bship.logic.Game;
import bship.logic.MultiplayerOpponent;
import bship.network.data.GameShootData;
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
		opponent.shoot(shootCoords);
		GameShootData correctData = new GameShootData(shootCoords);
		assertTrue(clientSocket.getLastBattleShipDataSent() instanceof GameShootData);
		GameShootData receivedData =  (GameShootData)clientSocket.getLastBattleShipDataSent();
		assertEquals(correctData.getCoords(), receivedData.getCoords());
	}
}
