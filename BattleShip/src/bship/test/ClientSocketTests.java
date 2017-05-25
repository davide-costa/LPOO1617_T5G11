package bship.test;

import java.io.IOException;

import bship.network.data.BattleShipData;
import bship.network.sockets.Client;

public class ClientSocketTests extends Client
{
	public ClientSocketTests()
	{
		super(0);
	}
	
	public void notifyOfData(BattleShipData data)
	{
		this.notifyObservers(data);
	}
	
}
