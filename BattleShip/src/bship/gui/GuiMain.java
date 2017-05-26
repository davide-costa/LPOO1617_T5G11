package bship.gui;

import java.awt.EventQueue;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import java.awt.CardLayout;

public class GuiMain 
{
	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 	{
			public void run() 
			{
				System.out.println(System.getProperty("user.name")); //platform independent
				java.net.InetAddress localMachine = null;
				try {
					localMachine = java.net.InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Hostname of local machine: " + localMachine.getHostName());
				GuiMain window = new GuiMain();
				window.frame.setVisible(true);
				MenuPanel menu = new MenuPanel(frame);
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
	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 450, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
	
	}
}

