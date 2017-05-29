package bship.network.sockets;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bship.gui.BattleShipServerLogin;
import bship.network.data.BattleShipData;
import bship.network.data.LoginRequestData;
import bship.network.data.LoginResponseData;

public class BattleShipServerLoginIntermediate extends SocketIntermediate implements Observer
{
	private BattleShipServerLogin gui;
	
	public void requestLogin(BattleShipServerLogin gui, String username, String password) throws IOException
	{
		this.gui = gui;
		Client.cleanInstance();
		Client clientSocket = Client.getInstance();
		clientSocket.refreshObserver(this);
		BattleShipData loginRequest = new LoginRequestData(username, password);
		clientSocket.sendBattleShipData(loginRequest);
		System.out.println("SEND REQUEST TO LOGIN");
	}

	@Override
	public void update(Observable clientSocket, Object object)
	{
		System.out.println("login");
		if (!(object instanceof LoginResponseData))
			return;
		
		LoginResponseData response = (LoginResponseData)object;
		//TODO: tirar este print
		System.out.println(response.isSucceeded());
		if(response.isSucceeded())
			gui.LoginResponse(response);
	}
}
