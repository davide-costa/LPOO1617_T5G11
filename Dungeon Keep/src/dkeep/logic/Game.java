package dkeep.logic;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


import dkeep.test.TestGuard;
import dkeep.test.TestOgre;

public class Game implements Serializable
{
	private static final long serialVersionUID = -834698263061286937L;
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
		return cell1.adjacentTo(cell2);
	}
	
	/**  
	 * Constructor of game class for the Gui interface.  
     * @param guard_name, is the name of the guard for that game instance, chosen by the user
	 * @param num_of_ogres, is the number of ogres for that game instance, chosen by the user
	 */  
	public Game(String guard_name, int num_of_ogres)
	{
		this.num_of_ogres = num_of_ogres;
		InitLevel1(guard_name);
		
		//debugging
		SetGameMap(map.NextMap());
		InitLevel2();
	}
	
	/**  
	 * Constructor of game class used in dungeon map tests.
     * @param map is the map in which the tests will occur
	 */  
	public Game(GameMap map)
	{
		SetGameMap(map);
		hero = new Hero(1, 1);
		this.level = 1;
		guard = new TestGuard(3, 1);
		RefreshMap();
	}
	
	//used to test keep map
	public Game(GameMap map, Ogre ogre)
	{
		SetGameMap(map);
		hero = new Hero(1, 1);
		this.level = 2;
		ogres = new ArrayList<Ogre>();
		ogres.add(ogre);
		RefreshMap();
	}
	
	public char GetCellState(Coords coords)
	{
		return map_matrix[coords.GetY()][coords.GetX()];
	}
	
	private void SetCellState(Coords coords, char symbol)
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
	
	private void RefreshMap()
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
	
	private void InitLevel1(String guard_name)
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
			guard = new Rookie(8, 1);
		else if(guard_num == 2)
			guard = new Drunken(8, 1);
		else
			guard = new Suspicious(8, 1);
		
		SetGameMap(new DungeonMap());
		Coords hero_coords = map.GetHeroCoords();
		hero = new Hero(hero_coords.GetX(), hero_coords.GetY());
		RefreshMap();
	}
	
	private void InitLevel2()
	{
		int ogre_init_spawn_x = 1;
		int ogre_init_spawn_y = 1;
		int club_init_spawn_x = 1;
		int club_init_spawn_y = 2;
		
		ogres = new ArrayList<Ogre>();
		
		if (num_of_ogres == 0)
		{
			num_of_ogres = ThreadLocalRandom.current().nextInt(1, 3 + 1);
		}
		
		Ogre curr_ogre;
		for (int i = 0; i < num_of_ogres; i++)
		{
			curr_ogre = new Ogre(ogre_init_spawn_x + i, ogre_init_spawn_y, club_init_spawn_x + i, club_init_spawn_y);
			ogres.add(curr_ogre);
		}
		
		level = 2;
		hero.SetCoords(map.GetHeroCoords());
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
	
	private void SetGameMap(GameMap map)
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
			guard.Move();
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
		switch (direction)
		{
		case "left":
			return hero.GetCoords().left();
		case "right":
			return hero.GetCoords().right();
		case "up":
			return hero.GetCoords().up();
		case "down":
			return hero.GetCoords().down();
		default:
			return null;
		}
	}
		
	private void MoveOgresAndClubs()
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
	
	private int MakePlay(Coords coords)
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
			map.PickUpKey(hero.GetCoords());
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
	
	private void TryStunOgres() 
	{
		for(Ogre ogre: ogres)
		{
			if (CellsAreAdjacent(hero.GetCoords(), ogre.GetCoords()))
				ogre.Stun();
		}
	}

	private boolean WasCaught()
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
	
	private boolean WasCaughtByMob(GameCreature mob)
	{
		if (mob.GetSymbol() == 'g' || mob.GetSymbol() == '8')
			return false;
		
		return CellsAreAdjacent(hero.GetCoords(), mob.GetCoords());
	}
	
	private boolean WasCaughtByClub(Ogre ogre)
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
