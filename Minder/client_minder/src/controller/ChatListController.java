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
import model.network.NetworkManager;
import network.ComunicationData;
import view.ChatListWindow;
import view.ChatWindow;
import view.LoginWindow;
import view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * És el controlador de la vista de la llista de chats.
 */
public class ChatListController implements ActionListener {
    private ChatListWindow chatListWindow;
    private NetworkManager networkManager;
    private LinkedList<Chat> chatList;
    private User userMostrado;

    /**
     * Constructor de la classe
     * @param chatListWindow Vista de la llista de chats
     * @param networkManager Classe que comunica amb el servidor
     */
    public ChatListController(ChatListWindow chatListWindow, NetworkManager networkManager) {
        this.chatListWindow = chatListWindow;
        this.networkManager = networkManager;
    }

    /**
     * Setter de l'usuari que s'està mostrant en aquell moment
     * @param userMostrado usuari que s'està mostrant en aquell moment
     */
    public void setUserMostrado(User userMostrado) {
        this.userMostrado = userMostrado;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Back":
                chatListWindow.dispose();
                MainWindow mainWindow = new MainWindow();
                MainController mainController = new MainController(mainWindow, networkManager);
                mainController.setUser(userMostrado);
                networkManager.setMainController(mainController);
                mainWindow.registraControladorMain(mainController);
                break;
            case "Exit":
                chatListWindow.dispose();
                LoginWindow loginWindow = new LoginWindow();
                LoginController loginController = new LoginController(loginWindow, networkManager);
                networkManager.setLoginController(loginController);
                loginWindow.registraControladorLogin(loginController);
                networkManager.sendData(new ComunicationData("ADEU", networkManager.getUser().getUsername()));
                break;
            default:
                User userChat = new User();
                for (Chat c : chatList) {
                     if (String.valueOf(c.getId()).equals(e.getActionCommand())) {
                         userChat = c.getOtherUser();
                         break;
                     }
                }
                chatListWindow.dispose();
                ChatWindow chatWindow = new ChatWindow(userChat.getUsername(), userChat.getImg(),Integer.valueOf(e.getActionCommand()));
                ChatController chatController = new ChatController(chatWindow,networkManager);
                chatController.setUserMostrant(userMostrado);
                chatController.setChats(chatList);
                chatWindow.registraControladorChat(chatController);
                networkManager.setChatController(chatController);
                chatWindow.setVisible(true);
                networkManager.sendData(new ComunicationData("LLISTAMSG", e.getActionCommand()));
                break;
        }
    }

    /**
     * Actualitza la llista de chats
     * @param chats Llista de chats
     */
    public void updateChats(LinkedList<Chat> chats) {
        chatListWindow.actualitzaChats(chats);
        chatListWindow.registraControladorChatList(this);
        this.chatList = chats;
    }

    /**
     * Setter de la llista de chats
     * @param chatList Llista de chats
     */
    public void setChatList(LinkedList<Chat> chatList) {
        this.chatList = chatList;
    }
}