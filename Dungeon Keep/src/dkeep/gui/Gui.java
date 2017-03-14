package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import dkeep.logic.Guard;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui 
{

	private JFrame frame;
	private JTextField num_ogres_value;

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
				Guard guard;
				int num_ogres = Integer.parseInt(num_ogres_value.getText());
				if(num_ogres > )
				{
					 JPanel panel = new JPanel();
					 JOptionPane.showMessageDialog(panel, "Number of ogres must ", "Error", JOptionPane.ERROR_MESSAGE);
				}
					
				String personality_name = personality_value.getName();
			}
		});
		btnNewGame.setBounds(690, 164, 115, 29);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnUp = new JButton("Up");
		btnUp.setBounds(690, 254, 115, 26);
		frame.getContentPane().add(btnUp);
		
		JButton btnDown = new JButton("Down");
		btnDown.setBounds(690, 338, 115, 26);
		frame.getContentPane().add(btnDown);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.setBounds(609, 296, 115, 26);
		frame.getContentPane().add(btnLeft);
		
		JButton btnRight = new JButton("Right");
		btnRight.setBounds(762, 296, 115, 26);
		frame.getContentPane().add(btnRight);
		
		JLabel LableState = new JLabel("");
		LableState.setHorizontalAlignment(SwingConstants.CENTER);
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
	}
}
