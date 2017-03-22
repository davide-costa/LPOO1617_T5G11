package dkeep.gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
	
	public boolean IsMapValid()
	{
		if(mobs_coords.size() == 0)
			return false;
		//if(keys.size() == 0)
			//return false;
		if(hero_coords == null)
			return false;
		
		return true;
		
		//TODO:
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
