package bship.gui;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.MultiplayerOpponent;
import bship.logic.Opponent;
import bship.logic.SingleplayerOpponent;

public class GameGui extends BattleShipGui implements Observer
{
	private JPanel gamePanel;
	private JLabel allyGameArea;
	private JLabel opponentGameArea;
	private Game game;
	public final static int cellSize = 60;
	public final static int allyBoardXStartPos = 210;
	public final static int allyBoardYStartPos = 250;
	public final static int opponentBoardXStartPos = 1110;
	public final static int opponentBoardYStartPos = 250;
	
	
	public GameGui(JFrame frame, JPanel shipPlacementPanel, GameMap map, boolean isSinglePlayer)
	{
		this.frame = frame;
		this.lastPanel = shipPlacementPanel;
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
		game = new Game(map, opponent, this);
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
		
		//TODO draw ships
		
		for(int i = 0; i < map.getMapYSize(); i++)
			for(int j = 0; j < map.getMapXSize(); j++)
			{
				Coords coords = new Coords(j, i);
				CellState cell = map.getCellState(coords);
				allyBoardCoordsToScreenCoords(coords);
				if(cell.isDiscoveredAndWater())
					drawDiscoveredWaterCell(coords, cell, graphics);
				//draw X on cells of ships that are destroyed (but not fully destroyed)
			}
	}
	
	private void paintOpponentGameArea(Graphics graphics) 
	{
		GameMap map = game.getOpponentMap();
		graphics.drawImage(ImagesData.boardImage, opponentBoardXStartPos, allyBoardXStartPos, null);
		
		//TODO draw ships that are already fully destroyed
		
		for(int i = 0; i < map.getMapYSize(); i++)
			for(int j = 0; j < map.getMapXSize(); j++)
			{
				Coords coords = new Coords(j, i);
				opponentBoardCoordsToScreenCoords(coords);
				CellState cell = map.getCellState(coords);
				if(cell.isDiscoveredAndWater())
					drawDiscoveredWaterCell(coords, cell, graphics);
				else
					if(cell.isDiscoveredAndShip())
						if (cell.getShip() == null); //draw ships that have been hit but are still not totally discovered
							drawCellDestroyed(coords, cell, graphics);
			}
	}

	private void drawCellDestroyed(Coords screenCoords, CellState cell, Graphics graphics) 
	{
		graphics.drawImage(ImagesData.destroyedCellImage, screenCoords.GetX(), screenCoords.GetY(), cellSize, cellSize, null);
	}

	private void drawDiscoveredWaterCell(Coords screenCoords, CellState state, Graphics graphics) 
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
}
