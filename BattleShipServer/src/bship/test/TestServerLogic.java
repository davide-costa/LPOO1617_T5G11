package bship.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bship.logic.BattleShipServer;

public class TestServerLogic
{
	@Test
	public void TestConstructor()
	{
		BattleShipServer server = new BattleShipServer();
		assertEquals(server.getOnlinePlayers().size(), 0);
		assertEquals(server.getBattleshipPlayers().size(), 0);
	}
}
