package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class CustomizableJFrame extends JFrame
{
	private JPanel contentPane;
	private String element_selected;

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
					CustomizableJFrame frame = new CustomizableJFrame();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomizableJFrame() 
	{
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1152, 851);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "ogre";
			}
		});
		btnOgre.setBounds(979, 238, 115, 29);
		contentPane.add(btnOgre);
		
		JButton btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "ogre";
			}
		});
		btnWall.setBounds(979, 328, 115, 29);
		contentPane.add(btnWall);
		
		JButton btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDoor.setBounds(979, 373, 115, 29);
		contentPane.add(btnDoor);
		
		JButton btnHero = new JButton("Hero");
		btnHero.setBounds(979, 283, 115, 29);
		contentPane.add(btnHero);
		
		JButton btnKey = new JButton("Key");
		btnKey.setBounds(979, 418, 115, 29);
		contentPane.add(btnKey);
		
		JPanel panel = new JPanel();
		panel.setBounds(32, 54, 900, 664);
		contentPane.add(panel);
	}
}
