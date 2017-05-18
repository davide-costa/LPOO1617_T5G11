package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import bship.network.data.BattleShipData;
import bship.network.data.EndOfGameData;
import bship.network.data.GameResultData;
import bship.network.data.GameResultData.Result;
import bship.network.data.GameShootData;
import bship.network.sockets.Client;

public class MultiplayerOpponent extends Opponent implements Observer
{
	private Client clientSocket;
	private Coords lastShootCoords;
	

	public MultiplayerOpponent(Game game) throws IOException
	{
		super(game);
		clientSocket = Client.getInstance(this);
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
		BattleShipData gameData = (BattleShipData)object;
		if (gameData instanceof GameShootData)
		{
			Coords shootCoords = (Coords) ((GameShootData) gameData).getCoords();
			game.shootAlly(shootCoords);
			lastShootCoords = shootCoords;
			
			Result result = game.getPlayEffects(shootCoords);
			boolean endOfGame = game.isEndOfGame();
			
			BattleShipData resultData = new GameResultData(result, endOfGame);
			
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
		else if (gameData instanceof GameResultData)
		{
			GameResultData resultData = (GameResultData) gameData;
			
			game.handleResultData(lastShootCoords, resultData.getResult());
			
			if(!resultData.isEndOfGame())
				return;
			
			BattleShipData endOfGameData = new EndOfGameData(game.getAllyMap()); 
	
			try
			{
				this.clientSocket.sendBattleShipData(endOfGameData);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (gameData instanceof EndOfGameData)
		{
			EndOfGameData resultData = (EndOfGameData) gameData;
			GameMap winnerGameMap = (GameMap) resultData.getWinnerGameMap();
			game.setOpponentMap(winnerGameMap);
		}
	}

}
