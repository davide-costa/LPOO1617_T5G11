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
    public void testHeroIsCapturedByGuard()
	{ 
        GameMap gameMap = new DungeonMap(); 
        Game game = new Game(); 
        assertFalse(game.IsEndOfGame());
        assertEquals(-1, game.MoveHero(3,1));
    }
	
}