package bship.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import bship.logic.Coords;

public class DraggableShip extends DraggableJLabel
{
	private String direction;
	private ImageIcon imageIcon;
	private ShipPlacementPanel shipPlacementPanel;
	
	public DraggableShip(ImageIcon image, ShipPlacementPanel shipPlacementPanel, Point initLocation) 
	{
		super(image);
		this.imageIcon = image;
		this.direction = "horizontal";
		this.shipPlacementPanel = shipPlacementPanel;

		this.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent event) {}

			@Override
			public void mouseEntered(MouseEvent event) {}

			@Override
			public void mouseExited(MouseEvent event) {}
			
			@Override
			public void mousePressed(MouseEvent event)
			{
				if(SwingUtilities.isRightMouseButton(event))
				{
					rotate();
				}
				else if(SwingUtilities.isLeftMouseButton(event))
					shipPlacementPanel.pickUpShip(DraggableShip.this);
				DraggableShip.this.requestFocusInWindow();
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				if (SwingUtilities.isRightMouseButton(event))
					return;
				if (SwingUtilities.isLeftMouseButton(event))
					shipPlacementPanel.tryDropShip(event, initLocation);
				DraggableShip.this.requestFocusInWindow();
			}
		});
	}
	
	protected Point getMiddlePoint() 
	{
		Rectangle bounds = this.getBounds();
		Point middlePoint = new Point((int)bounds.getCenterX(), (int)bounds.getCenterY());

		return middlePoint;
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
	
	protected void rotate() 
	{
		toggleDirection();
		imageIcon = shipPlacementPanel.getRotatedImage(imageIcon);
		int newWidth = this.getSize().height;
		int newHeight = this.getSize().width;
		
		setSize(newWidth, newHeight);
		setIcon(imageIcon);
	}

	public void resetDirection()
	{
		if (direction == "vertical")
			rotate();
	}

}
