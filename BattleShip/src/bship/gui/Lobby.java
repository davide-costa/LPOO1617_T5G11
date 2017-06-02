package bship.gui;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bship.network.sockets.LobbyIntermediate;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Lobby extends BattleShipGui
{
	private JPanel lobbyPanel;
	private JLabel txtOnlinePlayers;
	private JOptionPane waitingForResponse;
	private JLabel background;
	private DefaultListModel<String> model;
	private JList<String> inLobbyPlayersList;
	private String username;
	private String invitedPlayerUsername;
		
	public Lobby(JFrame frame, JPanel loginPanel, String username)
	{
		this.frame = frame;
		this.username = username;
		this.lastPanel = loginPanel;
		try 
		{
			this.intermediate = new LobbyIntermediate(this);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
		
		lobbyPanel = new JPanel();
		lobbyPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(lobbyPanel);
		lobbyPanel.setLayout(null);
		
		txtOnlinePlayers = new JLabel("Online Players");
		txtOnlinePlayers.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		txtOnlinePlayers.setBounds(870, 100, 180, 50);
		lobbyPanel.add(txtOnlinePlayers);
		
		
		model = new DefaultListModel<String>();
		inLobbyPlayersList = new JList<String>(model);
		inLobbyPlayersList.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		inLobbyPlayersList.setBounds(870, 250, 180, 750);
		lobbyPanel.add(inLobbyPlayersList);
		inLobbyPlayersList.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent event) 
					{
						invitePlayer(inLobbyPlayersList.getSelectedValue());
					}

					@Override
					public void mouseEntered(MouseEvent event) {}

					@Override
					public void mouseExited(MouseEvent event) {}

					@Override
					public void mousePressed(MouseEvent event) {}	

					@Override
					public void mouseReleased(MouseEvent event) {}

				});
		
		background = new JLabel(ImagesData.menuBackgroundIcon);
		background.setBounds(0, 0, 1920, 1080);
		lobbyPanel.add(background);

		lastPanel.setVisible(false);
		lobbyPanel.setVisible(true);
		lobbyPanel.addKeyListener(this);
		lobbyPanel.requestFocusInWindow();
	}
	

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			intermediate.closeConnection();
			new Menu(this.frame, this.lobbyPanel);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	private void invitePlayer(String playerUsername)
	{
		try 
		{
			invitedPlayerUsername = playerUsername;
			String message = "Wating for player" + invitedPlayerUsername + " response...";
			waitingForResponse = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION);
			waitingForResponse.setEnabled(true);
			lobbyPanel.add(waitingForResponse);
			
			((LobbyIntermediate) intermediate).invitePlayer(playerUsername);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}

	public void handleInvite(String inviterName) 
	{
		String message = "Player " + inviterName + " invited you to play a game";
		int option = JOptionPane.showConfirmDialog(null, message, "Invite Response", JOptionPane.YES_NO_OPTION);

		boolean response;
		if (option == JOptionPane.YES_OPTION)
		{
			new ShipPlacementPanel(this.frame, this.lobbyPanel, false);
			response = true;
		}
		else
			response = false;

		try 
		{
			((LobbyIntermediate) intermediate).inviteResponse(response);
		} 
		catch (IOException e) 
		{
			BattleShipExceptionHandler.handleBattleShipException();
		}
	}
	
	private void InviteRejectedMessage() 
	{
		String message = "Player " + invitedPlayerUsername + " has rejected your invitation";
		JOptionPane.showMessageDialog(null, message, "Invite Response", JOptionPane.OK_OPTION);	
	}


	public void handleInviteResponse(boolean wasAccepted) 
	{
		waitingForResponse.setEnabled(false);
		if(wasAccepted)
			new ShipPlacementPanel(this.frame, this.lobbyPanel, false);
		else
			InviteRejectedMessage();
	}


	public void setNamesInModel(ArrayList<String> playersNames) 
	{
		model.removeAllElements();
		
		for(String playerName: playersNames)
			model.addElement(playerName);
		
		model.removeElement(username);
	}
}
