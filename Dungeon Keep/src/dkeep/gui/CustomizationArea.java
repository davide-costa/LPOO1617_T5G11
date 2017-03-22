package dkeep.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CustomizationArea extends JPanel implements MouseListener
{
	public CustomizationArea()
	{
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent mouse_event) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent mouse_event) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouse_event)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouse_event) 
	{
		if(SwingUtilities.isRightMouseButton(mouse_event))
			RemoveElementAt(mouse_event.getX(), mouse_event.getY());
		else if(SwingUtilities.isLeftMouseButton(mouse_event))
			AddElementAt(mouse_event.getX(), mouse_event.getY());	
		
		System.out.println("dsvksjdhgksdh");
	}

	@Override
	public void mouseReleased(MouseEvent mouse_event)
	{
		
		
	}
	

	private void AddElementAt(int x, int y) 
	{
			
	}

	private void RemoveElementAt(int x, int y)
	{
			
	}

}
