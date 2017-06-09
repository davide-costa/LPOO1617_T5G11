package bship.network.sockets;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import bship.network.data.BattleShipData;
import bship.network.data.GameData;


public class Client extends Observable implements Runnable 
{
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
	
	public static void cleanInstance() 
	{
		if(instance != null)
			instance.disconnect();
		instance = null;
	}

	public Client() throws IOException
	{
		connected = false;
		connect("dservers.ddns.net", 5555);
		currObserver = null;
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
	
	public void connect(String hostName, int port) throws IOException 
	{
		if (!connected)
		{
			createSocket(hostName, port);
			getIOFromSocket(socket);

			connected = true;
			initiateThreadForServerReading();
		}
	}

	private void createSocket(String hostName, int port) throws UnknownHostException, IOException
	{
		this.hostName = hostName;
		this.port = port;
		socket = new Socket(hostName, port);
	}
	
	private void getIOFromSocket(Socket socket) throws IOException 
	{
		this.socket_output = new ObjectOutputStream(socket.getOutputStream());
		this.socket_input = new ObjectInputStream(socket.getInputStream());
	}
	
	private void initiateThreadForServerReading() 
	{
		Thread t = new Thread(this);
		t.start();
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
				this.setChanged();
				this.notifyObservers(data);
			}
		}
		catch (IOException | ClassNotFoundException ioe) {}
		finally { connected = false; }
	}

	public boolean isConnected() 
	{
		return connected;
	}

	public int getPort() 
	{
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
}