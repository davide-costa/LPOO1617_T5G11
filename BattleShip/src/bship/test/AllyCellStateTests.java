package bship.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bship.logic.AllyCellState;
import bship.logic.Carrier;
import bship.logic.CellState;
import bship.logic.Ship;

public class AllyCellStateTests 
{
	@Test
	public void testIsDiscoveredAndShip()
	{
		CellState stateNotShip = new AllyCellState(null);
		assertFalse(stateNotShip.isDiscoveredAndShip());
		
		Ship ship = new Carrier();
		CellState stateNotDiscovered = new AllyCellState(ship, false);
		assertFalse(stateNotDiscovered.isDiscoveredAndShip());
		
		CellState stateDiscovered = new AllyCellState(ship, true);
		assertTrue(stateDiscovered.isDiscoveredAndShip());	
	}
	
	@Test
	public void testHasShipDestroyed()
	{
		//without ship
		CellState state = new AllyCellState(null, true);
		assertFalse(state.hasShipDestroyed());
		
		state = new AllyCellState(null, false);
		assertFalse(state.hasShipDestroyed());
		
		//with ship
		Ship ship = new Carrier();
		state = new AllyCellState(ship, false);
		assertFalse(state.hasShipDestroyed());
		ship.Destroy();
		assertTrue(state.hasShipDestroyed());
		
		state = new AllyCellState(ship, true);
		assertTrue(state.isDiscoveredAndShip());
	}
	
	@Test
	public void testGetCopy()
	{
		//without ship
		CellState state = new AllyCellState(null);
		CellState copy = state.getCopy();
		assertEquals(state, copy);
		
		//with ship
		state = new AllyCellState(new Carrier());
		copy = state.getCopy();
		assertEquals(state, copy);
		assertFalse(state == copy);
	}
}
