package bship.gui;

import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bship.network.sockets.SocketIntermediate;

public abstract class BattleShipGui implements KeyListener
{
	protected JFrame frame;
	protected JPanel lastPanel;
	protected JPanel currPanel;
	protected SocketIntermediate intermediate;
}