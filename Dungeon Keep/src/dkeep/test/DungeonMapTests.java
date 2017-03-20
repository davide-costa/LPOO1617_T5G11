package dkeep.test;

import java.util.ArrayList;

import dkeep.logic.Coords;
import dkeep.logic.DungeonMap;

public class DungeonMapTests extends DungeonMap
{	
	public DungeonMapTests()
	{
		map_x_size = 5;
		map_y_size = 5;
		
		char[][] temp_map = {
				{ 'X', 'X', 'X', 'X', 'X'},
				{ 'X', 0,  0, 0, 'X'},
				{ 'I', 0, 0, 0, 'X'},
				{ 'I', 'k', 0, 0, 'X'},
				{ 'X', 'X', 'X', 'X', 'X'}
				};
		
		map = temp_map;
		doors_coords = new ArrayList<Coords>();
		doors_coords.add(new Coords(0, 2));
		doors_coords.add(new Coords(0, 3));	
		key_coords = new Coords(1, 3);
	}
//
//	@Override
//	public void PickUpKey()
//	{
//		map[3][1] = (char)0;
//	}
//
//	@Override
//	public boolean IsDoorOpen() 
//	{
//		if(map[5][0] == 'S' || map[6][0] == 'S')
//			return true;
//		else
//			return false;
//	}
}
