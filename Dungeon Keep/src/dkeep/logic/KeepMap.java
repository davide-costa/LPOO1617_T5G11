package dkeep.logic;

public class KeepMap implements GameMap
{
	private char map[][] = {
			{ 'X','X','X','X','X','X','X','X','X' },
			{ 'I',0,0,0,'O','*',0,'k','X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X',0,0,0,0,0,0,0,'X' },
			{ 'X','H',0,0,0,0,0,0,'X' },
			{ 'X','X','X','X','X','X','X','X','X' }
		};
	
	private int map_x_size = 10;
	private int map_y_size = 10;
	
	public boolean MoveTo(int x, int y)
	{
		if (x > 8 || x < 0) //Out of range of the map in x
			return false;
		
		if (y > 8 || y < 0) //Out of range of the map in y
			return false;
		
		if (map[y][x] == 'X' || map[y][x] == 'I')
			return false;
		
		return true;
	}
	
	public char[][] GetMap()
	{
		return map;
	}
	
	public int GetMapXSize()
	{
		return map_x_size;
	}
	
	public int GetMapYSize()
	{
		return map_y_size;
	}
	
	
	public GameMap NextMap()
	{
		return null;
	}
	
	public void SetCellAt(int x, int y, char symbol)
	{
		map[y][x] = symbol;
	}
}
