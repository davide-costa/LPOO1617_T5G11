package dkeep.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GameArea extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private Gui gui;
	
	public GameArea(Gui gui)
	{
		this.gui = gui;
		setBounds(43, 86, 527, 399);
		addKeyListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g); // clear background
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		 case KeyEvent.VK_LEFT: 
			 gui.NewPlay('a');
			 break;
		 case KeyEvent.VK_RIGHT: 
			 gui.NewPlay('d');
			 break;
		 case KeyEvent.VK_UP: 
			 gui.NewPlay('w');
			 break;
		 case KeyEvent.VK_DOWN: 
			 gui.NewPlay('s');
			 break;
		 }
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{

		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
	
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		
		
	}
	
	public void DrawBoard(char curr_map[][])
	{
		int map_x_size = curr_map[0].length;
		int map_y_size = curr_map.length;
		String character;
		
		//game_area.setText(null);
		for (int i = 0; i < map_y_size; i++)
		{
			for (int j = 0; j < map_x_size; j++)
			{
				if(curr_map[i][j] == 0)
					character = "    ";
				else
					character = curr_map[i][j] + " ";
				//game_area.append(character);
			}
		//	game_area.append("\n");
		}
	}
}
