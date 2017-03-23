package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class CustomizableJFrame extends JFrame
{
	private JPanel contentPane;
	private String element_selected = "";
	private CustomizationArea cust_area;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					CustomizableJFrame frame = new CustomizableJFrame();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomizableJFrame() 
	{
		setBounds(100, 100, 1152, 851);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		//String option = (String) JOptionPane.showInputDialog(null, message);
		//System.out.println(option);
		
		JTextField x_size_str = new JTextField();
		JTextField y_size_str = new JTextField();
		Object[] message = {
			    "KeepMap X size:", x_size_str,
			    "KeepMap Y size:", y_size_str
			};


		int option = JOptionPane.showConfirmDialog(null, message, "KeepMap size", JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) 
		{
			if(x_size_str.getText().isEmpty() || y_size_str.getText().isEmpty())
			 {
		    	JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "You must introduce x and y size for KeepMap", "Error", JOptionPane.ERROR_MESSAGE);
				this.dispose();
				return;
		    }
			
			System.out.println("::" + x_size_str.getText() + "::");
			System.out.println("::" + y_size_str.getText() + "::");
			
			int x_size = Integer.parseInt(x_size_str.getText());
		    if(x_size > 15 && x_size < 4)
		    {
		    	JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "KeepMap x size must be in 4 to 15 range", "Error", JOptionPane.ERROR_MESSAGE);
				return;
		    }
		    
		    int y_size = Integer.parseInt(y_size_str.getText());
		    if(y_size > 15 && y_size < 4)
		    {
		    	JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "KeepMap y size must be in 4 to 15 range", "Error", JOptionPane.ERROR_MESSAGE);
				return;
		    }
		    
		    cust_area.GetCustKeepMap().CreateNewGameMap(x_size, y_size);
		    	
		}
		
		//Create cust area
		cust_area = new CustomizationArea(this);
		cust_area.setBounds(38,51,846,637);
		this.getContentPane().add(cust_area);
		cust_area.requestFocusInWindow();
		cust_area.repaint();
		
		JButton btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "ogre";
			}
		});
		btnOgre.setBounds(979, 238, 115, 29);
		contentPane.add(btnOgre);
		
		JButton btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "wall";
			}
		});
		btnWall.setBounds(979, 328, 115, 29);
		contentPane.add(btnWall);
		
		JButton btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "door";
			}
		});
		btnDoor.setBounds(979, 373, 115, 29);
		contentPane.add(btnDoor);
		
		JButton btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "hero";
			}
		});
		btnHero.setBounds(979, 283, 115, 29);
		contentPane.add(btnHero);
		
		JButton btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				element_selected = "key";
			}
		});
		btnKey.setBounds(979, 418, 115, 29);
		contentPane.add(btnKey);
		
		JLabel InfoLabel = new JLabel("<html>Keep map must contain:<br>1 to 5 ogres<br>1 hero<br>A closed map (walls closing map)<br>1 key or more</html>");
		InfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		InfoLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		InfoLabel.setBounds(38, 717, 628, 95);
		contentPane.add(InfoLabel);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ArrayList<String> error_msgs = cust_area.GetCustKeepMap().IsMapValid();
				if(cust_area.GetCustKeepMap().IsMapValid().isEmpty())
				{
					cust_area.GetCustKeepMap().SaveTo("KeepMap");
					JPanel panel = new JPanel();
					InfoLabel.setText("Keep Map saved sucessfully");
					JOptionPane.showMessageDialog(panel, "Keep Map saved sucessfully", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					String message = "<html>";
					message += error_msgs.get(0);
					for(int i = 1; i < error_msgs.size(); i++)
					{
						message += "<br>";
						message += error_msgs.get(i);
					}
					message += "</html>";
					InfoLabel.setText(message);
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "The map is not valid. See the reasons below", "Error", JOptionPane.ERROR_MESSAGE);
				}
					
			}
		});
		btnSaveChanges.setBounds(979, 31, 115, 29);
		contentPane.add(btnSaveChanges);
		
		JFrame cust_frame = this;
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				cust_frame.dispose();
			}
		});
		btnExit.setBounds(979, 741, 115, 29);
		contentPane.add(btnExit);
		
	}
	
	public String GetElementSelected()
	{
		return element_selected;
	}
}
