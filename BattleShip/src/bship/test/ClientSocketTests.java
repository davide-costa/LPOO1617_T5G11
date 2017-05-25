package bship.test;

import java.io.IOException;

import bship.network.data.BattleShipData;
import bship.network.sockets.Client;

public class ClientSocketTests extends Client
{
	private BattleShipData lastBattleShipDataSent;
	
	public ClientSocketTests()
	{
		super(0);
		lastBattleShipDataSent = null;
	}

	public void simulateReceptionOfData(BattleShipData data)
	{
		this.notifyObservers(data);
	}
	
	public void sendBattleShipData(BattleShipData data)
	{
		lastBattleShipDataSent = data;
	}
	
	public BattleShipData getLastBattleShipDataSent()
	{
		return lastBattleShipDataSent;
	}

	public void setLastBattleShipDataSent(BattleShipData lastBattleShipDataSent)
	{
		this.lastBattleShipDataSent = lastBattleShipDataSent;
	}
}
