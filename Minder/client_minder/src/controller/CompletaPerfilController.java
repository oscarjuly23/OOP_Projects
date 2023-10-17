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
import view.CompletaPerfilWindow;
import view.LoginWindow;
import view.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Controlador de la vista de completar el perfil
 */

public class CompletaPerfilController implements ActionListener {

    private CompletaPerfilWindow completaPerfilWindow;
    private NetworkManager networkManager;

    /**
     * Constructor de la classe
     * @param completaPerfilWindow Vista del formulari de completar perfil
     * @param networkManager Classe que comunica amb el servidor
     */
    public CompletaPerfilController(CompletaPerfilWindow completaPerfilWindow, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.completaPerfilWindow = completaPerfilWindow;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("volver")) {
            completaPerfilWindow.dispose();
            LoginWindow loginWindow = new LoginWindow();
            LoginController loginController = new LoginController(loginWindow, networkManager);
            networkManager.setLoginController(loginController);
            loginWindow.registraControladorLogin(loginController);
        } else if (e.getActionCommand().equals("imagen")) {
            if (completaPerfilWindow.getJfcImage().showDialog(null, null) == JFileChooser.APPROVE_OPTION) {
                completaPerfilWindow.setArchivo(completaPerfilWindow.getJfcImage().getSelectedFile());
                if (completaPerfilWindow.getArchivo().canRead()) {
                    if (completaPerfilWindow.getArchivo().getName().toLowerCase().endsWith("jpg") || completaPerfilWindow.getArchivo().getName().toLowerCase().endsWith("png") || completaPerfilWindow.getArchivo().getName().toLowerCase().endsWith("gif")) {
                        completaPerfilWindow.mostrarImagen(completaPerfilWindow.getJfcImage().getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Archivo guardado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Archivo No Compatible");
                    }
                }
            }
        } else if (e.getActionCommand().equals("aceptar")) {
            try {
                File archivo = new File(completaPerfilWindow.getJfcImage().getSelectedFile().getAbsolutePath());
                if (!archivo.exists()) {
                    JOptionPane.showMessageDialog(null, "No has subido ninguna foto", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (completaPerfilWindow.getDescripcio().equals("")) {
                    JOptionPane.showMessageDialog(null, "Falta la descripción", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    User user = networkManager.getUser();
                    user.setDescription(completaPerfilWindow.getDescripcio());
                    user.setLang(completaPerfilWindow.getLanguage());
                    BufferedImage bImage = ImageIO.read(new File(completaPerfilWindow.getJfcImage().getSelectedFile().getAbsolutePath()));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "png", bos );
                    byte [] data = bos.toByteArray();
                    user.setImg(data);
                    networkManager.setUser(user);
                    UserDTO userDTO = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                    networkManager.sendData(new ComunicationData("EDIT", userDTO));
                    networkManager.sendData(new ComunicationData("REGLOG", userDTO));
                }
            } catch (NullPointerException | IOException n) {
                JOptionPane.showMessageDialog(null, "No has subido ninguna foto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * Obra la vista principal i tanca la de completa perfil del usuari
     */
    public void openMainWindow() {
        completaPerfilWindow.dispose();
        MainWindow mainWindow = new MainWindow();
        MainController mainController = new MainController(mainWindow, networkManager);
        networkManager.setMainController(mainController);
        mainWindow.registraControladorMain(mainController);
    }

    /**
     * Obra un error que indica que no s'ha pogut carregar la informació actualitzada de l'usuari
     */
    public void openPerfilError() {
        JOptionPane.showMessageDialog(null, "Error inesperado, el servidor ha rechazado la solicitud!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}