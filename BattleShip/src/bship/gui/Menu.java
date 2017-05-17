package bship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Menu implements KeyListener
{
	
	private JFrame frame;
	private JPanel menuPanel;
	
	public Menu(JFrame frame) 
	{
		this.frame = frame;
		menuPanel = new JPanel();
		frame.getContentPane().add(menuPanel, "MenuPanel");
		menuPanel.setLayout(null);
		menuPanel.addKeyListener(this);
		JButton btnChangeWindow = new JButton("Login Facebook");
		btnChangeWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("lanching chrome");
				FacebookLogin f = new FacebookLogin();

			}
		});
		btnChangeWindow.setBounds(294, 227, 130, 23);
		menuPanel.add(btnChangeWindow);

		JButton btnMultiplayerGame = new JButton("Multiplayer Game");
		btnMultiplayerGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				  try {
					   File file = new File("cartoon001" + ".wav");
					   Clip clip = AudioSystem.getClip();
					   clip.open(AudioSystem.getAudioInputStream(file));
					   clip.start();
					   
					  } catch (Exception e) {
					   System.err.println(e.getMessage());
					  }
			        
			         BattleShipServerLogin serverLogin = new BattleShipServerLogin(frame, menuPanel);
			
			}
		});
		btnMultiplayerGame.setBounds(135, 90, 175, 23);
		menuPanel.add(btnMultiplayerGame);
		
		menuPanel.requestFocusInWindow();
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
}
