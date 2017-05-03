package bship.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BattleShipGui {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=602,239
	 */
	private final JTextField textField = new JTextField();

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
		textField.setColumns(10);
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 300);
		frame.getContentPane().add(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 450, 300);
		frame.getContentPane().add(panel_1);
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				panel_1.setVisible(true);
				panel.setVisible(false);
			}
		});
		panel.add(btnNewButton);
		
		JCheckBox chckbxAshfkasdhf = new JCheckBox("ashfkasdhf\u00B4~");
		panel.add(chckbxAshfkasdhf);
		
	
		
		
	}

}
