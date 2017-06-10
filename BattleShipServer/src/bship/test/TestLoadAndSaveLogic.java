package bship.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import bship.logic.BattleShipServer;
import bship.logic.Player;

public class TestLoadAndSaveLogic
{
	@Test
	public void TestSaveAndLoadFromFile() throws InterruptedException
	{
		BattleShipServerTests server = new BattleShipServerTests();
		Player player = new Player(server, "battleship", "lpoo");
		server.addPlayer(player);
		server.forceSaveBattleShipPlayersFromFile();
		
		server.forceLoadBattleShipPlayersFromFile();
		HashMap<String, Player> players = server.getBattleshipPlayers();
		assertNotNull(players);
		assertEquals(1, players.size());
		Player playerInServer = players.get("battleship");
		assertNotNull(playerInServer);
		assertEquals(player.getUsername(), playerInServer.getUsername());
		assertEquals(player.getPassword(), playerInServer.getPassword());
		
		Thread.sleep(200);
		server.stopServer();
		
	}
}
