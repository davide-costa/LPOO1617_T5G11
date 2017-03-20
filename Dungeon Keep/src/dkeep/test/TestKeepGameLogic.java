package dkeep.test;

import static org.junit.Assert.*; 

import org.junit.Test;

import dkeep.logic.Coords;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.KeepMap;
import dkeep.logic.Ogre;

public class TestKeepGameLogic
{
	@Test
    public void testHeroIsCapturedByOgre()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(-1, game.MoveHero("right")); // move hero right
        assertTrue(game.IsGameOver());
    }
	
	@Test
    public void testHeroPicksUpKey()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals('k', game.GetMap().GetCellState(new Coords(1,3)));
        assertEquals(0, game.MoveHero("down")); // move hero down
        assertEquals(0, game.MoveHero("down")); // move hero down to the key cell
        assertEquals('K', game.GetHero().GetSymbol());  
        assertEquals((char)0, game.GetMap().GetCellState(new Coords(1,3)));
    }
	
	@Test
    public void testHeroCantLeave()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        assertEquals(0, game.MoveHero("left"));// move hero left. 
        assertEquals(new Coords(1, 1), game.GetHero().GetCoords());
    }
	
	@Test
    public void testHeroOpenDoor()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        game.MoveHero("down");//move hero down
        game.MoveHero("down");//move hero down
        assertTrue(game.GetMap().IsDoorOpen());
    }
	
	@Test
    public void testHeroWinsKeepLevel()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, new TestOgre(3,1,3,2));
        game.MoveHero("down");//moves down
        game.MoveHero("down");//moves down
        game.MoveHero("up");//moves up
        game.MoveHero("up");//moves up
        assertEquals(0, game.MoveHero("left"));//moves left
        assertEquals(new Coords(0, 1), game.GetHero().GetCoords()); //hero must be on top of the door cell
        game.MoveHero("left");//moves left
        assertTrue(game.IsEndOfGame());
    }
	
	@Test (timeout = 2000)
	public void TestOgreAndClubRandomness()
	{
		Coords init_coords = new Coords(4, 5);
		
		EnsureOgreGoesTo(init_coords, init_coords.left());
		EnsureOgreGoesTo(init_coords, init_coords.right());
		EnsureOgreGoesTo(init_coords, init_coords.up());
		EnsureOgreGoesTo(init_coords, init_coords.down());
		
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
	
	public void EnsureOgreGoesTo(Coords init_coords, Coords dst_coords)
	{
		Coords curr_coords = new Coords(dst_coords);
		curr_coords.SetX(0); //ensure the coords are different at start
		
		while (!curr_coords.equals(dst_coords))
		{
			GameMap gameMap = new KeepMap();
			Game game = new Game(gameMap, new Ogre(init_coords.GetX(),init_coords.GetY(),5,5));
	        game.MoveHero("down");//moves down
	        Ogre ogre = game.GetOgres().get(0);
	        curr_coords.Set(ogre.GetCoords());
	        assertEquals('O', game.GetCellState(ogre.GetCoords()));
	        assertEquals('*', game.GetCellState(ogre.GetClubCoords()));
	        if (!gameMap.MoveTo(ogre.GetCoords()))
	        	fail("Ogre moved onto forbiden position.");
	        else if (!init_coords.adjacentTo(ogre.GetCoords()))
	        	fail("Ogre moved onto not adjacent position (relative to his initial position)");
	        else if (!ogre.GetCoords().adjacentTo(ogre.GetClubCoords()))
	        	fail("Club new position is not adjacent to new Ogre position");
		}
	}
	
	public void EnsureClubGoesToAllPossiblePositions()
	{
		Coords init_coords = new Coords(4, 5);
		
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		Coords ogre_coords, club_coords;
		
		while (!up || !down || !left || !right)
		{
			GameMap gameMap = new KeepMap();
			Game game = new Game(gameMap, new Ogre(init_coords.GetX(),init_coords.GetY(),5,5));
	        game.MoveHero("down");//moves down
	        Ogre ogre = game.GetOgres().get(0);
	        ogre_coords = ogre.GetCoords();
	        club_coords = ogre.GetClubCoords();
	        if (club_coords.equals(ogre_coords.left()))
	        	left = true;
	        else if (club_coords.equals(ogre_coords.right()))
	        	right = true;
	        else if (club_coords.equals(ogre_coords.up()))
	        	up = true;
	        else if (club_coords.equals(ogre_coords.down()))
	        	down = true;
	        else
	        	fail("Club new position is not adjacent to new Ogre position");
		}
	}
}
