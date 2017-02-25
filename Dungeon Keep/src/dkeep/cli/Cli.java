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
