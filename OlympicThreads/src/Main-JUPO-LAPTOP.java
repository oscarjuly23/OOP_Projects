import javax.swing.SwingUtilities;

import controlador.Controller;
import model.Model;
import vista.ConfigWindow;
import vista.MainWindow;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {

			MainWindow mainWindow = new MainWindow();
			ConfigWindow configWindow = new ConfigWindow();

			Controller controller = new Controller(mainWindow, configWindow);

			configWindow.registerController(controller);
			configWindow.setVisible(true);
		});

	}

}
