package bship.logic;

import bship.network.data.BattleShipData;
import bship.network.data.GameData;

public class InGame extends PlayerState
{
	
	@Override
	public void HandleReceivedData(BattleShipData receivedData) 
	{
		if(!(receivedData instanceof GameData))
			return;
		
		
	}

}
