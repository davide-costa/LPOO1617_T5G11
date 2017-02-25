package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Ogre extends GameCreature
{
	private int club_x = 5;
	private int club_y = 1;
	private char club_symbol = '*';
	
	public Ogre(int x, int y, char symbol)
	{
		super(x, y, 'O');
	}
	
	public int GetClubX()
	{
		return club_x;
	}
	
	public void SetClubX(int club_x)
	{
		this.club_x = club_x;
	}
	
	public int GetClubY()
	{
		return club_y;
	}
	
	public void SetClubY(int club_y)
	{
		this.club_y = club_y;
	}
	
	public char GetClubSymbol()
	{
		return club_symbol;
	}
	
	public void SetClubSymbol(char club_symbol)
	{
		this.club_symbol = club_symbol;
	}
}
