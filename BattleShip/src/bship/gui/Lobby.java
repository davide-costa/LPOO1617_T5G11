package bship.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lobby implements KeyListener
{
	JFrame frame;
	JPanel battleShipLoginPanel;
	JPanel lobbyPanel;
	
	public Lobby(JFrame frame, JPanel battleShipLoginPanel)
	{
		this.frame = frame;
		this.battleShipLoginPanel = battleShipLoginPanel;
	
		lobbyPanel = new JPanel();
		frame.getContentPane().add(lobbyPanel, "Lobby Panel");
		lobbyPanel.setLayout(null);
		battleShipLoginPanel.setVisible(false);
		lobbyPanel.setVisible(true);
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void handleInvite(String inviterName) {
		// TODO Auto-generated method stub
		
	}

	public void handleInviteResponse(boolean wasAccepted) {
		// TODO Auto-generated method stub
		
	}
}
