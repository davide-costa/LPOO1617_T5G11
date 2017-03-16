package dkeep.test;

import static org.junit.Assert.*;
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
		game.MoveHero(1,2); // move hero down.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroIntoAWall()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero(0,1); // move hero right.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
	}
	
	@Test
    public void testHeroIsCapturedByGuard()
	{ 
        GameMap gameMap = new DungeonMapTests(); 
		Game game = new Game(gameMap);
        assertEquals(-1, game.MoveHero(2,1));
    }
	
	@Test
	public void testMoveHeroIntoAClosedDoor()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(0,2); // move hero left.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroAndOpenDoors()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(1,3); // move hero down.
		assertEquals('S', game.GetGameMap()[3][0]);
		assertEquals('S', game.GetGameMap()[2][0]);
	}

	@Test
	public void testMoveHeroAndGoToLevelKeep()
	{
		GameMap gameMap = new DungeonMapTests();
		Game game = new Game(gameMap);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(1,3); // move hero down.
		game.MoveHero(0,3); // move hero right.
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
        game.MoveHero(2,1);
        assertEquals(game.GetGuard().GetX(), 7);
        assertEquals(game.GetGuard().GetY(), 1);
	}
	
	@Test
	public void TestDrunkenFirstPos()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game("Drunken", 1);
        game.MoveHero(2,1);
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
        game.MoveHero(2,1);
        assertEquals(game.GetGuard().GetX(), 7);
        assertEquals(game.GetGuard().GetY(), 1);
	}
	
}