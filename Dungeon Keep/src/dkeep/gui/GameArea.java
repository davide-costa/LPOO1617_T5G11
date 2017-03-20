package dkeep.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameArea extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private Gui gui;
	private BufferedImage ogre_image;
	
	public GameArea(Gui gui)
	{
		this.gui = gui;
		ogre_image = loadImage("src/dkeep/gui/ogre.png");
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g); // clear background
		 g.drawImage(ogre_image, 20, 20, this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		 case KeyEvent.VK_LEFT: 
			 gui.NewPlay("left");
			 break;
		 case KeyEvent.VK_RIGHT: 
			 gui.NewPlay("right");
			 break;
		 case KeyEvent.VK_UP: 
			 gui.NewPlay("up");
			 break;
		 case KeyEvent.VK_DOWN: 
			 gui.NewPlay("down");
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
	
	 public BufferedImage loadImage(String path) 
	 {
	        BufferedImage image = null;
	        try 
	        {
	            image = ImageIO.read(new File(path));
	        }
	        catch(IOException e) 
	        {
	            // May as well use what is given...
	            System.out.println("read error:" + e.getMessage());
	        }
	        return image;
	    }
}
