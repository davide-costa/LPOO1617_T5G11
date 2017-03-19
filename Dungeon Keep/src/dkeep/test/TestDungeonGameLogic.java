package dkeep.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import dkeep.logic.Drunken;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Rookie;
import dkeep.logic.Suspicious;

public class TestDungeonGameLogic 
{
	@Test
	public void testMoveHeroIntoToFreeCell()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
		game.MoveHero("down"); // move hero down.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroIntoAWall()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero("up"); // move hero up.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
	}
	
	@Test
    public void testHeroIsCapturedByGuard()
	{ 
        GameMap gameMap = new DungeonMapTests(); 
		Game game = new Game(gameMap);
        assertEquals(-1, game.MoveHero("right"));
        assertTrue(game.IsGameOver());
    }
	
	@Test
	public void testMoveHeroIntoAClosedDoor()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero("down"); // move hero down.
		game.MoveHero("left"); // move hero left.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroAndOpenDoors()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero("down"); // move hero down.
		game.MoveHero("down"); // move hero down.
		assertEquals('S', game.GetGameMap()[3][0]);
		assertEquals('S', game.GetGameMap()[2][0]);
	}

	@Test
	public void testMoveHeroAndGoToLevelKeep()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero("down"); // move hero down.
		game.MoveHero("down"); // move hero down.
		game.MoveHero("left"); // move hero left.
		assertEquals(2, game.GetLevel());
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
	public void TestRookieFirstPos()
	{
        Game game = new Game("Rookie", 1);
        game.MoveHero("right");
        assertEquals(game.GetGuard().GetX(), 7);
        assertEquals(game.GetGuard().GetY(), 1);
	}
	
	@Test
	public void TestDrunkenFirstPos()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game("Drunken", 1);
        game.MoveHero("right");
        if(game.GetGuard().GetSymbol() == 'G')
        {
        	assertEquals(game.GetGuard().GetX(), 7);
            assertEquals(game.GetGuard().GetY(), 1);
        }
        else if(game.GetGuard().GetSymbol() == 'g')
        {
        	//initial pos because its sleeping
        	assertEquals(game.GetGuard().GetX(), 8);
            assertEquals(game.GetGuard().GetY(), 1);
        }
	}
	
	@Test
	public void TestSuspiciousFirstPos()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game("Suspicious", 1);
        game.MoveHero("right");
        assertEquals(game.GetGuard().GetX(), 7);
        assertEquals(game.GetGuard().GetY(), 1);
	}
	
	@Test (timeout = 2000)
	public void TestSuspiciousMovRandomness()
	{
        Game game = new Game("Suspicious", 1);
		boolean reversed = false;
		Queue<Integer> pos_x = new LinkedList<Integer>();
		Queue<Integer> pos_y = new LinkedList<Integer>();
		
		//Initialize the queue with two initial positions of the guard
		game.MoveHero("right");
		pos_x.add(game.GetGuard().GetX());
		pos_y.add(game.GetGuard().GetY());
		game.MoveHero("left");
		pos_x.add(game.GetGuard().GetX());
		pos_y.add(game.GetGuard().GetY());
		
		int guard_x, guard_y;
		int old_guard_x, old_guard_y;
		
		//ensure he reverts the patrolling path
		for (int i = 0; true; i++)
		{
			//Move the hero back and forth waiting for the guard to perform the reversing in patrolling path
			if (i % 2 == 0)
				game.MoveHero("right");
			else
				game.MoveHero("left");
		
			guard_x = game.GetGuard().GetX();
			guard_y = game.GetGuard().GetY();
			old_guard_x = pos_x.poll();
			old_guard_y = pos_y.poll();
			
			if (guard_x == old_guard_x && guard_y == old_guard_y)
				break;
			else
			{
				pos_x.add(guard_x);
				pos_y.add(guard_y);
			}
		}
	}
	
}