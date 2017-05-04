//ClientThread.java
//© Usman Saleem, 2002 and Beyond
//usman_saleem@yahoo.com

package bship.network.sockets;

import java.net.Socket;
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
		//get I/O from socket
		try
		{
			socket_input = new ObjectInputStream(socket.getInputStream());

			socket_output = new ObjectOutputStream(socket.getOutputStream());
			running = true; //set status
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

	/**
	*Stops clients connection
	*/

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
		BattleShipData data; //will hold message sent from client

							 //socket_output.println("Welcome to Java based Server");


							 //start listening message from client//


		try
		{
			boolean connectionSuccessful;
			do
			{
				if ((data = (BattleShipData)socket_input.readObject()) != null && running)
					connectionSuccessful = battleShipServer.newPlayerConnected(this, data);
				else
					break;
			} while (!connectionSuccessful);

			while ((data = (BattleShipData)socket_input.readObject()) != null && running)
			{
				//provide your server's logic here//
				player.HandleReceivedData(data);
				//right now it is acting as an ECHO server//

				socket_output.writeObject(data); //response to client//
				System.out.println(((GameData)data).stuff);
			}
			running = false;
		}
		catch (IOException ioe)
		{
			running = false;
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//it's time to close the socket
		try
		{
			this.socket.close();
			System.out.println("Closing connection");
		}
		catch (IOException ioe) {}

		//notify the observers for cleanup etc.
		this.setChanged();              //inherit from Observable
		this.notifyObservers(this);     //inherit from Observable
	}

	public void sendData(BattleShipData data) throws IOException 
	{
		socket_output.writeObject(data);
	}
}