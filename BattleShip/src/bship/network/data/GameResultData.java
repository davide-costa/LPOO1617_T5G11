package bship.network.data;

import java.util.ArrayList;

import bship.logic.CellState;
import bship.logic.Coords;

public class GameResultData extends GameData
{
	private ArrayList<Coords> coords;
	private ArrayList<CellState> resultStates;
	
	public GameResultData(ArrayList<Coords> coords, ArrayList<CellState> resultStates)
	{
		this.coords = coords;
		this.resultStates = resultStates;
	}
	
	public ArrayList<Coords> getCoords()
	{
		return coords;
	}
	
	public ArrayList<CellState> getCellStates()
	{
		return resultStates;
	}
}
