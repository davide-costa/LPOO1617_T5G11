package dkeep.logic;

public interface GameMap
{
	public boolean MoveTo(int x, int y);
	public char[][] GetMap();
	public int GetMapXSize();
	public int GetMapYSize();
	public GameMap NextMap();
	public void SetCellState(int x, int y, char symbol);
	public char GetCellState(int x, int y);
	public char[][] GetMapCopy();
	public void PickUpKey();
	public void OpenDoors();
	public boolean IsDoorOpen();
}
