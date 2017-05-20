package bship.logic;

import bship.network.data.BattleShipData;
import bship.network.data.ReadyForGameData;
import bship.network.data.ShipPlacementData;

public class InShipPlacement extends PlayerState
{
	boolean readyForGame = false;
	
	public InShipPlacement(Player player) 
	{
		super(player);
	}
	
	public void setReady()
	{
		readyForGame = true;
	}


	@Override
	public void HandleReceivedData(BattleShipData data) 
	{
		if (!(data instanceof ShipPlacementData))
			return;
		
		if(data instanceof ReadyForGameData)
		{
			player.setState(new ReadyForGame(player));
			player.getBattleShipServer().canGameBeInitiated(player, player.getOpponent());
			//sets player state as readyforgame
		}

		
	}
}
