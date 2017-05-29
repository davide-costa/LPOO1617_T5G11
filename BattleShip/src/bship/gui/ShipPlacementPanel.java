package bship.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bship.network.sockets.ShipPlacementIntermediate;

public class ShipPlacementPanel extends BattleShipGui
{
	private JPanel battleShipPlacementPanel;
	private JPanel gameMapArea;
	private JLabel labelCarrier;
	private JLabel labelBattleShip;
	private JLabel labelSubmarine;
	private JLabel labelCruiser1;
	private JLabel labelCruiser2;
	private JLabel labelDestroyer;
	
	public ShipPlacementPanel(JFrame frame, JPanel menuPanel)
	{
		this.frame = frame;
		this.lastPanel = menuPanel;
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
		gameMapArea.setBounds(400, 240, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
		
		labelCarrier = new JLabel(ImagesData.carrierImage);
		labelCarrier.setBounds(1100, 760, 300, 60);
		battleShipPlacementPanel.add(labelCarrier);
		
		labelBattleShip = new JLabel(ImagesData.battleShipImage);
		labelBattleShip.setBounds(1100, 630, 240, 60);
		battleShipPlacementPanel.add(labelBattleShip);
		
		labelSubmarine = new JLabel(ImagesData.submarineImage);
		labelSubmarine.setBounds(1100, 500, 180, 60);
		battleShipPlacementPanel.add(labelSubmarine);
		
		labelCruiser1 = new JLabel(ImagesData.cruiserImage);
		labelCruiser1.setBounds(1100, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser1);
		
		labelCruiser2 = new JLabel(ImagesData.cruiserImage);
		labelCruiser2.setBounds(1350, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser2);
		
		labelDestroyer = new JLabel(ImagesData.destroyerImage);
		labelDestroyer.setBounds(1100, 240, 120, 60);
		battleShipPlacementPanel.add(labelDestroyer);
		
		
		lastPanel.setVisible(false);
		battleShipPlacementPanel.setVisible(true);
		battleShipPlacementPanel.addKeyListener(this);
		battleShipPlacementPanel.requestFocusInWindow();
		System.out.println("battleShipPlacementPanel");
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			intermediate.closeConnection();
			new Menu(this.frame, this.battleShipPlacementPanel);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}

}
