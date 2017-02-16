import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class main
{
	private static char map2[][] = {
			{'X','X','X','X','X','X','X','X','X'}, 
			{'I',0,0,0,'O',0,0,'k','X'},
			{'X',0,0,0,0,0,0,0,'X'},
			{'X',0,0,0,0,0,0,0,'X'},
			{'X',0,0,0,0,0,0,0,'X'},
			{'X',0,0,0,0,0,0,0,'X'},
			{'X',0,0,0,0,0,0,0,'X'}, 
			{'X','H',0,0,0,0,0,0,'X'}, 
			{'X','X','X','X','X','X','X','X','X'}
			};
	
	private static char map1[][] = {
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
	
	private static int ogre_x_pos = 4;
	private static int ogre_y_pos = 1;
	private static char ogre_char = 'O'; //BY DEFAULT ITS AN 'O'
	
	
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
				Move_guard();
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
				guard_movement_step++;
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
		if(map1[dst_y][dst_x] == 'X' || map1[dst_y][dst_x] == 'I')
			return false;
		else
			return true;
	}
	
	public static int MakePlay()
	{
		map1[player_y_pos][player_x_pos] = 0;
		
		player_x_pos = dst_x;
		player_y_pos = dst_y;
		
		char last_state = map1[player_y_pos][player_x_pos];
		map1[player_y_pos][player_x_pos] = 'H';

		if(WasCaugthByGuard())
			return -1;
		else if(last_state == 0)
			return 0;
		else if(last_state == 'k')
			{
			OpenDoors();
			return 0;
			}
		else if(map1[player_y_pos][player_x_pos] == 'S')
			return 1;	
		
		return 0;
	}
	
	
	public static void OpenDoors()
	{
		map1[5][0] = 'S';
		map1[6][0] = 'S';
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
		map1[guard_y_pos][guard_x_pos] = 0;
		
		if(guard_movement_step == 24)
			guard_movement_step = 0;
		
		if(guard_movement_step == 0 || (guard_movement_step >= 5 && guard_movement_step < 11)) //for left moves
			guard_x_pos--;
		else if(guard_movement_step >= 12 && guard_movement_step <= 18) //for right moves
			guard_x_pos++;
		else if(guard_movement_step >= 19 && guard_movement_step <= 23) //for up moves
			guard_y_pos--;
		else if( (guard_movement_step >= 1 && guard_movement_step <= 4) || guard_movement_step == 11) //for down moves
			guard_y_pos++;
		
		map1[guard_y_pos][guard_x_pos] = 'G';
	}
	
	public static void Move_Ogre() 
	{
		//reset to empty the cell in which the ogre was
		map2[ogre_y_pos][ogre_x_pos] = 0;
		
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		int min = 1;
		int max = 4;
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		
		if(randomNum == 1)
			ogre_x_pos++; //ogre moves to right
		else if(randomNum == 2)
			ogre_x_pos--; //ogre moves to left
		else if(randomNum == 3)
			ogre_y_pos--; //ogre moves up
		else if(randomNum == 4)
			ogre_y_pos--; //ogre moves to down
		
		
		//Changes ogre char if he is in the key position
		if(map2[ogre_y_pos][ogre_x_pos] == 'k') 
			ogre_char = '$';
		else
			ogre_char = 'O';
			
		
		//Puts the new ogre cell with the char representing it
		map2[ogre_y_pos][ogre_x_pos] = ogre_char;
	}
	
	public static void DrawBoard() 
	{
		for(int i = 0; i < 10; i++)
		{
		for(int j = 0; j < 10; j++)
			{
			System.out.print(map1[i][j]);
			System.out.print(" ");
			}
		System.out.println();
		}
	}


	
	
	
//		for(int i = 0; i < 10; i++)
//		{
//			map1[i][0] = 'X';
//			map1[i][9] = 'X';
//		}
//		
//		for(int j = 1; j < 9; j++)
//		{
//		map1[0][j] = 'X';
//		map1[9][j] = 'X';
//		}
//		
//		
//		
//		
//		map1[1][4] = 'I';
//		map1[3][2] = 'I';
//		map1[3][4] = 'I';
//		map1[5][0] = 'I';
//		map1[6][0] = 'I';
//		map1[8][2] = 'I';
//		map1[8][4] = 'I';
//		

	}
