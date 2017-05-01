package bship.logic;
public class DefaultMap extends GameMap
{
	public DefaultMap()
	{
		sizeX = 10;
		sizeY = 10;
		map = new CellState[sizeX][sizeY];
	}
}
