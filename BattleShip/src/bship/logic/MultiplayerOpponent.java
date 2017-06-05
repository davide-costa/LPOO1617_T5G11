package bship.logic;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.gui.BattleShipExceptionHandler;
import bship.network.data.BattleShipData;
import bship.network.data.EndOfGameData;
import bship.network.data.GameResultData;
import bship.network.data.GameResultData.GameResult;
import bship.network.data.GameShootData;
import bship.network.data.PlayerDisconnectedData;
import bship.network.data.ReadyForGameData;
import bship.network.data.StartGameData;
import bship.network.sockets.Client;

public class MultiplayerOpponent extends Opponent implements Observer
{
	private Client clientSocket;
	private Coords lastShootCoords;
	

	public MultiplayerOpponent(Game game) throws IOException
	{
		super(game);
		clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
	}
	
	public MultiplayerOpponent(Game game, Client clientSocket)
	{
		super(game);
		this.clientSocket = clientSocket;
		clientSocket.refreshObserver(this);
	}
	
	public MultiplayerOpponent() throws IOException 
	{
		super();
		this.clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
	}

	@Override
	public void shoot(Coords shootCoords) throws IOException
	{
		lastShootCoords = shootCoords;
		BattleShipData data = new GameShootData(shootCoords.GetX(), shootCoords.GetY());
		clientSocket.sendBattleShipData(data);
	}

	@Override
	public void update(Observable clientSocket, Object object)
	{
		BattleShipData gameData = (BattleShipData)object;
		if (gameData instanceof GameShootData)
		{
			GameShootData shootData = (GameShootData) gameData;
			Coords shootCoords = new Coords(shootData.getX(), shootData.getY());
			System.out.println(shootCoords);
			game.shootAlly(shootCoords);
			
			GameResult result = game.getPlayEffects(shootCoords);
			boolean endOfGame = game.isEndOfGame();
			
			BattleShipData resultData = new GameResultData(result, endOfGame);
			
			try 
			{
				this.clientSocket.sendBattleShipData(resultData);
			} 
			catch (IOException e) 
			{
				BattleShipExceptionHandler.handleBattleShipException();
			}
		}
		else if (gameData instanceof GameResultData)
		{
			GameResultData resultData = (GameResultData) gameData;
			System.out.println(lastShootCoords);
			game.handleResultData(lastShootCoords, resultData.getResult());
			System.out.println(lastShootCoords);
			if(!resultData.isEndOfGame())
				return;
			System.out.println("isEndOfGame");
			BattleShipData endOfGameData = new EndOfGameData(game.getAllyMap()); 
	
			try 
			{
				this.clientSocket.sendBattleShipData(endOfGameData);
			} 
			catch (IOException e) 
			{
				BattleShipExceptionHandler.handleBattleShipException();
			}
		}
		else if (gameData instanceof EndOfGameData)
		{
			System.out.println("EndOfGameData");
			EndOfGameData resultData = (EndOfGameData) gameData;
			GameMap winnerGameMap = (GameMap) resultData.getWinnerGameMap();
			game.setOpponentMap(winnerGameMap);
		}
		else if (gameData instanceof PlayerDisconnectedData)
		{
			//game.OpponentDisconnected();
			//avisar que o opponet desconectou-se
		}
	}

}
