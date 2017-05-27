package bship.network.data;

public class LoginResponseData extends LoginData
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2708819194232353251L;
	boolean succeeded;
	boolean newAccountCreated;
	
	public LoginResponseData(boolean succeeded)
	{
		this.succeeded = succeeded;
		this.newAccountCreated = false;
	}
	
	public boolean isSucceeded()
	{
		return succeeded;
	}
	
	public void setAccountAsNew()
	{
		newAccountCreated = true;
	}
	
	public boolean newAcoountCreated() 
	{
		return newAccountCreated;
	}
}
