package bship.test;

import bship.gui.GameGui;
import bship.logic.DefaultMap;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.Opponent;

public class GameLogicTests extends Game
{
	public GameLogicTests()
	{
		super(new DefaultMap(false), null, null);
	}
	
	public void setAllyMap(GameMap allyGameMap)
	{
		this.map = allyGameMap;
	}
	
	public void setOpponentMap(GameMap opponentGameMap)
	{
		this.opponentMap = opponentGameMap;
	}
}
