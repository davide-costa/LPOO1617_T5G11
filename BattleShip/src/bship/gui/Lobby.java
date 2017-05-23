package bship.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bship.network.sockets.LobbyIntermediate;
import bship.network.sockets.SocketIntermediate;
import javax.swing.JList;

public class Lobby extends BattleShipGui
{
	public Lobby(JFrame frame, JPanel battleShipLoginPanel)
	{
		this.frame = frame;
		this.lastPanel = battleShipLoginPanel;
		try 
		{
			this.intermediate = new LobbyIntermediate(this);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		currPanel = new JPanel();
		
		
		
		frame.getContentPane().add(currPanel, "Lobby Panel");
		battleShipLoginPanel.setVisible(false);
		currPanel.setVisible(true);
		currPanel.addKeyListener(this);
		System.out.println("lobby constructor");
		
	    DefaultListModel<String> model = new DefaultListModel<String>();
	    model.addElement("Player1");
	    model.addElement("Player1");
	    model.addElement("Player1");
	    model.addElement("Player1");
	    model.addElement("Player1");
		JList<String> list_1 = new JList<String>(model);
		currPanel.add(list_1);
		list_1.setBounds(100, 200, 500, 500);
		currPanel.setVisible(true);
		currPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Lobby");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Lobby");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Lobby");
	}

	public void handleInvite(String inviterName) {
		// TODO Auto-generated method stub
		System.out.println("Lobby");
	}

	public void handleInviteResponse(boolean wasAccepted) {
		// TODO Auto-generated method stub
		System.out.println("Lobby");
	}
}
