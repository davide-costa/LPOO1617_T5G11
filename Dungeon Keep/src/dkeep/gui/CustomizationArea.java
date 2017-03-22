package dkeep.gui;

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
import javax.swing.SwingUtilities;

public class CustomizationArea extends JPanel implements MouseListener
{
	private BufferedImage ogre_image;
	private BufferedImage hero_image;
	private BufferedImage wall_image;
	private BufferedImage key_image;
	private BufferedImage club_image;
	private BufferedImage door_closed_image;
	
	private int images_x_length = 40;
	private int images_y_length = 40;
	
	
	public CustomizationArea()
	{
		addMouseListener(this);
		LoadImages();
	}
	
	public void LoadImages()
	{
		ogre_image = loadImage("img/ogre.png");
		hero_image = loadImage("img/hero.png");
		wall_image = loadImage("img/wall.png");
		key_image = loadImage("img/key.png");
		door_closed_image = loadImage("img/door_closed.png");
		club_image = loadImage("img/club.png");
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		char map[][] = {
				{ 'X','X','X','X','X','X','X','X','X' },
				{ 'I',0,0,0,0,0,0,'k','X' },
				{ 'X',0,0,0,0,0,0,0,'X' },
				{ 'X',0,0,0,0,0,0,0,'X' },
				{ 'X',0,0,0,0,0,0,0,'X' },
				{ 'X',0,0,0,0,0,0,0,'X' },
				{ 'X',0,0,0,0,0,0,0,'X' },
				{ 'X',0,0,0,0,0,0,0,'X' },
				{ 'X','X','X','X','X','X','X','X','X' }
			};
		int curr_y_pos = 0;
		int curr_x_pos = 0;
		int map_x_size = map[0].length;
		int map_y_size = map.length;
		
		super.paintComponent(g); // clear background
		for (int i = 0; i < map_y_size; i++)
		{
			for (int j = 0; j < map_x_size; j++)
			{
				DrawElement(g, map[i][j], curr_x_pos, curr_y_pos);
				curr_x_pos += images_x_length;
			}
			
			curr_y_pos += images_y_length;
			curr_x_pos = 0;
		}
	}

	public void DrawElement(Graphics g, char element, int curr_x_pos, int curr_y_pos)
	{
		switch(element)
		{
		case 'X':
			g.drawImage(wall_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'I':
			g.drawImage(door_closed_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'H':
			g.drawImage(hero_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'O':
			g.drawImage(ogre_image, curr_x_pos, curr_y_pos, null);
			break;
		case '*':
			g.drawImage(club_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'k':
			g.drawImage(key_image, curr_x_pos, curr_y_pos, null);
			break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent mouse_event) 
	{

	}

	@Override
	public void mouseEntered(MouseEvent mouse_event) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent mouse_event)
	{

	}

	@Override
	public void mousePressed(MouseEvent mouse_event) 
	{
		if(SwingUtilities.isRightMouseButton(mouse_event))
			RemoveElementAt(mouse_event.getX(), mouse_event.getY());
		else if(SwingUtilities.isLeftMouseButton(mouse_event))
			AddElementAt(mouse_event.getX(), mouse_event.getY());	
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
