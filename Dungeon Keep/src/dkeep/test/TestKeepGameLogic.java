package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class TestKeepGameLogic
{
	@Test
    public void testHeroIsCapturedByOgre()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, 2);
        assertEquals(-1, game.MoveHero(2,1));// move hero right. 
    }
	
	@Test
    public void testHeroPicksUpKey()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, 2);
        assertEquals(0, game.MoveHero(1,2)); // move hero down.
        assertEquals(0, game.MoveHero(1,3)); // move hero down to the key cell
        assertEquals('K', game.GetHero().GetSymbol());   
    }
	
	@Test
    public void testHeroCantLeave()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, 2);
        assertEquals(0, game.MoveHero(0,1));// move hero left. 
        assertEquals(1, game.GetHero().GetX());//hero must remain in postion 1 at x
    }
	
	@Test
    public void testHeroOpenDoor()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, 2);
        game.MoveHero(1,2);//moves down
        game.MoveHero(1,3);//moves down
        assertTrue(game.GetMap().IsDoorOpen());
    }
	
	@Test
    public void testHeroWinsKeepLevel()
	{
		GameMap gameMap = new KeepMapTests();
        Game game = new Game(gameMap, 2);
        game.MoveHero(1,2);//moves down
        game.MoveHero(1,3);//moves down
        game.MoveHero(1,2);//moves up
        game.MoveHero(1,1);//moves up
        game.MoveHero(0,1);//moves up
        game.MoveHero(-1,1);//moves up
        assertTrue(game.IsEndOfGame());
    }
}
