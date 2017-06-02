package bship.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bship.network.data.LoginResponseData;
import bship.network.sockets.BattleShipServerLoginIntermediate;
import bship.network.sockets.Client;
import bship.network.sockets.SocketIntermediate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private JLabel lblUsername;
	private JTextField usernameField;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JLabel background;
	private String username, password;
	
	public BattleShipServerLogin(JFrame frame, SocketIntermediate intermediate, JPanel menuPanel)
	{
		this.frame = frame;
		this.lastPanel = menuPanel;
		this.intermediate = new BattleShipServerLoginIntermediate();
		
		battleShipServerLoginPanel = new JPanel();
		battleShipServerLoginPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipServerLoginPanel);
		battleShipServerLoginPanel.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		passwordField.setBounds(985, 525, 250, 50);
		battleShipServerLoginPanel.add(passwordField);
		
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		usernameField.setBounds(985, 425, 250, 50);
		battleShipServerLoginPanel.add(usernameField);
		usernameField.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(685, 425, 250, 50);
		battleShipServerLoginPanel.add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblPassword.setBounds(685, 525, 250, 50);
		battleShipServerLoginPanel.add(lblPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				BattleShipServerLoginIntermediate login = new BattleShipServerLoginIntermediate();
				try 
				{
					username = usernameField.getText();
					password = passwordField.getPassword().toString();
					if(username.isEmpty() || password.isEmpty())
						return;
					login.requestLogin(BattleShipServerLogin.this, username, password);
				} 
				catch (IOException e1) 
				{
					BattleShipExceptionHandler.handleBattleShipException();
				}
				
				battleShipServerLoginPanel.requestFocusInWindow();
			}
		});
		btnLogin.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		btnLogin.setBounds(910, 625, 100, 30);
		battleShipServerLoginPanel.add(btnLogin);
		
		background = new JLabel(ImagesData.menuBackgroundIcon);
		background.setBounds(0, 0, 1920, 1080);
		battleShipServerLoginPanel.add(background);
		
		lastPanel.setVisible(false);
		battleShipServerLoginPanel.setVisible(true);
		battleShipServerLoginPanel.addKeyListener(this);
		battleShipServerLoginPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			intermediate.closeConnection();
			new Menu(this.frame, this.battleShipServerLoginPanel);
		}
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
			new Lobby(frame, this.battleShipServerLoginPanel, username);
		} 
		else
		{
			System.out.println("loginFailedMessage");
			loginFailedMessage();
		}
			
	}
}
