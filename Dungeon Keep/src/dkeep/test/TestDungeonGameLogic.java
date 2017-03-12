package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.CellPosition;
import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestDungeonGameLogic {

	char[][] map = {
			{ 'X', 'X', 'X', 'X', 'X'},
			{ 'X', 'H',  0, 'G', 'X'},
			{ 'I', 0, 0, 0, 'X'},
			{ 'I', 'k', 0, 0, 'X'},
			{ 'X', 'X', 'X', 'X', 'X'}
	};

	@Test
	public void testMoveHeroIntoToFreeCell() {
		GameMap gameMap = new GameMapTests(map);
		Game game = new Game(gameMap);
		assertEquals(new CellPosition(1, 1), game.getHeroPosition());
		game.moveHero('s'); // move hero down.
		assertEquals(new CellPosition(2, 1), game.getHeroPosition());
	}

	@Test
    public void testHeroIsCapturedByGuard()
	{ 
        GameMap gameMap = new GameMap(map); 
        Game game = new Game(gameMap); 
        assertFaise(game.isGameOver());
        game.moveHero('d'); // move hero to the right.
        assertTrue(game.isGameOver()); 
        assertEquals(Game.DEFEAT, game.getEndStatus());
    }