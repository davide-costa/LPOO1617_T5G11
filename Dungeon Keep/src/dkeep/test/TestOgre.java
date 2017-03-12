package dkeep.test;

import dkeep.logic.GameMap;
import dkeep.logic.Ogre;

public class TestOgre extends Ogre
{

	public TestOgre(int x, int y, int club_x, int club_y)
	{
		super(x, y, club_x, club_y);
	}
	
	@Override
	public void Move(GameMap map)
	{
		return;
	}
}
