package dkeep.logic;

public interface GameMap
{
	public boolean MoveTo(int x, int y);
	public char[][] GetMap();
	GameMap NextMap();
}
