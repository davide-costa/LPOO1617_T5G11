package dkeep.gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import dkeep.logic.Coords;
import dkeep.logic.KeepMap;

public class CustomizableKeepMap extends KeepMap
{
	public Coords ScrCoordsToBoardCoords(Coords scr_coords)
	{
		//TODO: make the adequate convertion
		int x = scr_coords.GetX();
		int y = scr_coords.GetY();
		
		return new Coords(x, y);
	}
	
	public void AddDoorAt(Coords scr_coords)
	{
		Coords board_c = ScrCoordsToBoardCoords(scr_coords);
		SetCellState(board_c, 'I');
	}
	
	public void AddWallAt(Coords scr_coords)
	{
		Coords board_c = ScrCoordsToBoardCoords(scr_coords);
		SetCellState(board_c, 'X');
	}
	
	public void AddKeyAt(Coords scr_coords)
	{
		Coords board_c = ScrCoordsToBoardCoords(scr_coords);
		SetCellState(board_c, 'k');
	}
	
	public void AddOgre(Coords scr_coords)
	{
		Coords board_c = ScrCoordsToBoardCoords(scr_coords);
		//TODO:
	}
	
	public void AddHero(Coords scr_coords)
	{
		Coords board_c = ScrCoordsToBoardCoords(scr_coords);
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
