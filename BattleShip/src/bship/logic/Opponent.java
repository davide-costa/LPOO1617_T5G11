package bship.logic;

import bship.network.sockets.*;

import java.io.IOException;

import bship.network.data.*;


public class Opponent
{
	private Game game;
	private Client socket;
	
	public void shoot(Coords coords) throws IOException
	{
		BattleShipData data = new GameShootData(coords);
		socket.sendBattleShipData(data);
	}
}
