package bship.logic;

import java.io.IOException;

public class SingleplayerOpponent extends Opponent 
{
	public SingleplayerOpponent() {}
	
	public SingleplayerOpponent(Game game)
	{
		super(game);
	}
	
	@Override
	public void shoot(Coords coords) throws IOException {}
}