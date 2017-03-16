package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Drunken;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.KeepMap;
import dkeep.logic.Ogre;
import dkeep.logic.Rookie;
import dkeep.logic.Suspicious;

public class TestKeepGameLogic
{
	@Test
    public void testHeroIsCapturedByOgre()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(-1, game.MoveHero(2,1));// move hero right. 
    }
	
	@Test
    public void testHeroPicksUpKey()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(0, game.MoveHero(1,2)); // move hero down.
        assertEquals(0, game.MoveHero(1,3)); // move hero down to the key cell
        assertEquals('K', game.GetHero().GetSymbol());   
    }
	
	@Test
    public void testHeroCantLeave()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(0, game.MoveHero(0,1));// move hero left. 
        assertEquals(1, game.GetHero().GetX());//hero must remain in postion 1 at x
    }
	
	@Test
    public void testHeroOpenDoor()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        game.MoveHero(1,2);//move hero down
        game.MoveHero(1,3);//move hero down
        assertTrue(game.GetMap().IsDoorOpen());
    }
	
	@Test
    public void testHeroWinsKeepLevel()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        game.MoveHero(1,2);//moves down
        game.MoveHero(1,3);//moves down
        game.MoveHero(1,2);//moves up
        game.MoveHero(1,1);//moves up
        assertEquals(0, game.MoveHero(0,1));//moves up
        assertEquals(0, game.GetHero().GetX());//hero must be on top of the door cell
        game.MoveHero(-1,1);//moves up
        assertTrue(game.IsEndOfGame());
    }
	
	@Test (timeout = 2000)
	public void TestOgreAndClubRandomness()
	{
		int init_x = 4;
		int init_y = 5;
		
		EnsureOgreGoesTo(init_x - 1, init_y);
		EnsureOgreGoesTo(init_x + 1, init_y);
		EnsureOgreGoesTo(init_x, init_y - 1);
		EnsureOgreGoesTo(init_x, init_y + 1);
		
		EnsureClubGoesToAllPossiblePositions();
	}
	
	public boolean CellsAreAdjacent(int x1, int y1, int x2, int y2)
	{
		if (x1 == x2 - 1 && y1 == y2)
			return true;
		else if (x1 == x2 + 1 && y1 == y2)
        	return true;
		else if (x1 == x2 && y1 == y2 - 1)
        	return true;
		else if (x1 == x2 && y1 == y2 + 1)
        	return true;
		else
			return false;
	}
	
	public void EnsureOgreGoesTo(int x, int y)
	{
		int init_x = 4;
		int init_y = 5;
		
		int curr_x = x - 1;
		int curr_y = y - 1;
		
		while (curr_x != x || curr_y != y)
		{
			GameMap gameMap = new KeepMap();
			Game game = new Game(gameMap, new Ogre(init_x,init_y,5,5));
	        game.MoveHero(1,2);//moves down
	        Ogre ogre = game.GetOgres().get(0);
	        curr_x = ogre.GetX();
	        curr_y = ogre.GetY();
	        if (!gameMap.MoveTo(ogre.GetX(), ogre.GetY()))
	        	fail("Ogre moved onto forbiden position.");
	        else if (!CellsAreAdjacent(init_x, init_y, ogre.GetX(), ogre.GetY()))
	        	fail("Ogre moved onto not adjacent position (relative to his initial position)");
	        else if (!CellsAreAdjacent(ogre.GetX(), ogre.GetY(), ogre.GetClubX(), ogre.GetClubY()))
	        	fail("Club new position is not adjacent to new Ogre position");
		}
	}
	
	public void EnsureClubGoesToAllPossiblePositions()
	{
		int init_x = 4;
		int init_y = 5;
		
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		int ogre_x, ogre_y, club_x, club_y;
		
		while (!up || !down || !left || !right)
		{
			GameMap gameMap = new KeepMap();
			Game game = new Game(gameMap, new Ogre(init_x,init_y,5,5));
	        game.MoveHero(1,2);//moves down
	        Ogre ogre = game.GetOgres().get(0);
	        ogre_x = ogre.GetX();
	        ogre_y = ogre.GetY();
	        club_x = ogre.GetClubX();
	        club_y = ogre.GetClubY();
	        if (club_x == ogre_x - 1 && club_y == ogre_y)
	        	left = true;
	        else if (club_x == ogre_x + 1 && club_y == ogre_y)
	        	right = true;
	        else if (club_x == ogre_x && club_y == ogre_y - 1)
	        	up = true;
	        else if (club_x == ogre_x && club_y == ogre_y + 1)
	        	down = true;
	        else
	        	fail("Club new position is not adjacent to new Ogre position");
		}
	}
	
	@Test
	public void EnsureAllGuardsAreSpawned()
	{
	   boolean rookie = false;
	   boolean drunken = false;
	   boolean suspicious = false;
	   
	   while (!(rookie && drunken && suspicious))
	   {
		   Game game = new Game(null, 1);
		   if (game.GetGuard() instanceof Rookie)
			   rookie = true;
		   else if (game.GetGuard() instanceof Drunken)
			   drunken = true;
		   else if (game.GetGuard()  instanceof Suspicious)
			   suspicious = true;
	   }
	}
	
	@Test
	public void TestRookie()
	{
        Game game = new Game("Rookie", 1);
        game.MoveHero(2,1);
        assertEquals(game.GetGuard().GetX(), 7);
        assertEquals(game.GetGuard().GetY(), 1);
	}
	/*
	@Test
	public void TestRookie()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game("Rookie", 1);
        game.MoveHero(2,1);
        assertEquals(game.GetGuard().GetX(), 7);
        assertEquals(game.GetGuard().GetY(), 1);
	}
	*/
	
}
