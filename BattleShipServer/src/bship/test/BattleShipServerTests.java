package bship.test;

import java.util.HashMap;

import bship.logic.BattleShipServer;
import bship.logic.Player;

public class BattleShipServerTests extends BattleShipServer
{
	public BattleShipServerTests()
	{
		super();
		battleshipPlayers = new HashMap<String, Player>();
	}
	
	public void saveBattleShipPlayersFromFile()
	{
		return;
	}
	
	public void loadBattleShipPlayersFromFile()
	{
		return;
	}
	
}
