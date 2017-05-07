package bship.network.data;

public class LoginResponseData extends LoginData
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2708819194232353251L;
	boolean succeeded;
	
	public LoginResponseData(boolean succeeded)
	{
		this.succeeded = succeeded;
	}
	public boolean isSucceeded()
	{
		return succeeded;
	}
}
