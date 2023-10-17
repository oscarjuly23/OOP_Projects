/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package controller;

import model.UserDTO;
import model.network.NetworkManager;
import network.ComunicationData;
import view.CompletaPerfilWindow;
import view.LoginWindow;
import view.MainWindow;
import view.RegistroWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe que controla la vista de login
 */
public class LoginController implements ActionListener {

    private LoginWindow loginWindow;
    private NetworkManager networkManager;

    /**
     * Constructor de la classe
     * @param loginWindow Vista del formulari per a loguejar l'usuari
     * @param networkManager Classe que comunica amb el servidor
     */
    public LoginController(LoginWindow loginWindow, NetworkManager networkManager){
        this.loginWindow = loginWindow;
        this.networkManager = networkManager;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("login")) {
            System.out.println("apretat!");
            if (loginWindow.getUserOEmail().equals("") || loginWindow.getPassword().equals("")){
                JOptionPane.showMessageDialog(null, "Rellena todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(loginWindow.getUserOEmail());
                userDTO.setPassword(loginWindow.getPassword());
                networkManager.sendData(new ComunicationData("LOG",  userDTO));
            }
        } else if(e.getActionCommand().equals("registrar")){
            loginWindow.dispose();
            RegistroWindow registroWindow = new RegistroWindow();
            RegistroController registroController = new RegistroController(registroWindow, networkManager);
            networkManager.setRegistroController(registroController);
            registroWindow.registraControladorRegistro(registroController);
        }
    }

    /**
     * Obra la vista de completar el perfil i tanca la vista de login
     */
    public void openPerfilWindow(){
        loginWindow.dispose();
        CompletaPerfilWindow completaPerfilWindow = new CompletaPerfilWindow();
        CompletaPerfilController completaPerfilController = new CompletaPerfilController(completaPerfilWindow, networkManager);
        networkManager.setCompletaPerfilController(completaPerfilController);
        completaPerfilWindow.registraControladorPerfil(completaPerfilController);
    }

    /**
     * Obra la vista principal i tanca la vista de login
     */
    public void openMainWindow(){
        loginWindow.dispose();
        MainWindow mainWindow = new MainWindow();
        MainController mainController = new MainController(mainWindow, networkManager);
        networkManager.setMainController(mainController);
        mainWindow.registraControladorMain(mainController);
    }

    /**
     * Mostra errors corresponents a no poder-se loguejar
     * @param error Classe d'error
     */
    public void openLoginError(int error){
        if(error == 1){
            JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos!","Error",JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,"Error inesperado, el servidor ha rechazado la solicitud!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}