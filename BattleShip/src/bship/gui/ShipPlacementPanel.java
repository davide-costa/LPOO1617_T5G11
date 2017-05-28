package bship.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bship.network.sockets.ShipPlacementIntermediate;

public class ShipPlacementPanel extends BattleShipGui
{
	private JPanel battleShipPlacementPanel;
	private JPanel gameMapArea;
	
	public ShipPlacementPanel(JFrame frame)
	{
		this.frame = frame;
		try 
		{
			this.intermediate = new ShipPlacementIntermediate(this);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
		
		battleShipPlacementPanel = new JPanel();
		battleShipPlacementPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipPlacementPanel);
		battleShipPlacementPanel.setLayout(null);
		
		gameMapArea = new JPanel();
		gameMapArea.setBounds(400, 200, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
