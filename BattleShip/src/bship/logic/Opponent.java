package bship.logic;

import bship.network.sockets.*;

import java.io.IOException;

import bship.network.data.*;


public abstract class Opponent
{
	protected Game game;
	
	public Opponent(Game game)
	{
		this.game = game;
	}
	
	public abstract void shoot(Coords coords) throws IOException;
}
