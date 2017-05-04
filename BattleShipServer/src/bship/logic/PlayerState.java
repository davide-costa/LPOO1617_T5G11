package bship.logic;

import bship.network.data.BattleShipData;

public abstract class PlayerState
{
	private Player player;
	abstract void HandleReceivedData(BattleShipData receivedData);

}
