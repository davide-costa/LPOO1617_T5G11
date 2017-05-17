package bship.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BattleShipServerLogin 
{
	private JFrame frame;
	private JPanel menuPanel;
	private JTextField txtNewWindowFor;
	private JPanel battleShipLoginPanel;
	
	public BattleShipServerLogin(JFrame frame, JPanel menuPanel)
	{
		this.frame = frame;
		this.menuPanel = menuPanel;
		battleShipLoginPanel = new JPanel();
		frame.getContentPane().add(battleShipLoginPanel, "name_160821879791158");
		battleShipLoginPanel.setLayout(null);
		battleShipLoginPanel.setVisible(false);
		battleShipLoginPanel.setVisible(true);
		
		txtNewWindowFor = new JTextField();
		txtNewWindowFor.setText("NEW WINDOW FOR THE WIN");
		txtNewWindowFor.setBounds(81, 80, 261, 53);
		battleShipLoginPanel.add(txtNewWindowFor);
		txtNewWindowFor.setColumns(10);
		
		menuPanel.setVisible(false);
		battleShipLoginPanel.setVisible(true);
	}
}
