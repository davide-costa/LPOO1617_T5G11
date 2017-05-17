package bship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu {
	
	private JFrame frame;
	private JPanel menuPanel;
	
	public Menu(JFrame frame) 
	{
		this.frame = frame;
		menuPanel = new JPanel();
		frame.getContentPane().add(menuPanel, "MenuPanel");
		menuPanel.setLayout(null);

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
				BattleShipServerLogin serverLogin = new BattleShipServerLogin(frame, menuPanel);
			
			}
		});
		btnMultiplayerGame.setBounds(135, 90, 175, 23);
		menuPanel.add(btnMultiplayerGame);
	}
}
