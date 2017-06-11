package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;
import bship.network.data.ReadyForGameData;
import bship.network.data.ShipPlacementData;
import bship.network.data.StartGameData;

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
			Player opponent = player.getOpponent();
			if (player.getBattleShipServer().canGameBeInitiated(player, opponent))
			{
				player.setState(new InGame(player));
				opponent.setState(new InGame(opponent));
				
				player.sendData(new StartGameData());
				opponent.sendData(new StartGameData());
			}
			else
			{
				player.setState(new ReadyForGame(player));
				opponent.sendData(data);
			}
			//sets player state as readyforgame
		}

		
	}
}
