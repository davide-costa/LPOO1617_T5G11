package dkeep.logic;

import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.test.TestGuard;

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
	private boolean game_over = false;
	
	
	/**  
	 * Constructor of game class for the Gui interface.  
     * @param guard_name The name of the guard for that game instance, chosen by the user. Can either be "Rookie", "Drunken" or "Suspicious". Any other cause the exception IllegalArgumentException to be thrown.
	 * @param num_of_ogres The number of ogres for that game instance, chosen by the user. Can be between 0 and 5. Any other cause the exception IllegalArgumentException to be thrown.
	 */  
	public Game(String guard_name, int num_of_ogres)
	{
		if (num_of_ogres < 0 || num_of_ogres > 5)
			throw new IllegalArgumentException();
		this.num_of_ogres = num_of_ogres;
		InitLevel1(guard_name);
	}
	
	/**  
	 * Constructor of game class used for testing the Dungeon Map logic tests.
     * @param map The the map in which the tests will occur
	 */
	public Game(GameMap map)
	{
		SetGameMap(map);
		hero = new Hero(1, 1);
		this.level = 1;
		guard = new TestGuard(3, 1);
		RefreshMap();
	}
	
	/**  
	 * Constructor of game class used in Keep Map logic tests.
     * @param map The map in which the tests will occur
     * @param ogre The Ogre object used in the tests.
	 */
	public Game(GameMap map, Ogre ogre)
	{
		SetGameMap(map);
		hero = new Hero(1, 1);
		this.level = 2;
		ogres = new ArrayList<Ogre>();
		ogres.add(ogre);
		RefreshMap();
	}
	
	/**  
	 * Allows to get the state of a cell given its coords.
     * @param coords The coords of which to return the state
     * @return the state of the cell.
	 */
	public char GetCellState(Coords coords)
	{
		return map_matrix[coords.GetY()][coords.GetX()];
	}
	
	private void SetCellState(Coords coords, char symbol)
	{
		map_matrix[coords.GetY()][coords.GetX()] = symbol;
	}
	
	/**  
	 * Allows to get the level in which the game is.
	 * @return the level in which the game is.
	 */
	public int GetLevel()
	{
		return level;
	}
	
	/**  
	 * Allows to get the list of ogres (the mobs used in level 2) of the game.
	 * @return the list of ogres of a game.
	 */
	public List<Ogre> GetOgres()
	{
		return ogres;
	}
	
	/**  
	 * Allows to get the guard (the mobs used in level 1) of the game.
	 * @return the guard of the game.
	 */
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
				SetCellState(ogre.GetClubCoords(), ogre.GetClubSymbol());
			for (Ogre ogre : ogres)
				SetCellState(ogre.GetCoords(), ogre.GetSymbol());
			
			break;
		}
	}
	
	private void InitLevel1(String guard_name)
	{
		level = 1;
		
		ComputeGuard(guard_name);
		
		hero = new Hero(0, 0); //hero initialization (this coords are unused, they'll be set properly in the next function call)
		SetGameMap(new DungeonMap());
		ComputeHero();

		RefreshMap();
	}
	
	private void ComputeGuard(String guard_name)
	{
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
				throw new IllegalArgumentException();
		}
		if(guard_num == 1)
			guard = new Rookie(8, 1);
		else if(guard_num == 2)
			guard = new Drunken(8, 1);
		else
			guard = new Suspicious(8, 1);
	}
	
	private void InitLevel2()
	{
		TryLoadKeepMapFromFile();
	
		ogres = new ArrayList<Ogre>();
		ComputeOgres();
		
		level = 2;
		
		ComputeHero();
	
		RefreshMap();
	}
	
	private void ComputeHero() 
	{
		if(map.GetInitHeroCoords() == null)
			hero.SetCoords(new Coords(1,7));
		else
			hero.SetCoords(map.GetInitHeroCoords());
	}

	private void ComputeOgres() 
	{
		Ogre curr_ogre;
		ArrayList<Coords> mobs_coords = map.GetInitMobsCoords();
		Coords ogre_spawn_coords = new Coords(1, 1);
		Coords club_spawn_coords = new Coords(1, 2);
		
		if(mobs_coords == null)
		{			
			if (num_of_ogres == 0)
				num_of_ogres = ThreadLocalRandom.current().nextInt(1, 3 + 1);

			for (int i = 0; i < num_of_ogres; i++)
			{
				curr_ogre = new Ogre(ogre_spawn_coords, club_spawn_coords);
				ogres.add(curr_ogre);
				ogre_spawn_coords = ogre_spawn_coords.right();
				club_spawn_coords = club_spawn_coords.right();
			}
		}
		else
		{
			for (int i = 0; i < mobs_coords.size(); i++)
			{
				curr_ogre = new Ogre(mobs_coords.get(i), map);
				ogres.add(curr_ogre);
			}
		}
	}

	private void TryLoadKeepMapFromFile() 
	{
		try
		{
			FileInputStream fileIn = new FileInputStream("KeepMap");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (KeepMap) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (ClassNotFoundException | IOException e)
		{
			//If the file doesn'tdoesn't exist, the map will be generated automatically using default vajes
			return;
		}
	}
	/**
	 * Returns the current map used in the game in the form of a bidimensional array.
	 * @return a bidimensional array that represents the current map.
	 */
	public char[][] GetGameMap()
	{
		return map_matrix;
	}
	/**
	 * Returns the current object used in the game to represent the game map.
	 * @return the current Map object.
	 */
	public GameMap GetMap()
	{
		return map;
	}
	/**
	 * Returns the object Hero used in the game.
	 * @return the Hero object.
	 */
	public Hero GetHero()
	{
		return hero;
	}
	
	private void SetGameMap(GameMap map)
	{
		this.map = map;
	}
	/**
	 * Function that establishes the interface between Cli and Gui classes on each game iteration (i.e. play).
	 * The Cli and Gui read the input from user and call this function to make a new game play.
	 * Returns a value that informs of what hapenned in the game.
	 * @param direction A string containing the direction in which the hero should move. It should be "left", "right", "up" or "down". (Any other value has no effect and is ignored by the Game class)
	 * @return Returns -1 if the Hero was caught; 1 if he went to the next level; 0 on any other situation (no action is performed by Gui or Cli in this case (no action necessary).
	 */
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
		if (level == 2 && HeroLeftKeep(dst_coords))
		{
			SetGameMap(map.NextMap());
			return 0;
		}
		
		if(!map.MoveTo(dst_coords) && !HeroIsLeaving(dst_coords))
			return 0;
		
		MoveMobsOnCurrLevel();

		make_play_value = MakePlay(dst_coords);
		if (make_play_value == 1)
			NextLevel();
		
		RefreshMap();
		return make_play_value;
	}
	
	private void MoveMobsOnCurrLevel()
	{
		switch(level)
		{
		case 1:
			guard.Move();
			break;
		case 2:
			MoveOgresAndClubs();
			break;
		}
	}
	
	private void NextLevel()
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
			if (level == 2)
				hero.SetSymbol('K');
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
		boolean adjacent = CellsAreAdjacent(hero.GetCoords(), ogre.GetClubCoords());
		boolean on_top_of = hero.GetCoords().equals(ogre.GetClubCoords());
		
		return adjacent || on_top_of;
	}
	
	private boolean HeroIsLeaving(Coords dst_coords)
	{
		for(Coords coord: map.GetDoorsCoords())
		{
			if(coord.equals(dst_coords) && map.IsDoorOpen())
				return true;
		}
		
		return false;
	}
	
	private boolean HeroLeftKeep(Coords dst_coords)
	{
		if(!HeroIsLeaving(hero.GetCoords()))//hero was leaving in the last play
			return false;
		
		int x_coord = dst_coords.GetX();
		int y_coord = dst_coords.GetY();
		if(x_coord == -1 || y_coord == -1 || x_coord == map.GetMapXSize() || y_coord == map.GetMapYSize())
			return true;
		
		return false;
	}
	
	private boolean CellsAreAdjacent(Coords cell1, Coords cell2)
	{
		return cell1.adjacentTo(cell2);
	}
	
	/**
	 * Function that evaluates the state of the game. Tells whether it has ended with victory or not. (Not ending with victory means not ending at all (still running), or ending with defeat).
	 * @return true if the game has ended with victory, false if not.
	 */
	public boolean IsEndOfGame()
	{
		return map == null;
	}
	
	/**
	 * Function that evaluates the state of the game. Tells whether it is over or not. The game is over when it ends with defeat or victory.
	 * @return true if the game is over, false if not.
	 */
	public boolean IsGameOver()
	{
		return map == null || game_over == true;
	}
}
