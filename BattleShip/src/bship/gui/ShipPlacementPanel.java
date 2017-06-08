package bship.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bship.ai.AIShipPlacer;
import bship.logic.Coords;
import bship.logic.DefaultMap;
import bship.logic.MultiplayerShipPlacement;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.SinglePlayerShipPlacement;
import bship.network.sockets.ShipPlacementIntermediate;
import java.awt.Color;

public class ShipPlacementPanel extends BattleShipGui
{
	private JPanel battleShipPlacementPanel;
	private boolean isSinglePlayer;
	private ShipPlacement shipPlacement;
	private JLabel gameMapArea;
	private JButton btnReady;
	private JLabel lblOpponentReady;
	private DraggableShip labelCarrier;
	private DraggableShip labelBattleShip;
	private DraggableShip labelSubmarine;
	private DraggableShip labelCruiser1;
	private DraggableShip labelCruiser2;
	private DraggableShip labelDestroyer;
	private HashMap<DraggableShip, String> draggableShipName;
	private HashMap<ImageIcon, ImageIcon> shipRotatedImage;
	public final static int boardXStartPos = 400;
	public final static int boardYStartPos = 240;
	public final static int boardSize = 600;
	public final static int boardDivisions = 10;
	public final static int cellSize = boardSize / boardDivisions;
	
	public ShipPlacementPanel(JFrame frame, JPanel battleShipServerPanel, boolean isSinglePlayer)
	{
		this.frame = frame;
		this.lastPanel = battleShipServerPanel;
		this.isSinglePlayer = isSinglePlayer;
		try 
		{
			this.intermediate = new ShipPlacementIntermediate(this);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
		
		createShipPlacement();
		fillShipRotatedImage();
		createShipPlacementPanel();
		createShipLabels();
		FillShips();
		createButtonReady();
		createButtonPlaceShipsAutomatically();		
		createLabelOpponentReady();
		
		gameMapArea = new JLabel(ImagesData.boardIcon);
		gameMapArea.setBounds(400, 240, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
		
		lastPanel.setVisible(false);
		battleShipPlacementPanel.setVisible(true);
		battleShipPlacementPanel.addKeyListener(this);
		battleShipPlacementPanel.requestFocusInWindow();
	}
	
	private void createShipPlacement()
	{
		if(isSinglePlayer)
			shipPlacement = new SinglePlayerShipPlacement(new DefaultMap(false));
		else
			shipPlacement = new MultiplayerShipPlacement(new DefaultMap(false));
	}
	
	private void createShipPlacementPanel()
	{
		battleShipPlacementPanel = new JPanel();
		battleShipPlacementPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipPlacementPanel);
		battleShipPlacementPanel.setLayout(null);
	}
	
	private void createShipLabels()
	{
		labelDestroyer = new DraggableShip(ImagesData.destroyerIcon, this, new Point(1100, 240));
		labelDestroyer.setBounds(1100, 240, 120, 60);
		battleShipPlacementPanel.add(labelDestroyer);
		
		labelCruiser1 = new DraggableShip(ImagesData.cruiserIcon, this, new Point(1100, 370));
		labelCruiser1.setBounds(1100, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser1);
		
		labelCruiser2 = new DraggableShip(ImagesData.cruiserIcon, this, new Point(1350, 370));
		labelCruiser2.setBounds(1350, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser2);
		
		labelSubmarine = new DraggableShip(ImagesData.submarineIcon, this, new Point(1100, 500));
		labelSubmarine.setBounds(1100, 500, 180, 60);
		battleShipPlacementPanel.add(labelSubmarine);
		
		labelBattleShip = new DraggableShip(ImagesData.battleShipIcon, this, new Point(1100, 630));
		labelBattleShip.setBounds(1100, 630, 240, 60);
		battleShipPlacementPanel.add(labelBattleShip);
		
		labelCarrier = new DraggableShip(ImagesData.carrierIcon, this, new Point(1100, 760));
		labelCarrier.setBounds(1100, 760, 300, 60);
		battleShipPlacementPanel.add(labelCarrier);
	}
	
	private void createButtonReady()
	{
		btnReady = new JButton("Ready");
		btnReady.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnReady.setBounds(1646, 785, 150, 35);
		battleShipPlacementPanel.add(btnReady);
		btnReady.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!shipPlacement.verifyAllShipsArePlaced())
				{
					String message = "Place all ships before start game";
					JOptionPane.showConfirmDialog(null, message, "Invalid start game", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				try
				{
					((ShipPlacementIntermediate)intermediate).playerIsReady();
				}
				catch (IOException e1)
				{
					BattleShipExceptionHandler.handleBattleShipException();
				}
				ShipPlacementPanel.this.requestFocusInWindow();
			}
		});
	}
	
	private void createLabelOpponentReady()
	{
		lblOpponentReady = new JLabel("Opponent Ready");
		lblOpponentReady.setForeground(Color.RED);
		lblOpponentReady.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblOpponentReady.setBounds(1610, 376, 230, 35);
		battleShipPlacementPanel.add(lblOpponentReady);
		lblOpponentReady.setVisible(false);
	}
	
	void createButtonPlaceShipsAutomatically()
	{
		JButton btnPlaceShipsAutomatically = new JButton("Place Ships Automatically");
		btnPlaceShipsAutomatically.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnPlaceShipsAutomatically.setBounds(1100, 880, 265, 35);
		btnPlaceShipsAutomatically.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				AIShipPlacer.removePlacedShipsOfMap(shipPlacement);
				AIShipPlacer.placeShipsInMap(shipPlacement);
				ShipPlacementPanel.this.refreshMapGraphics();
				ShipPlacementPanel.this.requestFocusInWindow();
			}
		});
		battleShipPlacementPanel.add(btnPlaceShipsAutomatically);
	}
	
	private void refreshMapGraphics()
	{
		Iterator it = draggableShipName.entrySet().iterator();
		
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry) it.next();
			DraggableShip draggableShip = (DraggableShip) pair.getKey();
			String shipName = (String) pair.getValue();
			drawShip(shipName, draggableShip);
		}
	}
	
	private void drawShip(String shipName, DraggableShip draggableShip)
	{
		Ship ship = shipPlacement.getShipName(shipName);
		if(!draggableShip.getDirection().equals(ship.getDirection()))
			draggableShip.rotate();
		
		int xCoord = boardXStartPos + ship.getInitCoords().GetX() * cellSize;
		int yCoord = boardYStartPos + ship.getInitCoords().GetY() * cellSize;
		draggableShip.setLocation(xCoord, yCoord);
	}

	private void FillShips() 
	{
		draggableShipName = new HashMap<DraggableShip, String>();
		draggableShipName.put(labelBattleShip, "BattleShip");
		draggableShipName.put(labelCarrier, "Carrier");
		draggableShipName.put(labelCruiser1, "Cruiser1");
		draggableShipName.put(labelCruiser2, "Cruiser2");
		draggableShipName.put(labelDestroyer, "Destroyer");
		draggableShipName.put(labelSubmarine, "Submarine");
	}
	

	private void fillShipRotatedImage() 
	{
		shipRotatedImage = new HashMap<ImageIcon, ImageIcon>();
		shipRotatedImage.put(ImagesData.battleShipIcon, ImagesData.battleShipVerticalIcon);
		shipRotatedImage.put(ImagesData.cruiserIcon, ImagesData.cruiserVerticalIcon);
		shipRotatedImage.put(ImagesData.carrierIcon, ImagesData.carrierVerticalIcon);
		shipRotatedImage.put(ImagesData.submarineIcon, ImagesData.submarineVerticalIcon);
		shipRotatedImage.put(ImagesData.destroyerIcon, ImagesData.destroyerVerticalIcon);
		shipRotatedImage.put(ImagesData.battleShipVerticalIcon, ImagesData.battleShipIcon);
		shipRotatedImage.put(ImagesData.cruiserVerticalIcon, ImagesData.cruiserIcon);
		shipRotatedImage.put(ImagesData.carrierVerticalIcon, ImagesData.carrierIcon);
		shipRotatedImage.put(ImagesData.submarineVerticalIcon, ImagesData.submarineIcon);
		shipRotatedImage.put(ImagesData.destroyerVerticalIcon, ImagesData.destroyerIcon);
	}
	
	public ImageIcon getRotatedImage(ImageIcon imageIcon)
	{
		return shipRotatedImage.get(imageIcon);
	}


	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
			backToMenu();
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}

	public void pickUpShip(DraggableShip ship)
	{
		shipPlacement.pickUpShip(draggableShipName.get(ship));
	}
	
	protected void tryDropShip(MouseEvent event, Point initLocation) 
	{
		DraggableShip shipJLabel = (DraggableShip)event.getComponent();
		Point screenDropLocation = shipJLabel.getLocation();
		Point boardDropLocation = new Point(screenDropLocation);
		MapToBoardReferencial(boardDropLocation);
		AdjustDropPosition(boardDropLocation);
		if(isDropInBoardRange(shipJLabel.getSize(), boardDropLocation))
		{
			boardDropLocation.x /= cellSize;
			boardDropLocation.y /= cellSize;
			
			if(shipPlacement.dropShip(new Coords(boardDropLocation), draggableShipName.get(shipJLabel), shipJLabel.getDirection()))
			{
				MapToBoardReferencial(screenDropLocation);
				AdjustDropPosition(screenDropLocation);
				MapToScreenReferencial(screenDropLocation);
				shipJLabel.setLocation(screenDropLocation);
				return;
			}
		}
				
		shipJLabel.setLocation(initLocation);
		shipJLabel.resetDirection();
	}
	
	private void MapToBoardReferencial(Point position)
	{
		position.x -= boardXStartPos;
		position.y -= boardYStartPos;
	}
	
	private void MapToScreenReferencial(Point position)
	{
		position.x += boardXStartPos;
		position.y += boardYStartPos;
	}
	
	public ShipPlacement getShipPlacement()
	{
		return shipPlacement;
	}

	private void AdjustDropPosition(Point position)
	{
		position.x = Math.round((float)position.x / cellSize) * cellSize;
		position.y = Math.round((float)position.y / cellSize) * cellSize;
	}

	private boolean isDropInBoardRange(Dimension shipDimension, Point dropLocation) 
	{
		int width = (int)shipDimension.getWidth();
		int height = (int)shipDimension.getHeight();
		
		if(dropLocation.getX() < 0 || dropLocation.getX() + width > boardSize)
			return false;
		if(dropLocation.getY() < 0 || dropLocation.getY() + height > boardSize)
			return false;
		
		return true;
	}
	
	//This function is called by the intermediate to inform this class that the opponent is ready
	public void opponentIsReady()
	{
		lblOpponentReady.setVisible(true);
	}
	
	public void startGame()
	{
		new GameGui(frame, battleShipPlacementPanel, shipPlacement, isSinglePlayer);
	}

	public void opponentQuit() 
	{
		String message = "Opponent quit. Game Over!";
		JOptionPane.showMessageDialog(null, message, "Opponent Quit", JOptionPane.INFORMATION_MESSAGE);
		backToMenu();
	}

	private void backToMenu() 
	{
		intermediate.closeConnection();
		new Menu(this.frame, this.battleShipPlacementPanel);
	}
}
