package bship.gui;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BattleShipExceptionHandler extends Throwable
{
	
	public BattleShipExceptionHandler()
	{
		String message = "An internal error ocurred";
		JOptionPane.showMessageDialog(null, message, "Internal Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
	
	
}
