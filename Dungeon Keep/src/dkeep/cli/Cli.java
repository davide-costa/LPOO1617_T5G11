package dkeep.cli;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import dkeep.logic.*;


public class Cli
{
	private Game game;

	public void main(String[] args)
	{
		char user_input;
		int make_play_value;
		game = new Game();
		game.SetGameMap(new DungeonMap());
		GameMap curr_map = game.GetGameMap();
		DrawBoard(curr_map);
		
		while (!game.IsEndOfGame())
		{
			curr_map = game.GetGameMap();
		
			user_input = WaitForPlay();
			
			ComputeDestination(user_input);
			
			game.MoveHero(dst_x, dst_y);
			//TODO isto devia estar antes maybe


			if (IsDestinationValid())
			{
				if (level == 10)
					Move_guard();
				else
					MoveOgreAndClub();
				make_play_value = MakePlay();

				if (make_play_value == 1)
				{
					System.out.println();
					System.out.println("NEXT LEVEL");
					System.out.println();
					curr_map = map2;
					curr_map_size = 9;
					mob_x_pos = 4;
					mob_y_pos = 1;
					player_x_pos = 1;
					player_y_pos = 7;
					while (!TryClubNextPos());
				}
				else if (make_play_value == -1)
				{
					System.out.println();
					System.out.println("END OF GAME");
					System.out.println("YOU LOSE");
					System.out.println();
					return;
				}
			}
			
			DrawBoard(curr_map);
		}
		
		System.out.println();
		System.out.println("END OF GAME");
		System.out.println("YOU WIN");
		System.out.println();
	}

	public static char WaitForPlay()
	{
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		while (input.length() == 0)
			input = scan.nextLine();
		char ch = input.charAt(0);

		return ch;
	}

	public void ComputeDestination(char input)
	{
		dst_x = game.GetHeroXPos();
		dst_y = game.GetHeroYPos();

		if (input == 'w' || input == 'W')
			dst_y--;
		else if (input == 'a' || input == 'A')
			dst_x--;
		else if (input == 's' || input == 'S')
			dst_y++;
		else if (input == 'd' || input == 'D')
			dst_x++;
	}
	
	public static boolean IsEndOfGame()
	{
		if(dst_x == -1)
			return true;
		else 
			return false;
	}

	public static boolean IsDestinationValid()
	{
		if (curr_map[dst_y][dst_x] == 'X' || (curr_map[dst_y][dst_x] == 'I' && player_char != 'K'))
			return false;
		else
			return true;
	}

	public static int MakePlay()
	{
		curr_map[player_y_pos][player_x_pos] = 0;

		player_x_pos = dst_x;
		player_y_pos = dst_y;

		char last_state = curr_map[player_y_pos][player_x_pos];
		curr_map[player_y_pos][player_x_pos] = player_char;

		if (WasCaugth())
			return -1;
		else if (last_state == 0)
			return 0;
		else if (last_state == 'k')
		{
			if (curr_map_size == 10)
				OpenDoors();
			else
			{
				player_char = 'K';
				curr_map[player_y_pos][player_x_pos] = player_char;
			}
			return 0;
		}
		else if (last_state == 'S')
			return 1; //nivel 2

		return 0;
	}


	public static void OpenDoors()
	{
		curr_map[5][0] = 'S';
		curr_map[6][0] = 'S';
	}

	public static boolean WasCaugth()
	{
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

	public static void Move_guard()
	{
		curr_map[mob_y_pos][mob_x_pos] = 0;

		if (guard_movement_step == 24)
			guard_movement_step = 0;

		if (guard_movement_step == 0 || (guard_movement_step >= 5 && guard_movement_step < 11)) //for left moves
			mob_x_pos--;
		else if (guard_movement_step >= 12 && guard_movement_step <= 18) //for right moves
			mob_x_pos++;
		else if (guard_movement_step >= 19 && guard_movement_step <= 23) //for up moves
			mob_y_pos--;
		else if ((guard_movement_step >= 1 && guard_movement_step <= 4) || guard_movement_step == 11) //for down moves
			mob_y_pos++;

		curr_map[mob_y_pos][mob_x_pos] = 'G';
		guard_movement_step++;
	}

	public static boolean TryOgreNextPos()
	{
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		int temp_x_pos = mob_x_pos;
		int temp_y_pos = mob_y_pos;

		if (randomNum == 1)
			mob_x_pos++; //ogre moves to right
		else if (randomNum == 2)
			mob_x_pos--; //ogre moves to left
		else if (randomNum == 3)
			mob_y_pos++; //ogre moves up
		else if (randomNum == 4)
			mob_y_pos--; //ogre moves to down

		if (map2[mob_y_pos][mob_x_pos] == 'X' || map2[mob_y_pos][mob_x_pos] == 'I')
		{
			mob_x_pos = temp_x_pos;
			mob_y_pos = temp_y_pos;
			return false;
		}

		return true;
	}
	
	public static void MoveOgreAndClub()
	{
		//reset to empty the cell in which the ogre was
		if (curr_map[mob_y_pos][mob_x_pos] == '$')
			curr_map[mob_y_pos][mob_x_pos] = 'k';
		else
			curr_map[mob_y_pos][mob_x_pos] = 0;
		
		//reset to empty the cell in which the club was
		if (curr_map[club_y_pos][club_x_pos] == '$')
			curr_map[club_y_pos][club_x_pos] = 'k';
		else
			curr_map[club_y_pos][club_x_pos] = 0;

		while (!TryOgreNextPos());
		while (!TryClubNextPos());

		//Changes ogre char if he is in the key position
		if (curr_map[mob_y_pos][mob_x_pos] == 'k')
			ogre_char = '$';
		else
			ogre_char = 'O';
		
		//Changes club char if he is in the key position
		if (curr_map[club_y_pos][club_x_pos] == 'k')
			club_char = '$';
		else
			club_char = '*';

		//Puts the new ogre cell with the char representing it
		curr_map[mob_y_pos][mob_x_pos] = ogre_char;
		curr_map[club_y_pos][club_x_pos] = club_char;
	}

	public static void DrawBoard(GameMap map)
	{
		char curr_map[][] = map.GetMap();
		int map_x_size = map.GetXSize();
		int map_y_size = map.GetYSize();
		for (int i = 0; i < map_y_size; i++)
		{
			for (int j = 0; j < map_x_size; j++)
			{
				System.out.print(curr_map[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
