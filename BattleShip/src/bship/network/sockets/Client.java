//Client.java
//� Usman Saleem, 2002 and Beyond.
//usman_saleem@yahoo.com

package bship.network.sockets;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import bship.network.data.BattleShipData;
import bship.network.data.GameData;
import bship.network.data.LobbyInfoData;


public class Client extends Observable implements Runnable {

	/**
	* Uses to connect to the server
	*/
	private Socket socket;

	/**
	* For reading input from server.
	*/
	private ObjectInputStream socket_input;

	/**
	* For writing output to server.
	*/
	private ObjectOutputStream socket_output;

	/**
	* Status of client.
	*/
	private boolean connected;

	/**
	* Port number of server
	*/
	private int port = 5555; //default port

							 /**
							 * Host Name or IP address in String form
							 */
	private String hostName;
	
	private static Client instance;
	
	private Observer currObserver;
	
	public static Client getInstance() throws IOException
	{
		if (instance == null)
			instance = new Client();
		
		return instance;
	}

	public Client() throws IOException
	{
		connected = false;
		connect("dservers.ddns.net", 5555);
		currObserver = null;
	}
	
	public static void cleanInstance() 
	{
		if(instance != null)
			instance.disconnect();
		instance = null;
	}
	
	//Constructor used for tests
	public Client(int unused)
	{
		connected = true;
	}
	
	public void refreshObserver(Observer observer)
	{
		if (currObserver != null)
			deleteObserver(currObserver);
		currObserver = observer;
		addObserver(observer);
	}
	
	public void connect(String hostName, int port) throws IOException {
		if (!connected)
		{
			this.hostName = hostName;
			this.port = port;
			socket = new Socket(hostName, port);
			//get I/O from socket
			this.socket_output = new ObjectOutputStream(socket.getOutputStream());
			this.socket_input = new ObjectInputStream(socket.getInputStream());

			connected = true;
			//initiate reading from server...
			Thread t = new Thread(this);
			t.start(); //will call run method of this class
		}
	}

	public void sendBattleShipData(BattleShipData data) throws IOException
	{
		sendBattleShipData(data, false);
	}
	
	public void sendBattleShipData(BattleShipData data, boolean closeSocketAtTheEnd) throws IOException
	{
		if (connected)
		{
			DataSender sender = new DataSender(data, socket_output, closeSocketAtTheEnd);
			Thread t = new Thread(sender);
			t.start();
		}
	}

	public void disconnect() 
	{
		if (socket != null && connected)
		{
			try
			{
				socket.shutdownOutput();
				socket.close();
			}
			catch (IOException ioe) 
			{
				//unable to close, nothing to do...
			}
			finally 
			{
				this.connected = false;
			}
		}
	}

	public void run() 
	{
		BattleShipData data;
		try {
			while (connected && (data = (BattleShipData)socket_input.readObject()) != null)
			{
				//notify observers//
				this.setChanged();
				//notify+send out recieved msg to Observers
				this.notifyObservers(data);
			}
		}
		catch (IOException | ClassNotFoundException ioe){}
		finally { connected = false; }
	}

	public boolean isConnected() 
	{
		return connected;
	}


	public int getPort() {
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getHostName() 
	{
		return hostName;
	}

	public void setHostName(String hostName) 
	{
		this.hostName = hostName;
	}

	//testing Client//
	public static void main(String[] argv)throws IOException
	{
		String msg = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Client c = new Client();
		while (!msg.equalsIgnoreCase("quit"))
		{
			msg = br.readLine();
			BattleShipData data = new GameData();
			c.sendBattleShipData(data);
		}
		c.disconnect();
	}
}