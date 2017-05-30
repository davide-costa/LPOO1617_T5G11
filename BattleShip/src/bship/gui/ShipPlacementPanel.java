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
	private JLabel gameMapArea;
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
		
	
		labelDestroyer = new DraggableJLabel(ImagesData.destroyerImage);
		labelDestroyer.setBounds(1100, 240, 120, 60);
		battleShipPlacementPanel.add(labelDestroyer);
		
		labelCruiser1 = new DraggableJLabel(ImagesData.cruiserImage);
		labelCruiser1.setBounds(1100, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser1);
		
		labelCruiser2 = new DraggableJLabel(ImagesData.cruiserImage);
		labelCruiser2.setBounds(1350, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser2);
		
		labelSubmarine = new DraggableJLabel(ImagesData.submarineImage);
		labelSubmarine.setBounds(1100, 500, 180, 60);
		battleShipPlacementPanel.add(labelSubmarine);
		
		labelBattleShip = new DraggableJLabel(ImagesData.battleShipImage);
		labelBattleShip.setBounds(1100, 630, 240, 60);
		battleShipPlacementPanel.add(labelBattleShip);
		
		labelCarrier = new DraggableJLabel(ImagesData.carrierImage);
		labelCarrier.setBounds(1100, 760, 300, 60);
		battleShipPlacementPanel.add(labelCarrier);
		
		
		gameMapArea = new JLabel(ImagesData.boardImage);
		gameMapArea.setBounds(400, 240, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
		
		
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
