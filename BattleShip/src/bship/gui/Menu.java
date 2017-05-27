package bship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Rectangle;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;


public class Menu extends BattleShipGui
{
	private JPanel menuPanel;
	private JButton btnMultiplayerGame;
	private JButton btnSingleplayerGame;
	private	JButton btnFcebookLogin;
	private Image backgroundImage;

	public Menu(JFrame frame) 
	{
		this.frame = frame;
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(0, 0, 1920, 1080);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton btnSingleplayer = new JButton("SinglePlayer");
		btnSingleplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnSingleplayer.setBounds(701, 236, 459, 70);
		menuPanel.add(btnSingleplayer);
		
		JButton btnMultiplayer = new JButton("MultiPlayer");
		btnMultiplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				BattleShipServerLogin serverLogin = new BattleShipServerLogin(frame, menuPanel, intermediate);
			}
		});
		btnMultiplayer.setBounds(701, 382, 459, 70);
		menuPanel.add(btnMultiplayer);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnExit.setBounds(701, 524, 459, 70);
		menuPanel.add(btnExit);
		
		JButton btnFacebookLogin = new JButton("Facebook Login");
		btnFacebookLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FacebookLogin f = new FacebookLogin();
			}
		});
		btnFacebookLogin.setBounds(192, 917, 123, 57);
		menuPanel.add(btnFacebookLogin);
		
		
	try {
			backgroundImage = ImageIO.read(new File("menuBackground.jpg"));
		} catch (IOException e1) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}

		menuPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		switch(event.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE:
			//TODO: launch are you sure you want t quit 
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Menu");
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Menu");
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		//g.drawOval(0, 0, 50, 500);
		g.drawImage(backgroundImage, 0, 0, 1920, 1080, this.frame);
	}
}
