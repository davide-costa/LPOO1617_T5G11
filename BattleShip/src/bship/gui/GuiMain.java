package bship.gui;

import javax.swing.JFrame;


public class GuiMain 
{
	private static JFrame frame;

	public static void main(String[] args) 
	{
		try 
		{
			initialize();
			ImagesData.loadImages();
			new Menu(frame, null);
		} 
		catch (Exception e)
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}

	private static void initialize() 
	{
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(0, 0, 1920, 1080);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
