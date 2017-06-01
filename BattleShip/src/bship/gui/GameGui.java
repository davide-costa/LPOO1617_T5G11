package bship.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		game = new Game(opponent, this);
		opponent.setGame(game);
		
		gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		allyGameArea = new JLabel(ImagesData.boardImage);
		allyGameArea.setBounds(210, 250, 600, 600);
		gamePanel.add(allyGameArea);
		
		opponentGameArea = new JLabel(ImagesData.boardImage);
		opponentGameArea.setBounds(1110, 250, 600, 600);
		gamePanel.add(opponentGameArea);	
		
		
		lastPanel.setVisible(false);
		gamePanel.setVisible(true);
		gamePanel.addKeyListener(this);
		gamePanel.requestFocusInWindow();
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
		super.paintComponent(graphics);
		
		paintAllyGameArea();
		paintOpponentGameArea();
	}

	private void paintAllyGameArea() 
	{
		GameMap map = game.getAllyMap();
		
		for(int i = 0; i < map.getMapYSize(); i++)
			for(int j = 0; j < map.getMapXSize(); j++)
			{
				
			}
		
	}

	@Override
	public void update(Observable gameMap, Object object) 
	{
		repaint();
	}
}
