package bship.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import bship.logic.AllyCellState;
import bship.logic.Carrier;
import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Cruiser;
import bship.logic.Game;
import bship.logic.OpponentCellState;
import bship.logic.Ship;
import bship.network.data.GameResultData.GameResult;

public class TestGameLogic
{	
	@Test
	public void testGetSorroundingCoordsOfShip()
	{
		Game game = new GameLogicTests();
		
		testVerticalLeftBottomShip(game);
		testVerticalRightUpShip(game);
	}
	
	private void testVerticalLeftBottomShip(Game game)
	{
		ArrayList<Coords> shipCoords = new ArrayList<Coords>();
		shipCoords.add(new Coords(0,9));
		shipCoords.add(new Coords(0,8));
		shipCoords.add(new Coords(0,7));
		shipCoords.add(new Coords(0,6));
		shipCoords.add(new Coords(0,5));
		Ship ship = new Carrier(shipCoords, "vertical");
		ArrayList<Coords> returned_coords = game.getSurroundingCoordsOfShip(ship);
		Collections.sort(returned_coords);
		
		ArrayList<Coords> correct_coords = new ArrayList<Coords>();  
		correct_coords.add(new Coords(1,9));
		correct_coords.add(new Coords(1,8));
		correct_coords.add(new Coords(1,7));
		correct_coords.add(new Coords(1,6));
		correct_coords.add(new Coords(1,5));
		correct_coords.add(new Coords(0,4));
		correct_coords.add(new Coords(1,4));
		Collections.sort(correct_coords);
		
		assertEquals(correct_coords, returned_coords);
	}
	
	private void testVerticalRightUpShip(Game game)
	{
		ArrayList<Coords> shipCoords = new ArrayList<Coords>();
		shipCoords.add(new Coords(9,0));
		shipCoords.add(new Coords(9,1));
		shipCoords.add(new Coords(9,2));
		shipCoords.add(new Coords(9,3));
		shipCoords.add(new Coords(9,4));
		Ship ship = new Carrier(shipCoords, "vertical");
		
		ArrayList<Coords> returned_coords= game.getSurroundingCoordsOfShip(ship);
		Collections.sort(returned_coords);
		
		ArrayList<Coords> correct_coords = new ArrayList<Coords>();  
		correct_coords.add(new Coords(8,0));
		correct_coords.add(new Coords(8,1));
		correct_coords.add(new Coords(8,2));
		correct_coords.add(new Coords(8,3));
		correct_coords.add(new Coords(8,4));
		correct_coords.add(new Coords(8,5));
		correct_coords.add(new Coords(9,5));
		Collections.sort(correct_coords);
		
		assertEquals(correct_coords, returned_coords);
	}	
	
	@Test
	public void testCellStateConstructor()
	{
		AllyCellState allyCell = new AllyCellState(null);
		assertFalse(allyCell.isDiscovered());
		assertNull(allyCell.getShip());
		
		OpponentCellState opponentCell = new OpponentCellState(null, true);
		assertFalse(opponentCell.isDiscovered());
		assertNull(opponentCell.getShip());
		assertTrue(opponentCell.hasShip());
	}
	
	@Test
	public void testShootAllyMapLogic()
	{	
		//Test shoot in water
		Game game = new GameLogicTests();
		GameMapTests map = new GameMapTests(false, 10, 10);
		game.setAllyMap(map);
		
		Coords cruiserCoords1 = new Coords(0,0);
		assertEquals(GameResult.WATER, game.getPlayEffects(cruiserCoords1));
		
		Coords cruiserCoords2 = new Coords(1,0);
		Coords cruiserCoords3 = new Coords(2,0);
		ArrayList<Coords> shipCoords = new ArrayList<Coords>();
		Ship cruiser = new Cruiser(shipCoords, "horizontal");
		CellState cell1 = new AllyCellState(cruiser);
		CellState cell2 = new AllyCellState(cruiser);
		CellState cell3 = new AllyCellState(cruiser);
		
		map.setCellState(cruiserCoords1, cell1);
		map.setCellState(cruiserCoords2, cell2);
		map.setCellState(cruiserCoords3, cell3);
		
		CellState cell;
		game.shootAlly(cruiserCoords1);
		cell = map.getCellState(cruiserCoords1);
		assertNotNull(cell);
		assertTrue(cell.isDiscovered());
		assertFalse(map.getCellState(cruiserCoords2).isDiscovered());
		assertFalse(map.getCellState(cruiserCoords3).isDiscovered());
		
		game.shootAlly(cruiserCoords2);
		cell = map.getCellState(cruiserCoords2);
		assertNotNull(cell);
		assertTrue(map.getCellState(cruiserCoords1).isDiscovered());
		assertTrue(cell.isDiscovered());
		assertFalse(map.getCellState(cruiserCoords3).isDiscovered());
		
		game.shootAlly(cruiserCoords3);
		cell = map.getCellState(cruiserCoords2);
		assertNotNull(cell);
		assertTrue(map.getCellState(cruiserCoords1).isDiscovered());
		assertTrue(map.getCellState(cruiserCoords2).isDiscovered());
		assertTrue(cell.isDiscovered());
	}
	
	@Test
	public void testShootAllyShipLogic()
	{
		//Test shoot in water
		Game game = new GameLogicTests();
		GameMapTests map = new GameMapTests(false, 10, 10);
		game.setAllyMap(map);
		
		Coords cruiserCoords1 = new Coords(0,0);
		assertEquals(GameResult.WATER, game.getPlayEffects(cruiserCoords1));
		
		Coords cruiserCoords2 = new Coords(1,0);
		Coords cruiserCoords3 = new Coords(2,0);
		ArrayList<Coords> shipCoords = new ArrayList<Coords>();
		Ship cruiser = new Cruiser(shipCoords, "horizontal");
		CellState cell1 = new AllyCellState(cruiser);
		CellState cell2 = new AllyCellState(cruiser);
		CellState cell3 = new AllyCellState(cruiser);
		
		map.setCellState(cruiserCoords1, cell1);
		map.setCellState(cruiserCoords2, cell2);
		map.setCellState(cruiserCoords3, cell3);
		
		int currHealth = 3;
		assertEquals(currHealth, cruiser.getHealth());
		game.shootAlly(cruiserCoords1);
		currHealth--;
		assertEquals(currHealth, cruiser.getHealth());
		game.shootAlly(cruiserCoords2);
		currHealth--;
		assertEquals(currHealth, cruiser.getHealth());
		
		game.shootAlly(cruiserCoords3);
		currHealth--;
		assertEquals(currHealth, cruiser.getHealth());
		assertTrue(cruiser.isDestroyed());
	}
	
	@Test
	public void testGetPlayEffects()
	{
		Game game = new GameLogicTests();
		GameMapTests map = new GameMapTests(false, 10, 10);
		game.setAllyMap(map);
		
		Coords cruiserCoords1 = new Coords(0,0);
		assertEquals(GameResult.WATER, game.getPlayEffects(cruiserCoords1));
		
		Coords cruiserCoords2 = new Coords(1,0);
		Coords cruiserCoords3 = new Coords(2,0);
		ArrayList<Coords> shipCoords = new ArrayList<Coords>();
		Ship cruiser = new Cruiser(shipCoords, "horizontal");
		CellState cell1 = new AllyCellState(cruiser);
		CellState cell2 = new AllyCellState(cruiser);
		CellState cell3 = new AllyCellState(cruiser);
		
		map.setCellState(cruiserCoords1, cell1);
		map.setCellState(cruiserCoords2, cell2);
		map.setCellState(cruiserCoords3, cell3);
		
		game.shootAlly(cruiserCoords1);
		assertEquals(GameResult.HIT, game.getPlayEffects(cruiserCoords1));
		
		game.shootAlly(cruiserCoords2);
		assertEquals(GameResult.HIT, game.getPlayEffects(cruiserCoords2));
		
		game.shootAlly(cruiserCoords3);
		assertEquals(GameResult.SINK_CRUISER, game.getPlayEffects(cruiserCoords3));	
	}
	
	
	@Test
	public void testHandleOpponentSankShip()
	{
		Game game = new GameLogicTests();
		GameMapTests map = new GameMapTests(false, 10, 10);
		game.setOpponentMap(map);
		
		
		ArrayList<Coords> shipCoords = new ArrayList<Coords>();
		Coords cruiserCoords1 = new Coords(5,3);
		Coords cruiserCoords2 = new Coords(5,4);
		Coords cruiserCoords3 = new Coords(5,5);
		Ship cruiser = new Cruiser(shipCoords, "vertical");
		CellState cell1 = new AllyCellState(cruiser);
		CellState cell2 = new AllyCellState(cruiser);
		CellState cell3 = new AllyCellState(cruiser);
		map.setCellState(cruiserCoords1, cell1);
		map.setCellState(cruiserCoords2, cell2);
		map.setCellState(cruiserCoords3, cell3);
		cruiser.addCoord(cruiserCoords1);
		cruiser.addCoord(cruiserCoords2);
		cruiser.addCoord(cruiserCoords3);
		cruiser.Destroy();
		
		CellState destroyedShipCell = new OpponentCellState(cruiser, true, true);
		assertEquals(game.handleOpponentSankShip(cruiserCoords2, GameResult.SINK_CRUISER), destroyedShipCell);
	
		ArrayList<Coords> sorroundingCoords = game.getSurroundingCoordsOfShip(cruiser);
		sorroundingCoords.addAll(cruiser.getCoords());
		
		for(Coords coords: sorroundingCoords)
			assertTrue(map.getCellState(coords).isDiscovered());

			
	}
	
}