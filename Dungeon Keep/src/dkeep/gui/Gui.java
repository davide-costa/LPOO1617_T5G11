package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import dkeep.logic.Game;
import dkeep.logic.Guard;
import dkeep.logic.Hero;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;

public class Gui
{
	private JFrame frame;
	private GameArea game_area;
	private JLabel LableState;
	JButton btnLoadGame;
	JButton btnSaveGame;
	JButton btnUp;
	JButton btnDown;
	JButton btnLeft;
	JButton btnRight;
	private Game game;
	private int dst_x = 0;
	private int dst_y = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
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
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 914, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//handle the num of ogres
				String num_ogres_str = JOptionPane.showInputDialog(null, "Number of ogres", "2");
				if(num_ogres_str.isEmpty())
				{
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "You must enter a number of ogres", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int num_ogres = Integer.parseInt(num_ogres_str);
				if(num_ogres < 1 || num_ogres > 5)
				{
					 JPanel panel = new JPanel();
					 JOptionPane.showMessageDialog(panel, "Number of ogres must be in 1 to 5 range!", "Error", JOptionPane.ERROR_MESSAGE);
					 return;
				}
					
				//handle the guard personality
				String personalities[] = new String[] {"Rookie", "Drunken", "Suspicious"};
				String personality_name = (String) JOptionPane.showInputDialog(null, "Select guard personality", "Guard Personality", JOptionPane.QUESTION_MESSAGE, null, personalities, personalities[0]);
				game = new Game(personality_name, num_ogres);
				LableState.setText("Game started");
				game_area.DrawBoard(game.GetGameMap());
				ActivateGameButtons();
				
				game_area.requestFocusInWindow();
				
				//TODO:no activate game buttons deve estar o saveGame
			}
		});
		btnNewGame.setBounds(690, 120, 115, 29);
		frame.getContentPane().add(btnNewGame);
		
		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('w');
			}
		});
		btnUp.setBounds(690, 254, 115, 26);
		frame.getContentPane().add(btnUp);
		
		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('s');
			}
		});
		btnDown.setBounds(690, 338, 115, 26);
		frame.getContentPane().add(btnDown);
		
		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('a');
			}
		});
		btnLeft.setBounds(609, 296, 115, 26);
		frame.getContentPane().add(btnLeft);
		
		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('d');
			}
		});
		btnRight.setBounds(762, 296, 115, 26);
		frame.getContentPane().add(btnRight);
		
		LableState = new JLabel("You can start a new game");
		LableState.setFont(new Font("Tahoma", Font.PLAIN, 13));
		LableState.setHorizontalAlignment(SwingConstants.LEFT);
		LableState.setBounds(43, 517, 616, 20);
		frame.getContentPane().add(LableState);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnExit.setBounds(690, 513, 115, 29);
		frame.getContentPane().add(btnExit);
		
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				game = null;
			     try 
			     {
			         FileInputStream fileIn = new FileInputStream("GameStateFile");
			         ObjectInputStream in = new ObjectInputStream(fileIn);
			         game = (Game) in.readObject();
			         in.close();
			         fileIn.close();
			         LableState.setText("Game loaded sucessfully");
			      }
			     catch(IOException i) 
			     {
			    	 JPanel panel = new JPanel();
			    	 JOptionPane.showMessageDialog(panel, "Error loading the game state file", "Error", JOptionPane.ERROR_MESSAGE);
			    	 return;	
			      }
			     catch(ClassNotFoundException c) 
			     {
			    	 JPanel panel = new JPanel();
			    	 JOptionPane.showMessageDialog(panel, "Error loading the game state file", "Error", JOptionPane.ERROR_MESSAGE);
			    	 return;
			      }
			     
			    LableState.setText("Game started");
				game_area.DrawBoard(game.GetGameMap());
				ActivateGameButtons();
				game_area.requestFocusInWindow();
			}
		});
		btnLoadGame.setBounds(216, 24, 115, 29);
		frame.getContentPane().add(btnLoadGame);
		
		btnSaveGame = new JButton("Save Game");
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 try 
			      {
			          FileOutputStream fileOut = new FileOutputStream("GameStateFile");
			          ObjectOutputStream out = new ObjectOutputStream(fileOut);
			          out.writeObject(game);
			          out.close();
			          fileOut.close();
			          LableState.setText("Game saved in GameStateFile");
			       }
			      catch(IOException i)
			      {
			    	  JPanel panel = new JPanel();
			    	  JOptionPane.showMessageDialog(panel, "Error saving the game state file", "Error", JOptionPane.ERROR_MESSAGE);
			    	  return;	
			      }
			}
		});
		btnSaveGame.setBounds(66, 24, 115, 29);
		frame.getContentPane().add(btnSaveGame);
		game_area.requestFocusInWindow();
	}
	
	public void NewPlay(char user_input)
	{
		char curr_map[][];
		int move_hero_value;

		ComputeDestination(user_input);

		move_hero_value = game.MoveHero(dst_x, dst_y);

		if (move_hero_value == 1)
			LableState.setText("NEXT LEVEL");
		else if (move_hero_value == -1)
		{
			curr_map = game.GetGameMap();
			game_area.DrawBoard(curr_map);
			LableState.setText("END OF GAME...YOU LOOSE");
			InactivateGameButtons();
			return;
		}

		if (game.IsEndOfGame())
		{
			curr_map = game.GetGameMap();
			game_area.DrawBoard(curr_map);
			LableState.setText("END OF GAME...YOU WIN");
			InactivateGameButtons();
		}
		

		curr_map = game.GetGameMap();
		game_area.DrawBoard(curr_map);
		if(!game.IsGameOver())
			LableState.setText("You can play");
		game_area.repaint();
		game_area.requestFocusInWindow();
	}
	
	public void ComputeDestination(char input)
	{
		Hero hero = game.GetHero();
		dst_x = hero.GetX();
		dst_y = hero.GetY();

		if (input == 'w')
			dst_y--;
		else if (input == 'a')
			dst_x--;
		else if (input == 's')
			dst_y++;
		else if (input == 'd')
			dst_x++;
	}


	public void InactivateGameButtons()
	{
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
	}
	
	public void ActivateGameButtons()
	{
		btnUp.setEnabled(true);
		btnDown.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
	}
}
