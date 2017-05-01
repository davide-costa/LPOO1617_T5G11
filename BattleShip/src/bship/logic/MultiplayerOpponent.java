package bship.logic;

import java.io.IOException;

import bship.network.data.BattleShipData;
import bship.network.data.GameShootData;
import bship.network.sockets.Client;

public class MultiplayerOpponent extends Opponent 
{

	private Client clientSocket;
	

	public MultiplayerOpponent(Game game) throws IOException
	{
		super(game);
		clientSocket = new Client(this);
	}
	
	@Override
	public void shoot(Coords coords) throws IOException
	{
		BattleShipData data = new GameShootData(coords);
		clientSocket.sendBattleShipData(data);
	}

}
