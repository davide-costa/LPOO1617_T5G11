package bship.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bship.logic.BattleShip;
import bship.logic.Carrier;
import bship.logic.Coords;
import bship.logic.Cruiser;
import bship.logic.Destroyer;
import bship.logic.Ship;
import bship.logic.Submarine;

public class TestShip 
{
	@Test
	public void testSubmarineConstructor()
	{
		ArrayList<Coords> submarineCoords = new ArrayList<Coords>();
		submarineCoords.add(new Coords(0,0));
		submarineCoords.add(new Coords(1,0));
		submarineCoords.add(new Coords(2,0));
		Ship ship = new Submarine(submarineCoords, "horizontal");
		
		assertEquals(ship.getDirection(), "horizontal");
		assertEquals(ship.getCoords(), submarineCoords);
		assertEquals(ship.getName(), "Submarine");
		assertEquals(ship.getSize(), 3);
		assertEquals(ship.getHealth(), 3);
		assertFalse(ship.isDestroyed());
	}
	
	@Test
	public void testCarrierConstructor()
	{
		ArrayList<Coords> carrierCoords = new ArrayList<Coords>();
		carrierCoords.add(new Coords(0,0));
		carrierCoords.add(new Coords(1,0));
		carrierCoords.add(new Coords(2,0));
		Ship ship = new Carrier(carrierCoords, "vertical");
		
		assertEquals(ship.getDirection(), "vertical");
		assertEquals(ship.getCoords(), carrierCoords);
		assertEquals(ship.getName(), "Carrier");
		assertEquals(ship.getSize(), 5);
		assertEquals(ship.getHealth(), 5);
		assertFalse(ship.isDestroyed());
	}
	
	@Test
	public void testBattleShipConstructor()
	{
		ArrayList<Coords> battleShipCoords = new ArrayList<Coords>();
		battleShipCoords.add(new Coords(0,0));
		battleShipCoords.add(new Coords(1,0));
		battleShipCoords.add(new Coords(2,0));
		Ship ship = new BattleShip(battleShipCoords, "horizontal");
		
		assertEquals(ship.getDirection(), "horizontal");
		assertEquals(ship.getCoords(), battleShipCoords);
		assertEquals(ship.getName(), "BattleShip");
		assertEquals(ship.getSize(), 4);
		assertEquals(ship.getHealth(), 4);
		assertFalse(ship.isDestroyed());
	}
	
	@Test
	public void testCruiserConstructor()
	{
		ArrayList<Coords> cruiserCoords = new ArrayList<Coords>();
		cruiserCoords.add(new Coords(0,0));
		cruiserCoords.add(new Coords(1,0));
		cruiserCoords.add(new Coords(2,0));
		Ship ship = new Cruiser(cruiserCoords, "horizontal");
		
		assertEquals(ship.getDirection(), "horizontal");
		assertEquals(ship.getCoords(), cruiserCoords);
		assertEquals(ship.getName(), "Cruiser");
		assertEquals(ship.getSize(), 3);
		assertEquals(ship.getHealth(), 3);
		assertFalse(ship.isDestroyed());
	}
	
	@Test
	public void testDestroyerConstructor()
	{
		ArrayList<Coords> destroyerCoords = new ArrayList<Coords>();
		destroyerCoords.add(new Coords(0,0));
		destroyerCoords.add(new Coords(1,0));
		destroyerCoords.add(new Coords(2,0));
		Ship ship = new Destroyer(destroyerCoords, "horizontal");
		
		assertEquals(ship.getDirection(), "horizontal");
		assertEquals(ship.getCoords(), destroyerCoords);
		assertEquals(ship.getName(), "Destroyer");
		assertEquals(ship.getSize(), 2);
		assertEquals(ship.getHealth(), 2);
		assertFalse(ship.isDestroyed());
	}
	

	@Test
	public void testFillCoordsByInitCoord()
	{
		Ship ship = new Submarine();
		testHorizontal(ship);
		testVertical(ship);
	}
	
	private void testHorizontal(Ship ship)
	{
		ArrayList<Coords> submarineCoords = new ArrayList<Coords>();
		submarineCoords.add(new Coords(0,0));
		submarineCoords.add(new Coords(1,0));
		submarineCoords.add(new Coords(2,0));
		
		ship.setDirection("horizontal");
		ship.fillCoordsByInitCoord(new Coords(0,0));
		assertEquals(submarineCoords, ship.getCoords());
	}
	
	private void testVertical(Ship ship)
	{
		ArrayList<Coords> submarineCoords = new ArrayList<Coords>();
		submarineCoords.add(new Coords(0,0));
		submarineCoords.add(new Coords(0,1));
		submarineCoords.add(new Coords(0,2));
		
		ship.setDirection("vertical");
		ship.fillCoordsByInitCoord(new Coords(0,0));
		assertEquals(submarineCoords, ship.getCoords());
	}
	
	@Test
	public void testDestroyerGetCopy()
	{
		Ship ship = new Destroyer();
		Ship copy = ship.getCopy();
		assertEquals(ship, copy);
		assertFalse(ship == copy);
	}
	
	@Test
	public void testSubmarineGetCopy()
	{
		Ship ship = new Submarine();
		Ship copy = ship.getCopy();
		assertEquals(ship, copy);
		assertFalse(ship == copy);
	}
	
	@Test
	public void testCruiserGetCopy()
	{
		Ship ship = new Cruiser();
		Ship copy = ship.getCopy();
		assertEquals(ship, copy);
		assertFalse(ship == copy);
	}
	@Test
	public void testBattleShipGetCopy()
	{
		Ship ship = new BattleShip();
		Ship copy = ship.getCopy();
		assertEquals(ship, copy);
		assertFalse(ship == copy);
	}
	
	@Test
	public void testCarrierGetCopy()
	{
		Ship ship = new Carrier();
		Ship copy = ship;
		assertTrue(ship == copy);
		
		copy = ship.getCopy();
		assertEquals(ship, copy);
		assertFalse(ship == copy);
	
	}	
}
