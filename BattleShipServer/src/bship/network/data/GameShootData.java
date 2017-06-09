package bship.network.data;

public class GameShootData extends GameData
{
	private static final long serialVersionUID = 5229487827874000072L;
	private int x;
	private int y;
	
	public GameShootData(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
}
