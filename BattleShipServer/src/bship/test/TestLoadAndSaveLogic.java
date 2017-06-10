package bship.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import bship.logic.BattleShipServer;
import bship.logic.Player;

public class TestLoadAndSaveLogic
{
	@Test
	public void TestSaveAndLoadFromFile()
	{
		BattleShipServerTests server = new BattleShipServerTests();
		Player player = new Player(server, "battleship", "lpoo");
		server.addPlayer(player);
		server.forceSaveBattleShipPlayersFromFile();
		
		server.forceLoadBattleShipPlayersFromFile();
		HashMap<String, Player> players = server.getBattleshipPlayers();
		assertNotNull(players);
		assertEquals(1, players.size());
		assertEquals(player.getUsername(), players.get(0).getUsername());
		assertEquals(player.getPassword(), players.get(0).getPassword());
		
		
	}
}
