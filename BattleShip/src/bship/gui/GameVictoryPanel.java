package bship.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bship.logic.ShipPlacement;

public class GameVictoryPanel extends BattleShipGui 
{
	public GameVictoryPanel(JFrame frame, JPanel lastPanel)
	{
		this.lastPanel = lastPanel;
		this.setBounds(0, 0, 1920, 1080);
		setLayout(null);
		
		JLabel backgroundLabel = new JLabel(ImagesData.gameVictoryIcon);
		backgroundLabel.setBounds(0, 0, 1920, 1080);
		add(backgroundLabel);
		
		JLabel facebookLogin = new JLabel(ImagesData.facebookShareIcon);
		facebookLogin.setBounds(1287, 835, 503, 196);
		add(facebookLogin);
		facebookLogin.addMouseListener(new MouseListener() 
		{
			@Override
			public void mouseClicked(MouseEvent event) {}

			@Override
			public void mouseEntered(MouseEvent event) {}

			@Override
			public void mouseExited(MouseEvent event) {}

			@Override
			public void mousePressed(MouseEvent event) 
			{
				String victoryMessage = "I win battleship! :)";
				FacebookLogin fbLogin = new FacebookLogin();
				fbLogin.post(victoryMessage);
			}

			@Override
			public void mouseReleased(MouseEvent event) {}
		});
		
		frame.getContentPane().add(this);
		lastPanel.setVisible(false);
		this.setVisible(true);
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			intermediate.closeConnection();
			new Menu(this.frame, this);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
