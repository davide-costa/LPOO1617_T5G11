package dkeep.logic;

import java.io.Serializable;
import java.util.Arrays;

public class DungeonMap implements GameMap, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7021531527768779419L;

	protected char[][] map =  {
			{ 'X','X','X','X','X','X','X','X','X','X' },
			{ 'X',0,0,0,'I',0,'X',0,0,'X' },
			{ 'X','X','X',0,'X','X','X',0,0,'X' },
			{ 'X',0,'I',0,'I',0,'X',0,0,'X' },
			{ 'X','X','X',0,'X','X','X',0,0,'X' },
			{ 'I',0,0,0,0,0,0,0,0,'X' },
			{ 'I',0,0,0,0,0,0,0,0,'X' },
			{ 'X','X','X',0,'X','X','X','X',0,'X' },
			{ 'X',0,'I',0,'I',0,'X','k',0,'X' },
			{ 'X','X','X','X','X','X','X','X','X','X'}
			};
	
	protected int map_x_size = 10;
	protected int map_y_size = 10;
	
	public boolean MoveTo(Coords coords)
	{
		int x = coords.GetX();
		int y = coords.GetY();
		
		if(x > map_x_size || x < 0)
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
	
	public void SetCellState(Coords coords, char symbol)
	{
		map[coords.GetY()][coords.GetX()] = symbol;
	}
	
	public char GetCellState(Coords coords)
	{
		return map[coords.GetY()][coords.GetX()];
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
		map[8][7] = (char)0;
	}

	@Override
	public void OpenDoors()
	{
		map[5][0] = 'S';
		map[6][0] = 'S';
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