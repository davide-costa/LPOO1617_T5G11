package bship.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameVictoryPanel extends BattleShipGui 
{	
	private JLabel facebookLogin;
	private JLabel backgroundLabel;
	private JLabel postInfoLabel;
	
	public GameVictoryPanel(JFrame frame, JPanel lastPanel)
	{
		this.frame = frame;
		this.lastPanel = lastPanel;
		this.setBounds(0, 0, 1920, 1080);
		setLayout(null);
		
		createFacebookLogin();
		createPostInfoLabel();
		createbackgroundLabel();
		
		frame.getContentPane().add(this);
		lastPanel.setVisible(false);
		this.setVisible(true);
		addKeyListener(this);
		this.requestFocusInWindow(true);
	}
	
	private void createPostInfoLabel() 
	{
		postInfoLabel = new JLabel("");
		postInfoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		postInfoLabel.setForeground(Color.WHITE);
		postInfoLabel.setBounds(1287, 835, 600, 100);
		add(postInfoLabel);
	}

	private void createbackgroundLabel() 
	{
		backgroundLabel = new JLabel(ImagesData.gameVictoryIcon);
		backgroundLabel.setBounds(0, 0, 1920, 1080);
		add(backgroundLabel);
	}

	private void createFacebookLogin() 
	{
		facebookLogin = new JLabel(ImagesData.facebookShareIcon);
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
				GameVictoryPanel.this.remove(facebookLogin);
				String victoryMessage = "I " + CurrGameData.getAllyName() + " win battleship against " + CurrGameData.getOpponentName() + "! :)";
				FacebookLogin fbLogin = new FacebookLogin();
				fbLogin.post(victoryMessage);
				displayPostInfoMessage(fbLogin.getUsername());
				GameVictoryPanel.this.requestFocusInWindow(true);
			}

			@Override
			public void mouseReleased(MouseEvent event) {}
		});
		
	}

	private void displayPostInfoMessage(String facebookUsername) 
	{
		String message = "The victory has been posted as " + facebookUsername + "!";
		postInfoLabel.setText(message);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
			new Menu(this.frame, this);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
