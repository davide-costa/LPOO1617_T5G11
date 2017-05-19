package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;
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
		
		//informs the opponent that the player this is ready
		try
		{
			player.getOpponent().sendData(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
