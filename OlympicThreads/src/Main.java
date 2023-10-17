import Controller.Controller;
import Vista.ConfigWindow;
import Vista.MainWindow;

import javax.swing.*;

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
