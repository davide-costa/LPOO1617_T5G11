package bship.gui;

import java.awt.Font;
import java.awt.Image;
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

import bship.network.sockets.SocketIntermediate;

public class MenuPanel extends JPanel implements KeyListener
{

	protected JFrame frame;
	protected JPanel lastPanel;
	protected JPanel currPanel;
	protected SocketIntermediate intermediate;
	private JButton btnMultiplayerGame;
	private JButton btnSingleplayerGame;
	private	JButton btnFcebookLogin;
	private Image backgroundImage;
	
	/**
	 * Create the panel.
	 */
	public MenuPanel(JFrame frame) 
	{
		try {
			backgroundImage = ImageIO.read(new File("menuBackground.jpg"));
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	
		this.frame = frame;
		
		JPanel panella = new JPanel();
		currPanel = panella;
		frame.getContentPane().add(panella);
		
		//JPanel panella = new JPanel();
		//getContentPane().add(panella, BorderLayout.NORTH);
		
		
		btnMultiplayerGame = new JButton("Multiplayer");
		btnMultiplayerGame.setFont(new Font("Comic Sans MS", Font.BOLD, 37));
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
			        
			         BattleShipServerLogin serverLogin = new BattleShipServerLogin(frame, currPanel, intermediate);
			
			}
		});
		btnMultiplayerGame.setBounds(597, 226, 630, 154);
		currPanel.add(btnMultiplayerGame);
		
		JButton btnFsddfs = new JButton("fsddfs");
		panella.add(btnFsddfs);
		currPanel.addKeyListener(this);
		currPanel.setBounds(0, 0, 1920, 1080);
		frame.setResizable(false);

	
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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
