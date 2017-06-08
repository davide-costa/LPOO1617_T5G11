package bship.gui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class FacebookLogin 
{
	private static final Version version = Version.VERSION_2_9;
	private String domain = "http://google.com";
	private String appId = "1286325254748169";
	private String accessToken;
	private String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId + "&redirect_uri=" + domain + "&scope=user_about_me,"
		+ "publish_actions";
	private WebDriver chromeURL;
	private FacebookClient fbClient;
	private User user;
	
	public FacebookLogin()
	{
		openChromeURL();
		getFacebookClient();
	}

	private void openChromeURL() 
	{
		System.setProperty("webdirver.chrome.driver", "chromedriver.exe");
		chromeURL = new ChromeDriver();
		chromeURL.get(authUrl);

	}
	
	private void getFacebookClient() 
	{
		while (true)
		{
			if (!chromeURL.getCurrentUrl().contains("facebook.com"))
			{
				String url = chromeURL.getCurrentUrl();
				accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
				chromeURL.quit();

				fbClient = new DefaultFacebookClient(accessToken, version);
				user = (User)fbClient.fetchObject("me", User.class);
				
				return;
			}
		}
	}
	
	public void post(String message)
	{
		try
		{
			fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
		}
		catch (FacebookException e)
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
	
	public String getUserName()
	{
		return user.getName();
	}
}