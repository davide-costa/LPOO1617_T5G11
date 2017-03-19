package dkeep.logic;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


import dkeep.test.TestGuard;
import dkeep.test.TestOgre;

public class Game implements Serializable
{
	private char map_matrix[][];
	private GameMap map;
	private int level;
	private Hero hero;
	private Guard guard;
	List<Ogre> ogres;
	int num_of_ogres;
	private int temp_x;
	private int temp_y;
	private boolean game_over = false;
	
	private boolean CellsAreAdjacent(Coords cell1, Coords cell2)
	{
		int cell1_x = cell1.GetX();
		int cell1_y = cell1.GetY();
		int cell2_x = cell2.GetX();
		int cell2_y = cell2.GetY();
		
		if (cell1_x == (cell2_x - 1) && cell1_y == cell2_y)
			return true;
		else if (cell1_x == (cell2_x + 1) && cell1_y == cell2_y)
			return true;
		else if (cell1_x == cell2_x && cell1_y == (cell2_y - 1))
			return true;
		else if (cell1_x == cell2_x && cell1_y == (cell2_y + 1))
			return true;
		else
			return false;
	}
	
	public Game(String guard_name, int num_of_ogres)
	{
		hero = new Hero(1,1);
		this.num_of_ogres = num_of_ogres;
		InitLevel1(guard_name);
		
		//debugging
//		SetGameMap(map.NextMap());
//		InitLevel2();
	}
	
	public Game(GameMap map)
	{
		SetGameMap(map);
		hero = new Hero(1,1);
		this.level = 1;
		guard = new TestGuard(3,1,'G');
		RefreshMap();
	}
	
	public Game(GameMap map, Ogre ogre)
	{
		SetGameMap(map);
		hero = new Hero(1,1);
		this.level = 2;
		ogres = new ArrayList<Ogre>();
		ogres.add(ogre);
		RefreshMap();
	}
	
	
	public char GetCellState(Coords coords)
	{
		return map_matrix[coords.GetY()][coords.GetX()];
	}
	
	public void SetCellState(Coords coords, char symbol)
	{
		map_matrix[coords.GetY()][coords.GetX()] = symbol;
	}
	
	public int GetLevel()
	{
		return level;
	}
	
	public List<Ogre> GetOgres()
	{
		return ogres;
	}
	
	public Guard GetGuard()
	{
		return guard;
	}
	
	public void RefreshMap()
	{
		map_matrix = map.GetMapCopy();
		SetCellState(hero.GetCoords(), hero.GetSymbol());
		
		switch (level)
		{
		case 1:
			SetCellState(guard.GetCoords(), guard.GetSymbol());
			break;
			
		case 2:
			for (Ogre ogre : ogres)
			{
				SetCellState(ogre.GetClubCoords(), ogre.GetClubSymbol());
				SetCellState(ogre.GetCoords(), ogre.GetSymbol());
			}
		}
	}
	
	public void InitLevel1(String guard_name)
	{
		level = 1;
		
		
		//Generate the type of guard for this game, if the user didn't specify the one he wants
		int guard_num;
		if (guard_name == null || guard_name == "")
		{
			int min = 1;
			int max = 3;
			guard_num = ThreadLocalRandom.current().nextInt(min, max + 1);
		}
		else
		{
			if(guard_name == "Rookie")
				guard_num = 1;
			else if(guard_name == "Drunken")
				guard_num = 2;
			else if(guard_name == "Suspicious")
				guard_num = 3;
			else
				guard_num = 1; //code never executed, just to avoid compilation error
		}
		
		if(guard_num == 1)
			guard = new Rookie(8,1,'G');
		else if(guard_num == 2)
			guard = new Drunken(8,1,'G');
		else
			guard = new Suspicious(8,1,'G');
		
		SetGameMap(new DungeonMap());
		RefreshMap();
	}
	
	public void InitLevel2()
	{
		int ogre_init_spawn_x = 1;
		int ogre_init_spawn_y = 1;
		int club_init_spawn_x = 1;
		int club_init_spawn_y = 2;
		
		ogres = new ArrayList<Ogre>();
		
		if (num_of_ogres == 0)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			
			Ogre curr_ogre;
			for (int i = 0; i < randomNum; i++)
			{
				curr_ogre = new Ogre(ogre_init_spawn_x + i, ogre_init_spawn_y, club_init_spawn_x + i, club_init_spawn_y);
				ogres.add(curr_ogre);
			}
		}
		
		level = 2;
		hero.SetCoords(1, 7);
		RefreshMap();
	}
	
	public char[][] GetGameMap()
	{
		return map_matrix;
	}
	
	public GameMap GetMap()
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
	
	public int MoveHero(String direction)
	{
		if (direction == null)
			return 0;
		
		Coords dst_coords = ComputeDestination(direction);
		if (dst_coords == null)
			return 0;
		
		if(IsGameOver())
			return 0;
		
		int make_play_value;
		if (dst_coords.equals(-1, 1) && level == 2)
		{
			SetGameMap(map.NextMap());
			return 0;
		}
		
		if(!map.MoveTo(dst_coords))
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

		make_play_value = MakePlay(dst_coords);
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
		
		RefreshMap();
		return make_play_value;
	}
	
	private Coords ComputeDestination(String direction)
	{
		int x = hero.GetX();
		int y = hero.GetY();
		switch (direction)
		{
		case "left":
			x--;
			break;
		case "right":
			x++;
			break;
		case "up":
			y--;
			break;
		case "down":
			y++;
			break;
		default:
			return null;
		}

		return new Coords(x, y);
	}
	
	public void MoveGuard()
	{
		SetCellState(guard.GetCoords(), (char)0);
		guard.Move();
		SetCellState(guard.GetCoords(), guard.GetSymbol());
	}
	
	public void MoveOgresAndClubs()
	{
		for (Ogre ogre : ogres)
		{
			Coords ogre_coords = ogre.GetCoords();
			Coords club_coords = ogre.GetClubCoords();
			
			ogre.Move(map);
			
			//Changes ogre char if he is in the key position
			if (map.GetCellState(ogre_coords) == 'k')
				ogre.SetOwnsKey();
			else
				ogre.SetNotOwnsKey();
			
			//Changes club char if he is in the key position
			if (map.GetCellState(club_coords) == 'k')
				ogre.SetClubSymbol('$');
			else
				ogre.SetClubSymbol('*');
		}
		
	}
	
	public int MakePlay(Coords coords)
	{
		hero.SetCoords(coords);
		
		char dst_state = GetCellState(coords);
		
		if (level == 2)
			TryStunOgres();
		
		if (WasCaught())
		{
			game_over = true;
			return -1;
		}	
		else if (dst_state == 0)
			return 0;
		else if (dst_state == 'k')
		{
			map.PickUpKey();
			map.OpenDoors();
			//TODO maybe remove this switch
			switch(level)
			{
			case 1:
				break;
			case 2:
				hero.SetSymbol('K');
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
		for(Ogre ogre: ogres)
		{
			if (CellsAreAdjacent(hero.GetCoords(), ogre.GetCoords()))
				ogre.Stun();
		}
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
		if (mob.GetSymbol() == 'g' || mob.GetSymbol() == '8')
			return false;
		
		return CellsAreAdjacent(hero.GetCoords(), mob.GetCoords());
	}
	
	public boolean WasCaughtByClub(Ogre ogre)
	{
		return CellsAreAdjacent(hero.GetCoords(), ogre.GetClubCoords());
	}
	
	public boolean IsEndOfGame()
	{
		return map == null;
	}

	public boolean IsGameOver()
	{
		return map == null || game_over == true;
	}
}
