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
	public static Image destroyedCellImage;
	public static Image discoveredWaterCellImage;
	
	public static ImageIcon carrierIcon;
	public static ImageIcon battleShipIcon;
	public static ImageIcon submarineIcon;
	public static ImageIcon cruiserIcon;
	public static ImageIcon destroyerIcons;
	public static ImageIcon menuBackgroundIcon;
	public static ImageIcon boardIcon;
	public static ImageIcon destroyedCellIcon;
	public static ImageIcon discoveredWaterCellIcon;
	
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
			destroyedCellImage = ImageIO.read(new File("res/images/atackedCellImage.png"));
			discoveredWaterCellImage = ImageIO.read(new File("res/images/discoveredWaterCellImage.png"));
			
			carrierIcon = new ImageIcon(carrierImage);
			battleShipIcon = new ImageIcon(battleShipImage);
			submarineIcon = new ImageIcon(submarineImage);
			cruiserIcon = new ImageIcon(cruiserImage);
			destroyerIcons = new ImageIcon(destroyerImage);
			menuBackgroundIcon = new ImageIcon(menuBackgroundImage);
			boardIcon = new ImageIcon(boardImage);
			destroyedCellIcon = new ImageIcon(destroyedCellImage);
			discoveredWaterCellIcon = new ImageIcon(discoveredWaterCellImage); 
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
}
