package bship.gui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.MultiplayerOpponent;
import bship.logic.Opponent;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.SingleplayerOpponent;

public class GameGui extends BattleShipGui implements Observer, MouseListener
{
	private JPanel gamePanel;
	private JLabel allyGameArea;
	private JLabel opponentGameArea;
	private Game game;
	private ArrayList<Ship> allyShips;
	private HashMap<String, Image> aliveShipsHorizontalImages;
	private HashMap<String, Image> destroyedShipsHorizontalImages;
	private HashMap<String, Image> aliveShipsVerticalImages;
	private HashMap<String, Image> destroyedShipsVerticalImages;
	public final static int cellSize = 60;
	public final static int allyBoardXStartPos = 210;
	public final static int allyBoardYStartPos = 250;
	public final static int opponentBoardXStartPos = 1110;
	public final static int opponentBoardYStartPos = 250;
	
	
	public GameGui(JFrame frame, JPanel shipPlacementPanel, ShipPlacement shipPlacement, boolean isSinglePlayer)
	{
		this.frame = frame;
		this.lastPanel = shipPlacementPanel;
		allyShips = shipPlacement.getPlacedShips();
		fillAliveShipsHorizontalImages();
		fillDestroyedShipsHorizontalImages();
		
		
		Opponent opponent = null;
		try 
		{
			if(isSinglePlayer)
				opponent = new SingleplayerOpponent();
			else
				opponent = new MultiplayerOpponent();
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
		game = new Game(shipPlacement.getMap(), opponent, this);
		opponent.setGame(game);
		
//		gamePanel = new JPanel();
//		gamePanel.setBounds(0, 0, 1920, 1080);
//		frame.getContentPane().add(gamePanel);
//		gamePanel.setLayout(null);
		
//		allyGameArea = new JLabel(ImagesData.boardImage);
//		allyGameArea.setBounds(210, 250, 600, 600);
//		gamePanel.add(allyGameArea);
//		
//		opponentGameArea = new JLabel(ImagesData.boardImage);
//		opponentGameArea.setBounds(1110, 250, 600, 600);
//		gamePanel.add(opponentGameArea);	
		
		
		lastPanel.setVisible(false);
		this.setVisible(true);
		setBounds(0, 0, 1920, 1080);
		this.setLayout(new FlowLayout());
		frame.getContentPane().add(this);
//		allyGameArea = new JLabel(ImagesData.boardImage);
//		allyGameArea.setBounds(210, 250, 600, 600);
//		this.add(allyGameArea);
//		gamePanel.setVisible(true);
//		gamePanel.addKeyListener(this);
//		gamePanel.requestFocusInWindow();
	}

	private void fillAliveShipsHorizontalImages() 
	{
		aliveShipsHorizontalImages = new HashMap<String, Image>();
		aliveShipsHorizontalImages.put("Cruiser", ImagesData.cruiserImage);
		aliveShipsHorizontalImages.put("Carrier", ImagesData.carrierImage);
		aliveShipsHorizontalImages.put("BattleShip", ImagesData.battleShipImage);
		aliveShipsHorizontalImages.put("Submarine", ImagesData.submarineImage);
		aliveShipsHorizontalImages.put("Destroyer", ImagesData.destroyerImage);
	}
	
	private void fillDestroyedShipsHorizontalImages() 
	{
		destroyedShipsHorizontalImages = new HashMap<String, Image>();
		destroyedShipsHorizontalImages.put("Cruiser", ImagesData.cruiserDestroyedImage);
		destroyedShipsHorizontalImages.put("Carrier", ImagesData.carrierDestroyedImage);
		destroyedShipsHorizontalImages.put("BattleShip", ImagesData.battleShipDestroyedImage);
		destroyedShipsHorizontalImages.put("Submarine", ImagesData.submarineDestroyedImage);
		destroyedShipsHorizontalImages.put("Destroyer", ImagesData.destroyerDestroyedImage);
	}
	
	public void fillAliveShipsVerticalImages()
	{
		aliveShipsHorizontalImages = new HashMap<String, Image>();
		aliveShipsHorizontalImages.put("Cruiser", ImagesData.cruiserVerticalImage);
		aliveShipsHorizontalImages.put("Carrier", ImagesData.carrierVerticalImage);
		aliveShipsHorizontalImages.put("BattleShip", ImagesData.battleShipVerticalImage);
		aliveShipsHorizontalImages.put("Submarine", ImagesData.submarineVerticalImage);
		aliveShipsHorizontalImages.put("Destroyer", ImagesData.destroyerVerticalImage);
	}
	
	public void filldestroyedShipsVerticalImages()
	{
		destroyedShipsHorizontalImages = new HashMap<String, Image>();
		destroyedShipsHorizontalImages.put("Cruiser", ImagesData.cruiserDestroyedVerticalImage);
		destroyedShipsHorizontalImages.put("Carrier", ImagesData.carrierDestroyedVerticalImage);
		destroyedShipsHorizontalImages.put("BattleShip", ImagesData.battleShipDestroyedVerticalImage);
		destroyedShipsHorizontalImages.put("Submarine", ImagesData.submarineDestroyedVerticalImage);
		destroyedShipsHorizontalImages.put("Destroyer", ImagesData.destroyerDestroyedVerticalImage);
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			intermediate.closeConnection();
			new Menu(this.frame, this.gamePanel);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
	
	@Override
	protected void paintComponent(Graphics graphics) 
	{
		System.out.println("paint compponent called");
		super.paintComponent(graphics);
		
		paintAllyGameArea(graphics);
		paintOpponentGameArea(graphics);
	}

	private void paintAllyGameArea(Graphics graphics) 
	{
		GameMap map = game.getAllyMap();
		graphics.drawImage(ImagesData.boardImage, allyBoardXStartPos, allyBoardYStartPos, null);
		
		drawAliveAllyShips(graphics);
		
		for(int i = 0; i < map.getMapYSize(); i++)
			for(int j = 0; j < map.getMapXSize(); j++)
			{
				Coords coords = new Coords(j, i);
				CellState cell = map.getCellState(coords);
				if(cell == null)
					continue;
				Coords screenCoords = allyBoardCoordsToScreenCoords(coords);
				if(cell.isDiscoveredAndWater())
					drawDiscoveredWaterCell(screenCoords, graphics);
				else if(cell.isDiscovered() && !cell.hasShipDestroyed())
					drawCellDestroyed(screenCoords, graphics);
				//draw X on cells of ships that are destroyed (but not fully destroyed)
			}
	}

	private void drawAliveAllyShips(Graphics graphics) 
	{
		for(Ship ship: allyShips)
		{
			if(ship.isDestroyed())
				continue;

			Coords screenCoords = allyBoardCoordsToScreenCoords(ship.getInitCoords());
			Image shipImage = getAliveShipImage(ship);
			graphics.drawImage(shipImage, screenCoords.GetX(), screenCoords.GetY(), shipImage.getWidth(null), shipImage.getHeight(null), null);
		}
	}

	private Image getAliveShipImage(Ship ship) 
	{
		if (ship.getDirection().equals("horizontal"))
			return aliveShipsHorizontalImages.get(ship.getName());
		else if (ship.getDirection().equals("horizontal"))
			return aliveShipsVerticalImages.get(ship.getName());
		else
			throw new IllegalArgumentException();
	}

	private void paintOpponentGameArea(Graphics graphics) 
	{
		GameMap map = game.getOpponentMap();
		graphics.drawImage(ImagesData.boardImage, opponentBoardXStartPos, opponentBoardYStartPos, null);
		
		drawOpponentFullyDestroyedShips(graphics);
		
		for(int i = 0; i < map.getMapYSize(); i++)
			for(int j = 0; j < map.getMapXSize(); j++)
			{
				Coords coords = new Coords(j, i);
				Coords screenCoords = opponentBoardCoordsToScreenCoords(coords);
				CellState cell = map.getCellState(coords);
			
				if(cell == null)
					return;
				if(cell.isDiscoveredAndWater())
					drawDiscoveredWaterCell(screenCoords, graphics);
				else
					if(cell.isDiscoveredAndShip())
						if (cell.getShip() == null) //draw ships that have been hit but are still not totally discovered
							drawCellDestroyed(screenCoords, graphics);
			}
	}

	private void drawOpponentFullyDestroyedShips(Graphics graphics) 
	{
		ArrayList<Ship> fullyDestroyedShips = game.getOpponentShips();
		
		for(Ship ship: fullyDestroyedShips)
		{
			if(ship.isDestroyed())
			{
				Coords screenCoords = opponentBoardCoordsToScreenCoords(ship.getInitCoords());

				Image shipImage = getDestroyedShipImage(ship);
				graphics.drawImage(shipImage, screenCoords.GetX(), screenCoords.GetY(),	shipImage.getWidth(null), shipImage.getHeight(null), null);
			}
		}
	}

	private Image getDestroyedShipImage(Ship ship) 
	{
		return destroyedShipsHorizontalImages.get(ship.getName());
	}

	private void drawCellDestroyed(Coords screenCoords, Graphics graphics) 
	{
		graphics.drawImage(ImagesData.destroyedCellImage, screenCoords.GetX(), screenCoords.GetY(), cellSize, cellSize, null);
	}

	private void drawDiscoveredWaterCell(Coords screenCoords, Graphics graphics) 
	{
		graphics.drawImage(ImagesData.discoveredWaterCellImage, screenCoords.GetX(), screenCoords.GetY(), cellSize, cellSize, null);
	}

	private Coords allyBoardCoordsToScreenCoords(Coords boardcoords)
	{
		return boardCoordsToScreenCoords(boardcoords, allyBoardXStartPos, allyBoardYStartPos);
	}
	
	private Coords opponentBoardCoordsToScreenCoords(Coords boardcoords)
	{
		return boardCoordsToScreenCoords(boardcoords, opponentBoardXStartPos, opponentBoardYStartPos);
	}
	
	private Coords boardCoordsToScreenCoords(Coords boardcoords, int boardXStartPos, int boardYStartPos)
	{
		int xCoord = boardXStartPos + boardcoords.GetX() * cellSize; 
		int yCoord = boardYStartPos + boardcoords.GetY() * cellSize; 
	
		return new Coords(xCoord, yCoord);
	}
	
	@Override
	public void update(Observable gameMap, Object object) 
	{
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent event) 
	{
		if(SwingUtilities.isLeftMouseButton(event))
		{
			Coords screenShootCoords = new Coords(event.getPoint());
			Coords boardShootCoords = allyBoardCoordsToScreenCoords(screenShootCoords);
			try 
			{	
				game.shootOpponent(boardShootCoords);
			} 
			catch (IOException e) 
			{
				BattleShipExceptionHandler.handleBattleShipException();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}
}
