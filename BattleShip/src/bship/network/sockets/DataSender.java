package bship.network.sockets;

import java.io.IOException;
import java.io.ObjectOutputStream;

import bship.gui.BattleShipExceptionHandler;
import bship.network.data.BattleShipData;

public class DataSender implements Runnable
{
	private BattleShipData dataToSend;
	private ObjectOutputStream output;
	private boolean closeSocketAtTheEnd;
	
	public DataSender(BattleShipData dataToSend, ObjectOutputStream output, boolean closeSocketAtTheEnd)
	{
		this.dataToSend = dataToSend;
		this.output = output;
		this.closeSocketAtTheEnd = closeSocketAtTheEnd;
	}
	
	@Override
	public void run()
	{
		try
		{
			output.writeObject(dataToSend);
			if (closeSocketAtTheEnd)
				Client.cleanInstance();
		}
		catch (IOException e)
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
}
