package bship.logic;


public class Game
{
	private final char cellNormal = 'N';
	private final char cellDestroyed = 'D';
	private final char cellChecked = 'C';
	private GameMap map;
	Player opponent;
	
	public Game(GameMap gameMap)
	{
		map = gameMap;
	}
	
	public char getCellState(Coords coords)
	{
		return map.getCellState(coords);
	}
	
	public void setCellState(Coords coords, char symbol)
	{
		map.setCellState(coords, symbol);
	}
	
	public void shoot(Coords coords)
	{
		if (getCellState(coords) == cellNormal)
			setCellState(coords, cellDestroyed);
		else
			setCellState(coords, cellChecked);
	}
	

	
	
}
