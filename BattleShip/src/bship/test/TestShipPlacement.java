package bship.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import bship.logic.AllyCellState;
import bship.logic.Carrier;
import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Cruiser;
import bship.logic.DefaultMap;
import bship.logic.Destroyer;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.MultiplayerShipPlacement;
import bship.logic.OpponentCellState;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.network.data.GameResultData.GameResult;

public class TestShipPlacement
{
	@Test
	public void testIsShipDropValid()
	{
		GameMap map = new DefaultMap(false);
		ShipPlacement shipPlacement = new MultiplayerShipPlacement(map);
		
		Ship shipinMap = new Cruiser();
		ArrayList<Coords> shipinMapCoords = new ArrayList<Coords>();
		shipinMapCoords.add(new Coords(0,0));
		shipinMapCoords.add(new Coords(1,0));
		shipinMapCoords.add(new Coords(2,0));
		shipinMap.setCoords(shipinMapCoords);
		
		//place the ship in map, setting the cell states in it coords as having a ship
		for(Coords coords: shipinMapCoords)
			map.setCellState(coords, new AllyCellState(shipinMap));	

		testPlaceShipVertically(shipPlacement);
		testPlaceShipHorizontally(shipPlacement);
	}
	
	private void testPlaceShipVertically(ShipPlacement shipPlacement)
	{
		ArrayList<Coords> shipToBePlacedCoords = new ArrayList<Coords>();
		
		Ship shipToBePlaced = new Cruiser();
		shipToBePlacedCoords.add(new Coords(0,1));
		shipToBePlacedCoords.add(new Coords(0,2));
		shipToBePlacedCoords.add(new Coords(0,3));
		shipToBePlaced.setCoords(shipToBePlacedCoords);
		assertFalse(shipPlacement.isShipDropValid(shipToBePlaced));
		
		shipToBePlaced = new Cruiser();
		shipToBePlacedCoords.clear();
		shipToBePlacedCoords.add(new Coords(0,2));
		shipToBePlacedCoords.add(new Coords(0,3));
		shipToBePlacedCoords.add(new Coords(0,4));
		shipToBePlaced.setCoords(shipToBePlacedCoords);
		assertTrue(shipPlacement.isShipDropValid(shipToBePlaced));	
	}
	
	private void testPlaceShipHorizontally(ShipPlacement shipPlacement)
	{
		ArrayList<Coords> shipToBePlacedCoords = new ArrayList<Coords>();
		
		Ship shipToBePlaced = new Cruiser();
		shipToBePlacedCoords.add(new Coords(3,0));
		shipToBePlacedCoords.add(new Coords(4,0));
		shipToBePlacedCoords.add(new Coords(5,0));
		shipToBePlaced.setCoords(shipToBePlacedCoords);
		assertFalse(shipPlacement.isShipDropValid(shipToBePlaced));
		
		shipToBePlaced = new Cruiser();
		shipToBePlacedCoords.clear();
		shipToBePlacedCoords.add(new Coords(4,0));
		shipToBePlacedCoords.add(new Coords(4,0));
		shipToBePlacedCoords.add(new Coords(6,0));
		shipToBePlaced.setCoords(shipToBePlacedCoords);
		assertTrue(shipPlacement.isShipDropValid(shipToBePlaced));	
	}
	
	
	@Test
	public void testDropShip()
	{
		GameMap map = new DefaultMap(false);
		ShipPlacement shipPlacement = new MultiplayerShipPlacement(map);
		
		Ship shipinMap = new Cruiser();
		ArrayList<Coords> shipinMapCoords = new ArrayList<Coords>();
		shipinMapCoords.add(new Coords(0,0));
		shipinMapCoords.add(new Coords(1,0));
		shipinMapCoords.add(new Coords(2,0));
		shipinMap.setCoords(shipinMapCoords);
		
		//place the ship in map, setting the cell states in it coords as having a ship
		for(Coords coords: shipinMapCoords)
			map.setCellState(coords, new AllyCellState(shipinMap));	

		String shipToPlaceName = "Destroyer";
		//drop in a npt allowed place
		Ship shipToBePlaced = new Destroyer();
		assertFalse(shipPlacement.dropShip(new Coords(3,0), "Destroyer", "horizontal"));
		assertTrue(shipToBePlaced.getCoords().isEmpty());
		assertNull(map.getCellState(new Coords(3,0)).getShip());
		assertNull(map.getCellState(new Coords(4,0)).getShip());
		assertFalse(shipPlacement.isShipsAlreadyPlaced(shipToPlaceName));
		
		//drop in an allowed place
		shipToBePlaced = new Destroyer();
		assertTrue(shipPlacement.dropShip(new Coords(4,0), "Destroyer", "horizontal"));
		ArrayList<Coords> shipToBePlacedCoords = new ArrayList<Coords>();
		shipToBePlacedCoords.add(new Coords(4,0));
		shipToBePlacedCoords.add(new Coords(5,0));
		//assertEquals(shipToBePlaced.getCoords(), shipToBePlacedCoords);
		
		assertEquals(shipToBePlaced, map.getCellState(new Coords(4,0)).getShip());		
		assertEquals(shipToBePlaced, map.getCellState(new Coords(5,0)).getShip());			
		assertTrue(shipPlacement.isShipsAlreadyPlaced(shipToPlaceName));
	}
}
