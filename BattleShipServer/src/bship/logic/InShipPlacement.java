package bship.logic;

import bship.network.data.BattleShipData;

public class InShipPlacement extends PlayerState
{
	public InShipPlacement(Player player) 
	{
		super(player);
	}

	boolean readyForGame = false;

	@Override
	public void HandleReceivedData(BattleShipData data) 
	{

	}
}
