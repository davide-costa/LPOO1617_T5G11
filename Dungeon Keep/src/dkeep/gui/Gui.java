package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import dkeep.logic.Game;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Gui
{
	private JFrame frame;
	private GameArea game_area;
	private JLabel LableState;
	private JButton btnNewGame;
	private JButton btnLoadGame;
	private JButton btnSaveGame;
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnExit;
	private JButton CustomizeKeepMap;
	private Game game;

	
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
					Gui window = new Gui();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		CreateJFrame();
		CreateGameArea();
		CreateButtonNewGame();
		CreateButtonUp();
		CreateButtonDown();
		CreateButtonLeft();
		CreateButtonRight();
		CreateLableState();
		CreateButtonExit();
		CreateButtonLoadGame();
		CreateButtonSaveGame();
		CreateButtonCustomizeKeepMap();	
	}
	
	private void CreateButtonCustomizeKeepMap() 
	{
		CustomizeKeepMap = new JButton("Customize Keep Map");
		CustomizeKeepMap.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JTextField x_size_str = new JTextField();
				JTextField y_size_str = new JTextField();
				Object[] message = {
					    "KeepMap X size:", x_size_str,
					    "KeepMap Y size:", y_size_str
					};

				int option = JOptionPane.showConfirmDialog(null, message, "KeepMap size", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) 
				{
					if(x_size_str.getText().isEmpty() || y_size_str.getText().isEmpty())
					 {
				    	JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "You must introduce x and y size for KeepMap", "Error", JOptionPane.ERROR_MESSAGE);
						return;
				    }
					
					int x_size = Integer.parseInt(x_size_str.getText());
				    if(x_size > 20 || x_size < 4)
				    {
				    	JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "KeepMap x size must be in 4 to 20 range", "Error", JOptionPane.ERROR_MESSAGE);
						return;
				    }
				    
				    int y_size = Integer.parseInt(y_size_str.getText());
				    if(y_size > 16 || y_size < 4)
				    {
				    	JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "KeepMap y size must be in 4 to 16 range", "Error", JOptionPane.ERROR_MESSAGE);
						return;
				    }
				    new CustomizationJFrame(x_size, y_size);
				}
			}
		});
		CustomizeKeepMap.setBounds(911, 44, 161, 29);
		frame.getContentPane().add(CustomizeKeepMap);
	}

	private void CreateButtonSaveGame() 
	{
		btnSaveGame = new JButton("Save Game");
		btnSaveGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (game == null)
				{
					LableState.setText("A game must be running in order to use the Save Game function.");
					return;
				}
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
				 game_area.requestFocusInWindow();
			}
		});
		btnSaveGame.setBounds(66, 24, 115, 29);
		frame.getContentPane().add(btnSaveGame);
	}

	private void CreateButtonLoadGame() 
	{
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				game = null;
				try
				{
					FileInputStream fileIn = new FileInputStream("GameStateFile");
					ObjectInputStream in = new ObjectInputStream(fileIn);
					game = (Game)in.readObject();
					in.close();
					fileIn.close();
				}
				catch (IOException i)
				{
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Error loading the game state file", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (ClassNotFoundException c)
				{
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Error loading the game state file", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				game_area.SetGame(game); //update the Game object pointer in the game to the newly loaded game
				frame.getContentPane().add(game_area);
				game_area.requestFocusInWindow();
				game_area.repaint();

				if (game.IsGameOver())
				{
					LableState.setText("This game has already ended. Please start a new game.");
					InactivateGameButtons();
				}
				else
				{
					LableState.setText("Game loaded sucessfully. You can play");
					ActivateGameButtons();
				}
			}
		});
		btnLoadGame.setBounds(216, 24, 115, 29);
		frame.getContentPane().add(btnLoadGame);
	}

	private void CreateButtonExit() 
	{
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnExit.setBounds(928, 516, 115, 29);
		frame.getContentPane().add(btnExit);
	}

	private void CreateLableState() 
	{
		LableState = new JLabel("You can start a new game");
		LableState.setFont(new Font("Tahoma", Font.PLAIN, 15));
		LableState.setHorizontalAlignment(SwingConstants.LEFT);
		LableState.setBounds(66, 740, 640, 52);
		frame.getContentPane().add(LableState);
	}

	private void CreateButtonRight() 
	{
		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay("right");
			}
		});
		btnRight.setBounds(1000, 299, 115, 26);
		frame.getContentPane().add(btnRight);
	}

	private void CreateButtonLeft()
	{
		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay("left");
			}
		});
		btnLeft.setBounds(847, 299, 115, 26);
		frame.getContentPane().add(btnLeft);
	}

	private void CreateButtonDown() 
	{
		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay("down");
			}
		});
		btnDown.setBounds(928, 341, 115, 26);
		frame.getContentPane().add(btnDown);	
	}

	private void CreateButtonUp()
	{
		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay("up");
			}
		});
		btnUp.setBounds(928, 257, 115, 26);
		frame.getContentPane().add(btnUp);
	}

	private JButton CreateButtonNewGame() 
	{
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int num_ogres = 0;
				if (!new File("KeepMap").isFile())
				{
					//handle the num of ogres
					String num_ogres_str = JOptionPane.showInputDialog(null, "Number of ogres", "2");
					if (num_ogres_str == null)
						return;

					if (num_ogres_str.isEmpty())
					{
						JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "You must enter a number of ogres", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					num_ogres = Integer.parseInt(num_ogres_str);
					if (num_ogres < 1 || num_ogres > 5)
					{
						JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "Number of ogres must be in 1 to 5 range!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				//handle the guard personality
				String personalities[] = new String[]{ "Rookie", "Drunken", "Suspicious" };
				String personality_name = (String)JOptionPane.showInputDialog(null, "Select guard personality", "Guard Personality", JOptionPane.QUESTION_MESSAGE, null, personalities, personalities[0]);
				if (personality_name == null)
					return;
				game = new Game(personality_name, num_ogres);
				LableState.setText("Game started");

				game_area.SetGame(game);
				frame.getContentPane().add(game_area);
				game_area.requestFocusInWindow();
				game_area.repaint();

				ActivateGameButtons();
			}
		});
		btnNewGame.setBounds(928, 123, 115, 29);
		frame.getContentPane().add(btnNewGame);
		return btnNewGame;
	}

	private void CreateGameArea() 
	{
		game_area = new GameArea(this);
		game_area.setBounds(36,89,800,640);
	}

	private void CreateJFrame() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1152, 851);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}

	public void NewPlay(String direction)
	{
		if (game.IsGameOver())
			return;
		
		int move_hero_value = game.MoveHero(direction);

		if (move_hero_value == 1)
			LableState.setText("NEXT LEVEL");
		else if (move_hero_value == -1)
		{
			LableState.setText("END OF GAME...YOU LOOSE");
			game_area.repaint();
			InactivateGameButtons();
			return;
		}

		if (game.IsEndOfGame())
		{
			LableState.setText("END OF GAME...YOU WIN");
			InactivateGameButtons();
		}
		
		if(!game.IsGameOver())
			LableState.setText("You can play");
		game_area.repaint();
		game_area.requestFocusInWindow();
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
