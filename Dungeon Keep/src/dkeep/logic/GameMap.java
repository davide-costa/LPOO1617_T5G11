package dkeep.logic;

import java.util.ArrayList;

public interface GameMap
{
	public boolean MoveTo(Coords coords);
	public char[][] GetMap();
	public int GetMapXSize();
	public int GetMapYSize();
	public GameMap NextMap();
	public void SetCellState(Coords coords, char symbol);
	public char GetCellState(Coords coords);
	public char[][] GetMapCopy();
	void PickUpKey(Coords key_coords);
	public void OpenDoors();
	public boolean IsDoorOpen();
	public ArrayList<Coords> GetInitMobsCoords();
}
