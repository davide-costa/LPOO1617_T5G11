package dkeep.logic;


public class Ogre extends GameCreature
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6436743285118414030L;
	private static char default_symbol = 'O';
	private static char stunned_symbol = '8';
	private static char owns_key_symbol = '$';
	private Coords club_coords;
	private char club_symbol = '*';
	private int stun_count;
	private boolean is_stunned;
	
	public Ogre(int x, int y, int club_x, int club_y)
	{
		super(x, y, default_symbol);
		club_coords = new Coords(club_x, club_y);
		is_stunned = false;
	}
	
	public Ogre(Coords coords, GameMap map)
	{
		super(coords, default_symbol);
		club_coords = new Coords(coords);
		while (!TryClubNextPos(map));
		is_stunned = false;
	}
	//TODO: consider removing this
	/*
	public int GetClubX()
	{
		return club_coords.GetX();
	}
	
	public void SetClubX(int club_x)
	{
		this.club_coords.SetX(club_x);
	}
	
	public int GetClubY()
	{
		return club_coords.GetY();
	}
	
	public void SetClubY(int club_y)
	{
		this.club_coords.SetY(club_y);
	}
	*/
	public Coords GetClubCoords()
	{
		return club_coords;
	}
	
	public void SetClubCoords(Coords coords)
	{
		club_coords.Set(coords);
	}
	
	public void SetClubCoords(int x, int y)
	{
		club_coords.Set(x, y);
	}
	
	public char GetClubSymbol()
	{
		return club_symbol;
	}
	
	public void SetClubSymbol(char club_symbol)
	{
		this.club_symbol = club_symbol;
	}
	
	public void Move(GameMap map)
	{		
		RefreshStun();
		if(GetSymbol() != '8')
			while (!TryOgreNextPos(map));
		while (!TryClubNextPos(map));
	}
	

	public boolean TryOgreNextPos(GameMap map)
	{
		Coords next_pos = GetCoords().randomAdjacentCell();

		if (!map.MoveTo(next_pos))
			return false;

		SetCoords(next_pos);
		
		return true;
	}
	
	public boolean TryClubNextPos(GameMap map)
	{
		Coords next_pos = GetCoords().randomAdjacentCell();
		
		if (!map.MoveTo(next_pos))
			return false;
		
		SetClubCoords(next_pos);
		
		return true;
	}
		
	public void Stun()
	{
		this.is_stunned = true;
		this.symbol = stunned_symbol;
		this.stun_count = 0;
	}
	
	public void RefreshStun()
	{
		if(!is_stunned)
			return;

		if(stun_count == 2)
		{
			is_stunned = false;
			symbol = default_symbol;
		}
		
		this.stun_count++;
	}
	
	public void SetOwnsKey()
	{
		symbol = owns_key_symbol;
	}
	
	public void SetNotOwnsKey()
	{
		if (is_stunned)
			symbol = stunned_symbol;
		else
			symbol = default_symbol;
	}
	
	
}
