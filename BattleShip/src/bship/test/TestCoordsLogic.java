package bship.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import bship.logic.Coords;

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
	
	@Test
	public void TestCopyConstructor()
	{
		Coords originalCoords = new Coords(1, 2);
		Coords coords = new Coords(originalCoords);
		assertEquals(originalCoords.GetX(), coords.GetX());
		assertEquals(originalCoords.GetY(), coords.GetY());
		
		//Ensure no aliasing occurs but a separate copy is created
		coords.SetX(5);
		assertEquals(originalCoords, new Coords(1, 2));
		coords.SetY(6);
		assertEquals(originalCoords, new Coords(1, 2));
	}
	
	@Test
	public void TestIncrementers()
	{
		Coords originalCoords = new Coords(1, 2);
		Coords coords = new Coords(originalCoords);
		
		EnsureRightIncrement(coords, 1);
		
		coords = new Coords(originalCoords);
		EnsureRightIncrement(coords, -1);
		
		coords = new Coords(originalCoords);
		EnsureRightIncrement(coords, 1000000);
		
		coords = new Coords(originalCoords);
		EnsureRightIncrement(coords, -1000000);
	}
	
	private void EnsureRightIncrement(Coords coords, int increment)
	{
		int init_x = coords.GetX();
		int init_y = coords.GetY();
		coords.incrementX(increment);
		assertEquals(coords.GetX(), init_x + increment);
		assertEquals(coords.GetY(), init_y);
		
		coords.incrementX(-increment);
		assertEquals(coords.GetX(), init_x);
		assertEquals(coords.GetY(), init_y);
		
		coords.incrementY(increment);
		assertEquals(coords.GetX(), init_x);
		assertEquals(coords.GetY(), init_y + increment);
		
		coords.incrementY(-increment);
		assertEquals(coords.GetX(), init_x);
		assertEquals(coords.GetY(), init_y);
	}
	
	@Test
	public void TestAdjacentGetters()
	{
		Coords coords = new Coords(1, 2);
		assertEquals(coords.left(), new Coords(0, 2));
		assertEquals(coords.right(), new Coords(2, 2));
		assertEquals(coords.up(), new Coords(1, 1));
		assertEquals(coords.down(), new Coords(1, 3));
	}
	
	@Test
	public void TestGetSurroundingCoords()
	{
		ArrayList<Coords> coordsArray = new ArrayList<Coords>();
		ArrayList<Coords> returned;
		ArrayList<Coords> correct = new ArrayList<Coords>();
		
		returned = Coords.getSurroundingCoords(coordsArray);
		assertEquals(returned, correct);
		
		//Comparator coordsComparator = 
		
		coordsArray.add(new Coords(2, 2));
		
		correct.add(new Coords(1, 1));
		correct.add(new Coords(2, 1));
		correct.add(new Coords(3, 1));
		
		correct.add(new Coords(1, 2));
		correct.add(new Coords(3, 2));
		
		correct.add(new Coords(1, 3));
		correct.add(new Coords(2, 3));
		correct.add(new Coords(3, 3));
		
		Collections.sort(correct);
		
		returned = Coords.getSurroundingCoords(coordsArray);
		Collections.sort(returned);
		for (Coords coord : correct)
		{
			System.out.println(coord.GetX() + " " + coord.GetY());
		}
		System.out.println("");
		System.out.println("");
		for (Coords coord : returned)
		{
			System.out.println(coord.GetX() + " " + coord.GetY());
		}
		
		assertEquals(returned, correct);
		

		
	}
}
