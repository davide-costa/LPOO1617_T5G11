package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;
import bship.network.data.EndOfGameData;
import bship.network.data.GameData;

public class InGame extends PlayerState
{
	public InGame(Player player) 
	{
		super(player);
	}

	@Override
	public void HandleReceivedData(BattleShipData receivedData) throws IOException 
	{
		if(!(receivedData instanceof GameData))
			return;
		
		player.sendData(receivedData);
		
		if(receivedData instanceof EndOfGameData)
			player.setState(new InLobby(player));	
	}

}
