package bship.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
					ImagesData.loadImages();
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
					
//					GuiMain window = new GuiMain();
//					window.frame.setVisible(true);
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
		
		
		JPanel battleShipPlacementPanel = new JPanel();
		battleShipPlacementPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipPlacementPanel);
		battleShipPlacementPanel.setLayout(null);
		
		JPanel gameMapArea = new JPanel();
		gameMapArea.setBounds(400, 240, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
		
		JLabel labelCarrier = new JLabel();
		labelCarrier.setBounds(1100, 760, 300, 60);
		battleShipPlacementPanel.add(labelCarrier);
		
		JLabel labelBattleShip = new JLabel();
		labelBattleShip.setBounds(1100, 630, 240, 60);
		battleShipPlacementPanel.add(labelBattleShip);
		
		JLabel labelSubmarine = new JLabel();
		labelSubmarine.setBounds(1100, 500, 180, 60);
		battleShipPlacementPanel.add(labelSubmarine);
		
		JLabel labelCruiser1 = new JLabel();
		labelCruiser1.setBounds(1100, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser1);
		
		JLabel labelCruiser2 = new JLabel();
		labelCruiser2.setBounds(1350, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser2);
		
		JLabel labelDestroyer = new JLabel();
		labelDestroyer.setBounds(1100, 240, 120, 60);
		battleShipPlacementPanel.add(labelDestroyer);
		
		JButton btnStartGame= new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStartGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnStartGame.setBounds(963, 908, 150, 35);
		battleShipPlacementPanel.add(btnStartGame);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		JPanel allyGameArea = new JPanel();
		allyGameArea.setBounds(210, 250, 600, 600);
		gamePanel.add(allyGameArea);
		allyGameArea.setLayout(null);
		
		JPanel opponentGameArea = new JPanel();
		opponentGameArea.setBounds(1110, 250, 600, 600);
		gamePanel.add(opponentGameArea);
		opponentGameArea.setLayout(null);
		
		
		JPanel battleShipServerLoginPanel = new JPanel();
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
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(685, 425, 250, 50);
		battleShipServerLoginPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblPassword.setBounds(685, 525, 250, 50);
		battleShipServerLoginPanel.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		btnLogin.setBounds(910, 625, 100, 30);
		battleShipServerLoginPanel.add(btnLogin);
		
		JPanel exitPanel = new JPanel();
		exitPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(exitPanel);
		exitPanel.setLayout(null);
		
		txtAreYouSure = new JTextField();
		txtAreYouSure.setEditable(false);
		txtAreYouSure.setHorizontalAlignment(SwingConstants.CENTER);
		txtAreYouSure.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		txtAreYouSure.setText("Are you sure you want to quit?");
		txtAreYouSure.setBounds(610, 450, 700, 65);
		exitPanel.add(txtAreYouSure);
		txtAreYouSure.setColumns(10);
		
		JButton btnYes = new JButton("Yes");
		btnYes.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnYes.setBounds(745, 580, 90, 35);
		exitPanel.add(btnYes);
		
		JButton btnNo = new JButton("No");
		btnNo.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnNo.setBounds(1085, 580, 90, 35);
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
		
		JPanel singlePlayerDifficultyPanel = new JPanel();
		singlePlayerDifficultyPanel.setBounds(610, 340, 700, 400);
		frame.getContentPane().add(singlePlayerDifficultyPanel);
		singlePlayerDifficultyPanel.setLayout(null);
	}
}
