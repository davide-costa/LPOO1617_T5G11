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
	
	public BattleShipServerLogin(JFrame frame, SocketIntermediate intermediate)
	{
		this.frame = frame;;
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
				
				battleShipServerLoginPanel.requestFocusInWindow();
			}
		});
		btnLogin.setBounds(155, 172, 89, 23);
		battleShipServerLoginPanel.add(btnLogin);
		
		this.setVisible(true);
		battleShipServerLoginPanel.addKeyListener(this);
		battleShipServerLoginPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
			new Menu(this.frame);
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}

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
		System.out.println("LoginResponse");
		if (response.isSucceeded()) 
		{
			if (response.newAcoountCreated())
			{
				System.out.println("newAcoountCreated");
				newAccountCreatedMessage();
			}
				
			System.out.println("Lobby");
			new Lobby(frame, username);
		} 
		else
		{
			System.out.println("loginFailedMessage");
			loginFailedMessage();
		}
			
	}
}
