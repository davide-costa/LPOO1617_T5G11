package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Ogre extends GameCreature
{
	private int club_x;
	private int club_y;
	private char club_symbol = '*';
	private int stun_count;
	
	public Ogre(int x, int y, int club_x, int club_y)
	{
		super(x, y, 'O');
		this.club_x = club_x;
		this.club_y = club_y;
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
	
	public void Stun()
	{
		this.symbol = '8';
		this.stun_count = 0;
	}
	
	public void RefreshStun()
	{
		if(symbol != '8')
			return;

		this.stun_count++;
		if(stun_count == 2)
			symbol = 'O';
	}
}
