package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;

public class Offline extends PlayerState {

	public Offline(Player player)
	{
		super(player);
	}

	@Override
	void HandleReceivedData(BattleShipData receivedData) throws IOException 
	{

	}

}
