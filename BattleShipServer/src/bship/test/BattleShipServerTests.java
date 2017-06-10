package bship.test;

import java.util.HashMap;

import bship.logic.BattleShipServer;
import bship.logic.Player;

public class BattleShipServerTests extends BattleShipServer
{
	
	public void addPlayer(Player player)
	{
		battleshipPlayers.put(player.getUsername(), player);
	}
	
	public BattleShipServerTests()
	{
		super();
		battleshipPlayers = new HashMap<String, Player>();
		battleShipPlayersFileName += "test";
	}
	
	public void saveBattleShipPlayersFromFile()
	{
		return;
	}
	
	public void loadBattleShipPlayersFromFile()
	{
		return;
	}
	
	public void forceSaveBattleShipPlayersFromFile()
	{
		super.saveBattleShipPlayersFromFile();
	}
	
	public void forceLoadBattleShipPlayersFromFile()
	{
		super.loadBattleShipPlayersFromFile();
	}
	
}
