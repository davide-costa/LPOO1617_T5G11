import java.util.Scanner;

public class main
{
	private static char board[][] = {
			{'X','X','X','X','X','X','X','X','X','X'}, 
			{'X','H',0,0,'I',0,'X',0,'G','X'},
			{'X','X','X',0,'X','X','X',0,0,'X'}, 
			{'X',0,'I',0,'I',0,'X',0,0,'X'}, 
			{'X','X','X',0,'X','X','X',0,0,'X'}, 
			{'I',0,0,0,0,0,0,0,0,'X'},
			{'I',0,0,0,0,0,0,0,0,'X'},
			{'X','X','X',0,'X','X','X','X',0,'X'}, 
			{'X',0,'I',0,'I',0,'X','k',0,'X'}, 
			{'X','X','X','X','X','X','X','X','X','X'}
			};
	
	private static int player_x_pos = 1 ;
	private static int player_y_pos = 1;
	
	private static int guard_x_pos = 8;
	private static int guard_y_pos = 1;
	private static int guard_movement_step = 0;
	
	private static int dst_x = 1;
	private static int dst_y = 1;
	
	public static void main(String[] args) 
	{
		char user_input;
		int make_play_value;
		while(true)
		{
			DrawBoard();
			user_input = WaitForPlay();
			ComputeDestination(user_input);
			
			if(IsDestinationValid())
			{
				make_play_value = MakePlay();
				//System.out.println(make_play_value);
				
				if ( make_play_value == 1)
				{
					System.out.println();
					System.out.println("END OF GAME");
					System.out.println("YOU WIN");
					System.out.println();
					return;
				}
				else if(make_play_value == -1)
				{
					System.out.println();
					System.out.println("END OF GAME");
					System.out.println("YOU LOSE");
					System.out.println();
					return;
				}
				Move_guard();
			}
			
			
		}
	}
	
	public static char WaitForPlay()
	{
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		char ch = input.charAt(0);
		return ch;
	}
	
	public static void ComputeDestination(char input)
	{
		dst_x = player_x_pos;
		dst_y = player_y_pos;
		
		if(input == 'w' || input == 'W')
			dst_y--;
		else if(input == 'a' || input == 'A')
			dst_x--;
		else if(input == 's' || input == 'S')
			dst_y++;
		else if(input == 'd' || input == 'D')
			dst_x++;
	}
	
	public static boolean IsDestinationValid()
	{
		if(board[dst_y][dst_x] == 'X' || board[dst_y][dst_x] == 'I')
			return false;
		else
			return true;
	}
	
	public static int MakePlay()
	{
		board[player_y_pos][player_x_pos] = 0;
		
		player_x_pos = dst_x;
		player_y_pos = dst_y;
		
		char last_state = board[player_y_pos][player_x_pos];
		board[player_y_pos][player_x_pos] = 'H';

		if(WasCaugthByGuard())
			return -1;
		else if(last_state == 0)
			return 0;
		else if(last_state == 'k')
			{
			OpenDoors();
			return 0;
			}
		else if(board[player_y_pos][player_x_pos] == 'S')
			return 1;	
		
		return 0;
	}
	
	
	public static void OpenDoors()
	{
		board[5][0] = 'S';
		board[6][0] = 'S';
	}
	
	public static boolean WasCaugthByGuard()
	{
		if (player_x_pos == (guard_x_pos - 1) && player_y_pos == guard_y_pos)
			return true;
		else if (player_x_pos == (guard_x_pos + 1) && player_y_pos == guard_y_pos)
			return true;
		else if (player_x_pos == guard_x_pos && player_y_pos == (guard_y_pos - 1))
			return true;
		else if (player_x_pos == guard_x_pos && player_y_pos == (guard_y_pos + 1))
			return true;
		else
			return false;
		
	}
	
	public static void Move_guard() 
	{
		if(guard_movement_step == 0 || guard_movement_step == 5 || guard_movement_step == 6 || guard_movement_step == 7
				|| guard_movement_step == 8 || guard_movement_step == 9 || guard_movement_step == 10 || guard_movement_step == 11)
			guard_x_pos--;
		if(guard_movement_step == 0 || guard_movement_step == 5 || guard_movement_step == 6 || guard_movement_step == 7
				|| guard_movement_step == 8 || guard_movement_step == 9 || guard_movement_step == 10 || guard_movement_step == 11)
			guard_x_pos--;
	}
	
	public static void DrawBoard() 
	{
		for(int i = 0; i < 10; i++)
		{
		for(int j = 0; j < 10; j++)
			{
			System.out.print(board[i][j]);
			System.out.print(" ");
			}
		System.out.println();
		}
	}


	
	
	
//		for(int i = 0; i < 10; i++)
//		{
//			board[i][0] = 'X';
//			board[i][9] = 'X';
//		}
//		
//		for(int j = 1; j < 9; j++)
//		{
//		board[0][j] = 'X';
//		board[9][j] = 'X';
//		}
//		
//		
//		
//		
//		board[1][4] = 'I';
//		board[3][2] = 'I';
//		board[3][4] = 'I';
//		board[5][0] = 'I';
//		board[6][0] = 'I';
//		board[8][2] = 'I';
//		board[8][4] = 'I';
//		

	}
