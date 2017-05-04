package bship.network.data;

public class LoginResponseData extends LoginData
{
	boolean succeed;
	public LoginResponseData(boolean succeed)
	{
		this.succeed = succeed;
	}
}
