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
	private CustomizationArea cust_area;

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
		setBounds(100, 100, 1152, 851);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Create cust area
		cust_area = new CustomizationArea();
		cust_area.setBounds(38,89,846,637);
		this.getContentPane().add(cust_area);
		cust_area.requestFocusInWindow();
		cust_area.repaint();
		
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
				element_selected = "wall";
			}
		});
		btnWall.setBounds(979, 328, 115, 29);
		contentPane.add(btnWall);
		
		JButton btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "door";
			}
		});
		btnDoor.setBounds(979, 373, 115, 29);
		contentPane.add(btnDoor);
		
		JButton btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "hero";
			}
		});
		btnHero.setBounds(979, 283, 115, 29);
		contentPane.add(btnHero);
		
		JButton btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "key";
			}
		});
		btnKey.setBounds(979, 418, 115, 29);
		contentPane.add(btnKey);
	}
}
