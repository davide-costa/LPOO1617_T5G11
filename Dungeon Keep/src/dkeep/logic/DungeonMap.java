package dkeep.logic;

public class DungeonMap implements GameMap
{
	private char[][] map =  {
			{ 'X','X','X','X','X','X','X','X','X','X' },
			{ 'X','H',0,0,'I',0,'X',0,'G','X' },
			{ 'X','X','X',0,'X','X','X',0,0,'X' },
			{ 'X',0,'I',0,'I',0,'X',0,0,'X' },
			{ 'X','X','X',0,'X','X','X',0,0,'X' },
			{ 'I',0,0,0,0,0,0,0,0,'X' },
			{ 'I',0,0,0,0,0,0,0,0,'X' },
			{ 'X','X','X',0,'X','X','X','X',0,'X' },
			{ 'X',0,'I',0,'I',0,'X','k',0,'X' },
			{ 'X','X','X','X','X','X','X','X','X','X'}
			};
	
	private int map_x_size = 10;
	private int map_y_size = 10;
	
	public boolean MoveTo(int x, int y)
	{
		if( x > 9 || x < 0 )
			return false;
		
		if(y > 9 || y < 0)
			return false;
			
		if(map[y][x] == 'X' || map[y][x] == 'I')
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
		return new KeepMap();
	}
	
	public void SetCellAt(int x, int y, char symbol)
	{
		map[y][x] = symbol;
	}
	
	public char GetCellAt(int x, int y)
	{
		return map[y][x];
	}
}