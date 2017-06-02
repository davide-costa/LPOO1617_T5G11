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
	
	public DraggableShip(ImageIcon image, ShipPlacementPanel shipPlacementPanel, Point initLocation) 
	{
		super(image);
		this.imageIcon = image;
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
					DraggableShip.this.imageIcon = shipPlacementPanel.getRotatedImage(DraggableShip.this.imageIcon);
					Point middlePoint = DraggableShip.this.getMiddlePoint();
					rotate(middlePoint);
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
	
	protected void rotate(Point middlePoint) 
	{ 
		int newWidth = this.getSize().height;
		int newHeight = this.getSize().width;
		
		this.setSize(newWidth, newHeight);
		((Graphics2D)this.getGraphics()).rotate(Math.PI / 2, middlePoint.getX(), middlePoint.getY());
		((Graphics2D)this.getGraphics()).drawImage(imageIcon.getImage(), this.getX(), this.getY(), newWidth, newHeight, null);
	}

}
