package bship.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import bship.logic.Coords;

public class DraggableShip extends DraggableJLabel 
{
	private ShipPlacementPanel shipPlacementPanel;
	private Point pickUpPoint;
	
	public DraggableShip(ImageIcon image, ShipPlacementPanel shipPlacementPanel) 
	{
		super(image);
		this.shipPlacementPanel = shipPlacementPanel;
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event)
			{
				if(SwingUtilities.isRightMouseButton(event))
					rotate(event.getPoint());
				else if(SwingUtilities.isLeftMouseButton(event))
					pickUpPoint = event.getPoint();
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
				
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				if(SwingUtilities.isLeftMouseButton(event))
					shipPlacementPanel.tryDropShip(event, pickUpPoint);
			}
		});
	}

	protected void rotate(Point clickPoint) 
	{
		int newWidth = this.getSize().height;
		int newHeight = this.getSize().width;
		
		this.setSize(newWidth, newHeight);
		((Graphics2D)this.getGraphics()).rotate(3.14, clickPoint.getX(), clickPoint.getY());
	}

}
