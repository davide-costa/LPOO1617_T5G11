package dkeep.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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

import dkeep.logic.GameMap;

public class GameArea extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private Gui gui;
	private BufferedImage ogre_image;
	private BufferedImage hero_image;
	private BufferedImage guard_image;
	private BufferedImage wall_image;
	private BufferedImage key_image;
	private BufferedImage door_open_image;
	private BufferedImage door_closed_image;
	private int curr_x_pos;
	private int curr_y_pos;
	
	public GameArea(Gui gui)
	{
		this.gui = gui;
		ogre_image = loadImage("img/ogre.png");
		ogre_image.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		hero_image = loadImage("img/hero.png");
		guard_image = loadImage("img/guard.png");
		wall_image = loadImage("img/wall.png");
		key_image = loadImage("img/key.png");
		door_open_image = loadImage("img/door_opened.png");
		door_closed_image = loadImage("img/door_closed.png");
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		char map[][] = gui.game.GetGameMap();
		int map_x_size = map[0].length;
		int map_y_size = map.length;
		String character;
		
		super.paintComponent(g); // clear background
		
		curr_y_pos = 0;
		curr_x_pos = 0;
		for (int i = 0; i < map_y_size; i++)
		{
			for (int j = 0; j < map_x_size; j++)
				DrawElement(g, map[i][j]); 
			
			curr_y_pos += 30;
			curr_x_pos = 0;
		}
			
		// g.drawImage(ogre_image, 20, 20, this);
	}

	public void DrawElement(Graphics g, char element)
	{
		switch(element)
		{
		case 'X':
			g.drawImage(wall_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'I':
			g.drawImage(door_open_image, curr_x_pos, curr_y_pos, null);	
			break;
		case 'S':
			g.drawImage(door_closed_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'G':
			g.drawImage(guard_image, curr_x_pos, curr_y_pos, null);	
			break;
		case 'O':
			g.drawImage(ogre_image, curr_x_pos, curr_y_pos, null);
			break;
		case 'k':
			g.drawImage(key_image, curr_x_pos, curr_y_pos, null);	
			break;
		}
		
		curr_x_pos += 30;
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
