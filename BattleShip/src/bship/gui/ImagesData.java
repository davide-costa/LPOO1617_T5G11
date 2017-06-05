package bship.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
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
	public static Image cruiserDestroyedImage;
	public static Image carrierDestroyedImage;
	public static Image battleShipDestroyedImage;
	public static Image submarineDestroyedImage;
	public static Image destroyerDestroyedImage;
	public static Image carrierVerticalImage;
	public static Image battleShipVerticalImage;
	public static Image cruiserVerticalImage;
	public static Image destroyerVerticalImage;
	public static Image submarineVerticalImage;
	public static Image cruiserDestroyedVerticalImage;
	public static Image carrierDestroyedVerticalImage;
	public static Image battleShipDestroyedVerticalImage;
	public static Image submarineDestroyedVerticalImage;
	public static Image destroyerDestroyedVerticalImage;
	public static Image gameDefeatImage;
	public static Image gameVictoryImage;
	public static Image facebookShareImage;
	
	public static ImageIcon carrierIcon;
	public static ImageIcon battleShipIcon;
	public static ImageIcon submarineIcon;
	public static ImageIcon cruiserIcon;
	public static ImageIcon destroyerIcon;
	public static ImageIcon menuBackgroundIcon;
	public static ImageIcon boardIcon;
	public static ImageIcon destroyedCellIcon;
	public static ImageIcon discoveredWaterCellIcon;
	public static ImageIcon carrierVerticalIcon;
	public static ImageIcon battleShipVerticalIcon;
	public static ImageIcon submarineVerticalIcon;
	public static ImageIcon cruiserVerticalIcon;
	public static ImageIcon destroyerVerticalIcon;
	public static ImageIcon facebookShareIcon;

	
	public static void loadImages()
	{
		try 
		{
			carrierImage = ImageIO.read(new File("res/images/Carrier.png"));
			battleShipImage = ImageIO.read(new File("res/images/BattleShip.png"));
			submarineImage = ImageIO.read(new File("res/images/Submarine.png"));
			cruiserImage = ImageIO.read(new File("res/images/Cruiser.png"));
			destroyerImage = ImageIO.read(new File("res/images/Destroyer.png"));
			carrierVerticalImage = ImageIO.read(new File("res/images/CarrierVertical.png"));
			battleShipVerticalImage = ImageIO.read(new File("res/images/BattleShipVertical.png"));
			submarineVerticalImage = ImageIO.read(new File("res/images/SubmarineVertical.png"));
			cruiserVerticalImage = ImageIO.read(new File("res/images/CruiserVertical.png"));
			destroyerVerticalImage = ImageIO.read(new File("res/images/DestroyerVertical.png"));
			menuBackgroundImage = ImageIO.read(new File("res/images/menuBackground.jpg"));
			boardImage = ImageIO.read(new File("res/images/board.png"));
			destroyedCellImage = ImageIO.read(new File("res/images/destroyedCellImage.png"));
			discoveredWaterCellImage = ImageIO.read(new File("res/images/discoveredWaterCellImage.png"));
			cruiserDestroyedImage = ImageIO.read(new File("res/images/CruiserDestroyed.png"));
			carrierDestroyedImage = ImageIO.read(new File("res/images/CarrierDestroyed.png"));
			battleShipDestroyedImage = ImageIO.read(new File("res/images/BattleShipDestroyed.png"));
			submarineDestroyedImage = ImageIO.read(new File("res/images/SubmarineDestroyed.png"));
			destroyerDestroyedImage = ImageIO.read(new File("res/images/DestroyerDestroyed.png"));
			cruiserDestroyedVerticalImage = ImageIO.read(new File("res/images/CruiserDestroyedVertical.png"));
			carrierDestroyedVerticalImage = ImageIO.read(new File("res/images/CarrierDestroyedVertical.png"));
			battleShipDestroyedVerticalImage = ImageIO.read(new File("res/images/BattleShipDestroyedVertical.png"));
			submarineDestroyedVerticalImage = ImageIO.read(new File("res/images/SubmarineDestroyedVertical.png"));
			destroyerDestroyedVerticalImage = ImageIO.read(new File("res/images/DestroyerDestroyedVertical.png"));
			gameDefeatImage = ImageIO.read(new File("res/images/GameDefeat.jpg"));
			gameVictoryImage = ImageIO.read(new File("res/images/GameVictory.jpg"));
			facebookShareImage = ImageIO.read(new File("res/images/FacebookShareButton.jpg"));
			
			carrierIcon = new ImageIcon(carrierImage);
			battleShipIcon = new ImageIcon(battleShipImage);
			submarineIcon = new ImageIcon(submarineImage);
			cruiserIcon = new ImageIcon(cruiserImage);
			destroyerIcon = new ImageIcon(destroyerImage);
			carrierVerticalIcon = new ImageIcon(carrierVerticalImage);
			battleShipVerticalIcon = new ImageIcon(battleShipVerticalImage);
			submarineVerticalIcon = new ImageIcon(submarineVerticalImage);
			cruiserVerticalIcon = new ImageIcon(cruiserVerticalImage);
			destroyerVerticalIcon = new ImageIcon(destroyerVerticalImage);
			menuBackgroundIcon = new ImageIcon(menuBackgroundImage);
			boardIcon = new ImageIcon(boardImage);
			facebookShareIcon = new ImageIcon(facebookShareImage);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
}
