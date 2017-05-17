package bship.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BattleShipServerLogin implements KeyListener
{
	private JFrame frame;
	private JPanel menuPanel;
	private JPanel battleShipLoginPanel;
	
	public BattleShipServerLogin(JFrame frame, JPanel menuPanel)
	{
		this.frame = frame;
		this.menuPanel = menuPanel;
		battleShipLoginPanel = new JPanel();
		frame.getContentPane().add(battleShipLoginPanel, "BattleShip Login Panel");
		battleShipLoginPanel.setLayout(null);
		menuPanel.setVisible(false);
		battleShipLoginPanel.setVisible(true);
		
		JButton btnLobby = new JButton("LOBBY");
		btnLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Lobby lobby = new Lobby(frame, battleShipLoginPanel);
			}
		});
		btnLobby.setBounds(155, 188, 89, 23);
		battleShipLoginPanel.add(btnLobby);
		
		menuPanel.setVisible(false);
		battleShipLoginPanel.setVisible(true);
		battleShipLoginPanel.addKeyListener(this);
		System.out.println("battleShipLoginPanel");
		battleShipLoginPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("battleShipLoginPanel");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("battleShipLoginPanel");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("battleShipLoginPanel");
	}
}
