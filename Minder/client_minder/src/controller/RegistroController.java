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
import view.LoginWindow;
import view.RegistroWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de la vista de registre
 */
public class RegistroController implements ActionListener {

    private RegistroWindow registroWindow;
    private NetworkManager networkManager;

    /**
     * Constructor de la classe
     * @param registroWindow Vista que mostra el formulari per al registre
     * @param networkManager Classe que comunica amb el servidor
     */
    public RegistroController(RegistroWindow registroWindow, NetworkManager networkManager){
        this.networkManager = networkManager;
        this.registroWindow = registroWindow;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("volver")){
            registroWindow.dispose();
            LoginWindow loginWindow = new LoginWindow();
            LoginController loginController = new LoginController(loginWindow, networkManager);
            networkManager.setLoginController(loginController);
            loginWindow.registraControladorLogin(loginController);
        } else if(e.getActionCommand().equals("registrar")){

            if(registroWindow.getEdad()<18){
                JOptionPane.showMessageDialog(null,"No puedes registrarte si eres menor de edad!","Error",JOptionPane.ERROR_MESSAGE);
            } else if(registroWindow.getEmail() == null){
                JOptionPane.showMessageDialog(null,"El email no sigue un formato válido","Error",JOptionPane.ERROR_MESSAGE);
            } else if(!registroWindow.validaPassword()){
                JOptionPane.showMessageDialog(null,"La contraseña es incorrecta. Debe incluir mayúsculas, minúsculas y numeros. Debe ser de 8 carácteres mínimo","Error",JOptionPane.ERROR_MESSAGE);
            } else if(!registroWindow.getPassword().equals(registroWindow.getPasswordConfirm())){
                JOptionPane.showMessageDialog(null,"Las contraseñas no coinciden","Error",JOptionPane.ERROR_MESSAGE);
            } else {

                boolean isPremium = false;
                if (registroWindow.getSubscriptionType().equals("Premium")) {
                    isPremium = true;
                }
                UserDTO userDTO = new UserDTO(registroWindow.getUser(), registroWindow.getEdad(), isPremium, registroWindow.getEmail(), registroWindow.getPassword(), null, null, null);
                networkManager.sendData(new ComunicationData("REG", userDTO));
            }
        }
    }

    /**
     * Obra la vista de login i tanca la de registre
     */
    public void openLoginView(){
        LoginWindow loginWindow = new LoginWindow();
        LoginController loginController = new LoginController(loginWindow, networkManager);
        networkManager.setLoginController(loginController);
        loginWindow.registraControladorLogin(loginController);
        registroWindow.dispose();
    }

    /**
     * Mostra errors corresponents a no poder-se registrar amb èxit
     * @param error Classe d'error
     */
    public void openRegisterError(int error){
        if(error == 1){
            JOptionPane.showMessageDialog(null, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,"Error inesperado, el servidor ha rechazado la solicitud!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}