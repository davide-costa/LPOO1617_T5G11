package bship.logic;


public class Game
{
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
	
	//this method is called by GUI to shoot the opponent and informs Opponent of the shot
	public void shootOpponent(Coords coords)
	{
		/*if (getCellState(coords).equals(cellNormal))
			setCellState(coords, cellDestroyed);
		else
			setCellState(coords, cellChecked);*/
	}
	
	//this method is called by Opponent class when the opponent shoots this player and informs the effects to Opponent class. The GUI is notified by observing that the map changed
	public void shootAlly(Coords coords)
	{
		
	}
	

	
	
}
