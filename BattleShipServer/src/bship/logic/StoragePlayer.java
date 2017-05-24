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
}
