package dkeep.test;

import static org.junit.Assert.*; 
import org.junit.Test;

import dkeep.logic.DungeonMap;
import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestDungeonGameLogic 
{
	char[][] map = {
			{ 'X', 'X', 'X', 'X', 'X'},
			{ 'X', 0,  0, 0, 'X'},
			{ 'I', 0, 0, 0, 'X'},
			{ 'I', 'k', 0, 0, 'X'},
			{ 'X', 'X', 'X', 'X', 'X'}
			};

	@Test
	public void testMoveHeroIntoToFreeCell() {
		GameMap gameMap = new GameMapTests(map);
		Game game = new Game(gameMap);
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
		game.MoveHero(1,2); // move hero down.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroIntoAWall() {
		GameMap gameMap = new GameMapTests(map);
		Game game = new Game(gameMap);
		game.MoveHero(0,1); // move hero right.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(1, game.GetHero().GetY());
	}
	
	@Test
    public void testHeroIsCapturedByGuard()
	{ 
        GameMap gameMap = new GameMapTests(map); 
        Game game = new Game(gameMap); 
        assertEquals(-1, game.MoveHero(2,1));
    }
	
	@Test
	public void testMoveHeroIntoAClosedDoor() {
		GameMap gameMap = new GameMapTests(map);
		Game game = new Game(gameMap);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(0,2); // move hero left.
		assertEquals(1, game.GetHero().GetX());
		assertEquals(2, game.GetHero().GetY());
	}
	
	@Test
	public void testMoveHeroAndOpenDoors() {
		GameMap gameMap = new GameMapTests(map);
		Game game = new Game(gameMap);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(1,3); // move hero down.
		assertEquals('S', game.GetGameMap()[3][0]);
		assertEquals('S', game.GetGameMap()[2][0]);
	}

	@Test
	public void testMoveHeroAndGoToLevelKeep() {
		GameMap gameMap = new GameMapTests(map);
		Game game = new Game(gameMap);
		game.MoveHero(1,2); // move hero down.
		game.MoveHero(1,3); // move hero down.
		game.MoveHero(0,3); // move hero right.
		assertEquals(2, game.GetLevel());
	}
	
	
}