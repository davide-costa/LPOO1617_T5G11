package bship.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bship.logic.BattleShip;
import bship.logic.Carrier;
import bship.logic.Coords;
import bship.logic.Cruiser;
import bship.logic.DefaultMap;
import bship.logic.Destroyer;
import bship.logic.GameMap;
import bship.logic.MultiplayerShipPlacement;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.SinglePlayerShipPlacement;
import bship.logic.Submarine;
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
	private HashMap<DraggableShip, String> ships;
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
		
		if(isSinglePlayer)
			shipPlacement = new SinglePlayerShipPlacement(new DefaultMap(false));
		else
			shipPlacement = new MultiplayerShipPlacement(new DefaultMap(false));
			
		
		battleShipPlacementPanel = new JPanel();
		battleShipPlacementPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(battleShipPlacementPanel);
		battleShipPlacementPanel.setLayout(null);
		
	
		labelDestroyer = new DraggableShip(ImagesData.destroyerImage, this, new Point(1100, 240));
		labelDestroyer.setBounds(1100, 240, 120, 60);
		battleShipPlacementPanel.add(labelDestroyer);
		
		labelCruiser1 = new DraggableShip(ImagesData.cruiserImage, this, new Point(1100, 370));
		labelCruiser1.setBounds(1100, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser1);
		
		labelCruiser2 = new DraggableShip(ImagesData.cruiserImage, this, new Point(1350, 370));
		labelCruiser2.setBounds(1350, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser2);
		
		labelSubmarine = new DraggableShip(ImagesData.submarineImage, this, new Point(1100, 500));
		labelSubmarine.setBounds(1100, 500, 180, 60);
		battleShipPlacementPanel.add(labelSubmarine);
		
		labelBattleShip = new DraggableShip(ImagesData.battleShipImage, this, new Point(1100, 630));
		labelBattleShip.setBounds(1100, 630, 240, 60);
		battleShipPlacementPanel.add(labelBattleShip);
		
		labelCarrier = new DraggableShip(ImagesData.carrierImage, this, new Point(1100, 760));
		labelCarrier.setBounds(1100, 760, 300, 60);
		battleShipPlacementPanel.add(labelCarrier);
		FillShips();
		
		btnReady = new JButton("Ready");
		btnReady.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnReady.setBounds(963, 908, 150, 35);
		battleShipPlacementPanel.add(btnReady);
		btnReady.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					((ShipPlacementIntermediate)intermediate).playerIsReady();
				}
				catch (IOException e1)
				{
					BattleShipExceptionHandler.handleBattleShipException();
				}
			}
		});
		
		lblOpponentReady = new JLabel("Opponent Ready");
		lblOpponentReady.setForeground(Color.RED);
		lblOpponentReady.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblOpponentReady.setBounds(1610, 376, 230, 35);
		battleShipPlacementPanel.add(lblOpponentReady);
		lblOpponentReady.setVisible(false);
		
		gameMapArea = new JLabel(ImagesData.boardImage);
		gameMapArea.setBounds(400, 240, 600, 600);
		battleShipPlacementPanel.add(gameMapArea);
		
		lastPanel.setVisible(false);
		battleShipPlacementPanel.setVisible(true);
		battleShipPlacementPanel.addKeyListener(this);
		battleShipPlacementPanel.requestFocusInWindow();
	}
	
	private void FillShips() 
	{
		ships = new HashMap<DraggableShip, String>();
		ships.put(labelBattleShip, "BattleShip");
		ships.put(labelCarrier, "Carrier");
		ships.put(labelCruiser1, "Cruiser1");
		ships.put(labelCruiser2, "Cruiser2");
		ships.put(labelDestroyer, "Destroyer");
		ships.put(labelSubmarine, "Submarine");
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			intermediate.closeConnection();
			new Menu(this.frame, this.battleShipPlacementPanel);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}

	public void pickUpShip(DraggableShip ship)
	{
		shipPlacement.pickUpShip(ships.get(ship));
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
			System.out.println(ships.get(shipJLabel));
			if(shipPlacement.dropShip(new Coords(boardDropLocation), ships.get(shipJLabel), shipJLabel.getDirection()))
			{
				MapToBoardReferencial(screenDropLocation);
				AdjustDropPosition(screenDropLocation);
				MapToScreenReferencial(screenDropLocation);
				shipJLabel.setLocation(screenDropLocation);
				return;
			}
		}
				
		shipJLabel.setLocation(initLocation);
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
//		//get the drop position relative to the cell where it was dropped
//		Point cellPosition = new Point(position.x % cellSize, position.y % cellSize);
//		
//		int halfCellSize = cellSize / 2;
//		if (cellPosition.x > halfCellSize)
//		{
//			int nextCellStartPos = position.x / cellSize + cellSize;
//			position.x = nextCellStartPos;
//		}
//		if (cellPosition.y > halfCellSize)
//		{
//			int nextCellStartPos = position.y / cellSize + cellSize;
//			position.y = nextCellStartPos;
//		}
		
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
		new GameGui(ShipPlacementPanel.this.frame, ShipPlacementPanel.this, shipPlacement.getMap(),  ShipPlacementPanel.this.isSinglePlayer);
	}
}
