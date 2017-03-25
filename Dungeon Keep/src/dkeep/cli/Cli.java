package dkeep.cli;
import java.util.Scanner;
import dkeep.logic.*;


public class Cli
{
	private static Game game;
	private static char curr_map[][];

	public static void main(String[] args)
	{
		String direction;
		int move_hero_value;
		game = new Game(null, 0);
		curr_map = game.GetGameMap();
		DrawBoard(curr_map);

		while (true)
		{
			direction = WaitForPlay();
			if (direction == null)
				continue;

			move_hero_value = game.MoveHero(direction);

			if (ComputeMoveHeroValue(move_hero_value) == -1)
				return;
			
			if (game.IsEndOfGame())
			{
				EndOfGame();
				return;
			}
			
			curr_map = game.GetGameMap();
			DrawBoard(curr_map);
		}
	}

	private static void EndOfGame() 
	{
		System.out.println();
		System.out.println("END OF GAME");
		System.out.println("YOU WIN");
		System.out.println();
	}

	private static int ComputeMoveHeroValue(int move_hero_value)
	{
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
			return -1;
		}
		
		return 0;
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
