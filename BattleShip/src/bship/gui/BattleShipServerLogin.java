package bship.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bship.network.data.LoginResponseData;
import bship.network.sockets.BattleShipServerLoginIntermediate;
import bship.network.sockets.SocketIntermediate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BattleShipServerLogin extends BattleShipGui
{
	private JPanel battleShipServerLoginPanel;
	private JTextArea usernameTextArea;
	private JTextField usernameField;
	private JTextArea passwordTextArea;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private String username, password;
	
	public BattleShipServerLogin(JFrame frame, JPanel menuPanel, SocketIntermediate intermediate)
	{
		this.frame = frame;
		this.lastPanel = menuPanel;
		this.intermediate = new BattleShipServerLoginIntermediate();
		
		battleShipServerLoginPanel = new JPanel();
		battleShipServerLoginPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipServerLoginPanel);
		battleShipServerLoginPanel.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		passwordField.setBounds(798, 510, 227, 50);
		battleShipServerLoginPanel.add(passwordField);
		
		usernameField = new JTextField();
		usernameField.setBounds(774, 438, 251, 50);
		battleShipServerLoginPanel.add(usernameField);
		usernameField.setColumns(10);
		
		usernameTextArea = new JTextArea();
		usernameTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		usernameTextArea.setText("Username");
		usernameTextArea.setBounds(645, 443, 119, 37);
		battleShipServerLoginPanel.add(usernameTextArea);
		
		passwordTextArea = new JTextArea();
		passwordTextArea.setText("Password");
		passwordTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		passwordTextArea.setBounds(645, 510, 119, 37);
		battleShipServerLoginPanel.add(passwordTextArea);
		
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
				BattleShipServerLoginIntermediate login = new BattleShipServerLoginIntermediate();
				try 
				{
					username = usernameField.getText();
					password = passwordField.getPassword().toString();
					login.requestLogin(BattleShipServerLogin.this, username, password);
				} 
				catch (IOException e1) 
				{
					BattleShipExceptionHandler.handleBattleShipException();
				}
			}
		});
		btnLogin.setBounds(155, 172, 89, 23);
		battleShipServerLoginPanel.add(btnLogin);
		
		lastPanel.setVisible(false);
		battleShipServerLoginPanel.setVisible(true);
		battleShipServerLoginPanel.addKeyListener(this);
		battleShipServerLoginPanel.requestFocusInWindow();
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

	private void loginFailedMessage()
	{
		String message = "Username and password don't match. Please verify them.";
		JOptionPane.showMessageDialog(null, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
	}
	
	private void newAccountCreatedMessage()
	{
		String message = "New account created sucessfully";
		JOptionPane.showMessageDialog(null, message, "New account created", JOptionPane.INFORMATION_MESSAGE);
	
	}
	
	public void LoginResponse(LoginResponseData response) 
	{
		if(response.isSucceeded())
			if(response.newAcoountCreated())
				newAccountCreatedMessage();
			else
				new Lobby(frame, battleShipServerLoginPanel, username);
		else
			loginFailedMessage();
	}
}
