package bship.network.data;

import java.util.ArrayList;

import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Ship;

public class GameResultData extends GameData
{
	static private char HIT = (char) 0; 
	static private char WATER = (char) 1; 
	static private char SINK = (char) 2; 
	private Coords coords;
	private Ship atackedShip;
	private char shootResult;
	private boolean endOfGame;
	
	
	public GameResultData(Coords coords, Ship atackedShip, char shootResult)
	{
		this.coords = coords;
		this.atackedShip = atackedShip;
		this.shootResult = shootResult;
	}
	
	public Coords getCoords()
	{
		return coords;
	}
	
	public Ship getShip()
	{
		return atackedShip;
	}
	
	public char getShootResult()
	{
		return shootResult;
	}
}
