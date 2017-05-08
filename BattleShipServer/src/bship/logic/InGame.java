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
	public void HandleReceivedData(BattleShipData data) throws IOException 
	{
		if(!(data instanceof GameData))
			return;
		
		player.sendData(data);
		
		if(data instanceof EndOfGameData)
			player.setState(new InLobby(player));	
	}

}
