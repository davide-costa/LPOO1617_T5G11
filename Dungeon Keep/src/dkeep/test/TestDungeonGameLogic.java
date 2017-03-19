package dkeep.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import dkeep.logic.Coords;
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
		assertEquals(new Coords(1, 1), game.GetHero().GetCoords());
		game.MoveHero("down"); // move hero down
		assertEquals(new Coords(1, 2), game.GetHero().GetCoords());
		assertEquals('H', game.GetCellState(new Coords(1, 2)));
	}
	
	@Test
	public void testMoveHeroIntoAWall()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero("up"); // move hero up.
		assertEquals(new Coords(1, 1), game.GetHero().GetCoords());
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
		assertEquals(new Coords(1, 2), game.GetHero().GetCoords());
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
        assertEquals(new Coords(7, 1), game.GetGuard().GetCoords());
        assertEquals('G', game.GetCellState(new Coords(7, 1)));
	}
	
	@Test
	public void TestDrunkenFirstPos()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game("Drunken", 1);
        game.MoveHero("right");
        if(game.GetGuard().GetSymbol() == 'G')
        {
        	assertEquals(new Coords(7, 1), game.GetGuard().GetCoords());
        }
        else if(game.GetGuard().GetSymbol() == 'g')
        {
        	//initial pos because its sleeping
        	assertEquals(new Coords(8, 1), game.GetGuard().GetCoords());
        }
	}
	
	@Test
	public void TestSuspiciousFirstPos()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game("Suspicious", 1);
        game.MoveHero("right");
        assertEquals(new Coords(7, 1), game.GetGuard().GetCoords());
	}
	
	@Test (timeout = 2000)
	public void TestSuspiciousMovRandomness()
	{
        Game game = new Game("Suspicious", 1);
		Queue<Coords> coords_q = new LinkedList<Coords>();
		
		//Initialize the queue with two initial positions of the guard
		game.MoveHero("right");
		coords_q.add(game.GetGuard().GetCoords());
		game.MoveHero("left");
		coords_q.add(game.GetGuard().GetCoords());
		
		Coords guard_coords, old_guard_c;
		
		//ensure he reverts the patrolling path
		for (int i = 0; true; i++)
		{
			//Move the hero back and forth waiting for the guard to perform the reversing in patrolling path
			if (i % 2 == 0)
				game.MoveHero("right");
			else
				game.MoveHero("left");
		
			guard_coords = game.GetGuard().GetCoords();
			old_guard_c = coords_q.poll();
			
			if (guard_coords.equals(old_guard_c))
				break;
			else
			{
				coords_q.add(guard_coords);
			}
		}
	}
	
}