package bship.gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagesData 
{
	public static Image carrierImage;
	public static Image battleShipImage;
	public static Image submarineImage;
	public static Image cruiserImage;
	public static Image destroyerImage;
	public static Image menuBackgroundImage;
	public static Image boardImage;
	public static ImageIcon carrierIcon;
	public static ImageIcon battleShipIcon;
	public static ImageIcon submarineIcon;
	public static ImageIcon cruiserIcon;
	public static ImageIcon destroyerIcons;
	public static ImageIcon menuBackgroundIcon;
	public static ImageIcon boardIcon;
	
	public static void loadImages()
	{
		try 
		{
			carrierImage = ImageIO.read(new File("res/images/Carrier.png"));
			battleShipImage = ImageIO.read(new File("res/images/BattleShip.png"));
			submarineImage = ImageIO.read(new File("res/images/Submarine.png"));
			cruiserImage = ImageIO.read(new File("res/images/Cruiser.png"));
			destroyerImage = ImageIO.read(new File("res/images/Destroyer.png"));
			menuBackgroundImage = ImageIO.read(new File("res/images/menuBackgound.jpg"));
			boardImage = ImageIO.read(new File("res/images/board.png"));
			
			carrierIcon = new ImageIcon(carrierImage);
			battleShipIcon = new ImageIcon(battleShipImage);
			submarineIcon = new ImageIcon(submarineImage);
			cruiserIcon = new ImageIcon(cruiserImage);
			destroyerIcons = new ImageIcon(destroyerImage);
			menuBackgroundIcon = new ImageIcon(menuBackgroundImage);
			boardIcon = new ImageIcon(boardImage);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
}
