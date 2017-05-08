package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;

public abstract class PlayerState
{
	protected Player player;
	
	public PlayerState(Player player)
	{
		this.player = player;
	}
	
	abstract void HandleReceivedData(BattleShipData data) throws IOException;

}
