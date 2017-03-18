package dkeep.logic;

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
	public void PickUpKey();
	public void OpenDoors();
	public boolean IsDoorOpen();
}
