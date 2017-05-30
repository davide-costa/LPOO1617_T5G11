package bship.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class DraggableShip extends DraggableJLabel 
{

	public DraggableShip(ImageIcon image) 
	{
		super(image);
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event)
			{
				if(SwingUtilities.isRightMouseButton(event))
					rotateShip(DraggableShip.this.);
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
