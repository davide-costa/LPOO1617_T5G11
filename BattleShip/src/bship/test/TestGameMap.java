package bship.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bship.logic.AllyCellState;
import bship.logic.DefaultMap;
import bship.logic.GameMap;
import bship.logic.OpponentCellState;

public class TestGameMap
{
	@Test
	public void TestFill()
	{
		GameMap map = new DefaultMap(false);
		map.fill(new AllyCellState(null));
		
		GameMap map2 = new DefaultMap(true);
		map2.fill(new OpponentCellState(null, false));
	}
}
