package bship.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bship.logic.Game;
import bship.logic.GameMap;
import bship.logic.MultiplayerOpponent;
import bship.logic.Opponent;
import bship.logic.SingleplayerOpponent;

public class GameGui extends BattleShipGui
{
	private JPanel gamePanel;
	private JLabel allyGameArea;
	private JLabel opponentGameArea;
	
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
		Game game = new Game(opponent, this);
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
	
}
