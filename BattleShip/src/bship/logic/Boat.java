package bship.logic;

public class Boat
{
	protected int size;
	Coords startPos;
	String direction;
	public Boat(int size, Coords startPos, String direction)
	{
		this.size = size;
		this.startPos = new Coords(startPos);
		this.direction = new String(direction);
	}
}
