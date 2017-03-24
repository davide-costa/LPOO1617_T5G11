package dkeep.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dkeep.logic.Game;


public class GameArea extends JPanel implements KeyListener
{
	private Gui gui;
	private Game game;
	private Hashtable<Character, BufferedImage> images;
	
	private int images_x_length = 40;
	private int images_y_length = 40;
	
	public GameArea(Gui gui)
	{
		this.gui = gui;
		images = new Hashtable<Character, BufferedImage>();
		addKeyListener(this);
		LoadImages();
	}
	
	public void LoadImages()
	{
		images.put('H', loadImage("img/hero.png"));
		images.put('K', loadImage("img/hero_with_key.png"));
		images.put('O', loadImage("img/ogre.png"));
		images.put('*', loadImage("img/club.png"));
		images.put('8', loadImage("img/ogre_stunned.png"));
		images.put('$', loadImage("img/ogre_club_key.png"));
		images.put('G', loadImage("img/guard.png"));
		images.put('g', loadImage("img/guard_sleeping.png"));
		images.put('X', loadImage("img/wall.png"));
		images.put('k', loadImage("img/key.png"));
		images.put('I', loadImage("img/door_closed.png"));
		images.put('S', loadImage("img/door_opened.png"));
	}
	
	public void SetGame(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		char map[][] = game.GetGameMap();
		int curr_y_pos = 0;
		int curr_x_pos = 0;
		int map_x_size = map[0].length;
		int map_y_size = map.length;
		
		super.paintComponent(g); // clear background
		for (int i = 0; i < map_y_size; i++)
		{
			for (int j = 0; j < map_x_size; j++)
			{
				g.drawImage(images.get(map[i][j]), curr_x_pos, curr_y_pos, null);
				curr_x_pos += images_x_length;
			}
			
			curr_y_pos += images_y_length;
			curr_x_pos = 0;
		}
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
