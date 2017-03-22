package dkeep.gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import dkeep.logic.Coords;
import dkeep.logic.KeepMap;

public class CustomizableKeepMap extends KeepMap
{	
	public CustomizableKeepMap()
	{
		super();
		map[hero_coords.GetY()][hero_coords.GetX()] = 'H';
	}
	
	public void AddDoorAt(Coords board_coords)
	{
		SetCellState(board_coords, 'I');
	}
	
	public void AddWallAt(Coords board_coords)
	{
		SetCellState(board_coords, 'X');
	}
	
	public void AddKeyAt(Coords board_coords)
	{
		SetCellState(board_coords, 'k');
	}
	
	public void AddOgreAt(Coords board_coords)
	{
		mobs_coords.add(board_coords);
		//TODO:
	}
	
	public void AddHeroAt(Coords board_coords)
	{
		hero_coords = board_coords;
		//TODO:
	}
	
	public void RemoveElement(Coords board_coords)
	{
		char state = GetCellState(board_coords);
		if(state == 'H')
			RemoveHero();
		if(state == 'O')
			RemoveOgre(board_coords);
		
		SetCellState(board_coords, (char) 0);
	}
	
	/*public void RemoveDoorAt(Coords board_c)
	{
		SetCellState(board_c, (char) 0);
	}
	
	public void RemoveWallAt(Coords board_c)
	{
		SetCellState(board_c, (char) 0);
	}
	
	public void RemoveKeyAt(Coords scr_coords)
	{
		SetCellState(board_c, (char) 0);
	}*/
	
	public void RemoveOgre(Coords board_coords)
	{
		mobs_coords.remove(board_coords);
		//TODO:
	}
	
	public void RemoveHero()
	{
		hero_coords = null;
		//TODO:
	}
	
	public ArrayList<String> IsMapValid()
	{
		ArrayList<String> error_messages = new ArrayList<String>();
		
		if(mobs_coords.size() == 0)
		{
			error_messages.add("The map must have at least one ogre");
		}
		//if(keys.size() == 0)
			//return false;
		if(hero_coords == null)
		{
			error_messages.add("The map must have a hero");
		}
		
		boolean key_found = false;
		for(int i = 0; i < map_y_size; i++)
			for(int j = 0; j < map_x_size; j++)
			{
				if(map[i][j] == 'k')
				{
					key_found = true;
					continue;
				}
			}
		
		if(!key_found)
			error_messages.add("The map must have at least one key");
		
		if(!IsMapClosed())
			error_messages.add("The map must have be closed (having a wall or door at his round)");
		
		return error_messages;
		
		//TODO:
	}
	
	public boolean IsMapClosed()
	{
		//analising the first line and the last one
		for(int i = 0; i < map_y_size; i += map_y_size-1)
			for(int j = 0; j < map_x_size; j++)
				if(map[i][j] != 'X' || map[i][j] != 'I')
					return false;
		
		//analising the first column and the last one
		for(int i = 1; i < map_y_size - 1; i++)
			for(int j = 1; j < map_x_size; j += map_x_size - 1)
				if(map[i][j] != 'X' || map[i][j] != 'I')
					return false;
				
		return true;
	}
	
	public void SaveTo(String file_path)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(file_path);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this);
	        out.close();
	        fileOut.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
