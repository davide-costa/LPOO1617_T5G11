package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DungeonMap extends GameMap implements Serializable
{
	private static final long serialVersionUID = -7021531527768779419L;
	protected Coords guard_coords;

	public DungeonMap()
	{
		doors_coords = new ArrayList<Coords>();
		doors_coords.add(new Coords(0, 5));
		doors_coords.add(new Coords(0, 6));
		hero_coords = new Coords(1, 1);
		map_x_size = 10;
		map_y_size = 10;
		char[][] dungeon_map =  {
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
		map = dungeon_map;
	}
		
	public GameMap NextMap()
	{
		return new KeepMap();
	}
	
	@Override
	public void OpenDoors()
	{
		for (Coords door_c : doors_coords)
		{
			SetCellState(door_c, 'S');
		}
	}

	@Override
	public boolean IsDoorOpen() 
	{
		for (Coords door_c : doors_coords)
		{
			if (GetCellState(door_c) == 'S')
				return true;
		}
		
		return false;
	}

	@Override
	public ArrayList<Coords> GetInitMobsCoords()
	{
		ArrayList<Coords> coords = new ArrayList<Coords>();
		coords.add(guard_coords);
		return coords;
	}
}