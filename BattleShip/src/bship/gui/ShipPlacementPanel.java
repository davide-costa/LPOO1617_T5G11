package bship.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

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

public class ShipPlacementPanel extends BattleShipGui
{
	private JPanel battleShipPlacementPanel;
	private ShipPlacement shipPlacement;
	private JLabel gameMapArea;
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
	
	public ShipPlacementPanel(JFrame frame, JPanel menuPanel, boolean isSinglePlayer)
	{
		this.frame = frame;
		this.lastPanel = menuPanel;
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
		
	
		labelDestroyer = new DraggableShip(ImagesData.destroyerImage, this);
		labelDestroyer.setBounds(1100, 240, 120, 60);
		battleShipPlacementPanel.add(labelDestroyer);
		
		labelCruiser1 = new DraggableShip(ImagesData.cruiserImage, this);
		labelCruiser1.setBounds(1100, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser1);
		
		labelCruiser2 = new DraggableShip(ImagesData.cruiserImage, this);
		labelCruiser2.setBounds(1350, 370, 180, 60);
		battleShipPlacementPanel.add(labelCruiser2);
		
		labelSubmarine = new DraggableShip(ImagesData.submarineImage, this);
		labelSubmarine.setBounds(1100, 500, 180, 60);
		battleShipPlacementPanel.add(labelSubmarine);
		
		labelBattleShip = new DraggableShip(ImagesData.battleShipImage, this);
		labelBattleShip.setBounds(1100, 630, 240, 60);
		battleShipPlacementPanel.add(labelBattleShip);
		
		labelCarrier = new DraggableShip(ImagesData.carrierImage, this);
		labelCarrier.setBounds(1100, 760, 300, 60);
		battleShipPlacementPanel.add(labelCarrier);
		FillShips();
		
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
	
	
	protected void tryDropShip(MouseEvent event, Point initLocation) 
	{
		Point dropLocation = event.getComponent().getLocation();
		MapToBoardReferencial(dropLocation);
		AdjustDropPosition(dropLocation);
		if(isDropInBoardRange(event.getComponent().getSize(), dropLocation))
		{
			dropLocation.x /= cellSize;
			dropLocation.y /= cellSize;
			if(shipPlacement.dropShip(new Coords(dropLocation), ships.get(event.getComponent())))
				return;
		}
				
		event.getComponent().setLocation(initLocation);
	}
	
	private void MapToBoardReferencial(Point position)
	{
		position.x -= boardXStartPos;
		position.y -= boardYStartPos;
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
		int boardXEndPos = boardXStartPos + boardSize;
		int boardYEndPos = boardYStartPos + boardSize;
		
		if(dropLocation.getX() < boardXStartPos || dropLocation.getX() + width > boardXEndPos)
			return false;
		if(dropLocation.getY() < boardYStartPos || dropLocation.getY() + height > boardYEndPos)
			return false;
		
		return true;
	}


}
