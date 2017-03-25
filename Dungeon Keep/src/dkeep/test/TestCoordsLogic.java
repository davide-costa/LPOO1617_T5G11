package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Coords;

public class TestCoordsLogic
{
	@Test
	public void TestConstructors()
	{
		Coords coords = new Coords(1,2);
		assertEquals(1, coords.GetX());
		assertEquals(2, coords.GetY());
	}
	
	@Test
	public void TestGettersAndSetters()
	{
		Coords coords = new Coords(1,2);
		coords.SetX(4);
		assertEquals(4, coords.GetX());
		coords.SetY(5);
		assertEquals(5, coords.GetY());
		
		assertEquals(new Coords(4, 5), coords);
		Coords coords2 = new Coords(3, 4);
		coords.Set(coords2);
		assertEquals(new Coords(3, 4), coords);
		assertEquals(3, coords.GetX());
		assertEquals(4, coords.GetY());
		
		coords.Set(1, 2);
		assertEquals(new Coords(1, 2), coords);
		assertEquals(1, coords.GetX());
		assertEquals(2, coords.GetY());
	}
	
	@Test
	public void TestAdjacentTo()
	{
		Coords coords = new Coords(1,2);
		Coords coords1 = new Coords(0, 2);
		Coords coords2 = new Coords(2, 2);
		Coords coords3 = new Coords(1, 1);
		Coords coords4 = new Coords(1, 3);
		
		Coords coords5 = new Coords(3, 4);
		
		assertTrue(coords.adjacentTo(coords1));
		assertTrue(coords.adjacentTo(coords2));
		assertTrue(coords.adjacentTo(coords3));
		assertTrue(coords.adjacentTo(coords4));
		assertEquals(false, coords.adjacentTo(coords5));
	}
}
