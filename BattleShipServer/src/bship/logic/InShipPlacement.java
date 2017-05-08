package bship.logic;

import bship.network.data.BattleShipData;
import bship.network.data.ShipPlacementData;

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
		if (!(data instanceof ShipPlacementData))
			return;
	}
}
