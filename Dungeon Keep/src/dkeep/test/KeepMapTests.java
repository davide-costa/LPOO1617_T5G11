package dkeep.test;

import java.util.ArrayList;

import dkeep.logic.Coords;
import dkeep.logic.KeepMap;

public class KeepMapTests extends KeepMap
{
	public KeepMapTests()
	{
		map_x_size = 5;
		map_y_size = 5;
		
		char[][] temp_map = {
				{ 'X', 'X', 'X', 'X', 'X'},
				{ 'I', 0,  0, 0, 'X'},
				{ 'X', 0, 0, 0, 'X'},
				{ 'X', 'k', 0, 0, 'X'},
				{ 'X', 'X', 'X', 'X', 'X'}
				};
		
		map = temp_map;
	}
	
	//TODO:
//	@Override
//	public void PickUpKey()
//	{
//		map[3][1] = (char)0;
//		
//	}
}
