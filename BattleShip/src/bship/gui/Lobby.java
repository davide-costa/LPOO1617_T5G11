package bship.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private DefaultListModel<String> model;
	private JList<String> inLobbyPlayersList;
	private String username;
		
	public Lobby(JFrame frame, JPanel battleShipLoginPanel, String username)
	{
		this.frame = frame;
		
		this.lastPanel = battleShipLoginPanel;
		this.username = username;
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
		
		model = new DefaultListModel<String>();
		inLobbyPlayersList = new JList<String>(model);
		currPanel.add(inLobbyPlayersList);
		inLobbyPlayersList.setBounds(100, 200, 500, 500);
		currPanel.setVisible(true);
		currPanel.requestFocusInWindow();
		inLobbyPlayersList.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						String selectedPlayer = inLobbyPlayersList.getSelectedValue();
						System.out.println(selectedPlayer);
						try {
							((LobbyIntermediate) intermediate).invitePlayer(selectedPlayer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						inLobbyPlayersList.setBackground(Color.GRAY);
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						inLobbyPlayersList.setBackground(getForeground());;
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			
				});
	}
	
	  @Override
	  public void repaint() 
	  {
	      super.repaint();
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

	public void handleInvite(String inviterName) 
	{
		System.out.println("LobbyInvitedData" + inviterName);
		System.out.println("Lobby");
		try {
			((LobbyIntermediate)intermediate).inviteResponse(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleInviteResponse(boolean wasAccepted) {
		// TODO Auto-generated method stub
		System.out.println("Lobby");
	}


	public void setNamesInModel(ArrayList<String> playersNames) 
	{
		model.removeAllElements();
		
		for(String playerName: playersNames)
			model.addElement(playerName);
		
		model.removeElement(username);
	}
}
