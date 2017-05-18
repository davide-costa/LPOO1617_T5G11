package bship.network.sockets;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.network.data.BattleShipData;
import bship.network.data.LoginRequestData;

public class BattleShipServerLogin implements Observer
{
	
	public void requestLogin(String login, String password) throws IOException
	{
		Client clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
		BattleShipData loginRequest = new LoginRequestData(login, password);
		clientSocket.sendBattleShipData(loginRequest);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		
	}
}
