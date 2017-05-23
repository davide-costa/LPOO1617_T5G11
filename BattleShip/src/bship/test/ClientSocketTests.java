package bship.test;

import java.io.IOException;

import bship.network.sockets.Client;

public class ClientSocketTests extends Client
{
	public ClientSocketTests() throws IOException
	{
		super("127.0.0.1");
	}
}
