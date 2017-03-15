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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Gui 
{

	private JFrame frame;
	private JTextField num_ogres_value;
	private static JTextArea GameArea;
	private JLabel LableState;
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
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 914, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel num_ogres_label = new JLabel("Number of ogres");
		num_ogres_label.setBounds(84, 16, 204, 20);
		num_ogres_label.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(num_ogres_label);
		
		num_ogres_value = new JTextField();
		num_ogres_value.setBounds(303, 13, 146, 26);
		frame.getContentPane().add(num_ogres_value);
		num_ogres_value.setColumns(10);
		
		JLabel GuardPersonality = new JLabel("Guard Personality");
		GuardPersonality.setBounds(127, 67, 144, 20);
		frame.getContentPane().add(GuardPersonality);
		
		JComboBox personality_value = new JComboBox();
		personality_value.setModel(new DefaultComboBoxModel(new String[] {"Rookie", "Drunken", "Suspicious"}));
		personality_value.setMaximumRowCount(3);
		personality_value.setToolTipText("");
		personality_value.setBounds(303, 64, 146, 26);
		frame.getContentPane().add(personality_value);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//handle the num of ogres
				int num_ogres = Integer.parseInt(num_ogres_value.getText());
				if(num_ogres < 1 || num_ogres > 5)
				{
					 JPanel panel = new JPanel();
					 JOptionPane.showMessageDialog(panel, "Number of ogres must be in 1 to 5 range!", "Error", JOptionPane.ERROR_MESSAGE);
					 return;
				}
					
				//handle the guard personality
				String personality_name = personality_value.getName();
				game = new Game(personality_name, num_ogres);
				LableState.setText("Game started");
			}
		});
		btnNewGame.setBounds(690, 164, 115, 29);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('w');
			}
		});
		btnUp.setBounds(690, 254, 115, 26);
		frame.getContentPane().add(btnUp);
		
		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('s');
			}
		});
		btnDown.setBounds(690, 338, 115, 26);
		frame.getContentPane().add(btnDown);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('a');
			}
		});
		btnLeft.setBounds(609, 296, 115, 26);
		frame.getContentPane().add(btnLeft);
		
		JButton btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				NewPlay('d');
			}
		});
		btnRight.setBounds(762, 296, 115, 26);
		frame.getContentPane().add(btnRight);
		
		LableState = new JLabel("");
		LableState.setHorizontalAlignment(SwingConstants.LEFT);
		LableState.setBounds(42, 522, 616, 20);
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
		
		GameArea = new JTextArea();
		GameArea.setBounds(31, 147, 506, 361);
		frame.getContentPane().add(GameArea);
	}
	
	public void NewPlay(char user_input)
	{
		int move_hero_value;
		char curr_map[][] = game.GetGameMap();
		DrawBoard(curr_map);

		ComputeDestination(user_input);

		move_hero_value = game.MoveHero(dst_x, dst_y);

		if (move_hero_value == 1)
			LableState.setText("NEXT LEVEL");
		else if (move_hero_value == -1)
		{
			curr_map = game.GetGameMap();
			DrawBoard(curr_map);
			System.out.println("END OF GAME...YOU LOSE");
			return;
		}

		if (game.IsEndOfGame())
		{
			curr_map = game.GetGameMap();
			DrawBoard(curr_map);
			System.out.println("END OF GAME...YOU WIN");	
		}
		
		LableState.setText("You can play");
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


	public static void DrawBoard(char curr_map[][])
	{
		int map_x_size = curr_map[0].length;
		int map_y_size = curr_map.length;
		
		GameArea.setText(null);
		for (int i = 0; i < map_y_size; i++)
		{
			for (int j = 0; j < map_x_size; j++)
			{
				String character = curr_map[i][j] + " ";
				GameArea.append(character);
			}
			GameArea.append("\n");
		}
	}
}
