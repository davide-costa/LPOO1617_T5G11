package bship.gui;

import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bship.network.sockets.SocketIntermediate;

public abstract class BattleShipGui extends JPanel implements KeyListener
{
	protected JFrame frame;
	protected JPanel lastPanel;
	protected SocketIntermediate intermediate;
}
