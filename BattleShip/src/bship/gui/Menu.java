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


public class Menu extends BattleShipGui implements KeyListener
{
	private JButton btnMultiplayerGame;
	private JButton btnSingleplayerGame;
	private	JButton btnFcebookLogin;
	private Image backgroundImage;

	public Menu(JFrame frame) 
	{
	try {
			backgroundImage = ImageIO.read(new File("menuBackground.jpg"));
		} catch (IOException e1) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	
		this.frame = frame;
		
		JPanel panella = new JPanel();
		currPanel = panella;
		getContentPane().add(panella);
		
		JButton btnFsddfs = new JButton("fsddfs");
		panella.add(btnFsddfs);
		currPanel.addKeyListener(this);
		currPanel.setBounds(0, 0, 1920, 1080);
		setResizable(false);
		
		//JPanel panella = new JPanel();
		//getContentPane().add(panella, BorderLayout.NORTH);
		
		
		btnMultiplayerGame = new JButton("Multiplayer");
		btnMultiplayerGame.setFont(new Font("Comic Sans MS", Font.BOLD, 37));
		btnMultiplayerGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					File file = new File("cartoon001" + ".wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();

				} 
				catch (Exception e) 
				{
					BattleShipExceptionHandler.handleBattleShipException();
				}

				BattleShipServerLogin serverLogin = new BattleShipServerLogin(frame, currPanel, intermediate);

			}
		});
		btnMultiplayerGame.setBounds(597, 226, 630, 154);
		currPanel.add(btnMultiplayerGame);

	
		btnFcebookLogin = new JButton("Login Facebook");
		btnFcebookLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("lanching chrome");
				FacebookLogin f = new FacebookLogin();

			}
		});
		btnFcebookLogin.setBounds(294, 227, 130, 23);
		currPanel.add(btnFcebookLogin);
		
		
		currPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		System.out.println("Menu");
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Menu");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Menu");
	}
	
	@Override
	public void paintComponents(Graphics graphics) 
	{
		super.paintComponents(graphics);
		graphics.drawImage(backgroundImage, 0, 0, this);
	}
}
