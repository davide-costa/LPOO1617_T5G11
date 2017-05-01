package bship.logic;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.network.data.BattleShipData;
import bship.network.data.GameResultData;
import bship.network.data.GameShootData;
import bship.network.sockets.Client;

public class MultiplayerOpponent extends Opponent implements Observer
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

	@Override
	public void update(Observable clientSocket, Object object)
	{
		BattleShipData data = (BattleShipData)object;
		if (data instanceof GameShootData)
		{
			Coords coords = ((GameShootData) data).getCoords();
			game.shootAlly(coords);
		}
		else if (data instanceof GameResultData)
		{
			
		}
		
	}

}
