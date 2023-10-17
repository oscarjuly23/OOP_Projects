/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

import controller.LoginController;

import model.ClasseJson;
import model.LlegirJsonConfig;
import model.network.NetworkManager;

import view.LoginWindow;

import javax.swing.*;

/**
 * Main del programa client
 */
public class main {

    /**
     * Executa el main
     * @param args Array d'arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LlegirJsonConfig lectorJson = new LlegirJsonConfig();
                ClasseJson json = lectorJson.obtenirConfig();
                NetworkManager networkManager = new NetworkManager(json.getIp(), json.getPort());
                LoginWindow loginWindow = new LoginWindow();
                LoginController loginController = new LoginController(loginWindow, networkManager);
                networkManager.setLoginController(loginController);
                loginWindow.registraControladorLogin(loginController);
            }
        });
    }
}