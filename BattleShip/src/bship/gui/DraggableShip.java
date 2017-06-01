package bship.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import bship.logic.Coords;

public class DraggableShip extends DraggableJLabel
{
	private String direction;
	
	public DraggableShip(ImageIcon image, ShipPlacementPanel shipPlacementPanel, Point initLocation) 
	{
		super(image);
		this.direction = "horizontal";
		
		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent event) 
			{
				
			}

			@Override
			public void mouseMoved(MouseEvent event) 
			{
				
			}
	
			
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event)
			{
		
			}

			@Override
			public void mouseEntered(MouseEvent event)
			{
				
			}

			@Override
			public void mouseExited(MouseEvent event)
			{
				
			}

			@Override
			public void mousePressed(MouseEvent event)
			{
				if(SwingUtilities.isRightMouseButton(event))
				{
					toggleDirection();
					rotate(event.getPoint());
				}
				else if(SwingUtilities.isLeftMouseButton(event))
					shipPlacementPanel.pickUpShip(DraggableShip.this);
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				if(SwingUtilities.isLeftMouseButton(event))
					shipPlacementPanel.tryDropShip(event, initLocation);
			}
		});
	}
	
	public String getDirection()
	{
		return direction;
	}

	private void toggleDirection()
	{
		if (direction == "vertical")
			direction = "horizontal";
		else if (direction == "horizontal")
			direction = "vertical";
	}
	
	protected void rotate(Point clickPoint) 
	{
		int newWidth = this.getSize().height;
		int newHeight = this.getSize().width;
		
		this.setSize(newWidth, newHeight);
		((Graphics2D)this.getGraphics()).rotate(3.14, clickPoint.getX(), clickPoint.getY());
	}

}