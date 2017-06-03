package bship.network.data;

import java.awt.Point;

import bship.logic.Coords;

public class GameShootData extends GameData
{
	private int x;
	private int y;
	
	public GameShootData(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
}
