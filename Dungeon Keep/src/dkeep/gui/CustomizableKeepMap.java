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
		hero_coords = null;
		if(mobs_coords == null)
			mobs_coords = new ArrayList<Coords>();
	}
	
	public void CreateNewGameMap(int x_size, int y_size)
	{
		map = new char[y_size][x_size];
		map_x_size = x_size;
		map_y_size = y_size;
		
		//filling the first line and the last one
				for(int i = 0; i < map_y_size; i += map_y_size-1)
					for(int j = 0; j < map_x_size; j++)
						map[i][j] = 'X';
				
				//filling the first column and the last one
				for(int i = 1; i < map_y_size - 1; i++)
					for(int j = 0; j < map_x_size; j += map_x_size - 1)
						if(map[i][j] != 'X' && map[i][j] != 'I')
							map[i][j] = 'X';
				
				map[1][0] = 'I';
	}
	
	public void AddElementAt(Coords board_coords, String element_selected)
	{
		if(IsOutOfRange(board_coords))
			return;
		
		switch(element_selected)
		{
		case "door":
			AddDoorAt(board_coords);
			break;
		case "wall":
			AddWallAt(board_coords);
			break;
		case "key":
			AddKeyAt(board_coords);
			break;
		case "ogre":
			AddOgreAt(board_coords);
			break;
		case "hero":
			AddHeroAt(board_coords);
			break;
		}
		return;
	}
	
	public void AddDoorAt(Coords board_coords)
	{
		if(board_coords.GetX() == 0 || board_coords.GetX() == map_x_size - 1)
			if(board_coords.GetY() == 0 || board_coords.GetY() == map_y_size - 1)
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
		SetCellState(board_coords, 'O');
		if (mobs_coords.size() > 5)
		{
			SetCellState(mobs_coords.get(0), (char)0);
			mobs_coords.remove(0);
		}
	}
	
	public void AddHeroAt(Coords board_coords)
	{
		//ensure that the user cannot place multiple hero instances on the map
		if (hero_coords != null)
			SetCellState(hero_coords, (char)0);
		
		hero_coords = board_coords;
		SetCellState(hero_coords, 'H');
	}
	
	public void RemoveElementAt(Coords board_coords)
	{
		if(IsOutOfRange(board_coords))
			return;
		
		char state = GetCellState(board_coords);
		if(state == 'H')
			RemoveHero();
		if(state == 'O')
			RemoveOgre(board_coords);
		
		SetCellState(board_coords, (char) 0);
	}
	
	public void RemoveOgre(Coords board_coords)
	{
		mobs_coords.remove(board_coords);
	}
	
	public void RemoveHero()
	{
		hero_coords = null;
	}
	
	public ArrayList<String> IsMapValid()
	{
		ArrayList<String> error_messages = new ArrayList<String>();
		
		if(mobs_coords.size() == 0)
		{
			error_messages.add("The map must have at least one ogre");
		}
		
		if(hero_coords == null)
		{
			error_messages.add("The map must have a hero");
		}
		
		boolean key_found = false;
		loop:		
		for(int i = 0; i < map_y_size; i++)
			for(int j = 0; j < map_x_size; j++)
			{
				if(map[i][j] == 'k')
				{
					key_found = true;
					break loop;
				}
			}
		if(!key_found)
			error_messages.add("The map must have at least one key");
		
		if(!IsMapClosed())
			error_messages.add("The map must be closed (having a wall or door at his round)");
		
		return error_messages;
	}
	
	public boolean IsMapClosed()
	{
		System.out.println(map_x_size);
		System.out.println(map_y_size);
		//analising the first line and the last one
		for(int i = 0; i < map_y_size; i += map_y_size-1)
			for(int j = 0; j < map_x_size; j++)
				if(map[i][j] != 'X' && map[i][j] != 'I')
					return false;
		
		//analising the first column and the last one
		for(int i = 1; i < map_y_size - 1; i++)
			for(int j = 0; j < map_x_size; j += map_x_size - 1)
				if(map[i][j] != 'X' && map[i][j] != 'I')
					return false;
				
		return true;
	}
	

	 public boolean IsOutOfRange(Coords board_coords)
	 {
		 if(board_coords.GetX() >= map_x_size || board_coords.GetX() < 0)
			 return true;
		 if(board_coords.GetY() >= map_y_size || board_coords.GetY() < 0)
			 return true;
		 return false;
	 }
	
	public void SaveTo(String file_path)
	{
		for (Coords coord : mobs_coords)
		{
			SetCellState(coord, (char)0);
		}
		SetCellState(hero_coords, (char)0);
		
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
