package bship.network.sockets;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class BattleShipServerLogin implements Observer
{
	public void requestLogin(String login, String password) throws IOException
	{
		Client clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		
	}
}
