package bship.gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagesData 
{
	public static ImageIcon carrierImage;
	public static ImageIcon battleShipImage;
	public static ImageIcon submarineImage;
	public static ImageIcon cruiserImage;
	public static ImageIcon destroyerImage;
	public static ImageIcon menuBackgroundImage;
	public static ImageIcon boardImage;
	
	
	public static void loadImages()
	{
		try 
		{
			carrierImage = new ImageIcon(ImageIO.read(new File("res/images/Carrier.jpg")));
			battleShipImage = new ImageIcon(ImageIO.read(new File("res/images/BattleShip.jpg")));
			submarineImage = new ImageIcon(ImageIO.read(new File("res/images/Submarine.jpg")));
			cruiserImage = new ImageIcon(ImageIO.read(new File("res/images/Cruiser.jpg")));
			destroyerImage = new ImageIcon(ImageIO.read(new File("res/images/Destroyer.jpg")));
			menuBackgroundImage = new ImageIcon(ImageIO.read(new File("res/images/menuBackground.jpg")));
			boardImage = new ImageIcon(ImageIO.read(new File("res/images/board.jpg")));
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
}
