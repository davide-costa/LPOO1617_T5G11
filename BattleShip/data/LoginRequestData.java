package bship.network.data;

import java.io.Serializable;

public class LoginRequestData extends LoginData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5676866486804632232L;
	private String username;
	private String password;
	
	public LoginRequestData(String username, String password)
	{
		this.username = username;
		this.password = password;
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
