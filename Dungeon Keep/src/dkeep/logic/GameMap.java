package dkeep.logic;

public interface GameMap
{
	public boolean MoveTo(int x, int y);
	public char[][] GetMap();
	public int GetMapXSize();
	public int GetMapYSize();
	public GameMap NextMap();
	public void SetCellAt(int x, int y, char symbol);
	public char GetCellAt(int x, int y);
	public char[][] GetMapCopy();
}
