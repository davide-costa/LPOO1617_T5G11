package bship.logic;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
			
			GameResult result = game.getPlayEffects(shootCoords);
			boolean endOfGame = game.isEndOfGame();
			
			BattleShipData resultData = new GameResultData(result, endOfGame);
			
			this.clientSocket.sendBattleShipData(resultData);
		}
		else if (gameData instanceof GameResultData)
		{
			GameResultData resultData = (GameResultData) gameData;
			
			game.handleResultData(lastShootCoords, resultData.getResult());
			
			if(!resultData.isEndOfGame())
				return;
			
			BattleShipData endOfGameData = new EndOfGameData(game.getAllyMap()); 
	
			this.clientSocket.sendBattleShipData(endOfGameData);
		}
		else if (gameData instanceof EndOfGameData)
		{
			EndOfGameData resultData = (EndOfGameData) gameData;
			GameMap winnerGameMap = (GameMap) resultData.getWinnerGameMap();
			game.setOpponentMap(winnerGameMap);
		}
		else if (gameData instanceof ReadyForGameData)
		{
			//avisar que o opponet ta ready
			
		}
		else if (gameData instanceof StartGameData)
		{
			//iniciar o jogo...so o servidor é que manda este tipo de Data, os clients apenas a recebem, nunca enviam
			
		}
		else if (gameData instanceof PlayerDisconnectedData)
		{
			
			//avisar que o opponet desconectou-se
		}
	}

}
