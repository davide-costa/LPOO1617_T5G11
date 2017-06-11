package bship.test;

import bship.logic.AllyCellState;
import bship.logic.GameMap;

public class GameMapTests extends GameMap
{

	public GameMapTests(boolean isOpponent, int sizeX, int sizeY)
	{
		super(isOpponent, sizeX, sizeY);
		fillMap();
	}
	
	private void fillMap()
	{
		for(int i = 0; i < sizeX; i++)
			for(int j = 0; j < sizeY; j++)
				map[i][j] = new AllyCellState(null);
	}
}
