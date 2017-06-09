package bship.network.data;

public class LoginResponseData extends LoginData
{
	private static final long serialVersionUID = -2708819194232353251L;
	private boolean succeeded;
	private boolean newAccountCreated;
	
	public LoginResponseData() {}
	
	public boolean isSucceeded()
	{
		return succeeded;
	}
	
	public void setAccountAsNew()
	{
		newAccountCreated = true;
	}
	
	public void setAccountAsNotNew()
	{
		newAccountCreated = false;
	}
	
	public void setAsSuceeded()
	{
		succeeded = true;
	}
	
	public void setAsNotSuceeded()
	{
		succeeded = false;
	}
	
	public boolean newAcoountCreated() 
	{
		return newAccountCreated;
	}
}