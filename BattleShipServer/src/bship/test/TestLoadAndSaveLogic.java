package bship.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import bship.logic.BattleShipServer;
import bship.logic.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestLoadAndSaveLogic
{
	@Test
	public void TestSaveAndLoadFromFile() throws InterruptedException, IOException
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
		
		Path path = Paths.get(server.getBattleShipPlayersFileName());
		Files.delete(path);
		
		Thread.sleep(200);
		server.stopServer();
		
	}
}
