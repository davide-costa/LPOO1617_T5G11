package bship.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bship.logic.Game;
import bship.logic.MultiplayerOpponent;
import bship.logic.Opponent;
import bship.logic.SingleplayerOpponent;

public class GameGui extends BattleShipGui
{
	private JPanel gamePanel;
	private JPanel allyGameArea;
	private JPanel opponentGameArea;
	
	public GameGui(JFrame frame, boolean isSinglePlayer)
	{
		this.frame = frame;
		
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
		
		allyGameArea = new JPanel();
		allyGameArea.setBounds(947, 5, 10, 10);
		gamePanel.add(allyGameArea);
		
		opponentGameArea = new JPanel();
		opponentGameArea.setBounds(962, 5, 10, 10);
		gamePanel.add(opponentGameArea);	
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
