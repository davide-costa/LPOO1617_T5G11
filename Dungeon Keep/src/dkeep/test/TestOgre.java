package dkeep.test;

import dkeep.logic.Coords;
import dkeep.logic.GameMap;
import dkeep.logic.Ogre;

public class TestOgre extends Ogre
{

	public TestOgre(Coords ogre_c, Coords club_c)
	{
		super(ogre_c, club_c);
	}
	
	@Override
	public void Move(GameMap map)
	{
		return;
	}
	
	public void Stun()
	{
		return;
	}
}
