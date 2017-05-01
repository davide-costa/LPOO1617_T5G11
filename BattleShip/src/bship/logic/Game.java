package bship.logic;


public class Game
{
	private final char cellNormal = 'N';
	private final char cellDestroyed = 'D';
	private final char cellChecked = 'C';
	private GameMap map;
	Opponent opponent;
	
	public Game(GameMap gameMap)
	{
		map = gameMap;
	}
	
	public CellState getCellState(Coords coords)
	{
		return map.getCellState(coords);
	}
	
	public void setCellState(Coords coords, CellState state)
	{
		map.setCellState(coords, state);
	}
	
	public void shoot(Coords coords)
	{
		/*if (getCellState(coords).equals(cellNormal))
			setCellState(coords, cellDestroyed);
		else
			setCellState(coords, cellChecked);*/
	}
	

	
	
}
