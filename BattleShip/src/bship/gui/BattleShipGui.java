package bship.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;

public class BattleShipGui {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JTextField txtOnlinePlayers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleShipGui window = new BattleShipGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BattleShipGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(0, 0, 1920, 1080);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel Lobbypanel = new JPanel();
		Lobbypanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(Lobbypanel);
		Lobbypanel.setLayout(null);
		
		JList list = new JList();
		list.setBounds(1077, 400, -197, -124);
		Lobbypanel.add(list);
		
		txtOnlinePlayers = new JTextField();
		txtOnlinePlayers.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		txtOnlinePlayers.setText("Online Players");
		txtOnlinePlayers.setBounds(875, 104, 172, 50);
		Lobbypanel.add(txtOnlinePlayers);
		txtOnlinePlayers.setColumns(10);
		
		JPanel BattleShipServerLogin = new JPanel();
		BattleShipServerLogin.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(BattleShipServerLogin);
		BattleShipServerLogin.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		passwordField.setBounds(798, 510, 227, 50);
		BattleShipServerLogin.add(passwordField);
		
		usernameField = new JTextField();
		usernameField.setBounds(774, 438, 251, 50);
		BattleShipServerLogin.add(usernameField);
		usernameField.setColumns(10);
		
		JTextArea userrnameTextArea = new JTextArea();
		userrnameTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		userrnameTextArea.setText("Username");
		userrnameTextArea.setBounds(645, 443, 119, 37);
		BattleShipServerLogin.add(userrnameTextArea);
		
		JTextArea passwordTextArea = new JTextArea();
		passwordTextArea.setText("Password");
		passwordTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		passwordTextArea.setBounds(645, 510, 119, 37);
		BattleShipServerLogin.add(passwordTextArea);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton btnSingleplayer = new JButton("SinglePlayer");
		btnSingleplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnSingleplayer.setBounds(701, 236, 459, 70);
		menuPanel.add(btnSingleplayer);
		
		JButton btnMultiplayer = new JButton("MultiPlayer");
		btnMultiplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnMultiplayer.setBounds(701, 382, 459, 70);
		menuPanel.add(btnMultiplayer);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnExit.setBounds(701, 524, 459, 70);
		menuPanel.add(btnExit);
		
		JButton btnFacebookLogin = new JButton("Facebook Login");
		btnFacebookLogin.setBounds(192, 917, 123, 57);
		menuPanel.add(btnFacebookLogin);
	}
}
