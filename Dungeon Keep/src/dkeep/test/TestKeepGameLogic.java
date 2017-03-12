package dkeep.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestKeepGameLogic
{
	@Test
	public void testMoveHeroIntoToFreeCell()
	{
		GameMap gameMap = new KeepMapTests();
		Game game = new Game(gameMap, 2);
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
		game.MoveHero(1,2); // move hero down.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroIntoKeyCell()
	{
		GameMap gameMap = new KeepMapTests();
		Game game = new Game(gameMap, 2);
		game.MoveHero(0,1); // move hero right.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
	}
	
	@Test
    public void testHeroIsCapturedByGuard()
	{ 
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, 2); 
        assertEquals(-1, game.MoveHero(2,1));
    }
	
	@Test
	public void testMoveHeroIntoAClosedDoor()
	{
		GameMap gameMap = new KeepMapTests();
		Game game = new Game(gameMap, 2);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(0,2); // move hero left.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroAndOpenDoors()
	{
		GameMap gameMap = new KeepMapTests();
		Game game = new Game(gameMap, 2);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(1,3); // move hero down.
		assertEquals('S', game.GetGameMap()[3][0]);
		assertEquals('S', game.GetGameMap()[2][0]);
	}

	@Test
	public void testMoveHeroAndGoToLevelKeep()
	{
		GameMap gameMap = new KeepMapTests();
		Game game = new Game(gameMap, 2);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(1,3); // move hero down.
		game.MoveHero(0,3); // move hero right.
		assertEquals(2, game.GetLevel());
	}
}
