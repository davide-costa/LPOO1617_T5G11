package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Ogre extends GameCreature
{
	private static char default_symbol = 'O';
	private static char stunned_symbol = '8';
	private static char owns_key_symbol = '$';
	private int club_x;
	private int club_y;
	private char club_symbol = '*';
	private int stun_count;
	private boolean is_stunned;
	
	public Ogre(int x, int y, int club_x, int club_y)
	{
		super(x, y, default_symbol);
		this.club_x = club_x;
		this.club_y = club_y;
		is_stunned = false;
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
	
	public void Move(GameMap map)
	{		
		RefreshStun();
		if(GetSymbol() != '8')
			while (!TryOgreNextPos(map));
		while (!TryClubNextPos(map));
	}
	

	public boolean TryOgreNextPos(GameMap map)
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		int temp_x = x;
		int temp_y = y;

		if (randomNum == 1)
			temp_x++; //ogre moves to right
		else if (randomNum == 2)
			temp_x--; //ogre moves to left
		else if (randomNum == 3)
			temp_y++; //ogre moves up
		else if (randomNum == 4)
			temp_y--; //ogre moves to down

		if (!map.MoveTo(temp_x, temp_y))
			return false;

		SetX(temp_x);
		SetY(temp_y);
		
		return true;
	}
	
	//TODO colocar no ogre
	public boolean TryClubNextPos(GameMap map)
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		int temp_x = x;
		int temp_y = y;
		
		if (randomNum == 1)
			temp_x++; //club moves to right
		else if (randomNum == 2)
			temp_x--; //club moves to left
		else if (randomNum == 3)
			temp_y++; //club moves up
		else if (randomNum == 4)
			temp_y--; //club moves to down

		if (!map.MoveTo(temp_x, temp_y))
			return false;
		
		SetClubX(temp_x);
		SetClubY(temp_y);
		
		return true;
	}
		
	public void Stun()
	{
		this.is_stunned = true;
		this.symbol = stunned_symbol;
		this.stun_count = 0;
	}
	
	public void RefreshStun()
	{
		if(!is_stunned)
			return;

		if(stun_count == 2)
		{
			is_stunned = false;
			symbol = default_symbol;
		}
		
		this.stun_count++;
	}
	
	public void SetOwnsKey()
	{
		symbol = owns_key_symbol;
	}
	
	public void SetNotOwnsKey()
	{
		if (is_stunned)
			symbol = stunned_symbol;
		else
			symbol = default_symbol;
	}
	
	
}
