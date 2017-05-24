package bship.logic;

public class StoragePlayer
{
	private String username;
	private String password;
	
	public StoragePlayer(Player player)
	{
		username = player.getUsername();
		password = player.getPassword();
	}
	
	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
