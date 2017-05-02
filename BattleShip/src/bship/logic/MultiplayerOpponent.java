package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
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
		BattleShipData shootData = (BattleShipData)object;
		if (shootData instanceof GameShootData)
		{
			Coords shootCoords = ((GameShootData) shootData).getCoords();
			ArrayList<Coords> coords = new ArrayList<Coords>();
			ArrayList<CellState> resultStates = new ArrayList<CellState>();
			game.shootAlly(shootCoords, coords, resultStates);
			
			
			BattleShipData resultData = new GameResultData(coords, resultStates);
			try
			{
				this.clientSocket.sendBattleShipData(resultData);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (shootData instanceof GameResultData)
		{
			
		}
		
	}

}
