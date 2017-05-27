package bship.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExitPanel extends BattleShipGui 
{
	private JPanel exitPanel;
	private JTextField txtAreYouSure;
	private JButton btnYes;
	private JButton btnNo;
	
	public ExitPanel(JFrame frame, JPanel menuPanel)
	{
		this.frame = frame;
		this.lastPanel = menuPanel;
		exitPanel = new JPanel();
		exitPanel.setBounds(610, 340, 700, 400);
		frame.getContentPane().add(exitPanel);
		exitPanel.setLayout(null);
		
		txtAreYouSure = new JTextField();
		txtAreYouSure.setHorizontalAlignment(SwingConstants.CENTER);
		txtAreYouSure.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		txtAreYouSure.setText("Are you sure you want to quit?");
		txtAreYouSure.setBounds(0, 64, 700, 69);
		exitPanel.add(txtAreYouSure);
		txtAreYouSure.setColumns(10);
		
		btnYes = new JButton("Yes");
		btnYes.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnYes.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnYes.setBounds(123, 243, 93, 37);
		exitPanel.add(btnYes);
		
		btnNo = new JButton("No");
		btnNo.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnNo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				backToMenu();
			}
		});
		btnNo.setBounds(480, 243, 93, 37);
		exitPanel.add(btnNo);
		
		lastPanel.setVisible(false);
		exitPanel.setVisible(true);
		exitPanel.requestFocusInWindow();
	}
	
	private void backToMenu()
	{
		exitPanel.setVisible(false);
		lastPanel.setVisible(true);
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
			backToMenu();
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}

}
