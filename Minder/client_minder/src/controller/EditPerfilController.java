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
import model.UserDTO;
import model.network.NetworkManager;
import network.ComunicationData;
import view.EditPerfilWindow;
import view.LoginWindow;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Controlador de la vista d'editar perfil
 */
public class EditPerfilController implements ActionListener {

    private EditPerfilWindow editPerfilWindow;
    private NetworkManager networkManager;
    private User user;

    /**
     * Constructor de la classe
     * @param editPerfilWindow Vista del formulari per editar perfil
     * @param networkManager Classe que comunica amb el servidor
     */
    public EditPerfilController(EditPerfilWindow editPerfilWindow, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.editPerfilWindow = editPerfilWindow;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("volver")) {
            editPerfilWindow.dispose();
            MainWindow mainWindow = new MainWindow();
            MainController mainController = new MainController(mainWindow, networkManager);
            mainController.setUser(user);
            networkManager.setMainController(mainController);
            mainWindow.registraControladorMain(mainController);
        } else if (e.getActionCommand().equals("imagen")) {
            if (editPerfilWindow.getJfcImage().showDialog(null, null) == JFileChooser.APPROVE_OPTION) {
                editPerfilWindow.setArchivo(editPerfilWindow.getJfcImage().getSelectedFile());
                if (editPerfilWindow.getArchivo().canRead()) {
                    if (editPerfilWindow.getArchivo().getName().endsWith("jpg") || editPerfilWindow.getArchivo().getName().endsWith("png") || editPerfilWindow.getArchivo().getName().endsWith("gif")) {
                        editPerfilWindow.mostrarImagen(editPerfilWindow.getJfcImage().getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Archivo cargado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Archivo No Compatible");
                    }
                }
            }
        } else if (e.getActionCommand().equals("aceptar")) {
            try {
                File archivo = new File(editPerfilWindow.getJfcImage().getSelectedFile().getAbsolutePath());
                if (!archivo.exists()) {
                    JOptionPane.showMessageDialog(null, "No has subido ninguna foto", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (editPerfilWindow.getDescripcio().equals("")) {
                    JOptionPane.showMessageDialog(null, "Falta la descripción", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    User user = networkManager.getUser();
                    user.setDescription(editPerfilWindow.getDescripcio());
                    user.setLang(editPerfilWindow.getLanguage());
                    user.setImg(user.convertToByteArray(editPerfilWindow.getJfcImage().getSelectedFile().getAbsolutePath()));
                    networkManager.setUser(user);
                    UserDTO userDTO = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                    networkManager.sendData(new ComunicationData("PERFIL", userDTO));
                }
            } catch (NullPointerException n) {
                if(networkManager.getUser().getImg() == null){
                    JOptionPane.showMessageDialog(null, "No has subido ninguna foto", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    User user = networkManager.getUser();
                    user.setDescription(editPerfilWindow.getDescripcio());
                    user.setLang(editPerfilWindow.getLanguage());
                    networkManager.setUser(user);
                    UserDTO userDTO = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                    networkManager.sendData(new ComunicationData("PERFIL", userDTO));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if(e.getActionCommand().equals("exit")){
            editPerfilWindow.dispose();
            LoginWindow loginWindow = new LoginWindow();
            LoginController loginController = new LoginController(loginWindow, networkManager);
            networkManager.setLoginController(loginController);
            loginWindow.registraControladorLogin(loginController);

            networkManager.sendData(new ComunicationData("ADEU", networkManager.getUser().getUsername()));
        }
    }

    /**
     * Obra la vista principal i tanca la de editar perfil
     */
    public void openMainWindow() {
        editPerfilWindow.dispose();
        MainWindow mainWindow = new MainWindow();
        MainController mainController = new MainController(mainWindow, networkManager);
        mainController.setUser(user);
        networkManager.setMainController(mainController);
        mainWindow.registraControladorMain(mainController);
    }

    /**
     * Obra un error que indica que no s'ha pogut carregar la informació actualitzada de l'usuari
     */
    public void openPerfilError() {
        JOptionPane.showMessageDialog(null, "Error inesperado, el servidor ha rechazado la solicitud!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Getter de l'usuari
     * @return L'usuari que està mostrant en aquell moment
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter de l'usuari
     * @param user L'usuari que està mostrant en aquell moment
     */
    public void setUser(User user) {
        this.user = user;
    }
}