package bship.logic;

public abstract class Ship
{
	protected int size;
	Coords startPos;
	String direction;
	public Ship(int size, Coords startPos, String direction)
	{
		this.size = size;
		this.startPos = new Coords(startPos);
		this.direction = new String(direction);
	}
}
