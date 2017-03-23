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
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dkeep.logic.Coords;

public class CustomizationArea extends JPanel implements MouseListener
{
	private CustomizableKeepMap cust_keep_map;
	private CustomizableJFrame cust_frame;
	private Hashtable<Character, BufferedImage> images;
	private int images_x_length = 40;
	private int images_y_length = 40;
	
	
	public CustomizationArea(CustomizableJFrame cust_frame)
	{
		cust_keep_map = new CustomizableKeepMap();
		this.cust_frame = cust_frame;
		images = new Hashtable<Character, BufferedImage>();
		addMouseListener(this);
		LoadImages();
	}
	
	public CustomizableKeepMap GetCustKeepMap()
	{
		return cust_keep_map;
	}
	
	public void LoadImages()
	{
		images.put('O',loadImage("img/ogre.png"));
		images.put('H',loadImage("img/hero.png"));
		images.put('X',loadImage("img/wall.png"));
		images.put('k',loadImage("img/key.png"));
		images.put('I',loadImage("img/door_closed.png"));
		images.put('*',loadImage("img/club.png"));
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		char map[][] = cust_keep_map.GetMap();
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
	
	public Coords ScrCoordsToBoardCoords(Coords scr_coords)
	{
		int board_x = scr_coords.GetX() / images_x_length;
		int board_y = scr_coords.GetY() / images_y_length;
		
		return new Coords(board_x, board_y);
	}

	private void AddElementAt(int x, int y) 
	{
		Coords board_coords = ScrCoordsToBoardCoords(new Coords(x,y));
		String element_selected = cust_frame.GetElementSelected();
		cust_keep_map.AddElementAt(board_coords, element_selected);
		this.repaint();
	}

	private void RemoveElementAt(int x, int y)
	{
		Coords board_coords = ScrCoordsToBoardCoords(new Coords(x,y));
		cust_keep_map.RemoveElementAt(board_coords);
		this.repaint();
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
