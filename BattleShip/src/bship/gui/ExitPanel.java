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
		
		createExitPanel();
		createTxtAreYouSure();
		createBtnYes();
		createBtnNo();

		lastPanel.setVisible(false);
		exitPanel.setVisible(true);
		exitPanel.addKeyListener(this);
		exitPanel.requestFocusInWindow();
	}

	private void createExitPanel() 
	{
		exitPanel = new JPanel();
		exitPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(exitPanel);
		exitPanel.setLayout(null);
	}
	
	private void createBtnYes() 
	{
		btnYes = new JButton("Yes");
		btnYes.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnYes.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnYes.setBounds(745, 580, 90, 35);
		exitPanel.add(btnYes);
	}
	
	private void createBtnNo() 
	{
		btnNo = new JButton("No");
		btnNo.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		btnNo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new Menu(ExitPanel.this.frame, ExitPanel.this.exitPanel);
			}
		});
		btnNo.setBounds(1085, 580, 90, 35);
		exitPanel.add(btnNo);
	}

	private void createTxtAreYouSure() 
	{
		txtAreYouSure = new JTextField();
		txtAreYouSure.setEditable(false);
		txtAreYouSure.setHorizontalAlignment(SwingConstants.CENTER);
		txtAreYouSure.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		txtAreYouSure.setText("Are you sure you want to quit?");
		txtAreYouSure.setBounds(610, 450, 700, 65);
		exitPanel.add(txtAreYouSure);
		txtAreYouSure.setColumns(10);
	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
			new Menu(this.frame, this.exitPanel);
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
}
