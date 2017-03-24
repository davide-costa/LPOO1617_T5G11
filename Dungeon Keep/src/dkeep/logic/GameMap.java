package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameMap implements Serializable
{
	private static final long serialVersionUID = 3515396607030332572L;
	protected Coords hero_coords;
	protected char[][] map;
	protected int map_x_size;
	protected int map_y_size;
	protected ArrayList<Coords> doors_coords;
	
	public abstract boolean MoveTo(Coords coords);
	
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
	
	public ArrayList<Coords> GetDoorsCoords()
	{
		return doors_coords;
	}
	
	public abstract GameMap NextMap();
	
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
	
	public Coords GetHeroCoords() 
	{
		return hero_coords;
	}

	public void PickUpKey(Coords key_coords)
	{
		SetCellState(key_coords, (char)0);
	}
	
	public abstract void OpenDoors();
	public abstract boolean IsDoorOpen();
	public abstract ArrayList<Coords> GetInitMobsCoords();
}
