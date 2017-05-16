package bship.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import bship.logic.Carrier;
import bship.logic.Coords;
import bship.logic.Destroyer;
import bship.logic.Game;
import bship.logic.Ship;

public class TestGameLogic
{
	@Test
	public void TestGetInstance()
	{
		Game game1 = Game.getInstance();
		Game game2 = Game.getInstance();
		assertEquals(game1, game2);
	}
	
	@Test
	public void TestGetSorroundingCoordsOfShip()
	{
		Game game = Game.getInstance();
		
		TestVerticalLeftBottomShip(game);
		TestVerticalRightUpShip(game);
	}
	
	private void TestVerticalLeftBottomShip(Game game)
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
	
	private void TestVerticalRightUpShip(Game game)
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
}