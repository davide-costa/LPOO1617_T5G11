package bship.gui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class FacebookLogin {

	public static final Version version = Version.VERSION_2_9;
	String domain = "http://google.com";
	String appId = "1286325254748169";
	String accessToken;
	String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId + "&redirect_uri=" + domain + "&scope=user_about_me,"
		+ "user_birthday,user_photos, ads_read,email,publish_actions";

	
	public FacebookLogin()
	{
		System.setProperty("webdirver.chrome.driver", "res/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get(authUrl);

		while (true)
		{
			if (!driver.getCurrentUrl().contains("facebook.com"))
			{
				String url = driver.getCurrentUrl();
				accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");

				driver.quit();

				FacebookClient fbClient = new DefaultFacebookClient(accessToken, version);
				
				//Post
				fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "Test post4"));
				
				//Get user info
				User user = (User)fbClient.fetchObject("me", User.class);
				System.out.println(user.getName());
				System.out.println(user.getId());
				System.out.println(user.getBirthday());
				return;
			}

		}


	}

}
