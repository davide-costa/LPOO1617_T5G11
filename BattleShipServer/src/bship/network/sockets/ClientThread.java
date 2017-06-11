package bship.network.sockets;

import java.net.Socket;
import java.net.SocketException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Observable;

import bship.logic.BattleShipServer;
import bship.logic.Player;
import bship.network.data.*;

public class ClientThread extends Observable implements Runnable
{
	/** For reading input from socket */
	private ObjectInputStream socket_input;

	/** For writing output to socket. */
	private ObjectOutputStream socket_output;

	/** Socket object representing client connection */
	private Socket socket;
	
	private BattleShipServer battleShipServer;
	private boolean running;
	private Player player;

	public ClientThread(Socket socket, BattleShipServer battleShipServer) throws IOException {
		this.socket = socket;
		this.battleShipServer = battleShipServer;
		running = false;
		
		try
		{
			socket_input = new ObjectInputStream(socket.getInputStream());

			socket_output = new ObjectOutputStream(socket.getOutputStream());
			running = true;
		}
		catch (IOException ioe)
		{
			throw ioe;
		}
	}

	public Player getPlayer()
	{
		return player;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public void stopClient()
	{
		try
		{
			this.socket.close();
		}
		catch (IOException ioe) {};
	}

	public void run() 
	{
		BattleShipData data;
		try
		{
			boolean loginSucceeded;
			do
			{
				data = (BattleShipData)socket_input.readObject();
				if (!running)
					break;
				
				loginSucceeded = battleShipServer.newPlayerConnected(this, data);
			} while (!loginSucceeded);

			while ((data = (BattleShipData)socket_input.readObject()) != null && running)
			{
				player.HandleReceivedData(data);
			}
			running = false;
		}
		catch (IOException ioe)
		{
			running = false;
		}
		catch (ClassNotFoundException e) {}


		try
		{
			this.socket.close();
			System.out.println("Closing connection");
		}
		catch (IOException ioe) {}

		
		this.setChanged();             
		this.notifyObservers(this);    
	}

	public void sendData(BattleShipData data)
	{
		try
		{
			socket_output.writeObject(data);
		}
		catch (SocketException e)
		{
			if (e.getMessage() != "Socket closed")
				e.printStackTrace();
		}
		catch (IOException e) {}
	}
}