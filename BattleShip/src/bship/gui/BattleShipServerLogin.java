package bship.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bship.network.data.LoginResponseData;
import bship.network.sockets.BattleShipServerLoginIntermediate;
import bship.network.sockets.SocketIntermediate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class BattleShipServerLogin extends BattleShipGui
{
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private String username, password;
	
	public BattleShipServerLogin(JFrame frame, JPanel menuPanel, SocketIntermediate intermediate)
	{
		this.frame = frame;
		lastPanel = menuPanel;
		this.intermediate = new BattleShipServerLoginIntermediate();
		currPanel = new JPanel();
		frame.getContentPane().add(currPanel, "BattleShip Login Panel");
		currPanel.setLayout(null);
		menuPanel.setVisible(false);
		currPanel.setVisible(true);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(155, 80, 86, 20);
		currPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(73, 83, 72, 14);
		currPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 129, 72, 14);
		currPanel.add(lblPassword);
		
		passwordTextField = new JTextField();

		passwordTextField.setColumns(10);
		passwordTextField.setBounds(155, 126, 86, 20);
		currPanel.add(passwordTextField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
				BattleShipServerLoginIntermediate login = new BattleShipServerLoginIntermediate();
				try 
				{
					username = usernameTextField.getText();
					password = passwordTextField.getText();
					login.requestLogin(BattleShipServerLogin.this, username, password);
				} 
				catch (IOException e1) 
				{
					BattleShipExceptionHandler.handleBattleShipException();
				}
			}
		});
		btnLogin.setBounds(155, 172, 89, 23);
		currPanel.add(btnLogin);
		
		menuPanel.setVisible(false);
		currPanel.setVisible(true);
		currPanel.addKeyListener(this);
		currPanel.requestFocusInWindow();
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
			new Lobby(frame, currPanel, username);
		else
			//do stuff like abrir um dialogo a dizer que ta mal ou assim
			return;
		
	}
}
