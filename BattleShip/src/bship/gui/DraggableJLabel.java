package bship.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class DraggableJLabel extends JLabel
{
	private Point initClick;
	
	public DraggableJLabel(ImageIcon image)
	{
		super(image);
		addMouseMotionListener();
		addMouseListener();
	}
	
	private void addMouseListener() 
	{
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event){}

			@Override
			public void mouseEntered(MouseEvent event){}

			@Override
			public void mouseExited(MouseEvent event){}

			@Override
			public void mousePressed(MouseEvent event)
			{
				if (SwingUtilities.isLeftMouseButton(event))
					initClick = new Point(event.getX(), event.getY());
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				if (SwingUtilities.isLeftMouseButton(event))
					initClick = null;
			}
		});
	}

	private void addMouseMotionListener()
	{
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent event)
			{
				if (!SwingUtilities.isLeftMouseButton(event))
					return;
				
				Point mouseLocation = new Point(event.getX(), event.getY());
				if (initClick == null)
				{
					initClick = mouseLocation;
					return;
				}
				
				drawLabelInMouseLocation(mouseLocation);
			}

			private Point calculateLabelPos(Point mouseLocation) 
			{
				Point labelLocation = DraggableJLabel.this.getLocation();
				
				//Determine how much the mouse moved since the initial click
				int xMoved = (labelLocation.x + mouseLocation.x) - (labelLocation.x + initClick.x);
				int yMoved = (labelLocation.y + mouseLocation.y) - (labelLocation.y + initClick.y);

				//Move picture to this position
				int newX = labelLocation.x + xMoved;
				int newY = labelLocation.y + yMoved;
				return new Point(newX, newY);
			}

			private void drawLabelInMouseLocation(Point mouseLocation) 
			{
				Point labelPos = calculateLabelPos(mouseLocation);
				DraggableJLabel.this.setLocation((int)labelPos.getX(), (int)labelPos.getY());
				DraggableJLabel.this.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent event){}
		});
	}
}
