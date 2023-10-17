/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package controller;

import model.entity.Chat;
import model.entity.User;
import model.UserDTO;
import model.network.NetworkManager;
import network.ComunicationData;
import view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Controlador de la vista principal
 */
public class MainController implements ActionListener {

    private MainWindow mainWindow;
    private NetworkManager networkManager;
    private User user;
    private boolean tancar;

    /**
     * Constructor de la classe
     * @param mainWindow Vista principal que permet acceptar o denegar usuaris
     * @param networkManager Classe que comunica amb el servidor
     */
    public MainController(MainWindow mainWindow, NetworkManager networkManager){
        this.networkManager = networkManager;
        this.mainWindow = mainWindow;
        tancar = false;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("foto")) {
            //obrir window amb més info
            if(mainWindow.getNoMoreUsers()){
                mainWindow.errorNoMoreList();
            } else {
                mainWindow.dispose();
                //PerfilOtroWindow perfilOtroWindow = new PerfilOtroWindow();
                PerfilOtroWindow perfilOtroWindow = new PerfilOtroWindow(getUser().getImg(), getUser().getLang(), getUser().getDescription(), getUser().getUsername(), getUser().getEdad());
                //perfilOtroWindow.setUserAtributs(getUser().getImg(), getUser().getDescription(), getUser().getUsername(),getUser().getLang(), getUser().getEdad());
                PerfilOtroController perfilOtroController = new PerfilOtroController(perfilOtroWindow, networkManager);
                perfilOtroController.setUser(getUser());
                networkManager.setPerfilOtroController(perfilOtroController);
                perfilOtroWindow.registraControladorPerfilOtro(perfilOtroController);
            }
        } else if (e.getActionCommand().equals("like")) {
            //enviar like al server
            if(!mainWindow.getNoMoreUsers()){
                User user = getUser();
                UserDTO userDTOto = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                networkManager.sendData(new ComunicationData("LIKE", userDTOto));
            }

        } else if (e.getActionCommand().equals("dislike")) {
            //passar al seguent usuari

            if(!mainWindow.getNoMoreUsers()){
                User user = getUser();
                UserDTO userDTOto = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                networkManager.sendData(new ComunicationData("DISLIKE", userDTOto));
            }

        } else if (e.getActionCommand().equals("exit")) {
            mainWindow.dispose();
            LoginWindow loginWindow = new LoginWindow();
            LoginController loginController = new LoginController(loginWindow, networkManager);
            networkManager.setLoginController(loginController);
            loginWindow.registraControladorLogin(loginController);
            networkManager.sendData(new ComunicationData("ADEU", networkManager.getUser().getUsername()));
        } else if (e.getActionCommand().equals("chat")) {
            networkManager.sendData(new ComunicationData("CHATS", networkManager.getUser().getUsername()));
            tancar = true;
        } else if (e.getActionCommand().equals("perfil")) {
            mainWindow.dispose();
            EditPerfilWindow editPerfilWindow = new EditPerfilWindow(networkManager.getUser().getImg(), networkManager.getUser().getLang(), networkManager.getUser().getDescription(), networkManager.getUser().getUsername(), networkManager.getUser().getEdad());
            EditPerfilController editPerfilController = new EditPerfilController(editPerfilWindow, networkManager);
            editPerfilController.setUser(user);
            networkManager.setEditPerfilController(editPerfilController);
            editPerfilWindow.registraControladorPerfil(editPerfilController);
        }
    }

    /**
     * Getter del user
     * @return Usuari que està mostrant
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter del user
     * @param user Usuari que està mostrant
     */
    public void setUser(User user){
        this.user = user;
        if(user != null) {
            mainWindow.setUserAtributes(user.getImg(), user.getUsername(), user.getEdad());
        } else {
            mainWindow.setNoMoreUsers();
        }
    }

    /**
     * Indica que no hi han més usuaris
     */
    public void setNoMoreUsers() {
        mainWindow.setNoMoreUsers();
        user = null;
    }

    /**
     * Obra la finestra de llista de chats i tanca la vista principal
     * @param chats Llista de chats
     */
    public void openChatListWindow(LinkedList<Chat> chats) {
        mainWindow.dispose();
        ChatListWindow chatListWindow = new ChatListWindow(chats);
        ChatListController chatListController = new ChatListController(chatListWindow, networkManager);
        chatListController.setChatList(chats);
        chatListController.setUserMostrado(user);
        networkManager.setChatListController(chatListController);
        chatListWindow.registraControladorChatList(chatListController);
    }

    public boolean isTancar() {
        return tancar;
    }
}