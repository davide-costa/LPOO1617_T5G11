package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;

public class ReadyForGame extends PlayerState 
{
	public ReadyForGame(Player player) 
	{
		super(player);
	}

	@Override
	void HandleReceivedData(BattleShipData data) throws IOException 
	{
		// TODO Auto-generated method stub
	}

}
