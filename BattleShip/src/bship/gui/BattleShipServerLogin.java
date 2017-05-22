package bship.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bship.network.data.LoginResponseData;
import bship.network.sockets.BattleShipServerLoginIntermediate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class BattleShipServerLogin implements KeyListener
{
	private JFrame frame;
	private JPanel menuPanel;
	private JPanel battleShipLoginPanel;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private String username, password;
	
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
		btnLobby.setBounds(155, 242, 89, 23);
		battleShipLoginPanel.add(btnLobby);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(155, 80, 86, 20);
		battleShipLoginPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(73, 83, 72, 14);
		battleShipLoginPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 129, 72, 14);
		battleShipLoginPanel.add(lblPassword);
		
		passwordTextField = new JTextField();

		passwordTextField.setColumns(10);
		passwordTextField.setBounds(155, 126, 86, 20);
		battleShipLoginPanel.add(passwordTextField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				BattleShipServerLoginIntermediate login = new BattleShipServerLoginIntermediate();
				try 
				{
					username = usernameTextField.getText();
					password = passwordTextField.getText();
					login.requestLogin(BattleShipServerLogin.this, username, password);
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(155, 172, 89, 23);
		battleShipLoginPanel.add(btnLogin);
		
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

	public void LoginResponse(LoginResponseData response) 
	{
		if(response.isSucceeded())
			new Lobby(frame, battleShipLoginPanel);
		else
			//do stuff like abrir um dialogo a dizer que ta mal ou assim
			return;
		
	}
}
