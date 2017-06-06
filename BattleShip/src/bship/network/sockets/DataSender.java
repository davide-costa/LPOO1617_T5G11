package bship.network.sockets;

import java.io.IOException;
import java.io.ObjectOutputStream;

import bship.gui.BattleShipExceptionHandler;
import bship.network.data.BattleShipData;

public class DataSender implements Runnable
{
	private BattleShipData dataToSend;
	private ObjectOutputStream output;
	
	public DataSender(BattleShipData dataToSend, ObjectOutputStream output)
	{
		this.dataToSend = dataToSend;
		this.output = output;
	}
	
	@Override
	public void run()
	{
		try
		{
			output.writeObject(dataToSend);
		}
		catch (IOException e)
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}

}
