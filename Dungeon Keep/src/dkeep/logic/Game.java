package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Game
{
	private GameMap map;
	private int level;
	private Hero hero;
	private Guard guard;
	private Ogre ogre;
	private int temp_x;
	private int temp_y;
	
	public Game()
	{
		hero = new Hero(1,1);
		guard = new Guard(8,1,'G');
		ogre = new Ogre(4,1, 'O');
	}
	
	public GameMap GetGameMap()
	{
		return map;
	}
	
	public void SetGameMap(GameMap map)
	{
		this.map = map;
	}
	
	public int MoveHero(int x, int y)
	{
		if(!map.MoveTo(x, y))
			return 0;
		
		switch(level)
		{
		case 1:
			MoveGuard();
			break;
		case 2:
			MoveOgreAndClub();
			break;
		}

		int make_play_value = MakePlay(x,y);
		if (make_play_value == 1)
		{
			SetGameMap(map.NextMap());
			level++;
			switch(level)
			{
			case 2:
				ogre.SetX(4);
				ogre.SetY(1);
				ogre.SetClubX(4);
				ogre.SetClubY(2);
				hero.SetX(1);
				hero.SetY(7);
				break;
			}
		}
		
		return make_play_value;
	}
	
	public void MoveGuard()
	{
		
	}
	
	public void MoveOgreAndClub()
	{
		while (!TryClubNextPos());
	}
	
	public void MakePlay(int x, int y)
	{
		map.SetCellAt(hero.GetX(), hero.GetY(), (char)0);
		
		hero.SetX(x);
		hero.SetY(y);
		
		char dst_state = 
		
	}
	
	public boolean TryClubNextPos()
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		temp_x = ogre.GetClubX();
		temp_y = ogre.GetClubY();
		
		if (randomNum == 1)
			temp_x++; //club moves to right
		else if (randomNum == 2)
			temp_x--; //club moves to left
		else if (randomNum == 3)
			temp_y++; //club moves up
		else if (randomNum == 4)
			temp_y--; //club moves to down

		return map.MoveTo(temp_x, temp_y);
	}

}
