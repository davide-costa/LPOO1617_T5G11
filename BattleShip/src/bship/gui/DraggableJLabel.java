package bship.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DraggableJLabel extends JLabel
{
	private Point initClick;
	
	public DraggableJLabel(ImageIcon image)
	{
		super(image);
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent event)
			{
				Point mouseLocation = new Point(event.getX(), event.getY());
				
				if (initClick == null)
				{
					initClick = mouseLocation;
					return;
				}
				Point labelLocation = DraggableJLabel.this.getLocation();
				
				//Determine how much the mouse moved since the initial click
				int xMoved = (labelLocation.x + mouseLocation.x) - (labelLocation.x + initClick.x);
				int yMoved = (labelLocation.y + mouseLocation.y) - (labelLocation.y + initClick.y);

				//Move picture to this position
				int newX = labelLocation.x + xMoved;
				int newY = labelLocation.y + yMoved;
				
				DraggableJLabel.this.setLocation(newX, newY);
				DraggableJLabel.this.repaint();
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
				initClick = event.getLocationOnScreen();
				
				Dimension labelSize = DraggableJLabel.this.getSize();
				
				//Move picture to this position
				int newX = initClick.x - labelSize.height / 2;
				int newY = initClick.y - labelSize.width / 2;
				
				DraggableJLabel.this.setLocation(newX, newY);
				DraggableJLabel.this.repaint();
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
				
			}
		});
	}
}
