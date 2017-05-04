package bship.network.data;

public class GameShootData extends GameData
{
	private Object coords;
	
	public GameShootData(Object coords)
	{
		this.coords = coords;
	}
	
	public Object getCoords()
	{
		return coords;
	}
	
}
