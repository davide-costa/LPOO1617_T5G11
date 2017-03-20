package dkeep.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class KeepMap implements GameMap, Serializable
{
	private static final long serialVersionUID = 8532318868372099106L;
	protected ArrayList<Coords> mobs_coords;

	public KeepMap()
	{
		try
		{
			LoadMapFromFile("KeepMap");
		}
		catch(IOException | ClassNotFoundException c) 
	    {
			//do nothing, leave the default map
			return;	
	    }
	}
	
	protected char map[][] = {
			{ 'X','X','X','X','X','X','X','X','X' },
			{ 'I',0,0,0,0,0,0,'k','X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X','X','X','X','X','X','X','X','X' }
		};
	
	protected int map_x_size = 9;
	protected int map_y_size = 9;
	protected boolean door_open = false;
	
	public boolean MoveTo(Coords coords)
	{
		int x = coords.GetX();
		int y = coords.GetY();
		
		if (x == 0 && y == 1 && door_open)
			return true;
		
		if (x > map_x_size || x < 0) //Out of range of the map in x
			return false;
		
		if (y > map_y_size || y < 0) //Out of range of the map in y
			return false;
		
		if (map[y][x] == 'X' || (map[y][x] == 'I' && map[y][x+1] != 'K'))
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
		return null;
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
	public void PickUpKey(Coords key_coords)
	{
		SetCellState(key_coords, (char)0);
	}

	@Override
	public void OpenDoors()
	{
		door_open = true;
		return;
	}

	@Override
	public boolean IsDoorOpen() 
	{
		return door_open;
	}

	@Override
	public ArrayList<Coords> GetInitMobsCoords()
	{
		return mobs_coords;
	}
	
	private void LoadMapFromFile(String file_path) throws IOException, ClassNotFoundException
	{
		FileInputStream fileIn = new FileInputStream("GameStateFile");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		KeepMap t = this;
		t = (KeepMap) in.readObject();
		in.close();
		fileIn.close();
	}
}
