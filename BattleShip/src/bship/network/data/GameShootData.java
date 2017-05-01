package bship.network.data;

import bship.logic.Coords;

public class GameShootData extends GameData
{
	private Coords coords;
	
	public GameShootData(Coords coords)
	{
		this.coords = coords;
	}
	
	public Coords getCoords()
	{
		return coords;
	}
	
}
