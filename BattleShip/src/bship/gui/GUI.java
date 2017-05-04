package bship.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField txtNewWindowFor;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
//		frame = new JFrame();
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		frame.setUndecorated(true);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
		

		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "name_160817388610410");
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "name_160821879791158");
		panel_1.setLayout(null);
		
		JButton btnChangeWindow = new JButton("Change Window");
		btnChangeWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("stuff");
				FacebookLogin f = new FacebookLogin();
				
			}
		});
		btnChangeWindow.setBounds(164, 113, 130, 23);
		panel.add(btnChangeWindow);
		
		

		
		txtNewWindowFor = new JTextField();
		txtNewWindowFor.setText("NEW WINDOW FOR THE WIN");
		txtNewWindowFor.setBounds(81, 80, 261, 53);
		panel_1.add(txtNewWindowFor);
		txtNewWindowFor.setColumns(10);

	}

}
