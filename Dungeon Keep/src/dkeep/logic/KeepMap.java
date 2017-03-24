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

public class KeepMap extends GameMap implements Serializable
{
	private static final long serialVersionUID = 8532318868372099106L;
	protected ArrayList<Coords> mobs_coords;
	protected boolean doors_open = false;
	
	public KeepMap()
	{
		map_x_size = 9;
		map_y_size = 9;
		char keep_map[][] = {
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
		map = keep_map;
		doors_coords = new ArrayList<Coords>();
		doors_coords.add(new Coords(0,1));
	}
	
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
	
	public GameMap NextMap()
	{
		return null;
	}

	@Override
	public void OpenDoors()
	{
		doors_open = true;
		return;
	}

	@Override
	public boolean IsDoorOpen() 
	{
		return doors_open;
	}

	@Override
	public ArrayList<Coords> GetInitMobsCoords()
	{
		return mobs_coords;
	}
	
	public void LoadMapFromFile(String file_path) throws IOException, ClassNotFoundException
	{
		FileInputStream fileIn = new FileInputStream("KeepMap");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		KeepMap t = this;
		t = (KeepMap) in.readObject();
		in.close();
		fileIn.close();
	}
}
