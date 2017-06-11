package bship.network.sockets;

public abstract class SocketIntermediate 
{
	public void closeConnection() 
	{
		Client.cleanInstance();
	}
}
