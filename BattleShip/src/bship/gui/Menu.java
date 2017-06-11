package bship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JLabel;


public class Menu extends BattleShipGui
{
	private JPanel menuPanel;
	private JButton btnMultiplayer;
	private JButton btnSingleplayer;
	private	JButton btnExit;
	private JLabel background;

	public Menu(JFrame frame, JPanel lastPanel) 
	{
		this.frame = frame;
		this.lastPanel = lastPanel;
		if(lastPanel != null)
			lastPanel.setVisible(false);
		
		createmenuPanel();
		createbtnSingleplayer();
		createbtnMultiplayer();
		createbtnExit();
		createbackground();

		menuPanel.setVisible(true);
		menuPanel.addKeyListener(this);
		menuPanel.requestFocusInWindow();
	}

	private void createmenuPanel() 
	{
		menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
	}

	private void createbtnSingleplayer() 
	{
		btnSingleplayer = new JButton("SinglePlayer");
		btnSingleplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnSingleplayer.setBounds(701, 236, 459, 70);
		menuPanel.add(btnSingleplayer);
	}

	private void createbtnMultiplayer() 
	{
		btnMultiplayer = new JButton("MultiPlayer");
		btnMultiplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new BattleShipServerLogin(frame, intermediate, menuPanel);
			}
		});
		btnMultiplayer.setBounds(701, 382, 459, 70);
		menuPanel.add(btnMultiplayer);
	}

	private void createbtnExit() 
	{
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new ExitPanel(frame, Menu.this.menuPanel);	
			}
		});
		btnExit.setBounds(701, 524, 459, 70);
		menuPanel.add(btnExit);
	}

	private void createbackground() 
	{
		background = new JLabel(ImagesData.menuBackgroundIcon);
		background.setBounds(0, 0, 1920, 1080);
		menuPanel.add(background);
	}

	@Override
	public void keyPressed(KeyEvent event) {}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
}
