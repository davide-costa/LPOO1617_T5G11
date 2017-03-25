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
	
	public boolean MoveTo(Coords coords)
	{
		int x = coords.GetX();
		int y = coords.GetY();
		
		if (x > map_x_size || x < 0) //Out of range of the map in x
			return false;
		
		if (y > map_y_size || y < 0) //Out of range of the map in y
			return false;
		
		if (map[y][x] == 'X' || map[y][x] == 'I')
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
	
	public ArrayList<Coords> GetDoorsCoords()
	{
		return doors_coords;
	}
	
	public abstract GameMap NextMap();
	
	/**
	 * Sets the state of a cell in the given coords with the given symbol.
	 * @param coords the coords of the cell we want to get the state.
	 * @param symbol the symbol we want to set in the given coords.
	 */
	public void SetCellState(Coords coords, char symbol)
	{
		map[coords.GetY()][coords.GetX()] = symbol;
	}
	
	/**
	 * Returns the state (i.e. representation or symbol) of a the given coords in the map.
	 * @param coords the coords of the cell we want to get the state.
	 * @return the state of the cell.
	 */
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
	
	public Coords GetInitHeroCoords() 
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
