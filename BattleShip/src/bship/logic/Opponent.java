package bship.logic;

import java.io.IOException;

public abstract class Opponent
{
	protected Game game;
	
	public Opponent() {}
	
	public Opponent(Game game)
	{
		this.game = game;
	}
	
	public abstract void shoot(Coords coords) throws IOException;
	
	public void setGame(Game game) 
	{
		this.game = game;
	}
}
