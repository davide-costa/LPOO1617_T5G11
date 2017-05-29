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
import java.awt.FlowLayout;

public class GuiMain 
{
	protected JFrame frame;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JTextField txtOnlinePlayers;
	private JTextField txtAreYouSure;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					JFrame frame = new JFrame();
					frame.setResizable(false);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setBounds(0, 0, 1920, 1080);
					frame.setUndecorated(true);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(null);
					Menu m = new Menu(frame, null);
					m.setVisible(true);
					//Thread.sleep(10000);
					
					//GuiMain window = new GuiMain();
					//window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiMain() {
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
		
		JPanel exitPanel = new JPanel();
		exitPanel.setBounds(610, 340, 700, 400);
		frame.getContentPane().add(exitPanel);
		exitPanel.setLayout(null);
		
		txtAreYouSure = new JTextField();
		txtAreYouSure.setHorizontalAlignment(SwingConstants.CENTER);
		txtAreYouSure.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		txtAreYouSure.setText("Are you sure you want to quit?");
		txtAreYouSure.setBounds(0, 64, 700, 69);
		exitPanel.add(txtAreYouSure);
		txtAreYouSure.setColumns(10);
		
		JButton btnYes = new JButton("Yes");
		btnYes.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnYes.setBounds(123, 243, 93, 37);
		exitPanel.add(btnYes);
		
		JButton btnNo = new JButton("No");
		btnNo.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnNo.setBounds(480, 243, 93, 37);
		exitPanel.add(btnNo);
		
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
		
		JPanel battleShipPlacementPanel = new JPanel();
		battleShipPlacementPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipPlacementPanel);
		battleShipPlacementPanel.setLayout(null);
		
		JPanel gameMapArea = new JPanel();
		gameMapArea.setBounds(400, 200, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		JPanel allyGameArea = new JPanel();
		allyGameArea.setBounds(947, 5, 10, 10);
		gamePanel.add(allyGameArea);
		
		JPanel opponentGameArea = new JPanel();
		opponentGameArea.setBounds(962, 5, 10, 10);
		gamePanel.add(opponentGameArea);
		
		JPanel endOfGamePanel = new JPanel();
		endOfGamePanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(endOfGamePanel);
		endOfGamePanel.setLayout(null);
		
		JPanel lobbyPanel = new JPanel();
		lobbyPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(lobbyPanel);
		lobbyPanel.setLayout(null);
		
		JList list = new JList();
		list.setBounds(1077, 400, -197, -124);
		lobbyPanel.add(list);
		
		txtOnlinePlayers = new JTextField();
		txtOnlinePlayers.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		txtOnlinePlayers.setText("Online Players");
		txtOnlinePlayers.setBounds(875, 104, 172, 50);
		lobbyPanel.add(txtOnlinePlayers);
		txtOnlinePlayers.setColumns(10);
		
		JPanel battleShipServerLoginPanel = new JPanel();
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
		
		JTextArea userrnameTextArea = new JTextArea();
		userrnameTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		userrnameTextArea.setText("Username");
		userrnameTextArea.setBounds(645, 443, 119, 37);
		battleShipServerLoginPanel.add(userrnameTextArea);
		
		JTextArea passwordTextArea = new JTextArea();
		passwordTextArea.setText("Password");
		passwordTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		passwordTextArea.setBounds(645, 510, 119, 37);
		battleShipServerLoginPanel.add(passwordTextArea);
		
		JPanel singlePlayerDifficultyPanel = new JPanel();
		singlePlayerDifficultyPanel.setBounds(610, 340, 700, 400);
		frame.getContentPane().add(singlePlayerDifficultyPanel);
		singlePlayerDifficultyPanel.setLayout(null);
	}
}
