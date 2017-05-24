package bship.logic;

import java.io.Serializable;

public class StoragePlayer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5480953916856501872L;
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
