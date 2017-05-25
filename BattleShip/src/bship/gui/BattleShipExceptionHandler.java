package bship.gui;
import javax.swing.JOptionPane;

public class BattleShipExceptionHandler
{
	public static void handleBattleShipException()
	{
		String message = "An internal error ocurred";
		JOptionPane.showMessageDialog(null, message, "Internal Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}
