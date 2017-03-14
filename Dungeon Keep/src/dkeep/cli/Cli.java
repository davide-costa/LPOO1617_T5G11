package dkeep.cli;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import dkeep.logic.*;


public class Cli
{
	private Game game;
	private int dst_x = 0;
	private int dst_y = 0;

	public void main()
	{
		char user_input;
		int move_hero_value;
		game = new Game(null, 0);
		char curr_map[][] = game.GetGameMap();
		DrawBoard(curr_map);

		while (true)
		{

			user_input = WaitForPlay();

			ComputeDestination(user_input);

			move_hero_value = game.MoveHero(dst_x, dst_y);

			if (move_hero_value == 1)
			{
				System.out.println();
				System.out.println("NEXT LEVEL");
				System.out.println();
			}
			else if (move_hero_value == -1)
			{
				curr_map = game.GetGameMap();
				DrawBoard(curr_map);
				System.out.println();
				System.out.println("END OF GAME");
				System.out.println("YOU LOSE");
				System.out.println();
				return;
			}

			if (game.IsEndOfGame())
				break;
			
			curr_map = game.GetGameMap();
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
		Hero hero = game.GetHero();
		dst_x = hero.GetX();
		dst_y = hero.GetY();

		if (input == 'w' || input == 'W')
			dst_y--;
		else if (input == 'a' || input == 'A')
			dst_x--;
		else if (input == 's' || input == 'S')
			dst_y++;
		else if (input == 'd' || input == 'D')
			dst_x++;
	}


	public static void DrawBoard(char curr_map[][])
	{
		int map_x_size = curr_map[0].length;
		int map_y_size = curr_map.length;
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
