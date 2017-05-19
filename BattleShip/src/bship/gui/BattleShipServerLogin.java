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
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class BattleShipServerLogin implements KeyListener
{
	private JFrame frame;
	private JPanel menuPanel;
	private JPanel battleShipLoginPanel;
	private JTextField usernameTextField;
	private JTextField PasswordTextField;
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
		usernameTextField.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) 
			{
				username = event.getText().toString();
			}
		});
		usernameTextField.setBounds(155, 80, 86, 20);
		battleShipLoginPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(73, 83, 72, 14);
		battleShipLoginPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 129, 72, 14);
		battleShipLoginPanel.add(lblPassword);
		
		PasswordTextField = new JTextField();
		PasswordTextField.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) 
			{
				password = event.getText().toString();
			}
		});
		PasswordTextField.setColumns(10);
		PasswordTextField.setBounds(155, 126, 86, 20);
		battleShipLoginPanel.add(PasswordTextField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				BattleShipServerLoginIntermediate login = new BattleShipServerLoginIntermediate();
				try 
				{
					System.out.println("1");
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
