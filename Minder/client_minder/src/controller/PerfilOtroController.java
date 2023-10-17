/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package controller;

import model.entity.User;
import model.network.NetworkManager;
import network.ComunicationData;
import view.LoginWindow;
import view.MainWindow;
import view.PerfilOtroWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de la vista que mostra l'usuari que s'està mostrant
 */
public class PerfilOtroController implements ActionListener {
    private PerfilOtroWindow perfilOtroWindow;
    private NetworkManager networkManager;
    private User user;

    /**
     * Controlador de la classe
     * @param perfilOtroWindow Vista que mostra l'usuari que s'està mostrant
     * @param networkManager Classe que comunica amb el servidor
     */
    public PerfilOtroController(PerfilOtroWindow perfilOtroWindow, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.perfilOtroWindow = perfilOtroWindow;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("volver")) {
            perfilOtroWindow.dispose();
            MainWindow mainWindow = new MainWindow();
            MainController mainController = new MainController(mainWindow, networkManager);
            mainController.setUser(user);
            networkManager.setMainController(mainController);
            mainWindow.registraControladorMain(mainController);
        } else if(e.getActionCommand().equals("exit")) {
            perfilOtroWindow.dispose();
            LoginWindow loginWindow = new LoginWindow();
            LoginController loginController = new LoginController(loginWindow, networkManager);
            networkManager.setLoginController(loginController);
            loginWindow.registraControladorLogin(loginController);
            networkManager.sendData(new ComunicationData("ADEU", networkManager.getUser().getUsername()));
        }
    }

    /**
     * Getter de l'usuari que s'està mostrant
     * @return L'usuari que s'està mostrant
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter de l'usuari que s'està mostrant
     * @param user Usuari que s'està mostrant
     */
    public void setUser(User user) {
          this.user = user;
          perfilOtroWindow.actualitza(user);
    }
}