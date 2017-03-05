package dkeep.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game
{
	private GameMap map;
	private int level;
	private GameCreature curr_mob;
	private Hero hero;
	private Guard guard;
	List<Ogre> ogres;
	private int temp_x;
	private int temp_y;
	
	public Game()
	{
		hero = new Hero(1,1);
		InitLevel1();
		
		//debugging
		SetGameMap(map.NextMap());
		InitLevel2();
	}
	
	public void InitLevel1()
	{
		level = 1;
		//Generate the type of guard for this game
		int min = 1;
		int max = 3;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		if(randomNum == 1)
			guard = new Rookie(8,1,'G');
		else if(randomNum == 2)
			guard = new Drunken(8,1,'G');
		else
			guard = new Suspicious(8,1,'G');
		
		SetGameMap(new DungeonMap());
		curr_mob = guard;
	}
	
	public void InitLevel2()
	{
		int ogre_spawn_x[] = {2, 3, 4};
		int ogre_spawn_y[] = {1, 1, 1};
		int club_spawn_x[] = {2, 3, 4};
		int club_spawn_y[] = {2, 2, 2};
		
		
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
		
		ogres = new ArrayList<Ogre>();
		Ogre curr_ogre;
		for (int i = 0; i < randomNum; i++)
		{
			curr_ogre = new Ogre(ogre_spawn_x[i], ogre_spawn_y[i], club_spawn_x[i], club_spawn_y[i]);
			map.SetCellAt(ogre_spawn_x[i], ogre_spawn_y[i], 'O');
			map.SetCellAt(club_spawn_x[i], club_spawn_y[i], '*');
			ogres.add(curr_ogre);
		}
		level = 2;
		hero.SetX(1);
		hero.SetY(7);
	}
	
	public GameMap GetGameMap()
	{
		return map;
	}
	
	public Hero GetHero()
	{
		return hero;
	}
	
	public void SetGameMap(GameMap map)
	{
		this.map = map;
	}
	
	public int MoveHero(int x, int y)
	{
		if (x == -1 && y == 1 && level == 2)
		{
			SetGameMap(map.NextMap());
			return 0;
		}
		
		if(!map.MoveTo(x, y))
			return 0;
		
		switch(level)
		{
		case 1:
			MoveGuard();
			break;
		case 2:
			MoveOgresAndClubs();
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
				InitLevel2();
				break;
			}
		}
		
		return make_play_value;
	}
	
	public void MoveGuard()
	{
		map.SetCellAt(guard.GetX(), guard.GetY(), (char)0);
		guard.Move();
		map.SetCellAt(guard.GetX(), guard.GetY(), guard.GetSymbol());
	}
	
	public void MoveOgresAndClubs()
	{
		for (Ogre ogre : ogres)
		{
			int ogre_x_pos = ogre.GetX();
			int ogre_y_pos = ogre.GetY();
			int club_x_pos = ogre.GetClubX();
			int club_y_pos = ogre.GetClubY();
			
			
			//reset to empty the cell in which the ogre was
			if (map.GetCellAt(ogre_x_pos, ogre_y_pos) == '$')
				map.SetCellAt(ogre_x_pos, ogre_y_pos, 'k');
			else
				map.SetCellAt(ogre_x_pos, ogre_y_pos, (char)0);
			
			//reset to empty the cell in which the club was
			if (map.GetCellAt(club_x_pos, club_y_pos) == '$')
				map.SetCellAt(club_x_pos, club_y_pos, 'k');
			else
				map.SetCellAt(club_x_pos, club_y_pos, (char)0);
	
			ogre.RefreshStun();
			if(ogre.GetSymbol() == 'O')
				while (!TryOgreNextPos(ogre));
			while (!TryClubNextPos(ogre));
			
	
			ogre_x_pos = ogre.GetX();
			ogre_y_pos = ogre.GetY();
			club_x_pos = ogre.GetClubX();
			club_y_pos = ogre.GetClubY();
			
			//Changes ogre char if he is in the key position
			if (map.GetCellAt(ogre_x_pos, ogre_y_pos) == 'k')
				ogre.SetSymbol('$');
			else
				ogre.SetSymbol('O');
			//Changes club char if he is in the key position
			if (map.GetCellAt(club_x_pos, club_y_pos) == 'k')
				ogre.SetClubSymbol('$');
			else
				ogre.SetClubSymbol('*');
	
			//Puts the new ogre cell with the char representing it
			map.SetCellAt(ogre_x_pos, ogre_y_pos, ogre.GetSymbol());
			map.SetCellAt(club_x_pos, club_y_pos, ogre.GetClubSymbol());
		}
		
	}
	
	public boolean TryOgreNextPos(Ogre ogre)
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		int temp_x = ogre.GetX();
		int temp_y = ogre.GetY();

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

		ogre.SetX(temp_x);
		ogre.SetY(temp_y);
		
		return true;
	}
	
	//TODO colocar no ogre
	public boolean TryClubNextPos(Ogre ogre)
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		temp_x = ogre.GetX();
		temp_y = ogre.GetY();
		
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
		
		ogre.SetClubX(temp_x);
		ogre.SetClubY(temp_y);
		
		return true;
	}
	
	public int MakePlay(int x, int y)
	{
		map.SetCellAt(hero.GetX(), hero.GetY(), (char)0);
		
		hero.SetX(x);
		hero.SetY(y);
		
		char dst_state = map.GetCellAt(x,y);
		map.SetCellAt(x, y, hero.GetSymbol());
		
		TryStunOgres();
		
		if (WasCaught())
			return -1;
		else if (dst_state == 0)
			return 0;
		else if (dst_state == 'k')
		{
			switch(level)
			{
			case 1:
				OpenDoors();
				break;
			case 2:
				hero.SetSymbol('K');
				map.SetCellAt(x, y, 'K');
				break;
			}
			return 0;
		}
		else if (dst_state == 'S')
			return 1; //nivel 2

		return 0;
	}
	
	public void TryStunOgres() 
	{
		int player_x_pos = hero.GetX();
		int player_y_pos = hero.GetY();
		int mob_x_pos;
		int mob_y_pos;
		
		for(Ogre ogre: ogres)
		{
			mob_x_pos = ogre.GetX();
			mob_y_pos = ogre.GetY();
			
			if (player_x_pos == (mob_x_pos - 1) && player_y_pos == mob_y_pos)
				ogre.Stun();
			else if (player_x_pos == (mob_x_pos + 1) && player_y_pos == mob_y_pos)
				ogre.Stun();
			else if (player_x_pos == mob_x_pos && player_y_pos == (mob_y_pos - 1))
				ogre.Stun();
			else if (player_x_pos == mob_x_pos && player_y_pos == (mob_y_pos + 1))
				ogre.Stun();
		}
		
	}

	public void OpenDoors()
	{
		map.SetCellAt(0, 5, 'S');
		map.SetCellAt(0, 6, 'S');
	}

	public boolean WasCaught()
	{
		switch (level)
		{
		case 1:
			return WasCaughtByMob(guard);
		case 2:
			for (Ogre ogre : ogres)
			{
				if (WasCaughtByMob(ogre) || WasCaughtByClub(ogre))
					return true;
			}
			return false;
		}
		
		return false;
	}
	
	public boolean WasCaughtByMob(GameCreature mob)
	{
		int player_x_pos = hero.GetX();
		int player_y_pos = hero.GetY();
		int mob_x_pos = mob.GetX();
		int mob_y_pos = mob.GetY();
		
		if (mob.GetSymbol() == 'g' || mob.GetSymbol() == '8')
			return false;
		
		if (player_x_pos == (mob_x_pos - 1) && player_y_pos == mob_y_pos)
			return true;
		else if (player_x_pos == (mob_x_pos + 1) && player_y_pos == mob_y_pos)
			return true;
		else if (player_x_pos == mob_x_pos && player_y_pos == (mob_y_pos - 1))
			return true;
		else if (player_x_pos == mob_x_pos && player_y_pos == (mob_y_pos + 1))
			return true;
		else
			return false;
	}
	
	public boolean WasCaughtByClub(Ogre ogre)
	{
		int player_x_pos = hero.GetX();
		int player_y_pos = hero.GetY();
		int club_x_pos = ogre.GetClubX();
		int club_y_pos = ogre.GetClubY();
		
		if (player_x_pos == club_x_pos && player_y_pos == club_y_pos)
			return true;
		else if (player_x_pos == (club_x_pos - 1) && player_y_pos == club_y_pos)
			return true;
		else if (player_x_pos == (club_x_pos + 1) && player_y_pos == club_y_pos)
			return true;
		else if (player_x_pos == club_x_pos && player_y_pos == (club_y_pos - 1))
			return true;
		else if (player_x_pos == club_x_pos && player_y_pos == (club_y_pos + 1))
			return true;
		else
			return false;
	}
	
	public boolean IsEndOfGame()
	{
		return map == null;
	}

}
