package dkeep.test;

import dkeep.logic.DungeonMap;

public class DungeonMapTests extends DungeonMap
{
	char[][] map = {
			{ 'X', 'X', 'X', 'X', 'X'},
			{ 'X', 0,  0, 0, 'X'},
			{ 'I', 0, 0, 0, 'X'},
			{ 'I', 'k', 0, 0, 'X'},
			{ 'X', 'X', 'X', 'X', 'X'}
			};
	
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
	}

	@Override
	public void PickUpKey()
	{
		map[3][1] = (char)0;
	}

	@Override
	public void OpenDoors()
	{
		map[2][0] = 'S';
		map[3][0] = 'S';
	}

	@Override
	public boolean IsDoorOpen() 
	{
		if(map[5][0] == 'S' || map[6][0] == 'S')
			return true;
		else
			return false;
	}
}
