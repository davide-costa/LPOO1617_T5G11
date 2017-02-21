package dkeep.logic;

public class Ogre extends GameCreature
{
	private int club_x_pos = 5;
	private int club_y_pos = 1;
	private char club_symbol = '*';
	public Ogre(int x, int y, char symbol)
	{
		super(x, y, 'O');
	}
}
