package bship.gui;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bship.network.sockets.LobbyIntermediate;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Lobby extends BattleShipGui
{
	private JPanel lobbyPanel;
	private JTextField txtOnlinePlayers;
	private DefaultListModel<String> model;
	private JList<String> inLobbyPlayersList;
	private String username;
	private String invitedPlayerUsername;
	private JOptionPane waitingForResponse;
		
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
		
		txtOnlinePlayers = new JTextField();
		txtOnlinePlayers.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		txtOnlinePlayers.setText("Online Players");
		txtOnlinePlayers.setBounds(870, 100, 180, 50);
		lobbyPanel.add(txtOnlinePlayers);
		txtOnlinePlayers.setColumns(10);
		
		
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
						String selectedPlayer = inLobbyPlayersList.getSelectedValue();
						try 
						{
							((LobbyIntermediate) intermediate).invitePlayer(selectedPlayer);
						} 
						catch (IOException e) 
						{
							BattleShipExceptionHandler.handleBattleShipException();
						}
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

	public void handleInvite(String inviterName) 
	{
		invitedPlayerUsername = inviterName;
		
		String message = "Player " + invitedPlayerUsername + " invited you to play a game";
		int option = JOptionPane.showConfirmDialog(null, message, "Invite Response", JOptionPane.YES_NO_OPTION);

		boolean response;
		if (option == JOptionPane.YES_OPTION)
			response = true;
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
		
		message = "Wating for player" + invitedPlayerUsername + " response...";
		waitingForResponse = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION);
		lobbyPanel.add(waitingForResponse);
		waitingForResponse.setEnabled(true);
	}
	
	private void InviteRejectedMessage() 
	{
		String message = "Player " + invitedPlayerUsername + " has rejected your invitation";
		JOptionPane.showMessageDialog(null, message, "Invite Response", JOptionPane.OK_OPTION);	
	}


	public void handleInviteResponse(boolean wasAccepted) 
	{
		//waitingForResponse.setEnabled(false);
		System.out.println(wasAccepted);
		if(wasAccepted)
			new ShipPlacementPanel(this.frame, this.lastPanel);
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
