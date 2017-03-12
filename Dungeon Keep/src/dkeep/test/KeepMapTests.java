package dkeep.test;

import java.util.Arrays;

import dkeep.logic.GameMap;
import dkeep.logic.KeepMap;

public class KeepMapTests implements GameMap
{
	private int map_x_size = 5;
	private int map_y_size = 5;
	char[][] map = {
			{ 'X', 'X', 'X', 'X', 'X'},
			{ 'I', 0,  0, 0, 'X'},
			{ 'X', 0, 0, 0, 'X'},
			{ 'X', 'k', 0, 0, 'X'},
			{ 'X', 'X', 'X', 'X', 'X'}
			};

	@Override
	public boolean MoveTo(int x, int y)
	{
		if( x > map_x_size || x < 0 )
			return false;
		
		if(y > map_y_size || y < 0)
			return false;
			
		if(map[y][x] == 'X' || map[y][x] == 'I')
			return false;
		
		return true;	
	}

	public char[][] GetMap()
	{
		return map;
	}
	
	public int GetMapXSize()
	{
		return map_x_size;
	}
	
	public int GetMapYSize()
	{
		return map_y_size;
	}
	
	public GameMap NextMap()
	{
		return new KeepMap();
	}
	
	public void SetCellState(int x, int y, char symbol)
	{
		map[y][x] = symbol;
	}
	
	public char GetCellState(int x, int y)
	{
		return map[y][x];
	}
	
	public char[][] GetMapCopy()
	{
		char map_copy[][] = new char[map_y_size][map_x_size];
		
		for (int y = 0; y < map_y_size; y++)
			map_copy[y] = Arrays.copyOf(map[y], map_x_size);
		
		return map_copy;
	}

	@Override
	public void PickUpKey()
	{
		map[3][1] = (char)0;
	}

	@Override
	public void OpenDoors()
	{
		return;
	}

}