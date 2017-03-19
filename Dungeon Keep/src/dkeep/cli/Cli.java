package dkeep.cli;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import dkeep.logic.*;


public class Cli
{
	private Game game;

	public void main()
	{
		String direction;
		int move_hero_value;
		game = new Game(null, 0);
		char curr_map[][] = game.GetGameMap();
		DrawBoard(curr_map);

		while (true)
		{

			direction = WaitForPlay();
			if (direction == null)
				continue;

			move_hero_value = game.MoveHero(direction);

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

	public static String WaitForPlay()
	{
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		while (input.length() == 0)
			input = scan.nextLine();
		char ch = input.charAt(0);
		
		String direction;
		switch(ch)
		{
		case 'a':
			direction = "left";
			break;
		case 'd':
			direction = "right";
			break;
		case 'w':
			direction = "up";
			break;
		case 's':
			direction = "down";
			break;
		default:
			direction = null;
		}

		return direction;
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
